package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Damage
// -----------------------------------------------------------------------------------//
{
  private final int dice;
  private final int sides;
  private final int plus;

  // ---------------------------------------------------------------------------------//
  public Damage (int[] aux)
  // ---------------------------------------------------------------------------------//
  {
    plus = aux[0];
    sides = aux[1];
    dice = aux[2];
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    if (plus > 0)
      return String.format ("Damage : %dd%d+%d", dice, sides, plus);
    return String.format ("Damage : %dd%d", dice, sides);
  }
}
