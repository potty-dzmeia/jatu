/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.pyrig_interfaces;

/**
 * Interface of a radio protocol parser.
 * 
 * Encode - The protocol parser is responsible of encoding commands (e.g. set 
 *          frequency to 14.100.100) into a packet of bytes that is ready to be 
 *          send to the radio.
 * Decode - The protocol parser is responsible of decoding packets coming from
 *          the radio into meaningful data (e.g. Mode was changed to CW)
 */
public interface I_Radio extends I_Rig
{
    /** Gets the command with which we can tell a radio to change frequency
     *  
     * @param freq - the frequency to which we would like to set the VFO
     * @param vfo - which VFO we would like to manipulate
     * @return Packet of bytes containing the command
     */
    public byte[] encodeSetFreq(long freq, int vfo);
    
    /** Gets the command that must be send to the radio in order to set mode (e.g. CW)
     * 
     * @param mode - the mode to which we would like to set the VFO
     * @param vfo - which VFO we would like to manipulate
     * @return Packet of bytes containing the command
     */
    public byte[] encodeSetMode(String mode, int vfo);
    
}
