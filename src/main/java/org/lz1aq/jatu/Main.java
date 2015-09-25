/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.jatu;


import java.util.LinkedList;
import java.util.Queue;
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
  
    public static void main(String args[])
    {  
        JythonObjectFactory f2 = new JythonObjectFactory(I_Radio.class, "icom", "Icom");
        I_Radio radioProtocol = (I_Radio) f2.createObject();
        
        byte[] command;
        
          
        command = radioProtocol.encodeSetFreq(7100000, 1);
        
        for(int i=0; i<command.length; i++) 
          System.out.println(String.format("%X", command[i]));
        
        
        command = radioProtocol.encodeSetMode(RadioModes.CW.toString(), 1);
        for(int i=0; i<command.length; i++) 
          System.out.println(String.format("%X", command[i]));
        
        I_Radio.I_SerialSettings serialSettings = radioProtocol.getSerialPortSettings();
        System.out.println(serialSettings.getParity());
        
        
        
        SerialPort
        
    }
    
    
//    String[] portNames = SerialPortList.getPortNames();
//    for (int i = 0; i < portNames.length; i++)
//    {
//      System.out.println(portNames[i]);
//    }SerialPort
//  }
  private I_Radio radioProtocol;

}
