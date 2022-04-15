package com.bytezone.mazewalker.gui;

import java.io.File;
import java.util.Arrays;
import java.util.prefs.Preferences;

import com.bytezone.appbase.AppBase;
import com.bytezone.appbase.SaveState;
import com.bytezone.appbase.StatusBar;
import com.bytezone.mazewalker.gui.RecentFiles.FileNameSelectedListener;
import com.bytezone.wizardry.origin.Damage;
import com.bytezone.wizardry.origin.Extra;
import com.bytezone.wizardry.origin.Location;
import com.bytezone.wizardry.origin.MazeCell;
import com.bytezone.wizardry.origin.Message;
import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.Utility;
import com.bytezone.wizardry.origin.WizardryOrigin;
import com.bytezone.wizardry.origin.WizardryOrigin.Direction;
import com.bytezone.wizardry.origin.WizardryOrigin.Trade;

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

  private Stage calculatorStage;
  private Stage charactersStage;
  private Stage monstersStage;
  private Stage itemsStage;
  private Stage rewardsStage;
  private Stage encountersStage;
  private Stage specialsStage;
  private Stage messagesStage;

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
    //    leftVBox.setMinSize (350, 350);
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
    if (calculatorStage == null)
    {
      calculatorStage = new Stage ();
      calculatorStage.setTitle ("Experience Points Calculator");
      experienceCalculator = new ExperienceCalculator (wizardry, calculatorStage);

      Scene scene = new Scene (experienceCalculator, 370, 450);       // wh
      calculatorStage.setScene (scene);
      scene.setOnKeyPressed (e -> experienceCalculator.keyPressed (e));
    }
  }

  // ---------------------------------------------------------------------------------//
  private void buildSpecials ()
  // ---------------------------------------------------------------------------------//
  {
    specialsStage = new Stage ();
    specialsStage.setTitle ("Special squares");
    specialsPane = new SpecialsPane (wizardry, specialsStage);

    Scene scene = new Scene (specialsPane, 1020, 600);               // wh
    specialsStage.setScene (scene);
    scene.setOnKeyPressed (e -> specialsPane.keyPressed (e));
  }

  // ---------------------------------------------------------------------------------//
  private void buildCharacters ()
  // ---------------------------------------------------------------------------------//
  {
    charactersStage = new Stage ();
    charactersStage.setTitle ("Wizardry Characters");
    charactersPane = new CharactersPane (wizardry, charactersStage);

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
    monstersPane = new MonstersPane (wizardry, monstersStage);

    Scene scene = new Scene (monstersPane, 840, 520);         // wh
    monstersStage.setScene (scene);
    scene.setOnKeyPressed (e -> monstersPane.keyPressed (e));
  }

  // ---------------------------------------------------------------------------------//
  private void buildItems ()
  // ---------------------------------------------------------------------------------//
  {
    itemsStage = new Stage ();
    itemsStage.setTitle ("Wizardry Items");
    itemsPane = new ItemsPane (wizardry, itemsStage);

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
    rewardsPane = new RewardsPane (wizardry, rewardsStage);

    Scene scene = new Scene (rewardsPane, 940, 575);            // wh
    rewardsStage.setScene (scene);
    scene.setOnKeyPressed (e -> rewardsPane.keyPressed (e));
  }

  // ---------------------------------------------------------------------------------//
  private void buildEncounters ()
  // ---------------------------------------------------------------------------------//
  {
    encountersStage = new Stage ();
    encountersStage.setTitle ("Wizardry Encounters");
    encountersPane = new EncountersPane (wizardry, encountersStage);

    Scene scene = new Scene (encountersPane, 640, 440);            // wh
    encountersStage.setScene (scene);
    scene.setOnKeyPressed (e -> encountersPane.keyPressed (e));
  }

  // ---------------------------------------------------------------------------------//
  private void buildMessages ()
  // ---------------------------------------------------------------------------------//
  {
    messagesStage = new Stage ();
    messagesStage.setTitle ("Wizardry Messages");
    messagesPane = new MessagesPane (wizardry, messagesStage);

    Scene scene = new Scene (messagesPane, 640, 540);            // wh
    messagesStage.setScene (scene);
    scene.setOnKeyPressed (e -> messagesPane.keyPressed (e));
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
    Extra extra = currentMazeCell.getExtra ();
    boolean lair = currentMazeCell.getLair ();

    if (extra != null)
    {
      description.append ("\n\n" + extra + "\n\n");
      int[] aux = extra.getAux ();

      switch (extra.getSquare ())
      {
        case SCNMSG:
          Message message = wizardry.getMessage (aux[1]);
          description.append (message.getText ());
          if (message.getId () != aux[1])
            description.append ("\n\n(not actual message)");

          switch (aux[2])
          {
            case 1:
              if (aux[0] == 0)
                description.append ("\n\nMessage will not be displayed");
              else if (aux[0] > 0)
                description.append ("\n\n(" + aux[0] + " left)");
              break;

            case 2:                       // special obtain (only blue ribbon so far)
              description.append ("\n\nObtain: " + wizardry.getItemName (aux[0]));
              break;

            case 4:
              description.append ("\n\nSearch (Y/N)?");
              if (aux[0] >= 0)               // monster
                description.append ("\n\nEncounter: " + wizardry.getMonster (aux[0]));
              else if (aux[0] > -1200)
                description.append ("\n\nObtain : " + wizardry.getItem (aux[0] * -1 - 1000));
              else
              {
                Trade trade = wizardry.getItemTrade (aux[0]);
                description.append ("\n\nTrade : " + wizardry.getItemName (trade.item1 ()) + " for "
                    + wizardry.getItemName (trade.item2 ()));
              }
              break;

            case 5:                       // requires
              description.append ("\n\nRequires: " + wizardry.getItemName (aux[0]));
              break;

            case 8:
              description.append ("\n\nReturn to castle??");
              break;

            case 9:
              description.append (String.format ("%n%nLook out : surrounded by fights"));
              break;

            case 10:
              description.append ("\n\nAnswer : " + wizardry.getMessageText (aux[0]));
              break;

            case 11:
              description.append ("\n\nPay : " + wizardry.getMessageText (aux[0]));
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
