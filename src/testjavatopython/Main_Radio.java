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
public class Main_Radio
{
    public static void main(String args[])
    {

        JythonObjectFactory factory = new JythonObjectFactory(RigType.class, "Rig", "Rig");

        RigType rig = (RigType) factory.createObject("safsadf");

        System.out.println(rig.getManufacturer());
        System.out.println(rig.getModel());
        System.out.println(rig.getSerialPortSettings());
        System.out.println(rig.encodeSetFreq(1223234, 0));
        
       
    }
}
