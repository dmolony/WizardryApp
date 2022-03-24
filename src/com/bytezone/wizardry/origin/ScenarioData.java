package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

// ---------------------------------------------------------------------------------//
class ScenarioData
// ---------------------------------------------------------------------------------//
{
  byte[] buffer;

  String title;

  int unitsPerBuffer;
  int totalUnits;
  int totalBlocks;
  int firstBlock;
  int type;
  int calc1;

  List<DataBlock> dataBlocks = new ArrayList<> ();

  // -------------------------------------------------------------------------------//
  public ScenarioData (byte[] buffer, int seq)
  // -------------------------------------------------------------------------------//
  {
    this.buffer = buffer;

    title = Utility.getPascalString (buffer, 0);

    int offset = 42 + seq * 2;
    unitsPerBuffer = buffer[offset] & 0xFF;
    totalUnits = buffer[offset + 16] & 0xFF;
    totalBlocks = buffer[offset + 32] & 0xFF;
    firstBlock = buffer[offset + 48] & 0xFF;
    type = seq;

    createDataBlocks ();
  }

  // -------------------------------------------------------------------------------//
  public void createDataBlocks ()
  // -------------------------------------------------------------------------------//
  {
    int offset = firstBlock * 512;
    int totalUnits = 0;
    int unitSize = Header.recordLengths[type];

    for (int bufferNo = 0; bufferNo < totalBlocks / 2; bufferNo++)
    {
      int ptr = offset + bufferNo * 1024;
      for (int unitNo = 0; unitNo < unitsPerBuffer; unitNo++)
      {
        dataBlocks.add (new DataBlock (buffer, ptr, unitSize));
        if (++totalUnits == this.totalUnits)
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
