// ***************************************************************************
// *   Copyright (C) 2015 by Chavdar Levkov                              
// *   ch.levkov@gmail.com                                                   
// *                                                                         
// *   This program is free software; you can redistribute it and/or modify  
// *   it under the terms of the GNU General Public License as published by  
// *   the Free Software Foundation; either version 2 of the License, or     
// *   (at your option) any later version.                                   
// *                                                                         
// *   This program is distributed in the hope that it will be useful,       
// *   but WITHOUT ANY WARRANTY; without even the implied warranty of        
// *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         
// *   GNU General Public License for more details.                          
// *                                                                         
// *   You should have received a copy of the GNU General Public License     
// *   along with this program; if not, write to the                         
// *   Free Software Foundation, Inc.,                                       
// *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             
// ***************************************************************************
package org.lz1aq.log;

import java.util.ArrayList;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.lz1aq.utils.TimeUtils;

/**
 * The Qso class is describing a contact between two radio station by using QsoParameters (e.g.
 * "date", "time", "frequency" and so on ...)
 */
public class Qso
{

  /**
   * Used for accessing Date parameter - the Date parameter has the format yyyy-mm-dd
   */
  static private final int DATE_INDEX = 0;
  static private final String DATE_TXT = "date";

  /**
   * Used for accessing Time parameter. The Time parameter has the format hhmmss (24hour format)
   */
  static private final int TIME_INDEX = 1;
  static private final String TIME_TXT = "time";

  /**
   * Used for accessing Frequency parameter - The Freq param is in Hz
   */
  static private final int FREQ_INDEX = 2;
  static private final String FREQ_TXT = "freq";

  /**
   * Used for accessing Mode parameter - see class RadioModes for the expected values
   */
  static private final int MODE_INDEX = 3;
  static private final String MODE_TXT = "mode";

  /**
   * Used for accessing MyCall parameter - the callsign of the user of this program
   */
  static private final int MYCALL_INDEX = 4;
  static private final String MYCALL_TXT = "myCall";

  /**
   * Used for accessing Time parameter - the callsign of the correspondent
   */
  static private final int HISCALL_INDEX = 5;
  static private final String HISCALL_TXT = "hisCall";

  /**
   * Used for accessing Snt parameter
   */
  static private final int SNT_INDEX = 6;
  static private final String SNT_TXT = "snt";

  /**
   * Used for accessing Rcv parameter
   */
  static private final int RCV_INDEX = 7;
  static private final String RCV_TXT = "rcv";

  /**
   * Used for accessing Extra parameters
   */
  static private final int FIRST_EXTRA_PARAM_INDEX = 8;

  /**
   * The minimum valid length for a callsign
   */
  static private final int CALLSIGN_MIN_LEN = 4;
  
  
  /**
   * Holds all the QSO parameters (such as "date", "time", "mode" ....)
   */
  private final ArrayList<QsoParameter> qsoParams;
  private DateTime utc;
  
  
  
  
  public Qso(long freq, String mode, String myCall, String hisCall)
  {  
    this.utc = TimeUtils.getUTC();
    
    qsoParams = new ArrayList<>();
    qsoParams.add(new QsoParameter(DATE_TXT, TimeUtils.toQsoDate(utc)));
    qsoParams.add(new QsoParameter(TIME_TXT, TimeUtils.toQsoTime(utc)));
    qsoParams.add(new QsoParameter(FREQ_TXT, Long.toString(freq)));
    qsoParams.add(new QsoParameter(MODE_TXT, mode));
    qsoParams.add(new QsoParameter(MYCALL_TXT, myCall));
    qsoParams.add(new QsoParameter(HISCALL_TXT, hisCall));
  }

  
  public Qso(long freq, String mode, String myCall, String hisCall, ArrayList<QsoParameter> extraQsoParams)
  {
    this.utc = TimeUtils.getUTC();
    
    qsoParams = new ArrayList<>();
    qsoParams.add(new QsoParameter(DATE_TXT, TimeUtils.toQsoDate(utc)));
    qsoParams.add(new QsoParameter(TIME_TXT, TimeUtils.toQsoTime(utc)));
    qsoParams.add(new QsoParameter(FREQ_TXT, Long.toString(freq)));
    qsoParams.add(new QsoParameter(MODE_TXT, mode));
    qsoParams.add(new QsoParameter(MYCALL_TXT, myCall));
    qsoParams.add(new QsoParameter(HISCALL_TXT, hisCall));
  }

  
  /**
   * Adding QSO for the LZ HF QRP contest
   *
   * @param freq
   * @param mode
   * @param myCall
   * @param hisCall
   * @param snt
   * @param rcv
   * @throws java.lang.Exception In case the input data contains invalid fields
   */
  public Qso(long freq, String mode, String myCall, String hisCall, String snt, String rcv) throws Exception
  {
    try
    {
      isValidEntry(freq, mode, myCall, hisCall, snt, rcv);
    }catch(Exception exc)
    {
      throw exc;
    }
              
            
    this.utc = TimeUtils.getUTC();
    
    qsoParams = new ArrayList<>();
    qsoParams.add(new QsoParameter(DATE_TXT, TimeUtils.toQsoDate(utc)));
    qsoParams.add(new QsoParameter(TIME_TXT, TimeUtils.toQsoTime(utc)));
    qsoParams.add(new QsoParameter(FREQ_TXT, Long.toString(freq)));
    qsoParams.add(new QsoParameter(MODE_TXT, mode));
    qsoParams.add(new QsoParameter(MYCALL_TXT, myCall));
    qsoParams.add(new QsoParameter(HISCALL_TXT, hisCall));
    qsoParams.add(new QsoParameter(SNT_TXT, snt));
    qsoParams.add(new QsoParameter(RCV_TXT, rcv));

  }

  
  @Override
  public String toString()
  {
    StringBuilder str = new StringBuilder(60);

    for (QsoParameter par : qsoParams)
    {
      str.append(' ');
      str.append(par.value);
    }

    return str.toString();
  }
  

  /**
   * @return The count of the total Qso parameters
   */
  public int getParamsCount()
  {
    return qsoParams.size();
  }

  
  /**
   * @return The count of the additional Qso parameters
   */
//  public int getExtraParamsCount()
//  {
//    return qsoParams.size()-FIRST_EXTRA_PARAM_INDEX;
//  }
  /**
   * @return Date in yyyy-mm-dd format
   */
  public String getDate()
  {
    return qsoParams.get(DATE_INDEX).value;
  }

  
  /**
   * @return Date in hhmm 24hour format
   */
  public String getTime()
  {
    return qsoParams.get(TIME_INDEX).value;
  }

  
  /**
   * @return Frequency on Hz
   */
  public String getFrequency()
  {
    return qsoParams.get(FREQ_INDEX).value;
  }

  
  /**
   * @return see class RadioModes for valid strings
   */
  public String getMode()
  {
    return qsoParams.get(MODE_INDEX).value;
  }

  
  public String getMyCallsign()
  {
    return qsoParams.get(MYCALL_INDEX).value;
  }

  
  public String getHisCallsign()
  {
    return qsoParams.get(HISCALL_INDEX).value;
  }

  
  /**
   * Gets the "Snt" parameter of the Qso
   * @return Snt is the data that we have sent to the other station
   */
  public String getSnt()
  {
    return qsoParams.get(SNT_INDEX).value;
  }
  
  
  /**
   * Gets the "Rcv" parameter of the Qso
   * @return 
   */
  public String getRcv()
  {
    return qsoParams.get(RCV_INDEX).value;
  }
  
  
  public String getParamName(int parameterIndex)
  {
    return qsoParams.get(parameterIndex).name;
  }

  public String getParamValue(int parameterIndex)
  {
    return qsoParams.get(parameterIndex).value;
  }

  public String setParamValue(int parameterIndex, String value)
  {
    return qsoParams.get(parameterIndex).value = value;
  }

  /**
   * For accessing qso parameters.
   *
   * Use the constants defined within this class to access desired params. E.g. Use DATE_INDEX to
   * access the "date" parameter. To access extra parameters use the FIRST_EXTRA_PARAM_INDEX for the
   * first one - Following extra parameters are obtained by incrementing FIRST_EXTRA_PARAM_INDEX.
   *
   * @param parameterIndex The index of the param (0 being the first one). Use the public static
   * variables defined in this class for accessing the standard params (e.g. DATE_INDEX).
   * @return Object describing the extra parameter
   */
  public QsoParameter getParam(int parameterIndex)
  {
    return qsoParams.get(parameterIndex);
  }

  
  
  public long getElapsedSeconds()
  { 
    return (TimeUtils.getUTC().getMillis()-utc.getMillis())/1000;
  }
  
//  /**
//   * Get the time of the QSO (time zone agnostic)
//   * 
//   * @return 
//   */
//  public LocalDateTime getQsoDateTime()
//  {
//    String yyyy= this.getDate().substring(0,3); // yyyy-mm-dd
//    String mm = this.getDate().substring(5, 6);
//    String dd = this.getDate().substring(8, 9);
//    
//    String hh = this.getTime()
//    new Loca this.getDate().substring(0, 3)
//  }
  
  /**
   * Throws exception if the input is not valid
   * 
   * @param freq
   * @param mode
   * @param myCall
   * @param hisCall
   * @param snt
   * @param rcv
   * @return
   * @throws Exception exception describing the issue
   */
  private void isValidEntry(long freq, String mode, String myCall, String hisCall, String snt, String rcv) throws Exception
  {
    if(!isValidFrequency(freq))
      throw new Exception("Invalid frequency");
    
    if(!isValidCallsign(myCall))
      throw new Exception("Your callsign is invalid");
    
    if(!isValidCallsign(myCall))
      throw new Exception("His callsign is invalid");
    
    if(!isValidSerial(snt))
      throw new Exception("Snt field is invalid");
    
    if(!isValidSerial(rcv))
      throw new Exception("Rcv field is invalid");
  }

  
  
  /**
   * Frequency validator. Should be between 28000000 and 3500000
   *
   * @param freq -
   * @return
   */
  private boolean isValidFrequency(long freq)
  {
    return !(freq < 3500000 || freq > 29000000);
  }

  /**
   * The serial number should be 6 digits long and space is allowed between the numbers.
   *
   * @param serial
   * @return true if serial was valid
   */
  private boolean isValidSerial(String serial)
  {
    // Remove white spaces
    String str = serial.replaceAll("\\s", "");

    if (str.length() < 6 || str.length() > 6)
    {
      return false;
    }

    // should be only numbers
    try
    {
      Integer.parseInt(str);
    }
    catch (Exception exc)
    {
      return false;
    }

    return true;
  }

  /**
   * Checks
   *
   * @param call
   * @return
   */
  private boolean isValidCallsign(String call)
  {
    return call.length() >= CALLSIGN_MIN_LEN;
  }

  
}
