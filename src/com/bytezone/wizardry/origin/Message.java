package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

// ---------------------------------------------------------------------------------//
public class Message
// ---------------------------------------------------------------------------------//
{
  private List<Location> locations = new ArrayList<> ();
  private List<MessageLine> messageLines = new ArrayList<> ();
  private int messageId;

  // ---------------------------------------------------------------------------------//
  public Message (int id)
  // ---------------------------------------------------------------------------------//
  {
    this.messageId = id;
  }

  // ---------------------------------------------------------------------------------//
  void addLine (MessageLine messageLine)
  // ---------------------------------------------------------------------------------//
  {
    messageLines.add (messageLine);
  }

  // ---------------------------------------------------------------------------------//
  void addLocations (List<Location> locations)
  // ---------------------------------------------------------------------------------//
  {
    this.locations.addAll (locations);
  }

  // ---------------------------------------------------------------------------------//
  public List<Location> getLocations ()
  // ---------------------------------------------------------------------------------//
  {
    return locations;
  }

  // ---------------------------------------------------------------------------------//
  public int getTotalLocations ()
  // ---------------------------------------------------------------------------------//
  {
    return locations.size ();
  }

  // ---------------------------------------------------------------------------------//
  public int getId ()
  // ---------------------------------------------------------------------------------//
  {
    return messageId;
  }

  // ---------------------------------------------------------------------------------//
  //  public boolean match (int messageNum)
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    if (messageId == messageNum)
  //      return true;
  //
  //    // this code is to allow for a bug in scenario #1
  //    if (messageNum > messageId && messageNum < (messageId + messageLines.size ()))
  //      return true;
  //
  //    return false;
  //  }

  // ---------------------------------------------------------------------------------//
  public String getText ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    for (MessageLine line : messageLines)
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
