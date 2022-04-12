package com.bytezone.wizardry.origin;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class Image
// -----------------------------------------------------------------------------------//
{
  byte[] buffer;
  int offset;
  int scenarioId;

  // ---------------------------------------------------------------------------------//
  public Image (int id, DataBlock dataBlock, int scenarioId)
  // ---------------------------------------------------------------------------------//
  {
    buffer = dataBlock.buffer;
    offset = dataBlock.offset;
    this.scenarioId = scenarioId;
  }

  // ---------------------------------------------------------------------------------//
  public void draw (Canvas canvas)
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = canvas.getGraphicsContext2D ();

    gc.setFill (Color.BLACK);
    gc.fillRect (0, 0, canvas.getWidth (), canvas.getHeight ());

    if (scenarioId == 3)
      drawV2 (gc);
    else
      drawV1 (gc);
  }

  // ---------------------------------------------------------------------------------//
  private void drawV1 (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    PixelWriter pixelWriter = gc.getPixelWriter ();

    int x = 0;
    int y = 0;

    for (int j = 0; j < 500; j++)
    {
      int bits = buffer[offset + j] & 0xFF;
      for (int m = 0; m < 7; m++)
      {
        if ((bits & 1) == 1)
          writePixel (pixelWriter, x, y);

        bits >>= 1;
        x += 4;
        if (x >= 280)
        {
          x = 0;
          y += 4;
        }
      }
    }
  }

  // ---------------------------------------------------------------------------------//
  private void drawV2 (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    PixelWriter pixelWriter = gc.getPixelWriter ();

    int offset = this.offset;

    for (int i = 0; i < 6; i++)                                 // 6
      for (int j = 0; j < 10; j++)                              // 10
        for (int k = 7; k >= 0; k--)                            // 8 
        {
          int element = i * 560 + j * 7 + k * 70;
          int x = element % 70 * 4;
          int y = element / 70 * 4;
          int bits = buffer[offset++] & 0xFF;

          for (int m = 6; m >= 0; m--)                          // 7 pixels
          {
            if ((bits & 1) == 1)
              writePixel (pixelWriter, x, y);

            bits >>= 1;
            x += 4;
          }
        }
  }

  // ---------------------------------------------------------------------------------//
  private void writePixel (PixelWriter pixelWriter, int x, int y)
  // ---------------------------------------------------------------------------------//
  {
    pixelWriter.setColor (x, y, Color.WHITE);
    pixelWriter.setColor (x + 1, y, Color.WHITE);
    pixelWriter.setColor (x + 2, y, Color.WHITE);
    pixelWriter.setColor (x + 3, y, Color.WHITE);

    pixelWriter.setColor (x, y + 1, Color.WHITE);
    pixelWriter.setColor (x + 1, y + 1, Color.WHITE);
    pixelWriter.setColor (x + 2, y + 1, Color.WHITE);
    pixelWriter.setColor (x + 3, y + 1, Color.WHITE);

    pixelWriter.setColor (x, y + 2, Color.WHITE);
    pixelWriter.setColor (x + 1, y + 2, Color.WHITE);
    pixelWriter.setColor (x + 2, y + 2, Color.WHITE);
    pixelWriter.setColor (x + 3, y + 2, Color.WHITE);

    pixelWriter.setColor (x, y + 3, Color.WHITE);
    pixelWriter.setColor (x + 1, y + 3, Color.WHITE);
    pixelWriter.setColor (x + 2, y + 3, Color.WHITE);
    pixelWriter.setColor (x + 3, y + 3, Color.WHITE);
  }
}
