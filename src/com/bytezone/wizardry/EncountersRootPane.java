package com.bytezone.wizardry;

import java.util.List;
import java.util.Random;

import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.MazeLevel;
import com.bytezone.wizardry.data.WizardryData;
import com.bytezone.wizardry.graphics.Display;

import javafx.scene.layout.GridPane;

//-----------------------------------------------------------------------------------//
public class EncountersRootPane extends DataPane
//-----------------------------------------------------------------------------------//
{
  private static final Random random = new Random ();

  private EncountersLabelsPane encountersLabels = new EncountersLabelsPane ();
  //  private EncountersHeader encountersHeader1 = new EncountersHeader (1);
  //  private EncountersHeader encountersHeader2 = new EncountersHeader (2);
  //  private EncountersHeader encountersHeader3 = new EncountersHeader (3);

  private EncountersTable encountersTable0 = new EncountersTable (0);
  private EncountersTable encountersTable1 = new EncountersTable (1);
  private EncountersTable encountersTable2 = new EncountersTable (2);

  private Display display;

  // ---------------------------------------------------------------------------------//
  public EncountersRootPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (80, 30);                           // columns, rows

    setAllColumnConstraints (5);              // all columns 5 pixels wide
    setPadding (defaultInsets);               // only the root pane has insets

    setLayout (encountersLabels, 0, 0);
    //    setLayout (encountersHeader1, 10, 0);
    //    setLayout (encountersHeader2, 29, 0);
    //    setLayout (encountersHeader3, 48, 0);

    setLayout (encountersTable0, 10, 0);
    setLayout (encountersTable1, 29, 0);
    setLayout (encountersTable2, 48, 0);

    setGridLinesVisible (false);

    getChildren ().addAll (encountersLabels,//
        //        encountersHeader1, encountersHeader2, encountersHeader3, //
        encountersTable0, encountersTable1, encountersTable2);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    encountersTable0.setWizardry (wizardry);
    encountersTable1.setWizardry (wizardry);
    encountersTable2.setWizardry (wizardry);

    if (wizardry.getScenarioId () < 3 && false)
    {
      display = new Display (wizardry);
      GridPane.setConstraints (display, 65, 1);      // left/top corner
      GridPane.setColumnSpan (display, 13);
      GridPane.setRowSpan (display, 14);
      getChildren ().add (display);

      List<MazeLevel> mazeLevels = wizardry.getMazeLevels ();
      int level = random.nextInt (mazeLevels.size ());
      display.update (mazeLevels.get (level));        // get encounter from this level
    }
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
