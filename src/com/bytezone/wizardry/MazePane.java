package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Location;
import com.bytezone.wizardry.data.MazeCell;
import com.bytezone.wizardry.data.MazeLevel;
import com.bytezone.wizardry.data.WizardryData;
import com.bytezone.wizardry.graphics.CellGraphic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
public class MazePane extends Canvas implements MovementListener
// -----------------------------------------------------------------------------------//
{
  //  private WizardryData wizardry;

  int currentLevel = -1;
  int currentRow;
  int currentColumn;

  private CellGraphic cellGraphic = new CellGraphic (getGraphicsContext2D ());

  // ---------------------------------------------------------------------------------//
  public MazePane ()
  // ---------------------------------------------------------------------------------//
  {
    super (808, 808);

    GraphicsContext gc = getGraphicsContext2D ();
    gc.setFill (Color.BLACK);
    gc.setFont (Font.font (18));
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    //    this.wizardry = wizardry;

    if (wizardry.getMazeLevels ().size () == 0)
    {
      GraphicsContext gc = getGraphicsContext2D ();
      gc.setFill (Color.LIGHTGRAY);
      gc.fillRect (0, 0, getWidth (), getHeight ());
    }
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
      cellGraphic.draw (cell);
    }
    else
    {
      currentLevel = walker.location.getLevel ();
      gc.setFill (Color.LIGHTGRAY);
      gc.fillRect (0, 0, getWidth (), getHeight ());

      update (walker.mazeLevel);
    }

    //    MazeCell cell = walker.mazeLevel.getMazeCell (walker.location);

    //    cellGraphic.drawWalker (cell, walker.direction, walker.location);
    cellGraphic.drawWalker (walker);

    currentRow = walker.location.getRow ();
    currentColumn = walker.location.getColumn ();
  }

  // ---------------------------------------------------------------------------------//
  void update (MazeLevel mazeLevel)
  // ---------------------------------------------------------------------------------//
  {
    for (int col = 0; col < 20; col++)
      for (int row = 0; row < 20; row++)
        cellGraphic.draw (mazeLevel.getMazeCell (col, row));
  }
}
