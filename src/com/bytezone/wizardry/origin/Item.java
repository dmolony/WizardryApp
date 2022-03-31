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

  int classUseFlags;
  int wepvsty2Flags;
  int wepvsty3Flags;
  int wepvstyFlags;

  // ---------------------------------------------------------------------------------//
  public Item (int id, DataBlock dataBlock)
  // ---------------------------------------------------------------------------------//
  {
    this.id = id;

    byte[] buffer = dataBlock.buffer;
    int offset = dataBlock.offset;

    name = Utility.getPascalString (buffer, offset);
    nameUnknown = Utility.getPascalString (buffer, offset + 16);
    type = ObjectType.values ()[buffer[offset + 32]];
    alignment = Alignment.values ()[buffer[offset + 33]];
    cursed = Utility.getSignedShort (buffer, offset + 34) == -1;
    special = Utility.getShort (buffer, offset + 36);
    changeTo = Utility.getShort (buffer, offset + 38);
    changeChance = Utility.getShort (buffer, offset + 40);
    price = Utility.getWizLong (buffer, offset + 42);
    boltac = Utility.getShort (buffer, offset + 48);
    spellPwr = Utility.getShort (buffer, offset + 50);
    classUseFlags = Utility.getShort (buffer, offset + 52);
    healPts = Utility.getShort (buffer, offset + 54);
    wepvsty2Flags = Utility.getShort (buffer, offset + 56);
    wepvsty3Flags = Utility.getShort (buffer, offset + 58);
    armorMod = Utility.getShort (buffer, offset + 60);
    wephitmd = Utility.getShort (buffer, offset + 62);
    wephpdam = new Dice (buffer, offset + 64);
    xtraSwing = Utility.getShort (buffer, offset + 66);
    crithitm = Utility.getShort (buffer, offset + 68) == 1;
    wepvstyFlags = Utility.getShort (buffer, offset + 70);
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
