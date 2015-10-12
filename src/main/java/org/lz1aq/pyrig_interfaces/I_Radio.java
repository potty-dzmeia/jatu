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
   /**
   * The function returns a string with all the modes that it supports.
   * Example: "cw ssb lsb"
   * 
   * @return A string with the supported modes. Each mode is separated from 
   * the next with space.
   */
  public String getAvailableModes();
  
  
  /**
   * The function returns a string with all the bands that it supports.
   * Example: "3.5 7 14"
   * 
   * @return A string with the supported bands. Each band is separated from the
   *  next with space.
   */
  public String getAvailableBands();
  
  
  /**
   * Gets the command(s) with which we can tell a radio to change frequency
   *
   * @param freq - the frequency to which we would like to set the VFO
   * @param vfo - which VFO we would like to manipulate
   * @return array of Transactions which are to be sent to the radio.
   */
  public I_EncodedTransaction[] encodeSetFreq(long freq, int vfo);
  
  
  /**
   * Gets the command(s) with which we can tell the radio to send us the frequency
   *
   * @param vfo - for which VFO we want the frequency
   * @return array of Transactions which are to be sent to the radio.
   */
  public I_EncodedTransaction[] encodeGetFreq(int vfo);
  
  
  /**
   * Gets the command(s) that must be send to the radio in order to set mode (e.g.
   * CW)
   *
   * @param mode - the mode to which we would like to set the VFO (see RadioModes)
   * @param vfo - which VFO we would like to manipulate
   * @return array of Transactions which are to be sent to the radio.
   */
  public I_EncodedTransaction[] encodeSetMode(String mode, int vfo);
  
  
  /**
   * Gets the command(s) with which we can tell the radio to send us the current
   * mode
   *
   * @param vfo - for which VFO we want the mode  
   * @return array of Transactions which are to be sent to the radio.
   */
  public I_EncodedTransaction[] encodeGetMode(int vfo);
  
}