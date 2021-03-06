package com.bytezone.wizardry;

import com.bytezone.appbase.DataLayout;
import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.EnemyOdds;
import com.bytezone.wizardry.data.MazeLevel;
import com.bytezone.wizardry.data.Monster;
import com.bytezone.wizardry.data.WizardryData;
import com.bytezone.wizardry.graphics.Display;

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
    super (7, 27);                             // columns, rows

    setColumnConstraints (110, 80, 130, 80, 130, 80, 130);
    setPadding (defaultInsets);

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

    // upper block
    createLabelsVertical (labels1, 0, 0, HPos.RIGHT);

    textOut1[0] = createTextFields (new DataLayout (1, 0, 6, Pos.CENTER_RIGHT, 1));
    textOut1[1] = createTextFields (new DataLayout (3, 0, 6, Pos.CENTER_RIGHT, 1));
    textOut1[2] = createTextFields (new DataLayout (5, 0, 6, Pos.CENTER_RIGHT, 1));

    // lower block
    createLabelsVertical (labels2, 0, 6, HPos.RIGHT);

    DataLayout dataLayout = new DataLayout (1, 6, 22, Pos.CENTER_RIGHT, 1);
    for (int i = 0; i < 5; i += 2)
    {
      textOut2[i] = createTextFields (dataLayout, Pos.CENTER_RIGHT);
      textOut2[i + 1] = createTextFields (dataLayout, Pos.CENTER_LEFT);
    }
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    for (int i = 0; i < MAX_GROUPS; i++)
      reset (textOut1[i]);
    for (int i = 0; i < MAX_GROUPS * 2; i++)
      reset (textOut2[i]);

    if (false)
    {
      if (display != null)
        getChildren ().remove (display);

      if (wizardry.getScenarioId () < 3)
      {
        display = new Display (wizardry);
        GridPane.setConstraints (display, 7, 1);
        GridPane.setRowSpan (display, 12);
        getChildren ().add (display);
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
    Monster minMonster = wizardry.getMonster (minEnemy);
    Monster maxMonster = wizardry.getMonster (maxEnemy);

    String minName = minMonster == null ? "?" : minMonster.name;
    String maxName = maxMonster == null ? "?" : maxMonster.name;

    setText (textOut2[index1][index2], minEnemy + " : " + maxEnemy);
    setText (textOut2[index1 + 1][index2], minName);
    setText (textOut2[index1 + 1][index2 + 1], maxName);
  }
}
