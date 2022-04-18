package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Font;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class FontsPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private final int scenarioId;
  private final Canvas canvas;

  // ---------------------------------------------------------------------------------//
  public FontsPane (WizardryOrigin wizardry, Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry, stage);

    scenarioId = wizardry.getScenarioId ();
    canvas = scenarioId < 3 ? new Canvas (500, 500) : null;

    setColumnConstraints (50, 60, 400);

    LabelPlacement lp0 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp0 = new DataPlacement (2, 0, Pos.CENTER_LEFT, 1);
    ComboBox<Font> fontsList = new ComboBox<> ();
    createComboBox ("Font", fontsList, wizardry.getFonts (),
        (options, oldValue, newValue) -> update (newValue), lp0, dp0);

    GridPane.setConstraints (canvas, 1, 8);
    GridPane.setColumnSpan (canvas, 3);
    GridPane.setRowSpan (canvas, 7);

    gridPane.getChildren ().add (canvas);
  }

  // ---------------------------------------------------------------------------------//
  private void update (Font font)
  // ---------------------------------------------------------------------------------//
  {

  }
}
