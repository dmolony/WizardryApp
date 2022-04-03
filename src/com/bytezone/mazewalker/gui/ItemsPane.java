package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Item;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

// -----------------------------------------------------------------------------------//
public class ItemsPane extends Pane
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

  String[] labelText = { "Generic name", "Kind", "Alignment", "Cursed", "Auto kill", "Change to",
      "Chance", "Wep Hit Damage", "Spell", "Id", "Special #", "AC", "Wep Hit Mod", "Extra swing",
      "In store", "Regeneration", "Value" };

  Label[] labels = new Label[labelText.length];
  TextField[] textOut = new TextField[labelText.length];
  ComboBox<Item> itemsList = new ComboBox<> ();

  Label[] labels1 = new Label[WizardryOrigin.monsterClass.length];
  TextField[] textOut1 = new TextField[WizardryOrigin.monsterClass.length];
  TextField[] textOut2 = new TextField[WizardryOrigin.monsterClass.length];

  Label[] labels3 = new Label[WizardryOrigin.resistance.length];
  TextField[] textOut3 = new TextField[WizardryOrigin.resistance.length];

  Label[] labels4 = new Label[WizardryOrigin.characterClass.length];
  TextField[] textOut4 = new TextField[WizardryOrigin.characterClass.length];

  GridPane gridPane = new GridPane ();
  WizardryOrigin wizardry;

  // ---------------------------------------------------------------------------------//
  public ItemsPane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    build ();

    gridPane.getColumnConstraints ().add (new ColumnConstraints (110));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (64));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (90));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (110));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (50));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (110));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (50));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (50));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (20));

    gridPane.setHgap (12);
    gridPane.setVgap (7);
    gridPane.setPadding (new Insets (15, 10, 12, 10));      // trbl

    getChildren ().add (new BorderPane (gridPane));
  }

  // ---------------------------------------------------------------------------------//
  private void build ()
  // ---------------------------------------------------------------------------------//
  {
    Label itemLabel = new Label ("Item");
    Label useLabel = new Label ("Can be used by");
    Label protectionLabel = new Label ("Protection vs");
    Label resistanceLabel = new Label ("Resistance");
    Label purposeLabel = new Label ("Purposed vs");

    GridPane.setConstraints (itemLabel, 0, 0);
    GridPane.setConstraints (itemsList, 1, 0);
    GridPane.setConstraints (useLabel, 3, 0);
    GridPane.setConstraints (protectionLabel, 5, 0);
    GridPane.setConstraints (purposeLabel, 7, 0);
    GridPane.setConstraints (resistanceLabel, 3, 10);

    GridPane.setHalignment (itemLabel, HPos.RIGHT);
    GridPane.setHalignment (protectionLabel, HPos.RIGHT);
    GridPane.setHalignment (useLabel, HPos.RIGHT);
    GridPane.setHalignment (resistanceLabel, HPos.RIGHT);

    GridPane.setColumnSpan (itemsList, 2);
    GridPane.setColumnSpan (protectionLabel, 2);
    GridPane.setColumnSpan (purposeLabel, 2);
    GridPane.setColumnSpan (useLabel, 2);
    GridPane.setColumnSpan (resistanceLabel, 2);

    gridPane.getChildren ().addAll (itemLabel, itemsList, useLabel, protectionLabel,
        resistanceLabel, purposeLabel);

    itemsList.setItems (FXCollections.observableArrayList (wizardry.getItems ()));
    itemsList.setVisibleRowCount (20);

    itemsList.getSelectionModel ().selectedItemProperty ()
        .addListener ( (options, oldValue, newValue) ->
        {
          Item item = newValue;
          if (item != null)
          {
            textOut[GENERIC_NAME].setText (item.nameUnknown);
            textOut[ITEM_CLASS].setText ((item.type.toString ()));      // ObjectType
            textOut[ALIGNMENT].setText (item.alignment.toString ());    // Alignment
            textOut[CURSED].setText (item.cursed ? "True" : "");       // boolean
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
            textOut[CRIT_HIT].setText (item.crithitm ? "True" : "");
            textOut[PRICE].setText (getText (item.price));              // long
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

    for (int i = 0; i < labels.length; i++)
    {
      row++;

      labels[i] = new Label (labelText[i]);
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

      GridPane.setConstraints (labels[i], col, row);
      GridPane.setConstraints (textOut[i], col + 1, row);

      textOut[i].setEditable (false);
      textOut[i].setFocusTraversable (false);

      GridPane.setHalignment (labels[i], HPos.RIGHT);

      gridPane.getChildren ().addAll (labels[i], textOut[i]);
    }

    col = 3;
    row = 1;

    for (int i = 0; i < WizardryOrigin.characterClass.length; i++)
    {
      labels4[i] = new Label (WizardryOrigin.characterClass[i]);
      textOut4[i] = new TextField ();

      GridPane.setConstraints (labels4[i], col, row);
      GridPane.setConstraints (textOut4[i], col + 1, row);

      textOut4[i].setEditable (false);
      textOut4[i].setFocusTraversable (false);

      GridPane.setHalignment (labels4[i], HPos.RIGHT);
      gridPane.getChildren ().addAll (labels4[i], textOut4[i]);

      row++;
    }

    col = 5;
    row = 1;

    for (int i = 0; i < WizardryOrigin.monsterClass.length; i++)
    {
      labels1[i] = new Label (WizardryOrigin.monsterClass[i]);
      textOut1[i] = new TextField ();
      textOut2[i] = new TextField ();

      GridPane.setConstraints (labels1[i], col, row);
      GridPane.setConstraints (textOut1[i], col + 1, row);
      GridPane.setConstraints (textOut2[i], col + 2, row);

      textOut1[i].setEditable (false);
      textOut1[i].setFocusTraversable (false);
      textOut2[i].setFocusTraversable (false);

      GridPane.setHalignment (labels1[i], HPos.RIGHT);
      gridPane.getChildren ().addAll (labels1[i], textOut1[i], textOut2[i]);

      row++;
    }

    col = 3;
    row = 11;

    for (int i = 0; i < WizardryOrigin.resistance.length; i++)
    {
      labels3[i] = new Label (WizardryOrigin.resistance[i]);
      textOut3[i] = new TextField ();

      GridPane.setConstraints (labels3[i], col, row);
      GridPane.setConstraints (textOut3[i], col + 1, row);

      textOut3[i].setEditable (false);
      textOut3[i].setFocusTraversable (false);

      GridPane.setHalignment (labels3[i], HPos.RIGHT);
      gridPane.getChildren ().addAll (labels3[i], textOut3[i]);

      row++;
    }
  }

  // ---------------------------------------------------------------------------------//
  private String getText (int value)
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%,15d", value).trim ();
  }

  // ---------------------------------------------------------------------------------//
  private String getText (long value)
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%,15d", value).trim ();
  }
}
