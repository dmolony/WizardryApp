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
    this.minAdd = Utility.getSignedShort (buffer, offset + 4);
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

    if (minAdd == 0)
      return String.format ("%dd%d", level, faces);

    if (minAdd < 0)
      return String.format ("%dd%d%d", level, faces, minAdd);

    return String.format ("%dd%d+%d", level, faces, minAdd);
  }
}
