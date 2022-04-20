package com.bytezone.wizardry.origin;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class Font
// -----------------------------------------------------------------------------------//
{
  private static int nextId = 1;

  String name;
  byte[] buffer;
  int offset;
  int id = nextId++;

  Color color = Color.PALEGREEN;

  int inset = 2;
  int size = 2;
  int charWidth = 7 * size;
  int charHeight = 8 * size;

  // ---------------------------------------------------------------------------------//
  public Font (String name, byte[] buffer, int offset, int length)
  // ---------------------------------------------------------------------------------//
  {
    this.name = name;
    this.buffer = buffer;
    this.offset = offset;
  }

  // ---------------------------------------------------------------------------------//
  public int id ()
  // ---------------------------------------------------------------------------------//
  {
    return id;
  }

  // ---------------------------------------------------------------------------------//
  public void setColor (Color color)
  // ---------------------------------------------------------------------------------//
  {
    this.color = color;
  }

  // ---------------------------------------------------------------------------------//
  public void drawString (GraphicsContext gc, int column, int row, String text)
  // ---------------------------------------------------------------------------------//
  {
    for (char c : text.toCharArray ())
      drawChar (gc, column++, row, c - 32);
  }

  // ---------------------------------------------------------------------------------//
  private void drawChar (GraphicsContext gc, int column, int row, int charNo)
  // ---------------------------------------------------------------------------------//
  {
    PixelWriter pixelWriter = gc.getPixelWriter ();

    int x = inset + column * charWidth;
    int y = inset + row * charHeight;

    int ptr = offset + charNo * 8;
    for (int i = 0; i < 8; i++)
    {
      int value = buffer[ptr++] & 0xFF;
      for (int bit = 0; bit < 7; bit++)
      {
        if ((value & 0x01) != 0)
          switch (size)
          {
            case 2:
              writePixel2 (pixelWriter, x, y);
              break;
            case 3:
              writePixel3 (pixelWriter, x, y);
              break;
            case 4:
              writePixel4 (pixelWriter, x, y);
              break;
          }

        value >>>= 1;
        x += size;
      }

      x = inset + column * charWidth;
      y += size;
    }
  }

  // ---------------------------------------------------------------------------------//
  private void writePixel2 (PixelWriter pixelWriter, int x, int y)
  // ---------------------------------------------------------------------------------//
  {
    pixelWriter.setColor (x, y, color);
    pixelWriter.setColor (x + 1, y, color);

    pixelWriter.setColor (x, y + 1, color);
    pixelWriter.setColor (x + 1, y + 1, color);
  }

  // ---------------------------------------------------------------------------------//
  private void writePixel3 (PixelWriter pixelWriter, int x, int y)
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
  private void writePixel4 (PixelWriter pixelWriter, int x, int y)
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

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return name;
  }
}
