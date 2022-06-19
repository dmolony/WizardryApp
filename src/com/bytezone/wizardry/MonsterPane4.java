package com.bytezone.wizardry;

import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.HPos;
import javafx.scene.control.CheckBox;

public class MonsterPane4 extends DataPane
{
  private final CheckBox[] checkBoxes1;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public MonsterPane4 ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 145);
    setAllRowConstraints (11, DataPane.ROW_HEIGHT);     // make all rows the same height

    // resistance
    createLabel ("Resistance", 2, 10, HPos.RIGHT, 2);
    checkBoxes1 = createCheckBoxes (WizardryData.resistance, 2, 11);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (checkBoxes1);
  }

  // ---------------------------------------------------------------------------------//
  void update (Monster monster)
  // ---------------------------------------------------------------------------------//
  {
    int resistance = monster.resistance;
    for (int i = 0; i < WizardryData.resistance.length; i++)
    {
      checkBoxes1[i].setSelected ((resistance & 0x01) != 0);
      resistance >>>= 1;
    }
  }
}
