package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Item;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class ItemPane3 extends DataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public ItemPane3 ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 20);
    setAllRowConstraints (8, getRowHeight ());           // make all rows the same height

    createLabel ("Resistance", 0, 0, HPos.RIGHT, 2);

    createLabelsVertical (new LabelPlacement (WizardryData.resistance, 0, 1, HPos.RIGHT, 1));
    checkBoxes =
        createCheckBoxes (new DataLayout (1, 1, WizardryData.resistance.length, Pos.CENTER));
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return 8;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getColumns ()
  // ---------------------------------------------------------------------------------//
  {
    return 2;
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

    int resistance = item.wepvsty3Flags;
    for (int i = 0; i < WizardryData.resistance.length; i++)
    {
      checkBoxes[i].setSelected ((resistance & 0x01) != 0);
      resistance >>>= 1;
    }
  }
}
