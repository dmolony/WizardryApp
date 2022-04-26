package com.bytezone.wizardry;

import java.util.prefs.Preferences;

import com.bytezone.appbase.TabBase;
import com.bytezone.wizardry.origin.Character;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
public class CharactersTab extends TabBase implements ScenarioChangeListener
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_INDEX = "CharactersIndex";

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
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void scenarioChanged (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    characterPane.setWizardry (wizardry);

    characters.getItems ().clear ();
    characters.getItems ().addAll (wizardry.getCharacters ());
    characters.getSelectionModel ().select (0);

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    int index = characters.getSelectionModel ().getSelectedIndex ();
    prefs.putInt (PREFS_INDEX, index);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    int index = prefs.getInt (PREFS_INDEX, -1);
    if (index >= 0)
      characters.getSelectionModel ().select (index);
  }
}
