package com.bytezone.wizardry.origin.gui;

import static com.bytezone.wizardry.origin.Walls.Wall.OPEN;

import com.bytezone.wizardry.origin.MazeCell;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
public class ViewPane extends Canvas implements WalkerListener
// -----------------------------------------------------------------------------------//
{
  private static final int[] corners = { 0, 30, 90, 130, 155, 170, 180, 187 };
  private static final int PANE_SIZE = 400;
  private static final int MIDDLE = 2;
  private static final int LEFT_1 = 1;
  private static final int LEFT_2 = 0;
  private static final int RIGHT_1 = 3;
  private static final int RIGHT_2 = 4;

  WizardryOrigin wizardry;

  // ---------------------------------------------------------------------------------//
  public ViewPane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    super (400, 400);

    this.wizardry = wizardry;

    GraphicsContext gc = getGraphicsContext2D ();
    gc.setFill (Color.BLACK);
    gc.setFont (Font.font (20));
  }

  // ---------------------------------------------------------------------------------//
  public void drawView (Walker walker)
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = getGraphicsContext2D ();

    gc.setFill (Color.LIGHTGRAY);
    gc.fillRect (0, 0, getWidth (), getHeight ());

    int leftObscured = -1;
    int rightObscured = -1;

    for (int distance = 0; distance < corners.length - 1; distance++)
    {
      MazeCell[] cells = walker.getCells (distance);

      if (walker.getLeftWall (cells[MIDDLE]) == OPEN)
      {
        if (walker.getCentreWall (cells[LEFT_1]) != OPEN)
        {
          drawFace (gc, distance, -1, leftObscured);
          leftObscured = distance;
        }
      }
      else
      {
        drawLeft (gc, distance);
        leftObscured = distance;
      }

      if (walker.getRightWall (cells[MIDDLE]) == OPEN)
      {
        if (walker.getCentreWall (cells[RIGHT_1]) != OPEN)
        {
          drawFace (gc, distance, 1, rightObscured);
          rightObscured = distance;
        }
      }
      else
      {
        drawRight (gc, distance);
        rightObscured = distance;
      }

      if (walker.getCentreWall (cells[MIDDLE]) != OPEN)
      {
        drawFace (gc, distance, 0, -1);
        break;
      }
    }
  }

  // ---------------------------------------------------------------------------------//
  private void drawFace (GraphicsContext gc, int distance, int offset, int obscured)
  // ---------------------------------------------------------------------------------//
  {
    int nearPoint = corners[distance];
    int farPoint = corners[distance + 1];
    int height = PANE_SIZE - 2 * farPoint;
    int width = height;

    int x = farPoint + offset * width;        // move face left or right if offset != 0
    int y = farPoint;

    if (offset != 0)
    {
      int gap = distance - obscured;
      switch (gap)
      {
        case 1:
          width = farPoint - nearPoint;
          if (offset < 0)
            x = (farPoint - width);
          break;

        case 2:
          width = farPoint - corners[distance - 1];
          if (offset < 0)
            x = (farPoint - width);
          break;
      }
    }

    gc.strokeRect (x, y, width, height);
  }

  // ---------------------------------------------------------------------------------//
  private void drawLeft (GraphicsContext gc, int distance)
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
    if (distance > 0)
      gc.strokeLine (nearPoint, nearPoint, nearPoint, nearX);
  }

  // ---------------------------------------------------------------------------------//
  private void drawRight (GraphicsContext gc, int distance)
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
    if (distance > 0)
      gc.strokeLine (nearX, nearPoint, nearX, nearX);
  }

  // ---------------------------------------------------------------------------------//
  private void drawView2 (Walker walker)
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = getGraphicsContext2D ();

    gc.setFill (Color.LIGHTGRAY);
    gc.fillRect (0, 0, getWidth (), getHeight ());

    for (int distance = corners.length - 2; distance >= 0; distance--)
    {
      MazeCell[] cells = walker.getCells (distance);

      if (walker.getCentreWall (cells[LEFT_1]) != OPEN)
        drawFace (gc, distance, -1);
      if (walker.getCentreWall (cells[MIDDLE]) != OPEN)
        drawFace (gc, distance, 0);
      if (walker.getCentreWall (cells[RIGHT_1]) != OPEN)
        drawFace (gc, distance, 1);

      //      if (walker.getLeftWall (cells[LEFT_1]) != OPEN)
      //        drawLeft (gc, distance, -1);
      if (walker.getLeftWall (cells[MIDDLE]) != OPEN)
        drawLeft (gc, distance, 0);

      //      if (walker.getRightWall (cells[RIGHT_1]) != OPEN)
      //        drawRight (gc, distance, 1);
      if (walker.getRightWall (cells[MIDDLE]) != OPEN)
        drawRight (gc, distance, 0);
    }
  }

  // ---------------------------------------------------------------------------------//
  private void drawFace (GraphicsContext gc, int distance, int offset)
  // ---------------------------------------------------------------------------------//
  {
    int x = corners[distance + 1];
    int y = x;
    int height = PANE_SIZE - 2 * x;
    int width = height;

    x = x + offset * width;

    gc.setFill (Color.LIGHTGRAY);
    gc.fillRect (x, y, width, height);
    gc.strokeRect (x, y, width, height);
  }

  // ---------------------------------------------------------------------------------//
  private void drawLeft (GraphicsContext gc, int distance, int offset)
  // ---------------------------------------------------------------------------------//
  {
    double x = corners[distance];
    double y = corners[distance];

    double x1 = corners[distance + 1];
    double y1 = corners[distance + 1];

    double x2 = x1;
    double y2 = getHeight () - y1;

    double x3 = x;
    double y3 = getHeight () - y;

    //    double o = offset * getWidth () - corners[distance + 1] * 2;

    //    double[] xx = new double[] { x + o, x1 + o, x2 + o, x3 + o };
    double[] xx = new double[] { x, x1, x2, x3 };
    double[] yy = new double[] { y, y1, y2, y3 };

    gc.fillPolygon (xx, yy, 4);
    gc.strokePolygon (xx, yy, 4);
  }

  // ---------------------------------------------------------------------------------//
  private void drawRight (GraphicsContext gc, int distance, int offset)
  // ---------------------------------------------------------------------------------//
  {
    double x = getWidth () - corners[distance];
    double y = getWidth () - corners[distance];

    double x1 = getWidth () - corners[distance + 1];
    double y1 = getWidth () - corners[distance + 1];

    double x2 = x1;
    double y2 = getHeight () - y1;

    double x3 = x;
    double y3 = getHeight () - y;

    double[] xx = new double[] { x, x1, x2, x3 };
    double[] yy = new double[] { y, y1, y2, y3 };

    gc.fillPolygon (xx, yy, 4);
    gc.strokePolygon (xx, yy, 4);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void walkerMoved (Walker walker)
  // ---------------------------------------------------------------------------------//
  {
    drawView2 (walker);
  }
}
