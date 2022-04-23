package com.bytezone.wizardry.gui;

import com.bytezone.appbase.TabBase;
import com.bytezone.wizardry.origin.Reward;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

//-----------------------------------------------------------------------------------//
public class RewardsTab extends TabBase implements ScenarioChangeListener
//-----------------------------------------------------------------------------------//
{
  private WizardryOrigin wizardry;
  private ListView<Reward> rewards = new ListView<> ();
  private RewardPane rewardPane = new RewardPane ();

  // ---------------------------------------------------------------------------------//
  public RewardsTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    BorderPane layout = new BorderPane ();
    setContent (layout);
    layout.setLeft (rewards);
    layout.setCenter (rewardPane);

    rewards.getSelectionModel ().selectedItemProperty ().addListener (new ChangeListener<Reward> ()
    {
      @Override
      public void changed (ObservableValue<? extends Reward> ov, Reward old_val, Reward new_val)
      {
        if (new_val != null)
          rewardPane.update (new_val);
      }
    });
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void update ()
  // ---------------------------------------------------------------------------------//
  {
    if (isValid ())
      return;

    setValid (true);

    rewardPane.setWizardry (wizardry);

    rewards.getItems ().clear ();
    rewards.getItems ().addAll (wizardry.getRewards ());
    rewards.getSelectionModel ().select (0);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void scenarioChanged (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    refresh ();
  }
}
