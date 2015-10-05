/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.rsi;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.lz1aq.rsi.RadioListener.FrequencyEvent;
import org.lz1aq.rsi.RadioListener.ModeEvent;
import org.lz1aq.utils.RadioModes;
import org.lz1aq.utils.RadioVfos;

/**
 *
 * @author potty
 */
public class JsonCommandParser
{
  // Currently supported commands comming from the radio
  public static final String NOT_SUPPORTED  = "not_supported";
  public static final String CONFIRMATION   = "confirmation";
  public static final String FREQUENCY      = "frequency";
  public static final String MODE           = "mode";

  
  private static final Logger     logger = Logger.getLogger(JsonCommandParser.class.getName());
  
  /**
   * Parser an object containing positive or negative Confirmation
   * 
   * @param obj - JSON object of the type:
   *    {
   *      "confirmation": "0" or "1"        <-- "0" is for negative; "1" is for positive
   *    }
   * @return "true" for positive confirmation, else "false.
   */
  public static boolean parseConfirmation(JSONObject obj)
  {
    if(obj.has("confirmation"))
    {
      return obj.getString("confirmation").equals("1");
    }
    else
    {    
      logger.log(Level.SEVERE, "JSON object didn't contained \"confirmation\" key as expected");
      return false;
    }
  }
  
  /**
   * Parser an object containing Frequency and some additional data (e.g. VFO)
   * 
   * @param obj - JSON object of the type:
   *    {
   *      "frequency": "14190000"
   *      "vfo": "0" or "1" and so on..     <--Optional - indicates which VFO changed its frequency
   *    }
   * @return Oject containing the parsed data
   */
  public static FrequencyEvent parseFrequency(JSONObject obj)
  {
    RadioVfos vfo;
    String    freq;
    
    
    if(obj.has("frequency"))
    {
      freq = obj.getString("frequency");
    }
    else
    {
      logger.log(Level.SEVERE, "JSON object didn't contained \"frequency\" key as expected");
      freq = "0";
    }
    
    if(obj.has("vfo"))
    {
      vfo= RadioVfos.values()[Integer.parseInt(obj.getString("vfo"))];
    }
    else
    {
      vfo = RadioVfos.NONE;
    }
    
    return new FrequencyEvent(freq, vfo);
  }
  
  
  /**
   * Parser an object containing Mode and some additional data (e.g. VFO)
   * 
   * @param obj - JSON object of the type:
   *    {
   *      "mode": "cw"
   *      "vfo": "0" or "1" and so on..     <--Optional - indicates which VFO changed its frequency
   *    }
   * @return Oject containing the parsed data
   */
  public static ModeEvent parseMode(JSONObject obj)
  {
    RadioModes  mode;
    RadioVfos   vfo;
 
    if(obj.has("mode"))
    {    
      mode = RadioModes.valueOf(obj.getString("mode").toUpperCase());
    }
    else
    {
      logger.log(Level.SEVERE, "JSON object didn't contained \"mode\" key as expected");
      mode = RadioModes.NONE;
    }
    
    
    if(obj.has("vfo"))
    {
      vfo= RadioVfos.values()[Integer.parseInt(obj.getString("vfo"))];
    }
    else
    {
      vfo = RadioVfos.NONE;
    }
    
    return new ModeEvent(mode, vfo);
  }
  
  
  /**
   * Parser an object containing non_supported key
   * 
   * @param obj - JSON object of the type:
   *    {
   *      "non_supported": "data that couldn't be parsed in hex format"
   *    }
   * @return "true" for positive confirmation, else "false.
   */
  public static String parseNotSupported(JSONObject obj)
  {
    if(obj.has("not_supported"))
    {
      return obj.getString("not_supported");
    }
    else
    {
      logger.log(Level.SEVERE, "JSON object didn't contained \"not_supported\" key as expected");
      return "something went wrong";
    }
  }
  
}
