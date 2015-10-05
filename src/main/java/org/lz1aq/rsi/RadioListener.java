package org.lz1aq.rsi;


import java.util.EventListener;
import org.lz1aq.utils.RadioModes;
import org.lz1aq.utils.RadioVfos;

/**
 *
 * @author potty
 */
public interface RadioListener extends EventListener
{
  /**
   * Called when the frequency of the radio changes
   * @param e - holds information about the event
   */
  public void frequency(FrequencyEvent e);

  /**
   * Called when the mode of the radio changes (e.g. to CW)
   * 
   * @param e - holds information about the event
   */
  public void mode(ModeEvent e);

  
  
  
  public static class FrequencyEvent
  {
    private final String    freq; // The new frequency
    private final RadioVfos vfo;  // Which VFO changed its frequency
    
    public FrequencyEvent(String freq, RadioVfos vfo)
    {
      this.freq = freq;
      this.vfo = vfo;
    }
    
    public String getFrequency()
    {
      return this.freq;
    }
    
    public RadioVfos getVfo()
    {
      return this.vfo;
    }
  }

  public static class ModeEvent
  {
    private final RadioModes  mode; // The new mode
    private final RadioVfos   vfo;  // Which VFO changed its frequency
    
    public ModeEvent(RadioModes mode, RadioVfos vfo)
    {
      this.mode = mode;
      this.vfo = vfo;
    }
    
    public RadioModes getMode()
    {
      return mode;
    }
    
    public RadioVfos getVfo()
    {
      return this.vfo;
    }
  }

  
}
