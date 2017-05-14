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

import org.lz1aq.rsi.event.ConfirmationEvent;
import org.lz1aq.rsi.event.FrequencyEvent;
import org.lz1aq.rsi.event.ModeEvent;
import org.lz1aq.rsi.event.NotsupportedEvent;
import org.lz1aq.rsi.event.RadioListener;
import org.lz1aq.rsi.event.SmeterEvent;
import org.lz1aq.utils.Misc;
import org.lz1aq.utils.RadioVfos;

/**
 *
 * @author potty
 * 
 * Represents the 
 */
public class RadioController
{
//    int vfoFreq = 14000000;
//
//    public RadioController()
//    {
//        
//    }
//    
//    void connect(String filename, String commPort)
//    {
//        
//    }
//    int getFrequency()
//    {
//        return freq;
//    }
//   
//    
//    void setFrequency(int freq)
//    {
//        
//    }
//    
//    
//    /**
//   * Handlers for events coming from the radio
//   */
//  private class LocalRadioListener implements RadioListener
//  {
//    @Override
//    public void eventNotsupported(NotsupportedEvent e){} // not interested
//
//    @Override
//    public void eventConfirmation(ConfirmationEvent e){} // not interested
//
//    @Override
//    public void eventFrequency(final FrequencyEvent e)
//    {
//      /* Create and display the form */
//      java.awt.EventQueue.invokeLater(new Runnable()
//      {
//        @Override
//        public void run()
//        {
//          if(e.getVfo() == RadioVfos.A)
//            frequencyATextfield.setText(Misc.formatFrequency(e.getFrequency()));
//          else if(e.getVfo() == RadioVfos.B)
//            frequencyBTextfield.setText(Misc.formatFrequency(e.getFrequency()));
//          else
//          {
//            frequencyATextfield.setText(Misc.formatFrequency(e.getFrequency()));
//            logger.warning("Frequency event from unknown VFO!");
//          }
//            
//        }
//      });
//    }
//
//    @Override
//    public void eventMode(final ModeEvent e)
//    {
//       /* Create and display the form */
//      java.awt.EventQueue.invokeLater(new Runnable()
//      {
//        @Override
//        public void run()
//        {
//          if(e.getVfo() == RadioVfos.A)
//            modeATextfield.setText(e.getMode().toString());
//          else if(e.getVfo() == RadioVfos.B)
//            modeBTextfield.setText(e.getMode().toString());
//          else
//          {
//            modeATextfield.setText(e.getMode().toString());
//            logger.warning("Mode event from unknown VFO!");
//          }
//          
//        }
//      });
//    }
//    
//    @Override
//    public void eventSmeter(final SmeterEvent e)
//    {
//      /* Create and display the form */
//      java.awt.EventQueue.invokeLater(new Runnable()
//      {
//        @Override
//        public void run()
//        {
//          meterProgressBar.setValue(e.getValue());
//        }
//      });
//    }
//  }
}
