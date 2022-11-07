package com.bytezone.wizardry;

import java.util.List;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.Monster;
import com.bytezone.wizardry.data.Reward;
import com.bytezone.wizardry.data.Reward.ItemRange;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class MonsterPane3 extends DataPane
// -----------------------------------------------------------------------------------//
{
  private final TextField[] textOut;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public MonsterPane3 ()
  // ---------------------------------------------------------------------------------//
  {
    super (1, 5);                             // columns, rows

    setColumnConstraints (300);

    textOut = createTextFields (new DataLayout (0, 0, getRows (), Pos.CENTER_LEFT));
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
    setText (textOut[0], monster.getBreatheEffect ());

    if (wizardry.getScenarioId () > 3)
      return;

    List<Reward> rewards = wizardry.getRewards ();

    // wandering rewards
    String range = rewards.get (monster.rewardWandering).goldRange ();
    if (range.isEmpty ())
      setText (textOut[1], "");
    else
      setText (textOut[1], rewards.get (monster.rewardWandering).goldRange () + " GP");

    // lair rewards
    for (int i = 0; i < 3; i++)
      setText (textOut[i + 2], "");

    Reward reward = rewards.get (monster.rewardLair);

    //    if (reward.isChest)
    if (reward.itemRange (0) != null)       // Werdna has a reward but no chest
    {
      for (int i = 0; i < 3; i++)
      {
        ItemRange itemRange = reward.itemRange (i);
        if (itemRange == null)
          break;

        if (itemRange.itemLo () == itemRange.itemHi ())
          setText (textOut[i + 2], wizardry.getItems ().get (itemRange.itemLo ()));
        else
        {
          String itemName1 = wizardry.getItemName (itemRange.itemLo ());
          String itemName2 = wizardry.getItemName (itemRange.itemHi ());
          setText (textOut[i + 2], itemName1 + " : " + itemName2);
        }
      }
    }
    else
      setText (textOut[2], reward.goldRange () + " GP");
  }
}
