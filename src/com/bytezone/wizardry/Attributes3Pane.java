package com.bytezone.wizardry;

import com.bytezone.wizardry.origin.Character;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

public class Attributes3Pane extends DataPane
{
  TextField[] textOut1;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public Attributes3Pane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (90, 50);
    gridPane.setPadding (new Insets (0, 0, 0, 0));      // trbl

    String[] attributesText = { "Strength", "IQ", "Piety", "Vitality", "Agility", "Luck" };

    // attributes
    LabelPlacement lp2 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp2 = new DataPlacement (1, 1, Pos.CENTER_RIGHT, 1);
    textOut1 = createTextFields (attributesText, lp2, dp2);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;
    reset (textOut1);
  }

  // ---------------------------------------------------------------------------------//
  void update (Character character)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < textOut1.length; i++)
      setText (textOut1[i], character.attributes[i]);
  }
}
