package com.bytezone.wizardry;

import com.bytezone.appbase.BorderedDataPane;
import com.bytezone.appbase.DataLayout;
import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.CharacterParty;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class PartyPane extends BorderedDataPane
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_PARTY_SIZE = 6;

  private TextField partyName;
  private TextField slogan;

  private TextField[] id;
  private TextField[] characterName;
  private TextField[] characterClass;
  private TextField[] armourClass;
  private TextField[] hitPoints;
  private TextField[] attributes;
  private TextField[] mageSpells;
  private TextField[] priestSpells;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public PartyPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (9, 8);                             // columns, rows

    int[] widths = { 70, 40, 110, 60, 40, 50, 130, 100, 100 };
    setColumnConstraints (widths);

    String[] partyLabels = { "Party", "Slogan", "# 1", "# 2", "# 3", "# 4", "# 5", "# 6" };
    createLabelsVertical (partyLabels, 0, 0, HPos.RIGHT);

    DataLayout dataLayout1 = new DataLayout (1, 0, 6, Pos.CENTER_LEFT, 4);
    DataLayout dataLayout2 = new DataLayout (1, 2, 6, Pos.CENTER_RIGHT);

    partyName = createTextField (dataLayout1);
    dataLayout1.setColumnSpan (5);
    slogan = createTextField (dataLayout1);

    id = createTextFields (dataLayout2);
    characterName = createTextFields (dataLayout2, Pos.CENTER_LEFT);
    characterClass = createTextFields (dataLayout2);
    armourClass = createTextFields (dataLayout2, Pos.CENTER_RIGHT);
    hitPoints = createTextFields (dataLayout2);
    attributes = createTextFields (dataLayout2, Pos.CENTER_LEFT);
    mageSpells = createTextFields (dataLayout2);
    priestSpells = createTextFields (dataLayout2);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;
  }

  // ---------------------------------------------------------------------------------//
  void update (Character character)
  // ---------------------------------------------------------------------------------//
  {
    if (wizardry.getScenarioId () != 4)
      return;

    CharacterParty party = character.getParty ();

    if (party == null)                  // blank character
    {
      setText (partyName, "");
      setText (slogan, "");
      for (int i = 0; i < MAX_PARTY_SIZE; i++)
        reset (i);
      return;
    }

    setText (partyName, party.getName ());
    setText (slogan, party.getMessage ());

    for (int i = 0; i < party.size (); i++)
    {
      Character member = party.get (i);
      setText (id[i], member.id);
      setText (characterName[i], member.name);
      setText (characterClass[i], member.getTypeString ());
      setText (armourClass[i], member.armourClass);
      setText (hitPoints[i], member.hpLeft);
      setText (attributes[i], member.getAttributeString ());
      setText (mageSpells[i], member.getSpellsString (Character.MAGE_SPELLS));
      setText (priestSpells[i], member.getSpellsString (Character.PRIEST_SPELLS));
    }

    for (int i = party.size (); i < MAX_PARTY_SIZE; i++)
      reset (i);
  }

  // ---------------------------------------------------------------------------------//
  private void reset (int i)
  // ---------------------------------------------------------------------------------//
  {
    setText (id[i], "");
    setText (characterName[i], "");
    setText (characterClass[i], "");
    setText (armourClass[i], "");
    setText (hitPoints[i], "");
    setText (attributes[i], "");
    setText (mageSpells[i], "");
    setText (priestSpells[i], "");
  }
}
