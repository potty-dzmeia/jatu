package org.lz1aq.rsi;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.lz1aq.pyrig_interfaces.I_Radio;


/**
 * Class for controlling a radio through the serial interface
 * 
 * @author potty
 */
public class Radio
{
  private I_Radio radioProtocolParser;    // Used for decoding/encoding msg from/to the radio
  private RadioListener radioListener;    // TODO: make multiple event listeners
  private LinkedBlockingQueue<RawCommand>  queueWithCommands; // Commands waiting to be sent to the radio
  
  /**
   * 
   * @param protocolParser - object implementing the I_Radio interface.
   */
  public Radio(I_Radio protocolParser)
  {
    radioProtocolParser = protocolParser;
    queueWithCommands = new LinkedBlockingQueue<RawCommand>(30);
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
    this.queueCommand(command);
    
  }
  
  public void setMode(String mode)
  {
    
  }
  
  public void addEventListener(RadioListener listener)
  {
      this.radioListener = listener;
  }
       
  
  private void queueCommand(RawCommand cmd)
  {
    queueWithCommands.offer(cmd);
    
    if(queueWithCommands.size()==1)
    {
      // If this is the first command in the queue we need to restart the timer
      restartTimer;
    }
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
