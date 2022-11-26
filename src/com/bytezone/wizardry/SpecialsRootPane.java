package com.bytezone.wizardry;

import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.MazeLevel;
import com.bytezone.wizardry.data.WizardryData;

//-----------------------------------------------------------------------------------//
public class SpecialsRootPane extends DataPane
//-----------------------------------------------------------------------------------//
{
  SpecialsTablePane specialsTablePane = new SpecialsTablePane ();

  // ---------------------------------------------------------------------------------//
  public SpecialsRootPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (9, 17);                             // columns, rows

    setAllColumnConstraints (10);             // all columns 10 pixels wide
    setPadding (defaultInsets);               // only the root pane has insets

    setLayout (specialsTablePane, 0, 0);
    getChildren ().addAll (specialsTablePane);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    specialsTablePane.setWizardry (wizardry);
  }

  // ---------------------------------------------------------------------------------//
  void update (MazeLevel mazeLevel)
  // ---------------------------------------------------------------------------------//
  {
    specialsTablePane.update (mazeLevel);
  }
}
