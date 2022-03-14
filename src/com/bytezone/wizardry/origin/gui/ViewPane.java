package com.bytezone.wizardry.origin.gui;

import com.bytezone.wizardry.origin.Maze;
import com.bytezone.wizardry.origin.MazeCell;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
public class ViewPane extends Canvas
// -----------------------------------------------------------------------------------//
{
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

    double width = getWidth ();
    double height = getHeight ();

    gc.setFill (Color.LIGHTGRAY);
    gc.fillRect (0, 0, width, height);

    wizardry.maze.mazeLevels.get (0).getMazeCell (0, 0).draw2d (gc, 0, Maze.Direction.NORTH);
  }

  // ---------------------------------------------------------------------------------//
  public void draw (MazeCell mazeCell)
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = getGraphicsContext2D ();
    mazeCell.draw (gc);
  }

}
