package com.bytezone.wizardry;

import com.bytezone.wizardry.graphics.Display;
import com.bytezone.wizardry.origin.EnemyOdds;
import com.bytezone.wizardry.origin.MazeLevel;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

//-----------------------------------------------------------------------------------//
public class EncounterPane extends DataPane
//-----------------------------------------------------------------------------------//
{
  private static final int MAX_GROUPS = 3;

  ComboBox<MazeLevel> mazeLevelList = new ComboBox<> ();

  TextField[][] textOut1 = new TextField[MAX_GROUPS][];
  TextField[][] textOut2 = new TextField[MAX_GROUPS * 2][];

  Display display;
  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public EncounterPane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 80, 130, 80, 130, 80, 130, 570);

    String[] labels1 = { "Group odds", "Minimum", "Range size", "Extra range odds", "Extra ranges",
        "Range offset" };

    String[] labels2 = new String[22];
    labels2[0] = "Base range";
    labels2[1] = "Odds";
    for (int i = 1; i < 11; i++)
    {
      labels2[i * 2] = "Extra range " + i;
      labels2[i * 2 + 1] = labels2[1];
    }

    LabelPlacement lp1 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 0, Pos.CENTER_RIGHT, 1);
    textOut1[0] = createTextFields (labels1, lp1, dp1);

    textOut1[1] = createTextFields (6, new DataPlacement (3, 0, Pos.CENTER_RIGHT, 1));
    textOut1[2] = createTextFields (6, new DataPlacement (5, 0, Pos.CENTER_RIGHT, 1));

    LabelPlacement lp2 = new LabelPlacement (0, 6, HPos.RIGHT, 1);
    DataPlacement dp2 = new DataPlacement (1, 6, Pos.CENTER_RIGHT, 1);
    textOut2[0] = createTextFields (labels2, lp2, dp2);

    textOut2[1] = createTextFields (22, new DataPlacement (2, 6, Pos.CENTER_LEFT, 1));
    textOut2[2] = createTextFields (22, new DataPlacement (3, 6, Pos.CENTER_RIGHT, 1));
    textOut2[3] = createTextFields (22, new DataPlacement (4, 6, Pos.CENTER_LEFT, 1));
    textOut2[4] = createTextFields (22, new DataPlacement (5, 6, Pos.CENTER_RIGHT, 1));
    textOut2[5] = createTextFields (22, new DataPlacement (6, 6, Pos.CENTER_LEFT, 1));
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    if (false)
    {
      if (display != null)
        gridPane.getChildren ().remove (display);

      if (wizardry.getScenarioId () < 3)
      {
        display = new Display (wizardry);
        GridPane.setConstraints (display, 7, 1);
        GridPane.setRowSpan (display, 12);
        gridPane.getChildren ().add (display);
      }
    }
  }

  // ---------------------------------------------------------------------------------//
  void update (MazeLevel mazeLevel)
  // ---------------------------------------------------------------------------------//
  {
    EnemyOdds[] enemyOdds = mazeLevel.getEnemyOdds ();
    double[] groupOdds = { 75, 18.75, 6.25 };

    for (int i = 0; i < MAX_GROUPS; i++)
    {
      int minEnemy = enemyOdds[i].minEnemy;
      int rangeSize = enemyOdds[i].rangeSize;
      int extraRangeOffset = enemyOdds[i].extraRangeOffset;
      int totExtraRanges = enemyOdds[i].totExtraRanges;
      int extraRangeOdds = enemyOdds[i].extraRangeOdds;

      double[] oddsTable = enemyOdds[i].getOdds ();

      for (int row = 2; row < 22; row++)
      {
        setText (textOut2[i * 2][row], "");
        setText (textOut2[i * 2 + 1][row], "");
      }

      setText (textOut1[i][0], groupOdds[i] + "%");
      setText (textOut1[i][1], minEnemy);
      setText (textOut1[i][2], rangeSize);
      setText (textOut1[i][3], extraRangeOdds + "%");
      setText (textOut1[i][4], totExtraRanges);
      setText (textOut1[i][5], extraRangeOffset);

      int maxEnemy = minEnemy + rangeSize - 1;
      setMinMax (i * 2, 0, minEnemy, maxEnemy);
      setText (textOut2[i * 2][1], String.format ("%5.3f%%", oddsTable[0] * 100));

      for (int row = 1; row <= totExtraRanges; row++)
      {
        minEnemy += extraRangeOffset;
        maxEnemy += extraRangeOffset;
        setMinMax (i * 2, row * 2, minEnemy, maxEnemy);
        setText (textOut2[i * 2][row * 2 + 1], String.format ("%5.3f%%", oddsTable[row] * 100));
      }
    }

    if (display != null)
      display.update (mazeLevel);
  }

  // ---------------------------------------------------------------------------------//
  private void setMinMax (int index1, int index2, int minEnemy, int maxEnemy)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut2[index1][index2], minEnemy + " : " + maxEnemy);
    setText (textOut2[index1 + 1][index2], wizardry.getMonster (minEnemy).name);
    setText (textOut2[index1 + 1][index2 + 1], wizardry.getMonster (maxEnemy).name);
  }
}
