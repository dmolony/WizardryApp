package com.bytezone.wizardry;

import com.bytezone.appbase.BorderedDataPane;
import com.bytezone.appbase.DataLayout;
import com.bytezone.wizardry.data.Reward;
import com.bytezone.wizardry.data.RewardDetails;
import com.bytezone.wizardry.data.RewardDetails.GoldReward;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class RewardsGoldPane extends BorderedDataPane
// -----------------------------------------------------------------------------------//
{
  private static final int GOLD_ODDS = 0;
  private static final int DICE_1 = 1;
  private static final int MULTIPLIER = 2;
  private static final int DICE_2 = 3;
  private static final int GOLD_MIN = 4;
  private static final int GOLD_MAX = 5;

  TextField[] gold;

  // ---------------------------------------------------------------------------------//
  public RewardsGoldPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 6);                              // columns, rows

    setColumnConstraints (110, 140);

    String[] labels = { "Probability", "# Dice", "Base", "Mult", "Gold min", "Gold max" };
    createLabelsVertical (labels, 0, 0, HPos.RIGHT);
    assert getRows () == labels.length;

    DataLayout dataLayout2 = new DataLayout (1, 0, 6, Pos.CENTER_LEFT);
    gold = createTextFields (dataLayout2);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    reset (gold);
  }

  // ---------------------------------------------------------------------------------//
  void update (Reward reward)
  // ---------------------------------------------------------------------------------//
  {
    // erase gold
    setText (gold[GOLD_ODDS], "");
    setText (gold[DICE_1], "");
    setText (gold[MULTIPLIER], "");
    setText (gold[DICE_2], "");
    setText (gold[GOLD_MIN], "");
    setText (gold[GOLD_MAX], "");

    for (int i = 0; i < reward.total; i++)
    {
      RewardDetails rewardDetails = reward.rewardDetails[i];

      if (rewardDetails.goldReward != null)
      {
        GoldReward goldReward = rewardDetails.goldReward;

        setText (gold[GOLD_ODDS], rewardDetails.rewardPct + "%");
        setText (gold[DICE_1], goldReward.dice1 ());
        setText (gold[MULTIPLIER], goldReward.base ());
        setText (gold[DICE_2], goldReward.dice2 ());
        setText (gold[GOLD_MIN], goldReward.getMin ());
        setText (gold[GOLD_MAX], goldReward.getMax ());
      }
    }
  }
}
