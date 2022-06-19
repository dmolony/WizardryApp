package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class Attributes1Pane extends DataPane
// -----------------------------------------------------------------------------------//
{
  static int MAGE_SPELLS = 0;
  static int PRIEST_SPELLS = 1;

  private static final int NAME = 0;
  private static final int PASSWORD = 1;
  private static final int STATUS = 2;
  private static final int ALIGNMENT = 3;
  private static final int RACE = 4;
  private static final int CLASS = 5;
  private static final int AGE = 6;
  private static final int AWARDS = 7;
  private static final int IN_MAZE = 8;
  private static final int GOLD = 9;
  private static final int EXPERIENCE = 10;
  private static final int CRIT = 11;
  private static final int HP_DAM_DICE = 12;
  private static final int MAGE_TOTALS = 13;
  private static final int PRIEST_TOTALS = 14;
  private static final int MYSTERY = 15;

  private TextField[] textOut;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public Attributes1Pane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 145);
    setAllRowConstraints (15, DataPane.ROW_HEIGHT);           // make all rows the same height

    String[] labelText =
        { "Name", "Password", "Status", "Alignment", "Race", "Class", "Age (weeks)", "Awards",
            "In maze", "Gold", "Experience", "Crit", "HP dam dice", "Mage", "Priest", "Bit 0" };

    LabelPlacement lp = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp = new DataPlacement (1, 0, Pos.CENTER_LEFT, 1);

    textOut = createTextFields (labelText, lp, dp);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut);
  }

  // ---------------------------------------------------------------------------------//
  void update (Character character)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut[NAME], character.name);
    setText (textOut[PASSWORD], character.password);
    setText (textOut[STATUS], character.status);
    setText (textOut[ALIGNMENT], character.alignment);
    setText (textOut[RACE], character.race);
    setText (textOut[CLASS], character.characterClass);
    setText (textOut[AGE], getText (character.age));
    setText (textOut[AWARDS], character.awards);
    setText (textOut[IN_MAZE], character.inMaze ? "** OUT **" : "");
    setText (textOut[GOLD], character.gold);
    setText (textOut[EXPERIENCE], character.experience);
    setText (textOut[CRIT], character.crithitm);
    setText (textOut[HP_DAM_DICE], character.hpdamrc);
    setText (textOut[MAGE_TOTALS], character.getSpellsString (MAGE_SPELLS));
    setText (textOut[PRIEST_TOTALS], character.getSpellsString (PRIEST_SPELLS));
    setText (textOut[MYSTERY], character.mysteryBit);
  }
}
