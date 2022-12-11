package com.bytezone.wizardry;

import com.bytezone.appbase.BorderedDataPane;
import com.bytezone.appbase.DataLayout;
import com.bytezone.wizardry.data.MazeLevel;
import com.bytezone.wizardry.data.Special;
import com.bytezone.wizardry.data.WizardryData;
import com.bytezone.wizardry.data.WizardryData.Square;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class SpecialsTablePane extends BorderedDataPane
// -----------------------------------------------------------------------------------//
{
  private static final int WIDTH = 63;

  TextField[][] textOut = new TextField[8][];

  // ---------------------------------------------------------------------------------//
  public SpecialsTablePane ()
  // ---------------------------------------------------------------------------------//
  {
    super (9, 17);                              // columns, rows

    setColumnConstraints (30, 85, WIDTH, WIDTH, WIDTH, WIDTH, 80, 40, 320);

    String[] headings = { "Square type", "Aux 0", "Aux 1", "Aux 2", "Occurs", "First location",
        "Msg #", "Description" };

    String[] squaresText = new String[16];
    for (int i = 0; i < squaresText.length; i++)
      squaresText[i] = i + "";

    createLabelsHorizontal (headings, 1, 0, HPos.CENTER);
    createLabelsVertical (squaresText, 0, 1, HPos.RIGHT);

    DataLayout dataLayout = new DataLayout (1, 1, 16, Pos.CENTER_LEFT);

    textOut[0] = createTextFields (dataLayout);
    textOut[1] = createTextFields (dataLayout, Pos.CENTER_RIGHT);
    textOut[2] = createTextFields (dataLayout);
    textOut[3] = createTextFields (dataLayout);
    textOut[4] = createTextFields (dataLayout);
    textOut[5] = createTextFields (dataLayout, Pos.CENTER_LEFT);
    textOut[6] = createTextFields (dataLayout, Pos.CENTER_RIGHT);
    textOut[7] = createTextFields (dataLayout, Pos.CENTER_LEFT);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < textOut.length; i++)
      reset (textOut[i]);
  }

  // ---------------------------------------------------------------------------------//
  void update (MazeLevel mazeLevel)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < 16; i++)
    {
      Special special = mazeLevel.getSpecial (i);

      if (special.square == Square.NORMAL && special.locations.size () == 0)
        for (int j = 0; j < textOut.length; j++)
          setText (textOut[j][i], "");
      else
      {
        setText (textOut[0][i], special.square);
        setText (textOut[1][i], special.aux[0]);
        setText (textOut[2][i], special.aux[1]);
        setText (textOut[3][i], special.aux[2]);
        setText (textOut[4][i], special.locations.size ());
        setText (textOut[5][i], special.getLocationText ());
        setText (textOut[6][i], special.isMessage () ? special.aux[1] : "");
        setText (textOut[7][i], special.getText ());
      }
    }
  }
}
