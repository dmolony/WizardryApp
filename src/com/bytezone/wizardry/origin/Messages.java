package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

// -----------------------------------------------------------------------------------//
public class Messages
// -----------------------------------------------------------------------------------//
{
  private List<OldMessage> messages = new ArrayList<> ();
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
          messages.add (new OldMessage (id, lines));
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
  public List<OldMessage> getMessages ()
  // ---------------------------------------------------------------------------------//
  {
    return messages;
  }

  // ---------------------------------------------------------------------------------//
  public OldMessage getMessage (int id)
  // ---------------------------------------------------------------------------------//
  {
    for (OldMessage message : messages)
      if (message.match (id))
        return message;

    return null;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    for (OldMessage message : messages)
    {
      text.append (message);
      text.append ("\n\n");
    }

    return text.toString ();
  }
}
