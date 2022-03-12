package com.bytezone.wizardry.origin.gui;

import java.util.prefs.Preferences;

import com.bytezone.appbase.AppBase;
import com.bytezone.appbase.StatusBar;

import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;

// -----------------------------------------------------------------------------------//
public class Walker extends AppBase
// -----------------------------------------------------------------------------------//
{
  private final Menu menuFile = new Menu ("File");

  private final TabPane tabPane = new TabPane ();
  private final Tab mazeTab = new Tab ("Maze");
  private final Tab viewTab = new Tab ("View");

  // ---------------------------------------------------------------------------------//
  @Override
  protected Parent createContent ()
  // ---------------------------------------------------------------------------------//
  {
    primaryStage.setTitle ("Vista Access Control");

    menuBar.getMenus ().addAll (menuFile);

    tabPane.setSide (Side.BOTTOM);
    tabPane.setTabClosingPolicy (TabClosingPolicy.UNAVAILABLE);
    tabPane.setTabMinWidth (100);
    tabPane.getTabs ().addAll (viewTab, mazeTab);

    viewTab.setContent (new ViewPane ());
    mazeTab.setContent (new MazePane ());

    return tabPane;
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
