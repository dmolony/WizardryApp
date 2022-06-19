package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
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

  private TextField[] textOut;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public Attributes2Pane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (90, 50);
    setAllRowConstraints (8, getRowHeight ());           // make all rows the same height

    String[] labelText =
        { "Max lev AC", "Level", "HP left", "Max HP", "HP calc", "AC", "Regen", "Swing" };

    LabelPlacement lp = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp = new DataPlacement (1, 1, Pos.CENTER_RIGHT, 1);
    textOut = createTextFields (labelText, lp, dp);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return 8;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getColumns ()
  // ---------------------------------------------------------------------------------//
  {
    return 2;
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut);
  }

  // ---------------------------------------------------------------------------------//
  void update (Character character)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut[MAXLEVAC], character.maxlevac);
    setText (textOut[CHAR_LEV], character.charlev);
    setText (textOut[HP_LEFT], character.hpLeft);
    setText (textOut[HP_MAX], character.hpMax);
    setText (textOut[HP_CALC_MD], character.hpCalCmd);
    setText (textOut[AC], character.armourClass);
    setText (textOut[REGEN], character.healPts);
    setText (textOut[SWING], character.swingCount);
  }
}
