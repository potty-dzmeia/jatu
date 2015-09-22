/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.rig_interfaces;

/**
 * Automatic Antenna Tuner specific methods
 * @author chavdar 
*/
public interface I_AntennaTuner extends I_Rig
{
    public byte[] encodeSetTuneValues(int c1, int c2, int l);
    public byte[] encodeSetAntenna(int ant);
    public byte[] encodeSetThrough(boolean throught);
    public byte[] encodeTunePa(boolean tunePa);
    public byte[] encodeGetForwardWave();
    public byte[] encodeGetReflectedWave();

}
