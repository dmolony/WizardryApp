package com.bytezone.wizardry;

import java.util.List;

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
  private final TextField[] textOut1;         // breathe
  private final TextField[] textOut2;         // wandering
  private final TextField[] textOut3;         // lair

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public MonsterPane3 ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (300);
    setAllRowConstraints (5, getRowHeight ());     // make all rows the same height

    textOut1 = createTextFields (1, new DataPlacement (0, 0, Pos.CENTER_LEFT, 1));
    textOut2 = createTextFields (1, new DataPlacement (0, 1, Pos.CENTER_LEFT, 1));
    textOut3 = createTextFields (3, new DataPlacement (0, 2, Pos.CENTER_LEFT, 1));
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return 5;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getColumns ()
  // ---------------------------------------------------------------------------------//
  {
    return 1;
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut1);
    reset (textOut2);
    reset (textOut3);
  }

  // ---------------------------------------------------------------------------------//
  void update (Monster monster)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut1[0], monster.getBreatheEffect ());

    if (wizardry.getScenarioId () > 3)
      return;

    List<Reward> rewards = wizardry.getRewards ();

    // wandering rewards
    setText (textOut2[0], rewards.get (monster.rewardWandering).goldRange () + " GP");

    // lair rewards
    for (int i = 0; i < 3; i++)
      setText (textOut3[i], "");

    Reward reward = rewards.get (monster.rewardLair);

    if (reward.isChest)
    {
      for (int i = 0; i < 3; i++)
      {
        ItemRange itemRange = reward.itemRange (i);
        if (itemRange == null)
          break;

        if (itemRange.itemLo () == itemRange.itemHi ())
          setText (textOut3[i], wizardry.getItems ().get (itemRange.itemLo ()));
        else
        {
          String itemName1 = wizardry.getItemName (itemRange.itemLo ());
          String itemName2 = wizardry.getItemName (itemRange.itemHi ());
          setText (textOut3[i], itemName1 + " : " + itemName2);
        }
      }
    }
    else
      setText (textOut3[0], reward.goldRange () + " GP");
  }
}
