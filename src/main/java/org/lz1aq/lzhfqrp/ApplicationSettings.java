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

  static final String SETTINGS_FILE_NAME = "settings.properties";

  static final String PROPERTY_COMPORT = "com_port";
  static final String PROPERTY_MY_CALL_SIGN = "my_callsign";
  static final String PROPERTY_QUICK_CALLSIGN_MODE = "quick_callsign_mode";
  static final String PROPERTY_DEFAULT_PREFIX = "default_prefix";
  static final String PROPERTY_QSO_REPEAT_PERIOD_SEC = "qso_repeat_period";
  static final String PROPERTY_INCOMING_QSO_MAX_ENTRIES = "incoming_qso_max_entries";
  static final String PROPERTY_INCOMING_QSO_HIDE_AFTER = "incoming_qso_hide_after";
  static final String PROPERTY_FUNCTION_KEYS = "function_keys";
  static final String PROPERTY_INTERNAL_FRAMES_BOUNDS = "internal_frames_dimensions";
  static final String PROPERTY_BANDMAP_STEP = "bandmap_step_in_hz";
  static final String PROPERTY_BANDMAP_COLUMN_COUNT = "bandmap_column_count";
  static final String PROPERTY_BANDMAP_ROW_COUNT = "bandmap_row_count";
  
  
  public static final int FUNCTION_KEYS_COUNT = 12; // The number of function keys
 
  
  public enum FrameIndex
  {
    JFRAME(0),
    ENTRY(1),       
    LOG(2),       
    INCOMING_QSO(3),    
    BANDMAP(4), 
    RADIO(5);
    
    private final int code;
    FrameIndex(int code)  { this.code = code; }
    public int toInt() { return code; }
  }
  

  private String comPort;
  private String myCallsign;
  private boolean isQuickCallsignModeEnabled;
  private String defaultPrefix;
  private int qsoRepeatPeriodInSeconds;
  private Rectangle[] framesDimensions; // Postition and size of all frames used by the program
  private final String[] functionKeyTexts;  // texts for the function keys
  private int incomingQsoMaxEntries; // How many entries will be shown on the IncomingQsoPanel
  private int incomingQsoHiderAfter; // Specifies due time after which we hide the entry
  private int bandmapStepInHz;
  private int bandmapRowCount;
  private int bandmapColumnCount;
  
  private final Properties prop;

  /**
   * Tries to read the settings from the disk. If it fails default values are used.
   */
  public ApplicationSettings()
  {
    this.prop = new Properties();
    framesDimensions = new Rectangle[FrameIndex.values().length];
    functionKeyTexts = new String[FUNCTION_KEYS_COUNT];

    this.LoadSettingsFromDisk();
  }

  public Rectangle getFrameDimensions(ApplicationSettings.FrameIndex index)
  {
    return framesDimensions[index.toInt()];
  }

  public void setFrameDimensions(ApplicationSettings.FrameIndex index, Rectangle rect)
  {
    this.framesDimensions[index.toInt()] = rect;
  }

  
  public void setBandmapStepInHz(int bandmapStepInHz)
  {
    this.bandmapStepInHz = bandmapStepInHz;
  }

  public void setBandmapRowCount(int bandmapRowCount)
  {
    this.bandmapRowCount = bandmapRowCount;
  }

  public void setBandmapColumnCount(int bandmapColumnCount)
  {
    this.bandmapColumnCount = bandmapColumnCount;
  }

  public int getBandmapStepInHz()
  {
    return bandmapStepInHz;
  }

  public int getBandmapRowCount()
  {
    return bandmapRowCount;
  }

  public int getBandmapColumnCount()
  {
    return bandmapColumnCount;
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
   * This setting is not saved to a file (also it is initialized to 0 on object creation)
   *
   * @param isEnabled True is single element mode is enabled
   */
  public void setQuickCallsignMode(boolean isEnabled)
  {
    this.isQuickCallsignModeEnabled = isEnabled;
  }

  /**
   * This setting is not saved to a file (also it is initialized to 0 on object creation)
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
   *
   * @return
   */
  public int getQsoRepeatPeriod()
  {
    return this.qsoRepeatPeriodInSeconds;
  }

  /**
   * Set the allowed repeat period for Qso in seconds
   *
   * @param periodInSeconds
   */
  public void setQsoRepeatPeriod(int periodInSeconds)
  {
    this.qsoRepeatPeriodInSeconds = periodInSeconds;
  }

  /**
   * Returns the text for the desired function key
   *
   * @param keyIndex - Index 0 is for the F1 key, 1 for F2 and so on...
   * @return
   */
  public String getFunctionKeyText(int keyIndex)
  {
    return functionKeyTexts[keyIndex];
  }

  /**
   * Sets the text for the desired function key
   *
   * @param keyIndex - Index 0 is for the F1 key, 1 for F2 and so on...
   * @param text - the text that will be set for the desired function key
   */
  public void setFunctionKeyText(int keyIndex, String text)
  {
    functionKeyTexts[keyIndex] = text;
  }
  
  
  public void setIncomingQsoMaxEntries(int incomingQsoMaxEntries)
  {
    this.incomingQsoMaxEntries = incomingQsoMaxEntries;
  }

  public void setIncomingQsoHiderAfter(int incomingQsoHiderAfter)
  {
    this.incomingQsoHiderAfter = incomingQsoHiderAfter;
  }

  public int getIncomingQsoMaxEntries()
  {
    return incomingQsoMaxEntries;
  }

  public int getIncomingQsoHiderAfter()
  {
    return incomingQsoHiderAfter;
  }

  /**
   * Stores the array of values into properties which are named using key+index of the value
   *
   * @param key - property key that will be used for writing this property
   * @param values - array of values that will be written
   */
  private void setProperties(String key, String[] values)
  {
    for (int i = 0; i < values.length; i++)
    {
      prop.setProperty(key + i, values[i]);
    }
  }

  private void getProperties(String key, String[] values)
  {
    for (int i = 0; i < values.length; i++)
    {
      values[i] = prop.getProperty(key + i);
    }
  }
  
  
  private void setPropertiesFramesSizes()
  {
    for(int i=0; i<framesDimensions.length; i++)
    {
      prop.setProperty(PROPERTY_INTERNAL_FRAMES_BOUNDS+i+"x", Integer.toString(framesDimensions[i].x));
      prop.setProperty(PROPERTY_INTERNAL_FRAMES_BOUNDS+i+"y", Integer.toString(framesDimensions[i].y));
      prop.setProperty(PROPERTY_INTERNAL_FRAMES_BOUNDS+i+"width", Integer.toString(framesDimensions[i].width));
      prop.setProperty(PROPERTY_INTERNAL_FRAMES_BOUNDS+i+"height", Integer.toString(framesDimensions[i].height));
    } 
  }
  
  private void getPropertiesFramesSizes()
  {
    for(int i=0; i<framesDimensions.length; i++)
    {
      // Read the JFrame dimensions:
      int x = Integer.parseInt(prop.getProperty(PROPERTY_INTERNAL_FRAMES_BOUNDS+i+"x"));
      int y = Integer.parseInt(prop.getProperty(PROPERTY_INTERNAL_FRAMES_BOUNDS+i+"y"));
      int w = Integer.parseInt(prop.getProperty(PROPERTY_INTERNAL_FRAMES_BOUNDS+i+"width"));
      int h = Integer.parseInt(prop.getProperty(PROPERTY_INTERNAL_FRAMES_BOUNDS+i+"height"));

      this.framesDimensions[i] = new Rectangle(x, y, w, h);
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
    prop.setProperty(PROPERTY_QSO_REPEAT_PERIOD_SEC, Integer.toString(qsoRepeatPeriodInSeconds));

    // Now save the texts for the function keys
    setProperties(PROPERTY_FUNCTION_KEYS, functionKeyTexts);

    prop.setProperty(PROPERTY_INCOMING_QSO_HIDE_AFTER, Integer.toString(incomingQsoHiderAfter));
    prop.setProperty(PROPERTY_INCOMING_QSO_MAX_ENTRIES, Integer.toString(incomingQsoMaxEntries));

    // Save the dimensions for the different frames
    setPropertiesFramesSizes();
    
    // Save the bandmap settings
    prop.setProperty(PROPERTY_BANDMAP_COLUMN_COUNT, Integer.toString(bandmapColumnCount));
    prop.setProperty(PROPERTY_BANDMAP_ROW_COUNT, Integer.toString(bandmapRowCount));
    prop.setProperty(PROPERTY_BANDMAP_STEP, Integer.toString(bandmapStepInHz));


    try
    {
      prop.store(new FileOutputStream(SETTINGS_FILE_NAME), null);
    }
    catch (IOException ex)
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
      comPort = prop.getProperty(PROPERTY_COMPORT);
      if (comPort == null)
        throwMissingPropertyException(PROPERTY_COMPORT);

      // My callsign
      myCallsign = prop.getProperty(PROPERTY_MY_CALL_SIGN);
      if (myCallsign == null)
        throwMissingPropertyException(PROPERTY_MY_CALL_SIGN);

      // Now read the texts for the function keys
      getProperties(PROPERTY_FUNCTION_KEYS, functionKeyTexts);
      for (String str : functionKeyTexts)
      {
        if (str == null)
          throwMissingPropertyException(PROPERTY_FUNCTION_KEYS);
      }

      // Quick callsign mode
      String temp = prop.getProperty(PROPERTY_QUICK_CALLSIGN_MODE);
      if (temp == null)
        throwMissingPropertyException(PROPERTY_QUICK_CALLSIGN_MODE);
      isQuickCallsignModeEnabled = Boolean.parseBoolean(temp);

      // Default prefix
      defaultPrefix = prop.getProperty(PROPERTY_DEFAULT_PREFIX);
      if (defaultPrefix == null)
        throwMissingPropertyException(PROPERTY_DEFAULT_PREFIX);

      // Repeat period for Qso
      temp = prop.getProperty(PROPERTY_QSO_REPEAT_PERIOD_SEC);
      if(temp == null)
        throwMissingPropertyException(PROPERTY_QSO_REPEAT_PERIOD_SEC);
      qsoRepeatPeriodInSeconds = Integer.parseInt(temp);
      
      // Incoming qso hide after
      temp = prop.getProperty(PROPERTY_INCOMING_QSO_HIDE_AFTER);
      if (temp == null)
        throwMissingPropertyException(PROPERTY_QSO_REPEAT_PERIOD_SEC);
      incomingQsoHiderAfter = Integer.parseInt(temp); 
      
      // Incoming qso max entries
      temp = prop.getProperty(PROPERTY_INCOMING_QSO_MAX_ENTRIES);
      if (temp == null)
        throwMissingPropertyException(PROPERTY_INCOMING_QSO_MAX_ENTRIES);
      incomingQsoMaxEntries = Integer.parseInt(temp);
     
      // Read the dimensions for the different frames
      getPropertiesFramesSizes();
      
      
      // Read the bandmap settings
      temp = prop.getProperty(PROPERTY_BANDMAP_COLUMN_COUNT);
      if (temp == null)
        throwMissingPropertyException(PROPERTY_BANDMAP_COLUMN_COUNT);
      bandmapColumnCount = Integer.parseInt(temp);
      
      temp = prop.getProperty(PROPERTY_BANDMAP_ROW_COUNT);
      if (temp == null)
      {
        throwMissingPropertyException(PROPERTY_BANDMAP_ROW_COUNT);
      }
      bandmapRowCount = Integer.parseInt(temp);
      
      temp = prop.getProperty(PROPERTY_BANDMAP_STEP);
      if (temp == null)
      {
        throwMissingPropertyException(PROPERTY_BANDMAP_STEP);
      }
      bandmapStepInHz = Integer.parseInt(temp);
    }
    catch (IOException ex)
    {
      this.SetSettingsToDefault();
      Logger.getLogger(ApplicationSettings.class.getName()).log(Level.SEVERE, null, ex);
    }
    catch (NumberFormatException ex)
    {
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
    qsoRepeatPeriodInSeconds = 1800;

    // Set texts for the direction buttons
    functionKeyTexts[0] = "test {mycall}";       // F1
    functionKeyTexts[1] = "not defined by user"; // F2
    functionKeyTexts[2] = "tu";                  // F3
    functionKeyTexts[3] = "not defined by user";
    functionKeyTexts[4] = "not defined by user";
    functionKeyTexts[5] = "agn";
    functionKeyTexts[6] = "?";
    functionKeyTexts[7] = "dupe";
    functionKeyTexts[8] = "";
    functionKeyTexts[9] = "";
    functionKeyTexts[10] = "not defined by user";
    functionKeyTexts[11] = "not defined by user";

    incomingQsoHiderAfter = -360; // If overtime is 6 minutes don't show the entry
    incomingQsoMaxEntries = 10;  // Number of entries visible on the Incoming Qso panel
    
    // Default positions for the different frames
    framesDimensions[FrameIndex.JFRAME.toInt()] = new Rectangle(20, 20, 600, 600); // Main window
    framesDimensions[FrameIndex.BANDMAP.toInt()] = new Rectangle(10, 10, 200, 200);
    framesDimensions[FrameIndex.ENTRY.toInt()] = new Rectangle(40, 40, 200, 200);
    framesDimensions[FrameIndex.INCOMING_QSO.toInt()] = new Rectangle(60, 60, 200, 200);
    framesDimensions[FrameIndex.LOG.toInt()] = new Rectangle(80, 80, 200, 200);
    framesDimensions[FrameIndex.RADIO.toInt()] = new Rectangle(100, 100, 300, 50);
    
    bandmapColumnCount = 16;
    bandmapRowCount = 15;
    bandmapStepInHz = 200;
  }

  void throwMissingPropertyException(String propertyName) throws Exception
  {
    throw new Exception("Error when trying to read element " + propertyName + " from file " + SETTINGS_FILE_NAME);
  }
}
