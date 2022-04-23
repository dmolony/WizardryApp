package com.bytezone.wizardry.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

import com.bytezone.appbase.AppBase;
import com.bytezone.appbase.SaveState;
import com.bytezone.appbase.StatusBar;
import com.bytezone.mazewalker.gui.RecentFiles;
import com.bytezone.mazewalker.gui.RecentFiles.FileNameSelectedListener;
import com.bytezone.wizardry.origin.Utility;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

// -----------------------------------------------------------------------------------//
public class WizardryApp extends AppBase implements SaveState, FileNameSelectedListener
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_FILE_NAME = "FileName";

  private final Menu menuFile = new Menu ("File");

  private final MenuItem openFileItem = new MenuItem ("Open file...");
  private final Menu recentFilesMenu = new Menu ("Recent files");

  private RecentFiles recentFiles = new RecentFiles (recentFilesMenu);
  private String wizardryFileName;
  private WizardryOrigin wizardry;
  private WizardryTabPane wizardryTabPane;

  private List<ScenarioChangeListener> listeners = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  @Override
  protected Parent createContent ()
  // ---------------------------------------------------------------------------------//
  {
    primaryStage.setTitle ("Wizardry");

    menuBar.getMenus ().addAll (menuFile);

    addItem (menuFile, openFileItem, KeyCode.O, e -> getWizardryDisk ());
    menuFile.getItems ().add (recentFilesMenu);

    wizardryTabPane = new WizardryTabPane (this, "Wizardry");

    saveStateList.addAll (Arrays.asList (this));
    recentFiles.addListener (this);

    return wizardryTabPane;
  }

  // ---------------------------------------------------------------------------------//
  private void addItem (Menu menu, MenuItem menuItem, KeyCode keyCode,
      EventHandler<ActionEvent> event)
  // ---------------------------------------------------------------------------------//
  {
    menu.getItems ().add (menuItem);
    menuItem.setOnAction (event);
    menuItem.setAccelerator (new KeyCodeCombination (keyCode, KeyCombination.SHORTCUT_DOWN));
  }

  // ---------------------------------------------------------------------------------//
  @Override
  protected void keyPressed (KeyEvent keyEvent)
  // ---------------------------------------------------------------------------------//
  {
    super.keyPressed (keyEvent);

    switch (keyEvent.getCode ())
    {
      case C:       // characters
      case D:       // disk
      case M:       // monsters
      case I:       // items
      case E:       // encounters
      case R:       // rewards
      case S:       // specials
      case T:       // messages
        wizardryTabPane.keyPressed (keyEvent);
        break;

      default:
        break;
    }
  }

  // ---------------------------------------------------------------------------------//
  private void getWizardryDisk ()
  // ---------------------------------------------------------------------------------//
  {
    FileChooser fileChooser = new FileChooser ();
    fileChooser.setTitle ("Select Wizardry Disk");

    if (wizardryFileName.isEmpty ())
      fileChooser.setInitialDirectory (new File (System.getProperty ("user.home")));
    else
      fileChooser.setInitialDirectory (new File (wizardryFileName).getParentFile ());

    File file = fileChooser.showOpenDialog (null);
    if (file != null && file.isFile () && !file.getAbsolutePath ().equals (wizardryFileName))
    {
      wizardryFileName = file.getAbsolutePath ();
      setWizardryDisk ();
    }
  }

  // ---------------------------------------------------------------------------------//
  private void setWizardryDisk ()
  // ---------------------------------------------------------------------------------//
  {
    wizardry = new WizardryOrigin (wizardryFileName);
    recentFiles.addLastFileName (wizardryFileName);

    for (ScenarioChangeListener listener : listeners)
      listener.scenarioChanged (wizardry);

    primaryStage.setTitle (Utility.removeUserName (wizardryFileName));
  }

  // ---------------------------------------------------------------------------------//
  @Override
  protected Preferences getPreferences ()
  // ---------------------------------------------------------------------------------//
  {
    return Preferences.userNodeForPackage (this.getClass ());
  }

  // ---------------------------------------------------------------------------------//
  @Override
  protected StatusBar getStatusBar ()
  // ---------------------------------------------------------------------------------//
  {
    return null;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    prefs.put (PREFS_FILE_NAME, wizardryFileName);

    recentFiles.save (prefs);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    recentFiles.restore (prefs);

    wizardryFileName = prefs.get (PREFS_FILE_NAME, "");
    if (!wizardryFileName.isEmpty ())
      setWizardryDisk ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void fileNameSelected (String fileName)
  // ---------------------------------------------------------------------------------//
  {
    wizardryFileName = fileName;
    setWizardryDisk ();
  }

  // ---------------------------------------------------------------------------------//
  public void addScenarioChangeListener (ScenarioChangeListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  public static void main (String[] args)
  // ---------------------------------------------------------------------------------//
  {
    Application.launch (args);
  }
}
