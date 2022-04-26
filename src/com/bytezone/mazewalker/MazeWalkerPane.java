package com.bytezone.mazewalker;

import com.bytezone.wizardry.graphics.CellGraphic;
import com.bytezone.wizardry.origin.Location;
import com.bytezone.wizardry.origin.MazeCell;
import com.bytezone.wizardry.origin.MazeLevel;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
public class MazeWalkerPane extends Canvas implements MovementListener
// -----------------------------------------------------------------------------------//
{
  WizardryData wizardry;

  int currentLevel = -1;
  int currentRow;
  int currentColumn;

  private CellGraphic cellGraphic = new CellGraphic (getGraphicsContext2D ());

  // ---------------------------------------------------------------------------------//
  public MazeWalkerPane (WizardryData wizardry)
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
      //      cell.draw (gc);
      cellGraphic.draw (cell);
    }
    else
    {
      currentLevel = walker.location.getLevel ();
      gc.setFill (Color.LIGHTGRAY);
      gc.fillRect (0, 0, getWidth (), getHeight ());
      //      walker.mazeLevel.draw (gc);
      drawMazeLevel (walker.mazeLevel);
    }

    MazeCell cell = walker.mazeLevel.getMazeCell (walker.location);
    //    cell.drawWalker (gc, walker.location, walker.direction);
    cellGraphic.drawWalker (cell, walker);

    currentRow = walker.location.getRow ();
    currentColumn = walker.location.getColumn ();
  }

  // ---------------------------------------------------------------------------------//
  private void drawMazeLevel (MazeLevel mazeLevel)
  // ---------------------------------------------------------------------------------//
  {
    for (int col = 0; col < 20; col++)
      for (int row = 0; row < 20; row++)
        cellGraphic.draw (mazeLevel.getMazeCell (col, row));
  }
}