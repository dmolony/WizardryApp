package com.bytezone.mazewalker.gui;

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
  String[] label2Text = { "Gold odds", "Dice 1", "Multiplier", "Dice 2", "Gold min", "Gold max", };
  String[] label3Text = { "Item odds", "Item # range", "Item from", "Item to", "Size", "Max",
      "Element", "Probability" };

  ComboBox<Reward> rewardsList = new ComboBox<> ();

  TextField[] textOut1;
  TextField[] textOut2;
  TextField[][] textOut3 = new TextField[4][];
  CheckBox[] checkBoxes1;

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
    textOut2 = createOutputFields (label2Text, lp3, dp3);

    // item reward
    LabelPlacement lp4 = new LabelPlacement (0, 10, HPos.RIGHT, 1);
    DataPlacement dp4 = new DataPlacement (1, 10, Pos.CENTER_LEFT, 1);
    textOut3[0] = createOutputFields (label3Text, lp4, dp4);

    // second reward
    for (int i = 1; i < 4; i++)
      textOut3[i] = createOutputFields (8, new DataPlacement (i + 1, 10, Pos.CENTER_LEFT, 1));

    // traps
    checkBoxes1 = createCheckBoxes (WizardryOrigin.trapType, 2, 1);

    rewardsList.getSelectionModel ().select (0);
  }

  // ---------------------------------------------------------------------------------//
  private void update (Reward reward)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut1[IS_CHEST], reward.isChest);

    // erase gold
    setText (textOut2[GOLD_ODDS], "");
    setText (textOut2[DICE_1], "");
    setText (textOut2[MULTIPLIER], "");
    setText (textOut2[DICE_2], "");
    setText (textOut2[GOLD_MIN], "");
    setText (textOut2[GOLD_MAX], "");

    // erase items
    for (int itemCol = 0; itemCol < 4; itemCol++)
    {
      setText (textOut3[itemCol][ITEM_ODDS], "");
      setText (textOut3[itemCol][ITEM_NO], "");
      setText (textOut3[itemCol][ITEM], "");
      setText (textOut3[itemCol][SIZE], "");
      setText (textOut3[itemCol][MAX], "");
      setText (textOut3[itemCol][ELEMENT], "");
      setText (textOut3[itemCol][ITEM_ODDS_2], "");
      setText (textOut3[itemCol][ITEM_MAX], "");
    }

    int itemCol = 0;
    for (int i = 0; i < reward.total; i++)
    {
      RewardDetails rewardOdds = reward.rewardDetails[i];

      if (rewardOdds.goldReward != null)
      {
        GoldReward goldReward = rewardOdds.goldReward;

        setText (textOut2[GOLD_ODDS], rewardOdds.rewardPct + "%");
        setText (textOut2[DICE_1], goldReward.dice1 ());
        setText (textOut2[MULTIPLIER], goldReward.base ());
        setText (textOut2[DICE_2], goldReward.dice2 ());
        setText (textOut2[GOLD_MIN], goldReward.getMin ());
        setText (textOut2[GOLD_MAX], goldReward.getMax ());
      }
      else
      {
        ItemReward itemReward = rewardOdds.itemReward;

        setText (textOut3[itemCol][ITEM_ODDS], rewardOdds.rewardPct + "%");
        setText (textOut3[itemCol][ITEM_NO], itemReward.item () + " : " + itemReward.getMax ());
        setText (textOut3[itemCol][ITEM], wizardry.getItem (itemReward.item ()));
        setText (textOut3[itemCol][SIZE], itemReward.size ());
        setText (textOut3[itemCol][MAX], itemReward.max ());
        setText (textOut3[itemCol][ELEMENT], itemReward.element ());
        setText (textOut3[itemCol][ITEM_ODDS_2], itemReward.odds () + "%");
        if (itemReward.item () != itemReward.getMax ())
          setText (textOut3[itemCol][ITEM_MAX], wizardry.getItem (itemReward.getMax ()));

        ++itemCol;
      }

      int trap = reward.trapTypeFlags;
      for (int j = 0; j < WizardryOrigin.trapType.length; j++)
      {
        checkBoxes1[j].setSelected ((trap & 0x01) != 0);
        trap >>>= 1;
      }
    }
  }
}
