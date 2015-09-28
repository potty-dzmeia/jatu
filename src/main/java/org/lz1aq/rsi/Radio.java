package org.lz1aq.rsi;

import com.sun.corba.se.impl.util.Utility;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import org.lz1aq.pyrig_interfaces.I_Radio;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.lz1aq.pyrig_interfaces.I_Rig.I_EncodedTransaction;

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
  
  private SerialPort      serialPort;           // Used for writing to serialPort
  private I_Radio         radioProtocolParser;  // Used for decoding/encoding msg from/to the radio (jython object)
  private RadioListener   eventListener;        // TODO: make multiple event listeners
  private BlockingQueue<I_EncodedTransaction>  queueWithTransactions; // Transactions waiting to be sent to the radio
  private Thread          threadPortWrite;      // Thread that writes transaction to the serial port
  private Thread          threadPortReader;     // Thread that receives transaction from the serial port
  private static final Logger logger = Logger.getLogger(Radio.class.getName());
  private ArrayList<Byte> receiveBuffer;
  
  
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
    queueWithTransactions = new LinkedBlockingQueue<I_EncodedTransaction>(); 
    threadPortWrite       = new Thread(new PortWriter(), "threadPortWrite");    
    receiveBuffer         = new ArrayList<Byte>();
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
    if(threadPortWrite.getState() != Thread.State.NEW )
      throw new Exception("Please create a new Radio object");
    
    if(serialPort.isOpened() == false)
      serialPort.openPort();

    threadPortWrite.start();
    
    // Register the serial port reader
    serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
    serialPort.addEventListener(new SerialPortReader());
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
    threadPortWrite.interrupt();
    
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
    if(threadPortWrite.getState() == Thread.State.NEW )
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
  
  private class SerialPortReader implements SerialPortEventListener
  {

    public void serialEvent(SerialPortEvent event)
    {
      try
      {
        byte buffer[] = serialPort.readBytes(10);
      } catch (SerialPortException ex)
      {
        System.out.println(ex);
      }
    }
  }
  
  
  
  /**
   * Implements a Thread which is taking care of writing transactions to the
   * serial port.
   */
  private class PortWriter implements Runnable
  {
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
              logger.log(Level.INFO, Utils.toString(trans.getTransaction()));
            } catch (SerialPortException ex)
            {
              logger.log(Level.SEVERE, null, ex);
            }
                      
//            if(trans.isConfirmationExpected())
//            {
                // w8 for notify() which tells us there is a response or for the timeout
//              wait(trans.getTimeout()); 
//              confirmation = getConfirmation();
//            }

            // Delay - if wanted introduce delay between transactions
            if(trans.getPostWriteDelay() > 0)
            {
              Thread.sleep(trans.getPostWriteDelay());
            }
            
            break;
            
//            // Transaction sent successfully 
//            if(confirmation == positive)
//            {
//              break; 
//            }        
          }//for   
          
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
    if(threadPortWrite.getState() == Thread.State.NEW )
      throw new Exception("You need to call the start() method");
     
    if(queueWithTransactions.size() >= QUEUE_SIZE)
    {
      throw new Exception("Max queue sized reached");
    }
    
    queueWithTransactions.offer(trans); // Insert the transaction in the queue
  }
  
}
