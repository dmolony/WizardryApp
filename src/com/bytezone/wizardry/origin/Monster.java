package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Monster
// -----------------------------------------------------------------------------------//
{
  public static final String[] property =
      { "Stone", "Poison", "Paralyse", "Autokill", "Be slept", "Run", "Gate in" };
  public static final String[] resistance =
      { "Friends", "Fire", "Cold", "Poison", "Level drain", "Stoning", "Magic" };

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
  public final int expamt;                                 // 126  (triple)
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
  public final String propertyText;
  public final String resistanceText;

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

    expamt = Utility.getWizLong (buffer, offset + 126);
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

    resistanceText = getFlagsText (flags1, resistance);
    propertyText = getFlagsText (flags2, property);
  }

  // ---------------------------------------------------------------------------------//
  private String getFlagsText (int value, String[] values)
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder p = new StringBuilder ();
    for (int i = 0; i < 7; i++)
    {
      if ((value & 0x01) != 0)
        p.append (values[i] + ", ");
      value >>>= 1;
    }
    if (p.length () > 0)
    {
      p.deleteCharAt (p.length () - 1);
      p.deleteCharAt (p.length () - 1);
    }
    return p.toString ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()       // used by ComboBox
  // ---------------------------------------------------------------------------------//
  {
    return name;
  }
}
