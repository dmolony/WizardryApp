package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Item;
import com.bytezone.wizardry.origin.Reward;
import com.bytezone.wizardry.origin.RewardDetails;
import com.bytezone.wizardry.origin.RewardDetails.GoldReward;
import com.bytezone.wizardry.origin.RewardDetails.ItemReward;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class RewardsPane extends BasePane
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_ITEMS = 5;

  private static final int IS_CHEST = 0;

  private static final int GOLD_ODDS = 0;
  private static final int DICE_1 = 1;
  private static final int MULTIPLIER = 2;
  private static final int DICE_2 = 3;
  private static final int GOLD_MIN = 4;
  private static final int GOLD_MAX = 5;

  private static final int ITEM_ODDS = 0;
  private static final int ITEM_NO = 1;
  private static final int ITEM = 2;
  private static final int ITEM_MAX = 3;
  private static final int SIZE = 4;
  private static final int MAX = 5;
  private static final int ELEMENT = 6;
  private static final int ITEM_ODDS_2 = 7;

  String[] label1Text = { "Is chest" };
  String[] goldLabels = { "Probability", "# Dice", "Base", "Mult", "Gold min", "Gold max", };
  String[] itemLabels = { "Probability", "Item # range", "Item from", "Item to", "Size", "Max",
      "Element", "Probability" };

  ComboBox<Reward> rewardsList = new ComboBox<> ();

  TextField[] textOut1;
  TextField[] gold;
  TextField[][] items = new TextField[MAX_ITEMS][];
  CheckBox[] traps;

  // ---------------------------------------------------------------------------------//
  public RewardsPane (WizardryOrigin wizardry, Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry, stage);

    int width = 140;
    setColumnConstraints (110, width, width, width, width, width, width);

    // make all rows the same height
    RowConstraints rowCo = new RowConstraints (25);
    for (int i = 0; i < 20; i++)
      gridPane.getRowConstraints ().add (rowCo);

    // table list
    LabelPlacement lp0 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp0 = new DataPlacement (1, 0, Pos.CENTER_LEFT, 2);
    setComboBox ("Item", rewardsList, wizardry.getRewards (),
        (options, oldValue, newValue) -> update (newValue), lp0, dp0);

    // basic attributes
    LabelPlacement lp1 = new LabelPlacement (0, 1, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 1, Pos.CENTER_LEFT, 1);
    textOut1 = createOutputFields (label1Text, lp1, dp1);

    // gold reward
    LabelPlacement lp3 = new LabelPlacement (0, 3, HPos.RIGHT, 1);
    DataPlacement dp3 = new DataPlacement (1, 4, Pos.CENTER_LEFT, 1);
    gold = createOutputFields (goldLabels, lp3, dp3);

    // first item reward
    LabelPlacement lp4 = new LabelPlacement (0, 10, HPos.RIGHT, 1);
    DataPlacement dp4 = new DataPlacement (1, 10, Pos.CENTER_LEFT, 1);
    items[0] = createOutputFields (itemLabels, lp4, dp4);

    // second  thru fourth item rewards
    for (int i = 1; i < MAX_ITEMS; i++)
      items[i] = createOutputFields (8, new DataPlacement (i + 1, 10, Pos.CENTER_LEFT, 1));

    // traps
    traps = createCheckBoxes (WizardryOrigin.trapType, 2, 1);

    rewardsList.getSelectionModel ().select (0);
  }

  // ---------------------------------------------------------------------------------//
  private void update (Reward reward)
  // ---------------------------------------------------------------------------------//
  {
    // erase gold
    setText (gold[GOLD_ODDS], "");
    setText (gold[DICE_1], "");
    setText (gold[MULTIPLIER], "");
    setText (gold[DICE_2], "");
    setText (gold[GOLD_MIN], "");
    setText (gold[GOLD_MAX], "");

    // erase items
    for (int itemCol = 0; itemCol < MAX_ITEMS; itemCol++)
    {
      setText (items[itemCol][ITEM_ODDS], "");
      setText (items[itemCol][ITEM_NO], "");
      setText (items[itemCol][ITEM], "");
      setText (items[itemCol][SIZE], "");
      setText (items[itemCol][MAX], "");
      setText (items[itemCol][ELEMENT], "");
      setText (items[itemCol][ITEM_ODDS_2], "");
      setText (items[itemCol][ITEM_MAX], "");
    }

    // erase traps
    for (int j = 0; j < WizardryOrigin.trapType.length; j++)
      traps[j].setSelected (false);

    setText (textOut1[IS_CHEST], reward.isChest);

    int itemCol = 0;
    for (int i = 0; i < reward.total; i++)
    {
      RewardDetails rewardOdds = reward.rewardDetails[i];

      if (rewardOdds.goldReward != null)
      {
        GoldReward goldReward = rewardOdds.goldReward;

        setText (gold[GOLD_ODDS], rewardOdds.rewardPct + "%");
        setText (gold[DICE_1], goldReward.dice1 ());
        setText (gold[MULTIPLIER], goldReward.base ());
        setText (gold[DICE_2], goldReward.dice2 ());
        setText (gold[GOLD_MIN], goldReward.getMin ());
        setText (gold[GOLD_MAX], goldReward.getMax ());
      }
      else
      {
        ItemReward itemReward = rewardOdds.itemReward;

        setText (items[itemCol][ITEM_ODDS], rewardOdds.rewardPct + "%");
        setText (items[itemCol][ITEM_NO], itemReward.item () + " : " + itemReward.getMax ());
        setText (items[itemCol][ITEM], wizardry.getItem (itemReward.item ()));
        setText (items[itemCol][SIZE], itemReward.size ());
        setText (items[itemCol][MAX], itemReward.max ());
        setText (items[itemCol][ELEMENT], itemReward.element ());
        setText (items[itemCol][ITEM_ODDS_2], itemReward.odds () + "%");

        if (itemReward.item () != itemReward.getMax ())
        {
          Item item = wizardry.getItem (itemReward.getMax ());
          String out = item == null ? "** No such item **" : item.toString ();
          setText (items[itemCol][ITEM_MAX], out);
        }

        ++itemCol;
      }

      int trap = reward.trapTypeFlags;
      for (int j = 0; j < WizardryOrigin.trapType.length; j++)
      {
        traps[j].setSelected ((trap & 0x01) != 0);
        trap >>>= 1;
      }
    }
  }
}
