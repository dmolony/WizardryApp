package com.bytezone.mazewalker;

import com.bytezone.wizardry.origin.EnemyOdds;
import com.bytezone.wizardry.origin.MazeLevel;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class EncountersPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private static final int MAX_GROUPS = 3;

  ComboBox<MazeLevel> mazeLevelList = new ComboBox<> ();

  TextField[][] textOut1 = new TextField[MAX_GROUPS][];
  TextField[][] textOut2 = new TextField[MAX_GROUPS][];

  Display display;

  // ---------------------------------------------------------------------------------//
  public EncountersPane (WizardryData wizardry, Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry, stage);

    setColumnConstraints (110, 50, 80, 50, 80, 50, 80, 30, 400);

    LabelPlacement lp0 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp0 = new DataPlacement (1, 0, Pos.CENTER_LEFT, 3);
    createComboBox ("Maze Level", mazeLevelList, wizardry.getMazeLevels (),
        (options, oldValue, newValue) -> update (newValue), lp0, dp0);

    // make all rows the same height
    RowConstraints rowCo = new RowConstraints (25);
    for (int i = 0; i < 30; i++)
      gridPane.getRowConstraints ().add (rowCo);

    String[] labels1 =
        { "Minimum", "Range size", "Extra range chance", "Extra ranges", "Extra range size" };
    String[] labels2 =
        { "Base range", "Monster from", "Monster to", "Max range", "Monster from", "Monster to" };

    // headings
    createLabel ("75%", 1, 1, HPos.LEFT, 2);
    createLabel ("18.75%", 3, 1, HPos.LEFT, 2);
    createLabel ("6.25%", 5, 1, HPos.LEFT, 2);

    LabelPlacement lp1 = new LabelPlacement (0, 2, HPos.RIGHT, 1);
    DataPlacement dp1 = new DataPlacement (1, 2, Pos.CENTER_RIGHT, 1);
    textOut1[0] = createTextFields (labels1, lp1, dp1);

    textOut1[1] = createTextFields (5, new DataPlacement (3, 2, Pos.CENTER_RIGHT, 1));
    textOut1[2] = createTextFields (5, new DataPlacement (5, 2, Pos.CENTER_RIGHT, 1));

    LabelPlacement lp2 = new LabelPlacement (0, 7, HPos.RIGHT, 1);
    DataPlacement dp2 = new DataPlacement (1, 7, Pos.CENTER_LEFT, 2);
    textOut2[0] = createTextFields (labels2, lp2, dp2);

    textOut2[1] = createTextFields (6, new DataPlacement (3, 7, Pos.CENTER_LEFT, 2));
    textOut2[2] = createTextFields (6, new DataPlacement (5, 7, Pos.CENTER_LEFT, 2));

    if (wizardry.getScenarioId () < 3)
    {
      display = new Display (wizardry);
      GridPane.setConstraints (display, 8, 0);
      GridPane.setRowSpan (display, 14);
      gridPane.getChildren ().add (display);
    }

    mazeLevelList.getSelectionModel ().select (0);
  }

  // ---------------------------------------------------------------------------------//
  private void update (MazeLevel mazeLevel)
  // ---------------------------------------------------------------------------------//
  {
    EnemyOdds[] enemyOdds = mazeLevel.getEnemyOdds ();

    for (int i = 0; i < MAX_GROUPS; i++)
    {
      int minEnemy = enemyOdds[i].minEnemy;
      int rangeSize = enemyOdds[i].rangeSize;
      int extraRangeOffset = enemyOdds[i].extraRangeOffset;
      int totExtraRanges = enemyOdds[i].totExtraRanges;
      int extraRangeOdds = enemyOdds[i].extraRangeOdds;

      setText (textOut1[i][0], minEnemy);
      setText (textOut1[i][1], rangeSize);
      setText (textOut1[i][2], extraRangeOdds + "%");
      setText (textOut1[i][3], totExtraRanges);
      setText (textOut1[i][4], extraRangeOffset);

      int maxEnemy = minEnemy + rangeSize - 1;
      setMinMax (i, 0, minEnemy, maxEnemy);

      if (extraRangeOdds > 0)
      {
        minEnemy += extraRangeOffset * totExtraRanges;
        maxEnemy += extraRangeOffset * totExtraRanges;
        setMinMax (i, 3, minEnemy, maxEnemy);
      }
      else
      {
        setText (textOut2[i][3], "");
        setText (textOut2[i][4], "");
        setText (textOut2[i][5], "");
      }
    }

    if (display != null)
      display.update (mazeLevel);
  }

  // ---------------------------------------------------------------------------------//
  private void setMinMax (int index1, int index2, int minEnemy, int maxEnemy)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut2[index1][index2++], minEnemy + " : " + maxEnemy);
    setText (textOut2[index1][index2++], wizardry.getMonster (minEnemy).name);
    setText (textOut2[index1][index2++], wizardry.getMonster (maxEnemy).name);
  }
}
