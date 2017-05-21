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

/**
 *
 * @author potty
 */
public class BandmapSpot
{

  final String callsign;
  final int freq;
  final boolean isManualSpot;

  public BandmapSpot(String callsign, int freq, boolean isManualSpot)
  {
    this.callsign = callsign;
    this.freq = freq;
    this.isManualSpot = isManualSpot;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj == null)
    {
      return false;
    }
    if (!BandmapSpot.class.isAssignableFrom(obj.getClass()))
    {
      return false;
    }
    final BandmapSpot other = (BandmapSpot) obj;
    if ((this.callsign == null) ? (other.callsign != null) : !this.callsign.equals(other.callsign))
    {
      return false;
    }
    if (this.isManualSpot != other.isManualSpot)
    {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode()
  {
    int hash = 3;
    hash = 53 * hash + (this.callsign != null ? this.callsign.hashCode() : 0);
    hash = 53 * hash + ((this.isManualSpot) ? 1 : 0);
    return hash;
  }

}
