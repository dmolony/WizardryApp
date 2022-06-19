package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Item;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class ItemPane5 extends DataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes3;
  private CheckBox[] checkBoxes4;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public ItemPane5 ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 30, 100);
    setAllRowConstraints (15, getRowHeight ());           // make all rows the same height

    createLabel ("Protection vs", 0, 0, HPos.RIGHT, 2);
    createLabel ("Purposed vs", 2, 0, HPos.LEFT, 2);

    // protection vs
    checkBoxes3 = createCheckBoxes (WizardryData.monsterClass, 0, 1);

    // purposed vs
    checkBoxes4 = createCheckBoxes (checkBoxes3.length, 2, 1);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (checkBoxes3);
    reset (checkBoxes4);
  }

  // ---------------------------------------------------------------------------------//
  void update (Item item)
  // ---------------------------------------------------------------------------------//
  {
    int protection = item.wepvsty2Flags;
    int purposed = item.wepvstyFlags;

    for (int i = 0; i < WizardryData.monsterClass.length; i++)
    {
      checkBoxes3[i].setSelected ((protection & 0x01) != 0);
      checkBoxes4[i].setSelected ((purposed & 0x01) != 0);

      protection >>>= 1;
      purposed >>>= 1;
    }
  }
}
