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
    super (60, 30);                           // columns, rows

    setAllColumnConstraints (10);             // all columns 10 pixels wide
    setPadding (defaultInsets);               // only the root pane has insets

    setGridLinesVisible (false);

    setLayout (characterPane1, 0, 0);
    setLayout (baggagePane, 0, 18);

    setLayout (characterPane2, 15, 0);
    setLayout (sipvalPane, 15, 10);           // Strength/IQ/Piety/Vitality/Agility/Luck

    setLayout (resistancePane, 23, 0);
    setLayout (savingThrowPane, 23, 10);
    setLayout (partyPane, 24, 18);            // only shown for scenario #4

    setLayout (monsterProtectPane, 31, 0);    // protect/vs 
    setLayout (mageSpellsPane, 41, 0);
    setLayout (priestSpellsPane, 55, 0);

    setLayout (tempWepVsPane, 42, 13);         // 3 WepVs values (temporary)

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
