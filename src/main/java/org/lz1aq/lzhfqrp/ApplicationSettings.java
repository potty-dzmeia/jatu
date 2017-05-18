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

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Chavdar
 */
public final class ApplicationSettings 
{
    static final String SETTINGS_FILE_NAME            = "settings.properties";
    
    static final String PROPERTY_COMPORT              = "comPort";
    static final String PROPERTY_MY_CALL_SIGN         = "myCallSign";
    static final String PROPERTY_QUICK_CALLSIGN_MODE  = "quickCallsignMode";  
    static final String PROPERTY_DEFAULT_PREFIX       = "defaultPrefix";
    static final String PROPERTY_QSO_REPEAT_PERIOD_SEC = "qsoRepeatPeriod";
    static final String PROPERTY_MAIN_WINDOW_X        = "x";
    static final String PROPERTY_MAIN_WINDOW_Y        = "y";
    static final String PROPERTY_MAIN_WINDOW_WIDTH    = "w";
    static final String PROPERTY_MAIN_WINDOW_HEIGHT   = "h";
    static final String PROPERTY_FUNCTION_KEYS        = "function_key";
    
    public static final int FUNCTION_KEYS_COUNT = 12; // The number of function keys

    private String             comPort;
    private String             myCallsign;
    private boolean            isQuickCallsignModeEnabled;
    private String             defaultPrefix;
    private String             qsoRepeatPeriod;
    private Rectangle          jFrameDimensions; // JFrame settings: position and size
    private final String[]     arrayFunctionKeysTexts;  // texts for the function keys
    
    private final Properties   prop;
    
    /**
     * Tries to read the settings from the disk. If it fails default values are used.
     */
    public ApplicationSettings()
    {     
        this.prop         = new Properties();
        jFrameDimensions  = new Rectangle();
        arrayFunctionKeysTexts  = new String[FUNCTION_KEYS_COUNT];
        
        this.LoadSettingsFromDisk();
    }

    
    public Rectangle getJFrameDimensions()
    {
        return jFrameDimensions;
    }

    public void setJFrameDimensions(Rectangle jFrameDimensions)
    {
        this.jFrameDimensions = jFrameDimensions;
    }

    public String getComPort()
    {
        return comPort;
    }

    public void setComPort(String comPort)
    {
        this.comPort = comPort;
    }

    
    public String getMyCallsign()
    {
      return this.myCallsign;
    }
    
    public void setMyCallsign(String callsign)
    {
      this.myCallsign = callsign;
    }
  
    /**
     * This setting is not saved to a file (also it is initialized to 0 on object
     * creation)
     * 
     * @param isEnabled True is single element mode is enabled
     */
    public void setQuickCallsignMode(boolean isEnabled)
    {
      this.isQuickCallsignModeEnabled = isEnabled;
    }
    
    /**
     * This setting is not saved to a file (also it is initialized to 0 on object
     * creation)
     * 
   * @return True is single element mode is enabled
     */
    public boolean isQuickCallsignModeEnabled()
    {
      return this.isQuickCallsignModeEnabled;
    }
    
    public String getDefaultPrefix()
    {
      return this.defaultPrefix;
    }
    public void setDefaultPrefix(String prefix)
    {
      this.defaultPrefix = prefix;
    }
    
    /**
     * Get the allowed repeat period for Qso in seconds
     * @return 
     */
    public int getQsoRepeatPeriod()
    {
      return Integer.parseInt(this.qsoRepeatPeriod);
    }
  
    /**
     * Set the allowed repeat period for Qso in seconds
     * @param periodInSeconds 
     */
    public void setQsoRepeatPeriod(int periodInSeconds)
    {
      this.qsoRepeatPeriod = Integer.toString(periodInSeconds);
    }
    
    
    /**
     * Returns the text for the desired function key
     * 
     * @param keyIndex - Index 0 is for the F1 key, 1 for F2 and so on...
     * @return 
     */
    public String getFunctionKeyText(int keyIndex)
    {
        return arrayFunctionKeysTexts[keyIndex];
    }
    
    
    /**
     * Sets the text for the desired function key
     * @param keyIndex - Index 0 is for the F1 key, 1 for F2 and so on...
     * @param text - the text that will be set for the desired function key
     */
    public void setFunctionKeyText(int keyIndex, String text)
    {
        arrayFunctionKeysTexts[keyIndex] = text;
    }
    
    
    /**
     * Stores the array of values into properties which are named using
     * key+index of the value
     * 
     * @param key - property key that will be used for writing this property
     * @param values - array of values that will be written
     */
    private void setProperties(String key, String[] values)
    {
      for(int i=0; i<values.length; i++)
      {
        prop.setProperty(key+i, values[i]);
      }
    }
    
    
    private void getProperties(String key, String[] values)
    {
      for(int i=0; i<values.length; i++)
      {
        values[i] = prop.getProperty(key+i);
      }
    }
    
    
    /**
     * Saves the settings into a file called "DLineSettings.properties"
     */
    public void SaveSettingsToDisk()
    {
        prop.setProperty(PROPERTY_COMPORT, comPort);
        prop.setProperty(PROPERTY_MY_CALL_SIGN, myCallsign);
        prop.setProperty(PROPERTY_QUICK_CALLSIGN_MODE, Boolean.toString(isQuickCallsignModeEnabled));
        prop.setProperty(PROPERTY_DEFAULT_PREFIX, defaultPrefix);
        prop.setProperty(PROPERTY_QSO_REPEAT_PERIOD_SEC, qsoRepeatPeriod);
        
        // Now save the texts for the function keys
        setProperties(PROPERTY_FUNCTION_KEYS, arrayFunctionKeysTexts);
        
        // Now save the JFrame dimensions:
        prop.setProperty(PROPERTY_MAIN_WINDOW_X, Integer.toString(jFrameDimensions.x));
        prop.setProperty(PROPERTY_MAIN_WINDOW_Y, Integer.toString(jFrameDimensions.y));
        prop.setProperty(PROPERTY_MAIN_WINDOW_WIDTH, Integer.toString(jFrameDimensions.width));
        prop.setProperty(PROPERTY_MAIN_WINDOW_HEIGHT, Integer.toString(jFrameDimensions.height));
        
        try
        {
            prop.store(new FileOutputStream(SETTINGS_FILE_NAME), null);
        } catch (IOException ex)
        {
            Logger.getLogger(ApplicationSettings.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
    }
    
    /**
     * Loads the settings from a settings file
     */
    public void LoadSettingsFromDisk()
    {
        try
        {
            
            prop.load(new FileInputStream(SETTINGS_FILE_NAME));
            
            // Comport
            comPort  = prop.getProperty(PROPERTY_COMPORT);
            if(comPort == null)
                throwMissingPropertyException(PROPERTY_COMPORT);
                
            // My callsign
            myCallsign = prop.getProperty(PROPERTY_MY_CALL_SIGN);
            if(myCallsign == null)
                throwMissingPropertyException(PROPERTY_MY_CALL_SIGN);
            
            // Now read the texts for the function keys
            getProperties(PROPERTY_FUNCTION_KEYS, arrayFunctionKeysTexts);
            for(String str : arrayFunctionKeysTexts)
            {
              if(str == null)
                throwMissingPropertyException(PROPERTY_FUNCTION_KEYS);
            }
            
            // Quick callsign mode
            String temp = prop.getProperty(PROPERTY_QUICK_CALLSIGN_MODE);
            if(temp == null)
                throwMissingPropertyException(PROPERTY_QUICK_CALLSIGN_MODE);
            isQuickCallsignModeEnabled = Boolean.parseBoolean(temp);
            
            // Default prefix
            defaultPrefix = prop.getProperty(PROPERTY_DEFAULT_PREFIX);
            if(defaultPrefix == null)
              throwMissingPropertyException(PROPERTY_DEFAULT_PREFIX);
            
            // Repeat period for Qso
            qsoRepeatPeriod = prop.getProperty(PROPERTY_QSO_REPEAT_PERIOD_SEC);
             if(qsoRepeatPeriod == null)
              throwMissingPropertyException(PROPERTY_QSO_REPEAT_PERIOD_SEC);
            
            
            
            // Read the JFrame dimensions:
            int x = Integer.parseInt(prop.getProperty(PROPERTY_MAIN_WINDOW_X));
            int y = Integer.parseInt(prop.getProperty(PROPERTY_MAIN_WINDOW_Y));
            int w = Integer.parseInt(prop.getProperty(PROPERTY_MAIN_WINDOW_WIDTH));
            int h = Integer.parseInt(prop.getProperty(PROPERTY_MAIN_WINDOW_HEIGHT));
            
            this.jFrameDimensions = new Rectangle(x,y,w,h);
        } catch (IOException ex)
        {
            // If some error we will set to default values
            this.SetSettingsToDefault();
            Logger.getLogger(ApplicationSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (NumberFormatException ex)
        {
            // If some error we will set to default values
            this.SetSettingsToDefault();
            Logger.getLogger(ApplicationSettings.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (Exception ex)
        {
            this.SetSettingsToDefault();
            Logger.getLogger(ApplicationSettings.class.getName()).log(Level.SEVERE, null, ex);    
        }
                    
    }
    
    /**
     * Set all settings to default
     */
    private void SetSettingsToDefault()
    {
        comPort = "";
        myCallsign = "LZ1ABC";
        isQuickCallsignModeEnabled = false;     
        defaultPrefix = "LZ0";
        qsoRepeatPeriod = "30";
        
        // Set texts for the direction buttons
        arrayFunctionKeysTexts[0]  = "test {mycall}";       // F1
        arrayFunctionKeysTexts[1]  = "not defined by user"; // F2
        arrayFunctionKeysTexts[2]  = "tu";                  // F3
        arrayFunctionKeysTexts[3]  = "not defined by user";
        arrayFunctionKeysTexts[4]  = "not defined by user";
        arrayFunctionKeysTexts[5]  = "agn";
        arrayFunctionKeysTexts[6]  = "?";
        arrayFunctionKeysTexts[7]  = "dupe";
        arrayFunctionKeysTexts[8]  = "";    
        arrayFunctionKeysTexts[9]  = "";
        arrayFunctionKeysTexts[10]  = "not defined by user";
        arrayFunctionKeysTexts[11]  = "not defined by user";
        
        
        // We have minimum size so we don't have to worry about the values:
        jFrameDimensions.height = 0;
        jFrameDimensions.width = 0;
        jFrameDimensions.x = 0;
        jFrameDimensions.y = 0;            
    }
    
    void throwMissingPropertyException(String propertyName) throws Exception
    {
      throw new Exception("Error when trying to read element " + propertyName + " from file " + SETTINGS_FILE_NAME);
    }
}

