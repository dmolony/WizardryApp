package com.bytezone.wizardry;

import java.util.prefs.Preferences;

import com.bytezone.wizardry.origin.Item;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
public class ItemsTab extends WizardryTabBase
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_INDEX = "ItemsIndex";

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

    items.setPrefWidth (LIST_WIDTH);
    items.setPlaceholder (new Label ("Items not found"));

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
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void scenarioChanged (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    itemPane.setWizardry (wizardry);

    items.getItems ().clear ();
    items.getItems ().addAll (wizardry.getItems ().values ());
    items.getSelectionModel ().select (0);

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    int index = items.getSelectionModel ().getSelectedIndex ();
    prefs.putInt (PREFS_INDEX, index);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    int index = prefs.getInt (PREFS_INDEX, -1);
    if (index >= 0)
    {
      items.getSelectionModel ().select (index);
      items.scrollTo (index);
    }
  }
}
