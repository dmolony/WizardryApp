package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Monster
// -----------------------------------------------------------------------------------//
{
  int id;
  String name;                                //   0
  String namePlural;                          //  16
  String genericName;                         //  32
  String genericNamePlural;                   //  48
  int image;                                  //  64
  Dice calc1;                                 //  66  groupsize?
  Dice hitPoints;                             //  72 
  int monsterClass;                           //  78
  int armourClass;                            //  80                    (11 - AC) * 40
  int recsn;                                  //  82  total recs
  Dice[] recs = new Dice[7];                  //  84  damage?
  int expamt;                                 // 128  (triple)
  int drainAmt;                               // 132                    * 200 exp
  int healPts;                                // 134                    *  90 exp
  int reward1;                                // 136  gold
  int reward2;                                // 138  chest
  int enemyTeam;                              // 140  partner id
  int teamPercentage;                         // 142  partner %
  int mageSpells;                             // 144  spell level?      *  35 exp
  int priestSpells;                           // 146  spell level?      *  35 exp
  int unique;                                 // 148
  int breathe;                                // 150
  int unaffect;                               // 152
  boolean[] wepvsty3 = new boolean[16];       // 154
  boolean[] sppc = new boolean[16];           // 156

  // ---------------------------------------------------------------------------------//
  public Monster (int id, DataBlock dataBlock)
  // ---------------------------------------------------------------------------------//
  {
    this.id = id;

    byte[] buffer = dataBlock.buffer;
    int offset = dataBlock.offset;

    genericName = Utility.getPascalString (buffer, offset);
    genericNamePlural = Utility.getPascalString (buffer, offset + 16);
    name = Utility.getPascalString (buffer, offset + 32);
    namePlural = Utility.getPascalString (buffer, offset + 48);

    image = Utility.getShort (buffer, offset + 64);
    hitPoints = new Dice (buffer, offset + 66);
    calc1 = new Dice (buffer, offset + 72);

    monsterClass = Utility.getShort (buffer, offset + 78);
    armourClass = Utility.getSignedShort (buffer, offset + 80);

    recsn = Utility.getShort (buffer, offset + 82);
    for (int i = 0; i < 7; i++)
      recs[i] = new Dice (buffer, offset + 84 + i * 6);

    //    expamt = Utility.readTriple (buffer, 130);
    drainAmt = Utility.getShort (buffer, offset + 132);
    healPts = Utility.getShort (buffer, offset + 134);
    reward1 = Utility.getShort (buffer, offset + 136);            // gold rewards index
    reward2 = Utility.getShort (buffer, offset + 138);            // chest rewards index
    enemyTeam = Utility.getShort (buffer, offset + 140);          // partner id
    teamPercentage = Utility.getShort (buffer, offset + 142);     // partner %
    mageSpells = Utility.getShort (buffer, offset + 144);         // spell level?
    priestSpells = Utility.getShort (buffer, offset + 146);       // spell level?
    unique = Utility.getShort (buffer, offset + 148);
    breathe = Utility.getShort (buffer, offset + 150);
    unaffect = Utility.getShort (buffer, offset + 152);
    int flags1 = Utility.getShort (buffer, offset + 154);         // wepvsty3
    int flags2 = Utility.getShort (buffer, offset + 156);         // sppc

    //    System.out.println (this);
    //    System.out.println (dump (buffer, offset + 112, 46));
  }

  // ---------------------------------------------------------------------------------//
  private String dump (byte[] buffer, int offset, int length)
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    text.append (String.format ("%-16s", name));
    for (int i = 0; i < length; i++)
      text.append (String.format ("%02X ", buffer[offset + i]));

    return text.toString ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%-16s %3d %8s %8s %3d %3d %3d %8s %8s %3d %3d %3d %3d", name, image,
        calc1, hitPoints, monsterClass, armourClass, recsn, recs[0], recs[1], drainAmt, healPts,
        reward1, reward2);
  }
}
