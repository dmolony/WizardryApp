package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Dice
{
  public final int level;
  public final int faces;
  public final int minAdd;

  // ---------------------------------------------------------------------------------//
  public Dice (byte[] buffer, int offset)
  // ---------------------------------------------------------------------------------//
  {
    this.level = Utility.getShort (buffer, offset);
    this.faces = Utility.getShort (buffer, offset + 2);
    this.minAdd = Utility.getShort (buffer, offset + 4);
  }

  // ---------------------------------------------------------------------------------//
  public Dice (int level, int hpfac, int hpminad)
  // ---------------------------------------------------------------------------------//
  {
    this.level = level;
    this.faces = hpfac;
    this.minAdd = hpminad;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    if (level == 0)
      return "";
    return String.format ("%dD%d+%d", level, faces, minAdd);
  }
}
