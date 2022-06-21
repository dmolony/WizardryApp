package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

// -----------------------------------------------------------------------------------//
public class CharacterPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private PartyPane partyPane = new PartyPane ();
  private BaggagePane baggagePane = new BaggagePane ();
  private Attributes1Pane attributes1Pane = new Attributes1Pane ();
  private Attributes2Pane attributes2Pane = new Attributes2Pane ();
  private Attributes3Pane attributes3Pane = new Attributes3Pane ();
  private Attributes4Pane attributes4Pane = new Attributes4Pane ();
  private MageSpellsPane mageSpellsPane = new MageSpellsPane ();
  private PriestSpellsPane priestSpellsPane = new PriestSpellsPane ();
  private boolean partyPaneAdded;

  // ---------------------------------------------------------------------------------//
  public CharacterPane ()
  // ---------------------------------------------------------------------------------//
  {
    setAllColumnConstraints (getColumns (), 20);          // all columns 20 pixels wide
    setAllRowConstraints (getRows (), getRowHeight ());   // make all rows the same height

    setGridLinesVisible (false);
    setPadding (defaultInsets);

    setLayout (attributes1Pane, 0, 0);
    setLayout (baggagePane, 0, 17);
    setLayout (attributes2Pane, 9, 0);
    setLayout (attributes3Pane, 14, 0);       // Strength/Agility etc
    setLayout (attributes4Pane, 14, 7);       // SaveVs
    setLayout (mageSpellsPane, 21, 0);
    setLayout (priestSpellsPane, 30, 0);
    setLayout (partyPane, 15, 18);

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
    attributes1Pane.setWizardry (wizardry);
    attributes2Pane.setWizardry (wizardry);
    attributes3Pane.setWizardry (wizardry);
    attributes4Pane.setWizardry (wizardry);
    mageSpellsPane.setWizardry (wizardry);
    priestSpellsPane.setWizardry (wizardry);
    baggagePane.setWizardry (wizardry);
    partyPane.setWizardry (wizardry);

    if (wizardry.getScenarioId () == 4)
    {
      if (!partyPaneAdded)
      {
        getChildren ().add (partyPane);
        partyPaneAdded = true;
      }
    }
    else if (partyPaneAdded)
    {
      getChildren ().remove (partyPane);
      partyPaneAdded = false;
    }
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
    partyPane.update (character);
  }
}
