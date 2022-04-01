package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Item;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
  private static final int SPECIAL = 4;
  private static final int CHANGE_TO = 5;
  private static final int CHANCE = 6;
  private static final int PRICE = 7;

  String[] labelText = { "Generic name", "Item class", "Alignment", "Cursed", "Special",
      "Change to", "Chance", "Price" };

  Label[] labels = new Label[labelText.length];
  TextField[] textOut = new TextField[labelText.length];
  ComboBox<Item> items = new ComboBox<> ();

  GridPane gridPane = new GridPane ();
  WizardryOrigin wizardry;

  // ---------------------------------------------------------------------------------//
  public ItemsPane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    build ();

    gridPane.getColumnConstraints ().add (new ColumnConstraints (110));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (230));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (110));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (65));

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
    GridPane.setConstraints (itemLabel, 0, 0);
    GridPane.setConstraints (items, 1, 0);
    GridPane.setHalignment (itemLabel, HPos.RIGHT);
    gridPane.getChildren ().addAll (itemLabel, items);

    ObservableList<Item> list = FXCollections.observableArrayList ();
    list.addAll (wizardry.getItems ());

    items.setItems (list);
    items.setVisibleRowCount (30);

    items.getSelectionModel ().selectedItemProperty ()
        .addListener ( (options, oldValue, newValue) ->
        {
          Item item = newValue;
          if (item != null)
          {
            textOut[GENERIC_NAME].setText (item.nameUnknown);
            textOut[ITEM_CLASS].setText ((item.type.toString ()));
            textOut[ALIGNMENT].setText (item.alignment.toString ());
            textOut[CURSED].setText (item.cursed ? "Yes" : "No");
            textOut[SPECIAL].setText (getText (item.special));
            textOut[CHANGE_TO].setText (getText (item.changeTo));
            textOut[CHANCE].setText (item.changeChance + "%");
            textOut[PRICE].setText (getText (item.price));
          }
        });

    int col = 0;
    int row = 0;
    for (int i = 0; i < labels.length; i++)
    {
      row++;
      if (i == 8)
      {
        col += 2;
        row = 0;
      }

      labels[i] = new Label (labelText[i]);
      textOut[i] = new TextField ();

      GridPane.setConstraints (labels[i], col, row);
      GridPane.setConstraints (textOut[i], col + 1, row);

      textOut[i].setEditable (false);
      textOut[i].setFocusTraversable (false);

      if (i <= 7)
        textOut[i].setAlignment (Pos.CENTER_LEFT);
      else
        textOut[i].setAlignment (Pos.CENTER_RIGHT);

      GridPane.setHalignment (labels[i], HPos.RIGHT);

      gridPane.getChildren ().addAll (labels[i], textOut[i]);
    }
  }

  // ---------------------------------------------------------------------------------//
  private String getText (int value)
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%,7d", value);
  }
}
