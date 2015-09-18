/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjavatopython;

/**
 * By Radio we mean transceiver.
 */
public interface I_Radio extends I_Rig
{
    /** @return command for frequency change
     */
    public byte[] encode_SetFreq(long freq, int vfo);
}
