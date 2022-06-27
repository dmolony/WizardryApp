package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Monster;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

public class MonsterPane4 extends DataPane
{
  private final CheckBox[] checkBoxes;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public MonsterPane4 ()
  // ---------------------------------------------------------------------------------//
  {
    super (8, 2);

    setColumnConstraints (110, 30);

    createLabel ("Resistance", 0, 0, HPos.CENTER, 2);

    createLabelsVertical (new LabelPlacement (WizardryData.resistance, 0, 1, HPos.RIGHT, 1));
    checkBoxes =
        createCheckBoxes (new DataLayout (1, 1, WizardryData.resistance.length, Pos.CENTER));
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (checkBoxes);
  }

  // ---------------------------------------------------------------------------------//
  void update (Monster monster)
  // ---------------------------------------------------------------------------------//
  {
    int resistance = monster.resistance;
    for (int i = 0; i < WizardryData.resistance.length; i++)
    {
      checkBoxes[i].setSelected ((resistance & 0x01) != 0);
      resistance >>>= 1;
    }
  }
}
