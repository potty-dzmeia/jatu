/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testjavatopython;

/**
 *
 */
public class Main
{
    public static void main(String args[])
    {

//        JythonObjectFactory f1 = new JythonObjectFactory(I_Rig.class, "Rig", "Rig");
//        I_Rig rig = (I_Rig) f1.createObject();

//        JythonObjectFactory f2 = new JythonObjectFactory(I_Radio.class, "Icom", "Icom");
//        I_Radio radio = (I_Radio) f2.createObject();
//        
//        System.out.println(radio.encode_SetFreq(14000000, 0));
        
//        System.out.println(rig.getManufacturer());
//        System.out.println(rig.getModel());
//        System.out.println(rig.getSerialPortSettings());
        
        
        JythonObjectFactory f = new JythonObjectFactory(I_AntennaTuner.class, "AntennaTuner", "AntennaTuner");
        I_AntennaTuner tunner = (I_AntennaTuner) f.createObject();
        
        System.out.println(tunner.getManufacturer());
       
    }
}
