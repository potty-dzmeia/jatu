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
 * In the diagram below can be seen how we can communicate with the rig.
 * Example1: The person has rotated the knob on the radio and changed the frequency
 *    - The rig sends some data to the program
 *    - The Java program receives the data
 *    - The Java program gives the data to icom.py to be decoded 
 *    - The icom.py returns the parsed data (e.g. Frequency change to 14,000,000)
 *    - Java program can use this info for example to update the GUI with the new frequency
 * 
 * Example2: the person using the Java program wants to change the frequency on the radio to 14,200,000 
 *    - The Java program ask the icom.py to encode the frequency change command
 *    - Icom.py encodes the command into a transaction that the radio can understand and returns the data to the Java program
 *    - The Java program sends the transaction to the radio
 *    - The radio receives the transaction and changes the frequency to the desired value of 14,200,000
 * 
 * 
 *   +-------------+     +----------------+        +----------------+
 *   |   icom.py   |     | Java program   |        |   Rig(Radio)   |
 *   +------+------+     +--------+-------+        +--------+-------+
 *          |                     |                         |        
 *          |                     |    series of bytes      |        
 *          |                     <-------------------------+        
 *          |  array of bytes     |                         |        
 *          <---------------------+                         |        
 * decoding |                     |                         |        
 *          | DecodedTransaction  |                         |        
 *          +--------------------->                         |        
 *          |                     |                         |        
 *  +----------------------------------------------------------------+
 *          |                     |                         |        
 *          | encodeChangeFrequency                         |        
 *          <---------------------+                         |        
 * encode   |                     |                         |        
 *          | EncodedTransaction  |                         |        
 *          +--------------------->                         |        
 *          |                     |    EncodedTransaction   |        
 *          |                     +------------------------->        
 *          |                     |                         |        
 *          |                     |                         |        
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
     * @return the decoded transaction with some additional control info
     */
    public I_DecodedTransaction decode(byte[] data);
    
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
      
      /**
       *  Returns parity parameter
       * @return Possible values are: 'None', 'Even', 'Odd', 'Mark', 'Space'
       */
      public String getParity();

      /**
       * Returns handshake parameter
       * @return Possible values are 'None', 'XonXoff', 'CtsRts'
       */
      public String getHandshake(); 
      
      /**
       * If the RTS line state should be changed.
       * 
       * @return Possible values are "None", "On", "Off"
       */
      public String getRts();
      
       /**
       * If the DTR line state should be changed.
       * 
       * @return Possible values are "None", "On", "Off"
       */
      public String getDtr();
    }
    
    /**
     * Container for a "transaction" which is to be sent over the serial port
     * 
     * A "transaction" is a packet of bytes being sent to the rig. Usually it
     * contains some command (e.g. change frequency in case of a radio)
     */
    public interface I_EncodedTransaction
    {
      /**
       * Gets the transaction which can be send to the rig
       * 
       * @return Transaction in the form of array of bytes  
       */
      public byte[] getTransaction();
      
      /**
       * If there should be a delay between each byte of the transaction being sent out
       *  
       * @return The amount of delay in milliseconds
       */
      public int getWriteDelay();
      
      /**
       * If there should be a delay between each transaction send out
       * 
       * @return The amount of delay in millisecond
       */
      public int getPostWriteDelay();
      
      /**
       *  Timeout after which we should abandon sending the transaction to the rig
       * 
       * @return Timeout, in milliseconds
       */
      public int getTimeout();
      
      /**
       * Maximum number of retries if command fails (0 for no retry)
       * 
       * @return number of retries before abandoning the transaction
       */
      public int getRetry();
      
      
      /** If the program should expect confirmation after sending this transaction to the rig
       * 
       * @return TRUE - if the rig will send confirmation after receiving this transaction
       */
      public boolean isConfirmationExpected();
    }
    
    
    /**
     * Contains the decoded transaction together with some control information
     *
     */
    public interface I_DecodedTransaction
    {
      /**
       * Gets the transaction which was received from the rig.
       * 
       * @return Transaction in the form of JSON formatted string. 
       * Might be null in case the supplied buffer did not contain a complete
       * transaction
       */
      public String getTransaction();
      
      
      /**
       * Returns the amount of bytes that were read from the supplied buffer
       * in order to decode the transaction.
       * 
       * 
       * Typical usage is:
       * 1) decodedTransaction1 = I_Rig.decode(receiveBuffer);
       *    to decode the transaction   
       * 
       * 2) receiveBuffer.removeBytes(decodedTransaction.getBytesRea  d());  
       *    to remove the already decoded bytes from the receive buffer
       * 
       * 3) decodedTransaction2 = I_Rig.decode(receiveBuffer);
       *    to decode next transaction
       * 
       * ...and so on...
       * 
       * @return The amount of bytes that were read. 0 if no transaction was found.
       * If this function returns 
       */
      public int getBytesRead();
      
    }
}

