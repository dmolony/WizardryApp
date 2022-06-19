package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
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
    setColumnConstraints (70, 30, 70, 30);
    setAllRowConstraints (16, getRowHeight ());           // make all rows the same height

    createLabel ("Priest spells", 1, 0, HPos.LEFT, 2);

    String[] priestSpells1 = new String[15];
    String[] priestSpells2 = new String[14];

    for (int i = 0; i < priestSpells1.length; i++)
      priestSpells1[i] = WizardryData.spells[21 + i];
    for (int i = 0; i < priestSpells2.length; i++)
      priestSpells2[i] = WizardryData.spells[36 + i];

    checkBox1 = createCheckBoxes (priestSpells1, 0, 1);
    checkBox2 = createCheckBoxes (priestSpells2, 2, 1);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return 16;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getColumns ()
  // ---------------------------------------------------------------------------------//
  {
    return 4;
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
