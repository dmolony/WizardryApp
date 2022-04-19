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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class MonstersPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private static final int NAME_PLURAL = 0;
  private static final int GENERIC_NAME = 1;
  private static final int MONSTER_CLASS = 2;
  private static final int PARTNER = 3;
  private static final int GROUP_DICE = 4;
  private static final int HP_DICE = 5;
  private static final int RECSN = 6;

  private static final int ID = 0;
  private static final int MAGE_LEVEL = 1;
  private static final int PRIEST_LEVEL = 2;
  private static final int MAGIC_RESIST = 3;
  private static final int PARTNER_PCT = 4;
  private static final int BREATHE = 5;
  private static final int DRAIN = 6;
  private static final int REGEN = 7;
  private static final int EXPERIENCE = 8;
  private static final int ARMOUR_CLASS = 9;
  private static final int UNIQUE = 10;
  private static final int IMAGE = 11;
  private static final int GOLD_REWARDS = 12;
  private static final int CHEST_REWARDS = 13;

  private final TextField[] textOut1;
  private final TextField[] textOut2;
  private final TextField[] textOut3;
  private final TextField[] textOut4;

  private final CheckBox[] checkBoxes1;
  private final CheckBox[] checkBoxes2;

  private final int scenarioId;
  private final Canvas canvas;

  // ---------------------------------------------------------------------------------//
  public MonstersPane (WizardryOrigin wizardry, Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry, stage);

    scenarioId = wizardry.getScenarioId ();
    canvas = scenarioId == 3 ? new Canvas (280, 192) : new Canvas (280, 200);

    setColumnConstraints (50, 60, 160, 30, 110, 65, 90, 20, 80, 20);

    LabelPlacement lp0 = new LabelPlacement (0, 0, HPos.RIGHT, 2);
    DataPlacement dp0 = new DataPlacement (2, 0, Pos.CENTER_LEFT, 1);
    ComboBox<Monster> monstersList = new ComboBox<> ();
    createComboBox ("Monster", monstersList, wizardry.getMonsters (),
        (options, oldValue, newValue) -> update (newValue), lp0, dp0);

    GridPane.setConstraints (canvas, 1, 9);
    GridPane.setColumnSpan (canvas, 3);
    GridPane.setRowSpan (canvas, 7);

    gridPane.getChildren ().add (canvas);

    String[] label1Text = { "Plural", "Generic name", "Monster class", "Partner", "Appear dice",
        "Hits dice", "Damage dice" };
    String[] label2Text = { "ID", "Mage level", "Priest level", "Magic resistance", "Partner odds",
        "Breathe", "Level drain", "Regen", "Experience", "Armour class", "# Encs", "Image",
        "Gold rewards", "Chest rewards" };

    LabelPlacement lp1 = new LabelPlacement (0, 1, HPos.RIGHT, 2);
    DataPlacement dp1 = new DataPlacement (2, 1, Pos.CENTER_LEFT, 1);
    textOut1 = createTextFields (label1Text, lp1, dp1);

    LabelPlacement lp2 = new LabelPlacement (4, 0, HPos.RIGHT, 1);
    DataPlacement dp2 = new DataPlacement (5, 0, Pos.CENTER_RIGHT, 1);
    textOut2 = createTextFields (label2Text, lp2, dp2);

    textOut3 = createTextFields (1, new DataPlacement (6, 12, Pos.CENTER_LEFT, 1));
    textOut4 = createTextFields (3, new DataPlacement (6, 13, Pos.CENTER_LEFT, 4));

    // resistance
    createLabel ("Resistance", 6, 0, HPos.RIGHT, 2);
    checkBoxes1 = createCheckBoxes (WizardryOrigin.resistance, 6, 1);

    // properties
    createLabel ("Property", 8, 0, HPos.RIGHT, 2);
    checkBoxes2 = createCheckBoxes (WizardryOrigin.property, 8, 1);

    monstersList.getSelectionModel ().select (0);
  }

  // ---------------------------------------------------------------------------------//
  private void update (Monster monster)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut1[NAME_PLURAL], monster.namePlural);
    setText (textOut1[GENERIC_NAME], monster.genericName);
    setText (textOut1[MONSTER_CLASS], WizardryOrigin.monsterClass[monster.monsterClass]);
    setText (textOut1[PARTNER], wizardry.getMonsters ().get (monster.enemyTeam).name);
    setText (textOut1[GROUP_DICE], monster.groupSize.toString ());
    setText (textOut1[HP_DICE], monster.hitPoints.toString ());
    setText (textOut1[RECSN], monster.damageDice);

    setText (textOut2[ID], monster.id);
    setText (textOut2[MAGE_LEVEL], monster.mageSpells + "");
    setText (textOut2[PRIEST_LEVEL], monster.priestSpells + "");
    setText (textOut2[MAGIC_RESIST], monster.unaffect + "%");
    setText (textOut2[PARTNER_PCT], monster.teamPercentage + "%");
    setText (textOut2[BREATHE], monster.breathe);
    setText (textOut2[DRAIN], monster.drainAmt);
    setText (textOut2[REGEN], monster.healPts);
    setText (textOut2[EXPERIENCE], monster.experiencePoints);
    setText (textOut2[ARMOUR_CLASS], monster.armourClass);
    setText (textOut2[UNIQUE], monster.unique);
    setText (textOut2[IMAGE], monster.image);
    setText (textOut2[GOLD_REWARDS], monster.reward1);
    setText (textOut2[CHEST_REWARDS], monster.reward2);

    // gold rewards
    setText (textOut3[0], wizardry.getRewards ().get (monster.reward1).goldRange ());

    // item rewards
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
          String itemName1 = wizardry.getItemName (item1);
          String itemName2 = wizardry.getItemName (item2);
          setText (textOut4[i], itemName1 + " : " + itemName2);
        }
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

    wizardry.getImage (monster.image).draw (canvas, 4, Color.WHITE);
  }
}
