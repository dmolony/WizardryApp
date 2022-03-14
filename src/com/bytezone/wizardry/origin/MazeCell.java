package com.bytezone.wizardry.origin;

import com.bytezone.wizardry.origin.Maze.Wall;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class MazeCell
// -----------------------------------------------------------------------------------//
{
  private static final int SIZE = 40;
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
  public void draw (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    int top = (19 - location.row) * SIZE + 5;
    int bottom = top + SIZE - 2;
    int left = location.column * SIZE + 5;
    int right = left + SIZE - 2;

    if (extra != null)
    {
      switch (extra.square)
      {
        case NORMAL:
          break;

        case DARK:
          gc.setFill (Color.DARKGRAY);
          gc.fillRect (left - 1, top - 1, SIZE, SIZE);
          break;

        case TRANSFER:
          gc.setFill (Color.GREEN);
          gc.fillOval (left + 12, top + 12, 14, 14);
          break;

        case CHUTE:
          break;

        case STAIRS:
          break;

        case ROCKWATE:
          break;

        case SPINNER:
          break;

        case BUTTONZ:
          break;

        case ENCOUNTE:
          break;

        case FIZZLE:
          break;

        case OUCHY:
          break;

        case PIT:
          break;

        case SCNMSG:
          break;
      }
    }

    if (fight)
    {
      gc.setFill (Color.GAINSBORO);
      gc.fillRect (left - 1, top - 1, SIZE, SIZE);
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
  public void draw2d (GraphicsContext gc, int distance, Maze.Direction direction)
  // ---------------------------------------------------------------------------------//
  {
    Wall[] facingWalls = new Wall[5];

    switch (direction)
    {
      case NORTH:
        facingWalls[0] = walls.north;
        break;

      case SOUTH:
        facingWalls[0] = walls.south;
        break;

      case EAST:
        facingWalls[0] = walls.east;
        break;

      case WEST:
        facingWalls[0] = walls.west;
        break;
    }

    int[] corners = { 0, 30, 80, 120, 155, 175, 190 };
    int size = 400;

    gc.setStroke (Color.GREEN);

    for (int i = 1; i < corners.length; i++)
    {
      // left diagonal top
      gc.strokeLine (corners[i], corners[i], corners[i - 1], corners[i - 1]);

      // left diagonal bottom
      gc.strokeLine (corners[i], size - corners[i], corners[i - 1], size - corners[i - 1]);

      // left vertical far
      gc.strokeLine (corners[i], corners[i], corners[i], size - corners[i]);

      // left vertical near
      gc.strokeLine (corners[i - 1], corners[i - 1], corners[i - 1], size - corners[i - 1]);

      // right diagonal bottom
      gc.strokeLine (size - corners[i], size - corners[i], size - corners[i - 1],
          size - corners[i - 1]);

      // right diagonal top
      gc.strokeLine (size - corners[i], corners[i], size - corners[i - 1], corners[i - 1]);

      // right vertical far
      gc.strokeLine (size - corners[i], corners[i], size - corners[i], size - corners[i]);

      // right vertical near
      gc.strokeLine (size - corners[i - 1], corners[i - 1], size - corners[i - 1],
          size - corners[i - 1]);
    }
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
