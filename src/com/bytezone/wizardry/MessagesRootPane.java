package com.bytezone.wizardry;

import java.util.List;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.Location;
import com.bytezone.wizardry.data.Message;
import com.bytezone.wizardry.data.Special;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class MessagesRootPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private WizardryData wizardry;

  TextField[] textOut1;
  TextField[] textOut2;
  TextField[] textOut3;

  TextArea textArea;

  // ---------------------------------------------------------------------------------//
  public MessagesRootPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (3, 25);                             // columns, rows

    setColumnConstraints (110, 120, 310);
    setPadding (defaultInsets);
    //    setGridLinesVisible (true);

    String[] labels = { "Used", "Location 1", "Location 2", "Location 3", "Text" };
    createLabelsVertical (labels, 0, 0, HPos.RIGHT);

    textOut1 = createTextFields (new DataLayout (1, 0, 1, Pos.CENTER_LEFT));
    DataLayout dataLayout = new DataLayout (1, 1, 3, Pos.CENTER_LEFT);
    textOut2 = createTextFields (dataLayout);
    textOut3 = createTextFields (dataLayout);

    textArea = createTextArea (new DataLayout (1, 4, 21, Pos.CENTER_LEFT, 2));
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut1);
    reset (textOut2);
    reset (textOut3);

    textArea.setText ("");
  }

  // ---------------------------------------------------------------------------------//
  public void update (Message message)
  // ---------------------------------------------------------------------------------//
  {
    for (TextField textField : textOut2)
      textField.setText ("");
    for (TextField textField : textOut3)
      textField.setText ("");

    List<Location> locations = message.getLocations ();

    setText (textOut1[0], locations.size ());

    for (int i = 0; i < 3; i++)
      if (locations.size () > i)
      {
        setText (textOut2[i], locations.get (i));
        Special special = wizardry.getSpecial (locations.get (i));
        if (special != null)
          setText (textOut3[i], special.getText ());
      }

    textArea.setText (message.getText ());
  }
}
