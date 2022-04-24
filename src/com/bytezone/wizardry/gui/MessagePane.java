package com.bytezone.wizardry.gui;

import com.bytezone.wizardry.origin.Location;
import com.bytezone.wizardry.origin.Message;
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
  TextArea textOut3;

  // ---------------------------------------------------------------------------------//
  public MessagePane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 120, 300);

    // make all rows the same height
    RowConstraints rowCo = new RowConstraints (25);
    for (int i = 0; i < 20; i++)
      gridPane.getRowConstraints ().add (rowCo);

    String[] label1Text = { "Used" };
    LabelPlacement lp1 = new LabelPlacement (0, 1, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 1, Pos.CENTER_LEFT, 1);
    textOut1 = createTextFields (label1Text, lp1, dp1);

    String[] label2Text = { "Locations" };
    LabelPlacement lp2 = new LabelPlacement (0, 2, HPos.RIGHT, 1);
    DataPlacement dp2 = new DataPlacement (1, 2, Pos.CENTER_LEFT, 2);
    textOut2 = createTextFields (label2Text, lp2, dp2);

    String[] label3Text = { "Text" };
    LabelPlacement lp3 = new LabelPlacement (0, 3, HPos.RIGHT, 1);
    DataPlacement2 dp3 = new DataPlacement2 (1, 3, Pos.CENTER_LEFT, 2, 18);
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
    setText (textOut1[0], message.getTotalLocations ());

    StringBuilder text = new StringBuilder ();
    for (Location location : message.getLocations ())
      text.append (location + ", ");
    if (text.length () > 0)
    {
      text.deleteCharAt (text.length () - 1);
      text.deleteCharAt (text.length () - 1);
    }

    setText (textOut2[0], text.toString ());

    textOut3.setText (message.getText ());
  }
}
