package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class EnemyOdds
{
  public final int minEnemy;       // ptr
  public final int multWors;       // classize
  public final int worse01;        // classmax
  public final int range0n;        // element
  public final int percWors;       // prob

  // ---------------------------------------------------------------------------------//
  public EnemyOdds (byte[] buffer, int offset)
  // ---------------------------------------------------------------------------------//
  {
    minEnemy = Utility.getShort (buffer, offset);
    multWors = Utility.getShort (buffer, offset + 2);
    worse01 = Utility.getShort (buffer, offset + 4);
    range0n = Utility.getShort (buffer, offset + 6);
    percWors = Utility.getSignedShort (buffer, offset + 8);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%3d  %3d  %3d  %3d  %3d", minEnemy, multWors, worse01, range0n,
        percWors);
  }
}
