/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.jatu;


import org.lz1aq.rig_interfaces.I_Radio;


/**
 *
 */
public class Main
{
    public static void main(String args[])
    {  
        JythonObjectFactory f2 = new JythonObjectFactory(I_Radio.class, "Icom", "Icom");
        I_Radio radio = (I_Radio) f2.createObject();
        
        byte[] command;
        
        
        command = radio.encode_SetFreq(7100000, 1);
        
        System.err.println(command);
//        byte[] array = icom.encode_SetFreq(14000000, 0);
//        
//        
//                
//                for (byte b : array)
//      {
//        System.err.println(String.format("%04x", (int) b));
//      }
    }
}
