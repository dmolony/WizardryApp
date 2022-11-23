package com.bytezone.wizardry;

import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

// -----------------------------------------------------------------------------------//
public class CharactersRootPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private PartyPane partyPane = new PartyPane ();
  private BaggagePane baggagePane = new BaggagePane ();
  private Attributes1Pane attributes1Pane = new Attributes1Pane ();
  private Attributes2Pane attributes2Pane = new Attributes2Pane ();
  private Attributes3Pane attributes3Pane = new Attributes3Pane ();
  private Attributes4Pane attributes4Pane = new Attributes4Pane ();
  //  private Attributes5Pane attributes5Pane = new Attributes5Pane ();
  private Attributes6Pane attributes6Pane = new Attributes6Pane ();
  private MageSpellsPane mageSpellsPane = new MageSpellsPane ();
  private PriestSpellsPane priestSpellsPane = new PriestSpellsPane ();
  private ItemPane5 itemPane5 = new ItemPane5 ();
  private ResistancePane monsterPane4 = new ResistancePane ();

  private boolean partyPaneVisible;

  // ---------------------------------------------------------------------------------//
  public CharactersRootPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (40, 26);                           // rows, columns

    setAllColumnConstraints (20);             // all columns 20 pixels wide
    setPadding (defaultInsets);               // only the root pane has insets

    setGridLinesVisible (false);

    setLayout (attributes1Pane, 0, 0);
    setLayout (baggagePane, 0, 18);

    setLayout (attributes2Pane, 10, 0);
    setLayout (attributes3Pane, 10, 10);      // Strength/Agility etc

    setLayout (monsterPane4, 15, 0);         // resistance
    setLayout (attributes6Pane, 15, 9);       // WepVs
    setLayout (attributes4Pane, 15, 12);       // SaveVs
    //    setLayout (attributes5Pane, 15, 10);      // Unknown
    setLayout (partyPane, 15, 19);

    setLayout (itemPane5, 20, 0);
    setLayout (mageSpellsPane, 27, 0);
    setLayout (priestSpellsPane, 36, 0);

    getChildren ().addAll (attributes1Pane, baggagePane, attributes2Pane, attributes3Pane,
        attributes4Pane, attributes6Pane, itemPane5, monsterPane4, mageSpellsPane,
        priestSpellsPane);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    attributes1Pane.setWizardry (wizardry);
    attributes2Pane.setWizardry (wizardry);
    attributes3Pane.setWizardry (wizardry);
    attributes4Pane.setWizardry (wizardry);
    //    attributes5Pane.setWizardry (wizardry);
    attributes6Pane.setWizardry (wizardry);
    itemPane5.setWizardry (wizardry);
    monsterPane4.setWizardry (wizardry);
    mageSpellsPane.setWizardry (wizardry);
    priestSpellsPane.setWizardry (wizardry);
    baggagePane.setWizardry (wizardry);
    partyPane.setWizardry (wizardry);

    if (wizardry.getScenarioId () == 4)
    {
      if (!partyPaneVisible)
      {
        getChildren ().add (partyPane);
        //        getChildren ().add (attributes5Pane);
        partyPaneVisible = true;
      }
    }
    else if (partyPaneVisible)
    {
      getChildren ().remove (partyPane);
      //      getChildren ().remove (attributes5Pane);
      partyPaneVisible = false;
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
    //    attributes5Pane.update (character);
    attributes6Pane.update (character);
    itemPane5.update (character.wep2[1], character.wep3[1]);
    monsterPane4.update (character.wep1);

    mageSpellsPane.update (character);
    priestSpellsPane.update (character);
    baggagePane.update (character);
    partyPane.update (character);
  }
}
