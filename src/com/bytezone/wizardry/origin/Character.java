package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.wizardry.origin.WizardryOrigin.Alignment;
import com.bytezone.wizardry.origin.WizardryOrigin.CharacterStatus;
import com.bytezone.wizardry.origin.WizardryOrigin.Race;

// -----------------------------------------------------------------------------------//
public class Character
// -----------------------------------------------------------------------------------//
{
  private static int MAX_POSSESSIONS = 8;

  public final String name;
  public final String password;
  public final boolean inMaze;
  public final Race race;
  public final String characterClass;
  public final int age;
  public final CharacterStatus status;
  public final Alignment alignment;
  public final int[] attributes = new int[6];      // 0:18

  int[] luckSkill = new int[5];       // 0:31
  int gold;
  int experience;
  int maxlevac;                       // max level armour class?
  int charlev;                        // character level?
  int hpLeft;
  int hpMax;
  boolean[] spellsKnown = new boolean[50];
  int[] mageSpells = new int[7];
  int[] priestSpells = new int[7];
  int hpCalCmd;
  int armourClass;
  int healPts;
  boolean crithitm;
  int swingCount;
  Dice hpdamrc;
  boolean[][] wepvsty2 = new boolean[2][14];
  boolean[][] wepvsty3 = new boolean[2][7];
  boolean[] wepvstyp = new boolean[14];
  LostXYL lostXYL;

  List<Possession> possessions = new ArrayList<> (MAX_POSSESSIONS);

  // ---------------------------------------------------------------------------------//
  public Character (int id, DataBlock dataBlock)
  // ---------------------------------------------------------------------------------//
  {
    byte[] buffer = dataBlock.buffer;
    int offset = dataBlock.offset;

    name = Utility.getPascalString (buffer, offset);
    password = Utility.getPascalString (buffer, offset + 16);
    inMaze = Utility.getShort (buffer, offset + 32) != 0;
    race = WizardryOrigin.Race.values ()[Utility.getShort (buffer, offset + 34)];
    characterClass = WizardryOrigin.characterClass[Utility.getShort (buffer, offset + 36)];
    age = Utility.getShort (buffer, offset + 38);
    status = WizardryOrigin.CharacterStatus.values ()[Utility.getShort (buffer, offset + 40)];
    alignment = WizardryOrigin.Alignment.values ()[Utility.getShort (buffer, offset + 42)];

    long attr = Utility.getLong (buffer, offset + 44);
    for (int i = 0; i < 6; i++)
    {
      attributes[i] = (int) (attr & 0x1F);
      attr >>>= i == 2 ? 6 : 5;
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return name;
  }
}
