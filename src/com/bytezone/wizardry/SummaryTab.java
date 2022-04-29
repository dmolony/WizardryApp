package com.bytezone.wizardry;

import com.bytezone.appbase.TextTabBase;
import com.bytezone.wizardry.origin.Utility;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

// -----------------------------------------------------------------------------------//
public class SummaryTab extends TextTabBase implements ScenarioChangeListener
// -----------------------------------------------------------------------------------//
{
  private final Text text = new Text ();
  private final TextFlow textFlow = new TextFlow (text);
  private final ScrollPane scrollPane = new ScrollPane (textFlow);
  private String fileName;

  // ---------------------------------------------------------------------------------//
  public SummaryTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    textFlow.setLineSpacing (1);
    text.setFont (Font.font (java.awt.Font.MONOSPACED, 14));

    scrollPane.setPadding (new Insets (5, 5, 5, 5));
    scrollPane.setStyle ("-fx-background: white;-fx-border-color: lightgray;");

    setContent (scrollPane);
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
  void setFileName (String fileName)
  // ---------------------------------------------------------------------------------//
  {
    this.fileName = fileName;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public void scenarioChanged (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {

    StringBuilder text = new StringBuilder ();
    text.append (String.format ("Disk file ......... %s%n", Utility.removeUserName (fileName)));
    text.append (wizardry.getHeader ().toString ());
    this.text.setText (text.toString ());

    refresh ();
  }
}
