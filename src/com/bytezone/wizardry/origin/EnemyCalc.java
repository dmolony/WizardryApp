package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class EnemyCalc
// -----------------------------------------------------------------------------------//
{
  int minEnemy;       // ptr
  int multWors;       // classize
  int worse01;        // classmax
  int range0n;        // element
  int percWors;       // prob

  // ---------------------------------------------------------------------------------//
  public EnemyCalc (byte[] buffer, int offset)
  // ---------------------------------------------------------------------------------//
  {
    minEnemy = Utility.getShort (buffer, offset);
    multWors = Utility.getShort (buffer, offset + 2);
    worse01 = Utility.getShort (buffer, offset + 4);
    range0n = Utility.getShort (buffer, offset + 6);
    percWors = Utility.getShort (buffer, offset + 8);
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
