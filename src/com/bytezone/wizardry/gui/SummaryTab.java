package com.bytezone.wizardry.gui;

import com.bytezone.appbase.TextTabBase;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

// -----------------------------------------------------------------------------------//
public class SummaryTab extends TextTabBase implements ScenarioChangeListener
// -----------------------------------------------------------------------------------//
{
  private final Text text = new Text ();
  private final TextFlow textFlow = new TextFlow (text);
  private final ScrollPane scrollPane = new ScrollPane (textFlow);
  private WizardryOrigin wizardry;

  // ---------------------------------------------------------------------------------//
  public SummaryTab (String title, KeyCode keyCode)
  // ---------------------------------------------------------------------------------//
  {
    super (title, keyCode);

    textFlow.setLineSpacing (1);

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

    if (wizardry != null)
      text.setText (wizardry.getScenarioName ());
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
