package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.MazeLevel;
import com.bytezone.wizardry.origin.Special;
import com.bytezone.wizardry.origin.WizardryOrigin;
import com.bytezone.wizardry.origin.WizardryOrigin.Square;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class SpecialsPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  ComboBox<MazeLevel> mazeLevelList = new ComboBox<> ();

  TextField[] textOut1;
  TextField[] textOut2;
  TextField[] textOut3;
  TextField[] textOut4;
  TextField[] textOut5;
  TextField[] textOut6;
  TextField[] textOut7;

  // ---------------------------------------------------------------------------------//
  public SpecialsPane (WizardryOrigin wizardry, Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry, stage);

    int width = 65;
    setColumnConstraints (110, 120, width, width, width, width, width, 320);

    LabelPlacement lp0 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp0 = new DataPlacement (1, 0, Pos.CENTER_LEFT, 1);
    createComboBox ("Maze Level", mazeLevelList, wizardry.getMazeLevels (),
        (options, oldValue, newValue) -> update (newValue), lp0, dp0);

    // special squares
    String[] squaresText = new String[16];
    for (int i = 0; i < squaresText.length; i++)
      squaresText[i] = i + "";

    LabelPlacement lp1 = new LabelPlacement (0, 5, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 5, Pos.CENTER_LEFT, 1);
    textOut1 = createTextFields (squaresText, lp1, dp1);

    textOut2 = createTextFields (16, new DataPlacement (2, 5, Pos.CENTER_RIGHT, 1));
    textOut3 = createTextFields (16, new DataPlacement (3, 5, Pos.CENTER_RIGHT, 1));
    textOut4 = createTextFields (16, new DataPlacement (4, 5, Pos.CENTER_RIGHT, 1));
    textOut5 = createTextFields (16, new DataPlacement (5, 5, Pos.CENTER_RIGHT, 1));
    textOut6 = createTextFields (16, new DataPlacement (6, 5, Pos.CENTER_RIGHT, 1));
    textOut7 = createTextFields (16, new DataPlacement (7, 5, Pos.CENTER_LEFT, 1));

    // headings
    createLabel ("Square", 1, 4, HPos.LEFT, 1);
    createLabel ("Aux 0", 2, 4, HPos.LEFT, 1);
    createLabel ("Aux 1", 3, 4, HPos.LEFT, 1);
    createLabel ("Aux 2", 4, 4, HPos.LEFT, 1);
    createLabel ("Total", 5, 4, HPos.LEFT, 1);
    createLabel ("Msg #", 6, 4, HPos.LEFT, 1);
    createLabel ("Description", 7, 4, HPos.LEFT, 1);

    mazeLevelList.getSelectionModel ().select (0);
  }

  // ---------------------------------------------------------------------------------//
  private void update (MazeLevel mazeLevel)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < 16; i++)
    {
      Special special = mazeLevel.getSpecial (i);

      setText (textOut1[i], special.square);
      setText (textOut2[i], special.aux[0]);
      setText (textOut3[i], special.aux[1]);

      //      if (special.is (Square.SCNMSG))
      //        setText (textOut4[i], Special.auxTypes[special.aux[2]]);
      //      else
      setText (textOut4[i], special.aux[2]);

      setText (textOut5[i], special.locations.size ());
      setText (textOut7[i], special.getText ());

      if (special.square == Square.SCNMSG && special.aux[2] <= 13)
        setText (textOut6[i], special.aux[1]);
      else
        setText (textOut6[i], "");
    }
  }
}
