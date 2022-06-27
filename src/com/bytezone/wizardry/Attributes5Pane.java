package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;

//-----------------------------------------------------------------------------------//
public class Attributes5Pane extends DataPane
//-----------------------------------------------------------------------------------//
{
  private TextField[] textOut;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public Attributes5Pane ()
  // ---------------------------------------------------------------------------------//
  {
    setColumnConstraints (90, 70);
    setAllRowConstraints (getRows (), getRowHeight ());     // make all rows the same height

    String[] labelText = { "Val 1", "Val 2", "Val 3", "Val 4", "Val 5", "Next" };
    assert getRows () == labelText.length;

    createLabelsVertical (new LabelPlacement (labelText, 0, 0, HPos.RIGHT, 1));
    textOut = createTextFields (new DataLayout (1, 0, getRows (), Pos.CENTER_RIGHT));
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return 6;
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
    setText (textOut[0], character.unknown1);
    setText (textOut[1], character.unknown2);
    setText (textOut[2], character.unknown3);
    setText (textOut[3], character.unknown4);
    setText (textOut[4], character.unknown5);
    setText (textOut[5], character.nextCharacterId);
  }
}