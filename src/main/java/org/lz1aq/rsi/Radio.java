package org.lz1aq.rsi;

import org.lz1aq.utils.Misc;
import org.lz1aq.utils.DynamicByteArray;
import org.lz1aq.pyrig_interfaces.I_Radio;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.lz1aq.pyrig_interfaces.I_Rig.I_DecodedTransaction;
import org.lz1aq.pyrig_interfaces.I_Rig.I_EncodedTransaction;
import org.json.*;
/**
 * Class for controlling a radio through the serial interface
 * 
 * After creating the star() method must be called!
 * 
 * @author potty
 */
public class Radio
{
  private static final int QUEUE_SIZE = 30;   // Max number of commands that queueWithTransactions can hold
  
  private RadioListener           eventListener;        // TODO: make multiple event listeners
  private final SerialPort        serialPort;           // Used for writing to serialPort
  private final I_Radio           radioProtocolParser;  // Used for decoding/encoding msg from/to the radio (jython object)
  private final Thread            threadPortWriter;     // Thread that writes transaction to the serial port
  private final DynamicByteArray  receiveBuffer;        // Where bytes received through the serial port will be put
   
  private final BlockingQueue<I_EncodedTransaction>  queueWithTransactions; // Transactions waiting to be sent to the radio
  
  private static final Logger logger = Logger.getLogger(Radio.class.getName());
 
  private enum CfmType{EMPTY, POSITIVE, NEGATIVE}
  private CfmType           confirmation = CfmType.EMPTY; // Variable holding the last positive or negative confirmation from the rig
  
  /**   
   * Constructor 
   * 
   * @param protocolParser - provides the protocol for communicating with the radio
   * @param serialPort -  serial port to be used for communication
   */
  public Radio(I_Radio protocolParser, SerialPort serialPort)
  {
    radioProtocolParser   = protocolParser;           // Store the reference to the jython object
    this.serialPort       = serialPort;
    queueWithTransactions = new LinkedBlockingQueue<>(); 
    threadPortWriter      = new Thread(new PortWriter(), "threadPortWrite");    
    receiveBuffer         = new DynamicByteArray(200);  // Set the initial size to some reasonable value
  }
  
    
  
  //----------------------------------------------------------------------
  //                           Public methods
  //----------------------------------------------------------------------
  
  

  /**
   * Opens the com port (if not already open) and starts threads
   * The method must be called before the Radio object can be used.
   * 
   * @throws SerialPortException 
   */
  public void start() throws Exception
  {
    if(threadPortWriter.getState() != Thread.State.NEW )
      throw new Exception("Please create a new Radio object");
    
    if(serialPort.isOpened() == false)
      serialPort.openPort();

    threadPortWriter.start();
    
    // Register the serial port reader
    serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
    serialPort.addEventListener(new PortReader());
  }
  
  
  /**
   * The user of class "Radio" can choose between closing the ports manually
   * or calling the stop() method.
   * 
   * After an object has been stop it can not be started again by calling the
   * start() method. For this purpose a new object must be created.
   * 
   * @throws jssc.SerialPortException
   */
  public void stop() throws SerialPortException
  {
    serialPort.closePort();
    threadPortWriter.interrupt();
    
    serialPort.removeEventListener();
  }
  
  
  /**
   * Set the frequency of the radio
   * 
   * @param freq - frequency value
   * @param vfo - VFO which frequency will be changed
   * @throws Exception 
   */
  public void setFrequency(long freq, int vfo) throws Exception
  {
    this.queueTransaction(radioProtocolParser.encodeSetFreq(freq, vfo));
  }
  
  
  /**
   * Set the working mode of the radio (e.g. to CW)
   * @param mode - mode value (see I_Radio.RadioModes)
   * @param vfo - VFO which frequency will be changed
   * @throws Exception 
   */
  public void setMode(String mode, int vfo) throws Exception
  {
    this.queueTransaction(radioProtocolParser.encodeSetMode(mode, vfo));
  }
  
  
  public void addEventListener(RadioListener listener) throws Exception
  {
    if(threadPortWriter.getState() == Thread.State.NEW )
      throw new Exception("You need to call the start() method");
    
    this.eventListener = listener;
  }
  
  
  public void removeEventListener(RadioListener listener)
  { 
    this.eventListener = null;
  }
  
  
 
  //----------------------------------------------------------------------
  //                           Private stuff
  //----------------------------------------------------------------------
  class PortReader implements SerialPortEventListener
  {
    /**
     * Reads bytes from the serial port and tries to decode them. If decoding
     * is successful the decoded transaction is send to a dispatcher who is
     * responsible of notifying the interested parties.
     * 
     * @param event 
     */
    @Override
    public void serialEvent(SerialPortEvent event)
    {
      try
      {  
        // Read all there is and add it to our receive buffer
        receiveBuffer.write(serialPort.readBytes());
      } catch (Exception ex)
      {
        logger.log(Level.SEVERE, null, ex);
      }
      
      // Pass the received data to the protocol parser for decoding
      I_DecodedTransaction trans = radioProtocolParser.decode(receiveBuffer.toByteArray());
      
      if(trans.getBytesRead() > 0)
      { 
        // Let the dispatcher notify the interested parties
        dispatchEvent(trans.getTransaction());
        // Remove the processed bytes from the received buffer
        receiveBuffer.remove(trans.getBytesRead());
      }
    }
  }
  
  
  
  /**
   * Implements a Thread which is taking care of writing transactions to the
   * serial port.
   */
  class PortWriter implements Runnable
  {
    CfmType cfm;
    
    public void run()
    {
      I_EncodedTransaction trans;
      
      try
      {
        while(true)
        {
          // Get the next transaction to be send (w8 if queue is empty)
          trans = queueWithTransactions.take();
          
          // Retry - Try to send it the specified amount of times
          for(int i = 0; i < trans.getRetry(); i++)
          {
            // Write to serial port
            try
            {
              serialPort.writeBytes(trans.getTransaction());
              serialPort.purgePort(SerialPort.PURGE_RXCLEAR);
              logger.log(Level.INFO, Misc.toString(trans.getTransaction()));
            } catch (SerialPortException ex)
            {
              logger.log(Level.SEVERE, null, ex);
            }
             
            
            // Wait for confirmation from the radio (optional)
            if(trans.isConfirmationExpected())
            {
              synchronized(this)
              {
                if(confirmation == CfmType.EMPTY)
                  wait(trans.getTimeout());
                else
                  logger.log(Level.WARNING, "\"confirmation\" arrived super fast!");
                cfm = confirmation;           // read the confirmation
                confirmation = CfmType.EMPTY; // reset to empty for the next operation
              }
              
            }

            // Delay between transactions (optional)
            if(trans.getPostWriteDelay() > 0)
            {
              Thread.sleep(trans.getPostWriteDelay());
            }
            
            // Transaction sent successfully 
            if(cfm == CfmType.POSITIVE)
            {
              break; 
            }        
          }//for(retry count) 
          
        }//while(true)
      }catch(InterruptedException e)
      {
        System.out.println("PortWriter was terminated!");
      }
    }// run()
  }// class
  
  
  /**
   * 
   * @param trans
   * @throws Exception 
   */
  private void queueTransaction(I_EncodedTransaction trans) throws Exception
  {
    if(threadPortWriter.getState() == Thread.State.NEW )
      throw new Exception("You need to call the start() method");
     
    if(queueWithTransactions.size() >= QUEUE_SIZE)
    {
      throw new Exception("Max queue sized reached");
    }
    
    queueWithTransactions.offer(trans); // Insert the transaction in the queue
  }
  
  
  /**
   * 
   * @param jsonEvent 
   */
  private void dispatchEvent(String jsonEvent)
  {
    // Get the command and the data that the radio has sent us
    JSONObject jso = new JSONObject(jsonEvent);
    String command = jso.getString("command");
    String data = jso.optString("data");
    
    
    switch (command)
    {
      // -----------------------------
      case RadioEvents.CONFIRMATION:
      // -----------------------------
        // Inform portWriter that we have received confirmation for last command we have sent
        synchronized(threadPortWriter)
        { 
          if(confirmation != CfmType.EMPTY)
            logger.log(Level.WARNING, "Upon receiving of confirmation from the radio the \"confirmation\" var is not empty!");
          if(data.equals("1"))
            confirmation = CfmType.POSITIVE;
          else
            confirmation = CfmType.NEGATIVE;
          
          threadPortWriter.notify();
        }
        break;
       
      // -----------------------------
      case RadioEvents.FREQUENCY:
      // -----------------------------
        eventListener.frequency(new RadioListener.FrequencyEvent(data));
        break;
        
      // -----------------------------
      case RadioEvents.MODE:
      // -----------------------------
        eventListener.mode(new RadioListener.ModeEvent(data));
        break;  
    }
  }
  
  
  /**
   *  Currently we support the following events coming from the radio
   */
  class RadioEvents
  {
    public static final String NOT_SUPPORTED  = "not_supported";
    public static final String CONFIRMATION   = "confirmation";
    public static final String FREQUENCY      = "frequency";
    public static final String MODE           = "mode";
  }
}
