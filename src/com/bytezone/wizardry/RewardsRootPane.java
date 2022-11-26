package com.bytezone.wizardry;

import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.Reward;
import com.bytezone.wizardry.data.WizardryData;

//-----------------------------------------------------------------------------------//
public class RewardsRootPane extends DataPane
//-----------------------------------------------------------------------------------//
{
  private RewardsGoldPane rewardsGoldPane = new RewardsGoldPane ();
  private RewardsItemsPane rewardsItemsPane = new RewardsItemsPane ();
  private RewardsTrapsPane rewardsTrapsPane = new RewardsTrapsPane ();
  private RewardsChestPane rewardsChestPane = new RewardsChestPane ();

  // ---------------------------------------------------------------------------------//
  public RewardsRootPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (6, 18);                             // columns, rows

    setAllColumnConstraints (10);             // all columns 10 pixels wide
    setPadding (defaultInsets);               // only the root pane has insets

    setLayout (rewardsChestPane, 0, 0);
    setLayout (rewardsGoldPane, 0, 2);
    setLayout (rewardsItemsPane, 0, 9);
    setLayout (rewardsTrapsPane, 30, 0);

    getChildren ().addAll (rewardsGoldPane, rewardsItemsPane, rewardsChestPane, rewardsTrapsPane);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    rewardsGoldPane.setWizardry (wizardry);
    rewardsItemsPane.setWizardry (wizardry);
    rewardsChestPane.setWizardry (wizardry);
    rewardsTrapsPane.setWizardry (wizardry);
  }

  // ---------------------------------------------------------------------------------//
  void update (Reward reward)
  // ---------------------------------------------------------------------------------//
  {
    rewardsItemsPane.update (reward);
    rewardsGoldPane.update (reward);
    rewardsChestPane.update (reward);
    rewardsTrapsPane.update (reward);
  }
}
