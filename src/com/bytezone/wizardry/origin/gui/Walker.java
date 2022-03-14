package com.bytezone.wizardry.origin.gui;

import java.util.prefs.Preferences;

import com.bytezone.appbase.AppBase;
import com.bytezone.appbase.StatusBar;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
public class Walker extends AppBase
// -----------------------------------------------------------------------------------//
{
  private final Menu menuFile = new Menu ("File");

  Canvas maze;
  Canvas view;
  private final BorderPane mainPane = new BorderPane ();

  // ---------------------------------------------------------------------------------//
  @Override
  protected Parent createContent ()
  // ---------------------------------------------------------------------------------//
  {
    primaryStage.setTitle ("Wizardry Dungeon Walker");

    WizardryOrigin wizardry = new WizardryOrigin ();

    menuBar.getMenus ().addAll (menuFile);
    maze = new MazePane (wizardry);
    view = new ViewPane (wizardry);

    mainPane.setLeft (view);
    mainPane.setRight (maze);

    return mainPane;
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
  public static void main (String[] args)
  // ---------------------------------------------------------------------------------//
  {
    Application.launch (args);
  }
}
