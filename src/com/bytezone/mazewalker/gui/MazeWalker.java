package com.bytezone.mazewalker.gui;

import java.io.File;
import java.util.Arrays;
import java.util.prefs.Preferences;

import com.bytezone.appbase.AppBase;
import com.bytezone.appbase.SaveState;
import com.bytezone.appbase.StatusBar;
import com.bytezone.mazewalker.gui.RecentFiles.FileNameSelectedListener;
import com.bytezone.wizardry.origin.Damage;
import com.bytezone.wizardry.origin.Location;
import com.bytezone.wizardry.origin.MazeCell;
import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.Special;
import com.bytezone.wizardry.origin.Utility;
import com.bytezone.wizardry.origin.WizardryOrigin;
import com.bytezone.wizardry.origin.WizardryOrigin.Direction;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class MazeWalker extends AppBase
    implements MovementListener, SaveState, FileNameSelectedListener
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_FILE_NAME = "FileName";
  private static final KeyCode[] keyCodes =
      { KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4, KeyCode.DIGIT5,
          KeyCode.DIGIT6, KeyCode.DIGIT7, KeyCode.DIGIT8, KeyCode.DIGIT9, KeyCode.DIGIT0, };

  private final Menu menuFile = new Menu ("File");
  private final Menu menuLevels = new Menu ("Levels");
  private final Menu menuTools = new Menu ("Tools");

  private final MenuItem openFileItem = new MenuItem ("Open file...");
  private final Menu recentFilesMenu = new Menu ("Recent files");

  private final MenuItem experienceItem = new MenuItem ("Experience Points...");
  private final MenuItem charactersItem = new MenuItem ("Characters...");
  private final MenuItem monstersItem = new MenuItem ("Monsters...");
  private final MenuItem itemsItem = new MenuItem ("Items...");
  private final MenuItem rewardsItem = new MenuItem ("Rewards...");
  private final MenuItem encountersItem = new MenuItem ("Encounters...");
  private final MenuItem specialsItem = new MenuItem ("Specials...");
  private final MenuItem messagesItem = new MenuItem ("Messages...");

  private MazeWalkerPane mazePane;
  private ViewPane viewPane;

  private VBox leftVBox = new VBox (10);
  private VBox rightVBox = new VBox (10);
  private Text text = new Text ();
  private ScrollPane sp = new ScrollPane (text);

  private final BorderPane mainPane = new BorderPane ();
  private WizardryOrigin wizardry;

  private Walker[] walker;
  private Walker currentWalker;

  private String wizardryFileName;
  private RecentFiles recentFiles = new RecentFiles (recentFilesMenu);

  SpecialsPane specialsPane;
  MonstersPane monstersPane;
  CharactersPane charactersPane;
  ItemsPane itemsPane;
  RewardsPane rewardsPane;
  EncountersPane encountersPane;
  MessagesPane messagesPane;
  ExperienceCalculator experienceCalculator;

  // ---------------------------------------------------------------------------------//
  @Override
  protected Parent createContent ()
  // ---------------------------------------------------------------------------------//
  {
    primaryStage.setTitle ("Wizardry Maze Walker");

    menuBar.getMenus ().addAll (menuFile, menuLevels, menuTools);

    addItem (menuFile, openFileItem, KeyCode.O, e -> getWizardryDisk ());
    addItem (menuTools, charactersItem, KeyCode.C, e -> charactersPane.show ());
    addItem (menuTools, specialsItem, KeyCode.S, e -> specialsPane.show ());
    addItem (menuTools, messagesItem, KeyCode.T, e -> messagesPane.show ());
    addItem (menuTools, monstersItem, KeyCode.M, e -> monstersPane.show ());
    addItem (menuTools, itemsItem, KeyCode.I, e -> itemsPane.show ());
    addItem (menuTools, rewardsItem, KeyCode.R, e -> rewardsPane.show ());
    addItem (menuTools, encountersItem, KeyCode.E, e -> encountersPane.show ());
    addItem (menuTools, experienceItem, KeyCode.X, e -> experienceCalculator.show ());

    menuFile.getItems ().add (recentFilesMenu);

    text.setFont (new Font ("Courier new", 14));
    sp.setStyle ("-fx-background-color:transparent;");

    leftVBox.setPadding (new Insets (10));
    rightVBox.setPadding (new Insets (10));
    rightVBox.setMinSize (820, 825);

    //    view.setStyle ("-fx-border-color: black");
    //    leftPane.setStyle ("-fx-border-color: black");

    mainPane.setLeft (leftVBox);
    mainPane.setCenter (rightVBox);

    saveStateList.addAll (Arrays.asList (this));
    recentFiles.addListener (this);

    return mainPane;
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

    primaryStage.setTitle (Utility.removeUserName (wizardryFileName));

    mazePane = new MazeWalkerPane (wizardry);
    viewPane = new ViewPane (wizardry);

    mazePane.setOnMouseClicked (e -> mouseClick (e));
    mazePane.setOnMouseEntered (e -> mazePane.setCursor (Cursor.HAND));
    mazePane.setOnMouseExited (e -> mazePane.setCursor (Cursor.DEFAULT));

    leftVBox.getChildren ().clear ();
    leftVBox.getChildren ().addAll (viewPane, sp);

    rightVBox.getChildren ().clear ();
    rightVBox.getChildren ().addAll (mazePane);

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
      walker[i].addWalkerListener (this);
    }

    setLevel (0);

    buildCalculator ();
    buildCharacters ();
    buildMonsters ();
    buildItems ();
    buildRewards ();
    buildEncounters ();
    buildSpecials ();
    buildMessages ();
  }

  // ---------------------------------------------------------------------------------//
  private void buildCalculator ()
  // ---------------------------------------------------------------------------------//
  {
    Stage stage = getStage ("Experience Points");
    experienceCalculator = new ExperienceCalculator (wizardry, stage);
    stage.setScene (getScene (experienceCalculator, 370, 450));
  }

  // ---------------------------------------------------------------------------------//
  private void buildSpecials ()
  // ---------------------------------------------------------------------------------//
  {
    Stage stage = getStage ("Special squares");
    specialsPane = new SpecialsPane (wizardry, stage);
    stage.setScene (getScene (specialsPane, 1020, 600));
  }

  // ---------------------------------------------------------------------------------//
  private void buildCharacters ()
  // ---------------------------------------------------------------------------------//
  {
    Stage stage = getStage ("Characters");
    charactersPane = new CharactersPane (wizardry, stage);
    stage.setScene (getScene (charactersPane, 910, 810));
  }

  // ---------------------------------------------------------------------------------//
  private void buildMonsters ()
  // ---------------------------------------------------------------------------------//
  {
    Stage stage = getStage ("Monsters");
    monstersPane = new MonstersPane (wizardry, stage);
    stage.setScene (getScene (monstersPane, 840, 520));
  }

  // ---------------------------------------------------------------------------------//
  private void buildItems ()
  // ---------------------------------------------------------------------------------//
  {
    Stage stage = getStage ("Items");
    itemsPane = new ItemsPane (wizardry, stage);
    stage.setScene (getScene (itemsPane, 770, 575));
  }

  // ---------------------------------------------------------------------------------//
  private void buildRewards ()
  // ---------------------------------------------------------------------------------//
  {
    Stage stage = getStage ("Rewards");
    rewardsPane = new RewardsPane (wizardry, stage);
    stage.setScene (getScene (rewardsPane, 940, 575));
  }

  // ---------------------------------------------------------------------------------//
  private void buildEncounters ()
  // ---------------------------------------------------------------------------------//
  {
    Stage stage = getStage ("Encounters");
    encountersPane = new EncountersPane (wizardry, stage);
    stage.setScene (getScene (encountersPane, 640, 440));
  }

  // ---------------------------------------------------------------------------------//
  private void buildMessages ()
  // ---------------------------------------------------------------------------------//
  {
    Stage stage = getStage ("Messages");
    messagesPane = new MessagesPane (wizardry, stage);
    stage.setScene (getScene (messagesPane, 640, 540));
  }

  // ---------------------------------------------------------------------------------//
  private Stage getStage (String name)
  // ---------------------------------------------------------------------------------//
  {
    Stage stage = new Stage ();
    stage.setTitle (wizardry.getScenarioName () + " - " + name);
    return stage;
  }

  // ---------------------------------------------------------------------------------//
  private Scene getScene (DataPane pane, int width, int height)
  // ---------------------------------------------------------------------------------//
  {
    Scene scene = new Scene (pane, width, height);
    scene.setOnKeyPressed (e -> pane.keyPressed (e));
    return scene;
  }

  // ---------------------------------------------------------------------------------//
  private void mouseClick (MouseEvent e)
  // ---------------------------------------------------------------------------------//
  {
    Location location = mazePane.getLocation (e.getX (), e.getY ());
    currentWalker.setLocation (location);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  protected void keyPressed (KeyEvent keyEvent)
  // ---------------------------------------------------------------------------------//
  {
    super.keyPressed (keyEvent);

    if (keyEvent.isMetaDown ())
      return;

    switch (keyEvent.getCode ())
    {
      case A:
        currentWalker.turnLeft ();
        break;

      case W:
        currentWalker.forward ();
        break;

      case D:
        currentWalker.turnRight ();
        break;

      case S:
        currentWalker.back ();
        break;

      default:
        break;
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void walkerMoved (Walker walker)
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder description = new StringBuilder ();
    description.append (currentWalker.toString ());

    MazeCell currentMazeCell = currentWalker.getCurrentMazeCell ();
    Special special = currentMazeCell.getSpecial ();
    boolean lair = currentMazeCell.getLair ();

    if (special != null)
    {
      description.append ("\n\n" + special + "\n\n");
      int[] aux = special.getAux ();

      switch (special.getSquare ())
      {
        case SCNMSG:
          description.append (wizardry.getMessageText (aux[1]));
          description.append ("\n\n");
          description.append (special.getText ());
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
          description.append (String.format ("Elevator levels : %d to %d", aux[2], aux[1]));
          break;

        case ROCKWATE:
          description.append ("You are in rock/water");
          break;

        case FIZZLE:
          description.append ("Spells cannot be cast here");
          break;

        case ENCOUNTE:
          Monster monster = wizardry.getMonster (aux[2]);
          String when = aux[0] == -1 ? "always" : aux[0] + " left";
          description.append (String.format ("An encounter : %s (%s)", monster, when));
          if (!lair)
            description.append ("\n\nError - room is not a LAIR");
          break;

        case NORMAL:
          break;
      }
    }

    if (lair)
      description.append ("\n\nLAIR");

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
