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
   * Gets the command with which we can tell a radio to change currently used
   * frequency
   * 
   * @param freq - the frequency to which we would like to set the radio
   * @return Transaction container containing the data plus some additional
   *         control information
   */
  public I_EncodedTransaction encodeSetFreq(long freq);
  
  
  /**
   * Gets the command with which we can tell the radio to send us the currently
   * set frequency
   * 
   * @return Transaction container containing the data plus some additional
   *         control information
   */
  public I_EncodedTransaction encodeGetFreq();
  

  /**
   * Gets the command with which we can tell a radio to change frequency
   *
   * @param freq - the frequency to which we would like to set the VFO
   * @param vfo - which VFO we would like to manipulate
   * @return Transaction container containing the data plus some additional
   *         control information
   */
  public I_EncodedTransaction encodeSetVfoFreq(long freq, int vfo);
  
  
  /**
   * Gets the command with which we can tell the radio to send us the frequency
   *
   * @param vfo - for which VFO we want the frequency
   * @return Transaction container containing the data plus some additional
   *         control information
   */
  public I_EncodedTransaction encodeGetVfoFreq(int vfo);
  
  
  /**
   * Gets the command that must be send to the radio in order to the mode
   * currently in use.
   *
   * @param mode - the mode to which we would like to set the transceiver
   * @return Transaction container containing the data plus some additional
   *         control information
   */
  public I_EncodedTransaction encodeSetMode(String mode);
  
  
  /**
   * Gets the command with which we can tell the radio to send us the current
   * mode
   * 
   * @return Transaction container containing the data plus some additional
   *         control information
   */
  public I_EncodedTransaction encodeGetMode();
  
  
  /**
   * Gets the command that must be send to the radio in order to set mode (e.g.
   * CW)
   *
   * @param mode - the mode to which we would like to set the VFO (see RadioModes)
   * @param vfo - which VFO we would like to manipulate
   * @return Transaction container containing the data plus some additional
   *         control information
   */
  public I_EncodedTransaction encodeSetVfoMode(String mode, int vfo);
  
  
  /**
   * Gets the command with which we can tell the radio to send us the current
   * mode
   *
   * @param vfo - for which VFO we want the mode  
   * @return Transaction container containing the data plus some additional
   *         control information
   */
  public I_EncodedTransaction encodeGetVfoMode(int vfo);

  
}


/**
 * Below are all possible JSON formatted decoded transactions 
 * ==========================================================
 * 
 * 0) Not Supported - if we couldn't decode the data coming from the radio
 * -----------------
 * {
 *  "not_supported": {
 *    "not_supported": "contains the data which couldn't be decoded"               
 *   }
 * }
 * 
 * 
 * 1) Confirmation - response from the radio after sending a command to it
 * -----------------
 * {
 *  "confirmation": {
 *    "confirmation": "0" or "1"        <-- "0" is for negative; "1" is for positive
 *   }
 * }
 * 
 * 
 * 2) Frequency - radio changed frequency
 * -----------------
 * {
 *  "frequency": {
 *    "frequency": "14190000"
 *    "vfo": "0" or "1" and so on..     <--Optional - indicates which VFO changed its frequency
 *   }
 * }
 * 
 * 
 * 3) Mode - radio changed mode
 * -----------------
 * {
 *  "mode": {
 *    "mode": "cw"
 *    "vfo": "0" or "1" and so on..     <--Optional - indicates which VFO changed its mode
 *   }
 * }
 * 
 */