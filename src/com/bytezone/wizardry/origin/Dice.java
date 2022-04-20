package com.bytezone.wizardry.origin;

import java.util.Random;

// -----------------------------------------------------------------------------------//
public class Dice
// -----------------------------------------------------------------------------------//
{
  private static final Random random = new Random ();

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
  public int max ()
  // ---------------------------------------------------------------------------------//
  {
    return level * faces + minAdd;
  }

  // ---------------------------------------------------------------------------------//
  public int min ()
  // ---------------------------------------------------------------------------------//
  {
    return level + minAdd;
  }

  // ---------------------------------------------------------------------------------//
  public int roll ()
  // ---------------------------------------------------------------------------------//
  {
    int total = minAdd;

    if (faces > 0)
      for (int die = 0; die < level; die++)
        total += random.nextInt (faces) + 1;

    return total;
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
