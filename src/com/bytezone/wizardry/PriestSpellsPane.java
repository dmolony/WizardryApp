package com.bytezone.wizardry;

import com.bytezone.appbase.BorderedDataPane;
import com.bytezone.appbase.DataLayout;
import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class PriestSpellsPane extends BorderedDataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBox1;
  private CheckBox[] checkBox2;

  // ---------------------------------------------------------------------------------//
  public PriestSpellsPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (4, 16);                             // columns, rows

    setColumnConstraints (66, 30, 66, 30);

    createLabel ("Priest spells", 1, 0, HPos.LEFT, 2);

    String[] priestSpells1 = new String[15];
    String[] priestSpells2 = new String[14];

    for (int i = 0; i < priestSpells1.length; i++)
      priestSpells1[i] = WizardryData.spells[21 + i];         // 21:35
    for (int i = 0; i < priestSpells2.length; i++)
      priestSpells2[i] = WizardryData.spells[36 + i];         // 36:49

    createLabelsVertical (priestSpells1, 0, 1, HPos.RIGHT);
    checkBox1 =
        createCheckBoxes (new DataLayout (1, 1, priestSpells1.length, Pos.CENTER, false));

    createLabelsVertical (priestSpells2, 2, 1, HPos.RIGHT);
    checkBox2 =
        createCheckBoxes (new DataLayout (3, 1, priestSpells2.length, Pos.CENTER, false));
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    reset (checkBox1);
    reset (checkBox2);
  }

  // ---------------------------------------------------------------------------------//
  void update (Character character)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < checkBox1.length; i++)
      checkBox1[i].setSelected (character.spellsKnown[i + 21]);

    for (int i = 0; i < checkBox2.length; i++)
      checkBox2[i].setSelected (character.spellsKnown[i + 36]);
  }
}
