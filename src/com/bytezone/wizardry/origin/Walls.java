package com.bytezone.wizardry.origin;

import com.bytezone.wizardry.origin.Maze.Wall;

// -----------------------------------------------------------------------------------//
public class Walls
// -----------------------------------------------------------------------------------//
{
  Wall west;
  Wall south;
  Wall east;
  Wall north;

  // ---------------------------------------------------------------------------------//
  public Walls (Wall west, Wall south, Wall east, Wall north)
  // ---------------------------------------------------------------------------------//
  {
    this.west = west;
    this.south = south;
    this.east = east;
    this.north = north;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%s %s %s %s", west, south, east, north);
  }
}
