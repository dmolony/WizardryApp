package com.bytezone.wizardry.origin;

import com.bytezone.wizardry.origin.Walls.Wall;
import com.bytezone.wizardry.origin.gui.Walker;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class MazeCell
// -----------------------------------------------------------------------------------//
{
  private static final int CELL_SIZE = 40;

  Location location;
  Walls walls;
  Extra extra;
  boolean fight;

  // ---------------------------------------------------------------------------------//
  public MazeCell (Location location, Walls box, boolean fight)
  // ---------------------------------------------------------------------------------//
  {
    this.location = location;
    this.fight = fight;
    this.walls = box;
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
  public void drawWalker (GraphicsContext gc, Walker walker)
  // ---------------------------------------------------------------------------------//
  {
    if (!location.matches (walker.location))
    {
      System.out.println ("wrong location");
      return;
    }

    int top = (19 - location.row) * CELL_SIZE + 5;
    int left = location.column * CELL_SIZE + 5;

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
    int top = (19 - location.row) * CELL_SIZE + 5;
    int bottom = top + CELL_SIZE - 2;
    int left = location.column * CELL_SIZE + 5;
    int right = left + CELL_SIZE - 2;

    if (fight)
      gc.setFill (Color.GAINSBORO);
    else
      gc.setFill (Color.LIGHTGRAY);

    gc.fillRect (left - 1, top - 1, CELL_SIZE, CELL_SIZE);

    if (extra != null)
    {
      switch (extra.square)
      {
        case NORMAL:
          break;

        case DARK:
          gc.setFill (Color.DARKGRAY);
          gc.fillRect (left - 1, top - 1, CELL_SIZE, CELL_SIZE);
          break;

        case TRANSFER:
          gc.setFill (Color.GREEN);
          gc.fillOval (left + 12, top + 12, 14, 14);
          break;

        case CHUTE:
          gc.setFill (Color.GREEN);
          gc.fillText ("C", left + 10, top + 25);
          break;

        case STAIRS:
          gc.setFill (Color.GREEN);
          gc.fillText ("S", left + 10, top + 25);
          break;

        case ROCKWATE:
          gc.setFill (Color.BEIGE);
          gc.fillRect (left - 1, top - 1, CELL_SIZE, CELL_SIZE);
          break;

        case SPINNER:
          gc.setFill (Color.GREEN);
          gc.fillText ("X", left + 10, top + 25);
          break;

        case BUTTONZ:
          gc.setFill (Color.GREEN);
          gc.fillText ("B", left + 10, top + 25);
          break;

        case ENCOUNTE:
          gc.setFill (Color.GREEN);
          gc.fillText ("E", left + 10, top + 25);
          break;

        case FIZZLE:
          gc.setFill (Color.GREEN);
          gc.fillText ("F", left + 10, top + 25);
          break;

        case OUCHY:
          gc.setFill (Color.GREEN);
          gc.fillText ("O", left + 10, top + 25);
          break;

        case PIT:
          gc.setFill (Color.GREEN);
          gc.fillText ("P", left + 10, top + 25);
          break;

        case SCNMSG:
          gc.setFill (Color.GREEN);
          gc.fillText ("M", left + 10, top + 25);
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
      return String.format ("%s %s %s", location, walls, fight);
    return String.format ("%s %s %s %s", location, walls, fight, extra);
  }
}
