package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class MageSpellsPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBox1;
  private CheckBox[] checkBox2;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public MageSpellsPane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (70, 30, 70, 30);
    setAllRowConstraints (getRows (), getRowHeight ());   // make all rows the same height

    createLabel ("Mage spells", 1, 0, HPos.LEFT, 2);

    String[] mageSpells1 = new String[11];
    String[] mageSpells2 = new String[10];

    for (int i = 0; i < mageSpells1.length; i++)
      mageSpells1[i] = WizardryData.spells[i];
    for (int i = 0; i < mageSpells2.length; i++)
      mageSpells2[i] = WizardryData.spells[11 + i];

    createLabelsVertical (new LabelPlacement (mageSpells1, 0, 1, HPos.RIGHT, 1));
    checkBox1 = createCheckBoxes (new DataLayout (1, 1, mageSpells1.length, Pos.CENTER));

    createLabelsVertical (new LabelPlacement (mageSpells2, 2, 1, HPos.RIGHT, 1));
    checkBox2 = createCheckBoxes (new DataLayout (3, 1, mageSpells2.length, Pos.CENTER));
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return 12;
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
      checkBox1[i].setSelected (character.spellsKnown[i]);

    for (int i = 0; i < checkBox2.length; i++)
      checkBox2[i].setSelected (character.spellsKnown[i + checkBox1.length]);
  }
}
