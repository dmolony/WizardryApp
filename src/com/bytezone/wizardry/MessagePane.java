package com.bytezone.wizardry;

import java.util.List;

import com.bytezone.wizardry.data.Location;
import com.bytezone.wizardry.data.Message;
import com.bytezone.wizardry.data.Special;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class MessagePane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private WizardryData wizardry;

  TextField[] textOut1;
  TextField[] textOut2;
  TextField[] textOut4;

  TextArea textOut3;

  // ---------------------------------------------------------------------------------//
  public MessagePane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 120, 310);
    setPadding (defaultInsets);

    String[] label1Text = { "Used" };
    LabelPlacement lp1 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 0, Pos.CENTER_LEFT, 1);
    textOut1 = createTextFields (label1Text, lp1, dp1);

    String[] label2Text = { "Location 1", "Location 2", "Location 3" };
    LabelPlacement lp2 = new LabelPlacement (0, 1, HPos.RIGHT, 1);
    DataPlacement dp2 = new DataPlacement (1, 1, Pos.CENTER_LEFT, 1);
    textOut2 = createTextFields (label2Text, lp2, dp2);

    textOut4 = createTextFields (3, new DataPlacement (2, 1, Pos.CENTER_LEFT, 1));

    String[] label3Text = { "Text" };
    LabelPlacement lp3 = new LabelPlacement (0, 4, HPos.RIGHT, 1);
    DataPlacement2 dp3 = new DataPlacement2 (1, 4, Pos.CENTER_LEFT, 2, 18);
    textOut3 = createTextArea (label3Text, lp3, dp3);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut1);
    reset (textOut2);
    reset (textOut4);

    textOut3.setText ("");
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

    for (int i = 0; i < 3; i++)
      if (locations.size () > i)
      {
        setText (textOut2[i], locations.get (i));
        Special special = wizardry.getSpecial (locations.get (i));
        if (special != null)
          setText (textOut4[i], special.getText ());
      }

    textOut3.setText (message.getText ());
  }
}
