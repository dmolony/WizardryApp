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
  int totalBlocks;                // size in blocks
  int firstBlock;                 // first block
  int type;

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

    //    System.out.println (HexFormatter.format (buffer, 0, 512));

    //    for (int ptr = 42; ptr < 106; ptr += 2)
    //      System.out.printf ("%d%n", Utility.getShort (buffer, ptr));
  }

  // -------------------------------------------------------------------------------//
  public void createDataBlocks ()
  // -------------------------------------------------------------------------------//
  {
    int offset = firstBlock * 512;
    int totalUnits = 0;
    int unitSize = Header.recordLength[type];

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
  public void displayRecords ()
  // -------------------------------------------------------------------------------//
  {
    int offset = firstBlock * 512;
    int totalUnits = 0;
    int unitSize = Header.recordLength[type];

    System.out.printf ("Unit size: %,d%n", unitSize);

    for (int bufferNo = 0; bufferNo < totalBlocks / 2; bufferNo++)
    {
      System.out.printf ("Buffer No: %d%n", bufferNo);
      int ptr = offset + bufferNo * 1024;
      for (int unitNo = 0; unitNo < unitsPerBuffer; unitNo++)
      {
        dataBlocks.add (new DataBlock (buffer, ptr, unitSize));
        System.out.printf ("Unit %d%n", unitNo);
        System.out.println (HexFormatter.format (buffer, ptr, unitSize));
        if (++totalUnits == this.totalUnits)
          break;
        ptr += unitSize;
        System.out.println ();
      }
    }
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
