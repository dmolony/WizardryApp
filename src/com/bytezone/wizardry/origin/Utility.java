package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Utility
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_SHORT = 0xFFFF;

  // ---------------------------------------------------------------------------------//
  public static String removeUserName (String filePath)
  // ---------------------------------------------------------------------------------//
  {
    String userHome = System.getProperty ("user.home");

    if (filePath.startsWith (userHome))
      return "~" + filePath.substring (userHome.length ());

    return filePath;
  }

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

  // ---------------------------------------------------------------------------------//
  public static int getLong (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    try
    {
      int val = 0;
      for (int i = 3; i >= 0; i--)
      {
        val <<= 8;
        val += buffer[ptr + i] & 0xFF;
      }
      return val;
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      System.out.printf ("Index out of range (getLong): %08X  %<d%n", ptr);
      return 0;
    }
  }

  // ---------------------------------------------------------------------------------//
  public static int getLongBigEndian (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    int val = 0;
    for (int i = 0; i < 4; i++)
    {
      val <<= 8;
      val += buffer[ptr + i] & 0xFF;
    }
    return val;
  }

  // ---------------------------------------------------------------------------------//
  public static long getWizLong (byte[] buffer, int offset)
  // ---------------------------------------------------------------------------------//
  {
    int low = Utility.getShort (buffer, offset);
    int mid = Utility.getShort (buffer, offset + 2);
    int high = Utility.getShort (buffer, offset + 4);

    return high * 100000000L + mid * 10000L + low;
  }

  // ---------------------------------------------------------------------------------//
  public static String getBitString (int value, int bitLength)
  // ---------------------------------------------------------------------------------//
  {
    String bits = "000000000000000" + Integer.toBinaryString (value);
    return bits.substring (bits.length () - bitLength);
  }

  // ---------------------------------------------------------------------------------//
  public static void trimComma (StringBuilder text)
  // ---------------------------------------------------------------------------------//
  {
    while (text.length () > 0)
    {
      if (text.charAt (text.length () - 1) == ' ')
        text.deleteCharAt (text.length () - 1);
      else if (text.charAt (text.length () - 1) == ',')
        text.deleteCharAt (text.length () - 1);
      else
        break;
    }
  }
}
