package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Dice
{
  int level;
  int hpfac;
  int hpminad;

  // ---------------------------------------------------------------------------------//
  public Dice (byte[] buffer, int offset)
  // ---------------------------------------------------------------------------------//
  {
    this.level = Utility.getShort (buffer, offset);
    this.hpfac = Utility.getShort (buffer, offset + 2);
    this.hpminad = Utility.getShort (buffer, offset + 4);
  }

  // ---------------------------------------------------------------------------------//
  public Dice (int level, int hpfac, int hpminad)
  // ---------------------------------------------------------------------------------//
  {
    this.level = level;
    this.hpfac = hpfac;
    this.hpminad = hpminad;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    if (level == 0)
      return "";
    return String.format ("%dD%d+%d", level, hpfac, hpminad);
  }
}
