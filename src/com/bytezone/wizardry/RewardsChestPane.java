package com.bytezone.wizardry;

import com.bytezone.appbase.BorderedDataPane;
import com.bytezone.appbase.DataLayout;
import com.bytezone.wizardry.data.Reward;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class RewardsChestPane extends BorderedDataPane
// -----------------------------------------------------------------------------------//
{
  private static final int IS_CHEST = 0;

  TextField[] chest;

  // ---------------------------------------------------------------------------------//
  public RewardsChestPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 1);                              // columns, rows

    setColumnConstraints (110, 140);

    String[] labels = { "Is chest" };
    createLabelsVertical (labels, 0, 0, HPos.RIGHT);

    DataLayout dataLayout1 = new DataLayout (1, 0, 1, Pos.CENTER_LEFT);
    chest = createTextFields (dataLayout1);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    reset (chest);
  }

  // ---------------------------------------------------------------------------------//
  void update (Reward reward)
  // ---------------------------------------------------------------------------------//
  {
    setText (chest[IS_CHEST], reward.isChest);
  }
}
