package com.bytezone.wizardry.gui;

import com.bytezone.wizardry.origin.MazeLevel;
import com.bytezone.wizardry.origin.Special;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

//-----------------------------------------------------------------------------------//
public class SpecialsPane extends DataPane
//-----------------------------------------------------------------------------------//
{
  TextField[] textOut1;
  TextField[] textOut2;
  TextField[] textOut3;
  TextField[] textOut4;
  TextField[] textOut5;
  TextField[] textOut6;
  TextField[] textOut7;
  TextField[] textOut8;

  private WizardryOrigin wizardry;

  // ---------------------------------------------------------------------------------//
  public SpecialsPane ()
  // ---------------------------------------------------------------------------------//
  {
    int width = 62;
    setColumnConstraints (110, 120, width, width, width, width, 80, 40, 320);

    // special squares
    String[] squaresText = new String[16];
    for (int i = 0; i < squaresText.length; i++)
      squaresText[i] = i + "";

    LabelPlacement lp1 = new LabelPlacement (0, 1, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 1, Pos.CENTER_LEFT, 1);
    textOut1 = createTextFields (squaresText, lp1, dp1);

    textOut2 = createTextFields (16, new DataPlacement (2, 1, Pos.CENTER_RIGHT, 1));
    textOut3 = createTextFields (16, new DataPlacement (3, 1, Pos.CENTER_RIGHT, 1));
    textOut4 = createTextFields (16, new DataPlacement (4, 1, Pos.CENTER_RIGHT, 1));
    textOut5 = createTextFields (16, new DataPlacement (5, 1, Pos.CENTER_RIGHT, 1));
    textOut7 = createTextFields (16, new DataPlacement (7, 1, Pos.CENTER_RIGHT, 1));
    textOut6 = createTextFields (16, new DataPlacement (6, 1, Pos.CENTER_LEFT, 1));
    textOut8 = createTextFields (16, new DataPlacement (8, 1, Pos.CENTER_LEFT, 1));

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
  public void setWizardry (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;
  }

  // ---------------------------------------------------------------------------------//
  void update (MazeLevel mazeLevel)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < 16; i++)
    {
      Special special = mazeLevel.getSpecial (i);

      setText (textOut1[i], special.square);
      setText (textOut2[i], special.aux[0]);
      setText (textOut3[i], special.aux[1]);
      setText (textOut4[i], special.aux[2]);
      setText (textOut5[i], special.locations.size ());
      setText (textOut7[i], special.isMessage () ? special.aux[1] : "");
      setText (textOut6[i], special.getLocationText ());
      setText (textOut8[i], special.getText ());
    }
  }
}
