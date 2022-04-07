package com.bytezone.mazewalker.gui;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.wizardry.origin.Encounter;
import com.bytezone.wizardry.origin.EnemyCalc;
import com.bytezone.wizardry.origin.MazeLevel;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class EncountersPane extends BasePane
// -----------------------------------------------------------------------------------//
{
  List<EnemyCalc[]> encounters = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  public EncountersPane (WizardryOrigin wizardry, Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry, stage);

    for (MazeLevel mazeLevel : wizardry.getMazeLevels ())
      encounters.add (mazeLevel.getEnemyCalc ());
  }

  // ---------------------------------------------------------------------------------//
  private void update (Encounter encounter)
  // ---------------------------------------------------------------------------------//
  {

  }
}
