package com.bytezone.wizardry;

import static com.bytezone.wizardry.data.Walls.Wall.DOOR;
import static com.bytezone.wizardry.data.Walls.Wall.HIDEDOOR;
import static com.bytezone.wizardry.data.Walls.Wall.OPEN;

import com.bytezone.wizardry.data.MazeCell;
import com.bytezone.wizardry.data.Walls.Wall;
import com.bytezone.wizardry.data.WizardryData;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
public class ViewPane extends Canvas implements MovementListener
// -----------------------------------------------------------------------------------//
{
  private static final int[] corners = { 0, 35, 90, 130, 155, 170, 180, 187 };
  private static final int[] doors = { 25, 20, 15, 10, 8, 6, 4, 2 };
  private static final int[] vGaps = { 20, 18, 16, 12, 10, 6, 5, 4 };
  private static final int PANE_SIZE = 400;
  private static final int MIDDLE = 2;
  private static final int LEFT_1 = 1;
  private static final int LEFT_2 = 0;
  private static final int RIGHT_1 = 3;
  private static final int RIGHT_2 = 4;

  WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public ViewPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (400, 400);

    GraphicsContext gc = getGraphicsContext2D ();
    gc.setFill (Color.LIGHTGRAY);
    gc.setFont (Font.font (20));
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void walkerMoved (Walker walker)
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = getGraphicsContext2D ();
    gc.fillRect (0, 0, getWidth (), getHeight ());
    gc.setStroke (Color.BLACK);

    for (int distance = corners.length - 2; distance >= 0; distance--)
    {
      MazeCell[] cells = walker.getCells (distance);

      Wall wallLeft = walker.getCentreWall (cells[LEFT_1]);
      if (wallLeft != OPEN)
        drawCentre (gc, distance, -1, wallLeft);

      Wall wallCentre = walker.getCentreWall (cells[MIDDLE]);
      if (wallCentre != OPEN)
        drawCentre (gc, distance, 0, wallCentre);

      Wall wallRight = walker.getCentreWall (cells[RIGHT_1]);
      if (wallRight != OPEN)
        drawCentre (gc, distance, 1, wallRight);

      //      if (walker.getLeftWall (cells[LEFT_1]) != OPEN)
      //        drawLeft (gc, distance, -1);

      wallLeft = walker.getLeftWall (cells[MIDDLE]);
      if (wallLeft != OPEN)
        drawLeft (gc, distance, 0, wallLeft);

      //      if (walker.getRightWall (cells[RIGHT_1]) != OPEN)
      //        drawRight (gc, distance, 1);

      wallRight = walker.getRightWall (cells[MIDDLE]);
      if (wallRight != OPEN)
        drawRight (gc, distance, 0, wallRight);
    }
  }

  // ---------------------------------------------------------------------------------//
  private void drawCentre (GraphicsContext gc, int distance, int offset, Wall wall)
  // ---------------------------------------------------------------------------------//
  {
    int x = corners[distance + 1];
    int y = x;
    int height = PANE_SIZE - 2 * x;
    int width = height;

    x += offset * width;                      // move square left or right if offset

    gc.fillRect (x, y, width, height);
    gc.setStroke (Color.BLACK);
    gc.strokeRect (x, y, width, height);

    if (wall == DOOR || wall == HIDEDOOR)
    {
      x += doors[distance];
      y += doors[distance];
      height -= doors[distance];
      width -= doors[distance] * 2;
      gc.setStroke (wall == DOOR ? Color.BLACK : Color.BLUE);
      gc.strokeRect (x, y, width, height);
    }
  }

  // ---------------------------------------------------------------------------------//
  private void drawLeft (GraphicsContext gc, int distance, int offset, Wall wall)
  // ---------------------------------------------------------------------------------//
  {
    double x = corners[distance];           // top left
    double y = x;

    double x1 = corners[distance + 1];      // top right
    double y1 = x1;

    double x2 = x1;
    double y2 = getHeight () - y1;          // bottom right

    double x3 = x;
    double y3 = getHeight () - y;           // bottom left

    double[] xx = new double[] { x, x1, x2, x3 };
    double[] yy = new double[] { y, y1, y2, y3 };

    gc.fillPolygon (xx, yy, xx.length);
    gc.setStroke (Color.BLACK);
    gc.strokePolygon (xx, yy, xx.length);

    if (wall == DOOR || wall == HIDEDOOR)
    {
      double hGap = Math.abs (x - x1) / 5;
      double vGap = vGaps[distance];

      if (distance == 0)      // no gap
      {
        x = 0;
        y = vGap;
      }
      else
      {
        x += hGap;
        y += hGap + vGap;
      }

      x1 -= hGap;
      y1 = y1 - hGap + vGap;

      x2 = x1;
      y2 = getHeight () - x2;

      x3 = x;
      y3 = getHeight () - x;

      xx = new double[] { x, x1, x2, x3 };
      yy = new double[] { y, y1, y2, y3 };

      gc.setStroke (wall == DOOR ? Color.BLACK : Color.BLUE);
      gc.strokePolygon (xx, yy, xx.length);
    }
  }

  // ---------------------------------------------------------------------------------//
  private void drawRight (GraphicsContext gc, int distance, int offset, Wall wall)
  // ---------------------------------------------------------------------------------//
  {
    double x = getWidth () - corners[distance];           // bottom right
    double y = x;

    double x1 = getWidth () - corners[distance + 1];      // bottom left
    double y1 = x1;

    double x2 = x1;                                       // top left
    double y2 = getHeight () - y1;

    double x3 = x;                                        // top right
    double y3 = getHeight () - y;

    double[] xx = new double[] { x, x1, x2, x3 };
    double[] yy = new double[] { y, y1, y2, y3 };

    gc.setStroke (Color.BLACK);
    gc.fillPolygon (xx, yy, xx.length);
    gc.strokePolygon (xx, yy, xx.length);

    if (wall == DOOR || wall == HIDEDOOR)
    {
      double hGap = Math.abs (x - x1) / 5;
      double vGap = vGaps[distance];

      if (distance == 0)
      {
        x = getWidth ();
        y = x;
      }
      else
      {
        x -= hGap;
        y -= hGap;
      }

      x1 += hGap;
      y1 = x1;

      x2 = x1;
      y2 = getHeight () - x2 + vGap;

      x3 = x;
      y3 = getHeight () - x + vGap;

      xx = new double[] { x, x1, x2, x3 };
      yy = new double[] { y, y1, y2, y3 };

      gc.setStroke (wall == DOOR ? Color.BLACK : Color.BLUE);
      gc.strokePolygon (xx, yy, xx.length);
    }
  }
}
