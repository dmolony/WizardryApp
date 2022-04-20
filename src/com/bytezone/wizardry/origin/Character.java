package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.wizardry.origin.WizardryOrigin.Alignment;
import com.bytezone.wizardry.origin.WizardryOrigin.CharacterClass;
import com.bytezone.wizardry.origin.WizardryOrigin.CharacterStatus;
import com.bytezone.wizardry.origin.WizardryOrigin.Race;

// -----------------------------------------------------------------------------------//
public class Character
// -----------------------------------------------------------------------------------//
{
  private static int MAX_POSSESSIONS = 8;
  private static char[] awardsText = ">!$#&*<?BCPKODG@".toCharArray ();

  public final String name;
  public final String password;
  public final boolean inMaze;
  public final Race race;
  public final CharacterClass characterClass;
  public final int age;
  public final CharacterStatus status;
  public final Alignment alignment;
  public final int[] attributes = new int[6];      // 0:18
  public final int[] luckSkill = new int[5];       // 0:31

  public final long gold;

  public final int possessionsCount;
  public final List<Possession> possessions = new ArrayList<> (MAX_POSSESSIONS);

  public final long experience;
  public final int maxlevac;                       // max level armour class?
  public final int charlev;                        // character level?
  public final int hpLeft;
  public final int hpMax;

  public final boolean mysteryBit;                          // first bit in spellsKnown
  public final boolean[] spellsKnown = new boolean[50];
  public final int[] mageSpells = new int[7];
  public final int[] priestSpells = new int[7];

  public final int hpCalCmd;
  public final int armourClass;
  public final int healPts;

  public final boolean crithitm;
  public final int swingCount;
  public final Dice hpdamrc;

  public final String awards;

  boolean[][] wepvsty2 = new boolean[2][14];
  boolean[][] wepvsty3 = new boolean[2][7];
  boolean[] wepvstyp = new boolean[14];
  LostXYL lostXYL;

  // ---------------------------------------------------------------------------------//
  public Character (int id, DataBlock dataBlock, int scenarioId) throws InvalidCharacterException
  // ---------------------------------------------------------------------------------//
  {
    byte[] buffer = dataBlock.buffer;
    int offset = dataBlock.offset;

    int nameLength = buffer[offset] & 0xFF;
    if (nameLength < 1 || nameLength > 15)
      throw new InvalidCharacterException ("Name too long");

    name = Utility.getPascalString (buffer, offset);
    if (name.isEmpty () || ("UNSET".equals (name) && buffer[offset + 40] == 0x07))   // 7 = LOST
      throw new InvalidCharacterException ("Character is UNSET");

    password = Utility.getPascalString (buffer, offset + 16);
    inMaze = Utility.getShort (buffer, offset + 32) != 0;
    race = WizardryOrigin.Race.values ()[Utility.getShort (buffer, offset + 34)];
    characterClass =
        WizardryOrigin.CharacterClass.values ()[Utility.getShort (buffer, offset + 36)];
    age = Utility.getShort (buffer, offset + 38);
    status = WizardryOrigin.CharacterStatus.values ()[Utility.getShort (buffer, offset + 40)];
    alignment = WizardryOrigin.Alignment.values ()[Utility.getShort (buffer, offset + 42)];

    long attr = Utility.getLong (buffer, offset + 44);
    for (int i = 0; i < 6; i++)
    {
      attributes[i] = (int) (attr & 0x1F);
      attr >>>= i == 2 ? 6 : 5;
    }

    // luck/skill
    //    System.out.println (HexFormatter.formatNoHeader (buffer, offset + 48, 4));

    gold = Utility.getWizLong (buffer, offset + 52);
    possessionsCount = Utility.getShort (buffer, offset + 58);      // 0-8

    for (int i = 0; i < possessionsCount; i++)
    {
      boolean equipped = Utility.getShort (buffer, offset + 60 + i * 8) == 1;
      boolean cursed = Utility.getShort (buffer, offset + 62 + i * 8) == 1;
      boolean identified = Utility.getShort (buffer, offset + 64 + i * 8) == 1;

      int itemId = Utility.getShort (buffer, offset + 66 + i * 8);
      if (scenarioId == 3 && itemId >= 1000)
        itemId -= 1000;             // why?

      possessions.add (new Possession (itemId, equipped, cursed, identified));
    }

    experience = Utility.getWizLong (buffer, offset + 124);
    maxlevac = Utility.getShort (buffer, offset + 130);
    charlev = Utility.getShort (buffer, offset + 132);
    hpLeft = Utility.getShort (buffer, offset + 134);
    hpMax = Utility.getShort (buffer, offset + 136);

    mysteryBit = (buffer[offset + 138] & 0x01) == 1;
    int index = -1;                         // skip mystery bit
    for (int i = 138; i < 145; i++)
      for (int bit = 0; bit < 8; bit++)
      {
        if (((buffer[offset + i] >>> bit) & 0x01) != 0)
          if (index >= 0)
            spellsKnown[index] = true;

        if (++index >= WizardryOrigin.spells.length)
          break;
      }

    for (int i = 0; i < 7; i++)
    {
      mageSpells[i] = Utility.getShort (buffer, offset + 146 + i * 2);
      priestSpells[i] = Utility.getShort (buffer, offset + 160 + i * 2);
    }

    hpCalCmd = Utility.getSignedShort (buffer, offset + 174);
    armourClass = Utility.getSignedShort (buffer, offset + 176);
    healPts = Utility.getShort (buffer, offset + 178);

    crithitm = Utility.getShort (buffer, offset + 180) == 1;
    swingCount = Utility.getShort (buffer, offset + 182);
    hpdamrc = new Dice (buffer, offset + 184);

    awards = getAwardString (buffer, offset);
  }

  // ---------------------------------------------------------------------------------//
  private String getAwardString (byte[] buffer, int offset)
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    int awards = Utility.getShort (buffer, offset + 206);

    for (int i = 0; i < 16; i++)
    {
      if ((awards & 0x01) != 0)
        text.append (awardsText[i]);
      awards >>>= 1;
    }

    return text.toString ();
  }

  // ---------------------------------------------------------------------------------//
  public String getRegenerationSign ()
  // ---------------------------------------------------------------------------------//
  {
    return healPts < 0 ? "-" : healPts == 0 ? " " : "+";
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return name;
  }
}
