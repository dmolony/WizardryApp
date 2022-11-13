package com.bytezone.wizardry;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class Attributes6Pane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private TextField[] textOut;

  // ---------------------------------------------------------------------------------//
  public Attributes6Pane ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 3);                             // columns, rows

    setColumnConstraints (90, 85);

    String[] labelText = { "Wep vs 1", "Wep vs 2", "Wep vs 3" };
    assert getRows () == labelText.length;

    createLabelsVertical (labelText, 0, 0, HPos.RIGHT);
    textOut = createTextFields (new DataLayout (1, 0, getRows (), Pos.CENTER_RIGHT));
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    reset (textOut);
  }

  // ---------------------------------------------------------------------------------//
  void update (Character character)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut[0], character.wepVs1);
    setText (textOut[1], character.wepVs2);
    setText (textOut[2], character.wepVs3);
  }
}
