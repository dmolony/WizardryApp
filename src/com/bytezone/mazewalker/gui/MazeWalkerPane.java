package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Location;
import com.bytezone.wizardry.origin.MazeCell;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
public class MazeWalkerPane extends Canvas implements MovementListener
// -----------------------------------------------------------------------------------//
{
  WizardryOrigin wizardry;

  int currentLevel = -1;
  int currentRow;
  int currentColumn;

  // ---------------------------------------------------------------------------------//
  public MazeWalkerPane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    super (808, 808);

    this.wizardry = wizardry;

    GraphicsContext gc = getGraphicsContext2D ();
    gc.setFill (Color.BLACK);
    gc.setFont (Font.font (18));
  }

  // ---------------------------------------------------------------------------------//
  public Location getLocation (double x, double y)
  // ---------------------------------------------------------------------------------//
  {
    int row = (int) ((getHeight () - y - MazeCell.INSET) / MazeCell.CELL_SIZE);
    int column = (int) ((x - MazeCell.INSET) / MazeCell.CELL_SIZE);

    return new Location (0, column, row);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void walkerMoved (Walker walker)
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = getGraphicsContext2D ();

    if (currentLevel == walker.location.getLevel ())
    {
      MazeCell cell =
          walker.mazeLevel.getMazeCell (new Location (currentLevel, currentColumn, currentRow));
      cell.draw (gc);
    }
    else
    {
      currentLevel = walker.location.getLevel ();
      gc.setFill (Color.LIGHTGRAY);
      gc.fillRect (0, 0, getWidth (), getHeight ());
      walker.mazeLevel.draw (gc);
    }

    MazeCell cell = walker.mazeLevel.getMazeCell (walker.location);
    cell.drawWalker (gc, walker);

    currentRow = walker.location.getRow ();
    currentColumn = walker.location.getColumn ();
  }
}
