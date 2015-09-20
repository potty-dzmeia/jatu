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
    public byte[] encode_SetTuneValues(int c1, int c2, int l);
    public byte[] encode_SetAntenna(int ant);
    public byte[] encode_SetThrough(boolean throught);
    public byte[] encode_TunePa(boolean tunePa);
    public byte[] encode_GetForwardWave();
    public byte[] encode_GetReflectedWave();

}
