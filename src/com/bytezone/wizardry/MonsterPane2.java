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

  private final TextField[] textOut;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public MonsterPane2 ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 50);
    setAllRowConstraints (14, DataPane.ROW_HEIGHT);     // make all rows the same height

    String[] labelText = { "ID", "Mage level", "Priest level", "Magic resistance", "Partner odds",
        "Image", "Level drain", "Regen", "Experience", "Armour class", "Unique", "Breathe",
        "Wandering reward", "Lair reward" };

    LabelPlacement lp = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp = new DataPlacement (1, 0, Pos.CENTER_RIGHT, 1);
    textOut = createTextFields (labelText, lp, dp);
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
    setText (textOut[ID], monster.id);
    setText (textOut[MAGE_LEVEL], monster.mageSpells + "");
    setText (textOut[PRIEST_LEVEL], monster.priestSpells + "");
    setText (textOut[MAGIC_RESIST], monster.unaffect + "%");
    if (wizardry.getScenarioId () <= 3)
      setText (textOut[PARTNER_PCT], monster.partnerOdds + "%");
    setText (textOut[BREATHE], monster.breathe);
    setText (textOut[DRAIN], monster.drain);
    setText (textOut[REGEN], monster.regen);
    setText (textOut[EXPERIENCE], monster.experiencePoints);
    setText (textOut[ARMOUR_CLASS], monster.armourClass);
    setText (textOut[UNIQUE], monster.unique);
    setText (textOut[IMAGE], monster.image);
    setText (textOut[WANDER_REWARDS], monster.rewardWandering);
    setText (textOut[LAIR_REWARDS], monster.rewardLair);
  }
}
