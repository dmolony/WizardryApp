package com.bytezone.mazewalker.gui;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.bytezone.wizardry.origin.Character;
import com.bytezone.wizardry.origin.Font;
import com.bytezone.wizardry.origin.Image;
import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

// -----------------------------------------------------------------------------------//
public class DisplayPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private final int scenarioId;
  private final Canvas canvas;
  private final Font alphabet;
  private final Font graphics;

  private List<Monster> monsters;
  private List<Character> characters;
  private Random random = new Random ();
  private List<DisplayColor> colors = Arrays.asList (new DisplayColor ("Green", Color.LIGHTGREEN),
      new DisplayColor ("White", Color.WHITE), new DisplayColor ("Cyan", Color.CYAN));

  // ---------------------------------------------------------------------------------//
  public DisplayPane (WizardryOrigin wizardry, Stage stage)
  // ---------------------------------------------------------------------------------//
  {
    super (wizardry, stage);

    if (wizardry.getScenarioId () <= 2)
    {
      alphabet = wizardry.getFonts ().get (0);
      graphics = wizardry.getFonts ().get (1);
    }
    else
    {
      alphabet = null;
      graphics = null;
    }

    scenarioId = wizardry.getScenarioId ();
    monsters = wizardry.getMonsters ();
    characters = wizardry.getCharacters ();
    canvas = scenarioId < 3 ? new Canvas (565, 390) : null;       // w/h

    setColumnConstraints (110, 400);

    // make all rows the same height
    RowConstraints rowCo = new RowConstraints (25);
    for (int i = 0; i < 10; i++)
      gridPane.getRowConstraints ().add (rowCo);

    LabelPlacement lp0 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp0 = new DataPlacement (1, 0, Pos.CENTER_LEFT, 1);
    ComboBox<DisplayColor> colorsList = new ComboBox<> ();
    createComboBox ("Color", colorsList, colors, (options, oldValue, newValue) -> update (newValue),
        lp0, dp0);

    GridPane.setConstraints (canvas, 1, 2);
    GridPane.setColumnSpan (canvas, 3);
    GridPane.setRowSpan (canvas, 15);

    gridPane.getChildren ().add (canvas);

    colorsList.getSelectionModel ().select (0);
  }

  // ---------------------------------------------------------------------------------//
  private void update (DisplayColor displayColor)
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = canvas.getGraphicsContext2D ();

    alphabet.setColor (displayColor.color);
    graphics.setColor (displayColor.color);

    gc.setFill (Color.BLACK);
    gc.fillRect (0, 0, canvas.getWidth (), canvas.getHeight ());

    drawData (gc, displayColor.color);
    drawGrid (gc, displayColor.color);
  }

  // ---------------------------------------------------------------------------------//
  private void drawGrid (GraphicsContext gc, Color color)
  // ---------------------------------------------------------------------------------//
  {
    int column = 0;
    int row = 0;

    String test4 = "!XXXXXXXXXXX[XXXXXXXXXXXXXXXXXXXXXXXXXX#";
    String test1 = "'XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX(";
    String test2 = "$                                      $";
    String test3 = "%XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX&";
    String test5 = "$           \\                          $";
    String test6 = "'XXXXXXXXXXX^XXXXXXXXXXXXXXXXXXXXXXXXXX(";
    String test7 = "$           ]XXXXXXXXXXXXXXXXXXXXXXXXXX(";

    // top line
    graphics.drawString (test4, column, row++, gc);

    // verticals
    for (int i = 0; i < 4; i++)
      graphics.drawString (test5, column, row++, gc);

    // half line
    graphics.drawString (test7, column, row++, gc);

    // verticals
    for (int i = 0; i < 4; i++)
      graphics.drawString (test5, column, row++, gc);

    // middle line 1
    graphics.drawString (test6, column, row++, gc);

    // verticals
    for (int i = 0; i < 4; i++)
      graphics.drawString (test2, column, row++, gc);

    // middle line 2
    graphics.drawString (test1, column, row++, gc);

    // verticals
    for (int i = 0; i < 7; i++)
      graphics.drawString (test2, column, row++, gc);

    // bottom line
    graphics.drawString (test3, column, row++, gc);
  }

  // ---------------------------------------------------------------------------------//
  private void drawData (GraphicsContext gc, Color color)
  // ---------------------------------------------------------------------------------//
  {
    int column = 13;
    int row = 1;

    int groupSize = random.nextInt (4) + 1;
    for (int i = 0; i < groupSize; i++)
    {
      Monster monster = monsters.get (random.nextInt (monsters.size ()));
      if (i == 0)
      {
        Image image = wizardry.getImage (monster.image);
        image.draw (canvas, 2, color, 16, 47);
      }
      int howMany = random.nextInt (5) + 1;
      String test = String.format ("%d) %d %s (%d)", i + 1, howMany,
          howMany == 1 ? monster.name : monster.namePlural, howMany);
      alphabet.drawString (test, column, row++, gc);
    }

    row += 5 - groupSize;
    String test = String.format ("%s'S OPTIONS", characters.get (0));
    alphabet.drawString (test, column, row++, gc);

    row++;
    test = "F)IGHT  S)PELL  P)ARRY";
    alphabet.drawString (test, column, row++, gc);

    test = "R)UN    U)SE    D)ISPELL";
    alphabet.drawString (test, column, row++, gc);

    column = 1;
    row += 6;

    test = "# CHARACTER NAME  CLASS AC HITS STATUS";
    alphabet.drawString (test, column, row++, gc);

    for (int i = 0; i < 6; i++)
    {
      Character character = characters.get (i);
      test = String.format ("%d %-15.15s %1.1s-%3.3s %2d  %3d   %3d", i + 1, character.name,
          character.alignment, character.characterClass, character.armourClass, character.hpLeft,
          character.hpMax);
      alphabet.drawString (test, column, row++, gc);
    }
  }
}
