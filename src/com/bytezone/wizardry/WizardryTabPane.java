package com.bytezone.wizardry;

import com.bytezone.appbase.TabPaneBase;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class WizardryTabPane extends TabPaneBase
// -----------------------------------------------------------------------------------//
{
  CharactersTab charactersTab = new CharactersTab ("Characters", KeyCode.C);
  MonstersTab monstersTab = new MonstersTab ("Monsters", KeyCode.M);
  SummaryTab headerTab = new SummaryTab ("Header", KeyCode.H);
  ItemsTab itemsTab = new ItemsTab ("Items", KeyCode.I);
  SpecialsTab specialsTab = new SpecialsTab ("Specials", KeyCode.S);
  RewardsTab rewardsTab = new RewardsTab ("Rewards", KeyCode.R);
  EncountersTab encountersTab = new EncountersTab ("Encounters", KeyCode.E);
  MazeTab mazeTab = new MazeTab ("Maze", KeyCode.L);
  MessagesTab messagesTab = new MessagesTab ("Messages", KeyCode.T);

  // ---------------------------------------------------------------------------------//
  public WizardryTabPane (String prefsId)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    addAll (headerTab, charactersTab, monstersTab, itemsTab, specialsTab, rewardsTab, encountersTab,
        messagesTab, mazeTab);

    setDefaultTab (2);
  }
}
