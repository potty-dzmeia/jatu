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
package org.lz1aq.py.contest;


/**
 * The class holds QSOs templates with different set of parameters 
 */
public interface I_QsoTemplate
{ 
  /**
   * Gets the name of the Qso template
   * @return The name of the QSO template
   */
  String getName();
  
  
  /**
   * Gets the number of sent parameters 
   * 
   * @return - Count of send parameters.
   */
  int getSntParamCount();
  
  
  /**
   * @param paramIndex - Index of the send parameter whose name we would like to 
   * read (0 being the first one)
   * @return - The name of a sent parameter
   */
  String getSntParamName(int paramIndex);
  
  
  /**
   * Gets the number of received parameters 
   * 
   * @return - Count of send parameters.
   */
  int getRcvParamCount();
 
  
  /**
   * Gets the name of a received parameter
   * 
   * @param paramIndex - Index of the receive parameter whose name we would like 
   * to read (0 being the first one)
   * @return - The name of a received parameter
   */
  String getRcvParamName(int paramIndex);
}
