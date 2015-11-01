/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.jatu;

import org.lz1aq.pyrig.I_Radio;
import org.lz1aq.pyrig.I_Rig;
import org.lz1aq.utils.Misc;


/**
 *
 */
public class Main
{
  static private Object serialPort;
  
    public static void main(String args[]) throws Exception
    {  
        JythonObjectFactory f2 = new JythonObjectFactory(I_Radio.class, "icom", "Icom");
        I_Radio radioProtocol = (I_Radio) f2.createObject();
        

//        I_Rig.I_DecodedTransaction decoded = radioProtocol.decode(transFreq);
//        
//        System.err.println("bytes read: "+decoded.getBytesRead());
//        System.out.println("jason: "+decoded.getTransaction());
//        
//        decoded = radioProtocol.decode(transMode);
//        System.err.println("bytes read: "+decoded.getBytesRead());
//        System.out.println("jason: "+decoded.getTransaction());
//        
//        decoded = radioProtocol.decode(transNegative);
//        System.err.println("bytes read: "+decoded.getBytesRead());
//        System.out.println("jason: "+decoded.getTransaction());
//        
//        decoded = radioProtocol.decode(transPositive);
//        System.err.println("bytes read: "+decoded.getBytesRead());
//        System.out.println("jason: "+decoded.getTransaction());
      
        try{
          radioProtocol.getManufacturer();
        }catch(Exception e)
        {
          System.out.println(e.toString());
        }
    
  }
      
    
//   
//  }  private I_Radio radioProtocol;

  private I_Radio radioProtocol;
  
   static byte[] transFreq = {(byte)0xFE, 
                            (byte)0xFE, 
                            (byte)0xE0, 
                            (byte)0x5C, 
                            (byte)0x00, 
                            (byte)0x31, 
                            (byte)0x02, 
                            (byte)0x10, 
                            (byte)0x14, 
                            (byte)0x00, 
                            (byte)0xFD};
         
  static byte[] transPositive = {(byte)0xFE, 
                                (byte)0xFE, 
                                (byte)0xE0, 
                                (byte)0x5C, 
                                (byte)0xFB, 
                                (byte)0xFD};
   
  static byte[] transNegative = {(byte)0xFE, 
                                (byte)0xFE, 
                                (byte)0xE0, 
                                (byte)0x5C, 
                                (byte)0xFA, 
                                (byte)0xFD};
   
  static byte[] transMode = {   (byte)0xFe, 
                                (byte)0xFE, 
                                (byte)0xe0, 
                                (byte)0x5C, 
                                (byte)0xFD,
                                (byte)0xFE,     
                                (byte)0xFE, 
                                (byte)0xE0, 
                                (byte)0x5C, 
                                (byte)0x01,
                                (byte)0x08,
                                (byte)0xFD};
  
  

}
