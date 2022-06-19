package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

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
    setAllColumnConstraints (40, 20);                         // 40 columns x 20 pixels
    setAllRowConstraints (26, DataPane.ROW_HEIGHT);           // make all rows the same height
    setGridLinesVisible (false);
    setPadding (defaultInsets);

    GridPane.setConstraints (attributes1Pane, 0, 0);
    GridPane.setColumnSpan (attributes1Pane, 2);
    GridPane.setRowSpan (attributes1Pane, 16);

    GridPane.setConstraints (baggagePane, 0, 17);
    GridPane.setColumnSpan (baggagePane, 10);
    GridPane.setRowSpan (baggagePane, 16);

    GridPane.setConstraints (attributes2Pane, 10, 8);
    GridPane.setColumnSpan (attributes2Pane, 2);
    GridPane.setRowSpan (attributes2Pane, 8);

    GridPane.setConstraints (attributes3Pane, 10, 1);       // Strength/Agility etc
    GridPane.setColumnSpan (attributes3Pane, 2);
    GridPane.setRowSpan (attributes3Pane, 6);

    GridPane.setConstraints (attributes4Pane, 16, 1);       // SaveVs
    GridPane.setColumnSpan (attributes4Pane, 2);
    GridPane.setRowSpan (attributes4Pane, 6);

    GridPane.setConstraints (mageSpellsPane, 22, 0);
    GridPane.setColumnSpan (mageSpellsPane, 2);
    GridPane.setRowSpan (mageSpellsPane, 12);

    GridPane.setConstraints (priestSpellsPane, 31, 0);
    GridPane.setColumnSpan (priestSpellsPane, 2);
    GridPane.setRowSpan (priestSpellsPane, 16);

    GridPane.setConstraints (partyPane, 16, 18);
    GridPane.setColumnSpan (partyPane, 2);
    GridPane.setRowSpan (partyPane, 8);

    getChildren ().addAll (attributes1Pane, baggagePane, attributes2Pane, attributes3Pane,
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
      getChildren ().add (partyPane);
      partyPane.setWizardry (wizardry);
    }
    else
      getChildren ().remove (partyPane);
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
