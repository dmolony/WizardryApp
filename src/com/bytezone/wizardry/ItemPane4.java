package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Item;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class ItemPane4 extends DataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public ItemPane4 ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 9);                             // columns, rows

    setColumnConstraints (110, 20);

    createLabel ("Equip", 0, 0, HPos.RIGHT, 2);

    createLabelsVertical (WizardryData.characterClass, 0, 1, HPos.RIGHT);
    checkBoxes =
        createCheckBoxes (new DataLayout (1, 1, WizardryData.characterClass.length, Pos.CENTER));
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (checkBoxes);
  }

  // ---------------------------------------------------------------------------------//
  void update (Item item)
  // ---------------------------------------------------------------------------------//
  {
    if (item == null)
      return;

    int characterClass = item.classUseFlags;
    for (int i = 0; i < WizardryData.characterClass.length; i++)
    {
      checkBoxes[i].setSelected ((characterClass & 0x01) != 0);
      characterClass >>>= 1;
    }
  }
}
