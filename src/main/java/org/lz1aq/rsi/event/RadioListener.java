package org.lz1aq.rsi.event;


import java.util.EventListener;
import org.lz1aq.rsi.event.*;

/**
 *
 * @author potty
 */
public interface RadioListener extends EventListener
{
  /**
   * Called when the radio sends us data that we couldn't decode
   * 
   * @param e - holds information about the event
   */
  public void notsupportedEvent(NotsupportedEvent e);
  
  /**
   * Called when the radio sends us positive or negative confirmation
   * 
   * @param e - holds information about the event
   */
  public void confirmationEvent(ConfirmationEvent e);
  
  /**
   * Called when the radio sends us the Frequency
   * 
   * @param e - holds information about the event
   */
  public void frequencyEvent(FrequencyEvent e);

  /**
   * Called when the radio sends us the Mode (e.g. to CW)
   * 
   * @param e - holds information about the event
   */
  public void modeEvent(ModeEvent e);

}
