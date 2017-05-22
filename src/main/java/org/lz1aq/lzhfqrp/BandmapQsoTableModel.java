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
import org.lz1aq.utils.Misc;

/**
 *
 * @author potty
 */
public class BandmapQsoTableModel extends AbstractTableModel
{
  private final static int NUMBER_OF_COLUMNS = 16;
  private final static int NUMBER_OF_ROWS    = 30;
  private int startFreqInHz;
  private int stepInHz; 
  private ApplicationSettings appSettings;
  
  /** Reference to the Log */
  private final Log log;

  
  ArrayList<Qso> lastSpQsos;
  
  
  public BandmapQsoTableModel(Log log, int startFreq, int stepInHz, ApplicationSettings appSettings)
  {
    this.log = log;
    this.startFreqInHz = startFreq;
    this.stepInHz = stepInHz;
    this.appSettings = appSettings;
    
    lastSpQsos = log.getLastSpContacts();
  }

  
  @Override
  public int getRowCount()
  {
    return NUMBER_OF_ROWS;
  }

  @Override
  public int getColumnCount()
  {
    return NUMBER_OF_COLUMNS;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex)
  {
    StringBuilder cellText = new StringBuilder();
    
    
    // If frequency cell ...
    if(columnIndex%2 == 0)
    {
      cellText.append(Misc.toBandmapFreq(cellToFreq(rowIndex, columnIndex))); // Add the frequncy value
    }
    // If Callsign cell...
    else
    {
      //String value;
      
      //for each Manual Spor check if should be inserted here
      
      // for each last worked SP check if should be inserted here
      for(Qso qso: lastSpQsos)
      {
        if(isQsoInThisCell(rowIndex, columnIndex, qso))
        {
         if(appSettings.isQuickCallsignModeEnabled())
           cellText.append(Misc.toShortCallsign(qso.getHisCallsign(), appSettings.getDefaultPrefix()));
         else
           cellText.append(qso.getHisCallsign());
         
         cellText.append(" ");
        }
      }
      
      
    }
    
    return cellText.toString();
  }
  
  
  /**
   * Updates the content of the table.
   * @param appSettings
   */
  public synchronized void refresh(ApplicationSettings appSettings)
          //int allowedQsoRepeatPeriodInSec, int hideAfterSeconds, int maxEntriesCount)
  {
    this.appSettings = appSettings;
    
    lastSpQsos = log.getLastSpContacts();
    
    this.fireTableDataChanged();
  }
  
  /**
   * Used for getting the frequency represented by the cell. Could be e frequency cell or a callsign
   * cell.
   * 
   * @param row
   * @param column
   * @return - frequency in Hz
   */
  public int cellToFreq(int row, int column)
  {
    // Odd column - means a callsign is hold in the cell
    if(column%2 == 1)
    {
      return startFreqInHz+((row)*stepInHz)+((NUMBER_OF_ROWS/2)*(column-1)*stepInHz);
    }
    // Even - a frequency is hold in this cell
    else
    {
      return startFreqInHz+((row)*stepInHz)+((NUMBER_OF_ROWS/2)*column*stepInHz);
    }
  }
 
  
  /**
   * Checks if the supplied "freq" fits the cell frequency
   * @param row - cell row
   * @param col - cell column
   * @param freq - the frequency that we want to check if it is within the cell frequency
   * @return 
   */
  public boolean isCurrentFreqInThisCell(int row, int col, int freq)
  {
    int cellFreq = cellToFreq(row, col);
    
    return freq >= cellFreq && freq < cellFreq+stepInHz;
  }
  
  
  /**
   * Check is the frequency of the Qso fits the cell frequency
   * 
   * @param row
   * @param col
   * @param qso
   * @return 
   */
  private boolean isQsoInThisCell(int row, int col, Qso qso)
  {
    int cellFreq = cellToFreq(row, col);
    
    return (qso.getFrequencyInt() >= cellFreq) && (qso.getFrequencyInt() < cellFreq+stepInHz);
  }
  
  
  public boolean containsExpiredCallsign(int row, int col)
  {
    
    for(Qso qso: lastSpQsos)
    {    
      if(isQsoInThisCell(row, col, qso) && 
        (appSettings.getQsoRepeatPeriod()-log.getLastQso(qso.getHisCallsign()).getElapsedSeconds())<=0 )
        return true;
    }
    return false;
  }
  
  
  
//  /**
//   * Use this function to determine the position of the marker inside the bandmap table.
//   * 
//   * @param freq - the current frequency.
//   * @return - object describing which cells of the table should be highlighted
//   */
//  public BandmapMarkerLocation getBandmapMarkerLocation(int freq)
//  {
//    int delta = freq-startFreqInHz;
//    delta = delta/stepInHz;
//    
//    int column = delta/(NUMBER_OF_ROWS*2);
//    int row = delta%NUMBER_OF_ROWS;
//    
//    return new BandmapMarkerLocation(row, column); 
//  }
//  
//  
//  
//  public class BandmapMarkerLocation
//  {
//    private final int freqCell_row;
//    private final int freqCell_col;
//  
//    
//    public BandmapMarkerLocation(int freqCell_row, int freqCell_col)
//    {
//      this.freqCell_col = freqCell_col;
//      this.freqCell_row = freqCell_row;
//    }
//    
//    public int getFreq_row()
//    {
//      return freqCell_row;
//    }
//
//    public int getFreq_col()
//    {
//      return freqCell_col;
//    }
//    
//    public int getCallsign_row()
//    {
//      return freqCell_row;
//    }
//    
//    public int getCallsign()
//    {
//      return freqCell_col+1;
//    }
//  }
}
