package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

// -----------------------------------------------------------------------------------//
public class Messages
// -----------------------------------------------------------------------------------//
{
  List<Message> messages = new ArrayList<> ();
  int codeOffset = 185;

  // ---------------------------------------------------------------------------------//
  public Messages (byte[] buffer, boolean coded)
  // ---------------------------------------------------------------------------------//
  {
    int ptr = 0;
    int id = 0;
    List<String> lines = new ArrayList<> ();

    while (ptr < buffer.length)
    {
      for (int i = 0; i < 504; i += 42)
      {
        String line =
            !coded ? Utility.getPascalString (buffer, ptr + i) : getLine (buffer, ptr + i);
        lines.add (line);

        int lastLine = buffer[ptr + i + 40] & 0xFF;
        if (lastLine == 1)
        {
          messages.add (new Message (id, lines));
          id += lines.size ();
          lines.clear ();
        }
      }
      ptr += 512;
    }
  }

  // ---------------------------------------------------------------------------------//
  protected String getLine (byte[] buffer, int offset)
  // ---------------------------------------------------------------------------------//
  {
    int length = buffer[offset++] & 0xFF;
    byte[] translation = new byte[length];
    codeOffset--;
    for (int j = 0; j < length; j++)
    {
      translation[j] = buffer[offset + j];
      translation[j] -= codeOffset - j * 3;
    }
    return HexFormatter.getString (translation, 0, length);
  }

  // ---------------------------------------------------------------------------------//
  public Message getMessage (int id)
  // ---------------------------------------------------------------------------------//
  {
    for (Message message : messages)
    {
      if (message.match (id))
        return message;
    }

    return null;
  }
}
