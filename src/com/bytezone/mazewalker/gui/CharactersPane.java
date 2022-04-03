package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Character;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

  String[] labelText =
      { "Name", "Password", "In maze", "Race", "Class", "Age (weeks)", "Status", "Alignment" };

  String[] attributesText = { "Strength", "IQ", "Piety", "Vitality", "Agility", "Luck" };

  Label[] labels = new Label[labelText.length];
  TextField[] textOut = new TextField[labelText.length];

  Label[] labels1 = new Label[attributesText.length];
  TextField[] textOut1 = new TextField[attributesText.length];

  ComboBox<Character> charactersList = new ComboBox<> ();

  // ---------------------------------------------------------------------------------//
  public CharactersPane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry);

    setColumnConstraints (110, 64, 90, 110, 64);
    setComboBox ("Character", charactersList, wizardry.getCharacters ());

    GridPane.setColumnSpan (charactersList, 2);

    charactersList.getSelectionModel ().selectedItemProperty ()
        .addListener ( (options, oldValue, newValue) ->
        {
          Character character = newValue;
          if (character != null)
          {
            textOut[NAME].setText (character.name);
            textOut[PASSWORD].setText (character.password);
            textOut[IN_MAZE].setText (character.inMaze ? "True" : "");
            textOut[RACE].setText (character.race.toString ());
            textOut[CLASS].setText (character.characterClass.toString ());
            textOut[AGE].setText (getText (character.age));
            textOut[STATUS].setText (character.status.toString ());
            textOut[ALIGNMENT].setText (character.alignment.toString ());

            for (int i = 0; i < attributesText.length; i++)
              textOut1[i].setText (getText (character.attributes[i]));
          }
        });

    int col = 0;
    int row = 0;

    for (int i = 0; i < labels.length; i++)
    {
      row++;

      labels[i] = new Label (labelText[i]);
      textOut[i] = new TextField ();

      if (i <= 8)
      {
        textOut[i].setAlignment (Pos.CENTER_LEFT);
        GridPane.setColumnSpan (textOut[i], 2);
      }
      else
        textOut[i].setAlignment (Pos.CENTER_RIGHT);

      GridPane.setConstraints (labels[i], col, row);
      GridPane.setConstraints (textOut[i], col + 1, row);

      textOut[i].setEditable (false);
      textOut[i].setFocusTraversable (false);

      GridPane.setHalignment (labels[i], HPos.RIGHT);

      gridPane.getChildren ().addAll (labels[i], textOut[i]);
    }

    col = 3;
    row = 1;

    for (int i = 0; i < attributesText.length; i++)
    {
      labels1[i] = new Label (attributesText[i]);
      textOut1[i] = new TextField ();

      GridPane.setConstraints (labels1[i], col, row);
      GridPane.setConstraints (textOut1[i], col + 1, row);
      GridPane.setHalignment (labels1[i], HPos.RIGHT);
      textOut1[i].setAlignment (Pos.CENTER_RIGHT);

      textOut1[i].setEditable (false);
      textOut1[i].setFocusTraversable (false);

      gridPane.getChildren ().addAll (labels1[i], textOut1[i]);

      row++;
    }
  }
}
