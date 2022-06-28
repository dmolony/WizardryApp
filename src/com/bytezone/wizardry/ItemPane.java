package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Item;
import com.bytezone.wizardry.data.WizardryData;

//-----------------------------------------------------------------------------------//
public class ItemPane extends DataPane
//-----------------------------------------------------------------------------------//
{
  private ItemPane1 itemPane1 = new ItemPane1 ();
  private ItemPane2 itemPane2 = new ItemPane2 ();
  private ItemPane3 itemPane3 = new ItemPane3 ();
  private ItemPane4 itemPane4 = new ItemPane4 ();
  private ItemPane5 itemPane5 = new ItemPane5 ();

  // ---------------------------------------------------------------------------------//
  public ItemPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (40, 18);                             // columns, rows

    setAllColumnConstraints (20);               // 40 columns x 20 pixels
    setPadding (defaultInsets);                 // only the root pane has insets

    setLayout (itemPane1, 0, 0);
    setLayout (itemPane2, 0, 12);
    setLayout (itemPane3, 10, 0);
    setLayout (itemPane4, 10, 10);
    setLayout (itemPane5, 16, 0);

    getChildren ().addAll (itemPane1, itemPane2, itemPane3, itemPane4, itemPane5);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    itemPane1.setWizardry (wizardry);
    itemPane2.setWizardry (wizardry);
    itemPane3.setWizardry (wizardry);
    itemPane4.setWizardry (wizardry);
    itemPane5.setWizardry (wizardry);
  }

  // ---------------------------------------------------------------------------------//
  void update (Item item)
  // ---------------------------------------------------------------------------------//
  {
    itemPane1.update (item);
    itemPane2.update (item);
    itemPane3.update (item);
    itemPane4.update (item);
    itemPane5.update (item);
  }
}
