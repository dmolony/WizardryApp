package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class RewardDetails
// -----------------------------------------------------------------------------------//
{
  public final int rewardPct;
  public final int type;

  public final GoldReward goldReward;       // if type == 0
  public final ItemReward itemReward;       // if type == 1

  // ---------------------------------------------------------------------------------//
  public RewardDetails (byte[] buffer, int offset)
  // ---------------------------------------------------------------------------------//
  {
    rewardPct = Utility.getShort (buffer, offset);
    type = Utility.getShort (buffer, offset + 2);

    if (type == 0)
    {
      Dice goldDice = new Dice (buffer, offset + 4);
      int base = Utility.getShort (buffer, offset + 10);
      Dice goldDice2 = new Dice (buffer, offset + 12);

      goldReward = new GoldReward (goldDice, base, goldDice2);
      itemReward = null;
    }
    else
    {
      int itemNo = Utility.getShort (buffer, offset + 4);
      int cSize = Utility.getShort (buffer, offset + 6);
      int cMax = Utility.getShort (buffer, offset + 8);
      int element = Utility.getShort (buffer, offset + 10);
      int odds = Utility.getSignedShort (buffer, offset + 12);

      itemReward = new ItemReward (itemNo, cSize, cMax, element, odds);
      goldReward = null;
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    text.append (String.format ("%3d%%  %d  ", rewardPct, type));

    if (type == 0)
    {
      text.append (String.format ("%s %3d %s  min %d  max %d", goldReward.dice1, goldReward.base,
          goldReward.dice2, goldReward.getMin (), goldReward.getMax ()));
    }
    else
    {
      text.append (String.format ("%3d %3d %3d %3d %3d%%  max %d", itemReward.item, itemReward.size,
          itemReward.max, itemReward.element, itemReward.odds, itemReward.getMax ()));
    }

    return text.toString ();
  }

  // ---------------------------------------------------------------------------------//
  public record GoldReward (Dice dice1, int base, Dice dice2)
  // ---------------------------------------------------------------------------------//
  {
    public int getMin ()
    {
      return dice1.min () * dice2.min ();
    }

    public int getMax ()
    {
      return dice1.max () * base * dice2.max ();
    }

    public String getRange ()
    {
      return getMin () + " : " + getMax ();
    }
  }

  // ---------------------------------------------------------------------------------//
  public record ItemReward (int item, int size, int max, int element, int odds)
  // ---------------------------------------------------------------------------------//
  {
    public int getMin ()
    {
      return item;
    }

    public int getMax ()
    {
      return item + size * max + element;         // should this be -1?
    }
  }
}
