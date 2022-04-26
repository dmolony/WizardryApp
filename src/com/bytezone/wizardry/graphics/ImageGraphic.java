package com.bytezone.wizardry.graphics;

import com.bytezone.wizardry.origin.WizardryImage;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class ImageGraphic extends Graphic
// -----------------------------------------------------------------------------------//
{
  WizardryImage image;

  // ---------------------------------------------------------------------------------//
  public ImageGraphic (WizardryImage image)
  // ---------------------------------------------------------------------------------//
  {
    this.image = image;
  }

  // ---------------------------------------------------------------------------------//
  public void draw (Canvas canvas, int size)
  // ---------------------------------------------------------------------------------//
  {
    draw (canvas, size, 0, 0);
  }

  // ---------------------------------------------------------------------------------//
  public void draw (Canvas canvas, int size, int xInset, int yInset)
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = canvas.getGraphicsContext2D ();

    gc.setFill (Color.BLACK);
    gc.fillRect (0, 0, canvas.getWidth (), canvas.getHeight ());

    //    gc.setStroke (color);

    if (image.scenarioId == 3)
      drawV2 (gc, size, xInset, yInset);
    else
      drawV1 (gc, size, xInset, yInset);
  }

  // ---------------------------------------------------------------------------------//
  private void drawV1 (GraphicsContext gc, int size, int xInset, int yInset)
  // ---------------------------------------------------------------------------------//
  {
    pixelWriter = gc.getPixelWriter ();

    int x = xInset;
    int y = yInset;
    int width = 70 * size + xInset;

    for (int j = 0; j < 500; j++)
    {
      int bits = image.buffer[image.offset + j] & 0xFF;
      for (int m = 0; m < 7; m++)
      {
        if ((bits & 1) == 1)
          if (size == 2)
            writePixel2 (x, y);
          else if (size == 3)
            writePixel3 (x, y);
          else
            writePixel4 (x, y);

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
  private void drawV2 (GraphicsContext gc, int size, int xInset, int yInset)
  // ---------------------------------------------------------------------------------//
  {
    //    gc.translate (xInset, yInset);
    pixelWriter = gc.getPixelWriter ();

    int offset = image.offset;

    for (int i = 0; i < 6; i++)                                 // 6
      for (int j = 0; j < 10; j++)                              // 10
        for (int k = 7; k >= 0; k--)                            // 8 
        {
          int element = i * 560 + j * 7 + k * 70;
          int x = element % 70 * size;
          int y = element / 70 * size;
          int bits = image.buffer[offset++] & 0xFF;

          for (int m = 6; m >= 0; m--)                          // 7 pixels
          {
            if ((bits & 1) == 1)
              if (size == 2)
                writePixel2 (x, y);
              else if (size == 3)
                writePixel3 (x, y);
              else
                writePixel4 (x, y);

            bits >>= 1;
            x += size;
          }
        }
    //    gc.translate (-xInset, -yInset);
  }
}
