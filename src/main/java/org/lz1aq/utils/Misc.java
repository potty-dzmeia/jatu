/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.utils;



/**
 *
 * @author potty
 */
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
