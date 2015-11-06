// ***************************************************************************
// *   Copyright (C) 2015 by Chavdar Levkov                              
// *   ch.levkov@gmail.com                                                   
// *                                                                         
// *   This program is free software; you can redistribute it and/or modify  
// *   it under the terms of the GNU General Public License as published by  
// *   the Free Software Foundation; either version 2 of the License, or     
// *   (at your option) any later version.                                   
// *                                                                         
// *   This program is distributed in the hope that it will be useful,       
// *   but WITHOUT ANY WARRANTY; without even the implied warranty of        
// *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         
// *   GNU General Public License for more details.                          
// *                                                                         
// *   You should have received a copy of the GNU General Public License     
// *   along with this program; if not, write to the                         
// *   Free Software Foundation, Inc.,                                       
// *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             
// ***************************************************************************
package org.lz1aq.pycontest;


public interface I_Contest
{
  /**
   * @return The name of the contest
   */
  public String getName();
  
  /**
   * Used for acquiring all QSO types for the given contest. By QSO types we 
   * mean QSOs with different set of parameters.
   * 
   * @return Object holding QSO definitions
   */
  public I_QsoTemplates getQsoTemplates();
  
  
  /**
   * Calculates overall result
   * 
   * @param log - Reference to the object holding all the QSOs
   * @return - The calculated result
   */
  public int  calculateResult(I_Log log);
 
  
  /**
   * 
   * @param log
   * @param protoQso
   * @return 
   */
  public I_CallsignCheckResult checkCallsign(I_Log log, I_ProtoQso protoQso);
  
  
  public void fillQsoParams(int qsoTemplate, I_Qso qso);
  
  
  /**
   * The function returns a string with all the modes that the contest supports
   * Example: "cw usb lsb"
   * 
   * @return A string with the supported modes. Each mode is separated from 
   * the next with space. Could be in lower case or capital letters.
   */
  public String getAvailableModes();
  
  
  /**
   * The function returns a string with all the bands that the contest supports.
   * Example: "3.5 7 14"
   * 
   * @return A string with the supported bands in MHz. Each band is separated 
   *  from the next one with space.
   */
  public String getAvailableBands();
}