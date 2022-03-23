package com.bytezone.wizardry.origin;

import static com.bytezone.wizardry.origin.ScenarioData.typeText;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

// -----------------------------------------------------------------------------------//
public class WizardryOrigin
// -----------------------------------------------------------------------------------//
{
  static final int MAZE_AREA = 1;
  static final int MONSTER_AREA = 2;
  static final int TREASURE_TABLE_AREA = 3;
  static final int ITEM_AREA = 4;
  static final int CHARACTER_AREA = 5;
  static final int IMAGE_AREA = 6;
  static final int EXPERIENCE_AREA = 7;

  List<ScenarioData> scenarioData = new ArrayList<> ();
  String scenarioName;

  public Maze maze;
  Messages messages;

  // ---------------------------------------------------------------------------------//
  public WizardryOrigin ()
  // ---------------------------------------------------------------------------------//
  {
    String mazeFileName = "/Users/denismolony/code/SCENARIO.DATA.BIN";
    String messagesFile = "/Users/denismolony/code/SCENARIO.MESGS.BIN";
    File file = new File (mazeFileName);
    if (!file.exists ())
    {
      System.out.println ("File does not exist");
      return;
    }

    try
    {
      byte[] buffer = Files.readAllBytes (file.toPath ());
      byte[] msgBuffer = Files.readAllBytes (new File (messagesFile).toPath ());

      scenarioName = Utility.getPascalString (buffer, 0);

      for (int i = 0; i < typeText.length; i++)
        scenarioData.add (new ScenarioData (buffer, i));

      ScenarioData sd = scenarioData.get (MAZE_AREA);
      maze = new Maze (buffer, sd.dataOffset * 512, sd.totalBlocks * 512);
      messages = new Messages (msgBuffer);
    }
    catch (IOException e)
    {
      e.printStackTrace ();
    }
  }

  // ---------------------------------------------------------------------------------//
  public Message getMessage (int id)
  // ---------------------------------------------------------------------------------//
  {
    return messages.getMessage (id);
  }

  // ---------------------------------------------------------------------------------//
  public Messages getMessages ()
  // ---------------------------------------------------------------------------------//
  {
    return messages;
  }

  // ---------------------------------------------------------------------------------//
  public static void main (String[] args)
  // ---------------------------------------------------------------------------------//
  {
    new WizardryOrigin ();
  }
}
