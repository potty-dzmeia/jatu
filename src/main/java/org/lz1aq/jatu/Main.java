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
        JythonObjectFactory f2 = new JythonObjectFactory(I_Radio.class, "icom", "Icom");
        I_Radio radio = (I_Radio) f2.createObject();
        
        byte[] command;
        
          
        command = radio.encodeSetFreq(7100000, 1);
        
        for(int i=0; i<command.length; i++) 
          System.err.println(String.format("%X", command[i]));
        
        
        command = radio.encodeSetMode(RadioModes.ECSSLSB.toString(), 1);
        for(int i=0; i<command.length; i++) 
          System.err.println(String.format("%X", command[i]));

    }
}
