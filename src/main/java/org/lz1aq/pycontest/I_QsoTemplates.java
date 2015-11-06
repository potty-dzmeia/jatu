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


/**
 * Defines QSO template(s) each having certain amount of send and 
 * receive parameters.
 * 
 * How to use:
 * (1) First Read the amount of QSO templates(definitions) using getTemplateCount()
 * (2) Then Read the send and receive parameters of each of the template(s) using 
 * get...ParamCount() and get...ParamName()
 * 
 */
public interface I_QsoTemplates
{
  /** 
   * @return The number of available QSO templates
   */
  int getTemplateCount();
  
  
  /**
   * Gets the name of the specified Qso template
   * 
   * @param templateIndex - Index of template whose name we would like to read (0 is being the first template)
   * @return The name of the QSO template
   */
  String getTemplateName(int templateIndex);
  
  
  /**
   * Gets the number of sent parameters for the selected QSO template
   * 
   * @param templateIndex -  Index of template (0 is being the first QSO template)
   * @return - Count of send parameters.
   */
  int getSntParamCount(int templateIndex);
  
  /**
   * Gets the name of a sent parameter for the selected QSO template
   * 
   * @param templateIndex - Index of template (0 is being the first QSO template)
   * @param paramIndex - Index of the send parameter whose name we would like to read
   * @return 
   */
  String getSntParamName(int templateIndex, int paramIndex);
  
  
  /**
   * Gets the number of received parameters for the selected QSO template
   * 
   * @param templateIndex -  Index of template (0 is being the first QSO template)
   * @return - Count of send parameters.
   */
  int getRcvParamCount(int templateIndex);
  
  
  /**
   * Gets the name of a received parameter for the selected QSO template
   * 
   * @param templateIndex - Index of template (0 is being the first QSO template)
   * @param paramIndex - Index of the receive parameter whose name we would like to read
   * @return 
   */
  String getRcvParamName(int templateIndex, int paramIndex);
  
}
