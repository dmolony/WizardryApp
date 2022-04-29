package com.bytezone.wizardry;

import com.bytezone.appbase.TabBase;

import javafx.scene.input.KeyCode;

//-------------------------------------------------------------------------------------//
public abstract class WizardryTabBase extends TabBase implements ScenarioChangeListener
//-------------------------------------------------------------------------------------//
{
  protected static final int LIST_WIDTH = 200;

  //-----------------------------------------------------------------------------------//
  public WizardryTabBase (String title, KeyCode keyCode)
  //-----------------------------------------------------------------------------------//
  {
    super (title, keyCode);
  }
}
