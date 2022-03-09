package com.bytezone.wizardry.origin;

// -----------------------------------------------------------------------------------//
public class Possession
// -----------------------------------------------------------------------------------//
{
  boolean equipped;
  boolean cursed;
  boolean identified;
  int id;

  // ---------------------------------------------------------------------------------//
  public Possession (int id, boolean equipped, boolean cursed, boolean identified)
  // ---------------------------------------------------------------------------------//
  {
    this.equipped = equipped;
    this.cursed = cursed;
    this.identified = identified;
    this.id = id;
  }
}
