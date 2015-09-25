package org.lz1aq.rsi;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.lz1aq.pyrig_interfaces.I_Radio;
import java.util.TimerTask;

/**
 * Class for controlling a radio through the serial interface
 * 
 * @author potty
 */
public class Radio
{
  private static final int QUEUE_SIZE = 30; // Max number of commands that queueWithCommands can hold
  
  private I_Radio radioProtocolParser;    // Used for decoding/encoding msg from/to the radio
  private RadioListener radioListener;    // TODO: make multiple event listeners
  private Queue<RawCommand>  queueWithCommands; // Commands waiting to be sent to the radio
  private Timer timer;                    // Used for sending commands (retries, delays etc.)
  
  
  // This class implements a State Machine for sending commands to the radio.
  // The variables below control this state machine:
  // -----------------------------------------------------------------------
  private CommStates commState =  CommStates.IDLE; // The current state of the communication with the radio
  private int retryCount = 0; // Number of times we tried to send a given command to the radio

  /**
   * Used for the state machine of this component
   */
  private enum CommStates
  {
   IDLE,        // No command is being send at the moment
   SENT,        // A command has been sent and we w8 for positive confirmation
   FAILED,      // A negative confirmation has been received
   TIMEOUT,     // A timeout has expired while w8ing for positive confirmation
   CFM,         // Positive confirmation has arrived
   DELAY,       // We are waiting before sending the next command
  }
  
  private enum StateMachineEvents
  {
    NEW_COMMAND_INSERTED,
    TIMER_EXPIRED,
  }
  
  //----------------------------------------------------------------------
  //                           Public methods
  //----------------------------------------------------------------------
  
  
  /**  
   * 
   * @param protocolParser - provides the protocol for communicating with the radio
   */
  public Radio(I_Radio protocolParser)
  {
    radioProtocolParser = protocolParser;
    queueWithCommands = new LinkedList<RawCommand>(); 
    timer = new Timer();
  }
  
  
  public void open()
  {
    
  }
  
  
  public void close()
  {
    
  }
  
  
  public void setFrequency(long freq)
  {
    RawCommand command = new RawCommand(radioProtocolParser.encodeSetFreq(freq, 1));
    this.send(command);
  }
  
  
  public void setMode(String mode)
  {
    
  }
  
  
  public void addEventListener(RadioListener listener)
  {
    this.radioListener = listener;
  }
  
  
  public void removeEventListener(RadioListener listener)
  { 
    this.radioListener = null;
  }
  
  
  
  
  //----------------------------------------------------------------------
  //                           Private methods
  //----------------------------------------------------------------------

  private synchronized void send(RawCommand cmd) throws Exception
  {
    if(queueWithCommands.size() >= QUEUE_SIZE)
    {
      throw new Exception("Max queue sized reached");
    }
    
    queueWithCommands.offer(cmd);     // Queue the command
    startTimer(radioProtocolParser.getSerialPortSettings().getTimeout());  // Start the timeout timer
  }
 
  
  private synchronized void timerExpired()
  {
    
  }
  
  private synchronized void startTimer(long milliSeconds)
  {
    timer.schedule(new TimerTask(){public void run() {timerExpired();}}, // When timer expires timerExpired() is called
                   milliSeconds);
  }
  

  /** Writes the command to the serial comm port
   * 
   * @param cmd - bytes to be written
   */
  private synchronized boolean writeCommand(byte[] cmd)
  {
    
  }
  
 
  
  
  
  
  protected class RawCommand
  {
    byte[] command;

    public RawCommand(byte[] command)
    {
      this.command = command;
    }
    
    public byte[] get()
    {
      return command;
    }
  }
  
}
