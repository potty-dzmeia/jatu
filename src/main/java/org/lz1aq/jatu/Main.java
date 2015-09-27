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
        byte[] trans = {1,2,3,4,5,6};
        
        I_Rig.I_DecodedTransaction decoded = radioProtocol.decode(trans);
        
        System.err.println(decoded.getBytesRead());
        System.out.println(decoded.getTransaction());
     
  }
    
    
//    String[] portNames = SerialPortList.getPortNames();
//    for (int i = 0; i < portNames.length; i++)
//    {
//      System.out.println(portNames[i]);
//    }SerialPort
//  }
  private I_Radio radioProtocol;

}
