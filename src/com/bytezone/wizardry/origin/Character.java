package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.wizardry.origin.WizardryOrigin.Alignment;
import com.bytezone.wizardry.origin.WizardryOrigin.Race;
import com.bytezone.wizardry.origin.WizardryOrigin.Status;

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
  Dice hpdamrc;
  boolean[][] wepvsty2 = new boolean[2][14];
  boolean[][] wepvsty3 = new boolean[2][7];
  boolean[] wepvstyp = new boolean[14];
  LostXYL lostXYL;

  List<Possession> possessions = new ArrayList<> (MAX_POSSESSIONS);

  // ---------------------------------------------------------------------------------//
  public Character (byte[] buffer, int offset)
  // ---------------------------------------------------------------------------------//
  {

  }
}
