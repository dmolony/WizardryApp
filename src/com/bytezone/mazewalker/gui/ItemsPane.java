package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Item;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
  private static final int WEP_HIT_DAM = 7;
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

    Label useLabel = new Label ("Can be used by");
    Label protectionLabel = new Label ("Protection vs");
    Label resistanceLabel = new Label ("Resistance");
    Label purposeLabel = new Label ("Purposed vs");

    GridPane.setConstraints (useLabel, 3, 0);
    GridPane.setConstraints (resistanceLabel, 3, 10);
    GridPane.setConstraints (protectionLabel, 5, 0);
    GridPane.setConstraints (purposeLabel, 7, 0);

    GridPane.setHalignment (useLabel, HPos.RIGHT);
    GridPane.setHalignment (resistanceLabel, HPos.RIGHT);
    GridPane.setHalignment (protectionLabel, HPos.RIGHT);

    GridPane.setColumnSpan (useLabel, 2);
    GridPane.setColumnSpan (resistanceLabel, 2);
    GridPane.setColumnSpan (protectionLabel, 2);
    GridPane.setColumnSpan (purposeLabel, 2);

    gridPane.getChildren ().addAll (useLabel, protectionLabel, resistanceLabel, purposeLabel);

    itemsList.getSelectionModel ().selectedItemProperty ()
        .addListener ( (options, oldValue, newValue) ->
        {
          Item item = newValue;
          if (item != null)
          {
            textOut0[GENERIC_NAME].setText (item.nameUnknown);
            textOut0[ITEM_CLASS].setText ((item.type.toString ()));      // ObjectType
            textOut0[ALIGNMENT].setText (item.alignment.toString ());    // Alignment
            textOut0[CURSED].setText (item.cursed ? "True" : "");        // boolean
            textOut0[CRIT_HIT].setText (item.crithitm ? "True" : "");      // Auto kil
            textOut0[CHANGE_TO].setText (wizardry.getItem (item.changeTo).name);
            textOut0[CHANCE].setText (item.changeChance + "%");
            textOut0[WEP_HIT_DAM].setText (item.wephpdam.toString ());     // Dice

            if (item.spellPwr > 0)
              textOut0[SPELL_POWER].setText (WizardryOrigin.spells[item.spellPwr - 1]);
            else
              textOut0[SPELL_POWER].setText ("");
            textOut0[PRICE].setText (getText (item.price));                // long

            textOut6[ID].setText (getText (item.id));
            textOut6[SPECIAL].setText (getText (item.special));
            textOut6[AC].setText (getText (item.armourClass));
            textOut6[WEP_HIT_MOD].setText (getText (item.wephitmd));
            textOut6[XTRA_SWING].setText (getText (item.xtraSwing));
            textOut6[BOLTAC].setText (getText (item.boltac));
            textOut6[REGEN].setText (getText (item.healPts));

            int protection = item.wepvsty2Flags;
            int purposed = item.wepvstyFlags;
            for (int i = 0; i < WizardryOrigin.monsterClass.length; i++)
            {
              textOut1[i].setText ((protection & 0x01) != 0 ? "Yes" : "");
              textOut2[i].setText ((purposed & 0x01) != 0 ? "Yes" : "");

              protection >>>= 1;
              purposed >>>= 1;
            }

            int resistance = item.wepvsty3Flags;
            for (int i = 0; i < WizardryOrigin.resistance.length; i++)
            {
              textOut3[i].setText ((resistance & 0x01) != 0 ? "Yes" : "");
              resistance >>>= 1;
            }

            int characterClass = item.classUseFlags;
            for (int i = 0; i < WizardryOrigin.characterClass.length; i++)
            {
              textOut4[i].setText ((characterClass & 0x01) != 0 ? "Yes" : "");
              characterClass >>>= 1;
            }
          }
        });

    textOut0 = setOutputFields (label1Text, 0, 1, Pos.CENTER_LEFT, 2);
    textOut6 = setOutputFields (label2Text, 0, 11, Pos.CENTER_RIGHT, 1);

    textOut1 = setOutputFields (WizardryOrigin.monsterClass, 5, 1, Pos.CENTER_LEFT, 1);
    textOut2 = setOutputFields (textOut1.length, 6, 1, Pos.CENTER_LEFT, 1);
    textOut3 = setOutputFields (WizardryOrigin.resistance, 3, 11, Pos.CENTER_LEFT, 1);
    textOut4 = setOutputFields (WizardryOrigin.characterClass, 3, 1, Pos.CENTER_LEFT, 1);
  }
}
