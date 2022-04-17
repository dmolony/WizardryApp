package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Font
// -----------------------------------------------------------------------------------//
{
  byte[] buffer;
  int offset;

  // ---------------------------------------------------------------------------------//
  public Font (byte[] buffer, int offset, int length)
  // ---------------------------------------------------------------------------------//
  {
    this.buffer = buffer;
    this.offset = offset;

    System.out.println (printChars ());
    System.out.println (getChar (5));
  }

  // ---------------------------------------------------------------------------------//
  private String printChars ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();
    for (int i = offset; i < offset + 512; i += 64)
    {
      for (int line = 0; line < 8; line++)
      {
        for (int j = 0; j < 8; j++)
        {
          int value = buffer[i + line + j * 8] & 0xFF;
          for (int bit = 0; bit < 7; bit++)
          {
            if ((value & 0x01) != 0)
              text.append ("O");
            else
              text.append (".");
            value >>>= 1;
          }
          text.append ("   ");
        }
        text.append ("\n");
      }
      text.append ("\n");
    }
    return text.toString ();
  }

  // ---------------------------------------------------------------------------------//
  private String getChar (int charNo)
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    int ptr = offset + charNo * 8;
    for (int i = 0; i < 8; i++)
    {
      int value = buffer[ptr++] & 0xFF;
      for (int bit = 0; bit < 7; bit++)
      {
        if ((value & 0x01) != 0)
          text.append ("O");
        else
          text.append (".");
        value >>>= 1;
      }
      text.append ("\n");
    }

    return text.toString ();
  }
}
