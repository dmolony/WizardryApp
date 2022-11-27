package com.bytezone.wizardry;

import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.Message;
import com.bytezone.wizardry.data.WizardryData;

// -----------------------------------------------------------------------------------//
public class MessagesRootPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  MessagesPane messagesPane = new MessagesPane ();

  // ---------------------------------------------------------------------------------//
  public MessagesRootPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (3, 25);                             // columns, rows

    setAllColumnConstraints (10);             // all columns 10 pixels wide
    setPadding (defaultInsets);               // only the root pane has insets

    setLayout (messagesPane, 0, 0);

    getChildren ().addAll (messagesPane);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    messagesPane.setWizardry (wizardry);
  }

  // ---------------------------------------------------------------------------------//
  public void update (Message message)
  // ---------------------------------------------------------------------------------//
  {
    messagesPane.update (message);
  }
}
