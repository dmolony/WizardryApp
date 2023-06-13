package com.bytezone.wizardry;

import com.bytezone.appbase.BorderedDataPane;
import com.bytezone.appbase.DataLayout;
import com.bytezone.wizardry.data.Item;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class ItemClassUsagePane extends BorderedDataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] checkBoxes;

  // ---------------------------------------------------------------------------------//
  public ItemClassUsagePane ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 9);                             // columns, rows

    setColumnConstraints (70, 30);

    createLabel ("Equip", 0, 0, HPos.RIGHT, 2);

    createLabelsVertical (WizardryData.characterClass, 0, 1, HPos.RIGHT);
    checkBoxes = createCheckBoxes (
        new DataLayout (1, 1, WizardryData.characterClass.length, Pos.CENTER, false));
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
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
