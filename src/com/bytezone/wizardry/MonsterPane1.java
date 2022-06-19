package com.bytezone.wizardry;

import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.WizardryData;

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

  private final TextField[] textOut1;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public MonsterPane1 ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 145);
    setAllRowConstraints (9, DataPane.ROW_HEIGHT);     // make all rows the same height

    String[] label1Text = { "Name", "Plural", "Generic name", "Generic plural", "Monster class",
        "Partner", "Appear dice", "Hits dice", "Damage dice" };

    LabelPlacement lp1 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 0, Pos.CENTER_LEFT, 2);
    textOut1 = createTextFields (label1Text, lp1, dp1);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut1);
  }

  // ---------------------------------------------------------------------------------//
  void update (Monster monster)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut1[NAME], monster.name);
    setText (textOut1[NAME_PLURAL], monster.namePlural);
    setText (textOut1[GENERIC_NAME], monster.genericName);
    setText (textOut1[GENERIC_NAME_PLURAL], monster.genericNamePlural);

    setText (textOut1[MONSTER_CLASS], WizardryData.monsterClass[monster.monsterClass]);
    if (wizardry.getScenarioId () <= 3)
      setText (textOut1[PARTNER], wizardry.getMonsters ().get (monster.partnerId).name);
    setText (textOut1[GROUP_DICE], monster.groupSize.toString ());
    setText (textOut1[HP_DICE], monster.hitPoints.toString ());
    setText (textOut1[RECSN], monster.damageDiceText);
  }
}
