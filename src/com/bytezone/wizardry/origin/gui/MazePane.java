package com.bytezone.wizardry.origin.gui;

import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
public class MazePane extends Canvas
// -----------------------------------------------------------------------------------//
{
  WizardryOrigin wizardry;

  // ---------------------------------------------------------------------------------//
  public MazePane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    super (808, 808);

    this.wizardry = wizardry;

    GraphicsContext gc = getGraphicsContext2D ();
    gc.setFill (Color.BLACK);
    gc.setFont (Font.font (20));

    double width = getWidth ();
    double height = getHeight ();

    gc.setFill (Color.LIGHTGRAY);
    gc.fillRect (0, 0, width, height);

    wizardry.maze.mazeLevels.get (0).draw (gc);
  }
}
