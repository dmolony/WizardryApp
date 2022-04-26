package com.bytezone.mazewalker.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.bytezone.wizardry.origin.Character;
import com.bytezone.wizardry.origin.WizardryData;
import com.bytezone.wizardry.origin.WizardryData.Alignment;
import com.bytezone.wizardry.origin.WizardryData.CharacterClass;
import com.bytezone.wizardry.origin.WizardryData.CharacterStatus;

// -----------------------------------------------------------------------------------//
public class PartyManager
// -----------------------------------------------------------------------------------//
{
  WizardryData wizardry;

  public PartyManager (WizardryData wizardry)
  {
    this.wizardry = wizardry;
  }

  // ---------------------------------------------------------------------------------//
  public List<Character> getParty ()
  // ---------------------------------------------------------------------------------//
  {
    List<Character> roster = new ArrayList<> (wizardry.getCharacters ());
    List<Character> party = new ArrayList<> ();

    int totalGood = 0;
    int totalEvil = 0;

    for (Character character : roster)
    {
      if (character.status != CharacterStatus.OK)
        continue;
      if (character.alignment == Alignment.GOOD)
        ++totalGood;
      else if (character.alignment == Alignment.EVIL)
        ++totalEvil;
    }

    Alignment reject = totalGood >= totalEvil ? Alignment.EVIL : Alignment.GOOD;

    addBestClass (roster, party, CharacterClass.THIEF, reject);
    addBestClass (roster, party, CharacterClass.MAGE, reject);
    addBestClass (roster, party, CharacterClass.PRIEST, reject);

    while (party.size () < 6)
      if (!addBestFighter (roster, party, reject))
        break;

    Collections.sort (party, new SortByAC ());

    return party;
  }

  // ---------------------------------------------------------------------------------//
  private boolean addBestClass (List<Character> roster, List<Character> party,
      CharacterClass characterClass, Alignment reject)
  // ---------------------------------------------------------------------------------//
  {
    int max = 0;
    int ptr = -1;

    for (int i = 0; i < roster.size (); i++)
    {
      Character character = roster.get (i);

      if (character.status != CharacterStatus.OK || character.alignment == reject
          || character.characterClass != characterClass || party.contains (character))
        continue;

      if (character.hpMax > max)
      {
        max = character.hpMax;
        ptr = i;
      }
    }

    if (ptr >= 0)
    {
      party.add (roster.get (ptr));
      roster.remove (ptr);
      return true;
    }
    return false;
  }

  // ---------------------------------------------------------------------------------//
  private boolean addBestFighter (List<Character> roster, List<Character> party, Alignment reject)
  // ---------------------------------------------------------------------------------//
  {
    int max = 0;
    int ptr = -1;

    for (int i = 0; i < roster.size (); i++)
    {
      Character character = roster.get (i);

      if (character.status != CharacterStatus.OK || character.alignment == reject
          || party.contains (character))
        continue;

      if (character.characterClass != CharacterClass.FIGHTER
          && character.characterClass != CharacterClass.LORD
          && character.characterClass != CharacterClass.NINJA
          && character.characterClass != CharacterClass.SAMURAI)
        continue;

      if (character.hpMax > max)
      {
        max = character.hpMax;
        ptr = i;
      }
    }

    if (ptr >= 0)
    {
      party.add (roster.get (ptr));
      roster.remove (ptr);
      return true;
    }

    return false;
  }

  // ---------------------------------------------------------------------------------//
  class SortByAC implements Comparator<Character>
  // ---------------------------------------------------------------------------------//
  {
    @Override
    public int compare (Character a, Character b)
    {
      return a.armourClass - b.armourClass;
    }
  }
}
