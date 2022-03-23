package com.bytezone.wizardry.origin;

import static com.bytezone.wizardry.origin.ScenarioData.typeText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bytezone.wizardry.disk.WizardryDisk;

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
    String diskFileName =
        "/Users/denismolony/Documents/Examples/Apple Disk Images/Wizardry/murasama.dsk";
    File file = new File (diskFileName);
    if (!file.exists ())
    {
      System.out.println ("File does not exist");
      return;
    }

    WizardryDisk disk = new WizardryDisk (diskFileName);

    byte[] buffer = disk.getScenarioData ();
    scenarioName = Utility.getPascalString (buffer, 0);

    for (int i = 0; i < typeText.length; i++)
      scenarioData.add (new ScenarioData (buffer, i));

    ScenarioData sd = scenarioData.get (MAZE_AREA);
    maze = new Maze (buffer, sd.dataOffset * 512, sd.totalBlocks * 512);
    messages = new Messages (disk.getScenarioMessages ());
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
