package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

// -----------------------------------------------------------------------------------//
public class CharactersPane extends Pane
// -----------------------------------------------------------------------------------//
{
  GridPane gridPane = new GridPane ();
  WizardryOrigin wizardry;

  // ---------------------------------------------------------------------------------//
  public CharactersPane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;
  }
}
