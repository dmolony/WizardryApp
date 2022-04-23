package com.bytezone.wizardry.gui;

import com.bytezone.appbase.TabBase;
import com.bytezone.wizardry.origin.MazeLevel;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

//-----------------------------------------------------------------------------------//
public class MazeTab extends TabBase implements ScenarioChangeListener
//-----------------------------------------------------------------------------------//
{
  private WizardryOrigin wizardry;
  private ListView<MazeLevel> mazeLevels = new ListView<> ();
  private MazePane mazePane = new MazePane ();

  // ---------------------------------------------------------------------------------//
  public MazeTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    BorderPane layout = new BorderPane ();
    setContent (layout);
    layout.setLeft (mazeLevels);
    layout.setCenter (mazePane);

    mazeLevels.getSelectionModel ().selectedItemProperty ()
        .addListener (new ChangeListener<MazeLevel> ()
        {
          @Override
          public void changed (ObservableValue<? extends MazeLevel> ov, MazeLevel old_val,
              MazeLevel new_val)
          {
            if (new_val != null)
              mazePane.update (new_val);
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
  public void scenarioChanged (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    mazePane.setWizardry (wizardry);

    mazeLevels.getItems ().clear ();
    mazeLevels.getItems ().addAll (wizardry.getMazeLevels ());
    mazeLevels.getSelectionModel ().select (0);

    refresh ();
  }
}