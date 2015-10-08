/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.lz1aq.utils;

import java.io.ByteArrayOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DynamicByteArray extends ByteArrayOutputStream and add one additional method
 * remove() which removes desired number of bytes starting from the beginning 
 * of the array.
 * 
 * @author chavdar
 */
public class DynamicByteArray extends ByteArrayOutputStream 
{
  private static final Logger logger = Logger.getLogger(DynamicByteArray.class.getName());
  
  
  public DynamicByteArray()
  {
    super();
  }
  public DynamicByteArray(int i)
  {
    super(i);
  }
  
  /**
   * Removes bytes starting from the beginning of the array.
   * 
   * @param removedBytes  - the number of bytes that we would like to remove.
   * If the number of bytes is more than the amount inside the array no error
   * will occur and all bytes from the array will be deleted
   * 
   */
  public void remove(int removedBytes)
  {
    
    if(removedBytes==0)
      return;
    
    // We can't remove more than there is in the array
    if(removedBytes > this.count)
      removedBytes = this.count;
            
    System.arraycopy(this.buf, removedBytes, buf, 0, this.count-removedBytes);
    
    this.count -=removedBytes;
    
    logger.log(Level.INFO, "Removed: "+removedBytes+"; Remaining: "+this.count+";  Buffer size "+this.buf.length);
    
  }
  
}
