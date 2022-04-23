package com.bytezone.wizardry.gui;

import com.bytezone.appbase.TabBase;
import com.bytezone.wizardry.origin.Character;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
public class CharactersTab extends TabBase implements ScenarioChangeListener
// -----------------------------------------------------------------------------------//
{
  private WizardryOrigin wizardry;
  private ListView<Character> characters = new ListView<> ();
  private CharacterPane characterPane = new CharacterPane ();

  // ---------------------------------------------------------------------------------//
  public CharactersTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    BorderPane layout = new BorderPane ();
    setContent (layout);
    layout.setLeft (characters);
    layout.setCenter (characterPane);

    characters.getSelectionModel ().selectedItemProperty ()
        .addListener (new ChangeListener<Character> ()
        {
          @Override
          public void changed (ObservableValue<? extends Character> ov, Character old_val,
              Character new_val)
          {
            if (new_val != null)
              characterPane.update (new_val);
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

    characterPane.setWizardry (wizardry);

    characters.getItems ().clear ();
    characters.getItems ().addAll (wizardry.getCharacters ());
    characters.getSelectionModel ().select (0);
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
