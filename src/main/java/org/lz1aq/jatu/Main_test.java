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
package org.lz1aq.jatu;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.lz1aq.utils.Qso;


/**
 *
 * @author chavdar
 */
public class Main_test
{
   public static void main(String args[]) throws Exception
   {  
//      DateTimeFormatter drFormatter = DateTimeFormat.shortDateTime();
//      
//      DateTime dt = new DateTime(DateTimeZone.UTC);
//      System.out.println(drFormatter.print(dt));
//      System.out.println("-=====");
//      System.out.println(drFormatter.print(dt).toString());
//      System.out.println("-=====");
     
     Qso qso = new Qso(14190000, "cw", "lz1abc", "lz1aq");
     
     System.out.println(qso);
     System.out.println(qso.toString().length());
   }
  
}
