package com.bytezone.wizardry;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.Item;
import com.bytezone.wizardry.data.Reward;
import com.bytezone.wizardry.data.RewardDetails;
import com.bytezone.wizardry.data.RewardDetails.GoldReward;
import com.bytezone.wizardry.data.RewardDetails.ItemReward;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

//-----------------------------------------------------------------------------------//
public class RewardsRootPane extends DataPane
//-----------------------------------------------------------------------------------//
{
  private static final int MAX_ITEMS = 5;       // columns of data

  private static final int IS_CHEST = 0;

  private static final int GOLD_ODDS = 0;
  private static final int DICE_1 = 1;
  private static final int MULTIPLIER = 2;
  private static final int DICE_2 = 3;
  private static final int GOLD_MIN = 4;
  private static final int GOLD_MAX = 5;

  private static final int ITEM_ODDS = 0;
  private static final int ITEM_NO = 1;
  private static final int ITEM_MIN = 2;
  private static final int ITEM_MAX = 3;
  private static final int MIN = 4;
  private static final int SIZE = 5;
  private static final int MAX = 6;
  private static final int RANGE = 7;
  private static final int ITEM_ODDS_2 = 8;

  ComboBox<Reward> rewardsList = new ComboBox<> ();

  TextField[] chest;
  TextField[] gold;
  TextField[][] items = new TextField[MAX_ITEMS][];
  CheckBox[] traps;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public RewardsRootPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (6, 18);                             // columns, rows

    int width = 140;
    setColumnConstraints (110, width, width, width, width, width);
    setPadding (defaultInsets);

    String[] labels = { "Is chest", "", "Probability", "# Dice", "Base", "Mult", "Gold min",
        "Gold max", "", "Probability", "Item # range", "Item from", "Item to", "Min", "Size", "Max",
        "Range", "Odds" };

    createLabelsVertical (labels, 0, 0, HPos.RIGHT);

    DataLayout dataLayout1 = new DataLayout (1, 0, 1, Pos.CENTER_LEFT);
    chest = createTextFields (dataLayout1);

    DataLayout dataLayout2 = new DataLayout (1, 2, 6, Pos.CENTER_LEFT);
    gold = createTextFields (dataLayout2);

    DataLayout dataLayout3 = new DataLayout (1, 9, 9, Pos.CENTER_LEFT);
    for (int i = 0; i < MAX_ITEMS; i++)
      items[i] = createTextFields (dataLayout3);

    // traps
    createLabelsVertical (WizardryData.trapType, 2, 0, HPos.RIGHT);
    traps = createCheckBoxes (new DataLayout (3, 0, WizardryData.trapType.length, HPos.LEFT));
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return 18;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getColumns ()
  // ---------------------------------------------------------------------------------//
  {
    return 6;
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (chest);
    reset (gold);

    reset (traps);

    for (int i = 0; i < MAX_ITEMS; i++)
      reset (items[i]);
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

    // erase items
    for (int itemCol = 0; itemCol < MAX_ITEMS; itemCol++)
    {
      setText (items[itemCol][ITEM_ODDS], "");
      setText (items[itemCol][ITEM_NO], "");
      setText (items[itemCol][ITEM_MIN], "");
      setText (items[itemCol][ITEM_MAX], "");
      setText (items[itemCol][MIN], "");
      setText (items[itemCol][SIZE], "");
      setText (items[itemCol][MAX], "");
      setText (items[itemCol][RANGE], "");
      setText (items[itemCol][ITEM_ODDS_2], "");
    }

    // erase traps
    for (int j = 0; j < WizardryData.trapType.length; j++)
      traps[j].setSelected (false);

    setText (chest[IS_CHEST], reward.isChest);

    int itemCol = 0;
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
      else
      {
        ItemReward itemReward = rewardDetails.itemReward;

        setText (items[itemCol][ITEM_ODDS], rewardDetails.rewardPct + "%");
        setText (items[itemCol][ITEM_NO], itemReward.getMin () + " : " + itemReward.getMax ());
        setText (items[itemCol][ITEM_MIN], wizardry.getItem (itemReward.getMin ()));
        setText (items[itemCol][MIN], itemReward.min ());
        setText (items[itemCol][SIZE], itemReward.size ());
        setText (items[itemCol][MAX], itemReward.max ());
        setText (items[itemCol][RANGE], itemReward.range ());
        setText (items[itemCol][ITEM_ODDS_2], itemReward.odds () + "%");

        if (itemReward.getMin () != itemReward.getMax ())
        {
          Item item = wizardry.getItem (itemReward.getMax ());
          String out = item == null ? "** No such item **" : item.toString ();
          setText (items[itemCol][ITEM_MAX], out);
        }

        ++itemCol;
      }

      int trap = reward.trapTypeFlags;
      for (int j = 0; j < WizardryData.trapType.length; j++)
      {
        traps[j].setSelected ((trap & 0x01) != 0);
        trap >>>= 1;
      }
    }
  }
}
