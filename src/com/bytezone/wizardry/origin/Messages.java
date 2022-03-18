package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Messages
// -----------------------------------------------------------------------------------//
{
  // ---------------------------------------------------------------------------------//
  public Messages (byte[] buffer)
  // ---------------------------------------------------------------------------------//
  {
    int ptr = 0;

    while (ptr < buffer.length)
    {
      for (int i = 0; i < 512; i += 42)
      {
        String line = Utility.getPascalString (buffer, ptr + i);
        System.out.println (line);
      }
      ptr += 512;
    }
  }
}
