package com.bytezone.wizardry;

import com.bytezone.appbase.BorderedDataPane;

import javafx.geometry.HPos;

// -----------------------------------------------------------------------------------//
public class EncountersLabelsPane extends BorderedDataPane
// -----------------------------------------------------------------------------------//
{
  // ---------------------------------------------------------------------------------//
  public EncountersLabelsPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (1, 28);                              // columns, rows

    setColumnConstraints (111);

    String[] labels = { "Group odds", "Minimum", "Range size", "Extra range odds", "Extra ranges",
        "Range offset", "Base range", "Odds", "", "", "", "", "", "", "", "", "", "", "", "", "",
        "", "", "", "", "", "", "" };

    for (int i = 0; i < 10; i++)
    {
      labels[i * 2 + 8] = "Extra range " + (i + 1);
      labels[i * 2 + 9] = labels[7];
    }

    createLabelsVertical (labels, 0, 0, HPos.RIGHT);
  }
}
