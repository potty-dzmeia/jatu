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
}
