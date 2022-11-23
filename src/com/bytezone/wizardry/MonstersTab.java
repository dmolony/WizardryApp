package com.bytezone.wizardry;

import java.util.prefs.Preferences;

import com.bytezone.wizardry.data.Monster;
import com.bytezone.wizardry.data.WizardryData;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
public class MonstersTab extends WizardryTabBase
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_INDEX = "MonstersIndex";

  private ListView<Monster> monsters = new ListView<> ();
  private MonsterRootPane monsterPane = new MonsterRootPane ();

  // ---------------------------------------------------------------------------------//
  public MonstersTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    BorderPane layout = new BorderPane ();
    setContent (layout);
    layout.setLeft (monsters);
    layout.setCenter (monsterPane);

    monsters.setPrefWidth (LIST_WIDTH);
    monsters.setPlaceholder (new Label ("No Monsters"));

    monsters.getSelectionModel ().selectedItemProperty ()
        .addListener (new ChangeListener<Monster> ()
        {
          @Override
          public void changed (ObservableValue<? extends Monster> ov, Monster old_val,
              Monster new_val)
          {
            if (new_val != null)
              monsterPane.update (new_val);
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
    monsterPane.setWizardry (wizardry);

    monsters.getItems ().clear ();
    monsters.getItems ().addAll (wizardry.getMonsters ());
    monsters.getSelectionModel ().select (0);

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    int index = monsters.getSelectionModel ().getSelectedIndex ();
    prefs.putInt (PREFS_INDEX, index);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    int index = prefs.getInt (PREFS_INDEX, -1);
    if (index >= 0)
    {
      monsters.getSelectionModel ().select (index);
      monsters.scrollTo (index);
    }
  }
}
