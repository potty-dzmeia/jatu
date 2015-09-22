/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.jatu;

/**
 *
 * @author potty
 */
public enum RadioModes
{
  NONE,
  AM,         // AM -- Amplitude Modulation 
  CW,         // CW - CW "normal" sideband
  USB,        // USB - Upper Side Band
  LSB,        // LSB - Lower Side Band 
  RTTY,       // RTTY - Radio Teletype 
  FM,         // FM - "narrow" band FM 
  WFM,        // WFM - broadcast wide FM 
  CWR,        // CWR - CW "reverse" sideband
  RTTYR,      // RTTYR - RTTY "reverse" sideband
  AMS,        // AMS - Amplitude Modulation Synchronous 
  PKTLSB,     // PKTLSB - Packet/Digital LSB mode (dedicated port) 
  PKTUSB,     // PKTUSB - Packet/Digital USB mode (dedicated port) 
  PKTFM,      // PKTFM - Packet/Digital FM mode (dedicated port) 
  ECSSUSB ,   // ECSSUSB - Exalted Carrier Single Sideband USB 
  ECSSLSB ,   // ECSSLSB - Exalted Carrier Single Sideband LSB 
  FAX,        // FAX - Facsimile Mode
  SAM,        // SAM - Synchronous AM double sideband
  SAL,        // SAL - Synchronous AM lower sideband
  SAH,        // SAH - Synchronous AM upper (higher) sideband
  DSB         // DSB - Double sideband suppressed carrier
}
