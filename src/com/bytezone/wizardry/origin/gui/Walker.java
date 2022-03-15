package com.bytezone.wizardry.origin.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.wizardry.origin.Location;
import com.bytezone.wizardry.origin.Maze.Direction;
import com.bytezone.wizardry.origin.MazeLevel;

// -----------------------------------------------------------------------------------//
public class Walker
// -----------------------------------------------------------------------------------//
{
  public Location location;
  public Direction direction;
  public MazeLevel mazeLevel;

  List<WalkerListener> listeners = new ArrayList<> ();

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
    notifyListeners ();
  }

  // ---------------------------------------------------------------------------------//
  public void turnLeft ()
  // ---------------------------------------------------------------------------------//
  {
    direction = switch (direction)
    {
      case NORTH -> Direction.WEST;
      case SOUTH -> Direction.EAST;
      case EAST -> Direction.NORTH;
      case WEST -> Direction.SOUTH;
    };
    notifyListeners ();
  }

  // ---------------------------------------------------------------------------------//
  public void turnRight ()
  // ---------------------------------------------------------------------------------//
  {
    direction = switch (direction)
    {
      case NORTH -> Direction.EAST;
      case SOUTH -> Direction.WEST;
      case EAST -> Direction.SOUTH;
      case WEST -> Direction.NORTH;
    };
    notifyListeners ();
  }

  // ---------------------------------------------------------------------------------//
  public void activate ()
  // ---------------------------------------------------------------------------------//
  {
    notifyListeners ();
  }

  // ---------------------------------------------------------------------------------//
  public void addWalkerListener (WalkerListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  private void notifyListeners ()
  // ---------------------------------------------------------------------------------//
  {
    for (WalkerListener listener : listeners)
      listener.walkerMoved (this);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    text.append (location);
    text.append (" : ");
    text.append (direction);

    return text.toString ();
  }
}
