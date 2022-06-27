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
  private Attributes5Pane attributes5Pane = new Attributes5Pane ();
  private MageSpellsPane mageSpellsPane = new MageSpellsPane ();
  private PriestSpellsPane priestSpellsPane = new PriestSpellsPane ();
  private boolean partyPaneAdded;

  // ---------------------------------------------------------------------------------//
  public CharacterPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (26, 40);         // rows, columns

    setAllColumnConstraints (getColumns (), 20);          // all columns 20 pixels wide
    setPadding (defaultInsets);                           // only the root pane has insets

    setGridLinesVisible (false);

    setLayout (attributes1Pane, 0, 0);
    setLayout (baggagePane, 0, 17);
    setLayout (attributes2Pane, 9, 0);
    setLayout (attributes5Pane, 9, 10);       // Unknown
    setLayout (attributes3Pane, 15, 0);       // Strength/Agility etc
    setLayout (attributes4Pane, 15, 7);       // SaveVs
    setLayout (mageSpellsPane, 21, 0);
    setLayout (priestSpellsPane, 30, 0);
    setLayout (partyPane, 15, 18);

    getChildren ().addAll (attributes1Pane, baggagePane, attributes2Pane, attributes3Pane,
        attributes4Pane, mageSpellsPane, priestSpellsPane);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    attributes1Pane.setWizardry (wizardry);
    attributes2Pane.setWizardry (wizardry);
    attributes3Pane.setWizardry (wizardry);
    attributes4Pane.setWizardry (wizardry);
    attributes5Pane.setWizardry (wizardry);
    mageSpellsPane.setWizardry (wizardry);
    priestSpellsPane.setWizardry (wizardry);
    baggagePane.setWizardry (wizardry);
    partyPane.setWizardry (wizardry);

    if (wizardry.getScenarioId () == 4)
    {
      if (!partyPaneAdded)
      {
        getChildren ().add (partyPane);
        getChildren ().add (attributes5Pane);
        partyPaneAdded = true;
      }
    }
    else if (partyPaneAdded)
    {
      getChildren ().remove (partyPane);
      getChildren ().remove (attributes5Pane);
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
    attributes5Pane.update (character);
    mageSpellsPane.update (character);
    priestSpellsPane.update (character);
    baggagePane.update (character);
    partyPane.update (character);
  }
}
