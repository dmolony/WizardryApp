package com.bytezone.wizardry.origin;

import java.util.Random;

// -----------------------------------------------------------------------------------//
public class EnemyOdds
// -----------------------------------------------------------------------------------//
{
  private static final Random random = new Random ();

  public final int minEnemy;       // first monster
  public final int range0n;        // range size
  public final int percWors;       // chance of upgrade
  public final int worse01;        // max upgrades possible
  public final int multWors;       // upgrade range size

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

    double odds;
    double oddsLeft = 1.0;
    double total = 0.0;

    for (int i = 0; i <= worse01; i++)
    {
      odds = oddsLeft * (1 - worse);
      oddsLeft = oddsLeft * worse;

      total += odds;

      if (i == worse01)         // last line, so combine both fields
      {
        System.out.printf ("%2d  %2d:%2d  %12.8f%n", i + 1, min, max, (odds + oddsLeft) * 100);
        total += oddsLeft;
      }
      else
        System.out.printf ("%2d  %2d:%2d  %12.8f%n", i + 1, min, max, odds * 100);

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
