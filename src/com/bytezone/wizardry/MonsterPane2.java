package com.bytezone.wizardry;

import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class MonsterPane2 extends DataPane
// -----------------------------------------------------------------------------------//
{
  private static final int ID = 0;
  private static final int MAGE_LEVEL = 1;
  private static final int PRIEST_LEVEL = 2;
  private static final int MAGIC_RESIST = 3;
  private static final int PARTNER_PCT = 4;
  private static final int IMAGE = 5;
  private static final int DRAIN = 6;
  private static final int REGEN = 7;
  private static final int EXPERIENCE = 8;
  private static final int ARMOUR_CLASS = 9;
  private static final int UNIQUE = 10;
  private static final int BREATHE = 11;
  private static final int WANDER_REWARDS = 12;
  private static final int LAIR_REWARDS = 13;

  private final TextField[] textOut2;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public MonsterPane2 ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 145);
    setAllRowConstraints (11, DataPane.ROW_HEIGHT);     // make all rows the same height

    String[] label2Text = { "ID", "Mage level", "Priest level", "Magic resistance", "Partner odds",
        "Image", "Level drain", "Regen", "Experience", "Armour class", "Unique", "Breathe",
        "Wandering reward", "Lair reward" };

    LabelPlacement lp2 = new LabelPlacement (0, 10, HPos.RIGHT, 1);
    DataPlacement dp2 = new DataPlacement (1, 10, Pos.CENTER_RIGHT, 1);
    textOut2 = createTextFields (label2Text, lp2, dp2);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut2);
  }

  // ---------------------------------------------------------------------------------//
  void update (Monster monster)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut2[ID], monster.id);
    setText (textOut2[MAGE_LEVEL], monster.mageSpells + "");
    setText (textOut2[PRIEST_LEVEL], monster.priestSpells + "");
    setText (textOut2[MAGIC_RESIST], monster.unaffect + "%");
    if (wizardry.getScenarioId () <= 3)
      setText (textOut2[PARTNER_PCT], monster.partnerOdds + "%");
    setText (textOut2[BREATHE], monster.breathe);
    setText (textOut2[DRAIN], monster.drain);
    setText (textOut2[REGEN], monster.regen);
    setText (textOut2[EXPERIENCE], monster.experiencePoints);
    setText (textOut2[ARMOUR_CLASS], monster.armourClass);
    setText (textOut2[UNIQUE], monster.unique);
    setText (textOut2[IMAGE], monster.image);
    setText (textOut2[WANDER_REWARDS], monster.rewardWandering);
    setText (textOut2[LAIR_REWARDS], monster.rewardLair);
  }
}
