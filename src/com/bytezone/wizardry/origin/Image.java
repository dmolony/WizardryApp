package com.bytezone.wizardry.origin;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class Image
// -----------------------------------------------------------------------------------//
{
  byte[] buffer;
  int offset;

  // ---------------------------------------------------------------------------------//
  public Image (int id, DataBlock dataBlock)
  // ---------------------------------------------------------------------------------//
  {
    buffer = dataBlock.buffer;
    offset = dataBlock.offset;
  }

  // ---------------------------------------------------------------------------------//
  public void draw (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    //    gc = canvas.getGraphicsContext2D ();
    PixelWriter pixelWriter = gc.getPixelWriter ();
    //    PixelFormat<ByteBuffer> pixelFormat = PixelFormat.getByteRgbInstance ();

    int x = 0;
    int y = 0;

    gc.setFill (Color.BLACK);
    gc.fillRect (0, 0, 280, 200);

    for (int j = 0; j < 500; j++)
    {
      int bits = buffer[offset + j] & 0xFF;
      for (int m = 0; m < 7; m++)
      {
        if ((bits & 1) == 1)
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

        bits >>= 1;
        x += 4;
        if (x >= 280)
        {
          x = 0;
          y += 4;
        }
      }
    }
    //    System.out.println ();
  }
}
