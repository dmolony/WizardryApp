package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// -----------------------------------------------------------------------------------//
public class Messages
// -----------------------------------------------------------------------------------//
{
  private int codeOffset = 185;
  private List<MessageLine> messageLines = new ArrayList<> ();
  private Map<Integer, Message> messages = new TreeMap<> ();

  // ---------------------------------------------------------------------------------//
  public Messages (byte[] buffer, int scenarioId)
  // ---------------------------------------------------------------------------------//
  {
    int offset = 0;

    while (offset < buffer.length)
    {
      for (int i = 0; i < 504; i += 42)
      {
        String line = scenarioId == 1 ? Utility.getPascalString (buffer, offset + i)
            : getCodedLine (buffer, offset + i);
        messageLines.add (new MessageLine (line, buffer[offset + i + 40] == 1));
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
  public Collection<Message> getMessages ()
  // ---------------------------------------------------------------------------------//
  {
    return messages.values ();
  }

  // ---------------------------------------------------------------------------------//
  public Message getMessage (int id)
  // ---------------------------------------------------------------------------------//
  {
    Message message = messages.get (id);
    if (message != null)
      return message;

    message = new Message (id);

    while (id < messageLines.size ())
    {
      MessageLine messageLine = messageLines.get (id++);
      message.addLine (messageLine);
      if (messageLine.endOfMessage)
        break;
    }

    messages.put (message.getId (), message);
    return message;
  }
}
