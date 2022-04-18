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

  private List<DisplayColor> colors = Arrays.asList (     //
      new DisplayColor ("White", Color.WHITE),            //
      new DisplayColor ("Pale green", Color.PALEGREEN),   //
      new DisplayColor ("Moccasin", Color.MOCCASIN),      //
      new DisplayColor ("Goldenrod", Color.GOLDENROD),    //
      new DisplayColor ("Sky blue", Color.SKYBLUE));

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

    drawMonster (gc, displayColor.color);
    drawData (gc);
    drawGrid (gc);
  }

  // ---------------------------------------------------------------------------------//
  private void drawMonsters (GraphicsContext gc, Color color)
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

      int howMany = monster.groupSize.roll ();
      String test = String.format ("%d) %d %s (%d)", i + 1, howMany,
          howMany == 1 ? monster.name : monster.namePlural, howMany);
      alphabet.drawString (test, column, row++, gc);
    }
  }

  // ---------------------------------------------------------------------------------//
  private void drawMonster (GraphicsContext gc, Color color)
  // ---------------------------------------------------------------------------------//
  {
    Monster monster = monsters.get (random.nextInt (monsters.size ()));
    Image image = wizardry.getImage (monster.image);
    int howMany = monster.groupSize.roll ();
    image.draw (canvas, 2, color, 16, 47);

    int row = 1;
    long totalExperience = howMany * monster.expamt;

    drawMonsterGroup (gc, monster, row++, howMany);
    int totalMonsterGroups = 1;

    while (totalMonsterGroups < 4)
    {
      if (monster.enemyTeam == 0 || random.nextInt (100) > monster.teamPercentage)
        break;

      Monster monster2 = wizardry.getMonster (monster.enemyTeam);
      howMany = monster2.groupSize.roll ();
      drawMonsterGroup (gc, monster2, row++, howMany);
      totalExperience += howMany * monster2.expamt;
      monster = monster2;
      ++totalMonsterGroups;
    }

    String experience = String.format ("TOTAL EXPERIENCE : %,d", totalExperience);
    alphabet.drawString (experience, 7, 12, gc);
  }

  // ---------------------------------------------------------------------------------//
  private void drawMonsterGroup (GraphicsContext gc, Monster monster, int row, int howMany)
  // ---------------------------------------------------------------------------------//
  {
    int column = 13;

    String test = String.format ("%d) %d %s (%d)", row, howMany,
        howMany == 1 ? monster.name : monster.namePlural, howMany);
    alphabet.drawString (test, column, row++, gc);
  }

  // ---------------------------------------------------------------------------------//
  private void drawGrid (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    int column = 0;
    int row = 0;

    String topLine = "!XXXXXXXXXXX[XXXXXXXXXXXXXXXXXXXXXXXXXX#";
    String vert1 = "$           \\                          $";
    String half = "$           ]XXXXXXXXXXXXXXXXXXXXXXXXXX(";
    String mid1 = "'XXXXXXXXXXX^XXXXXXXXXXXXXXXXXXXXXXXXXX(";
    String vert2 = "$                                      $";
    String mid2 = "'XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX(";
    String botLine = "%XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX&";

    // top line
    graphics.drawString (topLine, column, row++, gc);

    // verticals
    for (int i = 0; i < 4; i++)
      graphics.drawString (vert1, column, row++, gc);

    // half line
    graphics.drawString (half, column, row++, gc);

    // verticals
    for (int i = 0; i < 4; i++)
      graphics.drawString (vert1, column, row++, gc);

    // middle line 1
    graphics.drawString (mid1, column, row++, gc);

    // verticals
    for (int i = 0; i < 4; i++)
      graphics.drawString (vert2, column, row++, gc);

    // middle line 2
    graphics.drawString (mid2, column, row++, gc);

    // verticals
    for (int i = 0; i < 7; i++)
      graphics.drawString (vert2, column, row++, gc);

    // bottom line
    graphics.drawString (botLine, column, row++, gc);
  }

  // ---------------------------------------------------------------------------------//
  private void drawData (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    int column = 13;
    int row = 6;

    String characterName = characters.size () == 0 ? "NOBODY" : characters.get (0).name;

    String test = String.format ("%s'S OPTIONS", characterName);
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
      if (i >= characters.size ())
        break;

      Character character = characters.get (i);
      test = String.format ("%d %-15.15s %1.1s-%3.3s %2d  %3d   %3d", i + 1, character.name,
          character.alignment, character.characterClass, character.armourClass, character.hpLeft,
          character.hpMax);
      alphabet.drawString (test, column, row++, gc);
    }
  }
}
