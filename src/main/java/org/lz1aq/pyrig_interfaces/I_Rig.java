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

      /** Delay between each byte sent out, in milliseconds
       * 
       * @return 
       */
      public int getWriteDelay();
      
      /** Delay between each commands send out, in milliseconds
       * 
       * @return 
       */
      public int getPostWriteDelay();

      /** Timeout, in milliseconds
       * 
       * @return 
       */
      public int getTimeout();

      
      /** Maximum number of retries if command fails (0 for no retry)
       *  
       * @return 
       */
      public int getRetry();
    }
    
    /**
     *  Serial transaction contains:
     *  - the bytes that must be send to the radio
     *  - additional details concerning the transaction (e.g. if we should w8 for confirmation after sending the transaction)
     */
    public interface I_SerialTransaction
    {
      /**
       * Gets the transaction which can be send to the radio
       * 
       * @return Transaction in the form of array of bytes  
       */
      public byte[] getTransaction();
      
      /** Check if there will be a confirmation after this transaction
       * 
       * @return TRUE - is the radio will send confirmation after receiving this transaction
       */
      public boolean isWaitForConfirmation();
    }
}
