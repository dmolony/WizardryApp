package com.bytezone.wizardry;

import static com.bytezone.wizardry.data.Walls.EAST;
import static com.bytezone.wizardry.data.Walls.NORTH;
import static com.bytezone.wizardry.data.Walls.SOUTH;
import static com.bytezone.wizardry.data.Walls.WEST;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import com.bytezone.wizardry.data.Location;
import com.bytezone.wizardry.data.MazeCell;
import com.bytezone.wizardry.data.MazeLevel;
import com.bytezone.wizardry.data.Walls.Wall;
import com.bytezone.wizardry.data.WizardryData.Direction;

// -----------------------------------------------------------------------------------//
public class Walker
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_WALKER = "Walker:L";

  public Location location;
  public Direction direction;
  public MazeLevel mazeLevel;

  List<MovementListener> listeners = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  public Walker (MazeLevel mazeLevel, Direction direction, Location location)
  // ---------------------------------------------------------------------------------//
  {
    this.mazeLevel = mazeLevel;
    this.direction = direction;
    this.location = location;
  }

  // ---------------------------------------------------------------------------------//
  public MazeCell getCurrentMazeCell ()
  // ---------------------------------------------------------------------------------//
  {
    return mazeLevel.getMazeCell (location);
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
      case NORTH -> cell.getWalls ().wall (WEST);
      case SOUTH -> cell.getWalls ().wall (EAST);
      case EAST -> cell.getWalls ().wall (NORTH);
      case WEST -> cell.getWalls ().wall (SOUTH);
    };
  }

  // ---------------------------------------------------------------------------------//
  Wall getRightWall (MazeCell cell)
  // ---------------------------------------------------------------------------------//
  {
    return switch (direction)
    {
      case NORTH -> cell.getWalls ().wall (EAST);
      case SOUTH -> cell.getWalls ().wall (WEST);
      case EAST -> cell.getWalls ().wall (SOUTH);
      case WEST -> cell.getWalls ().wall (NORTH);
    };
  }

  // ---------------------------------------------------------------------------------//
  Wall getCentreWall (MazeCell cell)
  // ---------------------------------------------------------------------------------//
  {
    return switch (direction)
    {
      case NORTH -> cell.getWalls ().wall (NORTH);
      case SOUTH -> cell.getWalls ().wall (SOUTH);
      case EAST -> cell.getWalls ().wall (EAST);
      case WEST -> cell.getWalls ().wall (WEST);
    };
  }

  // ---------------------------------------------------------------------------------//
  public void setDirection (Direction direction)
  // ---------------------------------------------------------------------------------//
  {
    this.direction = direction;
  }

  // ---------------------------------------------------------------------------------//
  public void setLocation (Location location)
  // ---------------------------------------------------------------------------------//
  {
    this.location.set (location.getRow (), location.getColumn ());
    notifyListeners ();
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
  public void addWalkerListener (MovementListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  void notifyListeners ()
  // ---------------------------------------------------------------------------------//
  {
    for (MovementListener listener : listeners)
      listener.walkerMoved (this);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    text.append ("Location : " + location + "\n");
    text.append ("Facing   : " + direction);

    return text.toString ();
  }

  // ---------------------------------------------------------------------------------//
  void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    String key = String.format ("%s%02d", PREFS_WALKER, location.getLevel ());
    String value = String.format ("%s D:%02d", location.toString (), direction.ordinal ());
    prefs.put (key, value);
  }

  // ---------------------------------------------------------------------------------//
  void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    String key = String.format ("%s%02d", PREFS_WALKER, location.getLevel ());
    String value = prefs.get (key, "");

    if (!value.isEmpty ())
    {
      int north = Integer.parseInt (value.substring (8, 10));
      int east = Integer.parseInt (value.substring (14, 16));
      int dir = Integer.parseInt (value.substring (20));

      location.set (north, east);
      direction = Direction.values ()[dir];
    }
  }
}
