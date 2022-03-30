package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Reward
// -----------------------------------------------------------------------------------//
{
  int id;
  // ---------------------------------------------------------------------------------//
  public Reward (int id, DataBlock dataBlock)
  // ---------------------------------------------------------------------------------//
  {
    this.id = id;

    byte[] buffer = dataBlock.buffer;
    int offset = dataBlock.offset;

    //    System.out.println (HexFormatter.format (buffer, offset, dataBlock.length));
  }
}
