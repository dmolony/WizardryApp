package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Item;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

// -----------------------------------------------------------------------------------//
public class ItemsPane extends BasePane
// -----------------------------------------------------------------------------------//
{
  private static final int GENERIC_NAME = 0;
  private static final int ITEM_CLASS = 1;
  private static final int ALIGNMENT = 2;
  private static final int CURSED = 3;
  private static final int CRIT_HIT = 4;
  private static final int CHANGE_TO = 5;
  private static final int CHANCE = 6;
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

  String[] label1Text = { "Generic name", "Kind", "Alignment", "Cursed", "Auto kill", "Decay to",
      "Decay odds", "Damage dice", "Spell", "Value" };
  String[] label2Text =
      { "Id", "Special #", "AC", "To hit +", "# swings", "In store", "Regeneration" };

  ComboBox<Item> itemsList = new ComboBox<> ();

  TextField[] textOut0 = new TextField[label1Text.length];
  TextField[] textOut6 = new TextField[label2Text.length];

  TextField[] textOut1;
  TextField[] textOut2;
  TextField[] textOut3;
  TextField[] textOut4;

  // ---------------------------------------------------------------------------------//
  public ItemsPane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry);

    setColumnConstraints (110, 64, 90, 110, 50, 110, 50, 50, 20);

    setComboBox ("Item", itemsList, wizardry.getItems ());
    GridPane.setColumnSpan (itemsList, 2);

    setLabel ("Can be used by", 3, 0, HPos.RIGHT, 2);
    setLabel ("Protection vs", 5, 0, HPos.RIGHT, 2);
    setLabel ("Resistance", 3, 10, HPos.RIGHT, 2);
    setLabel ("Purposed vs", 7, 0, HPos.LEFT, 2);

    itemsList.getSelectionModel ().selectedItemProperty ()
        .addListener ( (options, oldValue, newValue) -> update (newValue));

    textOut0 = createOutputFields (label1Text, 0, 1, Pos.CENTER_LEFT, 2);
    textOut6 = createOutputFields (label2Text, 0, 11, Pos.CENTER_RIGHT, 1);

    textOut1 = createOutputFields (WizardryOrigin.monsterClass, 5, 1, Pos.CENTER_LEFT, 1);
    textOut2 = createOutputFields (textOut1.length, 6, 1, Pos.CENTER_LEFT, 1);
    textOut3 = createOutputFields (WizardryOrigin.resistance, 3, 11, Pos.CENTER_LEFT, 1);
    textOut4 = createOutputFields (WizardryOrigin.characterClass, 3, 1, Pos.CENTER_LEFT, 1);
  }

  // ---------------------------------------------------------------------------------//
  private void update (Item item)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut0[GENERIC_NAME], item.nameUnknown);
    setText (textOut0[ITEM_CLASS], item.type);
    setText (textOut0[ALIGNMENT], item.alignment);
    setText (textOut0[CURSED], item.cursed);
    setText (textOut0[CRIT_HIT], item.crithitm);                    // Auto kill
    setText (textOut0[CHANGE_TO], wizardry.getItem (item.changeTo).name);
    setText (textOut0[CHANCE], item.changeChance + "%");
    setText (textOut0[DAMAGE_DICE], item.wephpdam);

    if (item.spellPwr > 0)
      setText (textOut0[SPELL_POWER], WizardryOrigin.spells[item.spellPwr - 1]);
    else
      setText (textOut0[SPELL_POWER], "");

    setText (textOut0[PRICE], item.price);

    setText (textOut6[ID], item.id);
    setText (textOut6[SPECIAL], item.special);
    setText (textOut6[AC], item.armourClass);
    setText (textOut6[WEP_HIT_MOD], item.wephitmd);
    setText (textOut6[XTRA_SWING], item.xtraSwing);
    setText (textOut6[BOLTAC], item.boltac);
    setText (textOut6[REGEN], item.healPts);

    int protection = item.wepvsty2Flags;
    int purposed = item.wepvstyFlags;
    for (int i = 0; i < WizardryOrigin.monsterClass.length; i++)
    {
      setTextYN (textOut1[i], (protection & 0x01) != 0);
      setTextYN (textOut2[i], (purposed & 0x01) != 0);

      protection >>>= 1;
      purposed >>>= 1;
    }

    int resistance = item.wepvsty3Flags;
    for (int i = 0; i < WizardryOrigin.resistance.length; i++)
    {
      setTextYN (textOut3[i], (resistance & 0x01) != 0);
      resistance >>>= 1;
    }

    int characterClass = item.classUseFlags;
    for (int i = 0; i < WizardryOrigin.characterClass.length; i++)
    {
      setTextYN (textOut4[i], (characterClass & 0x01) != 0);
      characterClass >>>= 1;
    }
  }
}
