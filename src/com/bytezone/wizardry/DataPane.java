package com.bytezone.wizardry;

import java.util.Collection;

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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

// -----------------------------------------------------------------------------------//
public abstract class DataPane extends GridPane
// -----------------------------------------------------------------------------------//
{
  private static final int DEFAULT_ROW_HEIGHT = 25;
  static final Insets defaultInsets = new Insets (15, 10, 12, 10);
  protected int rowHeight;

  // ---------------------------------------------------------------------------------//
  public DataPane ()
  // ---------------------------------------------------------------------------------//
  {
    this (DEFAULT_ROW_HEIGHT);
  }

  // ---------------------------------------------------------------------------------//
  public DataPane (int rowHeight)
  // ---------------------------------------------------------------------------------//
  {
    this.rowHeight = rowHeight;

    setHgap (10);
    setVgap (3);

    setGridLinesVisible (false);
  }

  // ---------------------------------------------------------------------------------//
  public abstract int getRows ();
  // ---------------------------------------------------------------------------------//

  // ---------------------------------------------------------------------------------//
  public abstract int getColumns ();
  // ---------------------------------------------------------------------------------//

  // ---------------------------------------------------------------------------------//
  protected void setLayout (DataPane pane, int column, int row)
  // ---------------------------------------------------------------------------------//
  {
    GridPane.setConstraints (pane, column, row);
    GridPane.setColumnSpan (pane, 1);               // 0 gives an error, >1 is ignored
    GridPane.setRowSpan (pane, pane.getRows ());
  }

  // ---------------------------------------------------------------------------------//
  protected int getRowHeight ()
  // ---------------------------------------------------------------------------------//
  {
    return rowHeight;
  }

  // ---------------------------------------------------------------------------------//
  void setColumnConstraints (int... width)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < width.length; i++)
      getColumnConstraints ().add (new ColumnConstraints (width[i]));
  }

  // ---------------------------------------------------------------------------------//
  void setAllColumnConstraints (int numColumns, int colWidth)
  // ---------------------------------------------------------------------------------//
  {
    ColumnConstraints colConstraints = new ColumnConstraints (colWidth);

    for (int i = 0; i < numColumns; i++)
      getColumnConstraints ().add (colConstraints);
  }

  // ---------------------------------------------------------------------------------//
  void setRowConstraints (int... height)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < height.length; i++)
      getRowConstraints ().add (new RowConstraints (height[i]));
  }

  // ---------------------------------------------------------------------------------//
  void setAllRowConstraints (int numRows, int rowHeight)
  // ---------------------------------------------------------------------------------//
  {
    RowConstraints rowConstraints = new RowConstraints (rowHeight);

    for (int i = 0; i < numRows; i++)
      getRowConstraints ().add (rowConstraints);
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

    getChildren ().addAll (label, comboBox);

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

    getChildren ().add (label);

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
      getChildren ().addAll (label, textOut[i]);

      row++;
    }

    return textOut;
  }

  // ---------------------------------------------------------------------------------//
  TextField[] createTextFields (int totalFields, DataPlacement dataPos)
  // ---------------------------------------------------------------------------------//
  {
    TextField[] textOut = new TextField[totalFields];

    for (int i = 0; i < totalFields; i++)
    {
      textOut[i] = new TextField ();

      GridPane.setConstraints (textOut[i], dataPos.col, dataPos.row + i);
      GridPane.setColumnSpan (textOut[i], dataPos.colSpan);

      textOut[i].setAlignment (dataPos.alignment);
      textOut[i].setEditable (false);
      textOut[i].setFocusTraversable (false);

      getChildren ().add (textOut[i]);
    }

    return textOut;
  }

  // ---------------------------------------------------------------------------------//
  TextField[] createTextFields (String[] labelText, LabelPlacement labelPos, DataLayout dataLayout)
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
      textOut[i].setAlignment (dataLayout.alignment);
      textOut[i].setEditable (false);
      textOut[i].setFocusTraversable (false);

      GridPane.setConstraints (textOut[i], dataLayout.column, row);
      GridPane.setColumnSpan (textOut[i], dataLayout.columnSpan);

      GridPane.setHalignment (label, labelPos.alignment);
      getChildren ().addAll (label, textOut[i]);

      row++;
    }

    dataLayout.column += dataLayout.columnSpan;

    return textOut;
  }

  // ---------------------------------------------------------------------------------//
  void createLabelsVertical (LabelPlacement2 labelPos)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < labelPos.labels.length; i++)
    {
      Label label = new Label (labelPos.labels[i]);

      GridPane.setConstraints (label, labelPos.col, labelPos.row + i);
      GridPane.setColumnSpan (label, labelPos.colSpan);
      GridPane.setHalignment (label, labelPos.alignment);

      getChildren ().add (label);
    }
  }

  // ---------------------------------------------------------------------------------//
  void createLabelsHorizontal (LabelPlacement2 labelPos)
  // ---------------------------------------------------------------------------------//
  {
    int column = labelPos.col;
    for (int i = 0; i < labelPos.labels.length; i++)
    {
      Label label = new Label (labelPos.labels[i]);

      GridPane.setConstraints (label, column, labelPos.row);
      GridPane.setColumnSpan (label, labelPos.colSpan);
      GridPane.setHalignment (label, labelPos.alignment);

      getChildren ().add (label);
      column += labelPos.colSpan;
    }
  }

  // ---------------------------------------------------------------------------------//
  TextField createTextField (DataLayout dataLayout)
  // ---------------------------------------------------------------------------------//
  {
    TextField textField = new TextField ();

    GridPane.setConstraints (textField, dataLayout.column, dataLayout.row);
    GridPane.setColumnSpan (textField, dataLayout.columnSpan);

    textField.setAlignment (dataLayout.alignment);
    textField.setEditable (false);
    textField.setFocusTraversable (false);

    getChildren ().add (textField);

    dataLayout.row++;

    return textField;
  }

  // ---------------------------------------------------------------------------------//
  TextField[] createTextFields (DataLayout dataLayout, Pos alignment)
  // ---------------------------------------------------------------------------------//
  {
    dataLayout.alignment = alignment;
    return this.createTextFields (dataLayout);
  }

  // ---------------------------------------------------------------------------------//
  TextField[] createTextFields (DataLayout dataLayout)
  // ---------------------------------------------------------------------------------//
  {
    TextField[] textOut = new TextField[dataLayout.rows];

    for (int i = 0; i < dataLayout.rows; i++)
    {
      textOut[i] = new TextField ();

      GridPane.setConstraints (textOut[i], dataLayout.column, dataLayout.row + i);
      GridPane.setColumnSpan (textOut[i], dataLayout.columnSpan);

      textOut[i].setAlignment (dataLayout.alignment);
      textOut[i].setEditable (false);
      textOut[i].setFocusTraversable (false);

      getChildren ().add (textOut[i]);
    }

    dataLayout.column += dataLayout.columnSpan;     // next available column

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
      textArea.setPrefRowCount (dataPos.rowSpan);

      GridPane.setHalignment (label, labelPos.alignment);
      getChildren ().addAll (label, textArea);

      row++;
    }

    return textArea;
  }

  // ---------------------------------------------------------------------------------//
  CheckBox[] createCheckBoxes (DataLayout dataLayout, Pos alignment)
  // ---------------------------------------------------------------------------------//
  {
    dataLayout.alignment = alignment;
    return createCheckBoxes (dataLayout);
  }

  // ---------------------------------------------------------------------------------//
  CheckBox[] createCheckBoxes (DataLayout dataLayout)
  // ---------------------------------------------------------------------------------//
  {
    CheckBox[] checkBoxes = new CheckBox[dataLayout.rows];

    for (int i = 0; i < dataLayout.rows; i++)
    {
      checkBoxes[i] = new CheckBox ();

      GridPane.setConstraints (checkBoxes[i], dataLayout.column, dataLayout.row + i);

      checkBoxes[i].setDisable (true);
      checkBoxes[i].setStyle ("-fx-opacity: 1");    // make disabled checkbox look normal
      checkBoxes[i].setFocusTraversable (false);

      GridPane.setHalignment (checkBoxes[i], dataLayout.hpos);
      getChildren ().add (checkBoxes[i]);
    }

    dataLayout.column += dataLayout.columnSpan;     // next available column

    return checkBoxes;
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
      GridPane.setHalignment (checkBoxes[i], HPos.CENTER);
      getChildren ().addAll (label, checkBoxes[i]);

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
      GridPane.setHalignment (checkBoxes[i], HPos.CENTER);

      checkBoxes[i].setDisable (true);
      checkBoxes[i].setStyle ("-fx-opacity: 1");    // make disabled checkbox look normal
      checkBoxes[i].setFocusTraversable (false);

      getChildren ().add (checkBoxes[i]);

      row++;
    }

    return checkBoxes;
  }

  // ---------------------------------------------------------------------------------//
  protected void reset (TextField[] textOut)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < textOut.length; i++)
      setText (textOut[i], "");
  }

  // ---------------------------------------------------------------------------------//
  protected void reset (CheckBox[] checkBoxes)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < checkBoxes.length; i++)
      checkBoxes[i].setSelected (false);
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
  public record LabelPlacement (int col, int row, HPos alignment, int colSpan)
  // ---------------------------------------------------------------------------------//
  {
  }

  // ---------------------------------------------------------------------------------//
  public record LabelPlacement2 (String[] labels, int col, int row, HPos alignment, int colSpan)
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
