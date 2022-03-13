package com.bytezone.wizardry.origin.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

// -----------------------------------------------------------------------------------//
public class ViewPane extends Canvas
// -----------------------------------------------------------------------------------//
{
  // ---------------------------------------------------------------------------------//
  public ViewPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (650, 500);

    GraphicsContext gc = getGraphicsContext2D ();
    gc.setFill (Color.BLACK);
    gc.setFont (Font.font (20));

    double width = getWidth ();
    double height = getHeight ();

    gc.setFill (Color.YELLOW);
    gc.fillRect (0, 0, width, height);
  }
}
