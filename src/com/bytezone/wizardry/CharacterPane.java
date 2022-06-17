package com.bytezone.wizardry;

import com.bytezone.wizardry.origin.Character;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.scene.layout.GridPane;

// -----------------------------------------------------------------------------------//
public class CharacterPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private WizardryData wizardry;

  private PartyPane partyPane = new PartyPane ();
  private BaggagePane baggagePane = new BaggagePane ();
  private Attributes1Pane attributes1Pane = new Attributes1Pane ();
  private Attributes2Pane attributes2Pane = new Attributes2Pane ();
  private Attributes3Pane attributes3Pane = new Attributes3Pane ();
  private Attributes4Pane attributes4Pane = new Attributes4Pane ();
  private MageSpellsPane mageSpellsPane = new MageSpellsPane ();
  private PriestSpellsPane priestSpellsPane = new PriestSpellsPane ();

  // ---------------------------------------------------------------------------------//
  public CharacterPane ()
  // ---------------------------------------------------------------------------------//
  {
    int width = 20;
    setColumnConstraints (width, width, width, width, width, width, width, width, width, width,
        width, width, width, width, width, width, width, width);

    GridPane.setConstraints (attributes1Pane, 0, 0);
    GridPane.setColumnSpan (partyPane, 2);
    GridPane.setRowSpan (partyPane, 10);

    GridPane.setConstraints (attributes2Pane, 10, 8);
    GridPane.setColumnSpan (partyPane, 2);
    GridPane.setRowSpan (partyPane, 8);

    GridPane.setConstraints (attributes3Pane, 10, 1);       // Strength/Agility etc
    GridPane.setColumnSpan (partyPane, 2);
    GridPane.setRowSpan (partyPane, 6);

    GridPane.setConstraints (attributes4Pane, 15, 1);       // SaveVs
    GridPane.setColumnSpan (partyPane, 2);
    GridPane.setRowSpan (partyPane, 6);

    GridPane.setConstraints (baggagePane, 0, 17);
    GridPane.setColumnSpan (baggagePane, 2);
    GridPane.setRowSpan (baggagePane, 7);

    GridPane.setConstraints (partyPane, 16, 17);
    GridPane.setColumnSpan (partyPane, 2);
    GridPane.setRowSpan (partyPane, 10);

    GridPane.setConstraints (mageSpellsPane, 26, 0);
    GridPane.setColumnSpan (mageSpellsPane, 2);
    GridPane.setRowSpan (mageSpellsPane, 10);

    GridPane.setConstraints (priestSpellsPane, 32, 0);
    GridPane.setColumnSpan (priestSpellsPane, 2);
    GridPane.setRowSpan (priestSpellsPane, 10);

    gridPane.getChildren ().addAll (baggagePane, attributes1Pane, attributes2Pane, attributes3Pane,
        attributes4Pane, mageSpellsPane, priestSpellsPane);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    attributes1Pane.setWizardry (wizardry);
    attributes2Pane.setWizardry (wizardry);
    attributes3Pane.setWizardry (wizardry);
    attributes4Pane.setWizardry (wizardry);
    mageSpellsPane.setWizardry (wizardry);
    priestSpellsPane.setWizardry (wizardry);
    baggagePane.setWizardry (wizardry);

    // party
    if (wizardry.getScenarioId () == 4)
    {
      gridPane.getChildren ().add (partyPane);
      partyPane.setWizardry (wizardry);
    }
    else
      gridPane.getChildren ().remove (partyPane);
  }

  // ---------------------------------------------------------------------------------//
  void update (Character character)
  // ---------------------------------------------------------------------------------//
  {
    attributes1Pane.update (character);
    attributes2Pane.update (character);
    attributes3Pane.update (character);
    attributes4Pane.update (character);
    mageSpellsPane.update (character);
    priestSpellsPane.update (character);
    baggagePane.update (character);

    if (wizardry.getScenarioId () == 4)
      partyPane.update (character);
  }
}
