package com.bytezone.mazewalker.gui;

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
public class MazeWalker extends AppBase implements MovementListener
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_FILE_NAME = "FileName";
  private static final KeyCode[] keyCodes =
      { KeyCode.DIGIT1, KeyCode.DIGIT2, KeyCode.DIGIT3, KeyCode.DIGIT4, KeyCode.DIGIT5,
          KeyCode.DIGIT6, KeyCode.DIGIT7, KeyCode.DIGIT8, KeyCode.DIGIT9, KeyCode.DIGIT0, };

  private final Menu menuFile = new Menu ("File");
  private final Menu menuLevels = new Menu ("Levels");
  private final Menu menuTools = new Menu ("Tools");
  private final MenuItem openFileItem = new MenuItem ("Open file ...");
  private final MenuItem experienceItem = new MenuItem ("Experience Points ...");
  private final MenuItem charactersItem = new MenuItem ("Characters ...");
  private final MenuItem monstersItem = new MenuItem ("Monsters ...");
  private final MenuItem itemsItem = new MenuItem ("Items ...");
  private final MenuItem rewardsItem = new MenuItem ("Rewards ...");
  private final MenuItem encountersItem = new MenuItem ("Encounters ...");
  private final MenuItem specialsItem = new MenuItem ("Specials ...");

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

  private Stage calculatorStage;
  private Stage charactersStage;
  private Stage monstersStage;
  private Stage itemsStage;
  private Stage rewardsStage;
  private Stage encountersStage;
  private Stage specialsStage;

  // ---------------------------------------------------------------------------------//
  @Override
  protected Parent createContent ()
  // ---------------------------------------------------------------------------------//
  {
    primaryStage.setTitle ("Wizardry Maze Walker");

    menuBar.getMenus ().addAll (menuFile, menuLevels, menuTools);

    menuFile.getItems ().add (openFileItem);
    openFileItem.setOnAction (e -> getWizardryDisk ());
    openFileItem.setAccelerator (new KeyCodeCombination (KeyCode.O, KeyCombination.SHORTCUT_DOWN));

    menuTools.getItems ().addAll (experienceItem, charactersItem, monstersItem, itemsItem,
        rewardsItem, encountersItem, specialsItem);

    experienceItem.setOnAction (e -> calculatorStage.show ());
    experienceItem
        .setAccelerator (new KeyCodeCombination (KeyCode.X, KeyCombination.SHORTCUT_DOWN));

    charactersItem.setOnAction (e -> charactersStage.show ());
    charactersItem
        .setAccelerator (new KeyCodeCombination (KeyCode.C, KeyCombination.SHORTCUT_DOWN));

    specialsItem.setOnAction (e -> specialsStage.show ());
    specialsItem.setAccelerator (new KeyCodeCombination (KeyCode.S, KeyCombination.SHORTCUT_DOWN));

    monstersItem.setOnAction (e -> monstersStage.show ());
    monstersItem.setAccelerator (new KeyCodeCombination (KeyCode.M, KeyCombination.SHORTCUT_DOWN));

    itemsItem.setOnAction (e -> itemsStage.show ());
    itemsItem.setAccelerator (new KeyCodeCombination (KeyCode.I, KeyCombination.SHORTCUT_DOWN));

    rewardsItem.setOnAction (e -> rewardsStage.show ());
    rewardsItem.setAccelerator (new KeyCodeCombination (KeyCode.R, KeyCombination.SHORTCUT_DOWN));

    encountersItem.setOnAction (e -> encountersStage.show ());
    encountersItem
        .setAccelerator (new KeyCodeCombination (KeyCode.E, KeyCombination.SHORTCUT_DOWN));

    text.setFont (new Font ("Courier new", 14));
    sp.setStyle ("-fx-background-color:transparent;");

    leftVBox.setPadding (new Insets (10));
    rightVBox.setPadding (new Insets (10));
    //    leftVBox.setMinSize (350, 350);
    rightVBox.setMinSize (820, 825);

    //    view.setStyle ("-fx-border-color: black");
    //    leftPane.setStyle ("-fx-border-color: black");

    wizardryFileName = prefs.get (PREFS_FILE_NAME, "");
    if (!wizardryFileName.isEmpty ())
      setWizardryDisk ();

    mainPane.setLeft (leftVBox);
    mainPane.setCenter (rightVBox);

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

    String userHome = System.getProperty ("user.home");
    if (wizardryFileName.startsWith (userHome))
      primaryStage.setTitle ("~" + wizardryFileName.substring (userHome.length ()));
    else
      primaryStage.setTitle (wizardryFileName);

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
    buildMaze ();
  }

  // ---------------------------------------------------------------------------------//
  private void buildCalculator ()
  // ---------------------------------------------------------------------------------//
  {
    if (calculatorStage == null)
    {
      calculatorStage = new Stage ();
      calculatorStage.setTitle ("Experience Points Calculator");
      ExperienceCalculator experienceCalculator =
          new ExperienceCalculator (wizardry, calculatorStage);

      Scene scene = new Scene (experienceCalculator, 370, 450);       // wh
      calculatorStage.setScene (scene);
      scene.setOnKeyPressed (e -> experienceCalculator.keyPressed (e));
    }
  }

  // ---------------------------------------------------------------------------------//
  private void buildMaze ()
  // ---------------------------------------------------------------------------------//
  {
    specialsStage = new Stage ();
    specialsStage.setTitle ("Special squares");
    SpecialsPane specialsPane = new SpecialsPane (wizardry, specialsStage);

    Scene scene = new Scene (specialsPane, 910, 810);               // wh
    specialsStage.setScene (scene);
    scene.setOnKeyPressed (e -> specialsPane.keyPressed (e));
  }

  // ---------------------------------------------------------------------------------//
  private void buildCharacters ()
  // ---------------------------------------------------------------------------------//
  {
    charactersStage = new Stage ();
    charactersStage.setTitle ("Wizardry Characters");
    CharactersPane charactersPane = new CharactersPane (wizardry, charactersStage);

    Scene scene = new Scene (charactersPane, 910, 810);       // wh
    charactersStage.setScene (scene);
    scene.setOnKeyPressed (e -> charactersPane.keyPressed (e));
  }

  // ---------------------------------------------------------------------------------//
  private void buildMonsters ()
  // ---------------------------------------------------------------------------------//
  {
    monstersStage = new Stage ();
    monstersStage.setTitle ("Wizardry Monsters");
    MonstersPane monstersPane = new MonstersPane (wizardry, monstersStage);

    Scene scene = new Scene (monstersPane, 820, 520);         // wh
    monstersStage.setScene (scene);
    scene.setOnKeyPressed (e -> monstersPane.keyPressed (e));
  }

  // ---------------------------------------------------------------------------------//
  private void buildItems ()
  // ---------------------------------------------------------------------------------//
  {
    itemsStage = new Stage ();
    itemsStage.setTitle ("Wizardry Items");
    ItemsPane itemsPane = new ItemsPane (wizardry, itemsStage);

    Scene scene = new Scene (itemsPane, 770, 575);            // wh
    itemsStage.setScene (scene);
    scene.setOnKeyPressed (e -> itemsPane.keyPressed (e));
  }

  // ---------------------------------------------------------------------------------//
  private void buildRewards ()
  // ---------------------------------------------------------------------------------//
  {
    rewardsStage = new Stage ();
    rewardsStage.setTitle ("Wizardry Rewards");
    RewardsPane rewardsPane = new RewardsPane (wizardry, rewardsStage);

    Scene scene = new Scene (rewardsPane, 770, 575);            // wh
    rewardsStage.setScene (scene);
    scene.setOnKeyPressed (e -> rewardsPane.keyPressed (e));
  }

  // ---------------------------------------------------------------------------------//
  private void buildEncounters ()
  // ---------------------------------------------------------------------------------//
  {
    encountersStage = new Stage ();
    encountersStage.setTitle ("Wizardry Encounters");
    EncountersPane encountersPane = new EncountersPane (wizardry, encountersStage);

    Scene scene = new Scene (encountersPane, 640, 440);            // wh
    encountersStage.setScene (scene);
    scene.setOnKeyPressed (e -> encountersPane.keyPressed (e));
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

    Extra extra = currentWalker.getCurrentMazeCell ().getExtra ();
    boolean lair = currentWalker.getCurrentMazeCell ().getLair ();

    if (extra != null)
    {
      description.append ("\n\n" + extra + "\n\n");
      int[] aux = extra.getAux ();
      //      int val = aux[0];

      switch (extra.getSquare ())
      {
        case SCNMSG:
          int msg = aux[1];
          description.append (wizardry.getMessage (msg));

          switch (aux[2])
          {
            case 1:
              if (aux[0] == 0)
                description.append ("\n\nMessage will not be displayed");
              else if (aux[0] > 0)
                description.append ("\n\n(" + aux[0] + " left)");
              break;

            case 2:                       // special obtain (only blue ribbon so far)
              description.append ("\n\nObtain: " + wizardry.getItem (aux[0]));
              break;

            case 4:                       // monster or obtain
              if (aux[0] >= 0)               // monster
                description.append ("\n\nEncounter: " + wizardry.getMonster (aux[0]));
              else
                description.append ("\n\nObtain: " + wizardry.getItem (Math.abs (aux[0]) - 1000));
              break;

            case 5:                       // requires
              description.append ("\n\nRequires: " + wizardry.getItem (aux[0]));
              break;

            case 8:
              description.append ("\n\nReturn to castle??");
              break;

            case 9:
              description.append (String.format ("%n%nWhat does AUX0 = %04X mean?", aux[0]));
              break;
          }
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
