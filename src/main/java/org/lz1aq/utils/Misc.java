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
  public static String toString(byte[] bytes)
  {
    String resultString = "";
    
    for(byte b : bytes)
    {
      resultString = resultString + String.format("%02x",b) + " ";
    }
    
    return resultString;
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
}
