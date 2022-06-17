package com.bytezone.wizardry;

import com.bytezone.wizardry.origin.Character;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class Attributes2Pane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private static final int MAXLEVAC = 0;
  private static final int CHAR_LEV = 1;
  private static final int HP_LEFT = 2;
  private static final int HP_MAX = 3;
  private static final int HP_CALC_MD = 4;
  private static final int AC = 5;
  private static final int REGEN = 6;
  private static final int SWING = 7;

  TextField[] textOut2;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public Attributes2Pane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (90, 50);
    gridPane.setPadding (new Insets (0, 0, 0, 0));      // trbl

    String[] labelText2 =
        { "Max lev AC", "Level", "HP left", "Max HP", "HP calc", "AC", "Regen", "Swing" };

    // numeric values
    LabelPlacement lp3 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp3 = new DataPlacement (1, 1, Pos.CENTER_RIGHT, 1);
    textOut2 = createTextFields (labelText2, lp3, dp3);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut2);
  }

  // ---------------------------------------------------------------------------------//
  void update (Character character)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut2[MAXLEVAC], character.maxlevac);
    setText (textOut2[CHAR_LEV], character.charlev);
    setText (textOut2[HP_LEFT], character.hpLeft);
    setText (textOut2[HP_MAX], character.hpMax);
    setText (textOut2[HP_CALC_MD], character.hpCalCmd);
    setText (textOut2[AC], character.armourClass);
    setText (textOut2[REGEN], character.healPts);
    setText (textOut2[SWING], character.swingCount);
  }
}
