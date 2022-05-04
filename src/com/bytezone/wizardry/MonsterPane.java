package com.bytezone.wizardry;

import java.util.List;

import com.bytezone.wizardry.graphics.ImageGraphic;
import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.Reward;
import com.bytezone.wizardry.origin.Reward.ItemRange;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class MonsterPane extends DataPane
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

  private final TextField[] textOut1;
  private final TextField[] textOut2;
  private final TextField[] textOut3;
  private final TextField[] textOut4;
  private final TextField[] textOut5;

  private final CheckBox[] checkBoxes1;
  private final CheckBox[] checkBoxes2;

  private final Canvas canvas;
  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public MonsterPane ()
  // ---------------------------------------------------------------------------------//
  {
    canvas = new Canvas (280, 200);

    setColumnConstraints (110, 90, 100, 30, 100, 30, 130);

    GridPane.setConstraints (canvas, 4, 1);
    GridPane.setColumnSpan (canvas, 3);
    GridPane.setRowSpan (canvas, 7);

    gridPane.getChildren ().add (canvas);

    String[] label1Text = { "Name", "Plural", "Generic name", "Generic plural", "Monster class",
        "Partner", "Appear dice", "Hits dice", "Damage dice" };

    String[] label2Text = { "ID", "Mage level", "Priest level", "Magic resistance", "Partner odds",
        "Image", "Level drain", "Regen", "Experience", "Armour class", "Unique", "Breathe",
        "Wandering reward", "Lair reward" };

    LabelPlacement lp1 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 0, Pos.CENTER_LEFT, 2);
    textOut1 = createTextFields (label1Text, lp1, dp1);

    LabelPlacement lp2 = new LabelPlacement (0, 10, HPos.RIGHT, 1);
    DataPlacement dp2 = new DataPlacement (1, 10, Pos.CENTER_RIGHT, 1);
    textOut2 = createTextFields (label2Text, lp2, dp2);

    textOut5 = createTextFields (1, new DataPlacement (2, 21, Pos.CENTER_LEFT, 1));
    textOut3 = createTextFields (1, new DataPlacement (2, 22, Pos.CENTER_LEFT, 1));
    textOut4 = createTextFields (3, new DataPlacement (2, 23, Pos.CENTER_LEFT, 3));

    // resistance
    createLabel ("Resistance", 2, 10, HPos.RIGHT, 2);
    checkBoxes1 = createCheckBoxes (WizardryData.resistance, 2, 11);

    // properties
    createLabel ("Property", 4, 10, HPos.RIGHT, 2);
    checkBoxes2 = createCheckBoxes (WizardryData.property, 4, 11);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut1);
    reset (textOut2);
    reset (textOut3);
    reset (textOut4);
    reset (textOut5);

    reset (checkBoxes1);
    reset (checkBoxes2);

    GraphicsContext gc = canvas.getGraphicsContext2D ();
    gc.setFill (Color.BLACK);
    gc.fillRect (0, 0, canvas.getWidth (), canvas.getHeight ());
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
    setText (textOut1[PARTNER], wizardry.getMonsters ().get (monster.partnerId).name);
    setText (textOut1[GROUP_DICE], monster.groupSize.toString ());
    setText (textOut1[HP_DICE], monster.hitPoints.toString ());
    setText (textOut1[RECSN], monster.damageDiceText);

    setText (textOut2[ID], monster.id);
    setText (textOut2[MAGE_LEVEL], monster.mageSpells + "");
    setText (textOut2[PRIEST_LEVEL], monster.priestSpells + "");
    setText (textOut2[MAGIC_RESIST], monster.unaffect + "%");
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

    setText (textOut5[0], monster.getBreatheEffect ());

    List<Reward> rewards = wizardry.getRewards ();

    // wandering rewards
    setText (textOut3[0], rewards.get (monster.rewardWandering).goldRange () + " GP");

    // lair rewards
    for (int i = 0; i < 3; i++)
      setText (textOut4[i], "");

    Reward reward = rewards.get (monster.rewardLair);

    if (reward.isChest)
    {
      for (int i = 0; i < 3; i++)
      {
        ItemRange itemRange = reward.itemRange (i);
        if (itemRange == null)
          break;

        if (itemRange.itemLo () == itemRange.itemHi ())
          setText (textOut4[i], wizardry.getItems ().get (itemRange.itemLo ()));
        else
        {
          String itemName1 = wizardry.getItemName (itemRange.itemLo ());
          String itemName2 = wizardry.getItemName (itemRange.itemHi ());
          setText (textOut4[i], itemName1 + " : " + itemName2);
        }
      }
    }
    else
      setText (textOut4[0], reward.goldRange () + " GP");

    int resistance = monster.resistance;
    for (int i = 0; i < WizardryData.resistance.length; i++)
    {
      checkBoxes1[i].setSelected ((resistance & 0x01) != 0);
      resistance >>>= 1;
    }

    int property = monster.properties;
    for (int i = 0; i < WizardryData.property.length; i++)
    {
      checkBoxes2[i].setSelected ((property & 0x01) != 0);
      property >>>= 1;
    }

    ImageGraphic imageGraphic = new ImageGraphic (wizardry.getImage (monster.image));
    imageGraphic.setColor (Color.WHITE);
    imageGraphic.draw (canvas, 4);
  }
}
