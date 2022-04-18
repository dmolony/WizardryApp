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
  public void draw (Canvas canvas, int size, Color color)
  // ---------------------------------------------------------------------------------//
  {
    draw (canvas, size, color, 0, 0);
  }

  // ---------------------------------------------------------------------------------//
  public void draw (Canvas canvas, int size, Color color, int xInset, int yInset)
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = canvas.getGraphicsContext2D ();

    gc.setFill (Color.BLACK);
    gc.fillRect (0, 0, canvas.getWidth (), canvas.getHeight ());

    gc.setStroke (color);

    if (scenarioId == 3)
      drawV2 (gc, size, color, xInset, yInset);
    else
      drawV1 (gc, size, color, xInset, yInset);
  }

  // ---------------------------------------------------------------------------------//
  private void drawV1 (GraphicsContext gc, int size, Color color, int xInset, int yInset)
  // ---------------------------------------------------------------------------------//
  {
    PixelWriter pixelWriter = gc.getPixelWriter ();

    int x = xInset;
    int y = yInset;
    int width = 70 * size + xInset;

    for (int j = 0; j < 500; j++)
    {
      int bits = buffer[offset + j] & 0xFF;
      for (int m = 0; m < 7; m++)
      {
        if ((bits & 1) == 1)
          if (size == 2)
            writePixel2 (pixelWriter, x, y, color);
          else if (size == 3)
            writePixel3 (pixelWriter, x, y, color);
          else
            writePixel4 (pixelWriter, x, y, color);

        bits >>= 1;
        x += size;
        if (x >= width)
        {
          x = xInset;
          y += size;
        }
      }
    }
  }

  // ---------------------------------------------------------------------------------//
  private void drawV2 (GraphicsContext gc, int size, Color color, int xInset, int yInset)
  // ---------------------------------------------------------------------------------//
  {
    //    gc.translate (xInset, yInset);
    PixelWriter pixelWriter = gc.getPixelWriter ();

    int offset = this.offset;

    for (int i = 0; i < 6; i++)                                 // 6
      for (int j = 0; j < 10; j++)                              // 10
        for (int k = 7; k >= 0; k--)                            // 8 
        {
          int element = i * 560 + j * 7 + k * 70;
          int x = element % 70 * size;
          int y = element / 70 * size;
          int bits = buffer[offset++] & 0xFF;

          for (int m = 6; m >= 0; m--)                          // 7 pixels
          {
            if ((bits & 1) == 1)
              if (size == 2)
                writePixel2 (pixelWriter, x, y, color);
              else if (size == 3)
                writePixel3 (pixelWriter, x, y, color);
              else
                writePixel4 (pixelWriter, x, y, color);

            bits >>= 1;
            x += size;
          }
        }
    //    gc.translate (-xInset, -yInset);
  }

  // ---------------------------------------------------------------------------------//
  private void writePixel2 (PixelWriter pixelWriter, int x, int y, Color color)
  // ---------------------------------------------------------------------------------//
  {
    pixelWriter.setColor (x, y, color);
    pixelWriter.setColor (x + 1, y, color);

    pixelWriter.setColor (x, y + 1, color);
    pixelWriter.setColor (x + 1, y + 1, color);
  }

  // ---------------------------------------------------------------------------------//
  private void writePixel3 (PixelWriter pixelWriter, int x, int y, Color color)
  // ---------------------------------------------------------------------------------//
  {
    pixelWriter.setColor (x, y, color);
    pixelWriter.setColor (x + 1, y, color);
    pixelWriter.setColor (x + 2, y, color);

    pixelWriter.setColor (x, y + 1, color);
    pixelWriter.setColor (x + 1, y + 1, color);
    pixelWriter.setColor (x + 2, y + 1, color);

    pixelWriter.setColor (x, y + 2, color);
    pixelWriter.setColor (x + 1, y + 2, color);
    pixelWriter.setColor (x + 2, y + 2, color);
  }

  // ---------------------------------------------------------------------------------//
  private void writePixel4 (PixelWriter pixelWriter, int x, int y, Color color)
  // ---------------------------------------------------------------------------------//
  {
    pixelWriter.setColor (x, y, color);
    pixelWriter.setColor (x + 1, y, color);
    pixelWriter.setColor (x + 2, y, color);
    pixelWriter.setColor (x + 3, y, color);

    pixelWriter.setColor (x, y + 1, color);
    pixelWriter.setColor (x + 1, y + 1, color);
    pixelWriter.setColor (x + 2, y + 1, color);
    pixelWriter.setColor (x + 3, y + 1, color);

    pixelWriter.setColor (x, y + 2, color);
    pixelWriter.setColor (x + 1, y + 2, color);
    pixelWriter.setColor (x + 2, y + 2, color);
    pixelWriter.setColor (x + 3, y + 2, color);

    pixelWriter.setColor (x, y + 3, color);
    pixelWriter.setColor (x + 1, y + 3, color);
    pixelWriter.setColor (x + 2, y + 3, color);
    pixelWriter.setColor (x + 3, y + 3, color);
  }
}
