package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Reward
// -----------------------------------------------------------------------------------//
{
  int id;

  public final boolean isChest;
  public final int trapTypeFlags;
  public final int total;
  public final RewardOdds[] rewardOdds = new RewardOdds[9];

  // ---------------------------------------------------------------------------------//
  public Reward (int id, DataBlock dataBlock)
  // ---------------------------------------------------------------------------------//
  {
    this.id = id;

    byte[] buffer = dataBlock.buffer;
    int offset = dataBlock.offset;

    isChest = Utility.getSignedShort (buffer, offset) != 0;
    trapTypeFlags = Utility.getShort (buffer, offset + 2);
    total = Utility.getShort (buffer, offset + 4);

    System.out.printf ("%nTable     : %d%n", id);
    System.out.printf ("Chest     : %s%n", isChest);
    System.out.printf ("Trap type : %s%n", Utility.getBitString (trapTypeFlags, 8));
    System.out.printf ("Rewards   : %d%n%n", total);

    for (int i = 0; i < total; i++)
    {
      rewardOdds[i] = new RewardOdds (buffer, offset + 6 + 18 * i);
      System.out.println (rewardOdds[i]);
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return "Table # " + id;
  }
}
