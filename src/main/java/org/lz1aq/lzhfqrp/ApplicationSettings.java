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
    static final String PROPERTY_DEFAULT_PREFIX       = "LZ0";
    static final String PROPERTY_MAIN_WINDOW_X        = "x";
    static final String PROPERTY_MAIN_WINDOW_Y        = "y";
    static final String PROPERTY_MAIN_WINDOW_WIDTH    = "w";
    static final String PROPERTY_MAIN_WINDOW_HEIGHT   = "h";

    private String             comPort;
    private String             myCallsign;
    private boolean            isQuickCallsignModeEnabled;
    private String             defaultPrefix;
    private Rectangle          jFrameDimensions; // JFrame settings: position and size
    
    private final Properties   prop;
    
    /**
     * Tries to read the settings from the disk. If it fails default values are used.
     */
    public ApplicationSettings()
    {     
        this.prop         = new Properties();
        jFrameDimensions  = new Rectangle();

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
            
            // Quick callsign mode
            String temp = prop.getProperty(PROPERTY_QUICK_CALLSIGN_MODE);
            if(temp == null)
                throwMissingPropertyException(PROPERTY_QUICK_CALLSIGN_MODE);
            isQuickCallsignModeEnabled = Boolean.parseBoolean(temp);
            
            // Default prefix
            defaultPrefix = prop.getProperty(PROPERTY_DEFAULT_PREFIX);
            if(defaultPrefix == null)
              throwMissingPropertyException(PROPERTY_DEFAULT_PREFIX);
            
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

