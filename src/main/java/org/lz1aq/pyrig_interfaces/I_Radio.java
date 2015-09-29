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
   * Gets the command with which we can tell a radio to change frequency
   *
   * @param freq - the frequency to which we would like to set the VFO
   * @param vfo - which VFO we would like to manipulate
   * @return Packet of bytes containing the command
   */
  public I_EncodedTransaction encodeSetFreq(long freq, int vfo);

  /**
   * Gets the command that must be send to the radio in order to set mode (e.g.
   * CW)
   *
   * @param mode - the mode to which we would like to set the VFO (see RadioModes)
   * @param vfo - which VFO we would like to manipulate
   * @return Packet of bytes containing the command
   */
  public I_EncodedTransaction encodeSetMode(String mode, int vfo);

  
  
  
  public enum RadioModes
  {
    NONE,
    AM, // AM -- Amplitude Modulation 
    CW, // CW - CW "normal" sideband
    USB, // USB - Upper Side Band
    LSB, // LSB - Lower Side Band 
    RTTY, // RTTY - Radio Teletype 
    FM, // FM - "narrow" band FM 
    WFM, // WFM - broadcast wide FM 
    CWR, // CWR - CW "reverse" sideband
    RTTYR, // RTTYR - RTTY "reverse" sideband
    AMS, // AMS - Amplitude Modulation Synchronous 
    PKTLSB, // PKTLSB - Packet/Digital LSB mode (dedicated port) 
    PKTUSB, // PKTUSB - Packet/Digital USB mode (dedicated port) 
    PKTFM, // PKTFM - Packet/Digital FM mode (dedicated port) 
    ECSSUSB, // ECSSUSB - Exalted Carrier Single Sideband USB 
    ECSSLSB, // ECSSLSB - Exalted Carrier Single Sideband LSB 
    FAX, // FAX - Facsimile Mode
    SAM, // SAM - Synchronous AM double sideband
    SAL, // SAL - Synchronous AM lower sideband
    SAH, // SAH - Synchronous AM upper (higher) sideband
    DSB         // DSB - Double sideband suppressed carrier
  }

}
