package com.bytezone.wizardry.origin;

import com.bytezone.wizardry.origin.Maze.Direction;

// -----------------------------------------------------------------------------------//
public class Location
// -----------------------------------------------------------------------------------//
{
  int level;
  int row;
  int column;

  // ---------------------------------------------------------------------------------//
  public Location (int level, int column, int row)
  // ---------------------------------------------------------------------------------//
  {
    this.level = level;
    this.row = row;
    this.column = column;
  }

  // ---------------------------------------------------------------------------------//
  public void move (Direction direction)
  // ---------------------------------------------------------------------------------//
  {
    switch (direction)
    {
      case NORTH:
        row--;
        if (row < 0)
          row = 19;
        break;

      case SOUTH:
        row++;
        if (row > 19)
          row = 0;
        break;

      case EAST:
        column++;
        if (column > 19)
          column = 0;
        break;

      case WEST:
        column--;
        if (column < 0)
          column = 19;
        break;
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%2d %2d %2d", level, column, row);
  }
}
