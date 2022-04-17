package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

// ---------------------------------------------------------------------------------//
class ScenarioData
// ---------------------------------------------------------------------------------//
{
  static final int[] recordLengths = { 512, 894, 158, 168, 78, 208, 512, 78 };

  String title;               // game name

  int unitsPerBuffer;         // records per 2 x 512 byte blocks
  int totalUnits;             // records per disk
  int totalBlocks;            // 512 byte blocks
  int firstBlock;             // bloff

  int type;
  int calc1;

  List<DataBlock> dataBlocks = new ArrayList<> ();

  // -------------------------------------------------------------------------------//
  public ScenarioData (byte[] buffer, int seq)
  // -------------------------------------------------------------------------------//
  {
    title = Utility.getPascalString (buffer, 0);

    int offset = 42 + seq * 2;
    unitsPerBuffer = buffer[offset] & 0xFF;
    totalUnits = buffer[offset + 16] & 0xFF;
    totalBlocks = buffer[offset + 32] & 0xFF;
    firstBlock = buffer[offset + 48] & 0xFF;
    type = seq;

    offset = firstBlock * 512;
    int total = 0;
    int unitSize = recordLengths[type];

    for (int bufferNo = 0; bufferNo < totalBlocks / 2; bufferNo++)
    {
      int ptr = offset + bufferNo * 1024;
      for (int unitNo = 0; unitNo < unitsPerBuffer; unitNo++)
      {
        dataBlocks.add (new DataBlock (buffer, ptr, unitSize));
        if (++total == totalUnits)
          break;
        ptr += unitSize;
      }
    }
  }

  // -------------------------------------------------------------------------------//
  public void displayDataBlocks ()
  // -------------------------------------------------------------------------------//
  {
    for (DataBlock dataBlock : dataBlocks)
      System.out.println (dataBlock);
  }

  // -------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // -------------------------------------------------------------------------------//
  {
    return String.format ("%-15s  %3d    %3d    %3d    %3d", Header.typeText[type], firstBlock,
        totalBlocks, totalUnits, unitsPerBuffer);
  }
}
