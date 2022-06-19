package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Monster;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.scene.control.CheckBox;

// -----------------------------------------------------------------------------------//
public class MonsterPane5 extends DataPane
// -----------------------------------------------------------------------------------//
{
  private final CheckBox[] checkBoxes;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public MonsterPane5 ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 30);
    setAllRowConstraints (8, getRowHeight ());     // make all rows the same height

    createLabel ("Property", 0, 0, HPos.CENTER, 2);
    checkBoxes = createCheckBoxes (WizardryData.property, 0, 1);
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
    int property = monster.properties;
    for (int i = 0; i < WizardryData.property.length; i++)
    {
      checkBoxes[i].setSelected ((property & 0x01) != 0);
      property >>>= 1;
    }
  }
}
