package com.bytezone.wizardry.origin;

import com.bytezone.mazewalker.gui.Walker;
import com.bytezone.wizardry.origin.Walls.Wall;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class MazeCell
// -----------------------------------------------------------------------------------//
{
  public static final int CELL_SIZE = 40;
  public static final int INSET = 5;

  private Location location;
  private Walls walls;
  private Extra extra;
  private boolean lair;

  // ---------------------------------------------------------------------------------//
  public MazeCell (Location location, Walls walls, boolean lair)
  // ---------------------------------------------------------------------------------//
  {
    this.location = location;
    this.lair = lair;
    this.walls = walls;
  }

  // ---------------------------------------------------------------------------------//
  public void addExtra (Extra extra)
  // ---------------------------------------------------------------------------------//
  {
    this.extra = extra;
  }

  // ---------------------------------------------------------------------------------//
  public Walls getWalls ()
  // ---------------------------------------------------------------------------------//
  {
    return walls;
  }

  // ---------------------------------------------------------------------------------//
  public Extra getExtra ()
  // ---------------------------------------------------------------------------------//
  {
    return extra;
  }

  // ---------------------------------------------------------------------------------//
  public boolean getFight ()
  // ---------------------------------------------------------------------------------//
  {
    return lair;
  }

  // ---------------------------------------------------------------------------------//
  public Location getLocation ()
  // ---------------------------------------------------------------------------------//
  {
    return location;
  }

  // ---------------------------------------------------------------------------------//
  public void drawWalker (GraphicsContext gc, Walker walker)
  // ---------------------------------------------------------------------------------//
  {
    if (!location.matches (walker.location))
    {
      System.out.println ("wrong location");
      return;
    }

    int top = (19 - location.getRow ()) * CELL_SIZE + INSET;
    int left = location.getColumn () * CELL_SIZE + INSET;

    gc.setFill (Color.RED);
    gc.fillOval (left + 12, top + 12, 14, 14);

    gc.setStroke (Color.BLACK);

    switch (walker.direction)
    {
      case NORTH:
        gc.strokeLine (left + 8, top + 16, left + 19, top + 5);
        gc.strokeLine (left + 19, top + 5, left + 30, top + 16);
        break;

      case SOUTH:
        gc.strokeLine (left + 8, top + 22, left + 19, top + 33);
        gc.strokeLine (left + 19, top + 33, left + 30, top + 22);
        break;

      case EAST:
        gc.strokeLine (left + 21, top + 8, left + 32, top + 19);
        gc.strokeLine (left + 32, top + 19, left + 21, top + 30);
        break;

      case WEST:
        gc.strokeLine (left + 17, top + 8, left + 6, top + 19);
        gc.strokeLine (left + 6, top + 19, left + 17, top + 30);
        break;
    }
  }

  // ---------------------------------------------------------------------------------//
  public void draw (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    int top = (19 - location.getRow ()) * CELL_SIZE + INSET;
    int bottom = top + CELL_SIZE - 2;
    int left = location.getColumn () * CELL_SIZE + INSET;
    int right = left + CELL_SIZE - 2;

    int textLeft = left + 12;
    int textBase = top + 25;

    if (lair)
      gc.setFill (Color.GAINSBORO);
    else
      gc.setFill (Color.LIGHTGRAY);

    gc.fillRect (left - 1, top - 1, CELL_SIZE, CELL_SIZE);

    if (extra != null)
    {
      gc.setFill (Color.GREEN);
      switch (extra.getSquare ())
      {
        case NORMAL:
          gc.fillText ("N", textLeft, textBase);
          break;

        case DARK:
          gc.setFill (Color.DARKGRAY);
          gc.fillRect (left - 1, top - 1, CELL_SIZE, CELL_SIZE);
          break;

        case TRANSFER:
          gc.fillOval (left + 12, top + 12, 14, 14);
          break;

        case CHUTE:
          gc.fillText ("C", textLeft, textBase);
          break;

        case STAIRS:
          gc.fillText ("S", textLeft, textBase);
          break;

        case ROCKWATE:
          gc.setFill (Color.BEIGE);
          gc.fillRect (left - 1, top - 1, CELL_SIZE, CELL_SIZE);
          break;

        case SPINNER:
          gc.fillText ("X", textLeft, textBase);
          break;

        case BUTTONZ:
          gc.fillText ("B", textLeft, textBase);
          break;

        case ENCOUNTE:
          gc.fillText ("E", textLeft, textBase);
          break;

        case FIZZLE:
          gc.fillText ("F", textLeft, textBase);
          break;

        case OUCHY:
          gc.fillText ("O", textLeft, textBase);
          break;

        case PIT:
          gc.fillText ("P", textLeft, textBase);
          break;

        case SCNMSG:
          gc.fillText ("M", textLeft, textBase);
          break;
      }
    }

    gc.setStroke (Color.BLACK);
    if (walls.west == Wall.WALL)
      gc.strokeLine (left, top, left, bottom);
    if (walls.north == Wall.WALL)
      gc.strokeLine (left, top, right, top);
    if (walls.east == Wall.WALL)
      gc.strokeLine (right, top, right, bottom);
    if (walls.south == Wall.WALL)
      gc.strokeLine (left, bottom, right, bottom);

    gc.setStroke (Color.BLUEVIOLET);
    if (walls.west == Wall.DOOR)
      gc.strokeLine (left, top, left, bottom);
    if (walls.north == Wall.DOOR)
      gc.strokeLine (left, top, right, top);
    if (walls.east == Wall.DOOR)
      gc.strokeLine (right, top, right, bottom);
    if (walls.south == Wall.DOOR)
      gc.strokeLine (left, bottom, right, bottom);

    gc.setStroke (Color.BLUE);
    if (walls.west == Wall.HIDEDOOR)
      gc.strokeLine (left, top, left, bottom);
    if (walls.north == Wall.HIDEDOOR)
      gc.strokeLine (left, top, right, top);
    if (walls.east == Wall.HIDEDOOR)
      gc.strokeLine (right, top, right, bottom);
    if (walls.south == Wall.HIDEDOOR)
      gc.strokeLine (left, bottom, right, bottom);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    if (extra == null)
      return String.format ("%s %s %s", location, walls, lair);

    return String.format ("%s %s %s %s", location, walls, lair, extra);
  }
}
