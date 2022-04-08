package com.bytezone.wizardry.origin;

import com.bytezone.wizardry.origin.WizardryOrigin.Square;

// -----------------------------------------------------------------------------------//
public class Extra
// -----------------------------------------------------------------------------------//
{
  public final Square square;
  public final int[] aux = new int[3];

  // ---------------------------------------------------------------------------------//
  public Extra (int index, byte[] buffer, int offset)
  // ---------------------------------------------------------------------------------//
  {
    byte b = buffer[offset + (index) / 2];
    int val = index % 2 == 0 ? b & 0x0F : (b & 0xF0) >>> 4;

    square = WizardryOrigin.Square.values ()[val];
    aux[0] = Utility.getSignedShort (buffer, offset + 8 + index * 2);
    aux[1] = Utility.getSignedShort (buffer, offset + 40 + index * 2);
    aux[2] = Utility.getSignedShort (buffer, offset + 72 + index * 2);
  }

  // ---------------------------------------------------------------------------------//
  public Square getSquare ()
  // ---------------------------------------------------------------------------------//
  {
    return square;
  }

  // ---------------------------------------------------------------------------------//
  public int[] getAux ()
  // ---------------------------------------------------------------------------------//
  {
    return aux;
  }

  // ---------------------------------------------------------------------------------//
  public boolean is (Square square)
  // ---------------------------------------------------------------------------------//
  {
    return this.square == square;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%-8s  %4d  %4d  %4d", square, aux[0], aux[1], aux[2]);
  }
}
