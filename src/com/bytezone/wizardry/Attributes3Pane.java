package com.bytezone.wizardry;

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
    setColumnConstraints (90, 50);
    setAllRowConstraints (6, getRowHeight ());           // make all rows the same height

    String[] labelText = { "Strength", "IQ", "Piety", "Vitality", "Agility", "Luck" };

    LabelPlacement lp = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp = new DataPlacement (1, 1, Pos.CENTER_RIGHT, 1);
    textOut = createTextFields (labelText, lp, dp);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return 6;
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
      setText (textOut[i], character.attributes[i]);
  }
}
