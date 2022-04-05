package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Character;
import com.bytezone.wizardry.origin.Character.Possession;
import com.bytezone.wizardry.origin.Item;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

// -----------------------------------------------------------------------------------//
public class CharactersPane extends BasePane
// -----------------------------------------------------------------------------------//
{
  private static final int NAME = 0;
  private static final int PASSWORD = 1;
  private static final int IN_MAZE = 2;
  private static final int RACE = 3;
  private static final int CLASS = 4;
  private static final int AGE = 5;
  private static final int STATUS = 6;
  private static final int ALIGNMENT = 7;
  private static final int GOLD = 8;
  private static final int EXPERIENCE = 9;
  private static final int CRIT = 10;
  private static final int HP_DAM_DICE = 11;
  private static final int MAGE_TOTALS = 12;
  private static final int PRIEST_TOTALS = 13;

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
  TextField[] textOut3;
  TextField[] textOut4;

  CheckBox[] checkBox1;
  CheckBox[] checkBox2;
  CheckBox[] checkBox3;
  CheckBox[] checkBox4;
  CheckBox[] checkBox5;
  CheckBox[] checkBox6;

  ComboBox<Character> charactersList = new ComboBox<> ();

  // ---------------------------------------------------------------------------------//
  public CharactersPane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry);

    setColumnConstraints (110, 64, 70, 20, 20, 20, 40, 40, 80, 20, 70, 20, 70, 20);
    RowConstraints rowCo = new RowConstraints (25);
    for (int i = 0; i < 20; i++)
      gridPane.getRowConstraints ().add (rowCo);

    setComboBox ("Character", charactersList, wizardry.getCharacters (), (a, b, c) -> update (c));

    GridPane.setColumnSpan (charactersList, 2);

    String[] labelText1 = { "Name", "Password", "In maze", "Race", "Class", "Age (weeks)", "Status",
        "Alignment", "Gold", "Experience", "Crit", "HP dam dice", "Mage", "Priest" };

    String[] labelText2 =
        { "Max lev AC", "Level", "HP left", "Max HP", "HP calc", "AC", "Regen", "Swing" };

    String[] attributesText = { "Strength", "IQ", "Piety", "Vitality", "Agility", "Luck" };

    // text values
    LabelPlacement lp1 = new LabelPlacement (0, 1, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 1, Pos.CENTER_LEFT, 2);
    textOut = createOutputFields (labelText1, lp1, dp1);

    // attributes
    LabelPlacement lp2 = new LabelPlacement (4, 1, HPos.RIGHT, 3);
    DataPlacement dp2 = new DataPlacement (7, 1, Pos.CENTER_RIGHT, 1);
    textOut1 = createOutputFields (attributesText, lp2, dp2);

    // numeric values
    LabelPlacement lp3 = new LabelPlacement (4, 8, HPos.RIGHT, 3);
    DataPlacement dp3 = new DataPlacement (7, 8, Pos.CENTER_RIGHT, 1);
    textOut2 = createOutputFields (labelText2, lp3, dp3);

    // possessions headings
    setLabel ("Eq", 3, 17, HPos.LEFT, 1);
    setLabel ("Cu", 4, 17, HPos.LEFT, 1);
    setLabel ("Id", 5, 17, HPos.LEFT, 1);
    setLabel ("Value", 6, 17, HPos.CENTER, 2);

    // possessions
    String[] possessionsText = new String[8];
    for (int i = 0; i < possessionsText.length; i++)
      possessionsText[i] = "Item # " + (i + 1);

    LabelPlacement lp4 = new LabelPlacement (0, 18, HPos.RIGHT, 1);
    DataPlacement dp4 = new DataPlacement (1, 18, Pos.CENTER_LEFT, 2);
    textOut3 = createOutputFields (possessionsText, lp4, dp4);

    // possessions eq/cu/id
    checkBox4 = createCheckBoxes (8, 3, 18);
    checkBox5 = createCheckBoxes (8, 4, 18);
    checkBox6 = createCheckBoxes (8, 5, 18);

    // possessions value
    DataPlacement dp5 = new DataPlacement (6, 18, Pos.CENTER_RIGHT, 2);
    textOut4 = createOutputFields (8, dp5);

    // spells headings
    setLabel ("Mage spells", 8, 0, HPos.RIGHT, 2);
    setLabel ("Priest spells", 11, 0, HPos.LEFT, 2);

    // spells
    String[] mageSpells = new String[21];
    String[] priestSpells1 = new String[21];
    String[] priestSpells2 = new String[8];

    for (int i = 0; i < mageSpells.length; i++)
      mageSpells[i] = WizardryOrigin.spells[i];
    for (int i = 0; i < priestSpells1.length; i++)
      priestSpells1[i] = WizardryOrigin.spells[21 + i];
    for (int i = 0; i < priestSpells2.length; i++)
      priestSpells2[i] = WizardryOrigin.spells[42 + i];

    checkBox1 = createCheckBoxes (mageSpells, 8, 1);
    checkBox2 = createCheckBoxes (priestSpells1, 10, 1);
    checkBox3 = createCheckBoxes (priestSpells2, 12, 1);
  }

  // ---------------------------------------------------------------------------------//
  private void update (Character character)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut[NAME], character.name);
    setText (textOut[PASSWORD], character.password);
    setText (textOut[IN_MAZE], character.inMaze);
    setText (textOut[RACE], character.race);
    setText (textOut[CLASS], character.characterClass);
    setText (textOut[AGE], getText (character.age));
    setText (textOut[STATUS], character.status);
    setText (textOut[ALIGNMENT], character.alignment);
    setText (textOut[GOLD], character.gold);
    setText (textOut[EXPERIENCE], character.experience);
    setText (textOut[CRIT], character.crithitm);
    setText (textOut[HP_DAM_DICE], character.hpdamrc);
    setText (textOut[MAGE_TOTALS], add (character.mageSpells));
    setText (textOut[PRIEST_TOTALS], add (character.priestSpells));

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

    for (int i = 0; i < 8; i++)
    {
      if (i < character.possessionsCount)
      {
        Possession possession = character.possessions.get (i);
        int itemNo = possession.itemNo ();
        Item item = wizardry.getItem (itemNo);

        setText (textOut3[i], possession.identified () ? item.name : item.nameUnknown);

        if (possession.identified ())
        {
          checkBox4[i].setSelected (possession.equipped ());
          checkBox5[i].setSelected (possession.cursed ());
          checkBox6[i].setSelected (true);

          setText (textOut4[i], item.price);    // formatted as number
        }
        else
        {
          checkBox4[i].setSelected (false);
          checkBox5[i].setIndeterminate (true);
          checkBox6[i].setSelected (false);

          setText (textOut4[i], "?");           // formatted as text
        }
      }
      else
      {
        setText (textOut3[i], "");
        setText (textOut4[i], "");
        checkBox4[i].setSelected (false);
        checkBox5[i].setSelected (false);
        checkBox6[i].setSelected (false);
        checkBox5[i].setIndeterminate (false);
      }
    }

    for (int i = 0; i < checkBox1.length; i++)
      checkBox1[i].setSelected (character.spellsKnown[i]);
    for (int i = 0; i < checkBox2.length; i++)
      checkBox2[i].setSelected (character.spellsKnown[i + checkBox1.length]);
    for (int i = 0; i < checkBox3.length; i++)
      checkBox3[i].setSelected (character.spellsKnown[i + checkBox1.length + checkBox2.length]);
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
