package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Character;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

// -----------------------------------------------------------------------------------//
public class CharactersPane extends BasePane
// -----------------------------------------------------------------------------------//
{
  private static final int NAME = 0;
  private static final int PASSWORD = 1;
  private static final int IN_MAZE = 2;
  private static final int RACE = 3;
  private static final int CLASS = 4;
  private static final int AGE = 5;
  private static final int STATUS = 6;
  private static final int ALIGNMENT = 7;
  private static final int GOLD = 8;
  private static final int EXPERIENCE = 9;
  private static final int MAXLEVAC = 10;
  private static final int CHAR_LEV = 11;
  private static final int HP_LEFT = 12;
  private static final int HP_MAX = 13;

  TextField[] textOut;
  TextField[] textOut1;

  ComboBox<Character> charactersList = new ComboBox<> ();

  // ---------------------------------------------------------------------------------//
  public CharactersPane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry);

    setColumnConstraints (110, 64, 90, 110, 64);
    setComboBox ("Character", charactersList, wizardry.getCharacters (), (a, b, c) -> update (c));

    GridPane.setColumnSpan (charactersList, 2);

    String[] labelText = { "Name", "Password", "In maze", "Race", "Class", "Age (weeks)", "Status",
        "Alignment", "Gold", "Experience", "Max lev AC", "Level", "HP left", "Max HP" };

    String[] attributesText = { "Strength", "IQ", "Piety", "Vitality", "Agility", "Luck" };

    textOut = createOutputFields (labelText, 0, 1, Pos.CENTER_LEFT, 2);
    textOut1 = createOutputFields (attributesText, 3, 1, Pos.CENTER_RIGHT, 1);
  }

  // ---------------------------------------------------------------------------------//
  private void update (Character character)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut[NAME], character.name);
    setText (textOut[PASSWORD], character.password);
    setText (textOut[IN_MAZE], character.inMaze);
    setText (textOut[RACE], character.race);
    setText (textOut[CLASS], character.characterClass);
    setText (textOut[AGE], getText (character.age));
    setText (textOut[STATUS], character.status);
    setText (textOut[ALIGNMENT], character.alignment);
    setText (textOut[GOLD], character.gold);
    setText (textOut[EXPERIENCE], character.experience);
    setText (textOut[MAXLEVAC], character.maxlevac);
    setText (textOut[CHAR_LEV], character.charlev);
    setText (textOut[HP_LEFT], character.hpLeft);
    setText (textOut[HP_MAX], character.hpMax);

    for (int i = 0; i < textOut1.length; i++)
      setText (textOut1[i], character.attributes[i]);
  }
}
