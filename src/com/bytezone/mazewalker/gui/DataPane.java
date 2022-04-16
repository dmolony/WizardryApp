package com.bytezone.mazewalker.gui;

import java.util.Collection;

import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class DataPane extends BasePane
// -----------------------------------------------------------------------------------//
{
  GridPane gridPane = new GridPane ();

  // ---------------------------------------------------------------------------------//
  public DataPane (WizardryOrigin wizardry, Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry, stage);

    gridPane.setHgap (12);
    gridPane.setVgap (4);
    gridPane.setPadding (new Insets (15, 10, 12, 10));      // trbl

    getChildren ().add (gridPane);
  }

  // ---------------------------------------------------------------------------------//
  void setColumnConstraints (int... width)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < width.length; i++)
      gridPane.getColumnConstraints ().add (new ColumnConstraints (width[i]));
  }

  // ---------------------------------------------------------------------------------//
  void setRowConstraints (int... height)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < height.length; i++)
      gridPane.getRowConstraints ().add (new RowConstraints (height[i]));
  }

  // ---------------------------------------------------------------------------------//
  <T> void createComboBox (String labelText, ComboBox<T> comboBox, Collection<T> list,
      ChangeListener<T> listener, LabelPlacement labelPos, DataPlacement dataPos)
  // ---------------------------------------------------------------------------------//
  {
    Label label = new Label (labelText);

    GridPane.setConstraints (label, labelPos.col, labelPos.row);
    GridPane.setColumnSpan (label, labelPos.colSpan);
    GridPane.setHalignment (label, labelPos.alignment);

    GridPane.setConstraints (comboBox, dataPos.col, dataPos.row);
    GridPane.setColumnSpan (comboBox, dataPos.colSpan);

    gridPane.getChildren ().addAll (label, comboBox);

    comboBox.setItems (FXCollections.observableArrayList (list));
    comboBox.setVisibleRowCount (20);
    comboBox.getSelectionModel ().selectedItemProperty ().addListener (listener);
  }

  // ---------------------------------------------------------------------------------//
  Label createLabel (String labelText, int col, int row, HPos alignment, int span)
  // ---------------------------------------------------------------------------------//
  {
    Label label = new Label (labelText);

    GridPane.setConstraints (label, col, row);
    GridPane.setHalignment (label, alignment);
    GridPane.setColumnSpan (label, span);
    gridPane.getChildren ().add (label);

    return label;
  }

  // ---------------------------------------------------------------------------------//
  TextField[] createTextFields (String[] labelText, LabelPlacement labelPos, DataPlacement dataPos)
  // ---------------------------------------------------------------------------------//
  {
    TextField[] textOut = new TextField[labelText.length];
    int row = labelPos.row;

    for (int i = 0; i < labelText.length; i++)
    {
      Label label = new Label (labelText[i]);
      GridPane.setConstraints (label, labelPos.col, row);
      GridPane.setColumnSpan (label, labelPos.colSpan);

      textOut[i] = new TextField ();
      textOut[i].setAlignment (dataPos.alignment);
      textOut[i].setEditable (false);
      textOut[i].setFocusTraversable (false);

      GridPane.setConstraints (textOut[i], dataPos.col, row);
      GridPane.setColumnSpan (textOut[i], dataPos.colSpan);

      GridPane.setHalignment (label, labelPos.alignment);
      gridPane.getChildren ().addAll (label, textOut[i]);

      row++;
    }

    return textOut;
  }

  // ---------------------------------------------------------------------------------//
  TextField[] createTextFields (int totalFields, DataPlacement dataPos)
  // ---------------------------------------------------------------------------------//
  {
    TextField[] textOut = new TextField[totalFields];
    int row = dataPos.row;

    for (int i = 0; i < totalFields; i++)
    {
      textOut[i] = new TextField ();

      GridPane.setConstraints (textOut[i], dataPos.col, row);
      GridPane.setColumnSpan (textOut[i], dataPos.colSpan);

      textOut[i].setAlignment (dataPos.alignment);
      textOut[i].setEditable (false);
      textOut[i].setFocusTraversable (false);

      gridPane.getChildren ().add (textOut[i]);

      row++;
    }

    return textOut;
  }

  // ---------------------------------------------------------------------------------//
  TextArea createTextArea (String[] labelText, LabelPlacement labelPos, DataPlacement2 dataPos)
  // ---------------------------------------------------------------------------------//
  {
    TextArea textArea = new TextArea ();
    int row = labelPos.row;

    for (int i = 0; i < labelText.length; i++)
    {
      Label label = new Label (labelText[i]);
      GridPane.setConstraints (label, labelPos.col, row);
      GridPane.setColumnSpan (label, labelPos.colSpan);

      GridPane.setConstraints (textArea, dataPos.col, row);
      GridPane.setColumnSpan (textArea, dataPos.colSpan);
      GridPane.setRowSpan (textArea, dataPos.rowSpan);

      textArea.setEditable (false);
      textArea.setFocusTraversable (false);

      GridPane.setHalignment (label, labelPos.alignment);
      gridPane.getChildren ().addAll (label, textArea);

      row++;
    }

    return textArea;
  }

  // ---------------------------------------------------------------------------------//
  CheckBox[] createCheckBoxes (String[] labelText, int col, int row)
  // ---------------------------------------------------------------------------------//
  {
    CheckBox[] checkBoxes = new CheckBox[labelText.length];

    for (int i = 0; i < labelText.length; i++)
    {
      Label label = new Label (labelText[i]);
      checkBoxes[i] = new CheckBox ();

      GridPane.setConstraints (label, col, row);
      GridPane.setConstraints (checkBoxes[i], col + 1, row);

      checkBoxes[i].setDisable (true);
      checkBoxes[i].setStyle ("-fx-opacity: 1");    // make disabled checkbox look normal
      checkBoxes[i].setFocusTraversable (false);

      GridPane.setHalignment (label, HPos.RIGHT);
      gridPane.getChildren ().addAll (label, checkBoxes[i]);

      row++;
    }

    return checkBoxes;
  }

  // ---------------------------------------------------------------------------------//
  CheckBox[] createCheckBoxes (int total, int col, int row)
  // ---------------------------------------------------------------------------------//
  {
    CheckBox[] checkBoxes = new CheckBox[total];

    for (int i = 0; i < total; i++)
    {
      checkBoxes[i] = new CheckBox ();

      GridPane.setConstraints (checkBoxes[i], col, row);

      checkBoxes[i].setDisable (true);
      checkBoxes[i].setStyle ("-fx-opacity: 1");    // make disabled checkbox look normal
      checkBoxes[i].setFocusTraversable (false);

      gridPane.getChildren ().add (checkBoxes[i]);

      row++;
    }

    return checkBoxes;
  }

  // ---------------------------------------------------------------------------------//
  void setText (TextField textField, Object object)
  // ---------------------------------------------------------------------------------//
  {
    if (object == null)
      textField.setText ("** Error **");
    else
      textField.setText (object.toString ());
  }

  // ---------------------------------------------------------------------------------//
  void setText (TextField textField, boolean value)
  // ---------------------------------------------------------------------------------//
  {
    textField.setText (value ? "True" : "False");
  }

  // ---------------------------------------------------------------------------------//
  void setTextYN (TextField textField, boolean value)
  // ---------------------------------------------------------------------------------//
  {
    textField.setText (value ? "Yes" : "");
  }

  // ---------------------------------------------------------------------------------//
  void setText (TextField textField, String text)
  // ---------------------------------------------------------------------------------//
  {
    textField.setText (text);
  }

  // ---------------------------------------------------------------------------------//
  void setText (TextField textField, int value)
  // ---------------------------------------------------------------------------------//
  {
    textField.setText (getText (value));
  }

  // ---------------------------------------------------------------------------------//
  void setText (TextField textField, long value)
  // ---------------------------------------------------------------------------------//
  {
    textField.setText (getText (value));
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

  // ---------------------------------------------------------------------------------//
  protected void keyPressed (KeyEvent keyEvent)
  // ---------------------------------------------------------------------------------//
  {
    if (keyEvent.getCode () == KeyCode.ESCAPE)
      stage.hide ();

    if (keyEvent.getCode () == KeyCode.W && keyEvent.isMetaDown ())
      stage.hide ();
  }

  // ---------------------------------------------------------------------------------//
  public void show ()
  // ---------------------------------------------------------------------------------//
  {
    stage.show ();
    stage.toFront ();
  }

  // ---------------------------------------------------------------------------------//
  public record LabelPlacement (int col, int row, HPos alignment, int colSpan)
  // ---------------------------------------------------------------------------------//
  {
  }

  // ---------------------------------------------------------------------------------//
  public record DataPlacement (int col, int row, Pos alignment, int colSpan)
  // ---------------------------------------------------------------------------------//
  {
  }

  // ---------------------------------------------------------------------------------//
  public record DataPlacement2 (int col, int row, Pos alignment, int colSpan, int rowSpan)
  // ---------------------------------------------------------------------------------//
  {
  }
}
