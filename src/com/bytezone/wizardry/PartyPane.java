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
  private TextField[] textOut7;
  private TextField[] textOut8;
  private TextField[] textOut9;
  private TextField[] textOut10;
  private TextField[] textOut11;
  private TextField[] textOut12;
  private TextField[] textOut13;
  private TextField[] textOut14;
  private TextField[] textOut15;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public PartyPane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (70, 40, 110, 60, 35, 50, 130, 100, 100);
    setAllRowConstraints (7, getRowHeight ());           // make all rows the same height

    String[] partyLabels1 = { "Party", "Slogan" };
    String[] partyLabels2 = { "1", "2", "3", "4", "5", "6" };

    LabelPlacement lp7 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp7 = new DataPlacement (1, 0, Pos.CENTER_LEFT, 5);
    textOut7 = createTextFields (partyLabels1, lp7, dp7);

    LabelPlacement lp8 = new LabelPlacement (0, 2, HPos.RIGHT, 1);
    DataPlacement dp8 = new DataPlacement (1, 3, Pos.CENTER_RIGHT, 1);
    textOut8 = createTextFields (partyLabels2, lp8, dp8);

    DataPlacement dp9 = new DataPlacement (2, 2, Pos.CENTER_LEFT, 1);
    textOut9 = createTextFields (6, dp9);

    DataPlacement dp10 = new DataPlacement (3, 2, Pos.CENTER_LEFT, 1);
    textOut10 = createTextFields (6, dp10);

    DataPlacement dp11 = new DataPlacement (4, 2, Pos.CENTER_RIGHT, 1);
    textOut11 = createTextFields (6, dp11);

    DataPlacement dp12 = new DataPlacement (5, 2, Pos.CENTER_RIGHT, 1);
    textOut12 = createTextFields (6, dp12);

    DataPlacement dp13 = new DataPlacement (6, 2, Pos.CENTER_LEFT, 1);
    textOut13 = createTextFields (6, dp13);

    DataPlacement dp14 = new DataPlacement (7, 2, Pos.CENTER_LEFT, 1);
    textOut14 = createTextFields (6, dp14);

    DataPlacement dp15 = new DataPlacement (8, 2, Pos.CENTER_LEFT, 1);
    textOut15 = createTextFields (6, dp15);
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
    CharacterParty party = character.getParty ();

    setText (textOut7[0], party.getName ());
    setText (textOut7[1], party.getMessage ());

    int pos = 0;
    if (party.size () > 1)
      for (Character member : party)
      {
        setText (textOut8[pos], member.id);
        setText (textOut9[pos], member.name);
        setText (textOut10[pos], member.getTypeString ());
        setText (textOut11[pos], member.armourClass);
        setText (textOut12[pos], member.hpLeft);
        setText (textOut13[pos], member.getAttributeString ());
        setText (textOut14[pos], member.getSpellsString (Character.MAGE_SPELLS));
        setText (textOut15[pos], member.getSpellsString (Character.PRIEST_SPELLS));
        pos++;
      }

    for (int i = pos; i < 6; i++)
    {
      setText (textOut8[i], "");
      setText (textOut9[i], "");
      setText (textOut10[i], "");
      setText (textOut11[i], "");
      setText (textOut12[i], "");
      setText (textOut13[i], "");
      setText (textOut14[i], "");
      setText (textOut15[i], "");
    }
  }
}
