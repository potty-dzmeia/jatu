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



public class Misc
{
  
  /**
   * Converts array of bytes to string in which the bytes are presented in hex
   * format
   * 
   * @param bytes - the bytes to be converted
   * @return String with the values represented in hex format
   */
  public static String toHexString(byte[] bytes)
  {
    StringBuilder stringBuilder = new StringBuilder();
    for(byte b : bytes)
    {
      stringBuilder.append(String.format("%02x ",b));
    }
    
    return stringBuilder.toString();
  }
  
  
  /**
   * Inserts "," between the thousands 
   * 
   * @param freq - the frequency that we would like to format
   * @return - new frequency with the "," between the thousands
   */
  public static String formatFrequency(String freq)
  {
    StringBuilder buf = new StringBuilder();
    int counter = 0;
    
    for(int i=freq.length()-1; i>=0 ; i--)
    {
      counter++;
      buf.append(freq.charAt(i));
      if(counter==3 && i!=0)
      {
        buf.append(",");
        counter=0;
      }
    }
    buf.reverse();

    return buf.toString();
  }
  
  
  /**
   * Returns the current stack of the Thread
   * 
   * @return 
   */
  public static String getStack()
  {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

    StringBuilder stack = new StringBuilder();
    
    // Do not show the last to functions from the stack (user does not care about them)
    for (int st=2; st<stackTrace.length; st++)
    {
        stack.append(stackTrace[st].toString());
        stack.append("\n");
    }
    return stack.toString();
  }
}
