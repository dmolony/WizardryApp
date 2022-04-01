package com.bytezone.wizardry.origin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bytezone.wizardry.disk.WizardryDisk;

// -----------------------------------------------------------------------------------//
public class WizardryOrigin
// -----------------------------------------------------------------------------------//
{
  public static final String[] monsterClass = { "Fighter", "Mage", "Priest", "Thief", "Midget",
      "Giant", "Mythical", "Dragon", "Animal", "Were", "Undead", "Demon", "Insect", "Enchanted" };

  static final int MAZE_AREA = 1;
  static final int MONSTER_AREA = 2;
  static final int TREASURE_TABLE_AREA = 3;
  static final int ITEM_AREA = 4;
  static final int CHARACTER_AREA = 5;
  static final int IMAGE_AREA = 6;
  static final int EXPERIENCE_AREA = 7;

  Messages messages;
  Header header;

  private List<Monster> monsters;
  private List<Item> items;
  private List<MazeLevel> mazeLevels;
  private List<Reward> rewards;
  private List<Image> images;

  public enum Square
  {
    NORMAL, STAIRS, PIT, CHUTE, SPINNER, DARK, TRANSFER, OUCHY, BUTTONZ, ROCKWATE, FIZZLE, SCNMSG,
    ENCOUNTE
  }

  public enum Direction
  {
    NORTH, SOUTH, EAST, WEST
  }

  public enum ObjectType
  {
    WEAPON, ARMOR, SHIELD, HELMET, GAUNTLET, SPECIAL, MISC
  }

  public enum Race
  {
    NORACE, HUMAN, ELF, DWARF, GNOME, HOBBIT
  }

  public enum Alignment
  {
    UNALIGN, GOOD, NEUTRAL, EVIL
  }

  public enum CharacterStatus
  {
    OK, AFRAID, ASLEEP, PLYZE, STONED, DEAD, ASHES, LOST
  }

  public enum Attribute
  {
    STRENGTH, IQ, PIETY, VITALITY, AGILITY, LUCK
  }

  enum Class
  {
    FIGHTER, MAGE, PRIEST, THIEF, BISHOP, SAMURAI, LORD, NINJA
  }

  enum Status
  {
    OK, AFRAID, ASLEEP, PLYZE, STONED, DEAD, ASHES, LOST
  }

  // ---------------------------------------------------------------------------------//
  public WizardryOrigin (String diskFileName)
  // ---------------------------------------------------------------------------------//
  {
    File file = new File (diskFileName);
    if (!file.exists ())
    {
      System.out.println ("File does not exist");
      return;
    }

    WizardryDisk disk = new WizardryDisk (diskFileName);
    if (disk == null)
    {
      System.out.println ("Not a Wizardry disk");
      return;
    }

    byte[] buffer = disk.getScenarioData ();
    header = new Header (buffer);

    ScenarioData sd = header.get (MAZE_AREA);
    mazeLevels = new ArrayList<> (sd.totalUnits);

    int id = 0;
    for (DataBlock dataBlock : sd.dataBlocks)
      mazeLevels.add (new MazeLevel (++id, dataBlock));

    sd = header.get (MONSTER_AREA);
    monsters = new ArrayList<> (sd.totalUnits);

    id = 0;
    for (DataBlock dataBlock : sd.dataBlocks)
      monsters.add (new Monster (id++, dataBlock));

    sd = header.get (ITEM_AREA);
    items = new ArrayList<> (sd.totalUnits);

    id = 0;
    for (DataBlock dataBlock : sd.dataBlocks)
      items.add (new Item (id++, dataBlock));

    sd = header.get (TREASURE_TABLE_AREA);
    rewards = new ArrayList<> (sd.totalUnits);

    id = 0;
    for (DataBlock dataBlock : sd.dataBlocks)
      rewards.add (new Reward (id++, dataBlock));

    sd = header.get (IMAGE_AREA);
    images = new ArrayList<> (sd.totalUnits);

    id = 0;
    for (DataBlock dataBlock : sd.dataBlocks)
      images.add (new Image (id++, dataBlock));

    messages = new Messages (disk.getScenarioMessages (), header.scenarioId > 1);

    if (false)
      for (Square square : Square.values ())
      {
        showExtra (square);
        System.out.println ();
      }
  }

  // ---------------------------------------------------------------------------------//
  void showExtra (Square square)
  // ---------------------------------------------------------------------------------//
  {
    for (MazeLevel level : mazeLevels)
      for (int col = 0; col < 20; col++)
        for (int row = 0; row < 20; row++)
        {
          MazeCell mazeCell = level.getMazeCell (col, row);
          Extra extra = mazeCell.getExtra ();
          if (extra != null && extra.is (square))
          {
            String fight = level.lair[col][row] ? " lair" : "";
            System.out.printf ("%s  %s %s%n", extra, mazeCell.getLocation (), fight);
          }
        }
  }

  // ---------------------------------------------------------------------------------//
  public int getScenarioId ()
  // ---------------------------------------------------------------------------------//
  {
    return header.scenarioId;
  }

  // ---------------------------------------------------------------------------------//
  public List<MazeLevel> getMazeLevels ()
  // ---------------------------------------------------------------------------------//
  {
    return mazeLevels;
  }

  // ---------------------------------------------------------------------------------//
  public List<Monster> getMonsters ()
  // ---------------------------------------------------------------------------------//
  {
    return monsters;
  }

  // ---------------------------------------------------------------------------------//
  public Monster getMonster (int id)
  // ---------------------------------------------------------------------------------//
  {
    return monsters.get (id);
  }

  // ---------------------------------------------------------------------------------//
  public List<Item> getItems ()
  // ---------------------------------------------------------------------------------//
  {
    return items;
  }

  // ---------------------------------------------------------------------------------//
  public Item getItem (int id)
  // ---------------------------------------------------------------------------------//
  {
    if (id >= 0 && id < items.size ())
      return items.get (id);

    System.out.printf ("Item %d out of range%n", id);
    return null;
  }

  // ---------------------------------------------------------------------------------//
  public List<Image> getImages ()
  // ---------------------------------------------------------------------------------//
  {
    return images;
  }

  // ---------------------------------------------------------------------------------//
  public Image getImage (int id)
  // ---------------------------------------------------------------------------------//
  {
    if (id >= 0 && id < images.size ())
      return images.get (id);

    System.out.printf ("Image %d out of range%n", id);
    return null;
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
}
