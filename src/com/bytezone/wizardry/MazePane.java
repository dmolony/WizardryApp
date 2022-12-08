package com.bytezone.wizardry;

import java.util.ArrayList;
import java.util.List;

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

  private List<Location> teleportLocations = new ArrayList<> ();
  private List<Location> lostCharacterLocations = new ArrayList<> ();

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

    teleportLocations = wizardry.getTeleportLocations ();
    lostCharacterLocations = wizardry.getLostCharacterLocations ();

    //    System.out.println ("Teleport");
    //    for (Location location : wizardry.getTeleportLocations ())
    //      System.out.println (location);
    //    System.out.println ("Lost");
    //    for (Location location : wizardry.getLostCharacterLocations ())
    //      System.out.println (location);
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
      updateCellSymbols (cell);
    }
    else
    {
      currentLevel = walker.location.getLevel ();
      gc.setFill (Color.LIGHTGRAY);
      gc.fillRect (0, 0, getWidth (), getHeight ());

      update (walker.mazeLevel);
    }

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
    int row = mazeCell.getLocation ().getRow ();
    int col = mazeCell.getLocation ().getColumn ();
    int level = mazeCell.getLocation ().getLevel ();

    for (Location location : lostCharacterLocations)
      if (location.getLevel () == level && location.getRow () == row
          && location.getColumn () == col)
        cellGraphic.drawLost (mazeCell);

    for (Location location : teleportLocations)
      if (location.getLevel () == level && location.getRow () == row
          && location.getColumn () == col)
        cellGraphic.drawTarget (mazeCell);
  }
}
