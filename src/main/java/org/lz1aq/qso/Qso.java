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
package org.lz1aq.qso;

import java.util.ArrayList;
import org.joda.time.DateTime;
import org.lz1aq.utils.TimeUtils;



/**
 *
 * @author chavdar
 */
public class Qso
{
  /**Used for accessing Date parameter - the Date parameter has the format yyyy-mm-dd */
  static private final int DATE_INDEX = 0;
  
  /** Used for accessing Time parameter. The Time parameter has the format hhmm (24hour format) */
  static private final int TIME_INDEX = 1;
  
  /** Used for accessing Frequency parameter - The Freq param is in Hz */
  static private final int FREQ_INDEX = 2;
  
  /** Used for accessing Mode parameter - see class RadioModes for the expected values */
  static private final int MODE_INDEX = 3;
  
  /** Used for accessing MyCall parameter - the callsign of the user of this program */
  static private final int MYCALL_INDEX = 4;
  
  /** Used for accessing Time parameter - the callsign of the correspondent */
  static private final int HISCALL_INDEX = 5;
  
   /** Used for accessing Extra parameters */
  static private final int FIRST_EXTRA_PARAM_INDEX = 6;
  
  
  /**
   * Holds all the QSO parameters (such as "date", "time", "mode" ....)
   */
  private ArrayList<QsoParameter> qsoParams;
  
  
  public Qso(ArrayList<QsoParameter> qsoParams)
  {
    qsoParams = new ArrayList<>();
    
    this.qsoParams = qsoParams;
  }
  
  
  public Qso(long freq, String mode, String myCall, String hisCall)
  {
    qsoParams = new ArrayList<>();
    
    DateTime utc = TimeUtils.getUTC();
    qsoParams.add(new QsoParameter("date", TimeUtils.toQsoDate(utc)));
    qsoParams.add(new QsoParameter("time", TimeUtils.toQsoTime(utc)));
    qsoParams.add(new QsoParameter("frequency", Long.toString(freq)));
    qsoParams.add(new QsoParameter("mode", mode));
    qsoParams.add(new QsoParameter("myCall", myCall));
    qsoParams.add(new QsoParameter("hisCall", hisCall));
  }
  
  
  public Qso(long freq, String mode, String myCall, String hisCall, ArrayList<QsoParameter> extraQsoParams)
  {
    qsoParams = new ArrayList<>();
    
    DateTime utc = TimeUtils.getUTC();
    qsoParams.add(new QsoParameter("date", TimeUtils.toQsoDate(utc)));
    qsoParams.add(new QsoParameter("time", TimeUtils.toQsoTime(utc)));
    qsoParams.add(new QsoParameter("frequency", Long.toString(freq)));
    qsoParams.add(new QsoParameter("mode", mode));
    qsoParams.add(new QsoParameter("myCall", myCall));
    qsoParams.add(new QsoParameter("hisCall", hisCall));
    
    qsoParams.addAll(extraQsoParams);
  }
  
 
  @Override
  public String toString()
  {
    StringBuilder str = new StringBuilder(60);
   
    for(QsoParameter par: qsoParams)
    {
      str.append(' ');
      str.append(par.value);
    }
    
    return str.toString();
  }
  
  /**
   * @return The count of qso parameters. Qso parameters
   */
  public int getParamsCount()
  {
    return qsoParams.size();
  }
  
  
  /**
   * @return  Date in  yyyy-mm-dd format
   */
  public String getDate()
  {
    return qsoParams.get(DATE_INDEX).value;
  }
  
  /**
   * @return Date in hhmm 24hour format)
   */
  public String getTime()
  {
    return qsoParams.get(TIME_INDEX).value;
  }

  
  /**
   * @return Frequency on Hz
   */
  public String getFreq()
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
  
  
  public String getMyCall()
  {
    return qsoParams.get(MYCALL_INDEX).value;
  }
  
  
  public String getHisCall()
  {
    return qsoParams.get(HISCALL_INDEX).value;
  }
  

  /**
   * For accessing qso parameters.
   * 
   * Use the constants defined within this class to access desired params.
   * E.g. Use DATE_INDEX to access the "date" parameter.
   * To access extra parameters use the FIRST_EXTRA_PARAM_INDEX for the first
   * one - Following extra parameters are obtained by incrementing FIRST_EXTRA_PARAM_INDEX.
   * 
   * @param parameterIndex The index of the param (0 being the first one). Use the
          public static variables defined in this class for accessing the 
          standard params (e.g. DATE_INDEX).
   * @return Object describing the extra parameter
   */
  public QsoParameter getParam(int parameterIndex)
  {
    return qsoParams.get(parameterIndex);
  } 
  
}
