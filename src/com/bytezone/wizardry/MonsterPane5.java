package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Monster;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
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
    setAllRowConstraints (getRows (), getRowHeight ());     // make all rows the same height

    createLabel ("Property", 0, 0, HPos.CENTER, 2);

    createLabelsVertical (new LabelPlacement (WizardryData.property, 0, 1, HPos.RIGHT, 1));
    checkBoxes = createCheckBoxes (new DataLayout (1, 1, WizardryData.property.length, Pos.CENTER));
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
