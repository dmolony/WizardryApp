package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

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
    setAllColumnConstraints (40, 20);                     // 40 columns x 20 pixels
    setAllRowConstraints (26, getRowHeight ());           // make all rows the same height
    setGridLinesVisible (false);
    setPadding (defaultInsets);

    setLayout (attributes1Pane, 0, 0, 2, 16);
    setLayout (baggagePane, 0, 17, 10, 8);
    setLayout (attributes2Pane, 10, 8, 2, 8);
    setLayout (attributes3Pane, 10, 1, 2, 6);       // Strength/Agility etc
    setLayout (attributes4Pane, 16, 1, 2, 6);       // SaveVs
    setLayout (mageSpellsPane, 22, 0, 2, 12);
    setLayout (priestSpellsPane, 31, 0, 2, 16);
    setLayout (partyPane, 16, 18, 2, 8);

    getChildren ().addAll (attributes1Pane, baggagePane, attributes2Pane, attributes3Pane,
        attributes4Pane, mageSpellsPane, priestSpellsPane);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return attributes1Pane.getRows () + baggagePane.getRows () + 1;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getColumns ()
  // ---------------------------------------------------------------------------------//
  {
    return 40;
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
