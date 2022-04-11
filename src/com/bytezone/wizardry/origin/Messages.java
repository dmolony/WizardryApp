package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

// -----------------------------------------------------------------------------------//
public class Messages
// -----------------------------------------------------------------------------------//
{
  private List<Message> messages = new ArrayList<> ();
  private int codeOffset = 185;

  // ---------------------------------------------------------------------------------//
  public Messages (byte[] buffer, int scenarioId)
  // ---------------------------------------------------------------------------------//
  {
    int offset = 0;
    int id = 0;
    List<String> lines = new ArrayList<> ();

    while (offset < buffer.length)
    {
      for (int i = 0; i < 504; i += 42)
      {
        String line = scenarioId == 1 ? Utility.getPascalString (buffer, offset + i) //
            : getCodedLine (buffer, offset + i);
        lines.add (line);

        if (buffer[offset + i + 40] == 1)               // last line of message
        {
          messages.add (new Message (id, lines));
          id += lines.size ();
          lines.clear ();
        }
      }
      offset += 512;
    }
  }

  // ---------------------------------------------------------------------------------//
  private String getCodedLine (byte[] buffer, int offset)
  // ---------------------------------------------------------------------------------//
  {
    int length = buffer[offset++] & 0xFF;
    byte[] translation = new byte[length];
    codeOffset--;

    for (int j = 0; j < length; j++)
    {
      int letter = buffer[offset++] & 0xFF;
      translation[j] = (byte) (letter - (codeOffset - j * 3));
    }

    return new String (translation, 0, length);
  }

  // ---------------------------------------------------------------------------------//
  public Message getMessage (int id)
  // ---------------------------------------------------------------------------------//
  {
    for (Message message : messages)
      if (message.match (id))
        return message;

    return null;
  }
}
