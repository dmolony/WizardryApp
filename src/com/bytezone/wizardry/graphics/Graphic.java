package com.bytezone.wizardry.graphics;

import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class Graphic
// -----------------------------------------------------------------------------------//
{
  protected PixelWriter pixelWriter;
  private Color color = Color.PALEGREEN;

  // ---------------------------------------------------------------------------------//
  public void setColor (Color color)
  // ---------------------------------------------------------------------------------//
  {
    this.color = color;
  }

  // ---------------------------------------------------------------------------------//
  protected void writePixel2 (int x, int y)
  // ---------------------------------------------------------------------------------//
  {
    pixelWriter.setColor (x, y, color);
    pixelWriter.setColor (x + 1, y, color);

    pixelWriter.setColor (x, y + 1, color);
    pixelWriter.setColor (x + 1, y + 1, color);
  }

  // ---------------------------------------------------------------------------------//
  protected void writePixel3 (int x, int y)
  // ---------------------------------------------------------------------------------//
  {
    writePixel2 (x, y);

    pixelWriter.setColor (x + 2, y, color);
    pixelWriter.setColor (x + 2, y + 1, color);

    pixelWriter.setColor (x, y + 2, color);
    pixelWriter.setColor (x + 1, y + 2, color);
    pixelWriter.setColor (x + 2, y + 2, color);
  }

  // ---------------------------------------------------------------------------------//
  protected void writePixel4 (int x, int y)
  // ---------------------------------------------------------------------------------//
  {
    writePixel3 (x, y);

    pixelWriter.setColor (x + 3, y, color);
    pixelWriter.setColor (x + 3, y + 1, color);
    pixelWriter.setColor (x + 3, y + 2, color);

    pixelWriter.setColor (x, y + 3, color);
    pixelWriter.setColor (x + 1, y + 3, color);
    pixelWriter.setColor (x + 2, y + 3, color);
    pixelWriter.setColor (x + 3, y + 3, color);
  }
}
