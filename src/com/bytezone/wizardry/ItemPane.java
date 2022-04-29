package com.bytezone.wizardry;

import com.bytezone.wizardry.origin.Item;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

//-----------------------------------------------------------------------------------//
public class ItemPane extends DataPane
//-----------------------------------------------------------------------------------//
{
  private static final int NAME = 0;
  private static final int GENERIC_NAME = 1;
  private static final int ITEM_CLASS = 2;
  private static final int ALIGNMENT = 3;
  private static final int CURSED = 4;
  private static final int CRIT_HIT = 5;
  private static final int CHANCE = 6;
  private static final int CHANGE_TO = 7;
  private static final int DAMAGE_DICE = 8;
  private static final int SPELL_POWER = 9;
  private static final int PRICE = 10;

  private static final int ID = 0;
  private static final int SPECIAL = 1;
  private static final int AC = 2;
  private static final int WEP_HIT_MOD = 3;
  private static final int XTRA_SWING = 4;
  private static final int BOLTAC = 5;
  private static final int REGEN = 6;

  String[] label1Text = { "Name", "Generic name", "Kind", "Alignment", "Cursed", "Auto kill",
      "Decay odds", "Decay to", "Damage dice", "Spell", "Value" };
  String[] label2Text =
      { "Id", "Special #", "AC", "To hit +", "# swings", "In store", "Regeneration" };

  ComboBox<Item> itemsList = new ComboBox<> ();

  TextField[] textOut1;
  TextField[] textOut2;

  CheckBox[] checkBoxes1;
  CheckBox[] checkBoxes2;
  CheckBox[] checkBoxes3;
  CheckBox[] checkBoxes4;
  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public ItemPane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 64, 90, 110, 20, 110, 30, 100);

    createLabel ("Can be used by", 3, 0, HPos.RIGHT, 2);
    createLabel ("Resistance", 3, 10, HPos.RIGHT, 2);
    createLabel ("Protection vs", 5, 0, HPos.RIGHT, 2);
    createLabel ("Purposed vs", 7, 0, HPos.LEFT, 2);

    // basic attributes
    LabelPlacement lp1 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 0, Pos.CENTER_LEFT, 2);
    textOut1 = createTextFields (label1Text, lp1, dp1);

    // numeric attributes
    LabelPlacement lp2 = new LabelPlacement (0, 11, HPos.RIGHT, 1);
    DataPlacement dp2 = new DataPlacement (1, 11, Pos.CENTER_RIGHT, 1);
    textOut2 = createTextFields (label2Text, lp2, dp2);

    // used by
    checkBoxes2 = createCheckBoxes (WizardryData.characterClass, 3, 1);

    // resistance
    checkBoxes1 = createCheckBoxes (WizardryData.resistance, 3, 11);

    // protection vs
    checkBoxes3 = createCheckBoxes (WizardryData.monsterClass, 5, 1);

    // purposed vs
    checkBoxes4 = createCheckBoxes (checkBoxes3.length, 7, 1);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;
  }

  // ---------------------------------------------------------------------------------//
  void update (Item item)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut1[NAME], item.name);
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
      setText (textOut1[SPELL_POWER], WizardryData.spells[item.spellPwr - 1]);
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
    for (int i = 0; i < WizardryData.monsterClass.length; i++)
    {
      checkBoxes3[i].setSelected ((protection & 0x01) != 0);
      checkBoxes4[i].setSelected ((purposed & 0x01) != 0);

      protection >>>= 1;
      purposed >>>= 1;
    }

    int resistance = item.wepvsty3Flags;
    for (int i = 0; i < WizardryData.resistance.length; i++)
    {
      checkBoxes1[i].setSelected ((resistance & 0x01) != 0);
      resistance >>>= 1;
    }

    int characterClass = item.classUseFlags;
    for (int i = 0; i < WizardryData.characterClass.length; i++)
    {
      checkBoxes2[i].setSelected ((characterClass & 0x01) != 0);
      characterClass >>>= 1;
    }
  }
}
