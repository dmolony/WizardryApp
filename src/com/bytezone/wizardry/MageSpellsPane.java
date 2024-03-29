package com.bytezone.wizardry;

import com.bytezone.appbase.BorderedDataPane;
import com.bytezone.appbase.DataLayout;
import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class MageSpellsPane extends BorderedDataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBox1;
  private CheckBox[] checkBox2;

  // ---------------------------------------------------------------------------------//
  public MageSpellsPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (4, 12);                             // columns, rows

    setColumnConstraints (66, 30, 66, 30);

    createLabel ("Mage spells", 1, 0, HPos.LEFT, 2);

    String[] mageSpells1 = new String[11];
    String[] mageSpells2 = new String[10];

    for (int i = 0; i < mageSpells1.length; i++)
      mageSpells1[i] = WizardryData.spells[i];              // 0:10
    for (int i = 0; i < mageSpells2.length; i++)
      mageSpells2[i] = WizardryData.spells[11 + i];         // 11:20

    createLabelsVertical (mageSpells1, 0, 1, HPos.RIGHT);
    checkBox1 =
        createCheckBoxes (new DataLayout (1, 1, mageSpells1.length, Pos.CENTER, false));

    createLabelsVertical (mageSpells2, 2, 1, HPos.RIGHT);
    checkBox2 =
        createCheckBoxes (new DataLayout (3, 1, mageSpells2.length, Pos.CENTER, false));
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
      checkBox1[i].setSelected (character.spellsKnown[i]);

    for (int i = 0; i < checkBox2.length; i++)
      checkBox2[i].setSelected (character.spellsKnown[i + 11]);
  }
}
