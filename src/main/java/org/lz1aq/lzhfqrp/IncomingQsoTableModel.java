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
package org.lz1aq.lzhfqrp;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.table.AbstractTableModel;
import org.lz1aq.log.Log;
import org.lz1aq.log.Qso;

/**
 *
 * @author potty
 */
public class IncomingQsoTableModel extends AbstractTableModel
{
  private final static int NUMBER_OF_COLUMNS = 4;
  
  
  /** Reference to the Log */
  private final Log log;
  /** This will hold all the unique callsigns that have been worked */
  private ArrayList<IncomingQso> incomingQsoArrayList; 
  
  
  
  public IncomingQsoTableModel(Log log)
  {
    incomingQsoArrayList = new ArrayList<>(0);
    this.log = log;
  }


  @Override
  public int getRowCount()
  {
    return incomingQsoArrayList.size();
  }

  @Override
  public int getColumnCount()
  {
    return NUMBER_OF_COLUMNS;
  }
  
  
   @Override
  public String getColumnName(int col)
  {
    switch(col)
      {
        case 0:
          return "hisCall";
        case 1:
          return "type";
        case 2:
          return "freq";
        case 3:
          return "time left";
        default:
          return "should not be used";
      }
  }
  

  @Override
  public Object getValueAt(int rowIndex, int columnIndex)
  {
    switch(columnIndex)
    {
      case 0:
        return incomingQsoArrayList.get(rowIndex).hisCall;
      case 1:
        return incomingQsoArrayList.get(rowIndex).typeOfWork;
      case 2: 
        return incomingQsoArrayList.get(rowIndex).frequency;
      case 3: 
        return incomingQsoArrayList.get(rowIndex).timeLeftFormatted;
      default:
          return "should not happen";
    }
    
  }
  
  @Override
  public boolean isCellEditable(int row, int col)
  {
    return false;
  }
  
  
  /**
   * Updates the content of the table.
   */
  public synchronized void refresh(int allowedQsoRepeatPeriodInSec, int hideAfterSeconds, int maxEntriesCount)
  {
    ArrayList<String> callsigns = log.getUniqueCallsigns();
    
    incomingQsoArrayList = new ArrayList<>(0);
    
    // For each callsign insert the last qso with this station. 
    for(int i=0; i<callsigns.size(); i++)
    {
      if(incomingQsoArrayList.size() >= maxEntriesCount)
        break;
      Qso lastQso = log.getLastQso(callsigns.get(i));
      if(log.getSecondsLeft(lastQso, allowedQsoRepeatPeriodInSec) < hideAfterSeconds)
        continue;
      IncomingQso incoming = new IncomingQso(lastQso.getHisCallsign(), 
                                             lastQso.getType(), 
                                             lastQso.getFrequency(), 
                                             lastQso.getElapsedSeconds(),
                                             log.getTimeLeftFormatted(lastQso, allowedQsoRepeatPeriodInSec));
              
      incomingQsoArrayList.add(incoming);
      
    }
    
    // Now order the array starting from the Qso with the highest value for elapsed time 
    Collections.sort(incomingQsoArrayList);
    
    this.fireTableDataChanged();
  }
  
  
  
  
  public class IncomingQso implements Comparable<IncomingQso>
  {
    private String  hisCall;
    private String  typeOfWork; // CQ or SP
    private String  frequency;
    private String  timeLeftFormatted;
    private long    elapsedSeconds; // How many seconds before being able to work the station again

   
    
    public IncomingQso(String hisCall, String typeOfWork, String freq, long secondsElapsed, String timeLeftFormatted)
    {
      this.hisCall = hisCall;
      this.typeOfWork = typeOfWork;
      this.frequency = freq;
      this.elapsedSeconds = secondsElapsed;
      this.timeLeftFormatted = timeLeftFormatted;
    }
   
   
    
    public String getHisCall()
    {
      return hisCall;
    }

    public String getTypeOfWork()
    {
      return typeOfWork;
    }

    public String getFrequency()
    {
      return frequency;
    }

    

    public void setHisCall(String hisCall)
    {
      this.hisCall = hisCall;
    }

    public void setTypeOfWork(String typeOfWork)
    {
      this.typeOfWork = typeOfWork;
    }

    public void setFrequency(String frequency)
    {
      this.frequency = frequency;
    }

    public void setElapsedSeconds(long elapsedSeconds)
    {
      this.elapsedSeconds = elapsedSeconds;
    }

    public void setTimeLeftFormatted(String timeLeftFormatted)
    {
      this.timeLeftFormatted = timeLeftFormatted;
    }

    public long getElapsedSeconds()
    {
      return elapsedSeconds;
    }

    public String getTimeLeftFormatted()
    {
      return timeLeftFormatted;
    }

    
    @Override
    public int compareTo(IncomingQso o)
    {
      if(this.elapsedSeconds < o.elapsedSeconds)
        return 1;
      else if(this.elapsedSeconds == o.elapsedSeconds)
        return 0;
      else
        return -1;
    }

   
  }

  
  
}
