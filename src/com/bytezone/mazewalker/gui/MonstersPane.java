package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.Reward;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class MonstersPane extends BasePane
// -----------------------------------------------------------------------------------//
{
  private static final int GENERIC_NAME = 0;
  private static final int MONSTER_CLASS = 1;
  private static final int PARTNER = 2;
  private static final int GROUP_DICE = 3;
  private static final int HP_DICE = 4;
  private static final int RECSN = 5;

  private static final int ID = 0;
  private static final int MAGE_LEVEL = 1;
  private static final int PRIEST_LEVEL = 2;
  private static final int PARTNER_PCT = 3;
  private static final int MAGIC_RESIST = 4;
  private static final int BREATHE = 5;
  private static final int DRAIN = 6;
  private static final int REGEN = 7;
  private static final int TOTAL = 8;
  private static final int ARMOUR_CLASS = 9;
  private static final int UNIQUE = 10;
  private static final int IMAGE = 11;
  private static final int GOLD_REWARDS = 12;
  private static final int CHEST_REWARDS = 13;

  // Scenario #1 values
  private static int[] experience = {                                     //
      55, 235, 415, 230, 380, 620, 840, 520, 550, 350,                    // 00-09
      475, 515, 920, 600, 735, 520, 795, 780, 990, 795,                   // 10-19
      1360, 1320, 1275, 680, 960, 600, 755, 1120, 2075, 870,              // 20-29
      960, 600, 1120, 2435, 1080, 2280, 975, 875, 1135, 1200,             // 30-39
      620, 740, 1460, 1245, 960, 1405, 1040, 1220, 1520, 1000,            // 40-49
      960, 2340, 2160, 2395, 790, 1140, 1235, 1790, 1720, 2240,           // 50-59
      1475, 1540, 1720, 1900, 1240, 1220, 1020, 20435, 5100, 3515,        // 60-69
      2115, 2920, 2060, 2140, 1400, 1640, 1280, 4450, 42840, 3300,        // 70-79
      40875, 5000, 3300, 2395, 1935, 1600, 3330, 44090, 40840, 5200,      // 80-89
      4155, 3000, 9200, 3160, 7460, 7320, 15880, 1600, 2200, 1000,        // 90-99
      1900                                                                // 100 
  };

  TextField[] textOut1;
  TextField[] textOut2;
  TextField[] textOut3;
  TextField[] textOut4;

  CheckBox[] checkBoxes1;
  CheckBox[] checkBoxes2;

  private final int scenarioId;
  private final Canvas canvas = new Canvas (280, 200);      // wh

  // ---------------------------------------------------------------------------------//
  public MonstersPane (WizardryOrigin wizardry, Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry, stage);

    scenarioId = wizardry.getScenarioId ();

    setColumnConstraints (50, 60, 160, 30, 110, 65, 80, 20, 80, 20);

    LabelPlacement lp0 = new LabelPlacement (0, 0, HPos.RIGHT, 2);
    DataPlacement dp0 = new DataPlacement (2, 0, Pos.CENTER_LEFT, 1);
    ComboBox<Monster> monstersList = new ComboBox<> ();
    setComboBox ("Monster", monstersList, wizardry.getMonsters (),
        (options, oldValue, newValue) -> update (newValue), lp0, dp0);

    GridPane.setConstraints (canvas, 1, 8);
    GridPane.setColumnSpan (canvas, 3);
    GridPane.setRowSpan (canvas, 7);

    gridPane.getChildren ().add (canvas);

    String[] label1Text =
        { "Generic name", "Monster class", "Partner", "Appear dice", "Hits dice", "Damage dice" };
    String[] label2Text = { "ID", "Mage level", "Priest level", "Partner odds", "Magic resistance",
        "Breathe", "Level drain", "Regen", "Experience", "Armour class", "# Encs", "Image",
        "Gold rewards", "Chest rewards" };

    LabelPlacement lp1 = new LabelPlacement (0, 1, HPos.RIGHT, 2);
    DataPlacement dp1 = new DataPlacement (2, 1, Pos.CENTER_LEFT, 1);
    textOut1 = createOutputFields (label1Text, lp1, dp1);

    LabelPlacement lp2 = new LabelPlacement (4, 0, HPos.RIGHT, 1);
    DataPlacement dp2 = new DataPlacement (5, 0, Pos.CENTER_RIGHT, 1);
    textOut2 = createOutputFields (label2Text, lp2, dp2);

    textOut3 = createOutputFields (1, new DataPlacement (6, 12, Pos.CENTER_LEFT, 2));
    textOut4 = createOutputFields (3, new DataPlacement (6, 13, Pos.CENTER_LEFT, 4));

    // resistance
    setLabel ("Resistance", 6, 0, HPos.RIGHT, 2);
    checkBoxes1 = createCheckBoxes (WizardryOrigin.resistance, 6, 1);

    // properties
    setLabel ("Property", 8, 0, HPos.RIGHT, 2);
    checkBoxes2 = createCheckBoxes (WizardryOrigin.property, 8, 1);
  }

  // ---------------------------------------------------------------------------------//
  private void update (Monster monster)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut1[GENERIC_NAME], monster.genericName);
    setText (textOut1[MONSTER_CLASS], WizardryOrigin.monsterClass[monster.monsterClass]);
    setText (textOut1[PARTNER], wizardry.getMonsters ().get (monster.enemyTeam).name);
    setText (textOut1[GROUP_DICE], monster.groupSize.toString ());
    setText (textOut1[HP_DICE], monster.hitPoints.toString ());
    setText (textOut1[RECSN], monster.damageDice);

    setText (textOut2[ID], monster.id);
    setText (textOut2[MAGE_LEVEL], monster.mageSpells + "");
    setText (textOut2[PRIEST_LEVEL], monster.priestSpells + "");
    setText (textOut2[PARTNER_PCT], monster.teamPercentage + "%");
    setText (textOut2[MAGIC_RESIST], monster.unaffect + "%");
    setText (textOut2[BREATHE], monster.breathe);
    setText (textOut2[DRAIN], monster.drainAmt);
    setText (textOut2[REGEN], monster.healPts);
    setText (textOut2[TOTAL], scenarioId == 1 ? experience[monster.id] : monster.expamt);
    setText (textOut2[ARMOUR_CLASS], monster.armourClass);
    setText (textOut2[UNIQUE], monster.unique);
    setText (textOut2[IMAGE], monster.image);
    setText (textOut2[GOLD_REWARDS], monster.reward1);
    setText (textOut2[CHEST_REWARDS], monster.reward2);

    setText (textOut3[0], wizardry.getRewards ().get (monster.reward1).goldRange ());

    for (int i = 0; i < 3; i++)
    {
      Reward reward = wizardry.getRewards ().get (monster.reward2);
      String itemRange = reward.itemRange (i);
      if (itemRange.isEmpty ())
        setText (textOut4[i], "");
      else
      {
        int pos = itemRange.indexOf (':');
        int item1 = Integer.parseInt (itemRange.substring (0, pos - 1));
        int item2 = Integer.parseInt (itemRange.substring (pos + 2));

        if (item1 == item2)
          setText (textOut4[i], wizardry.getItems ().get (item1));
        else
        {
          String itemName1 = wizardry.getItems ().get (item1).name;
          String itemName2 = wizardry.getItems ().get (item2).name;
          setText (textOut4[i], itemName1 + " : " + itemName2);
        }
        //        }
      }
    }

    int resistance = monster.flags1;
    for (int i = 0; i < WizardryOrigin.resistance.length; i++)
    {
      checkBoxes1[i].setSelected ((resistance & 0x01) != 0);
      resistance >>>= 1;
    }

    int property = monster.flags2;
    for (int i = 0; i < WizardryOrigin.property.length; i++)
    {
      checkBoxes2[i].setSelected ((property & 0x01) != 0);
      property >>>= 1;
    }

    wizardry.getImage (monster.image).draw (canvas.getGraphicsContext2D ());
  }
}
