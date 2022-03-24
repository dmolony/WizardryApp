package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Monster
// -----------------------------------------------------------------------------------//
{
  int id;
  String name;
  String namePlural;
  String genericName;
  String genericNamePlural;
  int image;
  HitPoints hitPoints;
  int monsterClass;
  int armourClass;
  int recsn;
  HitPoints[] recs = new HitPoints[7];
  int expamt;
  int drainAmt;
  int healPts;
  int reward1;
  int reward2;
  int enemyTeam;
  int teamPercentage;
  int mageSpells;
  int priestSpells;
  int unique;
  int breathe;
  int unaffect;
  boolean[] wepvsty3 = new boolean[16];
  boolean[] sppc = new boolean[16];

  // ---------------------------------------------------------------------------------//
  public Monster (int id, DataBlock dataBlock)
  // ---------------------------------------------------------------------------------//
  {
    this.id = id;

    byte[] buffer = dataBlock.buffer;
    int offset = dataBlock.offset;
    int length = dataBlock.length;

    genericName = Utility.getPascalString (buffer, offset);
    genericNamePlural = Utility.getPascalString (buffer, offset + 16);
    name = Utility.getPascalString (buffer, offset + 32);
    namePlural = Utility.getPascalString (buffer, offset + 48);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    text.append (name);

    return text.toString ();
  }
}
