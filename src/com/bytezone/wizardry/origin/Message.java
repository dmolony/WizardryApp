package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

// -----------------------------------------------------------------------------------//
public class Message
// -----------------------------------------------------------------------------------//
{
  List<String> lines = new ArrayList<String> ();
  int messageId;

  // ---------------------------------------------------------------------------------//
  public Message (int ID, List<String> messages)
  // ---------------------------------------------------------------------------------//
  {
    lines.addAll (messages);
    messageId = ID;
  }

  // ---------------------------------------------------------------------------------//
  public int getId ()
  // ---------------------------------------------------------------------------------//
  {
    return messageId;
  }

  // ---------------------------------------------------------------------------------//
  public boolean match (int messageNum)
  // ---------------------------------------------------------------------------------//
  {
    if (messageId == messageNum)
      return true;

    // this code is to allow for a bug in scenario #1
    if (messageNum > messageId && messageNum < (messageId + lines.size ()))
      return true;

    return false;
  }

  // ---------------------------------------------------------------------------------//
  public String getText ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    for (String line : lines)
    {
      text.append (line);
      text.append ("\n");
    }

    text.deleteCharAt (text.length () - 1);

    return text.toString ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("Message %d", messageId);
  }
}
