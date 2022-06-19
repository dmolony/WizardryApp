package com.bytezone.wizardry;

import java.util.List;

import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.Reward;
import com.bytezone.wizardry.origin.Reward.ItemRange;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class MonsterPane3 extends DataPane
// -----------------------------------------------------------------------------------//
{
  private final TextField[] textOut3;
  private final TextField[] textOut4;
  private final TextField[] textOut5;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public MonsterPane3 ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 145);
    setAllRowConstraints (11, DataPane.ROW_HEIGHT);     // make all rows the same height

    textOut5 = createTextFields (1, new DataPlacement (2, 21, Pos.CENTER_LEFT, 1));
    textOut3 = createTextFields (1, new DataPlacement (2, 22, Pos.CENTER_LEFT, 1));
    textOut4 = createTextFields (3, new DataPlacement (2, 23, Pos.CENTER_LEFT, 3));
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut3);
    reset (textOut4);
    reset (textOut5);
  }

  // ---------------------------------------------------------------------------------//
  void update (Monster monster)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut5[0], monster.getBreatheEffect ());

    if (wizardry.getScenarioId () > 3)
      return;

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
  }
}
