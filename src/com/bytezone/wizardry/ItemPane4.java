package com.bytezone.wizardry;

import com.bytezone.wizardry.origin.Item;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.HPos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class ItemPane4 extends DataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes2;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public ItemPane4 ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 20);
    setAllRowConstraints (9, DataPane.ROW_HEIGHT);           // make all rows the same height

    createLabel ("Can be used by", 0, 0, HPos.RIGHT, 1);

    checkBoxes2 = createCheckBoxes (WizardryData.characterClass, 0, 1);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (checkBoxes2);
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
      checkBoxes2[i].setSelected ((characterClass & 0x01) != 0);
      characterClass >>>= 1;
    }
  }
}
