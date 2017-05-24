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
import java.util.List;
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
  private int startFreqInHz;
  private ApplicationSettings appSettings;
  
  /** Reference to the Log */
  private final Log log;

  
  ArrayList<Qso> lastSpQsos;
  List<BandmapSpot> manualSpots= new ArrayList();
  
  
  public BandmapQsoTableModel(Log log, int startFreq, ApplicationSettings appSettings)
  {
    this.log = log;
    this.startFreqInHz = startFreq;
    this.appSettings = appSettings;
    
    lastSpQsos = log.getLastSpContacts();
  }

  
  @Override
  public int getRowCount()
  {
    return appSettings.getBandmapRowCount();
  }

  @Override
  public int getColumnCount()
  {
    return appSettings.getBandmapColumnCount();
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex)
  {
    CellBuilder cell = new CellBuilder(appSettings);
    String temp;
    boolean isHtml = false; // if we need to enter HTML text
    
   
    // If frequency cell ...
    // ---------------------
    if(columnIndex%2 == 0)
      return Misc.toBandmapFreq(cellToFreq(rowIndex, columnIndex));
    
    
    
    // If Callsign cell...
    // ---------------------
      
    // Last worked SP
    for (Qso qso : lastSpQsos)
    {
      if (isQsoInThisCell(rowIndex, columnIndex, qso))
      {
        if(qso.isDupe(appSettings.getQsoRepeatPeriod()))
        {
          
        }
        
                
        if (appSettings.isQuickCallsignModeEnabled())
        {
          temp = Misc.toShortCallsign(qso.getHisCallsign(), appSettings.getDefaultPrefix());
        } else
        {
          temp = qso.getHisCallsign();
        }

        // Blue color if it needs to be worked  
        if ((appSettings.getQsoRepeatPeriod() - qso.getElapsedSeconds()) <= 0)
        {
          isHtml = true;
          cellText.append("<b><font color=blue>").append(temp).append("</b></font>");
        } else
        {
          cellText.append(temp);
        }
        cellText.append(" ");
      }
    }
    
    
    // Manual Spots
    for (BandmapSpot spot : manualSpots)
    {
      if (isCurrentFreqInThisCell(rowIndex, columnIndex, spot.getFreq()))
      {
        if (isExpired(spot.getCallsign()))
        {
          if (appSettings.isQuickCallsignModeEnabled())
          {
            temp = Misc.toShortCallsign(spot.getCallsign(), appSettings.getDefaultPrefix());
          }
          
          isHtml = true;
          cellText.append("*").append("<b><font color=blue>").append(spot.getCallsign()).append("</b></font>");
        } else
        {
          if (appSettings.isQuickCallsignModeEnabled())
          {
            temp = Misc.toShortCallsign(spot.getCallsign(), appSettings.getDefaultPrefix());
          }
          cellText.append("*").append(spot.getCallsign());
        }
        
        cellText.append(" ");
      }
    }
     
    
    if(isHtml)
    {
      cellText.insert(0, "<html>");
      cellText.append("</html>");
    }
    return cellText.toString();
  }
  
  
  private class CellBuilder
  {
    StringBuilder cellText = new StringBuilder();
    ApplicationSettings appSettings;
    boolean bIsHtml = false;
    
    
    public CellBuilder(ApplicationSettings appSettings)
    {
      this.appSettings = appSettings;
    }
    
    void addBalckCallsign
    void add
            
            
  }
  
  
  public void addSpot(String callsign, int freq)
  {
    manualSpots.add(new BandmapSpot(callsign, freq));
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
    
    //this.fireTableDataChanged();
    this.fireTableStructureChanged();
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
      return startFreqInHz+((row)*appSettings.getBandmapStepInHz())+((appSettings.getBandmapRowCount()/2)*(column-1)*appSettings.getBandmapStepInHz());
    }
    // Even - a frequency is hold in this cell
    else
    {
      return startFreqInHz+((row)*appSettings.getBandmapStepInHz())+((appSettings.getBandmapRowCount()/2)*column*appSettings.getBandmapStepInHz());
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
    
    return freq >= cellFreq && freq < cellFreq+appSettings.getBandmapStepInHz();
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
    
    return (qso.getFrequencyInt() >= cellFreq) && (qso.getFrequencyInt() < cellFreq+appSettings.getBandmapStepInHz());
  }
  
  
//  public boolean containsExpiredCallsign(int row, int col)
//  {
//    
//    for(Qso qso: lastSpQsos)
//    {    
//      if(isQsoInThisCell(row, col, qso) && 
//        (applicationSettings.getQsoRepeatPeriod()-log.getLastQso(qso.getHisCallsign()).getElapsedSeconds())<=0 )
//        return true;
//    }
//    return false;
//  }
//  
  
  private boolean isExpired(Qso qso)
  {
    return appSettings.getQsoRepeatPeriod()-qso.getElapsedSeconds()<=0;
  }
  
  private boolean isExpired(String callsign)
  {
    return log.isDupe(callsign, appSettings.getQsoRepeatPeriod());
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
