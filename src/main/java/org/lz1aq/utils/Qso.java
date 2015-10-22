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
package org.lz1aq.utils;

import org.joda.time.DateTime;



/**
 *
 * @author chavdar
 */
public class Qso
{
  private String time;        // hhmm (24hour format)
  private String date;        // yyyy-mm-dd
  private long   freq;        // Frequency on Hz
  private String mode;        // see class RadioModes
  private String myCall;      // 
  private String hisCall;       
//  private String sntInfo;     // additional sent data
//  private String rcvInfo;     // additional received data
  
  
  public Qso(long freq, String mode, String myCall, String hisCall)
  {
    DateTime utc = TimeUtils.getUTC();
    
    this.date = TimeUtils.toQsoDate(utc);
    this.time = TimeUtils.toQsoString(utc);
    this.freq = freq;
    this.mode = mode;
    this.myCall = myCall;
    this.hisCall = hisCall;
  }
  
  
  @Override
  public String toString()
  {
    StringBuilder str = new StringBuilder(60);
    str.append(date);
    str.append(' ');
    str.append(time);
    str.append(' ');
    str.append(Long.toString(freq));
    str.append(' ');
    str.append(mode);
    str.append(' ');
    str.append(myCall);
    str.append(' ');
    str.append(hisCall);
    
    return str.toString();
  }
}
