/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjavatopython;


/**
 *
 * @author potty
 */
public interface RigType 
{
    public String getManufacturer();
    public String getModel();
    public String getSerialPortSettings();
        
    public String encodeSetFreq(long freq, int vfo);             
}
