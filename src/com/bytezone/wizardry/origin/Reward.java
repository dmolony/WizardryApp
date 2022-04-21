package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Reward
// -----------------------------------------------------------------------------------//
{
  private final int id;

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
  public String goldRange ()
  // ---------------------------------------------------------------------------------//
  {
    if (rewardDetails[0].goldReward != null)
    {
      return rewardDetails[0].goldReward.getRange ();
    }
    return "";
  }

  // ---------------------------------------------------------------------------------//
  public ItemRange itemRange (int index)
  // ---------------------------------------------------------------------------------//
  {
    int count = 0;

    for (int i = 0; i < total; i++)
      if (rewardDetails[i].itemReward != null && count++ == index)
        return new ItemRange (rewardDetails[i].itemReward.getMin (),
            rewardDetails[i].itemReward.getMax ());

    return null;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return "Table # " + id;
  }

  // ---------------------------------------------------------------------------------//
  public record ItemRange (int itemLo, int itemHi)
  // ---------------------------------------------------------------------------------//
  {
  }
}
