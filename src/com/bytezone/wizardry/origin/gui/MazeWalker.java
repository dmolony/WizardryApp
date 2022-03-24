package com.bytezone.wizardry.origin.gui;

import java.io.File;
import java.util.prefs.Preferences;

import com.bytezone.appbase.AppBase;
import com.bytezone.appbase.StatusBar;
import com.bytezone.wizardry.origin.Damage;
import com.bytezone.wizardry.origin.Extra;
import com.bytezone.wizardry.origin.Location;
import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.WizardryOrigin;
import com.bytezone.wizardry.origin.WizardryOrigin.Direction;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

// -----------------------------------------------------------------------------------//
public class MazeWalker extends AppBase
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_FILE_NAME = "FileName";
  private static final KeyCode[] keyCodes =
      { KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4, KeyCode.DIGIT5,
          KeyCode.DIGIT6, KeyCode.DIGIT7, KeyCode.DIGIT8, KeyCode.DIGIT9, KeyCode.DIGIT0, };

  private final Menu menuFile = new Menu ("File");
  private final Menu menuLevels = new Menu ("Levels");
  private final MenuItem openFileItem = new MenuItem ("Open file ...");

  MazePane mazePane;
  ViewPane viewPane;

  VBox leftPane = new VBox (10);
  VBox rightPane = new VBox (10);
  Text text = new Text ();
  ScrollPane sp = new ScrollPane (text);

  private final BorderPane mainPane = new BorderPane ();
  WizardryOrigin wizardry;

  private Walker[] walker;
  Walker currentWalker;

  private String wizardryFileName;

  // ---------------------------------------------------------------------------------//
  @Override
  protected Parent createContent ()
  // ---------------------------------------------------------------------------------//
  {
    primaryStage.setTitle ("Wizardry Dungeon Walker");

    menuBar.getMenus ().addAll (menuFile, menuLevels);

    menuFile.getItems ().add (openFileItem);
    openFileItem.setOnAction (e -> getWizardryDisk ());
    openFileItem.setAccelerator (new KeyCodeCombination (KeyCode.O, KeyCombination.SHORTCUT_DOWN));

    text.setFont (new Font ("Courier new", 14));
    sp.setStyle ("-fx-background-color:transparent;");

    rightPane.setPadding (new Insets (10));
    leftPane.setPadding (new Insets (10));

    //    view.setStyle ("-fx-border-color: black");
    //    leftPane.setStyle ("-fx-border-color: black");

    wizardryFileName = prefs.get (PREFS_FILE_NAME, "");
    if (!wizardryFileName.isEmpty ())
      setWizardryDisk ();

    mainPane.setLeft (leftPane);
    mainPane.setRight (rightPane);

    return mainPane;
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
      prefs.put (PREFS_FILE_NAME, wizardryFileName);
      setWizardryDisk ();
    }
  }

  // ---------------------------------------------------------------------------------//
  private void setWizardryDisk ()
  // ---------------------------------------------------------------------------------//
  {
    wizardry = new WizardryOrigin (wizardryFileName);

    mazePane = new MazePane (wizardry);
    viewPane = new ViewPane (wizardry);

    leftPane.getChildren ().clear ();
    leftPane.getChildren ().addAll (viewPane, sp);

    rightPane.getChildren ().clear ();
    rightPane.getChildren ().addAll (mazePane);

    int levels = wizardry.getMazeLevels ().size ();
    walker = new Walker[levels];

    menuLevels.getItems ().clear ();
    for (int i = 0; i < levels; i++)
    {
      MenuItem item = new MenuItem ("Level " + (i + 1));
      menuLevels.getItems ().add (item);
      item.setAccelerator (new KeyCodeCombination (keyCodes[i], KeyCombination.SHORTCUT_DOWN));
      item.setOnAction (actionEvent -> selectMazeLevel (actionEvent));
      item.setId ("" + i);

      walker[i] = new Walker (wizardry.getMazeLevels ().get (i), Direction.NORTH,
          new Location (i + 1, 0, 0));
      walker[i].addWalkerListener (mazePane);
      walker[i].addWalkerListener (viewPane);
    }

    setLevel (0);
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
        break;
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
    {
      description.append ("\n\n" + extra + "\n\n");
      int[] aux = extra.getAux ();

      switch (extra.getSquare ())
      {
        case SCNMSG:
          int msg = aux[1];
          description.append (wizardry.getMessage (msg));
          break;

        case STAIRS:
          Location location = new Location (aux);
          description.append (String.format ("Stairs to: %s", location));
          break;

        case PIT:
          Damage damage = new Damage (aux);
          description.append (String.format ("Pit - %s", damage));
          break;

        case CHUTE:
          location = new Location (aux);
          description.append (String.format ("Chute to %s", location));
          break;

        case SPINNER:
          description.append ("Spinner");
          break;

        case DARK:
          description.append ("It is very dark here");
          break;

        case TRANSFER:
          location = new Location (aux);
          description.append (String.format ("Teleport to: %s", location));
          break;

        case OUCHY:
          break;

        case BUTTONZ:
          description.append (String.format ("Elevator levels : %d : %d", aux[2], aux[1]));
          break;

        case ROCKWATE:
          description.append ("You are in rock/water");
          break;

        case FIZZLE:
          description.append ("Spells cannot be cast here");
          break;

        case ENCOUNTE:
          Monster monster = wizardry.getMonster (aux[2]);
          description.append (String.format ("An encounter : %s", monster));
          break;

        case NORMAL:
          break;
      }
    }

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

  // ---------------------------------------------------------------------------------//
  //  private void validateFileNameOrExit ()
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    wizardryFileName = prefs.get (PREFS_FILE_NAME, "");
  //    if (wizardryFileName.isEmpty ())
  //    {
  //      AppBase.showAlert (AlertType.INFORMATION, "Wizardry Disk File",
  //          "Please choose the file which contains your Wizardry disk.");
  //    }
  //    else
  //    {
  //      File file = new File (wizardryFileName);
  //      if (!file.exists ())
  //        wizardryFileName = "";
  //    }
  //
  //    if (wizardryFileName.isEmpty () && !setWizardryDisk ())
  //    {
  //      Platform.exit ();
  //      System.exit (0);
  //    }
  //  }
}
