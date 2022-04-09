package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Walls
// -----------------------------------------------------------------------------------//
{
  public static final int WEST = 0;
  public static final int SOUTH = 1;
  public static final int EAST = 2;
  public static final int NORTH = 3;

  Wall[] walls = new Wall[4];

  public enum Wall
  {
    OPEN, WALL, DOOR, HIDEDOOR
  }

  // ---------------------------------------------------------------------------------//
  public Walls (Wall west, Wall south, Wall east, Wall north)
  // ---------------------------------------------------------------------------------//
  {
    walls[WEST] = west;
    walls[SOUTH] = south;
    walls[EAST] = east;
    walls[NORTH] = north;
  }

  // ---------------------------------------------------------------------------------//
  public Wall wall (int direction)
  // ---------------------------------------------------------------------------------//
  {
    return walls[direction];
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("W:%s S:%s E:%s N:%s", walls[WEST], walls[SOUTH], walls[EAST],
        walls[NORTH]);
  }
}
