package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.wizardry.origin.WizardryOrigin.Square;

// -----------------------------------------------------------------------------------//
public class Extra
// -----------------------------------------------------------------------------------//
{
  public final Square square;
  public final int[] aux = new int[3];
  public final List<Location> locations = new ArrayList<> ();

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
  void addLocation (Location location)
  // ---------------------------------------------------------------------------------//
  {
    locations.add (location);
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
    return String.format ("%-8s  %5d  %4d  %4d  (%2d)", square, aux[0], aux[1], aux[2],
        locations.size ());
  }
}
