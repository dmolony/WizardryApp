package com.bytezone.wizardry;

import com.bytezone.appbase.BorderedDataPane;

import javafx.geometry.HPos;
import javafx.geometry.Insets;

public class EncountersHeader extends BorderedDataPane
{
  // ---------------------------------------------------------------------------------//
  public EncountersHeader (int groupNo)
  // ---------------------------------------------------------------------------------//
  {
    super (1, 1);                              // columns, rows

    setColumnConstraints (228);
    String[] labels = { "Monster Group " + groupNo };
    createLabelsHorizontal (labels, 0, 0, HPos.CENTER);
    setPadding (new Insets (2, 7, 0, 7));
  }
}
