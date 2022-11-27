package com.bytezone.wizardry;

import com.bytezone.appbase.BorderedDataPane;
import com.bytezone.appbase.DataLayout;
import com.bytezone.wizardry.data.EnemyOdds;
import com.bytezone.wizardry.data.MazeLevel;
import com.bytezone.wizardry.data.Monster;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;

public class EncountersTable extends BorderedDataPane
{
  TextField[] textOut1;
  TextField[] textOut2;

  private WizardryData wizardry;
  private final int groupNo;
  private static double[] groupOdds = { 75, 18.75, 6.25 };

  // ---------------------------------------------------------------------------------//
  public EncountersTable (int groupNo)
  // ---------------------------------------------------------------------------------//
  {
    super (1, 28);                              // columns, rows

    this.groupNo = groupNo;

    setColumnConstraints (80, 139);

    textOut1 = createTextFields (new DataLayout (0, 0, 28, Pos.CENTER_RIGHT, 1));
    textOut2 = createTextFields (new DataLayout (1, 6, 22, Pos.CENTER_LEFT, 1));
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut1);
    reset (textOut2);
  }

  // ---------------------------------------------------------------------------------//
  void update (MazeLevel mazeLevel)
  // ---------------------------------------------------------------------------------//
  {

    EnemyOdds[] enemyOdds = mazeLevel.getEnemyOdds ();

    int minEnemy = enemyOdds[groupNo].minEnemy;
    int rangeSize = enemyOdds[groupNo].rangeSize;
    int extraRangeOffset = enemyOdds[groupNo].extraRangeOffset;
    int totExtraRanges = enemyOdds[groupNo].totExtraRanges;
    int extraRangeOdds = enemyOdds[groupNo].extraRangeOdds;

    double[] oddsTable = enemyOdds[groupNo].getOdds ();

    for (int row = 2; row < 22; row++)        // skip base
    {
      setText (textOut1[row + 6], "");
      setText (textOut2[row], "");
    }

    setText (textOut1[0], groupOdds[groupNo] + "%");
    setText (textOut1[1], minEnemy);
    setText (textOut1[2], rangeSize);
    setText (textOut1[3], extraRangeOdds + "%");
    setText (textOut1[4], totExtraRanges);
    setText (textOut1[5], extraRangeOffset);

    int maxEnemy = minEnemy + rangeSize - 1;
    setMinMax (0, minEnemy, maxEnemy);
    setText (textOut1[7], String.format ("%5.3f%%", oddsTable[0] * 100));

    for (int row = 1; row <= totExtraRanges; row++)
    {
      minEnemy += extraRangeOffset;
      maxEnemy += extraRangeOffset;
      setMinMax (row * 2, minEnemy, maxEnemy);
      setText (textOut1[row * 2 + 7], String.format ("%5.3f%%", oddsTable[row] * 100));
    }
  }

  // ---------------------------------------------------------------------------------//
  private void setMinMax (int index, int minEnemy, int maxEnemy)
  // ---------------------------------------------------------------------------------//
  {
    Monster minMonster = wizardry.getMonster (minEnemy);
    Monster maxMonster = wizardry.getMonster (maxEnemy);

    String minName = minMonster == null ? "?" : minMonster.name;
    String maxName = maxMonster == null ? "?" : maxMonster.name;

    setText (textOut1[index + 6], minEnemy + " : " + maxEnemy);
    setText (textOut2[index], minName);
    setText (textOut2[index + 1], maxName);
  }
}
