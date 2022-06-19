package com.bytezone.wizardry;

import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.HPos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class MonsterPane5 extends DataPane
// -----------------------------------------------------------------------------------//
{
  private final CheckBox[] checkBoxes2;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public MonsterPane5 ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 145);
    setAllRowConstraints (11, DataPane.ROW_HEIGHT);     // make all rows the same height

    // properties
    createLabel ("Property", 4, 10, HPos.RIGHT, 2);
    checkBoxes2 = createCheckBoxes (WizardryData.property, 4, 11);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (checkBoxes2);
  }

  // ---------------------------------------------------------------------------------//
  void update (Monster monster)
  // ---------------------------------------------------------------------------------//
  {
    int property = monster.properties;
    for (int i = 0; i < WizardryData.property.length; i++)
    {
      checkBoxes2[i].setSelected ((property & 0x01) != 0);
      property >>>= 1;
    }
  }
}
