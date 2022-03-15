package com.bytezone.wizardry.origin.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import com.bytezone.appbase.AppBase;
import com.bytezone.appbase.StatusBar;
import com.bytezone.wizardry.origin.Location;
import com.bytezone.wizardry.origin.Maze.Direction;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

// -----------------------------------------------------------------------------------//
public class MazeWalker extends AppBase
// -----------------------------------------------------------------------------------//
{
  private final Menu menuFile = new Menu ("File");
  private final Menu menuLevels = new Menu ("Levels");

  MazePane mazePane;
  ViewPane viewPane;
  Text text = new Text ();
  private final BorderPane mainPane = new BorderPane ();
  WizardryOrigin wizardry = new WizardryOrigin ();

  private Walker[] walker;
  Walker currentWalker;
  List<WalkerListener> listeners = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  @Override
  protected Parent createContent ()
  // ---------------------------------------------------------------------------------//
  {
    primaryStage.setTitle ("Wizardry Dungeon Walker");

    int levels = wizardry.maze.mazeLevels.size ();
    KeyCode[] keyCodes =
        { KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4, KeyCode.DIGIT5,
            KeyCode.DIGIT6, KeyCode.DIGIT7, KeyCode.DIGIT8, KeyCode.DIGIT9, KeyCode.DIGIT0, };

    for (int i = 0; i < levels; i++)
    {
      MenuItem item = new MenuItem ("Level " + (i + 1));
      menuLevels.getItems ().add (item);
      item.setAccelerator (new KeyCodeCombination (keyCodes[i], KeyCombination.SHORTCUT_DOWN));
      item.setOnAction (actionEvent -> selectMazeLevel (actionEvent));
      item.setId ("" + i);
    }

    walker = new Walker[levels];

    for (int i = 0; i < levels; i++)
      walker[i] = new Walker (wizardry.maze.mazeLevels.get (i), Direction.NORTH,
          new Location (i + 1, 0, 0));

    menuBar.getMenus ().addAll (menuFile, menuLevels);

    mazePane = new MazePane (wizardry);
    viewPane = new ViewPane (wizardry);

    addWalkerListener (mazePane);
    addWalkerListener (viewPane);
    setLevel (0);

    VBox leftPane = new VBox (10);
    VBox rightPane = new VBox (10);
    leftPane.getChildren ().addAll (viewPane, text);
    rightPane.getChildren ().addAll (mazePane);
    text.setText ("hello");

    rightPane.setPadding (new Insets (10));
    leftPane.setPadding (new Insets (10));

    //    view.setStyle ("-fx-border-color: black");
    //    leftPane.setStyle ("-fx-border-color: black");

    mainPane.setLeft (leftPane);
    mainPane.setRight (rightPane);

    return mainPane;
  }

  // ---------------------------------------------------------------------------------//
  private void selectMazeLevel (ActionEvent actionEvent)
  // ---------------------------------------------------------------------------------//
  {
    setLevel (Integer.parseInt (((MenuItem) actionEvent.getSource ()).getId ()));
  }

  // ---------------------------------------------------------------------------------//
  private void setLevel (int level)
  // ---------------------------------------------------------------------------------//
  {
    //    mazePane.setCurrentLevel (level);

    currentWalker = walker[level];
    notifyListeners ();
  }

  // ---------------------------------------------------------------------------------//
  public void addWalkerListener (WalkerListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  private void notifyListeners ()
  // ---------------------------------------------------------------------------------//
  {
    for (WalkerListener listener : listeners)
      listener.walkerMoved (currentWalker);
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
}
