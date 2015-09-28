/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.jatu;


import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPortList;
import org.lz1aq.pyrig_interfaces.I_Radio;
import org.lz1aq.pyrig_interfaces.I_Rig;
import org.lz1aq.rsi.Radio;


/**
 *
 */
public class Main
{
  static private Object serialPort;
  
    public static void main(String args[]) throws Exception
    {  
        JythonObjectFactory f2 = new JythonObjectFactory(I_Radio.class, "icom", "Icom");
        I_Radio radioProtocol = (I_Radio) f2.createObject();
        
//        byte[] command;
//        
//          
//        command = radioProtocol.encodeSetFreq(7100000, 1);
//        
//        for(int i=0; i<command.length; i++) 
//          System.out.println(String.format("%X", command[i]));
//        
//        
//        command = radioProtocol.encodeSetMode(RadioModes.CW.toString(), 1);
//        for(int i=0; i<command.length; i++) 
//          System.out.println(String.format("%X", command[i]));
//        
//        I_Radio.I_SerialSettings serialSettings = radioProtocol.getSerialPortSettings();
//        System.out.println(serialSettings.getParity());

        
        
//        String[] serialPorts= SerialPortList.getPortNames();
//        SerialPort port = new SerialPort(serialPorts[0]);
//        Radio rad = new Radio(radioProtocol, port);
//        rad.start();
//        
//        try
//        {
//          for(int i=0; i<1000000; i++)
//          {
//            rad.setFrequency(14000300, 1);
//            Thread.sleep(10);
//          }
//          
//        } catch (Exception ex)
//        {
//          Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.toString(), ex);
//        }
       
        
        I_Rig.I_DecodedTransaction decoded = radioProtocol.decode(transFreq);
        
        System.err.println("bytes read: "+decoded.getBytesRead());
        System.out.println("jason: "+decoded.getTransaction());
        
        decoded = radioProtocol.decode(transMode);
        System.err.println("bytes read: "+decoded.getBytesRead());
        System.out.println("jason: "+decoded.getTransaction());
        
        decoded = radioProtocol.decode(transNegative);
        System.err.println("bytes read: "+decoded.getBytesRead());
        System.out.println("jason: "+decoded.getTransaction());
        
        decoded = radioProtocol.decode(transPositive);
        System.err.println("bytes read: "+decoded.getBytesRead());
        System.out.println("jason: "+decoded.getTransaction());
        
     
  }
    
    
//    String[] portNames = SerialPortList.getPortNames();
//    for (int i = 0; i < portNames.length; i++)
//    {
//      System.out.println(portNames[i]);
//    }SerialPort
//  }
  private I_Radio radioProtocol;
  
   static byte[] transFreq = {(byte)0xFE, 
                            (byte)0xFE, 
                            (byte)0xE0, 
                            (byte)0x5C, 
                            (byte)0x00, 
                            (byte)0x31, 
                            (byte)0x02, 
                            (byte)0x10, 
                            (byte)0x14, 
                            (byte)0x00, 
                            (byte)0xFD};
         
  static byte[] transPositive = {(byte)0xFE, 
                                (byte)0xFE, 
                                (byte)0xE0, 
                                (byte)0x5C, 
                                (byte)0xFB, 
                                (byte)0xFD};
   
  static byte[] transNegative = {(byte)0xFE, 
                                (byte)0xFE, 
                                (byte)0xE0, 
                                (byte)0x5C, 
                                (byte)0xFA, 
                                (byte)0xFD};
   
  static byte[] transMode = {   (byte)0xFe, 
                                (byte)0xFE, 
                                (byte)0xe0, 
                                (byte)0x5C, 
                                (byte)0xFD,
                                (byte)0xFE, 
                                (byte)0xFE, 
                                (byte)0xE0, 
                                (byte)0x5C, 
                                (byte)0x01,
                                (byte)0x08,
                                (byte)0xFD};

}
