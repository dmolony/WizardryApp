package com.bytezone.wizardry.graphics;

import static com.bytezone.wizardry.data.Walls.EAST;
import static com.bytezone.wizardry.data.Walls.NORTH;
import static com.bytezone.wizardry.data.Walls.SOUTH;
import static com.bytezone.wizardry.data.Walls.WEST;
import static com.bytezone.wizardry.data.Walls.Wall.DOOR;
import static com.bytezone.wizardry.data.Walls.Wall.HIDEDOOR;
import static com.bytezone.wizardry.data.Walls.Wall.WALL;

import com.bytezone.wizardry.Walker;
import com.bytezone.wizardry.data.Location;
import com.bytezone.wizardry.data.MazeCell;
import com.bytezone.wizardry.data.Special;
import com.bytezone.wizardry.data.Walls;
import com.bytezone.wizardry.data.WizardryData.Direction;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class CellGraphic
// -----------------------------------------------------------------------------------//
{
  public static final int CELL_SIZE = 40;
  public static final int INSET = 5;

  GraphicsContext gc;

  // ---------------------------------------------------------------------------------//
  public CellGraphic (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    this.gc = gc;
  }

  // ---------------------------------------------------------------------------------//
  public void draw (MazeCell mazeCell)
  // ---------------------------------------------------------------------------------//
  {
    Location location = mazeCell.getLocation ();
    Special special = mazeCell.getSpecial ();
    Walls walls = mazeCell.getWalls ();

    int top = (19 - location.getRow ()) * CELL_SIZE + INSET;
    int bottom = top + CELL_SIZE - 2;
    int left = location.getColumn () * CELL_SIZE + INSET;
    int right = left + CELL_SIZE - 2;

    int textLeft = left + 12;
    int textBase = top + 25;

    gc.setFill (mazeCell.isLair () ? Color.DARKGREY : Color.LIGHTGREY);
    gc.fillRect (left - 1, top - 1, CELL_SIZE, CELL_SIZE);

    if (special != null)
    {
      gc.setFill (Color.GREEN);
      switch (special.getSquare ())
      {
        case NORMAL:
          gc.fillText ("N", textLeft, textBase);
          break;

        case DARK:
          gc.setFill (Color.GRAY);
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
    if (walls.wall (WEST) == WALL)
      gc.strokeLine (left, top, left, bottom);
    if (walls.wall (NORTH) == WALL)
      gc.strokeLine (left, top, right, top);
    if (walls.wall (EAST) == WALL)
      gc.strokeLine (right, top, right, bottom);
    if (walls.wall (SOUTH) == WALL)
      gc.strokeLine (left, bottom, right, bottom);

    //    gc.setStroke (Color.BLUEVIOLET);
    gc.setStroke (Color.YELLOW);
    if (walls.wall (WEST) == DOOR)
      gc.strokeLine (left, top, left, bottom);
    if (walls.wall (NORTH) == DOOR)
      gc.strokeLine (left, top, right, top);
    if (walls.wall (EAST) == DOOR)
      gc.strokeLine (right, top, right, bottom);
    if (walls.wall (SOUTH) == DOOR)
      gc.strokeLine (left, bottom, right, bottom);

    //    gc.setStroke (Color.BLUE);
    gc.setStroke (Color.RED);
    if (walls.wall (WEST) == HIDEDOOR)
      gc.strokeLine (left, top, left, bottom);
    if (walls.wall (NORTH) == HIDEDOOR)
      gc.strokeLine (left, top, right, top);
    if (walls.wall (EAST) == HIDEDOOR)
      gc.strokeLine (right, top, right, bottom);
    if (walls.wall (SOUTH) == HIDEDOOR)
      gc.strokeLine (left, bottom, right, bottom);
  }

  // ---------------------------------------------------------------------------------//
  public void drawWalker (Walker walker)
  // ---------------------------------------------------------------------------------//
  {
    MazeCell cell = walker.getCurrentMazeCell ();
    Location location = cell.getLocation ();
    Direction direction = walker.direction;

    if (!cell.getLocation ().matches (location))
    {
      System.out.println ("wrong location");
      return;
    }

    int top = (19 - location.getRow ()) * CELL_SIZE + INSET;
    int left = location.getColumn () * CELL_SIZE + INSET;

    gc.setFill (Color.RED);
    gc.fillOval (left + 12, top + 12, 14, 14);

    gc.setStroke (Color.BLACK);

    switch (direction)
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
}
