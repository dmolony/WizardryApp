package com.bytezone.wizardry.gui;

import com.bytezone.appbase.TabPaneBase;

import javafx.scene.input.KeyCode;

// -----------------------------------------------------------------------------------//
public class WizardryTabPane extends TabPaneBase
// -----------------------------------------------------------------------------------//
{
  WizardryApp app;

  CharactersTab charactersTab = new CharactersTab ("Characters", KeyCode.C);
  MonstersTab monstersTab = new MonstersTab ("Monsters", KeyCode.M);
  SummaryTab summaryTab = new SummaryTab ("Disk", KeyCode.D);
  ItemsTab itemsTab = new ItemsTab ("Items", KeyCode.I);
  SpecialsTab specialsTab = new SpecialsTab ("Specials", KeyCode.S);
  RewardsTab rewardsTab = new RewardsTab ("Rewards", KeyCode.R);
  EncountersTab encountersTab = new EncountersTab ("Encounters", KeyCode.E);

  // ---------------------------------------------------------------------------------//
  public WizardryTabPane (WizardryApp app, String prefsId)
  // ---------------------------------------------------------------------------------//
  {
    super (prefsId);

    this.app = app;

    add (summaryTab);
    add (charactersTab);
    add (monstersTab);
    add (itemsTab);
    add (specialsTab);
    add (rewardsTab);
    add (encountersTab);

    app.addScenarioChangeListener (summaryTab);
    app.addScenarioChangeListener (charactersTab);
    app.addScenarioChangeListener (monstersTab);
    app.addScenarioChangeListener (itemsTab);
    app.addScenarioChangeListener (specialsTab);
    app.addScenarioChangeListener (rewardsTab);
    app.addScenarioChangeListener (encountersTab);

    setDefaultTab (0);
  }
}
