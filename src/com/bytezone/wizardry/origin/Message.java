package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

// -----------------------------------------------------------------------------------//
public class Message
// -----------------------------------------------------------------------------------//
{
  List<String> lines = new ArrayList<String> ();
  int messageID;

  // ---------------------------------------------------------------------------------//
  public Message (int ID, List<String> messages)
  // ---------------------------------------------------------------------------------//
  {
    lines.addAll (messages);
    messageID = ID;
  }

  // ---------------------------------------------------------------------------------//
  public boolean match (int messageNum)
  // ---------------------------------------------------------------------------------//
  {
    if (messageID == messageNum)
      return true;

    // this code is to allow for a bug in scenario #1
    if (messageNum > messageID && messageNum < (messageID + lines.size ()))
      return true;

    return false;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
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
}
