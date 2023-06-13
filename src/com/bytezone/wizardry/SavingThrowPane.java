package com.bytezone.wizardry;

import com.bytezone.appbase.BorderedDataPane;
import com.bytezone.appbase.DataLayout;
import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class SavingThrowPane extends BorderedDataPane
// -----------------------------------------------------------------------------------//
{
  private TextField[] textOut;

  // ---------------------------------------------------------------------------------//
  public SavingThrowPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (2, 5);                             // columns, rows

    setColumnConstraints (50, 50);

    String[] labelText = { "Death", "Wand", "Breath", "Petrify", "Spell" };
    assert getRows () == labelText.length;

    createLabelsVertical (labelText, 0, 0, HPos.RIGHT);
    textOut =
        createTextFields (new DataLayout (1, 0, getRows (), Pos.CENTER_RIGHT, false));
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
    for (int i = 0; i < textOut.length; i++)
      setText (textOut[i], character.saveVs[i]);
  }
}
