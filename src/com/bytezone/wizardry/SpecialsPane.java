package com.bytezone.wizardry;

import com.bytezone.wizardry.data.MazeLevel;
import com.bytezone.wizardry.data.Special;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

//-----------------------------------------------------------------------------------//
public class SpecialsPane extends DataPane
//-----------------------------------------------------------------------------------//
{
  TextField[][] textOut = new TextField[8][];

  // ---------------------------------------------------------------------------------//
  public SpecialsPane ()
  // ---------------------------------------------------------------------------------//
  {
    int width = 63;
    setColumnConstraints (110, 120, width, width, width, width, 80, 40, 320);
    setAllRowConstraints (getRows (), getRowHeight ());     // make all rows the same height
    setPadding (defaultInsets);

    String[] headings = { "Square type", "Aux 0", "Aux 1", "Aux 2", "Occurs", "First location",
        "Msg #", "Description" };
    createLabelsHorizontal (new LabelPlacement (headings, 1, 0, HPos.CENTER, 1));

    String[] squaresText = new String[16];
    for (int i = 0; i < squaresText.length; i++)
      squaresText[i] = i + "";
    createLabelsVertical (new LabelPlacement (squaresText, 0, 1, HPos.RIGHT, 1));

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
  @Override
  public int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return 17;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getColumns ()
  // ---------------------------------------------------------------------------------//
  {
    return 9;
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
