package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class DataBlock
// -----------------------------------------------------------------------------------//
{
  byte[] buffer;
  int offset;
  int length;

  // ---------------------------------------------------------------------------------//
  public DataBlock (byte[] buffer, int offset, int length)
  // ---------------------------------------------------------------------------------//
  {
    this.buffer = buffer;
    this.offset = offset;
    this.length = length;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return HexFormatter.format (buffer, offset, length);
  }
}
