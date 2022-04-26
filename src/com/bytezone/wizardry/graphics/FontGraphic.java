package com.bytezone.wizardry.graphics;

import com.bytezone.wizardry.origin.WizardryFont;

import javafx.scene.canvas.GraphicsContext;

// -----------------------------------------------------------------------------------//
public class FontGraphic extends Graphic
// -----------------------------------------------------------------------------------//
{
  WizardryFont font;

  int inset = 2;
  int size = 2;

  int charWidth = 7 * size;
  int charHeight = 8 * size;

  // ---------------------------------------------------------------------------------//
  public FontGraphic (WizardryFont font)
  // ---------------------------------------------------------------------------------//
  {
    this.font = font;
  }

  // ---------------------------------------------------------------------------------//
  public void drawString (GraphicsContext gc, int column, int row, String text)
  // ---------------------------------------------------------------------------------//
  {
    pixelWriter = gc.getPixelWriter ();

    for (char c : text.toCharArray ())
      drawChar (column++, row, c - 32);
  }

  // ---------------------------------------------------------------------------------//
  private void drawChar (int column, int row, int charNo)
  // ---------------------------------------------------------------------------------//
  {
    int x = inset + column * charWidth;
    int y = inset + row * charHeight;

    int ptr = font.offset + charNo * 8;
    for (int i = 0; i < 8; i++)
    {
      int value = font.buffer[ptr++] & 0xFF;
      for (int bit = 0; bit < 7; bit++)
      {
        if ((value & 0x01) != 0)
          switch (size)
          {
            case 2:
              writePixel2 (x, y);
              break;
            case 3:
              writePixel3 (x, y);
              break;
            case 4:
              writePixel4 (x, y);
              break;
          }

        value >>>= 1;
        x += size;
      }

      x = inset + column * charWidth;
      y += size;
    }
  }
}
