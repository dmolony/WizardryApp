package com.bytezone.wizardry;

import com.bytezone.wizardry.origin.Item;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.HPos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class ItemPane3 extends DataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes1;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public ItemPane3 ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 20);
    setAllRowConstraints (8, DataPane.ROW_HEIGHT);           // make all rows the same height

    createLabel ("Resistance", 0, 0, HPos.RIGHT, 1);

    checkBoxes1 = createCheckBoxes (WizardryData.resistance, 0, 1);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (checkBoxes1);
  }

  // ---------------------------------------------------------------------------------//
  void update (Item item)
  // ---------------------------------------------------------------------------------//
  {
    if (item == null)
      return;

    int resistance = item.wepvsty3Flags;
    for (int i = 0; i < WizardryData.resistance.length; i++)
    {
      checkBoxes1[i].setSelected ((resistance & 0x01) != 0);
      resistance >>>= 1;
    }
  }
}
