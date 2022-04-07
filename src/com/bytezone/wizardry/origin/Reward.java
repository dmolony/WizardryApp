package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Reward
// -----------------------------------------------------------------------------------//
{
  int id;

  public final boolean isChest;
  public final int trapTypeFlags;
  public final int total;
  public final RewardDetails[] rewardDetails = new RewardDetails[9];

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

    for (int i = 0; i < total; i++)
      rewardDetails[i] = new RewardDetails (buffer, offset + 6 + 18 * i);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return "Table # " + id;
  }
}
