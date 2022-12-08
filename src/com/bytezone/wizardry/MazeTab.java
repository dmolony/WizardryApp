package com.bytezone.wizardry;

import java.util.List;
import java.util.prefs.Preferences;

import com.bytezone.wizardry.data.Location;
import com.bytezone.wizardry.data.MazeCell;
import com.bytezone.wizardry.data.MazeLevel;
import com.bytezone.wizardry.data.Special;
import com.bytezone.wizardry.data.WizardryData;
import com.bytezone.wizardry.data.WizardryData.Direction;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

//-----------------------------------------------------------------------------------//
public class MazeTab extends WizardryTabBase implements MovementListener
//-----------------------------------------------------------------------------------//
{
  private static final String PREFS_INDEX = "MazeIndex";

  private ListView<MazeLevel> mazeLevelsListView = new ListView<> ();

  private MazePane mazePane = new MazePane ();
  private ViewPane viewPane = new ViewPane ();

  private Walker[] walkers;
  private Walker currentWalker;

  private WizardryData wizardry;

  private Text text = new Text ();
  private ScrollPane sp = new ScrollPane (text);
  private VBox vBox = new VBox (10);

  private boolean initialising = true;

  // ---------------------------------------------------------------------------------//
  public MazeTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    vBox.setPadding (new Insets (10));

    text.setFont (new Font ("Courier new", 14));
    sp.setStyle ("-fx-background-color:transparent;");

    BorderPane layout = new BorderPane ();
    setContent (layout);

    layout.setLeft (mazeLevelsListView);
    layout.setCenter (mazePane);
    layout.setRight (vBox);

    mazeLevelsListView.setPrefWidth (LIST_WIDTH);
    mazeLevelsListView.setPlaceholder (new Label ("No Maze Levels"));

    mazePane.setOnMouseClicked (e -> mouseClick (e));
    mazePane.setOnMouseEntered (e -> mazePane.setCursor (Cursor.HAND));
    mazePane.setOnMouseExited (e -> mazePane.setCursor (Cursor.DEFAULT));

    vBox.getChildren ().addAll (viewPane, sp);

    mazeLevelsListView.getSelectionModel ().selectedItemProperty ()
        .addListener (new ChangeListener<MazeLevel> ()
        {
          @Override
          public void changed (ObservableValue<? extends MazeLevel> ov, MazeLevel old_val,
              MazeLevel new_val)
          {
            if (new_val != null)
            {
              mazePane.update (new_val);
              setLevel (new_val.displayLevel - 1);
            }
          }
        });
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    setValid (true);

  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void scenarioChanged (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    mazePane.setWizardry (wizardry);

    List<MazeLevel> mazeLevels = wizardry.getMazeLevels ();
    walkers = new Walker[mazeLevels.size ()];

    for (int i = 0; i < walkers.length; i++)
    {
      walkers[i] = new Walker (mazeLevels, Direction.NORTH, new Location (i + 1, 0, 0));
      walkers[i].addWalkerListener (mazePane);
      walkers[i].addWalkerListener (viewPane);
      walkers[i].addWalkerListener (this);
    }

    mazeLevelsListView.getItems ().clear ();
    mazeLevelsListView.getItems ().addAll (mazeLevels);

    if (!initialising)
      mazeLevelsListView.getSelectionModel ().select (0);

    refresh ();
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
  public void walkerMoved (Walker walker)
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder description = new StringBuilder ();

    description.append (currentWalker.toString ());

    MazeCell currentMazeCell = currentWalker.getCurrentMazeCell ();
    Special special = currentMazeCell.getSpecial ();

    if (special != null)
    {
      int[] aux = special.getAux ();
      description.append ("\n\n" + special + "\n\n");

      if (special.isMessage ())
      {
        description.append (wizardry.getMessageText (aux[1]));
        description.append ("\n\n");
      }
      description.append (special.getText ());
    }

    if (currentMazeCell.isLair ())
      description.append ("\n\nLAIR");

    text.setText (description.toString ());
  }

  // ---------------------------------------------------------------------------------//
  protected void keyPressed (KeyEvent keyEvent)
  // ---------------------------------------------------------------------------------//
  {
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
  private void setLevel (int level)
  // ---------------------------------------------------------------------------------//
  {
    currentWalker = walkers[level];
    currentWalker.notifyListeners ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    int index = mazeLevelsListView.getSelectionModel ().getSelectedIndex ();
    prefs.putInt (PREFS_INDEX, index);

    for (Walker walker : walkers)
      walker.save (prefs);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    if (walkers == null)
      return;

    for (Walker walker : walkers)
      walker.restore (prefs);

    int index = prefs.getInt (PREFS_INDEX, -1);
    mazeLevelsListView.getSelectionModel ().select (index < 0 ? 0 : index);

    initialising = false;
  }
}
