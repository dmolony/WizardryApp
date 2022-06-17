package com.bytezone.wizardry;

import com.bytezone.wizardry.origin.Character;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class MageSpellsPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  CheckBox[] checkBox1;
  CheckBox[] checkBox2;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public MageSpellsPane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (70, 30);
    gridPane.setPadding (new Insets (0, 0, 0, 0));      // trbl

    createLabel ("Mage spells", 1, 0, HPos.LEFT, 2);

    String[] mageSpells1 = new String[11];
    String[] mageSpells2 = new String[10];

    for (int i = 0; i < mageSpells1.length; i++)
      mageSpells1[i] = WizardryData.spells[i];
    for (int i = 0; i < mageSpells2.length; i++)
      mageSpells2[i] = WizardryData.spells[11 + i];

    checkBox1 = createCheckBoxes (mageSpells1, 0, 1);
    checkBox2 = createCheckBoxes (mageSpells2, 2, 1);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
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
      checkBox1[i].setSelected (character.spellsKnown[i]);

    for (int i = 0; i < checkBox2.length; i++)
      checkBox1[i].setSelected (character.spellsKnown[i + checkBox1.length]);
  }
}
