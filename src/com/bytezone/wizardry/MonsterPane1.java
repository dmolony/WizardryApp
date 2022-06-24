package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Monster;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class MonsterPane1 extends DataPane
// -----------------------------------------------------------------------------------//
{
  private static final int NAME = 0;
  private static final int NAME_PLURAL = 1;
  private static final int GENERIC_NAME = 2;
  private static final int GENERIC_NAME_PLURAL = 3;
  private static final int MONSTER_CLASS = 4;
  private static final int PARTNER = 5;
  private static final int GROUP_DICE = 6;
  private static final int HP_DICE = 7;
  private static final int RECSN = 8;

  private final TextField[] textOut;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public MonsterPane1 ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 145);
    setAllRowConstraints (9, getRowHeight ());     // make all rows the same height

    String[] labelText = { "Name", "Plural", "Generic name", "Generic plural", "Monster class",
        "Partner", "Appear dice", "Hits dice", "Damage dice" };
    assert getRows () == labelText.length;

    createLabelsVertical (new LabelPlacement2 (labelText, 0, 0, HPos.RIGHT, 1));
    textOut = createTextFields (new DataLayout (1, 0, getRows (), Pos.CENTER_LEFT));
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return 9;
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

    reset (textOut);
  }

  // ---------------------------------------------------------------------------------//
  void update (Monster monster)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut[NAME], monster.name);
    setText (textOut[NAME_PLURAL], monster.namePlural);
    setText (textOut[GENERIC_NAME], monster.genericName);
    setText (textOut[GENERIC_NAME_PLURAL], monster.genericNamePlural);

    setText (textOut[MONSTER_CLASS], WizardryData.monsterClass[monster.monsterClass]);
    if (wizardry.getScenarioId () <= 3)
      setText (textOut[PARTNER], wizardry.getMonsters ().get (monster.partnerId).name);
    setText (textOut[GROUP_DICE], monster.groupSize.toString ());
    setText (textOut[HP_DICE], monster.hitPoints.toString ());
    setText (textOut[RECSN], monster.damageDiceText);
  }
}
