package com.bytezone.wizardry.origin.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.wizardry.origin.Location;
import com.bytezone.wizardry.origin.Maze.Direction;
import com.bytezone.wizardry.origin.MazeCell;
import com.bytezone.wizardry.origin.MazeLevel;
import com.bytezone.wizardry.origin.Walls.Wall;

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
  public MazeCell[] getCells (int distance)
  // ---------------------------------------------------------------------------------//
  {
    MazeCell[] cells = new MazeCell[5];
    int column = location.getColumn ();
    int row = location.getRow ();

    switch (direction)
    {
      case NORTH:
        row += distance;
        cells[0] = mazeLevel.getMazeCell (column - 2, row);
        cells[1] = mazeLevel.getMazeCell (column - 1, row);
        cells[2] = mazeLevel.getMazeCell (column, row);
        cells[3] = mazeLevel.getMazeCell (column + 1, row);
        cells[4] = mazeLevel.getMazeCell (column + 2, row);
        break;

      case SOUTH:
        row -= distance;
        cells[0] = mazeLevel.getMazeCell (column + 2, row);
        cells[1] = mazeLevel.getMazeCell (column + 1, row);
        cells[2] = mazeLevel.getMazeCell (column, row);
        cells[3] = mazeLevel.getMazeCell (column - 1, row);
        cells[4] = mazeLevel.getMazeCell (column - 2, row);
        break;

      case EAST:
        column += distance;
        cells[0] = mazeLevel.getMazeCell (column, row + 2);
        cells[1] = mazeLevel.getMazeCell (column, row + 1);
        cells[2] = mazeLevel.getMazeCell (column, row);
        cells[3] = mazeLevel.getMazeCell (column, row - 1);
        cells[4] = mazeLevel.getMazeCell (column, row - 2);
        break;

      case WEST:
        column -= distance;
        cells[0] = mazeLevel.getMazeCell (column, row - 2);
        cells[1] = mazeLevel.getMazeCell (column, row - 1);
        cells[2] = mazeLevel.getMazeCell (column, row);
        cells[3] = mazeLevel.getMazeCell (column, row + 1);
        cells[4] = mazeLevel.getMazeCell (column, row + 2);
        break;
    }

    return cells;
  }

  // ---------------------------------------------------------------------------------//
  Wall getLeftWall (MazeCell cell)
  // ---------------------------------------------------------------------------------//
  {
    return switch (direction)
    {
      case NORTH -> cell.getWalls ().west;
      case SOUTH -> cell.getWalls ().east;
      case EAST -> cell.getWalls ().north;
      case WEST -> cell.getWalls ().south;
    };
  }

  // ---------------------------------------------------------------------------------//
  Wall getRightWall (MazeCell cell)
  // ---------------------------------------------------------------------------------//
  {
    return switch (direction)
    {
      case NORTH -> cell.getWalls ().east;
      case SOUTH -> cell.getWalls ().west;
      case EAST -> cell.getWalls ().south;
      case WEST -> cell.getWalls ().north;
    };
  }

  // ---------------------------------------------------------------------------------//
  Wall getCentreWall (MazeCell cell)
  // ---------------------------------------------------------------------------------//
  {
    return switch (direction)
    {
      case NORTH -> cell.getWalls ().north;
      case SOUTH -> cell.getWalls ().south;
      case EAST -> cell.getWalls ().east;
      case WEST -> cell.getWalls ().west;
    };
  }

  // ---------------------------------------------------------------------------------//
  public void setDirection (Direction direction)
  // ---------------------------------------------------------------------------------//
  {
    this.direction = direction;
  }

  // ---------------------------------------------------------------------------------//
  public void forward ()
  // ---------------------------------------------------------------------------------//
  {
    location.move (direction);
    notifyListeners ();
  }

  // ---------------------------------------------------------------------------------//
  public void back ()
  // ---------------------------------------------------------------------------------//
  {
    location.move (reverse ());
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
  private Direction reverse ()
  // ---------------------------------------------------------------------------------//
  {
    return switch (direction)
    {
      case NORTH -> Direction.SOUTH;
      case SOUTH -> Direction.NORTH;
      case EAST -> Direction.WEST;
      case WEST -> Direction.EAST;
    };
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
