package com.bytezone.wizardry.origin;

// ---------------------------------------------------------------------------------//
class ScenarioData
// ---------------------------------------------------------------------------------//
{
  static String[] typeText =
      { "header", "maze", "monsters", "rewards", "items", "characters", "images", "char levels" };
  static String[] scenarioNames = { "PROVING GROUNDS OF THE MAD OVERLORD!",
      "THE KNIGHT OF DIAMONDS", "THE LEGACY OF LLYLGAMYN", "THE RETURN OF WERDNA" };

  int dunno;
  int total;
  int totalBlocks;                // size in blocks
  int dataOffset;                 // first block
  int type;

  // -------------------------------------------------------------------------------//
  public ScenarioData (byte[] buffer, int seq)
  // -------------------------------------------------------------------------------//
  {
    int offset = 42 + seq * 2;
    dunno = buffer[offset] & 0xFF;
    total = buffer[offset + 16] & 0xFF;
    totalBlocks = buffer[offset + 32] & 0xFF;
    dataOffset = buffer[offset + 48] & 0xFF;
    type = seq;
  }

  // -------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // -------------------------------------------------------------------------------//
  {
    return String.format ("%-15s  %3d    %3d    %3d    %3d", typeText[type], dataOffset,
        totalBlocks, total, dunno);
  }
}
