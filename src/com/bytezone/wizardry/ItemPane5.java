package com.bytezone.wizardry;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class ItemPane5 extends DataPane
// -----------------------------------------------------------------------------------//
{
  private CheckBox[] protect;
  private CheckBox[] purpose;

  // ---------------------------------------------------------------------------------//
  public ItemPane5 ()
  // ---------------------------------------------------------------------------------//
  {
    super (3, 15);                             // columns, rows

    setColumnConstraints (110, 30, 30);

    createLabel ("Protect", 0, 0, HPos.RIGHT, 2);
    createLabel ("Vs", 2, 0, HPos.CENTER, 1);

    createLabelsVertical (WizardryData.monsterClass, 0, 1, HPos.RIGHT);

    DataLayout dataLayout = new DataLayout (1, 1, WizardryData.monsterClass.length, Pos.CENTER);
    protect = createCheckBoxes (dataLayout);
    purpose = createCheckBoxes (dataLayout);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    reset (protect);
    reset (purpose);
  }

  // ---------------------------------------------------------------------------------//
  void update (int protection, int purposed)
  // ---------------------------------------------------------------------------------//
  {
    //    int protection = item.wepvsty2Flags;
    //    int purposed = item.wepvstyFlags;

    for (int i = 0; i < WizardryData.monsterClass.length; i++)
    {
      protect[i].setSelected ((protection & 0x01) != 0);
      purpose[i].setSelected ((purposed & 0x01) != 0);

      protection >>>= 1;
      purposed >>>= 1;
    }
  }
}
