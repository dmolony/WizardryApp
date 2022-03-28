package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Utility
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_SHORT = 0xFFFF;

  // ---------------------------------------------------------------------------------//
  public static String getPascalString (byte[] buffer, int offset)
  // ---------------------------------------------------------------------------------//
  {
    int length = buffer[offset] & 0xFF;
    return new String (buffer, offset + 1, length);
  }

  // ---------------------------------------------------------------------------------//
  public static int readTriple (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    return (buffer[ptr] & 0xFF) | (buffer[ptr + 1] & 0xFF) << 8 | (buffer[ptr + 2] & 0xFF) << 16;
  }

  // ---------------------------------------------------------------------------------//
  public static int signShort (int val)
  // ---------------------------------------------------------------------------------//
  {
    if ((val & 0x8000) != 0)
      return val - MAX_SHORT - 1;
    else
      return val;
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

  // ---------------------------------------------------------------------------------//
  public static int getSignedShort (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    return signShort (getShort (buffer, ptr));
  }
}
