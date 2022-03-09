package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

// -----------------------------------------------------------------------------------//
public class Character
// -----------------------------------------------------------------------------------//
{
  private static int MAX_POSSESSIONS = 8;

  String name;
  String password;
  boolean inMaze;
  Race race;
  Class cClass;
  int age;
  Status status;
  Alignment alignment;
  int[] attributes = new int[6];      // 0:18
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
  HitPoints hpdamrc;
  boolean[][] wepvsty2 = new boolean[2][14];
  boolean[][] wepvsty3 = new boolean[2][7];
  boolean[] wepvstyp = new boolean[14];
  LostXYL lostXYL;

  List<Possession> possessions = new ArrayList<> (MAX_POSSESSIONS);

  enum Race
  {
    NORACE, HUMAN, ELF, DWARF, GNOME, HOBBIT
  }

  enum Class
  {
    FIGHTER, MAGE, PRIEST, THIEF, BISHOP, SAMURAI, LORD, NINJA
  }

  enum Status
  {
    OK, AFRAID, ASLEEP, PLYZE, STONED, DEAD, ASHES, LOST
  }

  enum Alignment
  {
    UNALIGN, GOOD, NEUTRAL, EVIL
  }

  // ---------------------------------------------------------------------------------//
  public Character (byte[] buffer, int offset)
  // ---------------------------------------------------------------------------------//
  {

  }
}
