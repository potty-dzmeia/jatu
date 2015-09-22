/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.rig_interfaces;

/**
 * By Radio we mean 
 */
public interface I_Radio extends I_Rig
{
    /**
     * 
     * @param freq
     * @param vfo
     * @return 
     */
    public byte[] encodeSetFreq(long freq, int vfo);
    
    /**
     * 
     * @param mode
     * @param vfo
     * @return 
     */
    public byte[] encodeSetMode(String mode, int vfo);
}
