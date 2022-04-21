package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Monster
// -----------------------------------------------------------------------------------//
{
  public final int id;
  public final String name;                                //   0
  public final String namePlural;                          //  16
  public final String genericName;                         //  32
  public final String genericNamePlural;                   //  48
  public final int image;                                  //  64
  public final Dice groupSize;                             //  66  
  public final Dice hitPoints;                             //  72 
  public final int monsterClass;                           //  78
  public final int armourClass;                            //  80          
  public final int recsn;                                  //  82  total recs
  public final Dice[] recs = new Dice[7];                  //  84  damage?
  public final long experiencePoints;                                // 126  wizlong
  public final int drainAmt;                               // 132                    
  public final int healPts;                                // 134                    
  public final int reward1;                                // 136  gold
  public final int reward2;                                // 138  chest
  public final int enemyTeam;                              // 140  partner id
  public final int teamPercentage;                         // 142  partner %
  public final int mageSpells;                             // 144  spell level?      
  public final int priestSpells;                           // 146  spell level?      
  public final int unique;                                 // 148
  public final int breathe;                                // 150
  public final int unaffect;                               // 152
  public final int flags1;                                 // 154
  public final int flags2;                                 // 156

  public final String damageDice;

  // Scenario #1 values
  private static int[] experience = {                                     //
      55, 235, 415, 230, 380, 620, 840, 520, 550, 350,                    // 00-09
      475, 515, 920, 600, 735, 520, 795, 780, 990, 795,                   // 10-19
      1360, 1320, 1275, 680, 960, 600, 755, 1120, 2075, 870,              // 20-29
      960, 600, 1120, 2435, 1080, 2280, 975, 875, 1135, 1200,             // 30-39
      620, 740, 1460, 1245, 960, 1405, 1040, 1220, 1520, 1000,            // 40-49
      960, 2340, 2160, 2395, 790, 1140, 1235, 1790, 1720, 2240,           // 50-59
      1475, 1540, 1720, 1900, 1240, 1220, 1020, 20435, 5100, 3515,        // 60-69
      2115, 2920, 2060, 2140, 1400, 1640, 1280, 4450, 42840, 3300,        // 70-79
      40875, 5000, 3300, 2395, 1935, 1600, 3330, 44090, 40840, 5200,      // 80-89
      4155, 3000, 9200, 3160, 7460, 7320, 15880, 1600, 2200, 1000,        // 90-99
      1900                                                                // 100 
  };

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
    groupSize = new Dice (buffer, offset + 66);
    hitPoints = new Dice (buffer, offset + 72);

    monsterClass = Utility.getShort (buffer, offset + 78);
    armourClass = Utility.getSignedShort (buffer, offset + 80);

    recsn = Utility.getShort (buffer, offset + 82);               // 0-7
    StringBuilder dd = new StringBuilder ();
    for (int i = 0; i < recsn; i++)
    {
      recs[i] = new Dice (buffer, offset + 84 + i * 6);
      dd.append (recs[i].toString () + ", ");
    }
    if (dd.length () > 0)
    {
      dd.deleteCharAt (dd.length () - 1);
      dd.deleteCharAt (dd.length () - 1);
    }
    damageDice = dd.toString ();

    long exp = Utility.getWizLong (buffer, offset + 126);
    drainAmt = Utility.getShort (buffer, offset + 132);
    healPts = Utility.getShort (buffer, offset + 134);

    reward1 = Utility.getShort (buffer, offset + 136);            // gold rewards index
    reward2 = Utility.getShort (buffer, offset + 138);            // chest rewards index

    enemyTeam = Utility.getShort (buffer, offset + 140);          // partner id
    teamPercentage = Utility.getShort (buffer, offset + 142);     // partner %

    mageSpells = Utility.getShort (buffer, offset + 144);         // spell level
    priestSpells = Utility.getShort (buffer, offset + 146);       // spell level

    unique = Utility.getSignedShort (buffer, offset + 148);
    breathe = Utility.getShort (buffer, offset + 150);
    unaffect = Utility.getShort (buffer, offset + 152);

    flags1 = Utility.getShort (buffer, offset + 154);             // wepvsty3
    flags2 = Utility.getShort (buffer, offset + 156);             // sppc

    this.experiencePoints = exp == 0 ? experience[id] : exp;
  }

  // ---------------------------------------------------------------------------------//
  public int getGroupSize (MazeLevel mazeLevel)
  // ---------------------------------------------------------------------------------//
  {
    int howMany = groupSize.roll ();

    if (howMany > mazeLevel.displayLevel + 4)
      howMany = mazeLevel.displayLevel + 4;
    if (howMany > 9)
      howMany = 9;
    if (howMany < 1)
      howMany = 1;

    return howMany;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()       // used by ComboBox
  // ---------------------------------------------------------------------------------//
  {
    return name;
  }
}
