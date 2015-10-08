package org.lz1aq.rsi;

import java.io.IOException;
import org.lz1aq.rsi.event.*;
import java.util.ArrayList;
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
import org.lz1aq.pyrig_interfaces.*;
import org.lz1aq.rsi.event.EmptyRadioListener;
/**
 * Class for controlling a radio through the serial interface
 
 After creating the radio object the connect() method must be called!
 * 
 * @author potty
 */
public class Radio
{
  private static final int QUEUE_SIZE = 30;   // Max number of commands that queueWithTransactions can hold
  
  private boolean                   isConnected = false;  // If there is a com port open
  private ArrayList<RadioListener>  eventListeners;       // 
  private final String              serialPortName;       // 
  private       SerialPort          serialPort;           // Used for writing to serialPort
  private final I_Radio             radioProtocolParser;  // Used for decoding/encoding msg from/to the radio (jython object)
  private final Thread              threadPortWriter;     // Thread that writes transaction to the serial port
  private final DynamicByteArray    receiveBuffer;        // Where bytes received through the serial port will be put
   
  private final BlockingQueue<I_EncodedTransaction>  queueWithTransactions; // Transactions waiting to be sent to the radio
  
  private static final Logger       logger = Logger.getLogger(Radio.class.getName());
 
  
  
  private enum ConfirmationTypes{EMPTY,    // No confirmation has arrived yet
                                 POSITIVE, // Positive confirmation
                                 NEGATIVE} // Negative confirmation
  private ConfirmationTypes  confirmationStatus = ConfirmationTypes.EMPTY; // Variable holding the last confirmation that came from rig
  
  
  
  
  /**   
   * Constructor 
   * 
   * @param protocolParser - provides the protocol for communicating with the radio
   * @param portName -  name of the serial port that will be used for communicating with the radio
   */
  public Radio(I_Radio protocolParser, String portName)
  {
    radioProtocolParser   = protocolParser;           // Store the reference to the jython object
    serialPortName        = portName;
    queueWithTransactions = new LinkedBlockingQueue<>(); 
    threadPortWriter      = new Thread(new PortWriter(), "threadPortWrite");    
    receiveBuffer         = new DynamicByteArray(200);  // Set the initial size to some reasonable value
    eventListeners        = new ArrayList<>();
  }
  
  
  
    
  
  //----------------------------------------------------------------------
  //                           Public methods
  //----------------------------------------------------------------------
  
  /**
   * Establishes communication with the radio using the desired Com port
   * 
   * This must be the first method that we call before being able to use this
   * class
   * 
   * @throws SerialPortException 
   */
  public void connect() throws Exception
  {
    if(threadPortWriter.getState() != Thread.State.NEW )
      throw new Exception("Please create a new Radio object");
    
   
    // Open the serial port using the settings from the python file
    serialPort = new SerialPort(serialPortName);
    serialPort.openPort();
    setComPortParams(serialPort, radioProtocolParser.getSerialPortSettings());
    
    // Register a listener - we are interested in the confirmation events
    eventListeners.add(new LocalRadioListener());
    
    // Start the thread responsible of sending the data to the radio
    threadPortWriter.start();
    
    // Register the serial port reader which is responsible for handling the incoming data
    serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
    serialPort.addEventListener(new PortReader());
    
    isConnected = true;
  }
  
  
  /**
   * The user of class "Radio" can choose between closing the ports manually 
   * or calling the disconnects() method.
   * 
   * After an object has been disconnects it can not be started again by calling 
   * the connect() method. For this purpose a new object must be created.
   * 
   * @throws jssc.SerialPortException
   */
  public void disconnects() throws SerialPortException
  {
    threadPortWriter.interrupt();
    serialPort.removeEventListener();
    serialPort.closePort();
    
    isConnected = false;
  }
  
  
  /**
   * Set the frequency of the radio
   * 
   * @param freq - frequency value
   * @throws Exception 
   */
  public void setFrequency(long freq) throws Exception
  {
    if(isConnected==false) 
      throw new Exception("Not connected to radio!");
    
    this.queueTransaction(radioProtocolParser.encodeSetFreq(freq));
  }
  
  /**
   * Set the VFO frequency of the radio
   * 
   * @param freq - frequency value
   * @param vfo - VFO which frequency will be changed
   * @throws Exception 
   */
  public void setFrequency(long freq, int vfo) throws Exception
  {
    if(isConnected==false) 
      throw new Exception("Not connected to radio!");
    
    this.queueTransaction(radioProtocolParser.encodeSetVfoFreq(freq, vfo));
  }
  
  /**
   * Asks the radio to send us the current frequency.
   * 
   * If we would like to get the frequency event when it comes we have to
   * register an EventListener
   * 
   * @throws Exception 
   */
  public void getFrequency() throws Exception
  {
    if(isConnected==false) 
      throw new Exception("Not connected to radio!");
    
    this.queueTransaction(radioProtocolParser.encodeGetFreq());
  }
  
  /**
   * Asks the radio to send us the current VFO frequency.
   * 
   * If we would like to get the frequency event when it comes we have to
   * register an EventListener
   * 
   * @param vfo - for which VFO we would like to get the frequency
   * @throws Exception 
   */
  public void getFrequency(int vfo) throws Exception
  {
    if(isConnected==false) 
      throw new Exception("Not connected to radio!");
    
    this.queueTransaction(radioProtocolParser.encodeGetVfoFreq(vfo));
  }
  
  
  /**
   * Set the working mode of the radio (e.g. to CW)
   * 
   * @param mode - mode value (see I_Radio.RadioModes)
   * @throws Exception 
   */
  public void setMode(String mode) throws Exception
  {
   if(isConnected==false) 
      throw new Exception("Not connected to radio!");
    
    this.queueTransaction(radioProtocolParser.encodeSetMode(mode));
  }
  
  /**
   * Set the working mode of the radio (e.g. to CW)
   * @param mode - mode value (see I_Radio.RadioModes)
   * @param vfo - VFO which mode will be changed
   * @throws Exception 
   */
  public void setMode(String mode, int vfo) throws Exception
  {
   if(isConnected==false) 
      throw new Exception("Not connected to radio!");
    
    this.queueTransaction(radioProtocolParser.encodeSetVfoMode(mode, vfo));
  }
  
  
   /**
   * Get the working mode of the radio
   * If we would like to get the mode event when it comes from the radio we 
   * have to register an EventListener
   * 
   * @throws Exception 
   */
  public void getMode() throws Exception
  {
   if(isConnected==false) 
      throw new Exception("Not connected to radio!");
    
    this.queueTransaction(radioProtocolParser.encodeGetMode());
  }
  
  /**
   * Get the VFO mode of the radio
   * If we would like to get the mode event when it comes from the radio we 
   * have to register an EventListener
   * 
   * @param vfo - VFO of which we want to read the mode
   * @throws Exception 
   */
  public void getMode(int vfo) throws Exception
  {
   if(isConnected==false) 
      throw new Exception("Not connected to radio!");
    
    this.queueTransaction(radioProtocolParser.encodeGetVfoMode(vfo));
  }
  
  
  public void addEventListener(RadioListener listener) throws Exception
  {
    this.eventListeners.add(listener);
  }
  
  
  public void removeEventListener(RadioListener listener)
  { 
    this.eventListeners.remove(listener);
  }
  
  
 
  //----------------------------------------------------------------------
  //                           Private stuff
  //----------------------------------------------------------------------
  private class PortReader implements SerialPortEventListener
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
      } catch (SerialPortException | IOException ex)
      {
        logger.log(Level.WARNING, ex.toString(), ex);
      }
      
      // Pass the received data to the protocol parser for decoding
      I_DecodedTransaction trans = radioProtocolParser.decode(receiveBuffer.toByteArray());
      
      if(trans.getBytesRead() > 0)
      { 
        // This will parseAndNotify the JSON string and notify all the interested parties
        JsonCommandParser.parseAndNotify(trans.getTransaction(), eventListeners);
        // Remove the processed bytes from the received buffer
        receiveBuffer.remove(trans.getBytesRead());
      }
    }
  }
  
  
  
  /**
   * Implements a Thread which is taking care of writing transactions to the
   * serial port.
   */
  private class PortWriter implements Runnable
  {
    ConfirmationTypes cfm;
    
    @Override
    public void run()
    {
      I_EncodedTransaction trans;
      
      try
      {
        while(true)
        {
          // Get the next transaction to be send (waits if the queue is empty)
          trans = queueWithTransactions.take();
          
          // Retry - Try to send it the specified amount of times
          for(int i = 0; i < trans.getRetry(); i++)
          {
            // Write to serial port
            try
            {
              serialPort.writeBytes(trans.getTransaction());
              serialPort.purgePort(SerialPort.PURGE_TXCLEAR);
              logger.log(Level.INFO, Misc.toString(trans.getTransaction()));
            } catch (SerialPortException ex)
            {
              logger.log(Level.SEVERE, null, ex);
            }
                        
            // Wait for confirmation from the radio (optional)
            if(trans.isConfirmationExpected())
            {
              waitForConfirmation(trans);
            }

            // Delay between transactions (optional)
            if(trans.getPostWriteDelay() > 0)
            {
              Thread.sleep(trans.getPostWriteDelay());
            }
            
            // Transaction sent successfully 
            if(cfm == ConfirmationTypes.POSITIVE)
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

    /**
     * Blocks the thread until the confirmation flag is updated or until the
     * timeout specified in trans expires.
     * 
     * @param trans - holds the timeout value for the confirmation
     * @throws InterruptedException 
     */
    private void waitForConfirmation(I_EncodedTransaction trans) throws InterruptedException
    {
      synchronized(this)
      {
        if(confirmationStatus == ConfirmationTypes.EMPTY)
          wait(trans.getTimeout());
        else
          logger.log(Level.WARNING, "\"confirmation\" arrived super fast!");
        cfm = confirmationStatus;           // read the confirmation
        confirmationStatus = ConfirmationTypes.EMPTY; // reset to empty for the next operation
      }
    }
  }// class
  
  
  
  private class LocalRadioListener extends EmptyRadioListener
  {
    @Override
    public void confirmationEvent(ConfirmationEvent e)
    {
      updateConfirmation(e.getConfirmation());
    }
    
    @Override
    public void notsupportedEvent(NotsupportedEvent e)
    {
      logger.log(Level.WARNING, "The following transaction couldn't be decoded: " + e.getData());
    }
  }
          
            
  /**
   * Inserts a transaction into the queueWithTransactions
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
   *  Stores the confirmation received from the radio and notifies the threadPortWriter
   * 
   * @param cfm - Confirmation type (i.e. positive or negative)
   */
  private void updateConfirmation(boolean cfm)
  {
    // Inform portWriter that we have received confirmation for last command we have sent
    synchronized(threadPortWriter)
    {
      if(confirmationStatus != ConfirmationTypes.EMPTY)
        logger.log(Level.WARNING, "Upon receiving of confirmation from the radio the \"confirmation\" var is not empty!");
      if(cfm)
        confirmationStatus = ConfirmationTypes.POSITIVE;
      else
        confirmationStatus = ConfirmationTypes.NEGATIVE;
      
      threadPortWriter.notify();
    }
  }
  
   /**
   * Sets Com port parameters
   * 
   * @param port The serial port which parameters will be adjusted
   * @param settings Source from which the values will be taken
   */
  private void setComPortParams(SerialPort port, I_SerialSettings settings) throws SerialPortException
  {
    int parity = SerialPort.PARITY_NONE;
    
    switch(settings.getParity().toLowerCase())
    {
      case "none":
        parity = SerialPort.PARITY_NONE;
        break;

      case "odd":
        parity = SerialPort.PARITY_ODD;
        break;

      case "even":
        parity = SerialPort.PARITY_EVEN;
        break;

      case "mark":
        parity = SerialPort.PARITY_MARK;
        break;

      case "space":
        parity = SerialPort.PARITY_SPACE;
        break;
    }
    
    port.setParams(settings.getBauderateMax(),
                   settings.getDataBits(),
                   settings.getStopBits(),
                   parity);
    
    switch (settings.getDtr().toLowerCase())
    {
      case "none":
        break;
      case "on":
        port.setDTR(true);
        break;
      case "off":
        port.setDTR(false);
        break;
    }

    switch (settings.getRts().toLowerCase())
    {
      case "none":
        break;
      case "on":
        port.setRTS(true);
        break;
      case "off":
        port.setRTS(false);
        break;
    }
  }
  
}
