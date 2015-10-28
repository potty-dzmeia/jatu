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
package org.lz1aq.jatu;

import com.db4o.*;
import java.util.ArrayList;
import org.lz1aq.log.Log;
import org.lz1aq.log.Qso;
import org.lz1aq.log.QsoParameter;
import org.lz1aq.utils.TimeUtils;


/**
 *
 * @author chavdar
 */
public class Main_test
{
   public static void main(String args[]) throws Exception
   {  
     
//      DateTimeFormatter drFormatter = DateTimeFormat.shortDateTime();
//      
//      DateTime dt = new DateTime(DateTimeZone.UTC);
//      System.out.println(drFormatter.print(dt));
//      System.out.println("-=====");
//      System.out.println(drFormatter.print(dt).toString());
//      System.out.println("-=====");
     

      // accessDb4o
     ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "test_params_only.db4o");
     try
     {
       ArrayList<QsoParameter> extraQsoParams = new ArrayList<>();
       for (int i = 0; i < 10000; i++)
       {

         extraQsoParams.add(new QsoParameter("date", "12-12-12"));
         extraQsoParams.add(new QsoParameter("time", "1111"));
         extraQsoParams.add(new QsoParameter("frequency", "14140000"));
         extraQsoParams.add(new QsoParameter("mode", "cw"));
         extraQsoParams.add(new QsoParameter("myCall", "lz1abc"));
         extraQsoParams.add(new QsoParameter("hisCall", "lz1aq"));
         extraQsoParams.add(new QsoParameter("sntRST", "599"));
         extraQsoParams.add(new QsoParameter("rcvRST", "599"));

       }
       db.store(extraQsoParams);

     } finally
     {
       db.close();
     }
     
     
//     Log log;
//     log = new Log();
//     for (int i = 0; i < 10000; i++)
//     {
//       ArrayList<QsoParameter> extraQsoParams = new ArrayList<>();
//       extraQsoParams.add(new QsoParameter("sntRST", "599"));
//       extraQsoParams.add(new QsoParameter("rcvRST", "599"));
//       Qso qso = new Qso(14190000, "cw", "lz1abc", "lz1aq", extraQsoParams);
//       log.add(qso);
//     }
//
//     try
//     {
//       db.store(log);
//     } finally
//     {
//       db.close();
//     }
     
     
   
   }
  
}
