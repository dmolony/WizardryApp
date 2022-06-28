package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Item;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class ItemPane1 extends DataPane
// -----------------------------------------------------------------------------------//
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

  private WizardryData wizardry;

  private TextField[] textOut;

  // ---------------------------------------------------------------------------------//
  public ItemPane1 ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 11);                             // columns, rows

    setColumnConstraints (110, 145);

    String[] labelText = { "Name", "Generic name", "Kind", "Alignment", "Cursed", "Auto kill",
        "Decay odds", "Decay to", "Damage dice", "Spell", "Value" };
    assert getRows () == labelText.length;

    createLabelsVertical (labelText, 0, 0, HPos.RIGHT, 1);
    textOut = createTextFields (new DataLayout (1, 0, getRows (), Pos.CENTER_LEFT));
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut);
  }

  // ---------------------------------------------------------------------------------//
  void update (Item item)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut[NAME], item.name);
    setText (textOut[GENERIC_NAME], item.nameGeneric);
    if (item.type == null)
      return;

    setText (textOut[ITEM_CLASS], item.type);
    setText (textOut[ALIGNMENT], item.alignment);
    setText (textOut[CURSED], item.cursed);
    setText (textOut[CRIT_HIT], item.crithitm);                    // Auto kill
    setText (textOut[CHANGE_TO],
        item.changeChance > 0 ? wizardry.getItem (item.changeTo).name : "");
    setText (textOut[CHANCE], item.changeChance > 0 ? item.changeChance + "%" : "");
    setText (textOut[DAMAGE_DICE], item.wephpdam);

    if (item.spellPwr > 0)
      setText (textOut[SPELL_POWER], WizardryData.spells[item.spellPwr - 1]);
    else
      setText (textOut[SPELL_POWER], "");

    setText (textOut[PRICE], item.price);

  }
}
