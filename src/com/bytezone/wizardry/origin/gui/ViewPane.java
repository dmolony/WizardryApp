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
  private static final int[] corners = { 0, 25, 80, 120, 155, 175, 190 };
  private static final int PANE_SIZE = 400;

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

    if (false)
    {
      old ();
      return;
    }

    boolean leftObscured = false;
    boolean rightObscured = false;

    for (int distance = 0; distance < 5; distance++)
    {
      MazeCell[] cells = walker.getCells (distance);
      //      System.out.printf ("%s%n%s%n%s%n%n", cells[0], cells[1], cells[2]);
      if (walker.getLeftWall (cells[1]) == OPEN)
      {
        if (walker.getCentreWall (cells[0]) != OPEN)
          drawFace (gc, distance, -1, leftObscured);
        leftObscured = false;
      }
      else
      {
        drawLeft (gc, distance);
        leftObscured = true;
      }

      if (walker.getRightWall (cells[1]) == OPEN)
      {
        if (walker.getCentreWall (cells[2]) != OPEN)
          drawFace (gc, distance, 1, rightObscured);
        rightObscured = false;
      }
      else
      {
        drawRight (gc, distance);
        rightObscured = true;
      }

      if (walker.getCentreWall (cells[1]) != OPEN)
      {
        drawFace (gc, distance, 0, false);
        break;
      }
    }
  }

  private void old ()
  {
    GraphicsContext gc = getGraphicsContext2D ();
    gc.setStroke (Color.GREEN);

    int distance = 5;

    drawFace (gc, distance, 0, false);
    drawLeft (gc, distance);
    drawRight (gc, distance);

    --distance;
    drawLeft (gc, distance);
    drawFace (gc, distance, 1, true);

    --distance;
    drawFace (gc, distance, -1, true);
    drawRight (gc, distance);

    --distance;
    drawLeft (gc, distance);
    drawFace (gc, distance, 1, true);

    --distance;
    drawLeft (gc, distance);
    drawRight (gc, distance);

    --distance;
    drawRight (gc, distance);
    drawFace (gc, distance, -1, false);
  }

  // ---------------------------------------------------------------------------------//
  private void drawFace (GraphicsContext gc, int distance, int offset, boolean obscured)
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
