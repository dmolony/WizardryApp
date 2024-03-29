package com.bytezone.wizardry;

import java.util.prefs.Preferences;

import com.bytezone.wizardry.data.MazeLevel;
import com.bytezone.wizardry.data.WizardryData;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
public class SpecialsTab extends WizardryTabBase
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_INDEX = "SpecialsIndex";

  private ListView<MazeLevel> mazeLevels = new ListView<> ();
  private SpecialsRootPane specialsPane = new SpecialsRootPane ();

  // ---------------------------------------------------------------------------------//
  public SpecialsTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    BorderPane layout = new BorderPane ();
    setContent (layout);
    layout.setLeft (mazeLevels);
    layout.setCenter (specialsPane);

    mazeLevels.setPrefWidth (LIST_WIDTH);
    mazeLevels.setPlaceholder (new Label ("No Maze Levels"));

    mazeLevels.getSelectionModel ().selectedItemProperty ()
        .addListener (new ChangeListener<MazeLevel> ()
        {
          @Override
          public void changed (ObservableValue<? extends MazeLevel> ov, MazeLevel old_val,
              MazeLevel new_val)
          {
            if (new_val != null)
              specialsPane.update (new_val);
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
    specialsPane.setWizardry (wizardry);

    mazeLevels.getItems ().clear ();
    mazeLevels.getItems ().addAll (wizardry.getMazeLevels ());
    mazeLevels.getSelectionModel ().select (0);

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    int index = mazeLevels.getSelectionModel ().getSelectedIndex ();
    prefs.putInt (PREFS_INDEX, index);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    int index = prefs.getInt (PREFS_INDEX, -1);
    if (index >= 0)
      mazeLevels.getSelectionModel ().select (index);
  }
}
