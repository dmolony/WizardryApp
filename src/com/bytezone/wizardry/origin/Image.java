package com.bytezone.wizardry.origin;

import java.nio.ByteBuffer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;

// -----------------------------------------------------------------------------------//
public class Image
// -----------------------------------------------------------------------------------//
{
  Canvas canvas = new Canvas (70, 50);      // wh

  // ---------------------------------------------------------------------------------//
  public Image (int id, DataBlock dataBlock)
  // ---------------------------------------------------------------------------------//
  {
    byte[] buffer = dataBlock.buffer;
    int offset = dataBlock.offset;

    System.out.println (id);
    System.out.println (dataBlock.length);

    GraphicsContext gc = canvas.getGraphicsContext2D ();
    PixelWriter pixelWriter = gc.getPixelWriter ();
    PixelFormat<ByteBuffer> pixelFormat = PixelFormat.getByteRgbInstance ();

    for (int j = 0; j < 500; j++)
    {
      if (j % 10 == 0)
        System.out.println ();

      int bits = buffer[offset + j] & 0xFF;
      for (int m = 0; m < 7; m++)
      {
        if ((bits & 1) == 1)
          //          db.setElem (element, 255);
          System.out.print ("XX");
        else
          System.out.print ("..");

        bits >>= 1;
        //        element++;
      }
    }
    System.out.println ();
  }

}
