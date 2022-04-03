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
  private static final int ID = 9;
  private static final int SPECIAL = 10;
  private static final int AC = 11;
  private static final int WEP_HIT_MOD = 12;
  private static final int XTRA_SWING = 13;
  private static final int BOLTAC = 14;
  private static final int REGEN = 15;
  private static final int PRICE = 16;

  String[] labelText = { "Generic name", "Kind", "Alignment", "Cursed", "Auto kill", "Decay to",
      "Decay odds", "Damage dice", "Spell", "Id", "Special #", "AC", "To hit +", "# swings",
      "In store", "Regeneration", "Value" };

  ComboBox<Item> itemsList = new ComboBox<> ();

  TextField[] textOut = new TextField[labelText.length];
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
            textOut[GENERIC_NAME].setText (item.nameUnknown);
            textOut[ITEM_CLASS].setText ((item.type.toString ()));      // ObjectType
            textOut[ALIGNMENT].setText (item.alignment.toString ());    // Alignment
            textOut[CURSED].setText (item.cursed ? "True" : "");        // boolean
            textOut[SPECIAL].setText (getText (item.special));
            textOut[CHANGE_TO].setText (wizardry.getItem (item.changeTo).name);
            textOut[CHANCE].setText (item.changeChance + "%");

            if (item.spellPwr > 0)
              textOut[SPELL_POWER].setText (WizardryOrigin.spells[item.spellPwr - 1]);
            else
              textOut[SPELL_POWER].setText ("");

            textOut[ID].setText (getText (item.id));
            textOut[AC].setText (getText (item.armourClass));
            textOut[WEP_HIT_MOD].setText (getText (item.wephitmd));
            textOut[WEP_HIT_DAM].setText (item.wephpdam.toString ());     // Dice
            textOut[XTRA_SWING].setText (getText (item.xtraSwing));
            textOut[CRIT_HIT].setText (item.crithitm ? "True" : "");      // Auto kil
            textOut[PRICE].setText (getText (item.price));                // long
            textOut[REGEN].setText (getText (item.healPts));
            textOut[BOLTAC].setText (getText (item.boltac));

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

    int col = 0;
    int row = 0;

    for (int i = 0; i < textOut.length; i++)
    {
      row++;

      Label label = new Label (labelText[i]);
      textOut[i] = new TextField ();

      if (i <= 8)
      {
        textOut[i].setAlignment (Pos.CENTER_LEFT);
        GridPane.setColumnSpan (textOut[i], 2);
      }
      else if (i == 16)
      {
        textOut[i].setAlignment (Pos.CENTER_LEFT);
        GridPane.setColumnSpan (textOut[i], 2);
      }
      else
        textOut[i].setAlignment (Pos.CENTER_RIGHT);

      GridPane.setConstraints (label, col, row);
      GridPane.setConstraints (textOut[i], col + 1, row);

      textOut[i].setEditable (false);
      textOut[i].setFocusTraversable (false);

      GridPane.setHalignment (label, HPos.RIGHT);

      gridPane.getChildren ().addAll (label, textOut[i]);
    }

    textOut1 = setOutputFields (WizardryOrigin.monsterClass, 5, 1, Pos.CENTER_LEFT);
    textOut2 = setOutputFields (textOut1.length, 6, 1, Pos.CENTER_LEFT);
    textOut3 = setOutputFields (WizardryOrigin.resistance, 3, 11, Pos.CENTER_LEFT);
    textOut4 = setOutputFields (WizardryOrigin.characterClass, 3, 1, Pos.CENTER_LEFT);
  }
}
