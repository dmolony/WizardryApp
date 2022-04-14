package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.EnemyCalc;
import com.bytezone.wizardry.origin.MazeLevel;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class EncountersPane extends BasePane
// -----------------------------------------------------------------------------------//
{
  ComboBox<MazeLevel> mazeLevelList = new ComboBox<> ();

  TextField[][] textOut1 = new TextField[3][];
  TextField[][] textOut2 = new TextField[3][];
  //  TextField[] textOut3;

  // ---------------------------------------------------------------------------------//
  public EncountersPane (WizardryOrigin wizardry, Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry, stage);

    setColumnConstraints (110, 50, 80, 50, 80, 50, 80);

    LabelPlacement lp0 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp0 = new DataPlacement (1, 0, Pos.CENTER_LEFT, 3);
    createComboBox ("Maze Level", mazeLevelList, wizardry.getMazeLevels (),
        (options, oldValue, newValue) -> update (newValue), lp0, dp0);

    String[] labels1 = { "minenemy", "multwors", "wors01", "range0n", "percwors" };
    String[] labels2 =
        { "base range", "monster from", "monster to", "ext range", "monster from", "monster to" };

    // headings
    createLabel ("Group 1", 1, 4, HPos.LEFT, 2);
    createLabel ("Group 2", 3, 4, HPos.LEFT, 2);
    createLabel ("Group 3", 5, 4, HPos.LEFT, 2);

    LabelPlacement lp1 = new LabelPlacement (0, 5, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 5, Pos.CENTER_RIGHT, 1);
    textOut1[0] = createTextFields (labels1, lp1, dp1);
    textOut1[1] = createTextFields (5, new DataPlacement (3, 5, Pos.CENTER_RIGHT, 1));
    textOut1[2] = createTextFields (5, new DataPlacement (5, 5, Pos.CENTER_RIGHT, 1));

    LabelPlacement lp2 = new LabelPlacement (0, 11, HPos.RIGHT, 1);
    DataPlacement dp2 = new DataPlacement (1, 11, Pos.CENTER_LEFT, 2);
    textOut2[0] = createTextFields (labels2, lp2, dp2);
    textOut2[1] = createTextFields (6, new DataPlacement (3, 11, Pos.CENTER_LEFT, 2));
    textOut2[2] = createTextFields (6, new DataPlacement (5, 11, Pos.CENTER_LEFT, 2));

    mazeLevelList.getSelectionModel ().select (0);
  }

  // ---------------------------------------------------------------------------------//
  private void update (MazeLevel mazeLevel)
  // ---------------------------------------------------------------------------------//
  {
    EnemyCalc[] enemyCalc = mazeLevel.getEnemyCalc ();

    for (int i = 0; i < 3; i++)
    {
      int minEnemy = enemyCalc[i].minEnemy;
      int multWors = enemyCalc[i].multWors;
      int worse01 = enemyCalc[i].worse01;
      int range0n = enemyCalc[i].range0n;
      int percWors = enemyCalc[i].percWors;

      setText (textOut1[i][0], minEnemy);
      setText (textOut1[i][1], multWors);
      setText (textOut1[i][2], worse01);
      setText (textOut1[i][3], range0n);
      setText (textOut1[i][4], percWors);

      int last = minEnemy + range0n - 1;

      setText (textOut2[i][0], minEnemy + " : " + last);
      setText (textOut2[i][1], wizardry.getMonster (minEnemy).name);
      setText (textOut2[i][2], wizardry.getMonster (last).name);

      int max = multWors * worse01;
      if (max > 0)
      {
        setText (textOut2[i][3], (minEnemy + range0n) + " : " + (last + max));
        setText (textOut2[i][4], wizardry.getMonster (minEnemy + range0n).name);
        setText (textOut2[i][5], wizardry.getMonster (last + max).name);
      }
      else
      {
        setText (textOut2[i][3], "");
        setText (textOut2[i][4], "");
        setText (textOut2[i][5], "");
      }
    }
  }
}
