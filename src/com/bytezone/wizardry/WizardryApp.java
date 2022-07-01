package com.bytezone.wizardry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

import com.bytezone.appbase.AppBase;
import com.bytezone.appbase.RecentFiles;
import com.bytezone.appbase.RecentFiles.FileNameSelectedListener;
import com.bytezone.appbase.StatusBar;
import com.bytezone.wizardry.data.DiskFormatException;
import com.bytezone.wizardry.data.Wizardry;
import com.bytezone.wizardry.data.WizardryData;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

// -----------------------------------------------------------------------------------//
public class WizardryApp extends AppBase implements FileNameSelectedListener
// -----------------------------------------------------------------------------------//
{
  private final Menu menuFile = new Menu ("File");
  private final MenuItem openFileItem = new MenuItem ("Open file...");
  private final Menu recentFilesMenu = new Menu ("Recent files");

  private final RecentFiles recentFiles = new RecentFiles (recentFilesMenu);
  private WizardryData wizardry;
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

    wizardryTabPane = new WizardryTabPane ("Wizardry");

    for (Tab tab : wizardryTabPane.getTabs ())
      addScenarioChangeListener ((ScenarioChangeListener) tab);

    // recentFiles must be restored first
    saveStateList.addAll (Arrays.asList (recentFiles, wizardryTabPane));
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
      case A:
      case S:
      case D:
      case W:
        wizardryTabPane.mazeTab.keyPressed (keyEvent);
        break;

      default:
    }
  }

  // ---------------------------------------------------------------------------------//
  private void getWizardryDisk ()
  // ---------------------------------------------------------------------------------//
  {
    File file = recentFiles.chooseFile ("Select Wizardry Disk");

    if (file != null && file.isFile ())
      fileNameSelected (file.getAbsolutePath ());
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void fileNameSelected (String fileName)
  // ---------------------------------------------------------------------------------//
  {
    try
    {
      wizardry = Wizardry.getWizardryData (fileName);
      recentFiles.addLastFileName (fileName);

      for (ScenarioChangeListener listener : listeners)
        listener.scenarioChanged (wizardry);

      primaryStage.setTitle (wizardry.getScenarioName ());
    }
    catch (DiskFormatException | FileNotFoundException e)
    {
      showAlert (AlertType.ERROR, "Disk Format Error", e.getMessage ());
    }
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
  void addScenarioChangeListener (ScenarioChangeListener listener)
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
