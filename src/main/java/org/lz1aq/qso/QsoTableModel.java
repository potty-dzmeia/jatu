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
package org.lz1aq.qso;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 * Class used for inserting Qso objects into a table
 * 
 */
public class QsoTableModel extends AbstractTableModel
{
  private ArrayList<Qso> qsoList;
  
  public QsoTableModel(ArrayList<Qso> qsoList)
  {
    this.qsoList = qsoList;
  }
  
  
  @Override
  public int getRowCount()
  {
    return qsoList.size();
  }

  @Override
  public int getColumnCount()
  {
    Qso qso = qsoList.get(0); // We will use the first Qso from the list as an example
    return qso.getParamsCount();
  }

  @Override
  public Object getValueAt(int row, int col)
  {
    Qso qso = qsoList.get(row);
    return qso.getParam(col).value;
  }
  
  @Override
  public String getColumnName(int col)
  {
    Qso qso = qsoList.get(0); // We will use the first Qso from the list as an example
    return qso.getParam(col).name;
  }
  
  
  @Override
  public boolean isCellEditable(int row, int col)
  {
    return true;
  }
   
  @Override
  public void setValueAt(Object value, int row, int col)
  {
    Qso qso = qsoList.get(row); 
    qso.getParam(col).value = (String) value;
  }

}
