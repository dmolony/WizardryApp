package com.bytezone.wizardry.gui;

import java.util.List;

import com.bytezone.wizardry.origin.Location;
import com.bytezone.wizardry.origin.Message;
import com.bytezone.wizardry.origin.Special;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.RowConstraints;

// -----------------------------------------------------------------------------------//
public class MessagePane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private WizardryOrigin wizardry;

  TextField[] textOut1;
  TextField[] textOut2;
  TextField[] textOut4;
  TextArea textOut3;

  // ---------------------------------------------------------------------------------//
  public MessagePane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 120, 300);

    // make all rows the same height
    RowConstraints rowCo = new RowConstraints (30);
    for (int i = 0; i < 20; i++)
      gridPane.getRowConstraints ().add (rowCo);

    String[] label1Text = { "Used" };
    LabelPlacement lp1 = new LabelPlacement (0, 1, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 1, Pos.CENTER_LEFT, 1);
    textOut1 = createTextFields (label1Text, lp1, dp1);

    String[] label2Text = { "Location 1", "Location 2", "Location 3" };
    LabelPlacement lp2 = new LabelPlacement (0, 2, HPos.RIGHT, 1);
    DataPlacement dp2 = new DataPlacement (1, 2, Pos.CENTER_LEFT, 1);
    textOut2 = createTextFields (label2Text, lp2, dp2);

    textOut4 = createTextFields (3, new DataPlacement (2, 2, Pos.CENTER_LEFT, 1));

    String[] label3Text = { "Text" };
    LabelPlacement lp3 = new LabelPlacement (0, 5, HPos.RIGHT, 1);
    DataPlacement2 dp3 = new DataPlacement2 (1, 6, Pos.CENTER_LEFT, 2, 20);
    textOut3 = createTextArea (label3Text, lp3, dp3);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;
  }

  // ---------------------------------------------------------------------------------//
  public void update (Message message)
  // ---------------------------------------------------------------------------------//
  {
    for (TextField textField : textOut2)
      textField.setText ("");
    for (TextField textField : textOut4)
      textField.setText ("");

    List<Location> locations = message.getLocations ();

    setText (textOut1[0], locations.size ());

    if (locations.size () > 0)
    {
      setText (textOut2[0], locations.get (0));
      Special special = wizardry.getSpecial (locations.get (0));
      if (special != null)
        setText (textOut4[0], special.getText ());
    }

    if (locations.size () > 1)
    {
      setText (textOut2[1], locations.get (1));
      Special special = wizardry.getSpecial (locations.get (1));
      if (special != null)
        setText (textOut4[1], special.getText ());
    }

    if (locations.size () > 2)
    {
      setText (textOut2[2], locations.get (2));
      Special special = wizardry.getSpecial (locations.get (2));
      if (special != null)
        setText (textOut4[2], special.getText ());
    }

    textOut3.setText (message.getText ());
  }
}
