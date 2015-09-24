/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.rsi;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;

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
    private final long frequency;
    public FrequencyEvent(long freq)
    {
      frequency = freq;
    }
    
    public long getFrequency()
    {
      return frequency;
    }
  }

  public static class ModeEvent
  {
    private final String mode;
    public ModeEvent(String md)
    {
      mode = md;
    }
    
    public String getMode()
    {
      return mode;
    }
  }

  
}
