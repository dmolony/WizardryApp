package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.CharacterParty;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class PartyPane extends DataPane
// -----------------------------------------------------------------------------------//
{
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
    setColumnConstraints (70, 40, 110, 60, 35, 50, 130, 100, 100);
    setAllRowConstraints (8, getRowHeight ());           // make all rows the same height
    //    setGridLinesVisible (true);

    String[] partyLabels = { "Party", "Slogan", "# 1", "# 2", "# 3", "# 4", "# 5", "# 6" };
    createLabelsVertical (new LabelPlacement (partyLabels, 0, 0, HPos.RIGHT, 1));

    DataLayout dataLayout1 = new DataLayout (1, 0, 6, Pos.CENTER_LEFT, 4);
    DataLayout dataLayout2 = new DataLayout (1, 2, 6, Pos.CENTER_RIGHT);

    partyName = createTextField (dataLayout1);
    dataLayout1.columnSpan = 5;
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
  @Override
  public int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return 8;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getColumns ()
  // ---------------------------------------------------------------------------------//
  {
    return 9;
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
      for (int i = 0; i < 6; i++)
        reset (i);
      return;
    }

    setText (partyName, party.getName ());
    setText (slogan, party.getMessage ());

    int pos = 0;
    if (party.size () > 1)
      for (Character member : party)
      {
        setText (id[pos], member.id);
        setText (characterName[pos], member.name);
        setText (characterClass[pos], member.getTypeString ());
        setText (armourClass[pos], member.armourClass);
        setText (hitPoints[pos], member.hpLeft);
        setText (attributes[pos], member.getAttributeString ());
        setText (mageSpells[pos], member.getSpellsString (Character.MAGE_SPELLS));
        setText (priestSpells[pos], member.getSpellsString (Character.PRIEST_SPELLS));
        pos++;
      }

    for (int i = pos; i < 6; i++)
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
