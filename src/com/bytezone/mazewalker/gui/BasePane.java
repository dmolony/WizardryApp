package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class BasePane extends Pane
// -----------------------------------------------------------------------------------//
{
  WizardryOrigin wizardry;
  Stage stage;

  // ---------------------------------------------------------------------------------//
  public BasePane (WizardryOrigin wizardry, Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;
    this.stage = stage;
  }
}
