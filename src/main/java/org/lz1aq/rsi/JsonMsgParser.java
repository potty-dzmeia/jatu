/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.rsi;

import org.lz1aq.rsi.event.RadioListener;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
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
  
  // A field wich can be found inside the FREQUENCY_MSG and MODE_MSG
  public static final String VFO_PAR            = "vfo";

  
  private static final Logger logger = Logger.getLogger(JsonMsgParser.class.getName());
  
  
  /**
   * Parses the JSON formatted message and notifies the listeners
   * 
   * @param jsoString - JSON formatted string of the type: {"some_command_name": { ...data...}}
   * @param listeners - Listeners which will be notified for the content of the JSON message
   */
  public static void parseAndNotify(String jsoString, CopyOnWriteArrayList<RadioListener> listeners)
  {
    
     // Get the command (i.e. the name of the object) that the radio has sent us
    JSONObject jso = new JSONObject(jsoString);
    Iterator<?> keys = jso.keys();
    
    if(keys.hasNext()==false)
    {
      logger.log(Level.SEVERE, "dispatchEvent(): We received an empty decoded transaction");
      return;
    }
    
    // We can have several messages inside the JSON block...
    while(keys.hasNext())
    {
      String command = (String)keys.next();
      
      switch (command)
      {
        // -----------------------------
        case JsonMsgParser.CONFIRMATION_MSG:
          ConfirmationEvent cfmEv = parseConfirmationMsg(jso);
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
          NotsupportedEvent unsupportedEv = parseNotsupportedMsg(jso);
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
    }// for each message
    
  }
  
 
  /**
   * Parses an object containing data that couldn't be decoded by
   * @param jso - JSON object of the type: {"not-supported":"the data that couldn't be decoded in hex format"}
   * @return event holding the parsed data
   */
  private static NotsupportedEvent parseNotsupportedMsg(JSONObject jso)
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
   * @return event holding the parsed data
   */
  private static ConfirmationEvent parseConfirmationMsg(JSONObject jso)
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
   * @return event holding the parsed data
   */
  private static FrequencyEvent parseFrequencyMsg(JSONObject jso)
  {
    String  freq = "0";

    if(jso.has(FREQUENCY_MSG))
    {
      freq = jso.getString(FREQUENCY_MSG);
    }
    else
    {
      logger.log(Level.SEVERE, "JSON object didn't contained " + FREQUENCY_MSG + " key as expected");
    }
    
    return new FrequencyEvent(freq, parseVfoField(jso));
  }
  
  
  /**
   * Parser an object containing Mode and some additional data (e.g. VFO)
   * 
   * @param jso - JSON object of the type:
   *    {
   *      "mode": "cw"
   *      "vfo": "0" or "1" and so on..     <--Optional - indicates which VFO changed its frequency
   *    }
   * @return event holding the parsed data
   */
  private static ModeEvent parseModeMsg(JSONObject jso)
  {
    RadioModes  mode = RadioModes.NONE;
    
    if(jso.has(MODE_MSG))
    {    
      mode = RadioModes.valueOf(jso.getString(MODE_MSG).toUpperCase());
    }
    else
    {
      logger.severe("JSON object didn't contained " + MODE_MSG + " key as expected");
    }
    
    return new ModeEvent(mode, parseVfoField(jso));
  }

  
  /**
   *  Extracts the string from the "VFO" field
   * 
   * @param jso - JSON object containing that we will try to extract the field from
   * @return RadioVfos object specifying the VFO. RadioVfos.NONE in case the 
   *         field was missing or was set to None
   */
  private static RadioVfos parseVfoField(JSONObject jso)
  {
    if (jso.has(VFO_PAR))
    {
      int i = Integer.parseInt(jso.getString(VFO_PAR));
      for(RadioVfos vfo: RadioVfos.values())
      {
        if(vfo.getCode() == i)
          return vfo;
      }
    } 
    
    logger.warning("SON object didn't contained " + VFO_PAR +" key.");  
    return RadioVfos.NONE;
  }
  
}
