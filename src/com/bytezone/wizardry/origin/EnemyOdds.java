package com.bytezone.wizardry.origin;

import java.util.Random;

// -----------------------------------------------------------------------------------//
public class EnemyOdds
// -----------------------------------------------------------------------------------//
{
  private static final Random random = new Random ();

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
  public int getRandomMonster ()
  // ---------------------------------------------------------------------------------//
  {
    // decide which range to use
    int encounterCalc = 0;
    while (random.nextInt (100) < percWors && encounterCalc < worse01)
      ++encounterCalc;

    return minEnemy + random.nextInt (range0n) + multWors * encounterCalc;
  }

  // ---------------------------------------------------------------------------------//
  public void showOdds ()
  // ---------------------------------------------------------------------------------//
  {
    int min = minEnemy;
    int max = minEnemy + range0n - 1;

    double worse = percWors / 100.0;

    double right = 1.0;
    double left;
    double total = 0.0;

    for (int i = 0; i <= worse01; i++)
    {
      left = right * (1 - worse);
      right = right * worse;

      total += left;

      if (i == worse01)
      {
        System.out.printf ("%2d  %2d:%2d  %12.8f%n", i + 1, min, max, (left + right) * 100);
        total += right;
      }
      else
        System.out.printf ("%2d  %2d:%2d  %12.8f%n", i + 1, min, max, left * 100);

      min += multWors;
      max += multWors;
    }

    System.out.println ("           ------------");
    System.out.printf ("           %12.8f%n", total * 100);
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
