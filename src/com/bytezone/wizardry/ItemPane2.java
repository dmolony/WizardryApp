package com.bytezone.wizardry;

import com.bytezone.wizardry.origin.Item;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class ItemPane2 extends DataPane
// -----------------------------------------------------------------------------------//
{
  private static final int ID = 0;
  private static final int SPECIAL = 1;
  private static final int AC = 2;
  private static final int WEP_HIT_MOD = 3;
  private static final int XTRA_SWING = 4;
  private static final int BOLTAC = 5;
  private static final int REGEN = 6;

  private TextField[] textOut;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public ItemPane2 ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (110, 45);
    setAllRowConstraints (7, DataPane.ROW_HEIGHT);           // make all rows the same height

    String[] label2Text =
        { "Id", "Special #", "AC", "To hit +", "# swings", "In store", "Regeneration" };

    LabelPlacement lp = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp = new DataPlacement (1, 0, Pos.CENTER_RIGHT, 1);
    textOut = createTextFields (label2Text, lp, dp);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (textOut);
  }

  // ---------------------------------------------------------------------------------//
  void update (Item item)
  // ---------------------------------------------------------------------------------//
  {
    setText (textOut[ID], item.id);

    if (item.type == null)
      return;

    setText (textOut[SPECIAL], item.special);
    setText (textOut[AC], item.armourClass);
    setText (textOut[WEP_HIT_MOD], item.wephitmd);
    setText (textOut[XTRA_SWING], item.xtraSwing);
    setText (textOut[BOLTAC], item.boltac);
    setText (textOut[REGEN], item.healPts);
  }
}