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
import org.lz1aq.pycontest.I_Log;
import org.lz1aq.pycontest.I_Qso;



/**
 * This class is a container for Qso objects. 
 * 
 * We can think of the Log as a table:
 * - The number of columns is equal to the number of parameters of the first Qso
 *   object in the log.
 * - The number of rows is equal to the number of Qso objects contained in the Log.
 * 
 */
public class Log implements I_Log
{
  private final LogDatabase db;  // the interface to a db4o database, stand-alone or client/server. 
  private final ArrayList<Qso> qsoList;
  
  
  /**
   *
   * 
   * @param db interface to a already opened database
   */
  public Log(LogDatabase db)
  {
    this.db = db;
    qsoList = new ArrayList<> (db.getAll()); // Load Qsos from the database
  }
  
  
  /**
   * Inserts a QSO object inside the log.
   * 
   * No two references pointing to the same objects should be inserted to the 
   * log as this will case a miss-alignment with the database.
   * 
   * @param qso - Reference to a new Qso object. Insert only newly created Qso
   * objects.
   */
  public void add(Qso qso)
  {
    db.add(qso);      // Add the qso to the database
    qsoList.add(qso); // Add the qso to RAM (i.e local list)
  }

  
  /**
   * Method for accessing QSOs inside the log.
   * 
   * @param index - Qso index inside the log (0 is being the first QSO in the log)
   * @return  Reference to the QSO object
   */
  @Override
  public I_Qso get(int index)
  {
    return qsoList.get(index);
  }
  
  
  /**
   * Removes a QSO object from the log.
   * 
   * @param index Index of the Qso object to be removed
   */
  public void remove(int index)
  {
    db.remove(qsoList.get(index)); // Remove the qso from the RAM (i.e local list)
    qsoList.remove(index);         // Remove the qso from the database
  }
  
  
  /**
   * Row count is equivalent to the count of QSO object contained in the Log.
   * 
   * @return Returns the amount of QSO objects contained in the Log.
   */
  @Override
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
   * @return The number of columns inside the log. If log is empty the return 
   * value will be 0.
   */
  @Override
  public int getColumnCount()
  {
    if(qsoList.isEmpty())
      return 0;
    
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
  @Override
  public String getValueAt(int row, int col)
  {
    Qso qso = qsoList.get(row);
    return qso.getParamValue(col);
  }
  
  
  /**
   * Returns the column name of the log which is equivalent to a Qso parameter
   * name.
   * 
   * @param col Column index of which we would like to get the name
   * @return Name of the column (i.e. name of the Qso param)
   */
  @Override
  public String getColumnName(int col)
  {
    Qso qso = qsoList.get(0); // We will use the first Qso from the list as a prototype
    return qso.getParamName(col);
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
    qso.setParamValue(col,value); // Update 
    db.modify(qso); // Update the database
  }
  
  
  /**
   * Commits all not committed changes to the database
   */
  public void writeToDB()
  {
    db.commit();
  }
 
  
}