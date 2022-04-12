package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Message;
import com.bytezone.wizardry.origin.Messages;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class MessagesPane extends BasePane
// -----------------------------------------------------------------------------------//
{
  TextArea textOut;

  // ---------------------------------------------------------------------------------//
  public MessagesPane (WizardryOrigin wizardry, Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry, stage);

    setColumnConstraints (110, 120, 300);

    // make all rows the same height
    RowConstraints rowCo = new RowConstraints (25);
    for (int i = 0; i < 20; i++)
      gridPane.getRowConstraints ().add (rowCo);

    Messages messages = wizardry.getMessages ();

    LabelPlacement lp0 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp0 = new DataPlacement (1, 0, Pos.CENTER_LEFT, 1);
    ComboBox<Message> messagesList = new ComboBox<> ();
    setComboBox ("Message", messagesList, messages.getMessages (),
        (options, oldValue, newValue) -> update (newValue), lp0, dp0);

    String[] label1Text = { "Text" };
    LabelPlacement lp1 = new LabelPlacement (0, 1, HPos.RIGHT, 1);
    DataPlacement2 dp1 = new DataPlacement2 (1, 1, Pos.CENTER_LEFT, 2, 16);
    textOut = createOutputField (label1Text, lp1, dp1);

    messagesList.getSelectionModel ().select (0);
  }

  // ---------------------------------------------------------------------------------//
  private void update (Message message)
  // ---------------------------------------------------------------------------------//
  {
    textOut.setText (message.getText ());
  }
}
