package com.bytezone.wizardry.gui;

import com.bytezone.appbase.TabPaneBase;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class WizardryTabPane extends TabPaneBase
// -----------------------------------------------------------------------------------//
{
  CharactersTab charactersTab = new CharactersTab ("Characters", KeyCode.C);
  MonstersTab monstersTab = new MonstersTab ("Monsters", KeyCode.M);
  SummaryTab summaryTab = new SummaryTab ("Disk", KeyCode.D);
  ItemsTab itemsTab = new ItemsTab ("Items", KeyCode.I);
  SpecialsTab specialsTab = new SpecialsTab ("Specials", KeyCode.S);
  RewardsTab rewardsTab = new RewardsTab ("Rewards", KeyCode.R);
  EncountersTab encountersTab = new EncountersTab ("Encounters", KeyCode.E);
  MazeTab mazeTab = new MazeTab ("Maze", KeyCode.L);

  // ---------------------------------------------------------------------------------//
  public WizardryTabPane (String prefsId)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    add (summaryTab);
    add (charactersTab);
    add (monstersTab);
    add (itemsTab);
    add (specialsTab);
    add (rewardsTab);
    add (encountersTab);
    add (mazeTab);

    setDefaultTab (2);
  }
}
