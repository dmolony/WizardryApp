package com.bytezone.wizardry.gui;

import com.bytezone.appbase.TabBase;
import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
public class MonstersTab extends TabBase implements ScenarioChangeListener
// -----------------------------------------------------------------------------------//
{
  private WizardryOrigin wizardry;
  private ListView<Monster> monsters = new ListView<> ();
  private MonsterPane monstersPane = new MonsterPane ();

  // ---------------------------------------------------------------------------------//
  public MonstersTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    BorderPane layout = new BorderPane ();
    setContent (layout);
    layout.setLeft (monsters);
    layout.setCenter (monstersPane);

    monsters.getSelectionModel ().selectedItemProperty ()
        .addListener (new ChangeListener<Monster> ()
        {
          @Override
          public void changed (ObservableValue<? extends Monster> ov, Monster old_val,
              Monster new_val)
          {
            if (new_val != null)
              monstersPane.update (new_val);
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

    monstersPane.setWizardry (wizardry);

    monsters.getItems ().clear ();
    monsters.getItems ().addAll (wizardry.getMonsters ());
    monsters.getSelectionModel ().select (0);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void scenarioChanged (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    refresh ();
  }
}