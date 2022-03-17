package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Walls
// -----------------------------------------------------------------------------------//
{
  public Wall west;
  public Wall south;
  public Wall east;
  public Wall north;

  public enum Wall
  {
    OPEN, WALL, DOOR, HIDEDOOR
  }

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
