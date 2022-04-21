package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

// -----------------------------------------------------------------------------------//
public class Header
// -----------------------------------------------------------------------------------//
{
  static public final String[] typeText =
      { "header", "maze", "monsters", "rewards", "items", "characters", "images", "char levels" };
  static public final String[] scenarioNames = { "PROVING GROUNDS OF THE MAD OVERLORD!",
      "THE KNIGHT OF DIAMONDS", "THE LEGACY OF LLYLGAMYN", "THE RETURN OF WERDNA" };

  List<ScenarioData> scenarioData = new ArrayList<> ();
  String scenarioName;
  int scenarioId;

  List<Font> fonts = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  public Header (byte[] buffer)
  // ---------------------------------------------------------------------------------//
  {
    scenarioName = Utility.getPascalString (buffer, 0);

    for (int i = 0; i < scenarioNames.length; i++)
      if (scenarioNames[i].equals (scenarioName))
      {
        scenarioId = i + 1;
        break;
      }

    for (int i = 0; i < typeText.length; i++)
      scenarioData.add (new ScenarioData (buffer, i));

    if (scenarioId < 3)
    {
      fonts.add (new Font ("Alphabet", buffer, 512, 512));
      fonts.add (new Font ("Graphics", buffer, 1024, 512));
      fonts.add (new Font ("Unknown", buffer, 1536, 512));    // probably not a font
    }
  }

  // ---------------------------------------------------------------------------------//
  public ScenarioData get (int index)
  // ---------------------------------------------------------------------------------//
  {
    return scenarioData.get (index);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    text.append (String.format ("Scenario # ........ %d%n", scenarioId));
    text.append (String.format ("Scenario name ..... %s%n", scenarioName));

    text.append ("\n");
    for (ScenarioData sd : scenarioData)
    {
      text.append (sd);
      text.append ("\n");
    }

    return text.toString ();
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
