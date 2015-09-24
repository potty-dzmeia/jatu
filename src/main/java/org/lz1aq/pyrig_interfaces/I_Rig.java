/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.pyrig_interfaces;


/**
 * A rig is an equipment which can be controlled with the help of commands 
 * send from the program to the rig. 
 * 
 * The Rig can also send data backwards.
 * 
 */
public interface I_Rig 
{
    /** @return string specifying the manufacturer of the rig - E.g. "Kenwood"*/
    public String getManufacturer();
    
    /** @return string specifying the model of the Rig - E.g. "IC-756PRO"*/
    public String getModel();
    
   /** @return JSON formatted string describing the COM port settings that 
    * needs to be used when communicating with this Rig*/
    public I_SerialSettings getSerialPortSettings();
    
    /**
     * Decodes information coming from the Rig into JSON formatted string
     * 
     * @param data The data coming from the Rig
     * @return JSON formatted string
     */
    public String decode(byte[] data);
    
    /** @return Initialization command that is to be send to the Rig*/
    public byte[] encodeInit();
    
    /** @return Cleanup command that is to be send to the Rig */
    public byte[] encodeCleanup();   
    
    
    /**
     *  Serial Port settings that must be used when connecting to the rig
     */
    public interface I_SerialSettings
    {

      public int getBauderateMin();

      public int getBauderateMax();

      public int getDataBits();

      public int getStopBits();

      public String getParity();

      public String getHandshake();

      public int getWriteDelay();
      
      public int getPostWriteDelay();

      public int getTimeout();

      public int getRetry();
    }
}
