package com.bytezone.wizardry;

import com.bytezone.wizardry.graphics.CellGraphic;
import com.bytezone.wizardry.origin.MazeLevel;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
public class MazePane extends Canvas
// -----------------------------------------------------------------------------------//
{
  private WizardryData wizardry;
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
    this.wizardry = wizardry;
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
