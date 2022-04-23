package com.bytezone.wizardry.gui;

import com.bytezone.appbase.TabBase;
import com.bytezone.wizardry.origin.Item;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
public class ItemsTab extends TabBase implements ScenarioChangeListener
// -----------------------------------------------------------------------------------//
{
  private WizardryOrigin wizardry;
  private ListView<Item> items = new ListView<> ();
  private ItemPane itemPane = new ItemPane ();

  // ---------------------------------------------------------------------------------//
  public ItemsTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    BorderPane layout = new BorderPane ();
    setContent (layout);
    layout.setLeft (items);
    layout.setCenter (itemPane);

    items.getSelectionModel ().selectedItemProperty ().addListener (new ChangeListener<Item> ()
    {
      @Override
      public void changed (ObservableValue<? extends Item> ov, Item old_val, Item new_val)
      {
        if (new_val != null)
          itemPane.update (new_val);
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

    itemPane.setWizardry (wizardry);

    items.getItems ().clear ();
    items.getItems ().addAll (wizardry.getItems ());
    items.getSelectionModel ().select (0);
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
