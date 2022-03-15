package com.bytezone.wizardry.origin.gui;

import com.bytezone.wizardry.origin.Location;
import com.bytezone.wizardry.origin.Maze.Direction;
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
  int currentLevel;

  private Walker[] walker;

  // ---------------------------------------------------------------------------------//
  public MazePane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    super (808, 808);

    this.wizardry = wizardry;
    int levels = wizardry.maze.mazeLevels.size ();
    walker = new Walker[levels];
    for (int i = 0; i < levels; i++)
      walker[i] = new Walker (wizardry.maze.mazeLevels.get (i), Direction.NORTH,
          new Location (i + 1, 0, 0));

    GraphicsContext gc = getGraphicsContext2D ();
    gc.setFill (Color.BLACK);
    gc.setFont (Font.font (18));

    setCurrentLevel (0);
  }

  // ---------------------------------------------------------------------------------//
  public void setCurrentLevel (int level)
  // ---------------------------------------------------------------------------------//
  {
    currentLevel = level;
    GraphicsContext gc = getGraphicsContext2D ();
    gc.setFill (Color.LIGHTGRAY);
    gc.fillRect (0, 0, getWidth (), getHeight ());
    wizardry.maze.mazeLevels.get (level).draw (gc);
  }
}
