package com.bytezone.wizardry;

import com.bytezone.appbase.BorderedDataPane;
import com.bytezone.appbase.DataLayout;
import com.bytezone.wizardry.data.Reward;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class RewardsTrapsPane extends BorderedDataPane
// -----------------------------------------------------------------------------------//
{
  CheckBox[] traps;

  // ---------------------------------------------------------------------------------//
  public RewardsTrapsPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 8);                              // columns, rows

    setColumnConstraints (90, 30);

    createLabelsVertical (WizardryData.trapType, 0, 0, HPos.RIGHT);
    traps = createCheckBoxes (
        new DataLayout (1, 0, WizardryData.trapType.length, HPos.LEFT, false));
    assert getRows () == traps.length;
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    reset (traps);
  }

  // ---------------------------------------------------------------------------------//
  void update (Reward reward)
  // ---------------------------------------------------------------------------------//
  {
    int trap = reward.trapTypeFlags;
    for (int j = 0; j < WizardryData.trapType.length; j++)
    {
      traps[j].setSelected ((trap & 0x01) != 0);
      trap >>>= 1;
    }
  }
}
