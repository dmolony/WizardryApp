package com.bytezone.wizardry;

import com.bytezone.wizardry.origin.MazeLevel;
import com.bytezone.wizardry.origin.Special;
import com.bytezone.wizardry.origin.WizardryData;

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

    // special squares
    String[] squaresText = new String[16];
    for (int i = 0; i < squaresText.length; i++)
      squaresText[i] = i + "";

    LabelPlacement lp1 = new LabelPlacement (0, 1, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 1, Pos.CENTER_LEFT, 1);
    textOut[0] = createTextFields (squaresText, lp1, dp1);

    textOut[1] = createTextFields (16, new DataPlacement (2, 1, Pos.CENTER_RIGHT, 1));
    textOut[2] = createTextFields (16, new DataPlacement (3, 1, Pos.CENTER_RIGHT, 1));
    textOut[3] = createTextFields (16, new DataPlacement (4, 1, Pos.CENTER_RIGHT, 1));
    textOut[4] = createTextFields (16, new DataPlacement (5, 1, Pos.CENTER_RIGHT, 1));
    textOut[5] = createTextFields (16, new DataPlacement (6, 1, Pos.CENTER_LEFT, 1));
    textOut[6] = createTextFields (16, new DataPlacement (7, 1, Pos.CENTER_RIGHT, 1));
    textOut[7] = createTextFields (16, new DataPlacement (8, 1, Pos.CENTER_LEFT, 1));

    // headings
    createLabel ("Square type", 1, 0, HPos.LEFT, 1);
    createLabel ("Aux 0", 2, 0, HPos.LEFT, 1);
    createLabel ("Aux 1", 3, 0, HPos.LEFT, 1);
    createLabel ("Aux 2", 4, 0, HPos.LEFT, 1);
    createLabel ("Occurs", 5, 0, HPos.LEFT, 1);
    createLabel ("First location", 6, 0, HPos.LEFT, 1);
    createLabel ("Msg #", 7, 0, HPos.LEFT, 1);
    createLabel ("Description", 8, 0, HPos.LEFT, 1);
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
