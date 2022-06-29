package com.bytezone.wizardry;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class Attributes3Pane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private TextField[] textOut;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public Attributes3Pane ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 6);                             // columns, rows

    setColumnConstraints (90, 50);

    String[] labelText = { "Strength", "IQ", "Piety", "Vitality", "Agility", "Luck" };
    assert getRows () == labelText.length;

    createLabelsVertical (labelText, 0, 0, HPos.RIGHT);
    textOut = createTextFields (new DataLayout (1, 0, getRows (), Pos.CENTER_RIGHT));
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut);
  }

  // ---------------------------------------------------------------------------------//
  void update (Character character)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < textOut.length; i++)
      setText (textOut[i], character.attributes[i]);
  }
}
