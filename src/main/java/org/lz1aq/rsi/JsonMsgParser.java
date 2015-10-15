/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.rsi;

import org.lz1aq.rsi.event.RadioListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.lz1aq.rsi.event.*;
import org.lz1aq.utils.RadioModes;
import org.lz1aq.utils.RadioVfos;

/**
 *
 * @author potty
 */
public class JsonMsgParser
{
  // Currently supported JSON messages comming from the radio
  public static final String NOT_SUPPORTED_MSG  = "not_supported";
  public static final String CONFIRMATION_MSG   = "confirmation";
  public static final String FREQUENCY_MSG      = "frequency";
  public static final String MODE_MSG           = "mode";

  
  private static final Logger logger = Logger.getLogger(JsonMsgParser.class.getName());
  
  
  /**
   * Parses the JSON formatted message and notifies the listeners
   * 
   * @param jsoString - JSON formatted string of the type: {"some_command_name": { ...data...}}
   * @param listeners - Listeners which will be notified for the content of the JSON message
   */
  public static void parseAndNotify(String jsoString, ArrayList<RadioListener> listeners)
  {
    
     // Get the command (i.e. the name of the object) that the radio has sent us
    JSONObject jso = new JSONObject(jsoString);
    Iterator<?> keys = jso.keys();
    
    if(keys.hasNext()==false)
    {
      logger.log(Level.SEVERE, "dispatchEvent(): We received an empty decoded transaction");
      return;
    }
    
    // We can have several commands inside the JSON block...
    while(keys.hasNext())
    {
      String command = (String)keys.next();
      
      switch (command)
      {
        // -----------------------------
        case JsonMsgParser.CONFIRMATION_MSG:
          ConfirmationEvent cfmEv = parseConfirmationMsg(jso.getJSONObject(command));
          for (RadioListener listener : listeners)
          {
            listener.confirmationEvent(cfmEv);
          }
          break;

        // -----------------------------
        case JsonMsgParser.FREQUENCY_MSG:
          // -----------------------------
          FrequencyEvent freqEv = parseFrequencyMsg(jso.getJSONObject(command));
          for (RadioListener listener : listeners)
          {
            listener.frequencyEvent(freqEv);
          }
          break;

        // -----------------------------
        case JsonMsgParser.MODE_MSG:
          // -----------------------------
          ModeEvent modeEv = parseModeMsg(jso.getJSONObject(command));
          for (RadioListener listener : listeners)
          {
            listener.modeEvent(modeEv);
          }
          break;

        // -----------------------------
        case JsonMsgParser.NOT_SUPPORTED_MSG:
          // -----------------------------
          NotsupportedEvent unsupportedEv = parseNotsupportedMsg(jso.getJSONObject(command));
          for (RadioListener listener : listeners)
          {
            listener.notsupportedEvent(unsupportedEv);
          }
          break;

        // -----------------------------
        default:
          // -----------------------------
          logger.log(Level.SEVERE, "JSON message contained an unknown command: " + command);
          break;
      }
    }
    
    
    
  }
  
 
  /**
   * Parses an object containing data that couldn't be decoded by
   * @param jso - JSON object of the type: {"not-supported":"the data that couldn't be decoded in hex format"}
   * @return 
   */
  public static NotsupportedEvent parseNotsupportedMsg(JSONObject jso)
  {
    if(jso.has(NOT_SUPPORTED_MSG))
    {
      return new NotsupportedEvent(jso.getString(NOT_SUPPORTED_MSG));
    }
    else
    {    
      logger.log(Level.SEVERE, "JSON object didn't contain " + NOT_SUPPORTED_MSG + " key as expected");
      return new NotsupportedEvent("");
    }
  }
  /**
   * Parser an object containing positive or negative Confirmation
   * 
   * @param jso - JSON object of the type: {"confirmation": "0" or "1"}     <-- "0" is for negative; "1" is for positive
   * @return Object colding the confirmation data
   */
  public static ConfirmationEvent parseConfirmationMsg(JSONObject jso)
  {
    if(jso.has(CONFIRMATION_MSG))
    {
      return new ConfirmationEvent(jso.getString(CONFIRMATION_MSG).equals("1"));
    }
    else
    {    
      logger.log(Level.SEVERE, "JSON object didn't contain " + CONFIRMATION_MSG + " key as expected");
      return new ConfirmationEvent(false);
    }
  }
  
  /**
   * Parser an object containing Frequency and some additional data (e.g. VFO)
   * 
   * @param jso - JSON object of the type:
   *    {
   *      "frequency": "14190000"
   *      "vfo": "0" or "1" and so on..     <--Optional - indicates which VFO changed its frequency
   *    }
   * @return Oject containing the parsed data
   */
  public static FrequencyEvent parseFrequencyMsg(JSONObject jso)
  {
    RadioVfos vfo;
    String    freq;
    
    
    if(jso.has(FREQUENCY_MSG))
    {
      freq = jso.getString(FREQUENCY_MSG);
    }
    else
    {
      logger.log(Level.SEVERE, "JSON object didn't contained " + FREQUENCY_MSG + " key as expected");
      freq = "0";
    }
    
    if(jso.has("vfo"))
    {
      vfo= RadioVfos.values()[Integer.parseInt(jso.getString("vfo"))];
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
   * @param jso - JSON object of the type:
   *    {
   *      "mode": "cw"
   *      "vfo": "0" or "1" and so on..     <--Optional - indicates which VFO changed its frequency
   *    }
   * @return Oject containing the parsed data
   */
  public static ModeEvent parseModeMsg(JSONObject jso)
  {
    RadioModes  mode;
    RadioVfos   vfo;
 
    if(jso.has(MODE_MSG))
    {    
      mode = RadioModes.valueOf(jso.getString(MODE_MSG).toUpperCase());
    }
    else
    {
      logger.log(Level.SEVERE, "JSON object didn't contained " + MODE_MSG + " key as expected");
      mode = RadioModes.NONE;
    }
    
    
    if(jso.has("vfo"))
    {
      vfo= RadioVfos.values()[Integer.parseInt(jso.getString("vfo"))];
    }
    else
    {
      vfo = RadioVfos.NONE;
    }
    
    return new ModeEvent(mode, vfo);
  }

}
