package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

// -----------------------------------------------------------------------------------//
public class Header
// -----------------------------------------------------------------------------------//
{
  static String[] typeText =
      { "header", "maze", "monsters", "rewards", "items", "characters", "images", "char levels" };
  static String[] scenarioNames = { "PROVING GROUNDS OF THE MAD OVERLORD!",
      "THE KNIGHT OF DIAMONDS", "THE LEGACY OF LLYLGAMYN", "THE RETURN OF WERDNA" };
  static int[] recordLengths = { 0, 894, 158, 168, 78, 208, 512, 78 };

  byte[] buffer;
  List<ScenarioData> scenarioData = new ArrayList<> ();
  String scenarioName;
  int scenarioId;

  // ---------------------------------------------------------------------------------//
  public Header (byte[] buffer)
  // ---------------------------------------------------------------------------------//
  {
    this.buffer = buffer;

    scenarioName = Utility.getPascalString (buffer, 0);

    for (int i = 0; i < scenarioNames.length; i++)
      if (scenarioNames[i].equals (scenarioName))
      {
        scenarioId = i + 1;
        break;
      }

    for (int i = 0; i < typeText.length; i++)
      scenarioData.add (new ScenarioData (buffer, i));

    if (false)
      for (ScenarioData sd : scenarioData)
        System.out.println (sd);
  }

  // ---------------------------------------------------------------------------------//
  public ScenarioData get (int index)
  // ---------------------------------------------------------------------------------//
  {
    return scenarioData.get (index);
  }
  /*
  TEXP = ARRAY[ FIGHTER..NINJA] OF ARRAY[ 0..12] OF TWIZLONG;
  TBCD = ARRAY[ 0..13] OF INTEGER;
  TSPEL012 = (GENERIC, PERSON, GROUP);
  TZSCN = (ZZERO, ZMAZE, ZENEMY, ZREWARD, ZOBJECT, ZCHAR, ZSPCCHRS, ZEXP);
  
  TSCNTOC = RECORD
         GAMENAME : STRING[ 40];
         RECPER2B : ARRAY[ ZZERO..ZEXP] OF INTEGER;
         RECPERDK : ARRAY[ ZZERO..ZEXP] OF INTEGER;
         UNUSEDXX : ARRAY[ ZZERO..ZEXP] OF INTEGER;
         BLOFF    : ARRAY[ ZZERO..ZEXP] OF INTEGER;
         RACE     : ARRAY[ NORACE..HOBBIT]         OF STRING[ 9];
         CLASS    : PACKED ARRAY[ FIGHTER..NINJA]  OF STRING[ 9];
         STATUS   : ARRAY[ OK..LOST]               OF STRING[ 8];
         ALIGN    : PACKED ARRAY[ UNALIGN..EVIL]   OF STRING[ 9];
         SPELLHSH : PACKED ARRAY[ 0..50] OF INTEGER;
         SPELLGRP : PACKED ARRAY[ 0..50] OF 0..7;
         SPELL012 : PACKED ARRAY[ 0..50] OF TSPEL012;
       END;
      */
}
