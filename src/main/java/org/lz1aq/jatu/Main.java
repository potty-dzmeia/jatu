/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.jatu;

import org.lz1aq.py.rig.I_Radio;
import org.lz1aq.py.rig.I_Rig;
import java.util.ArrayList;;
import org.lz1aq.utils.Misc;


/**
 *
 */
public class Main
{
  static private Object serialPort;
  
    public static void main(String args[]) throws Exception
    {
      ArrayList<Integer> list = new ArrayList<>();
      Integer integer = 5;
      list.add(integer);
      list.add(integer);
      System.out.println(list.size());
      System.out.println(list.get(0));
      System.out.println(list.get(1).hashCode());
    } 
}
