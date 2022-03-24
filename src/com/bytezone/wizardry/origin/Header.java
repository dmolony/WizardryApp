package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

// -----------------------------------------------------------------------------------//
public class Header
// -----------------------------------------------------------------------------------//
{
  static String[] typeText =
      { "header", "maze", "monsters", "rewards", "items", "characters", "images", "char levels" };
  static String[] scenarioNames = { "PROVING GROUNDS OF THE MAD OVERLORD!",
      "THE KNIGHT OF DIAMONDS", "THE LEGACY OF LLYLGAMYN", "THE RETURN OF WERDNA" };
  static int[] recordLength = { 0, 894, 158, 168, 78, 208, 480, 78 };

  byte[] buffer;
  List<ScenarioData> scenarioData = new ArrayList<> ();
  String scenarioName;

  // ---------------------------------------------------------------------------------//
  public Header (byte[] buffer)
  // ---------------------------------------------------------------------------------//
  {
    this.buffer = buffer;

    scenarioName = Utility.getPascalString (buffer, 0);

    for (int i = 0; i < typeText.length; i++)
      scenarioData.add (new ScenarioData (buffer, i));

    if (false)
      for (ScenarioData sd : scenarioData)
        System.out.println (sd);
  }

  // ---------------------------------------------------------------------------------//
  public ScenarioData get (int index)
  // ---------------------------------------------------------------------------------//
  {
    return scenarioData.get (index);
  }
}
