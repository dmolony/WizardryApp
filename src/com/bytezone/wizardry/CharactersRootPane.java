package com.bytezone.wizardry;

import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

// -----------------------------------------------------------------------------------//
public class CharactersRootPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private CharacterPane1 characterPane1 = new CharacterPane1 ();
  private CharacterPane2 characterPane2 = new CharacterPane2 ();
  private CharacterPane3 sipvalPane = new CharacterPane3 ();

  private TempWepVsPane tempWepVsPane = new TempWepVsPane ();       // temp

  private SavingThrowPane savingThrowPane = new SavingThrowPane ();
  private MonsterProtectPane monsterProtectPane = new MonsterProtectPane ();
  private ResistancePane resistancePane = new ResistancePane ();

  private MageSpellsPane mageSpellsPane = new MageSpellsPane ();
  private PriestSpellsPane priestSpellsPane = new PriestSpellsPane ();

  private BaggagePane baggagePane = new BaggagePane ();
  private PartyPane partyPane = new PartyPane ();

  private boolean partyPaneVisible;

  // ---------------------------------------------------------------------------------//
  public CharactersRootPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (40, 26);                           // rows, columns

    setAllColumnConstraints (20);             // all columns 20 pixels wide
    setPadding (defaultInsets);               // only the root pane has insets

    setGridLinesVisible (false);

    setLayout (characterPane1, 0, 0);
    setLayout (baggagePane, 0, 18);

    setLayout (characterPane2, 10, 0);
    setLayout (sipvalPane, 10, 10);           // Strength/IQ/Piety/Vitality/Agility/Luck

    setLayout (resistancePane, 15, 0);        // resistance
    setLayout (tempWepVsPane, 15, 9);         // 3 WepVs values (temporary)
    setLayout (savingThrowPane, 15, 12);      // SaveVs
    setLayout (partyPane, 15, 19);            // only shown for scenario #4

    setLayout (monsterProtectPane, 20, 0);    // protect/vs 
    setLayout (mageSpellsPane, 27, 0);
    setLayout (priestSpellsPane, 36, 0);

    getChildren ().addAll (characterPane1, characterPane2, sipvalPane, savingThrowPane,
        tempWepVsPane, monsterProtectPane, resistancePane, mageSpellsPane, priestSpellsPane,
        baggagePane);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    characterPane1.setWizardry (wizardry);
    characterPane2.setWizardry (wizardry);

    sipvalPane.setWizardry (wizardry);
    savingThrowPane.setWizardry (wizardry);

    tempWepVsPane.setWizardry (wizardry);

    monsterProtectPane.setWizardry (wizardry);
    resistancePane.setWizardry (wizardry);

    mageSpellsPane.setWizardry (wizardry);
    priestSpellsPane.setWizardry (wizardry);

    baggagePane.setWizardry (wizardry);
    partyPane.setWizardry (wizardry);

    if (wizardry.getScenarioId () == 4)
    {
      if (!partyPaneVisible)
      {
        getChildren ().add (partyPane);
        partyPaneVisible = true;
      }
    }
    else if (partyPaneVisible)
    {
      getChildren ().remove (partyPane);
      partyPaneVisible = false;
    }
  }

  // ---------------------------------------------------------------------------------//
  void update (Character character)
  // ---------------------------------------------------------------------------------//
  {
    characterPane1.update (character);
    characterPane2.update (character);
    sipvalPane.update (character);
    savingThrowPane.update (character);
    tempWepVsPane.update (character);

    monsterProtectPane.update (character.wep2[0] | character.wep2[1], character.wep1);
    resistancePane.update (character.wep3[0] | character.wep3[1]);

    mageSpellsPane.update (character);
    priestSpellsPane.update (character);
    baggagePane.update (character);
    partyPane.update (character);
  }
}
