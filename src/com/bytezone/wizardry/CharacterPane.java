package com.bytezone.wizardry;

import com.bytezone.wizardry.origin.Character;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

// -----------------------------------------------------------------------------------//
public class CharacterPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  static int MAGE_SPELLS = 0;
  static int PRIEST_SPELLS = 1;

  private static final int NAME = 0;
  private static final int PASSWORD = 1;
  private static final int AWARDS = 7;
  private static final int IN_MAZE = 8;
  private static final int RACE = 4;
  private static final int CLASS = 5;
  private static final int AGE = 6;
  private static final int STATUS = 2;
  private static final int ALIGNMENT = 3;
  private static final int GOLD = 9;
  private static final int EXPERIENCE = 10;
  private static final int CRIT = 11;
  private static final int HP_DAM_DICE = 12;
  private static final int MAGE_TOTALS = 13;
  private static final int PRIEST_TOTALS = 14;
  private static final int MYSTERY = 15;

  private static final int MAXLEVAC = 0;
  private static final int CHAR_LEV = 1;
  private static final int HP_LEFT = 2;
  private static final int HP_MAX = 3;
  private static final int HP_CALC_MD = 4;
  private static final int AC = 5;
  private static final int REGEN = 6;
  private static final int SWING = 7;

  TextField[] textOut;
  TextField[] textOut1;
  TextField[] textOut2;
  //  TextField[] textOut3;
  //  TextField[] textOut4;
  TextField[] textOut5;

  CheckBox[] checkBox1;
  CheckBox[] checkBox11;
  CheckBox[] checkBox2;
  CheckBox[] checkBox3;
  //  CheckBox[] checkBox4;
  //  CheckBox[] checkBox5;
  //  CheckBox[] checkBox6;

  TextField[] textOut7;
  TextField[] textOut8;
  TextField[] textOut9;
  TextField[] textOut10;
  TextField[] textOut11;
  TextField[] textOut12;

  private WizardryData wizardry;
  private PartyPane partyPane = new PartyPane ();
  private BaggagePane baggagePane = new BaggagePane ();

  // ---------------------------------------------------------------------------------//
  public CharacterPane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 75, 70, 20, 20, 20, 30, 50, 80, 50, 80, 30, 70, 30, 70, 30, 70, 30);

    String[] labelText1 =
        { "Name", "Password", "Status", "Alignment", "Race", "Class", "Age (weeks)", "Awards",
            "In maze", "Gold", "Experience", "Crit", "HP dam dice", "Mage", "Priest", "Bit 0" };

    String[] labelText2 =
        { "Max lev AC", "Level", "HP left", "Max HP", "HP calc", "AC", "Regen", "Swing" };

    String[] attributesText = { "Strength", "IQ", "Piety", "Vitality", "Agility", "Luck" };
    String[] saveVsText = { "Death", "Wand", "Breath", "Petrify", "Spell" };

    // text values
    LabelPlacement lp1 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 0, Pos.CENTER_LEFT, 2);
    textOut = createTextFields (labelText1, lp1, dp1);

    // attributes
    LabelPlacement lp2 = new LabelPlacement (4, 1, HPos.RIGHT, 3);
    DataPlacement dp2 = new DataPlacement (7, 1, Pos.CENTER_RIGHT, 1);
    textOut1 = createTextFields (attributesText, lp2, dp2);

    // save Vs
    LabelPlacement lp5 = new LabelPlacement (8, 1, HPos.RIGHT, 1);
    DataPlacement dp5 = new DataPlacement (9, 1, Pos.CENTER_RIGHT, 1);
    textOut5 = createTextFields (saveVsText, lp5, dp5);

    // numeric values
    LabelPlacement lp3 = new LabelPlacement (4, 8, HPos.RIGHT, 3);
    DataPlacement dp3 = new DataPlacement (7, 8, Pos.CENTER_RIGHT, 1);
    textOut2 = createTextFields (labelText2, lp3, dp3);

    // spells headings
    createLabel ("Mage spells", 11, 0, HPos.LEFT, 2);
    createLabel ("Priest spells", 15, 0, HPos.LEFT, 2);

    // spells
    String[] mageSpells1 = new String[11];
    String[] mageSpells2 = new String[10];
    String[] priestSpells1 = new String[15];
    String[] priestSpells2 = new String[14];

    for (int i = 0; i < mageSpells1.length; i++)
      mageSpells1[i] = WizardryData.spells[i];
    for (int i = 0; i < mageSpells2.length; i++)
      mageSpells2[i] = WizardryData.spells[11 + i];
    for (int i = 0; i < priestSpells1.length; i++)
      priestSpells1[i] = WizardryData.spells[21 + i];
    for (int i = 0; i < priestSpells2.length; i++)
      priestSpells2[i] = WizardryData.spells[36 + i];

    checkBox1 = createCheckBoxes (mageSpells1, 10, 1);
    checkBox11 = createCheckBoxes (mageSpells2, 12, 1);
    checkBox2 = createCheckBoxes (priestSpells1, 14, 1);
    checkBox3 = createCheckBoxes (priestSpells2, 16, 1);

    GridPane.setConstraints (partyPane, 8, 17);
    GridPane.setColumnSpan (partyPane, 10);
    GridPane.setColumnSpan (partyPane, 10);

    GridPane.setConstraints (baggagePane, 0, 17);
    GridPane.setColumnSpan (baggagePane, 7);
    GridPane.setColumnSpan (baggagePane, 7);
    gridPane.getChildren ().add (baggagePane);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut);
    reset (textOut1);
    reset (textOut2);
    reset (textOut5);

    reset (textOut2);
    reset (checkBox1);
    reset (checkBox2);
    reset (checkBox3);

    baggagePane.setWizardry (wizardry);

    // party
    if (wizardry.getScenarioId () == 4)
    {
      gridPane.getChildren ().add (partyPane);
      partyPane.setWizardry (wizardry);
    }
    else
      gridPane.getChildren ().remove (partyPane);
  }

  // ---------------------------------------------------------------------------------//
  void update (Character character)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut[NAME], character.name);
    setText (textOut[AWARDS], character.awards);
    setText (textOut[PASSWORD], character.password);
    setText (textOut[IN_MAZE], character.inMaze ? "** OUT **" : "");
    setText (textOut[RACE], character.race);
    setText (textOut[CLASS], character.characterClass);
    setText (textOut[AGE], getText (character.age));
    setText (textOut[STATUS], character.status);
    setText (textOut[ALIGNMENT], character.alignment);
    setText (textOut[GOLD], character.gold);
    setText (textOut[EXPERIENCE], character.experience);
    setText (textOut[CRIT], character.crithitm);
    setText (textOut[HP_DAM_DICE], character.hpdamrc);
    setText (textOut[MAGE_TOTALS], add (character.spellAllowance[MAGE_SPELLS]));
    setText (textOut[PRIEST_TOTALS], add (character.spellAllowance[PRIEST_SPELLS]));
    setText (textOut[MYSTERY], character.mysteryBit);

    setText (textOut2[MAXLEVAC], character.maxlevac);
    setText (textOut2[CHAR_LEV], character.charlev);
    setText (textOut2[HP_LEFT], character.hpLeft);
    setText (textOut2[HP_MAX], character.hpMax);
    setText (textOut2[HP_CALC_MD], character.hpCalCmd);
    setText (textOut2[AC], character.armourClass);
    setText (textOut2[REGEN], character.healPts);
    setText (textOut2[SWING], character.swingCount);

    for (int i = 0; i < textOut1.length; i++)
      setText (textOut1[i], character.attributes[i]);

    for (int i = 0; i < textOut5.length; i++)
      setText (textOut5[i], character.saveVs[i]);

    for (int i = 0; i < checkBox1.length; i++)
      checkBox1[i].setSelected (character.spellsKnown[i]);
    for (int i = 0; i < checkBox2.length; i++)
      checkBox2[i].setSelected (character.spellsKnown[i + checkBox1.length]);
    for (int i = 0; i < checkBox3.length; i++)
      checkBox3[i].setSelected (character.spellsKnown[i + checkBox1.length + checkBox2.length]);

    baggagePane.update (character);

    if (wizardry.getScenarioId () == 4)
      partyPane.update (character);
  }

  // ---------------------------------------------------------------------------------//
  private String add (int[] totals)
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ();

    for (int i = 0; i < totals.length; i++)
      text.append (totals[i] + " / ");

    text.deleteCharAt (text.length () - 1);
    text.deleteCharAt (text.length () - 1);
    text.deleteCharAt (text.length () - 1);

    return text.toString ();
  }
}
