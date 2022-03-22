package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

// -----------------------------------------------------------------------------------//
public class Messages
// -----------------------------------------------------------------------------------//
{
  List<Message> messages = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  public Messages (byte[] buffer)
  // ---------------------------------------------------------------------------------//
  {
    int ptr = 0;
    int id = 0;
    List<String> lines = new ArrayList<> ();

    while (ptr < buffer.length)
    {
      for (int i = 0; i < 504; i += 42)
      {
        String line = Utility.getPascalString (buffer, ptr + i);
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
