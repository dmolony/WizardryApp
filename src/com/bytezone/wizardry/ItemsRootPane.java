package com.bytezone.wizardry;

import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.Item;
import com.bytezone.wizardry.data.WizardryData;

//-----------------------------------------------------------------------------------//
public class ItemsRootPane extends DataPane
//-----------------------------------------------------------------------------------//
{
  private ItemAttributesPane1 itemPane1 = new ItemAttributesPane1 ();
  private ItemAttributesPane2 itemPane2 = new ItemAttributesPane2 ();
  private ResistancePane resistancePane = new ResistancePane ();
  private ItemClassUsagePane itemPane4 = new ItemClassUsagePane ();
  private MonsterProtectPane monsterProtectPane = new MonsterProtectPane ();

  // ---------------------------------------------------------------------------------//
  public ItemsRootPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (40, 18);                             // columns, rows

    setAllColumnConstraints (20);               // 40 columns x 20 pixels
    setPadding (defaultInsets);                 // only the root pane has insets

    setLayout (itemPane1, 0, 0);
    setLayout (itemPane2, 0, 12);
    setLayout (resistancePane, 10, 0);
    setLayout (itemPane4, 10, 10);
    setLayout (monsterProtectPane, 16, 0);

    getChildren ().addAll (itemPane1, itemPane2, resistancePane, itemPane4, monsterProtectPane);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    itemPane1.setWizardry (wizardry);
    itemPane2.setWizardry (wizardry);
    resistancePane.setWizardry (wizardry);
    itemPane4.setWizardry (wizardry);
    monsterProtectPane.setWizardry (wizardry);
  }

  // ---------------------------------------------------------------------------------//
  void update (Item item)
  // ---------------------------------------------------------------------------------//
  {
    itemPane1.update (item);
    itemPane2.update (item);
    resistancePane.update (item.wepvsty3Flags);
    itemPane4.update (item);
    monsterProtectPane.update (item.wepvsty2Flags, item.wepvstyFlags);
  }
}
