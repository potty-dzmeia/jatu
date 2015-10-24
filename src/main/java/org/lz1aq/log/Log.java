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
package org.lz1aq.log;

import java.util.ArrayList;

/**
 * This class is a container for Qso objects. 
 * 
 * We can think of the Log as a table:
 * - The number of columns is equal to the number of parameters of the first Qso
 *   object in the log.
 * - The number of rows is equal to the number of Qso objects contained in the Log.
 * 
 */
public class Log
{
  private ArrayList<Qso> qsoList;
  
  
  /**
   * After object creation the first method to be called must be add()! This
   * is so because the Log needs to know the amount of columns that it will be
   * working with.
   */
  public Log()
  {
    qsoList = new ArrayList<>(0);
  }
  
  
  /**
   * Inserts a QSO object inside the log. This must be the first method called
   * after Log creation.
   * 
   * @param qso - Reference to a Qso object.
   */
  public void add(Qso qso)
  {
    qsoList.add(qso);
  }
  
  
  /**
   * Removes a QSO object from the log.
   * 
   * @param index Index of the Qso object to be removed
   */
  public void remove(int index)
  {
    qsoList.remove(index);
  }
  
  
  /**
   * Row count is equivalent to the count of Qso object contained in the Log.
   * 
   * @return Returns the amount of Qso objects contained in the Log.
   */
  public int getRowCount()
  {
    return qsoList.size();
  }

 
  /**
   * The number of columns inside the Log are equal to the number of parameters
   * of the first Qso that was added to the log.
   * 
   * For example:
   * (1) We create the Log object.
   * (2) We add() a Qso which has 7 parameters (i.e. Qso.getParamsCount() == 7)
   * (3) Then we add a second Qso which has 8 parameters 
   * (4) If now we call the method getColumnCount() it will return 7
   * 
   * @return The number of columns inside the log
   */
  public int getColumnCount()
  {
    Qso qso = qsoList.get(0); // Use the first Qso from the list as a prototype
    return qso.getParamsCount();
  }
  

  /**
   * Returns the value from the specified cell.
   * 
   * In other words this returns the parameter value of a certain Qso.
   * 
   * @param row Row index (i.e. Qso index).
   * @param col Column index (i.e. index of Qso param)
   * @return 
   */
  public String getValueAt(int row, int col)
  {
    Qso qso = qsoList.get(row);
    return qso.getParam(col).value;
  }
  
  
  /**
   * Returns the column name of the log which is equivalent to a Qso parameter
   * name.
   * 
   * @param col Column index of which we would like to get the name
   * @return Name of the column (i.e. name of the Qso param)
   */
  public String getColumnName(int col)
  {
    Qso qso = qsoList.get(0); // We will use the first Qso from the list as an example
    return qso.getParam(col).name;
  }
   
  
  /**
   * Sets the value of the specified Cell.
   * 
   * In other words this sets the parameter of a certain Qso to the specified value.
   * 
   * @param value - Value to be set.
   * @param row - Row index. This is equivalent to a Qso index.
   * @param col - Column index. This is equivalent to a Qso parameter index
   */
  public void setValueAt(String value, int row, int col)
  {
    Qso qso = qsoList.get(row); 
    qso.getParam(col).value = value;
  }
 
  
}