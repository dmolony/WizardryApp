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

    int leftObscured = 0;
    int rightObscured = 0;

    for (int distance = 0; distance < corners.length - 1; distance++)
    {
      MazeCell[] cells = walker.getCells (distance);

      if (walker.getLeftWall (cells[MIDDLE]) == OPEN)
      {
        if (walker.getCentreWall (cells[LEFT_1]) != OPEN)
        {
          drawFace (gc, distance, -1, leftObscured);
          leftObscured = 1;
        }
        else
          leftObscured = 0;
      }
      else
      {
        drawLeft (gc, distance);
        leftObscured = 1;
      }

      if (walker.getRightWall (cells[MIDDLE]) == OPEN)
      {
        if (walker.getCentreWall (cells[RIGHT_1]) != OPEN)
        {
          drawFace (gc, distance, 1, rightObscured);
          rightObscured = 1;
        }
        else
          rightObscured = 0;
      }
      else
      {
        drawRight (gc, distance);
        rightObscured = 1;
      }

      if (walker.getCentreWall (cells[MIDDLE]) != OPEN)
      {
        drawFace (gc, distance, 0, 0);
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
    int width = PANE_SIZE - 2 * farPoint;

    int x = farPoint + offset * width;
    int y = farPoint;

    if (obscured > 0)
    {
      assert offset != 0;                 // cannot obscure centre pane
      width = farPoint - nearPoint;
      if (offset < 0)
        x = (farPoint - width);
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
  @Override
  public void walkerMoved (Walker walker)
  // ---------------------------------------------------------------------------------//
  {
    drawView (walker);
  }
}
