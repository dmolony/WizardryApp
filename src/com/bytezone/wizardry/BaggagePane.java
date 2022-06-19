package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.Character.Possession;
import com.bytezone.wizardry.data.Item;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class BaggagePane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private TextField[] textOut3;
  private TextField[] textOut4;

  private CheckBox[] checkBox4;
  private CheckBox[] checkBox5;
  private CheckBox[] checkBox6;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public BaggagePane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 145, 20, 20, 20, 90);
    setAllRowConstraints (9, DataPane.ROW_HEIGHT);           // make all rows the same height

    // possessions headings
    createLabel ("Item", 1, 0, HPos.CENTER, 1);
    createLabel ("Eq", 2, 0, HPos.LEFT, 1);
    createLabel ("Cu", 3, 0, HPos.LEFT, 1);
    createLabel ("Id", 4, 0, HPos.LEFT, 1);
    createLabel ("Value", 5, 0, HPos.CENTER, 1);

    // possessions
    String[] possessionsText = new String[8];
    for (int i = 0; i < possessionsText.length; i++)
      possessionsText[i] = "# " + (i + 1);

    LabelPlacement lp = new LabelPlacement (0, 1, HPos.RIGHT, 1);
    DataPlacement dp = new DataPlacement (1, 1, Pos.CENTER_LEFT, 1);
    textOut3 = createTextFields (possessionsText, lp, dp);

    // possessions eq/cu/id
    checkBox4 = createCheckBoxes (8, 2, 1);
    checkBox5 = createCheckBoxes (8, 3, 1);
    checkBox6 = createCheckBoxes (8, 4, 1);

    // possessions value
    DataPlacement dp6 = new DataPlacement (5, 1, Pos.CENTER_RIGHT, 1);
    textOut4 = createTextFields (8, dp6);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut3);
    reset (textOut4);
    reset (checkBox4);
    reset (checkBox5);
    reset (checkBox6);
  }

  // ---------------------------------------------------------------------------------//
  void update (Character character)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < 8; i++)
      if (i < character.possessionsCount)
      {
        Possession possession = character.possessions.get (i);
        Item item = wizardry.getItem (possession.id ());
        if (item == null)
        {
          System.out.printf ("%d null item #%d ", character.id, possession.id ());
          break;
        }

        if (possession.identified ())
        {
          setText (textOut3[i], item.name);
          setText (textOut4[i], item.price);    // formatted as number

          checkBox4[i].setSelected (possession.equipped ());
          checkBox5[i].setSelected (possession.cursed ());
          checkBox6[i].setSelected (true);

          checkBox5[i].setIndeterminate (false);
        }
        else
        {
          setText (textOut3[i], item.nameGeneric);
          setText (textOut4[i], "?");           // formatted as text

          checkBox4[i].setSelected (false);
          checkBox6[i].setSelected (false);

          checkBox5[i].setIndeterminate (true);
        }
      }
      else
      {
        setText (textOut3[i], "");
        setText (textOut4[i], "");

        checkBox4[i].setSelected (false);
        checkBox5[i].setSelected (false);
        checkBox6[i].setSelected (false);

        checkBox5[i].setIndeterminate (false);
      }
  }
}
