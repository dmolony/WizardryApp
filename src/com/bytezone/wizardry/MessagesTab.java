package com.bytezone.wizardry;

import java.util.prefs.Preferences;

import com.bytezone.wizardry.data.Message;
import com.bytezone.wizardry.data.WizardryData;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;

// -----------------------------------------------------------------------------------//
public class MessagesTab extends WizardryTabBase
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_INDEX = "MessagesIndex";

  private ListView<Message> messages = new ListView<> ();
  private MessagePane messagePane = new MessagePane ();

  // ---------------------------------------------------------------------------------//
  public MessagesTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    BorderPane layout = new BorderPane ();
    setContent (layout);
    layout.setLeft (messages);
    layout.setCenter (messagePane);

    messages.setPrefWidth (LIST_WIDTH);
    messages.setPlaceholder (new Label ("No Messages"));

    messages.getSelectionModel ().selectedItemProperty ()
        .addListener (new ChangeListener<Message> ()
        {
          @Override
          public void changed (ObservableValue<? extends Message> ov, Message old_val,
              Message new_val)
          {
            if (new_val != null)
              messagePane.update (new_val);
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
    messagePane.setWizardry (wizardry);

    messages.getItems ().clear ();
    messages.getItems ().addAll (wizardry.getMessages ().getMessages ());
    messages.getSelectionModel ().select (0);

    refresh ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    int index = messages.getSelectionModel ().getSelectedIndex ();
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
      messages.getSelectionModel ().select (index);
      messages.scrollTo (index);
    }
  }
}
