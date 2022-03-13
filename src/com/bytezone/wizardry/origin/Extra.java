package com.bytezone.wizardry.origin;

import com.bytezone.wizardry.origin.Maze.Square;

// -----------------------------------------------------------------------------------//
public class Extra
// -----------------------------------------------------------------------------------//
{
  Square square;
  int[] aux = new int[3];

  // ---------------------------------------------------------------------------------//
  public Extra (Square square, int aux0, int aux1, int aux2)
  // ---------------------------------------------------------------------------------//
  {
    this.square = square;
    this.aux[0] = aux0;
    this.aux[1] = aux1;
    this.aux[2] = aux2;
  }
}
