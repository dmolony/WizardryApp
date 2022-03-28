package com.bytezone.wizardry.origin;

import com.bytezone.wizardry.origin.WizardryOrigin.Alignment;
import com.bytezone.wizardry.origin.WizardryOrigin.ObjectType;

// -----------------------------------------------------------------------------------//
public class Item
// -----------------------------------------------------------------------------------//
{
  int id;
  String name;
  String nameUnknown;
  ObjectType type;
  Alignment alignment;
  boolean cursed;
  int special;
  int changeTo;
  int changeChance;
  int price;
  int boltac;
  int spellPwr;
  boolean[] classUse = new boolean[8];      // by Class
  int healPts;
  boolean[] wepvsty2 = new boolean[16];
  boolean[] wepvsty3 = new boolean[16];
  int armorMod;
  int wephitmd;
  Dice wephpdam;
  int xtraSwing;
  boolean crithitm;
  boolean[] wepvstyp = new boolean[14];

  // ---------------------------------------------------------------------------------//
  public Item (int id, DataBlock dataBlock)
  // ---------------------------------------------------------------------------------//
  {
    this.id = id;

    byte[] buffer = dataBlock.buffer;
    int offset = dataBlock.offset;
    int length = dataBlock.length;

    name = Utility.getPascalString (buffer, offset);
    nameUnknown = Utility.getPascalString (buffer, offset + 16);
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
