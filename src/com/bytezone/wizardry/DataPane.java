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
  static final Insets defaultInsets = new Insets (15, 10, 15, 10);      // TRBL
  private int rowHeight = 25;
  private int rows;
  private int columns;

  // ---------------------------------------------------------------------------------//
  public DataPane (int rows, int columns)
  // ---------------------------------------------------------------------------------//
  {
    init (rows, columns);
  }

  // ---------------------------------------------------------------------------------//
  public DataPane (int rows, int columns, int rowHeight)
  // ---------------------------------------------------------------------------------//
  {
    this.rowHeight = rowHeight;
    init (rows, columns);
  }

  // ---------------------------------------------------------------------------------//
  private void init (int rows, int columns)
  // ---------------------------------------------------------------------------------//
  {
    this.rows = rows;
    this.columns = columns;

    setHgap (8);
    setVgap (3);

    setGridLinesVisible (false);

    setAllRowConstraints (rows, rowHeight);     // make all rows the same height
  }

  // ---------------------------------------------------------------------------------//
  public int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return rows;
  }

  // ---------------------------------------------------------------------------------//
  public int getColumns ()
  // ---------------------------------------------------------------------------------//
  {
    return columns;
  }

  // ---------------------------------------------------------------------------------//
  protected int getRowHeight ()
  // ---------------------------------------------------------------------------------//
  {
    return rowHeight;
  }

  // ---------------------------------------------------------------------------------//
  protected void setLayout (DataPane pane, int column, int row)
  // ---------------------------------------------------------------------------------//
  {
    GridPane.setConstraints (pane, column, row);
    GridPane.setColumnSpan (pane, 1);               // 0 gives an error, >1 is ignored
    GridPane.setRowSpan (pane, pane.getRows ());
  }

  // ---------------------------------------------------------------------------------//
  void setColumnConstraints (int... width)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < width.length; i++)
      getColumnConstraints ().add (new ColumnConstraints (width[i]));
  }

  // ---------------------------------------------------------------------------------//
  void setAllColumnConstraints (int colWidth)
  // ---------------------------------------------------------------------------------//
  {
    ColumnConstraints colConstraints = new ColumnConstraints (colWidth);

    for (int i = 0; i < getColumns (); i++)
      getColumnConstraints ().add (colConstraints);
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
  protected <T> ComboBox<T> createComboBox (Collection<T> list, ChangeListener<T> listener,
      DataLayout dataLayout)
  // ---------------------------------------------------------------------------------//
  {
    ComboBox<T> comboBox = new ComboBox<T> ();

    GridPane.setConstraints (comboBox, dataLayout.column, dataLayout.row);
    GridPane.setColumnSpan (comboBox, dataLayout.columnSpan);

    getChildren ().add (comboBox);

    comboBox.setItems (FXCollections.observableArrayList (list));
    comboBox.setVisibleRowCount (20);
    comboBox.getSelectionModel ().selectedItemProperty ().addListener (listener);

    return comboBox;
  }

  // ---------------------------------------------------------------------------------//
  protected Label createLabel (String labelText, int col, int row, HPos alignment, int span)
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
  protected void createLabelsVertical (LabelPlacement labelPos)
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
  protected void createLabelsHorizontal (LabelPlacement labelPos)
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
  protected TextField createTextField (DataLayout dataLayout)
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
  protected TextField[] createTextFields (DataLayout dataLayout, Pos alignment)
  // ---------------------------------------------------------------------------------//
  {
    dataLayout.alignment = alignment;
    return this.createTextFields (dataLayout);
  }

  // ---------------------------------------------------------------------------------//
  protected TextField[] createTextFields (DataLayout dataLayout)
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
  protected TextArea createTextArea (DataLayout dataLayout)
  // ---------------------------------------------------------------------------------//
  {
    TextArea textArea = new TextArea ();

    GridPane.setConstraints (textArea, dataLayout.column, dataLayout.row);
    GridPane.setColumnSpan (textArea, dataLayout.columnSpan);
    GridPane.setRowSpan (textArea, dataLayout.rows);

    textArea.setEditable (false);
    textArea.setFocusTraversable (false);
    textArea.setPrefRowCount (dataLayout.rows);

    getChildren ().add (textArea);

    return textArea;
  }

  // ---------------------------------------------------------------------------------//
  protected CheckBox[] createCheckBoxes (DataLayout dataLayout, Pos alignment)
  // ---------------------------------------------------------------------------------//
  {
    dataLayout.alignment = alignment;
    return createCheckBoxes (dataLayout);
  }

  // ---------------------------------------------------------------------------------//
  protected CheckBox[] createCheckBoxes (DataLayout dataLayout)
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
  protected CheckBox[] createCheckBoxes (String[] labelText, int col, int row)
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
  protected CheckBox[] createCheckBoxes (int total, int col, int row)
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
  protected void setText (TextField textField, Object object)
  // ---------------------------------------------------------------------------------//
  {
    if (object == null)
      textField.setText ("** Error **");
    else
      textField.setText (object.toString ());
  }

  // ---------------------------------------------------------------------------------//
  protected void setText (TextField textField, boolean value)
  // ---------------------------------------------------------------------------------//
  {
    textField.setText (value ? "True" : "False");
  }

  // ---------------------------------------------------------------------------------//
  protected void setTextYN (TextField textField, boolean value)
  // ---------------------------------------------------------------------------------//
  {
    textField.setText (value ? "Yes" : "");
  }

  // ---------------------------------------------------------------------------------//
  protected void setText (TextField textField, String text)
  // ---------------------------------------------------------------------------------//
  {
    textField.setText (text);
  }

  // ---------------------------------------------------------------------------------//
  protected void setText (TextField textField, int value)
  // ---------------------------------------------------------------------------------//
  {
    textField.setText (getText (value));
  }

  // ---------------------------------------------------------------------------------//
  protected void setText (TextField textField, long value)
  // ---------------------------------------------------------------------------------//
  {
    textField.setText (getText (value));
  }

  // ---------------------------------------------------------------------------------//
  protected String getText (int value)
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%,15d", value).trim ();
  }

  // ---------------------------------------------------------------------------------//
  protected String getText (long value)
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%,15d", value).trim ();
  }

  // ---------------------------------------------------------------------------------//
  public record LabelPlacement (String[] labels, int col, int row, HPos alignment, int colSpan)
  // ---------------------------------------------------------------------------------//
  {
  }
}
