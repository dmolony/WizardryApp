package com.bytezone.mazewalker.gui;

import java.util.List;

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
public class BasePane extends Pane
// -----------------------------------------------------------------------------------//
{
  GridPane gridPane = new GridPane ();
  WizardryOrigin wizardry;

  // ---------------------------------------------------------------------------------//
  public BasePane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    gridPane.setHgap (12);
    gridPane.setVgap (7);
    gridPane.setPadding (new Insets (15, 10, 12, 10));      // trbl

    getChildren ().add (new BorderPane (gridPane));
  }

  // ---------------------------------------------------------------------------------//
  void setColumnConstraints (int... width)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < width.length; i++)
      gridPane.getColumnConstraints ().add (new ColumnConstraints (width[i]));
  }

  // ---------------------------------------------------------------------------------//
  <T> void setComboBox (String labelText, ComboBox<T> comboBox, List<T> list)
  // ---------------------------------------------------------------------------------//
  {
    Label label = new Label (labelText);

    GridPane.setConstraints (label, 0, 0);
    GridPane.setHalignment (label, HPos.RIGHT);

    GridPane.setConstraints (comboBox, 1, 0);

    gridPane.getChildren ().addAll (label, comboBox);

    comboBox.setItems (FXCollections.observableArrayList (list));
    comboBox.setVisibleRowCount (20);
  }

  // ---------------------------------------------------------------------------------//
  TextField[] setOutputFields (String[] labelText, int col, int row, Pos alignment)
  // ---------------------------------------------------------------------------------//
  {
    TextField[] textOut = new TextField[labelText.length];

    for (int i = 0; i < labelText.length; i++)
    {
      Label label = new Label (labelText[i]);
      textOut[i] = new TextField ();

      GridPane.setConstraints (label, col, row);
      GridPane.setConstraints (textOut[i], col + 1, row);

      textOut[i].setEditable (false);
      textOut[i].setFocusTraversable (false);
      textOut[i].setAlignment (alignment);

      GridPane.setHalignment (label, HPos.RIGHT);
      gridPane.getChildren ().addAll (label, textOut[i]);

      row++;
    }

    return textOut;
  }

  // ---------------------------------------------------------------------------------//
  TextField[] setOutputFields (int totalFields, int col, int row, Pos alignment)
  // ---------------------------------------------------------------------------------//
  {
    TextField[] textOut = new TextField[totalFields];

    for (int i = 0; i < totalFields; i++)
    {
      textOut[i] = new TextField ();

      GridPane.setConstraints (textOut[i], col + 1, row);

      textOut[i].setEditable (false);
      textOut[i].setFocusTraversable (false);
      textOut[i].setAlignment (alignment);

      gridPane.getChildren ().add (textOut[i]);

      row++;
    }

    return textOut;
  }

  // ---------------------------------------------------------------------------------//
  String getText (int value)
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%,15d", value).trim ();
  }

  // ---------------------------------------------------------------------------------//
  String getText (long value)
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%,15d", value).trim ();
  }
}
