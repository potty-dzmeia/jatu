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


/**
 * A Qso has the following standard params: date, time, mode, frequency, myCall and 
 * hisCall.
 * However there could some extra info which was exchanged during the QSO like
 * for example "sent RST=599". This class is used to describe all such extra
 * parameters.
 */
public class QsoParameter
{
  public String  name;  // The name describing the extra param (e.g. "sntRst")
  public String  value; // The value of the param (e.g. "599")
  
  public QsoParameter(String name, String value)
  {
    this.name = name;
    this.value = value;
  }
}
