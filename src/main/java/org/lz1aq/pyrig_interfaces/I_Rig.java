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
    /**
     * Decodes information coming from the Rig into JSON formatted string
     * 
     * @param data The data coming from the Rig
     * @return the decoded transaction with some additional control info
     */
    public I_DecodedTransaction decode(byte[] data);
    
    /** @return string specifying the manufacturer of the rig - E.g. "Kenwood"*/
    public String getManufacturer();
    
    /** @return string specifying the model of the Rig - E.g. "IC-756PRO"*/
    public String getModel();
    
   /** @return JSON formatted string describing the COM port settings that 
    * needs to be used when communicating with this Rig*/
    public I_SerialSettings getSerialPortSettings();
 
    /** @return Initialization command that is to be send to the Rig*/
    public I_EncodedTransaction encodeInit();
    
    /** @return Cleanup command that is to be send to the Rig */
    public I_EncodedTransaction encodeCleanup();   
 
}

