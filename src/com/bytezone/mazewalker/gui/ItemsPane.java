package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Item;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class ItemsPane extends BasePane
// -----------------------------------------------------------------------------------//
{
  private static final int GENERIC_NAME = 0;
  private static final int ITEM_CLASS = 1;
  private static final int ALIGNMENT = 2;
  private static final int CURSED = 3;
  private static final int CRIT_HIT = 4;
  private static final int CHANCE = 5;
  private static final int CHANGE_TO = 6;
  private static final int DAMAGE_DICE = 7;
  private static final int SPELL_POWER = 8;
  private static final int PRICE = 9;

  private static final int ID = 0;
  private static final int SPECIAL = 1;
  private static final int AC = 2;
  private static final int WEP_HIT_MOD = 3;
  private static final int XTRA_SWING = 4;
  private static final int BOLTAC = 5;
  private static final int REGEN = 6;

  String[] label1Text = { "Generic name", "Kind", "Alignment", "Cursed", "Auto kill", "Decay odds",
      "Decay to", "Damage dice", "Spell", "Value" };
  String[] label2Text =
      { "Id", "Special #", "AC", "To hit +", "# swings", "In store", "Regeneration" };

  ComboBox<Item> itemsList = new ComboBox<> ();

  TextField[] textOut1;
  TextField[] textOut2;

  CheckBox[] checkBoxes1;
  CheckBox[] checkBoxes2;
  CheckBox[] checkBoxes3;
  CheckBox[] checkBoxes4;

  // ---------------------------------------------------------------------------------//
  public ItemsPane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry);

    setColumnConstraints (110, 64, 90, 110, 50, 110, 50, 50, 20);

    LabelPlacement lp0 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp0 = new DataPlacement (1, 0, Pos.CENTER_LEFT, 2);
    setComboBox ("Item", itemsList, wizardry.getItems (),
        (options, oldValue, newValue) -> update (newValue), lp0, dp0);
    //    GridPane.setColumnSpan (itemsList, 2);

    setLabel ("Can be used by", 3, 0, HPos.RIGHT, 2);
    setLabel ("Resistance", 3, 10, HPos.RIGHT, 2);
    setLabel ("Protection vs", 5, 0, HPos.RIGHT, 2);
    setLabel ("Purposed vs", 7, 0, HPos.LEFT, 2);

    // basic attributes
    LabelPlacement lp1 = new LabelPlacement (0, 1, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 1, Pos.CENTER_LEFT, 2);
    textOut1 = createOutputFields (label1Text, lp1, dp1);

    // numeric attributes
    LabelPlacement lp2 = new LabelPlacement (0, 11, HPos.RIGHT, 1);
    DataPlacement dp2 = new DataPlacement (1, 11, Pos.CENTER_RIGHT, 1);
    textOut2 = createOutputFields (label2Text, lp2, dp2);

    // used by
    checkBoxes2 = createCheckBoxes (WizardryOrigin.characterClass, 3, 1);

    // resistance
    checkBoxes1 = createCheckBoxes (WizardryOrigin.resistance, 3, 11);

    // protection vs
    checkBoxes3 = createCheckBoxes (WizardryOrigin.monsterClass, 5, 1);

    // purposed vs
    checkBoxes4 = createCheckBoxes (checkBoxes3.length, 7, 1);
  }

  // ---------------------------------------------------------------------------------//
  private void update (Item item)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut1[GENERIC_NAME], item.nameUnknown);
    setText (textOut1[ITEM_CLASS], item.type);
    setText (textOut1[ALIGNMENT], item.alignment);
    setText (textOut1[CURSED], item.cursed);
    setText (textOut1[CRIT_HIT], item.crithitm);                    // Auto kill
    setText (textOut1[CHANGE_TO],
        item.changeChance > 0 ? wizardry.getItem (item.changeTo).name : "");
    setText (textOut1[CHANCE], item.changeChance > 0 ? item.changeChance + "%" : "");
    setText (textOut1[DAMAGE_DICE], item.wephpdam);

    if (item.spellPwr > 0)
      setText (textOut1[SPELL_POWER], WizardryOrigin.spells[item.spellPwr - 1]);
    else
      setText (textOut1[SPELL_POWER], "");

    setText (textOut1[PRICE], item.price);

    setText (textOut2[ID], item.id);
    setText (textOut2[SPECIAL], item.special);
    setText (textOut2[AC], item.armourClass);
    setText (textOut2[WEP_HIT_MOD], item.wephitmd);
    setText (textOut2[XTRA_SWING], item.xtraSwing);
    setText (textOut2[BOLTAC], item.boltac);
    setText (textOut2[REGEN], item.healPts);

    int protection = item.wepvsty2Flags;
    int purposed = item.wepvstyFlags;
    for (int i = 0; i < WizardryOrigin.monsterClass.length; i++)
    {
      checkBoxes3[i].setSelected ((protection & 0x01) != 0);
      checkBoxes4[i].setSelected ((purposed & 0x01) != 0);

      protection >>>= 1;
      purposed >>>= 1;
    }

    int resistance = item.wepvsty3Flags;
    for (int i = 0; i < WizardryOrigin.resistance.length; i++)
    {
      checkBoxes1[i].setSelected ((resistance & 0x01) != 0);
      resistance >>>= 1;
    }

    int characterClass = item.classUseFlags;
    for (int i = 0; i < WizardryOrigin.characterClass.length; i++)
    {
      checkBoxes2[i].setSelected ((characterClass & 0x01) != 0);
      characterClass >>>= 1;
    }
  }
}
