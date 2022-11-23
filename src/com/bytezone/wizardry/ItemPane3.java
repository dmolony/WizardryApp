package com.bytezone.wizardry;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class ItemPane3 extends DataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes;

  // ---------------------------------------------------------------------------------//
  public ItemPane3 ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 8);                             // columns, rows

    setColumnConstraints (110, 20);

    createLabel ("Resistance", 0, 0, HPos.RIGHT, 2);

    createLabelsVertical (WizardryData.resistance, 0, 1, HPos.RIGHT);
    checkBoxes =
        createCheckBoxes (new DataLayout (1, 1, WizardryData.resistance.length, Pos.CENTER));
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    reset (checkBoxes);
  }

  // ---------------------------------------------------------------------------------//
  void update (int wepstyv3)
  // ---------------------------------------------------------------------------------//
  {
    //    if (item == null)
    //      return;

    //    int resistance = item.wepvsty3Flags;
    for (int i = 0; i < WizardryData.resistance.length; i++)
    {
      checkBoxes[i].setSelected ((wepstyv3 & 0x01) != 0);
      wepstyv3 >>>= 1;
    }
  }
}
