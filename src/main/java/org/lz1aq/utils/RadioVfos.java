/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.utils;

/**
 *
 * @author potty
 */
public enum RadioVfos {
   NONE(-1), 
   A(0),
   B(1), 
   C(2), 
   D(3), 
   E(4), 
   F(5), 
   G(6);

   private final int code;
   RadioVfos(int code)  { this.code = code; }
   public int getCode() { return code; }
}
