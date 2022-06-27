package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Item;
import com.bytezone.wizardry.data.WizardryData;

import javafx.scene.layout.GridPane;

//-----------------------------------------------------------------------------------//
public class ItemPane extends DataPane
//-----------------------------------------------------------------------------------//
{
  private ItemPane1 itemPane1 = new ItemPane1 ();
  private ItemPane2 itemPane2 = new ItemPane2 ();
  private ItemPane3 itemPane3 = new ItemPane3 ();
  private ItemPane4 itemPane4 = new ItemPane4 ();
  private ItemPane5 itemPane5 = new ItemPane5 ();

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public ItemPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (18, 40);

    setAllColumnConstraints (40, 20);                     // 40 columns x 20 pixels
    setPadding (defaultInsets);

    GridPane.setConstraints (itemPane1, 0, 0);
    GridPane.setColumnSpan (itemPane1, 2);
    GridPane.setRowSpan (itemPane1, 11);

    GridPane.setConstraints (itemPane2, 0, 12);
    GridPane.setColumnSpan (itemPane2, 2);
    GridPane.setRowSpan (itemPane2, 7);

    GridPane.setConstraints (itemPane3, 10, 0);
    GridPane.setColumnSpan (itemPane3, 2);
    GridPane.setRowSpan (itemPane3, 8);

    GridPane.setConstraints (itemPane4, 10, 10);
    GridPane.setColumnSpan (itemPane4, 2);
    GridPane.setRowSpan (itemPane4, 9);

    GridPane.setConstraints (itemPane5, 16, 0);
    GridPane.setColumnSpan (itemPane5, 2);
    GridPane.setRowSpan (itemPane5, 15);

    getChildren ().addAll (itemPane1, itemPane2, itemPane3, itemPane4, itemPane5);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

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
