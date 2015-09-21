/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.jatu;


import java.net.URL;
import java.net.URLClassLoader;
import org.lz1aq.rig_interfaces.I_Radio;


import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;
/**
 *
 */
public class Main
{
    public static void main(String args[])
    {

//        JythonObjectFactory f1 = new JythonObjectFactory(I_Rig.class, "Rig", "Rig");
//        I_Rig rig = (I_Rig) f1.createObject();
//
      
//        PythonInterpreter interpreter = new PythonInterpreter();
//        interpreter.exec("import sys\n"+"sys.path.append(\"target/classes/pyrig/\")");
        //interpreter.exec("import sys\n"+"sys.path.append(\" pyrig/\")");
        
//        interpreter.exec("print sys.path");
        
        JythonObjectFactory f2 = new JythonObjectFactory(I_Radio.class, "Icom", "Icom");
        I_Radio radio = (I_Radio) f2.createObject();
        
        byte[] array = radio.encode_SetFreq(14000000, 0);
        
        for (byte b : array)
      {
        System.err.println(String.format("%04x", (int) b));
      }
//        System.out.println(radio.getManufacturer());
//        System.out.println(radio.getModel());
//        System.out.println(radio.getSerialPortSettings());
        
//      
//        //Get the System Classloader
//        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
// 
//        //Get the URLs
//        URL[] urls = ((URLClassLoader)sysClassLoader).getURLs();
// 
//        for (URL url : urls)
//        {
//          System.out.println("-------------------------");
//            System.out.println(url.getFile());
//        }       

//      PythonInterpreter interpreter = new PythonInterpreter();
//      interpreter.exec("from sys import *\n" +
//"import os\n" +
//"\n" +
//"print os.getcwd()\n" +
//"\n" +
//"print path");
//       
    }
}
