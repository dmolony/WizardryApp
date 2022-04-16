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
  public static final String[] resistance =
      { "No elements", "Fire", "Cold", "Poison", "Level drain", "Stoning", "Magic" };
  public static final String[] characterClass =
      { "Fighter", "Mage", "Priest", "Thief", "Bishop", "Samurai", "Lord", "Ninja" };
  public static final String[] property =
      { "Stone", "Poison", "Paralyze", "Auto Kill", "Be slept", "Run", "Gate in" };
  public static String[] trapType = { "Trapless chest", "Poison needle", "Gas bomb", "Bolt",
      "Teleporter", "Anti-mage", "Anti-priest", "Alarm" };
  public static String[] trapType3 =
      { "Crossbow bolt", "Exploding box", "Splinters", "Blades", "Stunner" };

  public static final String[] spells = { "Halito", "Mogref", "Katino", "Dumapic", "Dilto", "Sopic",
      "Mahalito", "Molito", "Morlis", "Dalto", "Lahalito", "Mamorlis", "Makanito", "Madalto",
      "Lakanito", "Zilwan", "Masopic", "Haman", "Malor", "Mahaman", "Tiltowait",

      "Kalki", "Dios", "Badios", "Milwa", "Porfic", "Matu", "Calfo", "Manifo", "Montino", "Lomilwa",
      "Dialko", "Latumapic", "Bamatu", "Dial", "Badial", "Latumofis", "Maporfic", "Dialma",
      "Badialma", "Litokan", "Kandi", "Di", "Badi", "Lorto", "Madi", "Mabadi", "Loktofeit",
      "Malikto", "Kadorto" };

  public static final String[] race = { "No race", "Human", "Elf", "Dwarf", "Gnome", "Hobbit" };

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
  private List<Character> characters;

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

  enum CharacterClass
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

    // add messages (before maze levels)
    messages = new Messages (disk.getScenarioMessages (), getScenarioId ());

    // add maze levels
    ScenarioData sd = header.get (MAZE_AREA);
    mazeLevels = new ArrayList<> (sd.totalUnits);

    int id = 0;
    for (DataBlock dataBlock : sd.dataBlocks)
    {
      MazeLevel mazeLevel = new MazeLevel (this, ++id, dataBlock);
      mazeLevels.add (mazeLevel);

      for (Special special : mazeLevel.getSpecials ())
        if (special.square == Square.SCNMSG)
        {
          Message message = getMessage (special.aux[1]);          // force message creation
          message.addLocations (special.locations);
        }
    }

    // add characters
    sd = header.get (CHARACTER_AREA);
    characters = new ArrayList<> (sd.totalUnits);

    id = 0;
    for (DataBlock dataBlock : sd.dataBlocks)
      try
      {
        characters.add (new Character (id++, dataBlock, getScenarioId ()));
      }
      catch (InvalidCharacterException e)
      {
        //        System.out.println (e);
        //        break;
        continue;
      }

    // add monsters
    sd = header.get (MONSTER_AREA);
    monsters = new ArrayList<> (sd.totalUnits);

    id = 0;
    for (DataBlock dataBlock : sd.dataBlocks)
      monsters.add (new Monster (id++, dataBlock));

    // add items
    sd = header.get (ITEM_AREA);
    items = new ArrayList<> (sd.totalUnits);

    id = 0;
    for (DataBlock dataBlock : sd.dataBlocks)
      items.add (new Item (id++, dataBlock));

    // add rewards
    sd = header.get (TREASURE_TABLE_AREA);
    rewards = new ArrayList<> (sd.totalUnits);

    id = 0;
    for (DataBlock dataBlock : sd.dataBlocks)
      rewards.add (new Reward (id++, dataBlock));

    // add images
    sd = header.get (IMAGE_AREA);
    images = new ArrayList<> (sd.totalUnits);

    id = 0;
    for (DataBlock dataBlock : sd.dataBlocks)
      images.add (new Image (id++, dataBlock, getScenarioId ()));

    //    if (false)
    //      for (Square square : Square.values ())
    //      {
    //        showExtra (square);
    //        System.out.println ();
    //      }
  }

  // ---------------------------------------------------------------------------------//
  //  private void showExtra (Square square)
  //  // ---------------------------------------------------------------------------------//
  //  {
  //    for (MazeLevel level : mazeLevels)
  //      for (int col = 0; col < 20; col++)
  //        for (int row = 0; row < 20; row++)
  //        {
  //          MazeCell mazeCell = level.getMazeCell (col, row);
  //          Special extra = mazeCell.getExtra ();
  //          if (extra != null && extra.is (square))
  //          {
  //            String fight = level.lair[col][row] ? " lair" : "";
  //            System.out.printf ("%s  %s %s%n", extra, mazeCell.getLocation (), fight);
  //          }
  //        }
  //  }

  // ---------------------------------------------------------------------------------//
  public int getScenarioId ()
  // ---------------------------------------------------------------------------------//
  {
    return header.scenarioId;
  }

  // ---------------------------------------------------------------------------------//
  public String getScenarioName ()
  // ---------------------------------------------------------------------------------//
  {
    return header.scenarioName;
  }

  // ---------------------------------------------------------------------------------//
  public List<MazeLevel> getMazeLevels ()
  // ---------------------------------------------------------------------------------//
  {
    return mazeLevels;
  }

  // ---------------------------------------------------------------------------------//
  public List<Character> getCharacters ()
  // ---------------------------------------------------------------------------------//
  {
    return characters;
  }

  // ---------------------------------------------------------------------------------//
  public Character getCharacter (int id)
  // ---------------------------------------------------------------------------------//
  {
    return characters.get (id);
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
  public String getItemName (int id)
  // ---------------------------------------------------------------------------------//
  {
    if (id >= 0 && id < items.size ())
      return items.get (id).name;

    return "** No such item **";
  }

  // ---------------------------------------------------------------------------------//
  public Trade getItemTrade (int itemNo)
  // ---------------------------------------------------------------------------------//
  {
    itemNo *= -1;
    itemNo -= 20000;

    int item1 = itemNo / 100;
    int item2 = itemNo % 100;

    return new Trade (item1, item2);
  }

  // ---------------------------------------------------------------------------------//
  public List<Reward> getRewards ()
  // ---------------------------------------------------------------------------------//
  {
    return rewards;
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
  public String getMessageText (int id)
  // ---------------------------------------------------------------------------------//
  {
    return messages.getMessage (id).getText ();
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
  public record Trade (int item1, int item2)
  // ---------------------------------------------------------------------------------//
  {

  }
}
