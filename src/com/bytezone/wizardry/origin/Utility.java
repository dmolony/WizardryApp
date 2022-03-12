package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Utility
// -----------------------------------------------------------------------------------//
{
  // ---------------------------------------------------------------------------------//
  public static String getPascalString (byte[] buffer, int offset)
  // ---------------------------------------------------------------------------------//
  {
    int length = buffer[offset] & 0xFF;
    return new String (buffer, 1, length);
  }

  // ---------------------------------------------------------------------------------//
  public static int readTriple (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    return (buffer[ptr] & 0xFF) | (buffer[ptr + 1] & 0xFF) << 8 | (buffer[ptr + 2] & 0xFF) << 16;
  }

  // ---------------------------------------------------------------------------------//
  public static int getShort (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    try
    {
      return (buffer[ptr] & 0xFF) | ((buffer[ptr + 1] & 0xFF) << 8);
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      System.out.printf ("Index out of range (getShort): %04X  %<d%n", ptr);
      return 0;
    }
  }
}
