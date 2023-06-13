package com.bytezone.wizardry;

import com.bytezone.appbase.BorderedDataPane;
import com.bytezone.appbase.DataLayout;
import com.bytezone.wizardry.data.Item;
import com.bytezone.wizardry.data.Reward;
import com.bytezone.wizardry.data.RewardDetails;
import com.bytezone.wizardry.data.RewardDetails.ItemReward;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class RewardsItemsPane extends BorderedDataPane
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_ITEMS = 5;       // columns of data

  private static final int ITEM_ODDS = 0;
  private static final int ITEM_NO = 1;
  private static final int ITEM_MIN = 2;
  private static final int ITEM_MAX = 3;
  private static final int MIN = 4;
  private static final int SIZE = 5;
  private static final int MAX = 6;
  private static final int RANGE = 7;
  private static final int ITEM_ODDS_2 = 8;

  TextField[][] items = new TextField[MAX_ITEMS][];

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public RewardsItemsPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (6, 9);                              // columns, rows

    int width = 140;
    setColumnConstraints (110, width, width, width, width, width);

    String[] labels = { "Probability", "Item # range", "Item from", "Item to", "Min",
        "Size", "Max", "Range", "Odds" };
    assert getRows () == labels.length;

    createLabelsVertical (labels, 0, 0, HPos.RIGHT);

    DataLayout dataLayout3 = new DataLayout (1, 0, 9, Pos.CENTER_LEFT, false);
    for (int i = 0; i < MAX_ITEMS; i++)
      items[i] = createTextFields (dataLayout3);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    for (int i = 0; i < MAX_ITEMS; i++)
      reset (items[i]);
  }

  // ---------------------------------------------------------------------------------//
  void update (Reward reward)
  // ---------------------------------------------------------------------------------//
  {
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

    int itemCol = 0;
    for (int i = 0; i < reward.total; i++)
    {
      RewardDetails rewardDetails = reward.rewardDetails[i];

      if (rewardDetails.itemReward != null)
      {
        ItemReward itemReward = rewardDetails.itemReward;

        setText (items[itemCol][ITEM_ODDS], rewardDetails.rewardPct + "%");
        setText (items[itemCol][ITEM_NO],
            itemReward.getMin () + " : " + itemReward.getMax ());
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
    }
  }
}
