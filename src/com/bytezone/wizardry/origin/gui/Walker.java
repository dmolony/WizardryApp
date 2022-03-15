package com.bytezone.wizardry.origin.gui;

import com.bytezone.wizardry.origin.Location;
import com.bytezone.wizardry.origin.Maze.Direction;
import com.bytezone.wizardry.origin.MazeLevel;

// -----------------------------------------------------------------------------------//
public class Walker
// -----------------------------------------------------------------------------------//
{
  Location location;
  Direction direction;
  MazeLevel mazeLevel;

  // ---------------------------------------------------------------------------------//
  public Walker (MazeLevel mazeLevel, Direction direction, Location location)
  // ---------------------------------------------------------------------------------//
  {
    this.mazeLevel = mazeLevel;
    this.direction = direction;
    this.location = location;
  }

  // ---------------------------------------------------------------------------------//
  public void setDirection (Direction direction)
  // ---------------------------------------------------------------------------------//
  {
    this.direction = direction;
  }

  // ---------------------------------------------------------------------------------//
  public void move ()
  // ---------------------------------------------------------------------------------//
  {
    location.move (direction);
  }
}
