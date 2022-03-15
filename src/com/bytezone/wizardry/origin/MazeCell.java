package com.bytezone.wizardry.origin;

import com.bytezone.wizardry.origin.Maze.Wall;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class MazeCell
// -----------------------------------------------------------------------------------//
{
  private static final int[] corners = { 0, 30, 80, 120, 155, 175, 190 };
  private static final int CELL_SIZE = 40;
  private static final int PANE_SIZE = 400;

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
    int top = (19 - location.row) * CELL_SIZE + 5;
    int bottom = top + CELL_SIZE - 2;
    int left = location.column * CELL_SIZE + 5;
    int right = left + CELL_SIZE - 2;

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
      gc.fillRect (left - 1, top - 1, CELL_SIZE, CELL_SIZE);
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

    gc.setStroke (Color.GREEN);

    distance = 4;
    drawLeftSide (gc, distance);
    //    drawRightSide (gc, distance);

    drawFront (gc, distance, 0, false);
    drawFront (gc, distance, 1, true);
    drawFront (gc, distance - 1, -1, true);
    //    drawFront (gc, distance - 1, 1, false);
    //    drawFront (gc, distance - 1, 2, false);

    drawLeftSide (gc, distance - 2);
    drawLeftSide (gc, distance - 3);
    drawRightSide (gc, distance - 1);
    drawRightSide (gc, distance - 3);
    drawRightSide (gc, distance - 4);

    drawFront (gc, distance - 4, -1, false);
    drawFront (gc, distance - 2, 1, true);
  }

  // ---------------------------------------------------------------------------------//
  private void drawFront (GraphicsContext gc, int distance, int offset, boolean obscured)
  // ---------------------------------------------------------------------------------//
  {
    int nearPoint = corners[distance];
    int farPoint = corners[distance + 1];
    int height = PANE_SIZE - 2 * farPoint;
    int width = PANE_SIZE - 2 * farPoint;

    int x = farPoint + offset * width;
    int y = farPoint;

    if (obscured)
    {
      assert offset != 0;                 // cannot obscure centre pane
      width = farPoint - nearPoint;
      if (offset < 0)
        x = (farPoint - width);
    }

    gc.strokeRect (x, y, width, height);
  }

  // ---------------------------------------------------------------------------------//
  private void drawLeftSide (GraphicsContext gc, int distance)
  // ---------------------------------------------------------------------------------//
  {
    int nearPoint = corners[distance];
    int farPoint = corners[distance + 1];
    int farX = PANE_SIZE - farPoint;
    int nearX = PANE_SIZE - nearPoint;

    // left diagonal top
    gc.strokeLine (farPoint, farPoint, nearPoint, nearPoint);

    // left diagonal bottom
    gc.strokeLine (farPoint, farX, nearPoint, nearX);

    // left vertical far
    gc.strokeLine (farPoint, farPoint, farPoint, farX);

    // left vertical near
    gc.strokeLine (nearPoint, nearPoint, nearPoint, nearX);
  }

  // ---------------------------------------------------------------------------------//
  private void drawRightSide (GraphicsContext gc, int distance)
  // ---------------------------------------------------------------------------------//
  {
    int nearPoint = corners[distance];
    int farPoint = corners[distance + 1];
    int farX = PANE_SIZE - farPoint;
    int nearX = PANE_SIZE - nearPoint;

    // right diagonal bottom
    gc.strokeLine (farX, farX, nearX, nearX);

    // right diagonal top
    gc.strokeLine (farX, farPoint, nearX, nearPoint);

    // right vertical far
    gc.strokeLine (farX, farPoint, farX, farX);

    // right vertical near
    gc.strokeLine (nearX, nearPoint, nearX, nearX);
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
