package com.bytezone.mazewalker;

import com.bytezone.wizardry.origin.WizardryData;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class BasePane extends Pane
// -----------------------------------------------------------------------------------//
{
  WizardryData wizardry;
  Stage stage;

  // ---------------------------------------------------------------------------------//
  public BasePane (WizardryData wizardry, Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;
    this.stage = stage;
  }
}
