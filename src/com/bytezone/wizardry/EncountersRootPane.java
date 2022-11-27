package com.bytezone.wizardry;

import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.MazeLevel;
import com.bytezone.wizardry.data.WizardryData;

//-----------------------------------------------------------------------------------//
public class EncountersRootPane extends DataPane
//-----------------------------------------------------------------------------------//
{
  private EncountersLabelsPane encountersLabels = new EncountersLabelsPane ();
  private EncountersTable encountersTable0 = new EncountersTable (0);
  private EncountersTable encountersTable1 = new EncountersTable (1);
  private EncountersTable encountersTable2 = new EncountersTable (2);

  // ---------------------------------------------------------------------------------//
  public EncountersRootPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (50, 28);                           // columns, rows

    setAllColumnConstraints (5);              // all columns 5 pixels wide
    setPadding (defaultInsets);               // only the root pane has insets

    setLayout (encountersLabels, 0, 0);
    setLayout (encountersTable0, 10, 0);
    setLayout (encountersTable1, 29, 0);
    setLayout (encountersTable2, 48, 0);

    getChildren ().addAll (encountersLabels, encountersTable0, encountersTable1, encountersTable2);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    encountersTable0.setWizardry (wizardry);
    encountersTable1.setWizardry (wizardry);
    encountersTable2.setWizardry (wizardry);
  }

  // ---------------------------------------------------------------------------------//
  void update (MazeLevel mazeLevel)
  // ---------------------------------------------------------------------------------//
  {
    encountersTable0.update (mazeLevel);
    encountersTable1.update (mazeLevel);
    encountersTable2.update (mazeLevel);
  }
}
