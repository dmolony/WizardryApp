package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

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
  private static final int RESISTANCE = 6;
  private static final int ABILITY = 7;

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
  private static final int GOLD_REWARDS = 10;
  private static final int CHEST_REWARDS = 11;
  private static final int UNIQUE = 12;
  private static final int IMAGE = 13;

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

  String[] label1Text = { "Generic name", "Monster class", "Partner", "Appear dice", "Hits dice",
      "Damage dice", "Resistance", "Properties" };
  String[] label2Text = { "ID", "Mage level", "Priest level", "Partner odds", "Magic resistance",
      "Breathe", "Level drain", "Regen", "Experience", "Armour class", "Gold rewards",
      "Chest rewards", "# Encs", "Image" };

  //  Label[] labels = new Label[label1Text.length];
  TextField[] textOut1 = new TextField[label1Text.length];
  TextField[] textOut2;
  ComboBox<Monster> monstersList = new ComboBox<> ();
  private final int scenarioId;

  public Canvas canvas = new Canvas (210, 150);      // wh

  // ---------------------------------------------------------------------------------//
  public MonstersPane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry);

    scenarioId = wizardry.getScenarioId ();

    setColumnConstraints (110, 230, 110, 65);

    setComboBox ("Monster", monstersList, wizardry.getMonsters ());

    GridPane.setConstraints (canvas, 1, 9);
    GridPane.setColumnSpan (canvas, 2);
    GridPane.setRowSpan (canvas, 5);

    gridPane.getChildren ().add (canvas);

    monstersList.getSelectionModel ().selectedItemProperty ()
        .addListener ( (options, oldValue, newValue) ->
        {
          Monster monster = newValue;
          if (monster != null)
          {
            textOut1[GENERIC_NAME].setText (monster.genericName);
            textOut1[MONSTER_CLASS].setText (WizardryOrigin.monsterClass[monster.monsterClass]);
            textOut1[PARTNER].setText (wizardry.getMonsters ().get (monster.enemyTeam).name);
            textOut1[GROUP_DICE].setText (monster.groupSize.toString ());
            textOut1[HP_DICE].setText (monster.hitPoints.toString ());
            textOut1[RECSN].setText (monster.damageDice);
            textOut1[RESISTANCE].setText (monster.resistanceText);
            textOut1[ABILITY].setText (monster.propertyText);

            textOut2[ID].setText (getText (monster.id));
            textOut2[MAGE_LEVEL].setText (monster.mageSpells + "");
            textOut2[PRIEST_LEVEL].setText (monster.priestSpells + "");
            textOut2[PARTNER_PCT].setText (monster.teamPercentage + "%");
            textOut2[MAGIC_RESIST].setText (monster.unaffect + "%");
            textOut2[BREATHE].setText (monster.breathe + "");
            textOut2[DRAIN].setText (monster.drainAmt + "");
            textOut2[REGEN].setText (monster.healPts + "");
            textOut2[TOTAL]
                .setText (getText (scenarioId == 1 ? experience[monster.id] : monster.expamt));
            textOut2[ARMOUR_CLASS].setText (getText (monster.armourClass));
            textOut2[GOLD_REWARDS].setText (getText (monster.reward1));
            textOut2[CHEST_REWARDS].setText (getText (monster.reward2));
            textOut2[UNIQUE].setText (getText (monster.unique));
            textOut2[IMAGE].setText (getText (monster.image));

            wizardry.getImage (monster.image).draw (canvas.getGraphicsContext2D ());
          }
        });

    textOut1 = setOutputFields (label1Text, 0, 0, Pos.CENTER_LEFT, 1);
    textOut2 = setOutputFields (label2Text, 2, 0, Pos.CENTER_RIGHT, 1);
  }
}
