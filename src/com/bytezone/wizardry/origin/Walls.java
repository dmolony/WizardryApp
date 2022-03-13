package com.bytezone.wizardry.origin;

import com.bytezone.wizardry.origin.Maze.Wall;

// -----------------------------------------------------------------------------------//
public class Walls
// -----------------------------------------------------------------------------------//
{
  Wall east;
  Wall west;
  Wall north;
  Wall south;

  // ---------------------------------------------------------------------------------//
  public Walls (Wall west, Wall north, Wall south, Wall east)
  // ---------------------------------------------------------------------------------//
  {
    this.west = west;
    this.north = north;
    this.south = south;
    this.east = east;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%s %s %s, %s", west, north, south, east);
  }
}
