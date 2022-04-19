package com.bytezone.mazewalker.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.bytezone.wizardry.origin.Character;
import com.bytezone.wizardry.origin.Font;
import com.bytezone.wizardry.origin.Image;
import com.bytezone.wizardry.origin.Item;
import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.Possession;
import com.bytezone.wizardry.origin.WizardryOrigin;
import com.bytezone.wizardry.origin.WizardryOrigin.CharacterStatus;

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
public class FightPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private final int scenarioId;
  private final Canvas canvas;
  private final Font alphabet;
  private final Font graphics;

  private List<Monster> monsters;
  private List<Character> party;

  private Random random = new Random ();

  private List<DisplayColor> colors = Arrays.asList (     //
      new DisplayColor ("White", Color.WHITE),            //
      new DisplayColor ("Pale green", Color.PALEGREEN),   //
      new DisplayColor ("Moccasin", Color.MOCCASIN),      //
      new DisplayColor ("Goldenrod", Color.GOLDENROD),    //
      new DisplayColor ("Sky blue", Color.SKYBLUE));

  // ---------------------------------------------------------------------------------//
  public FightPane (WizardryOrigin wizardry, Stage stage)
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
    party = getParty ();

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

    switch (random.nextInt (2))
    {
      case 0:
        drawMonster (gc, displayColor.color);
        break;

      case 1:
        drawMoves (gc);
        break;

      default:
        System.out.println ("empty case");
        drawSpecial (gc);
    }

    drawParty (gc);
    drawGrid (gc);
  }

  // ---------------------------------------------------------------------------------//
  private void drawSpecial (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    String experience = String.format ("COME TO KFEST 2022");
    alphabet.drawString (experience, 11, 12, gc);
  }

  // ---------------------------------------------------------------------------------//
  private void drawMonster (GraphicsContext gc, Color color)
  // ---------------------------------------------------------------------------------//
  {
    Monster monster = monsters.get (random.nextInt (monsters.size ()));
    Image image = wizardry.getImage (monster.image);
    image.draw (canvas, 2, color, 16, 47);

    if (random.nextInt (10) == 0)
    {
      drawFriendly (gc, monster);
      return;
    }

    int row = 1;

    int howMany = monster.groupSize.roll ();
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

    drawOptions (gc);

    String experience = String.format ("TOTAL EXPERIENCE : %,d", totalExperience);
    alphabet.drawString (experience, 7, 12, gc);
  }

  // ---------------------------------------------------------------------------------//
  private void drawMonsterGroup (GraphicsContext gc, Monster monster, int row, int howMany)
  // ---------------------------------------------------------------------------------//
  {
    int column = 13;

    String text = String.format ("%d) %d %s (%d)", row, howMany,
        howMany == 1 ? monster.name : monster.namePlural, howMany);
    alphabet.drawString (text, column, row++, gc);
  }

  // ---------------------------------------------------------------------------------//
  private void drawFriendly (GraphicsContext gc, Monster monster)
  // ---------------------------------------------------------------------------------//
  {
    int row = 11;
    int column = 1;

    String text = "A FRIENDLY GROUP OF " + monster.genericNamePlural;
    alphabet.drawString (text, column, row++, gc);

    text = "THEY HAIL YOU IN WELCOME!";
    alphabet.drawString (text, column, row++, gc);

    row++;
    text = "YOU MAY F)IGHT OR L)EAVE IN PEACE";
    alphabet.drawString (text, column, row++, gc);
  }

  // ---------------------------------------------------------------------------------//
  private void drawMoves (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    int row = 1;
    int column = 13;

    String text = "F)ORWARD  C)AMP    S)TATUS";
    alphabet.drawString (text, column, row++, gc);

    text = "L)EFT     Q)UICK   A<-W->D";
    alphabet.drawString (text, column, row++, gc);

    text = "R)IGHT    T)IME    CLUSTER";
    alphabet.drawString (text, column, row++, gc);

    text = "K)ICK     I)NSPECT";
    alphabet.drawString (text, column, row++, gc);

    row += 2;
    text = "SPELLS :";
    alphabet.drawString (text, column, row++, gc);
  }

  // ---------------------------------------------------------------------------------//
  private void drawOptions (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    int column = 13;
    int row = 6;

    int whoFights = random.nextInt (party.size ());
    String characterName = party.size () == 0 ? "NOBODY" : party.get (whoFights).name;

    String text = String.format ("%s'S OPTIONS", characterName);
    alphabet.drawString (text, column, row++, gc);

    row++;
    text = "F)IGHT  S)PELL  P)ARRY";
    alphabet.drawString (text, column, row++, gc);

    text = "R)UN    U)SE    D)ISPELL";
    alphabet.drawString (text, column, row++, gc);
  }

  // ---------------------------------------------------------------------------------//
  private void drawParty (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    int column = 1;
    int row = 16;

    String text = "# CHARACTER NAME  CLASS AC HITS STATUS";
    alphabet.drawString (text, column, row++, gc);

    int ptr = 1;
    for (Character character : party)
    {
      String extra = getExtra (character);
      text = String.format ("%d %-15.15s %1.1s-%3.3s %2d  %3d%1.1s  %3d", ptr++, character.name,
          character.alignment, character.characterClass, character.armourClass, character.hpLeft,
          extra, character.hpMax);
      alphabet.drawString (text, column, row++, gc);
    }
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
  private String getExtra (Character character)
  // ---------------------------------------------------------------------------------//
  {
    for (Possession possession : character.possessions)
    {
      Item item = wizardry.getItem (possession.id ());
      if (possession.equipped () && item.special == 1)
        return "+";
    }

    return "";
  }

  // ---------------------------------------------------------------------------------//
  private List<Character> getParty ()
  // ---------------------------------------------------------------------------------//
  {
    List<Character> roster = new ArrayList<> (wizardry.getCharacters ());
    List<Character> party = new ArrayList<> ();

    while (party.size () < 6)
    {
      int max = 0;
      int ptr = -1;

      for (int i = 0; i < roster.size (); i++)
      {
        Character character = roster.get (i);

        if (character.status != CharacterStatus.OK)
          continue;

        if (character.hpMax > max)
        {
          max = character.hpMax;
          ptr = i;
        }
      }

      if (ptr < 0)          // nobody qualified
        break;

      party.add (roster.get (ptr));
      roster.remove (ptr);
    }

    return party;
  }
}
