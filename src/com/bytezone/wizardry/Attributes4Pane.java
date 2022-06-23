package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class Attributes4Pane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private TextField[] textOut;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public Attributes4Pane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (90, 50);
    setAllRowConstraints (getRows (), getRowHeight ());  // make all rows the same height

    String[] labelText = { "Death", "Wand", "Breath", "Petrify", "Spell" };
    assert getRows () == labelText.length;

    createLabelsVertical (new LabelPlacement2 (labelText, 0, 0, HPos.RIGHT, 1));
    textOut = createTextFields (new DataLayout (1, 0, getRows (), Pos.CENTER_RIGHT));
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return 5;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getColumns ()
  // ---------------------------------------------------------------------------------//
  {
    return 2;
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
      setText (textOut[i], character.saveVs[i]);
  }
}
