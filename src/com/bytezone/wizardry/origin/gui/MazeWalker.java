package com.bytezone.wizardry.origin.gui;

import java.util.prefs.Preferences;

import com.bytezone.appbase.AppBase;
import com.bytezone.appbase.StatusBar;
import com.bytezone.wizardry.origin.Extra;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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

  // ---------------------------------------------------------------------------------//
  @Override
  protected Parent createContent ()
  // ---------------------------------------------------------------------------------//
  {
    primaryStage.setTitle ("Wizardry Dungeon Walker");

    mazePane = new MazePane (wizardry);
    viewPane = new ViewPane (wizardry);

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
    {
      walker[i] = new Walker (wizardry.maze.mazeLevels.get (i), Direction.NORTH,
          new Location (i + 1, 0, 0));
      walker[i].addWalkerListener (mazePane);
      walker[i].addWalkerListener (viewPane);
    }

    menuBar.getMenus ().addAll (menuFile, menuLevels);

    setLevel (0);

    VBox leftPane = new VBox (10);
    VBox rightPane = new VBox (10);
    text.setFont (new Font ("Courier new", 14));
    leftPane.getChildren ().addAll (viewPane, text);
    rightPane.getChildren ().addAll (mazePane);

    rightPane.setPadding (new Insets (10));
    leftPane.setPadding (new Insets (10));

    //    view.setStyle ("-fx-border-color: black");
    //    leftPane.setStyle ("-fx-border-color: black");

    mainPane.setLeft (leftPane);
    mainPane.setRight (rightPane);

    return mainPane;
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
        currentWalker.turnLeft ();
        updateText ();
        break;

      case W:
        currentWalker.forward ();
        updateText ();
        break;

      case D:
        currentWalker.turnRight ();
        updateText ();
        break;

      case S:
        currentWalker.back ();
        updateText ();
        break;

      default:
        super.keyPressed (keyEvent);
    }
  }

  // ---------------------------------------------------------------------------------//
  private void updateText ()
  // ---------------------------------------------------------------------------------//
  {
    Extra extra = currentWalker.getCurrentMazeCell ().getExtra ();
    boolean fight = currentWalker.getCurrentMazeCell ().getFight ();
    StringBuilder description = new StringBuilder (currentWalker.toString ());

    if (extra != null)
      description.append ("\n\n" + extra);

    if (fight)
      description.append ("\n\nFIGHT");

    text.setText (description.toString ());
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
    currentWalker = walker[level];
    currentWalker.activate ();
    updateText ();
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
