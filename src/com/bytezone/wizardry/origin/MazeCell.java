package com.bytezone.wizardry.origin;

import java.awt.Graphics2D;

// -----------------------------------------------------------------------------------//
public class MazeCell
// -----------------------------------------------------------------------------------//
{
  Location location;
  Walls box;
  Extra extra;
  boolean fight;

  // ---------------------------------------------------------------------------------//
  public MazeCell (Location location, Walls box, boolean fight)
  // ---------------------------------------------------------------------------------//
  {
    this.location = location;
    this.fight = fight;
    this.box = box;
  }

  // ---------------------------------------------------------------------------------//
  public MazeCell (Location location, Walls box, boolean fight, Extra extra)
  // ---------------------------------------------------------------------------------//
  {
    this.location = location;
    this.fight = fight;
    this.box = box;
    this.extra = extra;
  }

  // ---------------------------------------------------------------------------------//
  void draw (Graphics2D graphics)
  // ---------------------------------------------------------------------------------//
  {
  }

  // ---------------------------------------------------------------------------------//
  void draw2d (Graphics2D graphics, int distance, int direction)
  // ---------------------------------------------------------------------------------//
  {
  }
}
