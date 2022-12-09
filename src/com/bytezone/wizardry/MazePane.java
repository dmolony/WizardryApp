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
  int currentLevel = -1;
  int currentRow;
  int currentColumn;

  private CellGraphic cellGraphic = new CellGraphic (getGraphicsContext2D ());

  private WizardryData wizardry;

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
    if (wizardry.getMazeLevels ().size () == 0)
    {
      GraphicsContext gc = getGraphicsContext2D ();
      gc.setFill (Color.LIGHTGRAY);
      gc.fillRect (0, 0, getWidth (), getHeight ());
    }

    this.wizardry = wizardry;
  }

  // ---------------------------------------------------------------------------------//
  public Location getLocation (double x, double y)
  // ---------------------------------------------------------------------------------//
  {
    int row = (int) ((getHeight () - y - MazeCell.INSET) / MazeCell.CELL_SIZE);
    int column = (int) ((x - MazeCell.INSET) / MazeCell.CELL_SIZE);

    return new Location (currentLevel, column, row);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void walkerMoved (Walker walker)
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = getGraphicsContext2D ();

    if (currentLevel == walker.location.getLevel ())      // redraw old location
    {
      MazeCell cell =
          walker.mazeLevel.getMazeCell (new Location (currentLevel, currentColumn, currentRow));
      cellGraphic.draw (cell);
      updateCellSymbols (cell);
    }
    else                                                  // draw the whole maze
    {
      currentLevel = walker.location.getLevel ();
      gc.setFill (Color.LIGHTGRAY);
      gc.fillRect (0, 0, getWidth (), getHeight ());

      update (walker.mazeLevel);
    }

    cellGraphic.drawWalker (walker);                      // draw new location

    currentRow = walker.location.getRow ();
    currentColumn = walker.location.getColumn ();
  }

  // ---------------------------------------------------------------------------------//
  void update (MazeLevel mazeLevel)
  // ---------------------------------------------------------------------------------//
  {
    for (int col = 0; col < 20; col++)
      for (int row = 0; row < 20; row++)
      {
        MazeCell mazeCell = mazeLevel.getMazeCell (col, row);
        cellGraphic.draw (mazeCell);
        updateCellSymbols (mazeCell);
      }
  }

  // ---------------------------------------------------------------------------------//
  private void updateCellSymbols (MazeCell mazeCell)
  // ---------------------------------------------------------------------------------//
  {
    if (wizardry.hasLostCharacter (mazeCell.getLocation ()))
      cellGraphic.drawLost (mazeCell);

    if (wizardry.isTeleportTarget (mazeCell.getLocation ()))
      cellGraphic.drawTarget (mazeCell);
  }
}
