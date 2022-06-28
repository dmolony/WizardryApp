package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class PriestSpellsPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBox1;
  private CheckBox[] checkBox2;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public PriestSpellsPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (4, 16);                             // columns, rows

    setColumnConstraints (70, 30, 70, 30);

    createLabel ("Priest spells", 1, 0, HPos.LEFT, 2);

    String[] priestSpells1 = new String[15];
    String[] priestSpells2 = new String[14];

    for (int i = 0; i < priestSpells1.length; i++)
      priestSpells1[i] = WizardryData.spells[21 + i];
    for (int i = 0; i < priestSpells2.length; i++)
      priestSpells2[i] = WizardryData.spells[36 + i];

    createLabelsVertical (priestSpells1, 0, 1, HPos.RIGHT, 1);
    checkBox1 = createCheckBoxes (new DataLayout (1, 1, priestSpells1.length, Pos.CENTER));

    createLabelsVertical (priestSpells2, 2, 1, HPos.RIGHT, 1);
    checkBox2 = createCheckBoxes (new DataLayout (3, 1, priestSpells2.length, Pos.CENTER));
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

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
      checkBox2[i].setSelected (character.spellsKnown[i + checkBox1.length + 21]);
  }
}
