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
  public  static final String DEFAULT_CALLSIGN_PREFIX = "LZ0";
  
  private final LogDatabase db;         // Interface to a db4o database, stand-alone or client/server. 
  private final ArrayList<Qso> qsoList; // Log is also mirrored in RAM
  private final Qso   exampleQso;
  
  
  /**
   *
   * 
   * @param db interface to an already opened database
   * @param exampleQso - here we must input an example Qso from which the log 
   * will determine the column count and their names.
   */
  public Log(LogDatabase db, Qso exampleQso)
  {
    this.db = db;
    qsoList = new ArrayList<> (db.getAll()); // Load Qsos from the database
    
    this.exampleQso = exampleQso;
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
    db.commit();
  }

  
  /**
   * Method for accessing QSOs inside the log.
   * 
   * @param index - Qso index inside the log (0 is being the first QSO in the log)
   * @return  Reference to the QSO object
   */
  public Qso get(int index)
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
    db.commit();
  }
  
  
  /**
   * Row count is equivalent to the count of QSO object contained in the Log.
   * 
   * @return Returns the amount of QSO objects contained in the Log.
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
   * @return The number of columns inside the log. If log is empty the return 
   * value will be 0.
   */
  public int getColumnCount()
  {
    return exampleQso.getParamsCount();
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
    return exampleQso.getParamName(col);
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
    return qso.getParamValue(col);
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
    db.commit();
  }
  
  public int getQsoCount()
  {
      return qsoList.size();
  }

  
  /**
   * Returns the serial number that we must send for the next Qso
   * 
   * @return string of the type 010 020. First part is the serial number of the Qso.
   * Second part is the first three figures of received report during previous Qso.
   * Report for the  first Qso is 001 000.
   */
  public String getNextSentReport()
  {
    // If the log is empty send "001000"
    if(getRowCount() == 0)
    {
      return "001000";
    }
    // Else send serial number + first three digits of received report during previous Qso
    else
    {
      String part1 = String.format("%03d", getRowCount()+1);
      
      Qso qso = qsoList.get(qsoList.size()-1);
      String part2 = qso.getRcv().substring(0, 3);
      
      return part1+part2;
    }
  }
 
  
  
  /**
   * Return the last Qso that we had with this station
   * @param callsign station callsign
   * @return Qso object
   */
  public Qso getLastQso(String callsign)
  {
    //find last Qso with this station
    for (int i = qsoList.size() - 1; i >= 0; i--)
    {
      if (callsign.equalsIgnoreCase(qsoList.get(i).getHisCallsign()))
      {
        return qsoList.get(i);
      }
    }
    
    return null;
  }
  
  
  /**
   * Returns time left till the next possible contact
   * 
   * @param qso
   * @param allowedQsoRepeatPeriod - the repeat period for another qso with the same station (in seconds)
   * @return
   */
  public long getSecondsLeft(Qso qso, int allowedQsoRepeatPeriod)
  {
    return allowedQsoRepeatPeriod-qso.getElapsedSeconds();
  }
  
  /**
   * Same as getSecondsLeft but returns the seconds left in the following format mm:ss
   * 
   * @param qso
   * @param allowedQsoRepeatPeriod  - the repeat period for another qso with the same station (in seconds)
   * @return 
   */
  public String getTimeLeftFormatted(Qso qso, int allowedQsoRepeatPeriod)
  {
    long secondsleft = getSecondsLeft(qso, allowedQsoRepeatPeriod);

    long second = secondsleft % 60;
    long minute = (secondsleft / 60) % 60;
    String timeleft = String.format("%02d:%02d", minute, second);
    
    return timeleft;
  }
  
  
  /**
   * Checks if it is OK to work the station
   * 
   * @param callsign
   * @param allowedQsoRepeatPeriod - the repeat period for another qso with the same station (in seconds)
   * @return true if the required time has not elapsed
   */
  public boolean isDupe(String callsign, int allowedQsoRepeatPeriod)
  {
    Qso qso = getLastQso(callsign);
    
    // If there is no previous contact with this station
    if(qso == null)
    {
      return false;
    }
    
    //Prvious contact was found...
    long secondsLeft = getSecondsLeft(qso, allowedQsoRepeatPeriod);
    
    return secondsLeft > 0;
  }
 
  
}