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

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import jssc.SerialPortList;
import org.lz1aq.log.Log;
import org.lz1aq.log.LogDatabase;
import org.lz1aq.log.LogTableModel;
import org.lz1aq.log.Qso;
import org.lz1aq.rsi.Radio;
import org.lz1aq.utils.RadioModes;
import org.lz1aq.utils.RadioVfos;

/**
 *
 * @author potty
 */
public class MainWindow extends javax.swing.JFrame
{
  static final String PROGRAM_VERSION = "1.0.0";
  static final String PROGRAM_NAME    = "LZ Log";
          
  private Log                         log;
  private LogTableModel               qsoTableModel;
  private final ApplicationSettings   applicationSettings;
  private final RadioController       radioController;
  private LocalRadioControllerListener radioListener;
          
  private DocumentFilter              filter = new UppercaseDocumentFilter();
  private final JFileChooser          chooser;
  
  
  
  private static final Logger logger = Logger.getLogger(Radio.class.getName());
  /**
   * Creates new form MainWindow
   */
  public MainWindow()
  {
     
    // Init the Log/database 
    try
    {
      Qso example = new Qso(14190000, "cw", "lz1abc", "lz0fs", "200 091", "200 091", "cq"); // We need to supply an example QSO whwn creating/opening new
      log = new Log(new LogDatabase("log_test.db4o"), example);
      qsoTableModel = new LogTableModel(log);
    }
    catch (Exception ex)
    {
      logger.log(Level.SEVERE, "Couldn't open the log database!", ex);
    }
    
    
    // Init GUI
    initComponents();
    
    radioController = new RadioController();
    
    // Load user settings from the properties file
    this.applicationSettings = new ApplicationSettings();
    
    //This is used for catching global key presses (i.e. needed for F1-F12 presses)
    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
    manager.addKeyEventDispatcher(new MyDispatcher());
    

    // Prepare the entry fields to have the necessary data
    initEntryFields();

    // Callsign text field should show capital letters only
    ((AbstractDocument) jtextfieldCallsign.getDocument()).setDocumentFilter(filter);
    
    // Configure the FileChooser
      chooser = new JFileChooser();
      chooser.setFileFilter(new FileNameExtensionFilter("Python files", "py"));
      chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

    // Needed so that jTable to scroll automatically upon entering a new Qso
    jtableLog.addComponentListener(new ComponentAdapter()
    {
      @Override
      public void componentResized(ComponentEvent e)
      {
        jtableLog.scrollRectToVisible(jtableLog.getCellRect(jtableLog.getRowCount() - 1, 0, true));
      }
    });
  }

  
  private DefaultComboBoxModel getBandsComboboxModel()
  {
    return new DefaultComboBoxModel(new String[] { "1.8", "3.5", "7", "14", "21", "28" });
  }
  
  
  private DefaultComboBoxModel getModeComboboxModel()
  {
    return new DefaultComboBoxModel(new String[] { "SSB", "CW" });
  }
  
  
  /**
   * This method is called from within the constructor to initialize the form. WARNING: Do NOT
   * modify this code. The content of this method is always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {
    java.awt.GridBagConstraints gridBagConstraints;

    buttonGroupTypeOfWork = new javax.swing.ButtonGroup();
    jDialogSettings = new javax.swing.JDialog();
    jPanel1 = new javax.swing.JPanel();
    jPanel4 = new javax.swing.JPanel();
    jComboBoxComPort = new javax.swing.JComboBox();
    jLabel12 = new javax.swing.JLabel();
    jPanel3 = new javax.swing.JPanel();
    textfieldSettingsMyCallsign = new javax.swing.JTextField();
    jPanel7 = new javax.swing.JPanel();
    jLabel3 = new javax.swing.JLabel();
    jtextfieldf1 = new javax.swing.JTextField();
    jLabel9 = new javax.swing.JLabel();
    jtextfieldf3 = new javax.swing.JTextField();
    jLabel4 = new javax.swing.JLabel();
    jtextfieldf6 = new javax.swing.JTextField();
    jLabel5 = new javax.swing.JLabel();
    jtextfieldf7 = new javax.swing.JTextField();
    jLabel8 = new javax.swing.JLabel();
    jtextfieldf8 = new javax.swing.JTextField();
    jLabel6 = new javax.swing.JLabel();
    jtextfieldf9 = new javax.swing.JTextField();
    jLabel7 = new javax.swing.JLabel();
    jtextfieldf10 = new javax.swing.JTextField();
    jPanel6 = new javax.swing.JPanel();
    checkboxSettingsQuickMode = new javax.swing.JCheckBox();
    textfieldSettingsDefaultPrefix = new javax.swing.JTextField();
    jPanel2 = new javax.swing.JPanel();
    jtextfieldQsoRepeatPeriod = new javax.swing.JTextField();
    jLabel2 = new javax.swing.JLabel();
    jPanel5 = new javax.swing.JPanel();
    jButtonCancel = new javax.swing.JButton();
    jButtonSave = new javax.swing.JButton();
    jSplitPane2 = new javax.swing.JSplitPane();
    jsplitRighPanel = new javax.swing.JSplitPane();
    jpanelLog = new javax.swing.JPanel();
    jpanelCompleteLog = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    jtableLog = new javax.swing.JTable();
    jbuttonDeleteEntry = new javax.swing.JButton();
    jpanelSearchLog = new javax.swing.JPanel();
    jScrollPane3 = new javax.swing.JScrollPane();
    jtableSearch = new javax.swing.JTable();
    jPanel8 = new javax.swing.JPanel();
    jScrollPane5 = new javax.swing.JScrollPane();
    jTable3 = new javax.swing.JTable();
    jsplitLeftPanel = new javax.swing.JSplitPane();
    jpanelEntry = new javax.swing.JPanel();
    jpanelCallsign = new javax.swing.JPanel();
    jtextfieldCallsign = new javax.swing.JTextField();
    jtextfieldSnt = new javax.swing.JTextField();
    jtextfieldRcv = new javax.swing.JTextField();
    jpanelTypeOfWork = new javax.swing.JPanel();
    jradiobuttonCQ = new javax.swing.JRadioButton();
    jradiobuttonSP = new javax.swing.JRadioButton();
    jLabel1 = new javax.swing.JLabel();
    jcomboboxMode = new javax.swing.JComboBox();
    jcomboboxBand = new javax.swing.JComboBox();
    jpanelFunctionKeys = new javax.swing.JPanel();
    jButton1 = new javax.swing.JButton();
    jButton2 = new javax.swing.JButton();
    jButton3 = new javax.swing.JButton();
    jButton4 = new javax.swing.JButton();
    jButton5 = new javax.swing.JButton();
    jButton6 = new javax.swing.JButton();
    jButton7 = new javax.swing.JButton();
    jButton8 = new javax.swing.JButton();
    jButton9 = new javax.swing.JButton();
    jButton10 = new javax.swing.JButton();
    jButton11 = new javax.swing.JButton();
    jButton12 = new javax.swing.JButton();
    jpanelAdditionalKeys = new javax.swing.JPanel();
    jPanelStatusBar = new javax.swing.JPanel();
    jlabelCallsignStatus = new javax.swing.JLabel();
    jpanelIncomingQso = new javax.swing.JPanel();
    jScrollPane2 = new javax.swing.JScrollPane();
    jTable1 = new javax.swing.JTable();
    jpanelRadio = new javax.swing.JPanel();
    jpanelVfoA = new javax.swing.JPanel();
    jtogglebuttonConnectToRadio = new javax.swing.JToggleButton();
    jtextfieldFrequency = new javax.swing.JTextField();
    jtextfieldMode = new javax.swing.JTextField();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    jMenu2 = new javax.swing.JMenu();
    jMenuItem1 = new javax.swing.JMenuItem();

    jDialogSettings.setTitle("Settings");
    jDialogSettings.setAlwaysOnTop(true);
    jDialogSettings.setModal(true);
    jDialogSettings.setType(java.awt.Window.Type.UTILITY);
    jDialogSettings.addComponentListener(new java.awt.event.ComponentAdapter()
    {
      public void componentShown(java.awt.event.ComponentEvent evt)
      {
        jDialogSettingsComponentShown(evt);
      }
    });
    jDialogSettings.getContentPane().setLayout(new java.awt.GridBagLayout());

    jPanel1.setLayout(new java.awt.GridBagLayout());

    jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("CommPort settings"));
    jPanel4.setLayout(new java.awt.GridBagLayout());

    jComboBoxComPort.setModel(getComportsComboboxModel());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
    jPanel4.add(jComboBoxComPort, gridBagConstraints);

    jLabel12.setText("CommPort");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
    jPanel4.add(jLabel12, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
    jPanel1.add(jPanel4, gridBagConstraints);

    jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("My callsign"));
    jPanel3.setLayout(new java.awt.GridBagLayout());

    textfieldSettingsMyCallsign.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
    textfieldSettingsMyCallsign.setText("Your callsign here");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 20);
    jPanel3.add(textfieldSettingsMyCallsign, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
    jPanel1.add(jPanel3, gridBagConstraints);

    jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Function keys"));
    jPanel7.setLayout(new java.awt.GridLayout(0, 2));

    jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jLabel3.setText("F1 Cq");
    jPanel7.add(jLabel3);

    jtextfieldf1.setText("jTextField1");
    jPanel7.add(jtextfieldf1);

    jLabel9.setText("F3 Tu");
    jPanel7.add(jLabel9);

    jtextfieldf3.setText("jTextField2");
    jPanel7.add(jtextfieldf3);

    jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jLabel4.setText("F6 Agn");
    jPanel7.add(jLabel4);

    jtextfieldf6.setText("jTextField3");
    jPanel7.add(jtextfieldf6);

    jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jLabel5.setText("F7 ?");
    jPanel7.add(jLabel5);

    jtextfieldf7.setText("jTextField4");
    jPanel7.add(jtextfieldf7);

    jLabel8.setText("F8 Dupe");
    jPanel7.add(jLabel8);

    jtextfieldf8.setText("jTextField5");
    jPanel7.add(jtextfieldf8);

    jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jLabel6.setText("F9 Spare");
    jPanel7.add(jLabel6);

    jtextfieldf9.setText("jTextField6");
    jPanel7.add(jtextfieldf9);

    jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jLabel7.setText("F10 Spare");
    jPanel7.add(jLabel7);

    jtextfieldf10.setText("jTextField7");
    jPanel7.add(jtextfieldf10);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
    jPanel1.add(jPanel7, gridBagConstraints);

    jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Quick callsign mode"));
    jPanel6.setLayout(new java.awt.GridBagLayout());

    checkboxSettingsQuickMode.setText("Enable quick callsign entry");
    checkboxSettingsQuickMode.setToolTipText("If enabled will allow to enter callsign by using only the sufix");
    checkboxSettingsQuickMode.addChangeListener(new javax.swing.event.ChangeListener()
    {
      public void stateChanged(javax.swing.event.ChangeEvent evt)
      {
        checkboxSettingsQuickModeStateChanged(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(5, 5, 10, 5);
    jPanel6.add(checkboxSettingsQuickMode, gridBagConstraints);

    textfieldSettingsDefaultPrefix.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
    textfieldSettingsDefaultPrefix.setText("LZ0");
    textfieldSettingsDefaultPrefix.setToolTipText("The default prefix which will be added");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 0.3;
    gridBagConstraints.weighty = 1.0;
    jPanel6.add(textfieldSettingsDefaultPrefix, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.1;
    gridBagConstraints.weighty = 0.1;
    gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
    jPanel1.add(jPanel6, gridBagConstraints);

    jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Qso repeat period"));
    jPanel2.setToolTipText("");
    jPanel2.setLayout(new java.awt.GridBagLayout());

    jtextfieldQsoRepeatPeriod.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
    jtextfieldQsoRepeatPeriod.setText("30");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.3;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
    jPanel2.add(jtextfieldQsoRepeatPeriod, gridBagConstraints);

    jLabel2.setText("Period in seconds:");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
    jPanel2.add(jLabel2, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 0.1;
    gridBagConstraints.weighty = 0.1;
    jPanel1.add(jPanel2, gridBagConstraints);

    jPanel5.setLayout(new java.awt.GridBagLayout());

    jButtonCancel.setText("Cancel");
    jButtonCancel.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonCancelActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
    jPanel5.add(jButtonCancel, gridBagConstraints);

    jButtonSave.setText("Save");
    jButtonSave.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButtonSaveActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 2);
    jPanel5.add(jButtonSave, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
    jPanel1.add(jPanel5, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jDialogSettings.getContentPane().add(jPanel1, gridBagConstraints);

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    addWindowListener(new java.awt.event.WindowAdapter()
    {
      public void windowOpened(java.awt.event.WindowEvent evt)
      {
        formWindowOpened(evt);
      }
      public void windowClosing(java.awt.event.WindowEvent evt)
      {
        formWindowClosing(evt);
      }
    });
    getContentPane().setLayout(new java.awt.GridLayout(1, 0));

    jsplitRighPanel.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

    jpanelLog.setLayout(new java.awt.GridBagLayout());

    jpanelCompleteLog.setBorder(javax.swing.BorderFactory.createTitledBorder("Log"));
    jpanelCompleteLog.setLayout(new java.awt.GridBagLayout());

    jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

    jtableLog.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
    jtableLog.setModel(qsoTableModel);
    jtableLog.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    jScrollPane1.setViewportView(jtableLog);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jpanelCompleteLog.add(jScrollPane1, gridBagConstraints);

    jbuttonDeleteEntry.setToolTipText("Deletes Qso from the Log.");
    jbuttonDeleteEntry.setLabel("Delete entry");
    jbuttonDeleteEntry.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jbuttonDeleteEntryActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 0.1;
    gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
    jpanelCompleteLog.add(jbuttonDeleteEntry, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jpanelLog.add(jpanelCompleteLog, gridBagConstraints);

    jpanelSearchLog.setLayout(new java.awt.GridBagLayout());

    jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("Search results"));
    jScrollPane3.setToolTipText("");

    jtableSearch.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String []
      {
        "Title 1", "Title 2", "Title 3", "Title 4"
      }
    ));
    jScrollPane3.setViewportView(jtableSearch);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 0.3;
    gridBagConstraints.weighty = 0.3;
    jpanelSearchLog.add(jScrollPane3, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 0.2;
    gridBagConstraints.weighty = 0.2;
    jpanelLog.add(jpanelSearchLog, gridBagConstraints);

    jsplitRighPanel.setRightComponent(jpanelLog);

    jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Bandmap"));
    jPanel8.setLayout(new java.awt.GridLayout(1, 0));

    jTable3.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String []
      {
        "Title 1", "Title 2", "Title 3", "Title 4"
      }
    ));
    jScrollPane5.setViewportView(jTable3);

    jPanel8.add(jScrollPane5);

    jsplitRighPanel.setTopComponent(jPanel8);

    jSplitPane2.setRightComponent(jsplitRighPanel);

    jsplitLeftPanel.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

    jpanelEntry.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
    jpanelEntry.setLayout(new java.awt.GridBagLayout());

    jpanelCallsign.setFocusCycleRoot(true);
    jpanelCallsign.setLayout(new java.awt.GridLayout(1, 0));

    jtextfieldCallsign.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
    jtextfieldCallsign.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    jtextfieldCallsign.setText("Callsign");
    jtextfieldCallsign.setBorder(javax.swing.BorderFactory.createTitledBorder("Callsign"));
    jtextfieldCallsign.addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyTyped(java.awt.event.KeyEvent evt)
      {
        jtextfieldCallsignKeyTyped(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt)
      {
        jtextfieldCallsignKeyReleased(evt);
      }
    });
    jpanelCallsign.add(jtextfieldCallsign);

    jtextfieldSnt.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
    jtextfieldSnt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    jtextfieldSnt.setText("020 043");
    jtextfieldSnt.setBorder(javax.swing.BorderFactory.createTitledBorder("Snt"));
    jpanelCallsign.add(jtextfieldSnt);

    jtextfieldRcv.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
    jtextfieldRcv.setHorizontalAlignment(javax.swing.JTextField.CENTER);
    jtextfieldRcv.setText("299 209");
    jtextfieldRcv.setBorder(javax.swing.BorderFactory.createTitledBorder("Rcv"));
    jtextfieldRcv.setMinimumSize(new java.awt.Dimension(210, 58));
    jtextfieldRcv.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jtextfieldRcvActionPerformed(evt);
      }
    });
    jpanelCallsign.add(jtextfieldRcv);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    jpanelEntry.add(jpanelCallsign, gridBagConstraints);

    jpanelTypeOfWork.setLayout(new java.awt.GridBagLayout());

    buttonGroupTypeOfWork.add(jradiobuttonCQ);
    jradiobuttonCQ.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jradiobuttonCQ.setText("CQ");
    jradiobuttonCQ.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.1;
    gridBagConstraints.weighty = 1.0;
    jpanelTypeOfWork.add(jradiobuttonCQ, gridBagConstraints);

    buttonGroupTypeOfWork.add(jradiobuttonSP);
    jradiobuttonSP.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jradiobuttonSP.setSelected(true);
    jradiobuttonSP.setText("S&P");
    jradiobuttonSP.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 0.1;
    gridBagConstraints.weighty = 1.0;
    jpanelTypeOfWork.add(jradiobuttonSP, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jpanelTypeOfWork.add(jLabel1, gridBagConstraints);

    jcomboboxMode.setModel(getModeComboboxModel());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 4;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 0.1;
    gridBagConstraints.weighty = 1.0;
    jpanelTypeOfWork.add(jcomboboxMode, gridBagConstraints);

    jcomboboxBand.setModel(getBandsComboboxModel());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 0.1;
    gridBagConstraints.weighty = 1.0;
    jpanelTypeOfWork.add(jcomboboxBand, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jpanelEntry.add(jpanelTypeOfWork, gridBagConstraints);

    jpanelFunctionKeys.setLayout(new java.awt.GridLayout(3, 0));

    jButton1.setText("F1 CQ");
    jButton1.setFocusable(false);
    jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jButton1.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton1ActionPerformed(evt);
      }
    });
    jpanelFunctionKeys.add(jButton1);

    jButton2.setText("F2 Exch");
    jButton2.setFocusable(false);
    jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jButton2.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton2ActionPerformed(evt);
      }
    });
    jpanelFunctionKeys.add(jButton2);

    jButton3.setText("F3 Tu");
    jButton3.setFocusable(false);
    jButton3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jButton3.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton3ActionPerformed(evt);
      }
    });
    jpanelFunctionKeys.add(jButton3);

    jButton4.setText("F4 MyCall");
    jButton4.setFocusable(false);
    jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jButton4.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton4ActionPerformed(evt);
      }
    });
    jpanelFunctionKeys.add(jButton4);

    jButton5.setText("F5 His Call");
    jButton5.setFocusable(false);
    jButton5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jButton5.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton5ActionPerformed(evt);
      }
    });
    jpanelFunctionKeys.add(jButton5);

    jButton6.setText("F6 Agn");
    jButton6.setFocusable(false);
    jButton6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jButton6.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton6ActionPerformed(evt);
      }
    });
    jpanelFunctionKeys.add(jButton6);

    jButton7.setText("F7 ?");
    jButton7.setFocusable(false);
    jButton7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jButton7.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton7ActionPerformed(evt);
      }
    });
    jpanelFunctionKeys.add(jButton7);

    jButton8.setText("F8 Dupe");
    jButton8.setFocusable(false);
    jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jButton8.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton8ActionPerformed(evt);
      }
    });
    jpanelFunctionKeys.add(jButton8);

    jButton9.setText("F9 Spare");
    jButton9.setFocusable(false);
    jButton9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jButton9.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton9ActionPerformed(evt);
      }
    });
    jpanelFunctionKeys.add(jButton9);

    jButton10.setText("F10 Spare");
    jButton10.setToolTipText("");
    jButton10.setFocusable(false);
    jButton10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jButton10.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton10ActionPerformed(evt);
      }
    });
    jpanelFunctionKeys.add(jButton10);

    jButton11.setText("F11 Spot");
    jButton11.setFocusable(false);
    jButton11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jButton11.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton11ActionPerformed(evt);
      }
    });
    jpanelFunctionKeys.add(jButton11);

    jButton12.setText("F12 Wipe");
    jButton12.setFocusable(false);
    jButton12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jButton12.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton12ActionPerformed(evt);
      }
    });
    jpanelFunctionKeys.add(jButton12);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jpanelEntry.add(jpanelFunctionKeys, gridBagConstraints);

    jpanelAdditionalKeys.setLayout(new java.awt.GridBagLayout());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jpanelEntry.add(jpanelAdditionalKeys, gridBagConstraints);

    jPanelStatusBar.setLayout(new java.awt.GridBagLayout());

    jlabelCallsignStatus.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
    jlabelCallsignStatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    jlabelCallsignStatus.setText("status text here");
    jlabelCallsignStatus.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jPanelStatusBar.add(jlabelCallsignStatus, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 10, 0);
    jpanelEntry.add(jPanelStatusBar, gridBagConstraints);

    jsplitLeftPanel.setLeftComponent(jpanelEntry);

    jpanelIncomingQso.setBorder(javax.swing.BorderFactory.createTitledBorder("Incoming Qsos"));
    jpanelIncomingQso.setLayout(new java.awt.GridBagLayout());

    jTable1.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][]
      {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String []
      {
        "Title 1", "Title 2", "Title 3", "Title 4"
      }
    ));
    jScrollPane2.setViewportView(jTable1);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jpanelIncomingQso.add(jScrollPane2, gridBagConstraints);

    jpanelRadio.setBorder(javax.swing.BorderFactory.createTitledBorder("Radio"));
    jpanelRadio.setLayout(new java.awt.GridBagLayout());

    jpanelVfoA.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
    jpanelVfoA.setLayout(new java.awt.GridBagLayout());

    jtogglebuttonConnectToRadio.setText("Connect/Disconnect");
    jtogglebuttonConnectToRadio.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jtogglebuttonConnectToRadioActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 0.2;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 20);
    jpanelVfoA.add(jtogglebuttonConnectToRadio, gridBagConstraints);

    jtextfieldFrequency.setEditable(false);
    jtextfieldFrequency.setBackground(new java.awt.Color(0, 0, 0));
    jtextfieldFrequency.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jtextfieldFrequency.setForeground(new java.awt.Color(255, 255, 255));
    jtextfieldFrequency.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    jtextfieldFrequency.setText("frequency");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jpanelVfoA.add(jtextfieldFrequency, gridBagConstraints);

    jtextfieldMode.setEditable(false);
    jtextfieldMode.setBackground(new java.awt.Color(0, 0, 0));
    jtextfieldMode.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jtextfieldMode.setForeground(new java.awt.Color(255, 255, 255));
    jtextfieldMode.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    jtextfieldMode.setText("mode");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 0.2;
    gridBagConstraints.weighty = 1.0;
    jpanelVfoA.add(jtextfieldMode, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    jpanelRadio.add(jpanelVfoA, gridBagConstraints);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 0.1;
    jpanelIncomingQso.add(jpanelRadio, gridBagConstraints);

    jsplitLeftPanel.setBottomComponent(jpanelIncomingQso);

    jSplitPane2.setLeftComponent(jsplitLeftPanel);

    getContentPane().add(jSplitPane2);

    jMenu1.setText("File");
    jMenuBar1.add(jMenu1);

    jMenu2.setText("Tools");

    jMenuItem1.setText("Settings");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jMenuItem1ActionPerformed(evt);
      }
    });
    jMenu2.add(jMenuItem1);

    jMenuBar1.add(jMenu2);

    setJMenuBar(jMenuBar1);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCancelActionPerformed
  {//GEN-HEADEREND:event_jButtonCancelActionPerformed
    jDialogSettings.setVisible(false);
  }//GEN-LAST:event_jButtonCancelActionPerformed

  private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonSaveActionPerformed
  {//GEN-HEADEREND:event_jButtonSaveActionPerformed
    // Validate myCallsign
    if(!Qso.isValidCallsign(textfieldSettingsMyCallsign.getText()))
    {
      JOptionPane.showMessageDialog(null, "Invalid callsign!");
      return;
    }
    
    // Validate repeat period
    try
    {
      Integer.parseInt(jtextfieldQsoRepeatPeriod.getText());
    }catch(Exception exc)
    {
      JOptionPane.showMessageDialog(null, "Invalid repeat Qso period! Must be a number.");
      return;
    }
    
    
    jDialogSettings.setVisible(false); // Hide the SettingsDialog
    storeSettingsDialogParams();       // Read the state of the controls and save them

    initMainWindow(false);
  }//GEN-LAST:event_jButtonSaveActionPerformed

  private void jDialogSettingsComponentShown(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_jDialogSettingsComponentShown
  {//GEN-HEADEREND:event_jDialogSettingsComponentShown
    // Settings dialog is shown and we need to set the states of the controls
    initSettingsDialog();
  }//GEN-LAST:event_jDialogSettingsComponentShown

  private void formWindowOpened(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowOpened
  {//GEN-HEADEREND:event_formWindowOpened
    initMainWindow(true);
  }//GEN-LAST:event_formWindowOpened

  private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
  {//GEN-HEADEREND:event_formWindowClosing
    applicationSettings.SaveSettingsToDisk(); // Save all settings to disk
  }//GEN-LAST:event_formWindowClosing

  private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
  {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
    jDialogSettings.pack();
    jDialogSettings.setVisible(true);
  }//GEN-LAST:event_jMenuItem1ActionPerformed

  private void checkboxSettingsQuickModeStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_checkboxSettingsQuickModeStateChanged
  {//GEN-HEADEREND:event_checkboxSettingsQuickModeStateChanged
    if(checkboxSettingsQuickMode.isSelected())
      textfieldSettingsDefaultPrefix.setEnabled(true);
    else
      textfieldSettingsDefaultPrefix.setEnabled(false);
  }//GEN-LAST:event_checkboxSettingsQuickModeStateChanged

  private void jButton12ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton12ActionPerformed
  {//GEN-HEADEREND:event_jButton12ActionPerformed
    pressedF12();
  }//GEN-LAST:event_jButton12ActionPerformed

  private void jButton11ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton11ActionPerformed
  {//GEN-HEADEREND:event_jButton11ActionPerformed
    pressedF11();
  }//GEN-LAST:event_jButton11ActionPerformed

  private void jtextfieldRcvActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtextfieldRcvActionPerformed
  {//GEN-HEADEREND:event_jtextfieldRcvActionPerformed
    // If DUPE ask for confirmation to log
    if(log.isDupe(getCallsignFromTextField(), applicationSettings.getQsoRepeatPeriod()))
    {
      int response = JOptionPane.showConfirmDialog(null, "Do you want to log DUPE Qso?", "Confirm",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      if (response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION)
      {
        return; // do nothing
      }
    }

    // Log Qso
    if(addEntryToLog())
    {
      initEntryFields();
      // Move focus to Callsign field
      jtextfieldCallsign.requestFocus();
    }
  }//GEN-LAST:event_jtextfieldRcvActionPerformed

  private void jtextfieldCallsignKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jtextfieldCallsignKeyReleased
  {//GEN-HEADEREND:event_jtextfieldCallsignKeyReleased
    // On every key press update the callsign status
    String status = getCallsignStatusText(getCallsignFromTextField());
    jlabelCallsignStatus.setText(status);
  }//GEN-LAST:event_jtextfieldCallsignKeyReleased

  private void jtextfieldCallsignKeyTyped(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jtextfieldCallsignKeyTyped
  {//GEN-HEADEREND:event_jtextfieldCallsignKeyTyped
    switch(evt.getKeyChar())
    {
      case KeyEvent.VK_SPACE:
      // If Dupe - clear the fields
      if(log.isDupe(getCallsignFromTextField(), applicationSettings.getQsoRepeatPeriod()))
      {
        initEntryFields();
        evt.consume();
      }
      // If not DUPE - move to Rcv field
      else
      {
        jtextfieldRcv.requestFocus();
        evt.consume();
      }
      break;

      case KeyEvent.VK_ENTER:
      // Move to Rcv field
      jtextfieldRcv.requestFocus();
      evt.consume();
      break;
    }
  }//GEN-LAST:event_jtextfieldCallsignKeyTyped

  private void jbuttonDeleteEntryActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jbuttonDeleteEntryActionPerformed
  {//GEN-HEADEREND:event_jbuttonDeleteEntryActionPerformed
    // Ask for confirmation
    int response = JOptionPane.showConfirmDialog(null, "Delete the selected Qso entry?", "Confirm",
      JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    if (response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION)
    {
      return; // do nothing
    }

    // Get the selected row
    int selection = jtableLog.getSelectedRow();
    if(selection >= 0)
    {
      selection = jtableLog.convertRowIndexToModel(selection);
      qsoTableModel.removeRow(selection);
    }
    else
    {
      JOptionPane.showMessageDialog(null, "Pease select entry!");
    }
  }//GEN-LAST:event_jbuttonDeleteEntryActionPerformed

  private void jtogglebuttonConnectToRadioActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jtogglebuttonConnectToRadioActionPerformed
  {//GEN-HEADEREND:event_jtogglebuttonConnectToRadioActionPerformed
    JToggleButton tBtn = (JToggleButton) evt.getSource();
    
    // Connect
    // --------------------
    if (tBtn.isSelected())
    {
      // Select the python file describing the radio protocol
      boolean result = loadRadioProtocolParser();
      if (!result)
      {
        jtogglebuttonConnectToRadio.setSelected(false);
        return;
      }

      // Now establish connection with the radio
      result = connectToRadio();
      if(!result)
      {
        jtogglebuttonConnectToRadio.setSelected(false);
      }
    }
    // Disconnect
    // --------------------
    else
    {
      if (radioController != null)
      {
        radioController.disconnect();
        // If we are disconnecting from the radio we need to enable the Frequency and the Mode comboboxes
        jcomboboxBand.setEnabled(true);
        jcomboboxMode.setEnabled(true);
      }

    }
  }//GEN-LAST:event_jtogglebuttonConnectToRadioActionPerformed

  private void jButton3ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton3ActionPerformed
  {//GEN-HEADEREND:event_jButton3ActionPerformed
    pressedF3();
  }//GEN-LAST:event_jButton3ActionPerformed

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton1ActionPerformed
  {//GEN-HEADEREND:event_jButton1ActionPerformed
    pressedF1();
  }//GEN-LAST:event_jButton1ActionPerformed

  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton2ActionPerformed
  {//GEN-HEADEREND:event_jButton2ActionPerformed
    pressedF2();
  }//GEN-LAST:event_jButton2ActionPerformed

  private void jButton4ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton4ActionPerformed
  {//GEN-HEADEREND:event_jButton4ActionPerformed
    pressedF4();
  }//GEN-LAST:event_jButton4ActionPerformed

  private void jButton5ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton5ActionPerformed
  {//GEN-HEADEREND:event_jButton5ActionPerformed
    pressedF5();
  }//GEN-LAST:event_jButton5ActionPerformed

  private void jButton6ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton6ActionPerformed
  {//GEN-HEADEREND:event_jButton6ActionPerformed
    pressedF6();
  }//GEN-LAST:event_jButton6ActionPerformed

  private void jButton7ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton7ActionPerformed
  {//GEN-HEADEREND:event_jButton7ActionPerformed
    pressedF7();
  }//GEN-LAST:event_jButton7ActionPerformed

  private void jButton8ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton8ActionPerformed
  {//GEN-HEADEREND:event_jButton8ActionPerformed
    pressedF8();
  }//GEN-LAST:event_jButton8ActionPerformed

  private void jButton9ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton9ActionPerformed
  {//GEN-HEADEREND:event_jButton9ActionPerformed
    pressedF9();
  }//GEN-LAST:event_jButton9ActionPerformed

  private void jButton10ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButton10ActionPerformed
  {//GEN-HEADEREND:event_jButton10ActionPerformed
    pressedF10();
  }//GEN-LAST:event_jButton10ActionPerformed
  
  
  private boolean connectToRadio()
  {
    boolean result = radioController.connect(applicationSettings.getComPort(), new LocalRadioControllerListener());
    if (!result)
    {
      JOptionPane.showMessageDialog(null, "Coud not connect to radio!", "Serial connection error...", JOptionPane.ERROR_MESSAGE);
    }
    
    // If we are connected to the radio we need to disable the Frequency and the Mode comboboxes
    jcomboboxBand.setEnabled(false);
    jcomboboxMode.setEnabled(false);
    return result;
  }
  
  
  /**
   * Opens a file chooser which lets the user select the appropriate radio protocol parser.
   * @return true - if the loading was successful
   */
  private boolean loadRadioProtocolParser()
  {
    try
    {
      int returnVal = chooser.showOpenDialog(this.getParent());
      if (returnVal != JFileChooser.APPROVE_OPTION)
        return false;
    }catch(Exception exc)
    {
        logger.log(Level.SEVERE, "Coudln't start file chooser", exc);
        return false;
    }
   
    boolean result = radioController.loadProtocolParser(chooser.getSelectedFile().getName());
    if (result == false)
    {
      JOptionPane.showMessageDialog(null, "Error when trying to load the radio protocol parser file!", "Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }
     
    // Show the serial settings that we are going to use when connecting to this radio
    JOptionPane.showMessageDialog(null, radioController.getInfo());
    return true;
  }
  
  
  /**
   * Reads the info from the entry window and if all data is valid it saves it into the Log
   *
   * @return true - if the QSO was successfully logged
   *         false - 
   */
  boolean addEntryToLog()
  {
    
    String mode;
    Qso qso;

    try
    {
     
      qso = new Qso(getFreq(),
                    getMode(),     
                    applicationSettings.getMyCallsign(),
                    getCallsignFromTextField(),
                    jtextfieldSnt.getText(),
                    jtextfieldRcv.getText(),
                    getTypeOfWork());
    }
    catch (Exception exc)
    {
      return false;
    }

    qsoTableModel.addRow(qso);

    return true;
  }

  
  /**
   * Determines if the current Type of work is SP or CQ
   * @return 
   */
  private String getTypeOfWork()
  {
    if(jradiobuttonSP.isSelected())
      return "SP";
    else
      return "CQ";
  }
  
  
  /**
   * Determines the current working mode. Takes into account if the connected
   * to the radio or not.
   * @return - String describing the mode (e.g. "cw", "ssb")
   */
  private String getMode()
  {
    // If radio is connected get the frequency from there
    if(radioController.isConnected())
    {
      RadioModes mode;
      mode = radioController.getMode();
      return mode.toString();
    }
    // If no radio is connected - read the mode from the combobox model
    else
    {
      String temp = jcomboboxMode.getSelectedItem().toString();
      return temp;
    }
  }
  
  
  /**
   * Determines the current working frequency. Takes into account if the connected
   * to the radio or not.
   * @return - frequency in Hz
   */
  private long getFreq()
  {
    int freq;
    
    // If radio is connected get the frequency from there
    if(radioController.isConnected())
    {
      freq = radioController.getFrequency();
    }
    // If no radio is connected - read the freq from the dropdown box
    else
    {
      String temp = jcomboboxBand.getSelectedItem().toString();
      // convert to Hz
      freq = Math.round(Float.parseFloat(temp)*1000000);
    }
    
    return freq;
  }
  
  
  /**
   * Gets the callsign from the jtextfieldCallsign.
   * If the callsign was inserted in the short form (e.g. HH) this function will return the full
   * form (i.e. LZ2HH)
   * @return - the callsign in its full form (e.g. LZ6HH)
   */
  private String getCallsignFromTextField()
  {
    String callsign = jtextfieldCallsign.getText();
    
    if(applicationSettings.isQuickCallsignModeEnabled())
    {
      callsign = Log.DEFAULT_CALLSIGN_PREFIX+callsign;
    }
    
    return callsign;
  }
  
  
  /**
   * Prints info concerning the callsign:
   * NEW - If no qso before
   * OK - Qso before but the required time has elapsed
   * DUPE time left... - Qso before and the required time has not elapsed
   * @param callsign
   * @return 
   */
  private String getCallsignStatusText(String callsign)
  {
    String statusText = "";

    Qso qso = log.getLastQso(callsign);

    // Unknown callsign - OK to work
    if (qso == null)
    {
      statusText = "New";
    }
    else
    {
      // Required time has not elapsed
      if (log.getSecondsLeft(qso, applicationSettings.getQsoRepeatPeriod()) > 0)
      {
        // Print DUPE
        statusText = statusText.concat("DUPE   ");

        //Print the time left till next possible contact
        statusText = statusText.concat("time left " + log.getTimeLeftFormatted(qso, applicationSettings.getQsoRepeatPeriod()));
      }
      else
      {
        statusText = "OK";
      }
    }
    
    return statusText;
  }
  
  
  /**
   * Cleans/prepares the entry fields for the next QSO
   */
  private void initEntryFields()
  {
    // Clean the callsign field
    jtextfieldCallsign.setText("");
    // Add the new Snt number
    jtextfieldSnt.setText(log.getNextSentReport());
    // Cean the Rcv field
    jtextfieldRcv.setText("");
    // Clean the callsign status
    jlabelCallsignStatus.setText("New");
  }
  

  /**
   * @return Returns a new DefaultComboBoxModel containing all available COM ports
   */
  private DefaultComboBoxModel getComportsComboboxModel()
  {
    String[] portNames = SerialPortList.getPortNames();
    return new DefaultComboBoxModel(portNames);
  }
  
  
  /**
   * Initialize the controls of the main windows
   */
  private void initMainWindow(boolean isStartup)
  {
//    // Read last used JFrame dimensions and restore it
//    if (isStartup)
//    {
//      if (applicationSettings.getJFrameDimensions().isEmpty() == false)
//      {
//        this.setBounds(applicationSettings.getJFrameDimensions());
//      }
//
//    }
  }
    
    
  /**
   * User has opened the setting dialog and we need to load the state of the controls
   */
  private void initSettingsDialog()
  {
    // Comport selection
    jComboBoxComPort.setSelectedItem(applicationSettings.getComPort());
  
    // my callsing texts
    textfieldSettingsMyCallsign.setText(applicationSettings.getMyCallsign());

    // Quick callsign mode 
    checkboxSettingsQuickMode.setSelected(applicationSettings.isQuickCallsignModeEnabled());
    
    // Set the text for the function keys
    jtextfieldf1.setText(applicationSettings.getFunctionKeyText(0));
    jtextfieldf3.setText(applicationSettings.getFunctionKeyText(2));
    jtextfieldf6.setText(applicationSettings.getFunctionKeyText(5));
    jtextfieldf7.setText(applicationSettings.getFunctionKeyText(6));
    jtextfieldf8.setText(applicationSettings.getFunctionKeyText(7));
    jtextfieldf9.setText(applicationSettings.getFunctionKeyText(8));
    jtextfieldf10.setText(applicationSettings.getFunctionKeyText(9));
    
    // Default prefix
    textfieldSettingsDefaultPrefix.setText(applicationSettings.getDefaultPrefix());
   
    if(applicationSettings.isQuickCallsignModeEnabled() == false) 
    {
      textfieldSettingsDefaultPrefix.setEnabled(false); // Disable the "default prefix" text field if the "Quick callsign mode" is disabled
    }
    
    // Repeat period in seconds
    jtextfieldQsoRepeatPeriod.setText(Integer.toString(applicationSettings.getQsoRepeatPeriod()));
   
  }
    
    
  /**
   * User has closed the setting dialog and we need to save the state of the controls
   */
  private void storeSettingsDialogParams()
  {
    // Commport
    if (jComboBoxComPort.getSelectedItem() != null)
    {
      applicationSettings.setComPort(jComboBoxComPort.getSelectedItem().toString());
    }
    
    // Callsign
    applicationSettings.setMyCallsign(textfieldSettingsMyCallsign.getText());
    
    // Function keys texts
    applicationSettings.setFunctionKeyText(0, jtextfieldf1.getText());
    applicationSettings.setFunctionKeyText(2, jtextfieldf3.getText());
    applicationSettings.setFunctionKeyText(5, jtextfieldf6.getText());
    applicationSettings.setFunctionKeyText(6, jtextfieldf7.getText());
    applicationSettings.setFunctionKeyText(7, jtextfieldf8.getText());
    applicationSettings.setFunctionKeyText(8, jtextfieldf9.getText());
    applicationSettings.setFunctionKeyText(9, jtextfieldf10.getText());
    
   
    // Quick callsign mode
    applicationSettings.setQuickCallsignMode(checkboxSettingsQuickMode.isSelected());
    
    // Default prefix
    applicationSettings.setDefaultPrefix(textfieldSettingsDefaultPrefix.getText());
    
    // Qso repeat period
    applicationSettings.setQsoRepeatPeriod(Integer.parseInt(jtextfieldQsoRepeatPeriod.getText()));
  }

  
  private void pressedF1()
  {
    String text = applicationSettings.getFunctionKeyText(0); // Get the text for the F1 key
    text = text.replaceAll("\\{mycall\\}", applicationSettings.getMyCallsign()); // Substitute {mycall} with my callsign
    radioController.sendMorse(text); // Send to radio
  }
  
  private void pressedF2()
  {
    radioController.sendMorse(jtextfieldSnt.getText());
  }
  
  private void pressedF3()
  {
    radioController.sendMorse(applicationSettings.getFunctionKeyText(2));
  }
  
  private void pressedF4()
  {
    radioController.sendMorse(applicationSettings.getMyCallsign());
  }
  
  private void pressedF5()
  {
    radioController.sendMorse(getCallsignFromTextField());
  }
  
  private void pressedF6()
  {
    radioController.sendMorse(applicationSettings.getFunctionKeyText(5));
  }
  
  private void pressedF7()
  {
    radioController.sendMorse(applicationSettings.getFunctionKeyText(6));
  }
  
  private void pressedF8()
  {
    radioController.sendMorse(applicationSettings.getFunctionKeyText(7));
  }
  
  private void pressedF9()
  {
    radioController.sendMorse(applicationSettings.getFunctionKeyText(8));
  }
  
  private void pressedF10()
  {
    radioController.sendMorse(applicationSettings.getFunctionKeyText(9));
  }
  
  private void pressedF11()
  {
    // TODO  add to bandmap
  }
  
  private void pressedF12()
  {
    initEntryFields();
  }
  
  
  class LocalRadioControllerListener implements RadioControllerListener
  {

    @Override
    public void frequency()
    {
      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable()
      {
        @Override
        public void run()
        {
          jtextfieldFrequency.setText(radioController.getActiveVfo().toString()+" " +Integer.toString(radioController.getFrequency()));
        }
      });
     
    }

    @Override
    public void mode()
    {
      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable()
      {
        @Override
        public void run()
        {
          jtextfieldMode.setText(radioController.getMode().toString());
        }
      });
    }

    @Override
    public void vfo()
    {
      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable()
      {
        @Override
        public void run()
        {
          jtextfieldMode.setText(radioController.getMode().toString());
          jtextfieldFrequency.setText(radioController.getActiveVfo().toString()+" " +Integer.toString(radioController.getFrequency()));
        }
      });
    }
    
  }
  /**
   * @param args the command line arguments
   */
  public static void main(String args[])
  {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try
    {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
      {
        if ("Nimbus".equals(info.getName()))
        {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    }
    catch (ClassNotFoundException ex)
    {
      java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (InstantiationException ex)
    {
      java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (IllegalAccessException ex)
    {
      java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    catch (javax.swing.UnsupportedLookAndFeelException ex)
    {
      java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        new MainWindow().setVisible(true);
      }
    });
  }

  private class MyDispatcher implements KeyEventDispatcher
  {

    @Override
    public boolean dispatchKeyEvent(KeyEvent evt)
    {
      if (evt.getID() != KeyEvent.KEY_RELEASED) 
        return false;
      
      // Function keys events
      switch (evt.getKeyCode())
      {
        case KeyEvent.VK_F1:
          pressedF1();
          evt.consume();
          break;
        case KeyEvent.VK_F2:
          pressedF2();
          evt.consume();
          break;
        case KeyEvent.VK_F3:
          pressedF3();
          evt.consume();
          break;
        case KeyEvent.VK_F4:
          pressedF4();
          evt.consume();
          break;
        case KeyEvent.VK_F5:
          pressedF5();
          evt.consume();
          break;
        case KeyEvent.VK_F6:
          pressedF6();
          evt.consume();
          break;
        case KeyEvent.VK_F7:
          pressedF7();
          evt.consume();
          break;
        case KeyEvent.VK_F8:
          pressedF8();
          evt.consume();
          break;
        case KeyEvent.VK_F9:
          pressedF9();
          evt.consume();
          break;
        case KeyEvent.VK_F10:
          pressedF10();
          evt.consume();
          break;
        case KeyEvent.VK_F11:
          pressedF11();
          evt.consume();
          break;
        case KeyEvent.VK_F12:
          pressedF12();
          evt.consume();
          break;
      }
      return false;
    }
  }
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.ButtonGroup buttonGroupTypeOfWork;
  private javax.swing.JCheckBox checkboxSettingsQuickMode;
  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton10;
  private javax.swing.JButton jButton11;
  private javax.swing.JButton jButton12;
  private javax.swing.JButton jButton2;
  private javax.swing.JButton jButton3;
  private javax.swing.JButton jButton4;
  private javax.swing.JButton jButton5;
  private javax.swing.JButton jButton6;
  private javax.swing.JButton jButton7;
  private javax.swing.JButton jButton8;
  private javax.swing.JButton jButton9;
  private javax.swing.JButton jButtonCancel;
  private javax.swing.JButton jButtonSave;
  private javax.swing.JComboBox jComboBoxComPort;
  private javax.swing.JDialog jDialogSettings;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel12;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenu jMenu2;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JMenuItem jMenuItem1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JPanel jPanel4;
  private javax.swing.JPanel jPanel5;
  private javax.swing.JPanel jPanel6;
  private javax.swing.JPanel jPanel7;
  private javax.swing.JPanel jPanel8;
  private javax.swing.JPanel jPanelStatusBar;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JScrollPane jScrollPane5;
  private javax.swing.JSplitPane jSplitPane2;
  private javax.swing.JTable jTable1;
  private javax.swing.JTable jTable3;
  private javax.swing.JButton jbuttonDeleteEntry;
  private javax.swing.JComboBox jcomboboxBand;
  private javax.swing.JComboBox jcomboboxMode;
  private javax.swing.JLabel jlabelCallsignStatus;
  private javax.swing.JPanel jpanelAdditionalKeys;
  private javax.swing.JPanel jpanelCallsign;
  private javax.swing.JPanel jpanelCompleteLog;
  private javax.swing.JPanel jpanelEntry;
  private javax.swing.JPanel jpanelFunctionKeys;
  private javax.swing.JPanel jpanelIncomingQso;
  private javax.swing.JPanel jpanelLog;
  private javax.swing.JPanel jpanelRadio;
  private javax.swing.JPanel jpanelSearchLog;
  private javax.swing.JPanel jpanelTypeOfWork;
  private javax.swing.JPanel jpanelVfoA;
  private javax.swing.JRadioButton jradiobuttonCQ;
  private javax.swing.JRadioButton jradiobuttonSP;
  private javax.swing.JSplitPane jsplitLeftPanel;
  private javax.swing.JSplitPane jsplitRighPanel;
  private javax.swing.JTable jtableLog;
  private javax.swing.JTable jtableSearch;
  private javax.swing.JTextField jtextfieldCallsign;
  private javax.swing.JTextField jtextfieldFrequency;
  private javax.swing.JTextField jtextfieldMode;
  private javax.swing.JTextField jtextfieldQsoRepeatPeriod;
  private javax.swing.JTextField jtextfieldRcv;
  private javax.swing.JTextField jtextfieldSnt;
  private javax.swing.JTextField jtextfieldf1;
  private javax.swing.JTextField jtextfieldf10;
  private javax.swing.JTextField jtextfieldf3;
  private javax.swing.JTextField jtextfieldf6;
  private javax.swing.JTextField jtextfieldf7;
  private javax.swing.JTextField jtextfieldf8;
  private javax.swing.JTextField jtextfieldf9;
  private javax.swing.JToggleButton jtogglebuttonConnectToRadio;
  private javax.swing.JTextField textfieldSettingsDefaultPrefix;
  private javax.swing.JTextField textfieldSettingsMyCallsign;
  // End of variables declaration//GEN-END:variables
}
