package com.bytezone.mazewalker.gui;

import java.util.List;
import java.util.Random;

import com.bytezone.wizardry.graphics.FontGraphic;
import com.bytezone.wizardry.graphics.ImageGraphic;
import com.bytezone.wizardry.origin.Character;
import com.bytezone.wizardry.origin.MazeLevel;
import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.PartyManager;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class Display extends Canvas
// -----------------------------------------------------------------------------------//
{
  private final static int CHEST = 18;
  private final static int GOLD = 19;
  private final static int IMAGE_SIZE = 2;

  private final WizardryOrigin wizardry;

  private final FontGraphic alphabet;
  private final FontGraphic graphics;
  private final ImageGraphic gold;
  private final ImageGraphic chest;

  private List<Monster> monsters;
  private List<Character> party;

  private Random random = new Random ();
  private Color color = Color.PALEGREEN;

  // ---------------------------------------------------------------------------------//
  public Display (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    super (565, 390);

    this.wizardry = wizardry;

    alphabet = new FontGraphic (wizardry.getFonts ().get (0));
    graphics = new FontGraphic (wizardry.getFonts ().get (1));
    monsters = wizardry.getMonsters ();

    gold = new ImageGraphic (wizardry.getImage (GOLD));
    chest = new ImageGraphic (wizardry.getImage (CHEST));

    party = new PartyManager (wizardry).getParty ();
  }

  // ---------------------------------------------------------------------------------//
  public void update (MazeLevel mazeLevel)
  // ---------------------------------------------------------------------------------//
  {
    GraphicsContext gc = getGraphicsContext2D ();

    alphabet.setColor (color);
    graphics.setColor (color);

    gc.setFill (Color.BLACK);
    gc.fillRect (0, 0, getWidth (), getHeight ());

    drawMonster (gc, color, mazeLevel);

    drawParty (gc);
    drawGrid (gc);
  }

  // ---------------------------------------------------------------------------------//
  private void drawSpecial (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    alphabet.drawString (gc, 11, 12, "COME TO KFEST 2022");
  }

  // ---------------------------------------------------------------------------------//
  private void drawGold (GraphicsContext gc, Color color)
  // ---------------------------------------------------------------------------------//
  {
    //    Image image = wizardry.getImage (GOLD);
    gold.setColor (color);
    gold.draw (this, IMAGE_SIZE, 16, 47);

    int row = 12;
    int column = 1;

    String text = String.format ("EACH SHARE IS WORTH %d GP!", random.nextInt (1000) + 1);
    alphabet.drawString (gc, column, row++, text);
  }

  // ---------------------------------------------------------------------------------//
  private void drawChest (GraphicsContext gc, Color color)
  // ---------------------------------------------------------------------------------//
  {
    //    Image image = wizardry.getImage (CHEST);
    chest.setColor (color);
    chest.draw (this, IMAGE_SIZE, 16, 47);

    int column = 13;

    int row = 1;
    alphabet.drawString (gc, column, row++, "FOR KILLING THE MONSTERS");
    alphabet.drawString (gc, column, row++, "EACH SURVIVOR GETS 99999");
    alphabet.drawString (gc, column, row++, "EXPERIENCE POINTS");

    row = 6;
    alphabet.drawString (gc, column, row++, "A CHEST! YOU MAY:");

    row = 8;
    alphabet.drawString (gc, column, row++, "O)PEN     C)ALFO   L)EAVE");
    alphabet.drawString (gc, column, row++, "I)NSPECT  D)ISARM");
  }

  // ---------------------------------------------------------------------------------//
  private void drawMonster (GraphicsContext gc, Color color, MazeLevel mazeLevel)
  // ---------------------------------------------------------------------------------//
  {
    Monster monster = monsters.get (mazeLevel.getRandomMonster ());

    //    Image image = wizardry.getImage (monster.image);
    ImageGraphic image = new ImageGraphic (wizardry.getImage (monster.image));
    image.setColor (color);
    image.draw (this, IMAGE_SIZE, 16, 47);

    int howMany = monster.getGroupSize (mazeLevel);

    long totalExperience = howMany * monster.experiencePoints;

    int row = 1;
    drawMonsterGroup (gc, monster, row++, howMany);

    int chums = 0;
    int maxChums = mazeLevel.displayLevel < 3 ? mazeLevel.displayLevel : 3;

    while (chums < maxChums)
    {
      if (monster.partnerId == 0 || random.nextInt (100) >= monster.partnerOdds)
        break;

      Monster chum = wizardry.getMonster (monster.partnerId);
      howMany = chum.getGroupSize (mazeLevel);
      drawMonsterGroup (gc, chum, row++, howMany);
      totalExperience += howMany * chum.experiencePoints;
      monster = chum;
      ++chums;
    }

    drawOptions (gc);

    alphabet.drawString (gc, 7, 12, String.format ("TOTAL EXPERIENCE : %,d", totalExperience));
  }

  // ---------------------------------------------------------------------------------//
  private void drawMonsterGroup (GraphicsContext gc, Monster monster, int row, int howMany)
  // ---------------------------------------------------------------------------------//
  {
    int column = 13;

    String text = String.format ("%d) %d %s (%d)", row, howMany,
        howMany == 1 ? monster.name : monster.namePlural, monster.id);
    alphabet.drawString (gc, column, row++, text);
  }

  // ---------------------------------------------------------------------------------//
  private void drawFriendly (GraphicsContext gc, Monster monster)
  // ---------------------------------------------------------------------------------//
  {
    int column = 1;
    int row = 11;

    alphabet.drawString (gc, column, row++, "A FRIENDLY GROUP OF " + monster.genericNamePlural);
    alphabet.drawString (gc, column, row++, "THEY HAIL YOU IN WELCOME!");
    row++;
    alphabet.drawString (gc, column, row++, "YOU MAY F)IGHT OR L)EAVE IN PEACE");
  }

  // ---------------------------------------------------------------------------------//
  private void drawMoves (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    int row = 1;
    int column = 13;

    alphabet.drawString (gc, column, row++, "F)ORWARD  C)AMP    S)TATUS");
    alphabet.drawString (gc, column, row++, "L)EFT     Q)UICK   A<-W->D");
    alphabet.drawString (gc, column, row++, "R)IGHT    T)IME    CLUSTER");
    alphabet.drawString (gc, column, row++, "K)ICK     I)NSPECT");
    row += 2;
    alphabet.drawString (gc, column, row++, "SPELLS :");
  }

  // ---------------------------------------------------------------------------------//
  private void drawOptions (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    int column = 13;
    int row = 6;

    int whoFights = random.nextInt (party.size ());
    String characterName = party.size () == 0 ? "NOBODY" : party.get (whoFights).name;
    alphabet.drawString (gc, column, row++, String.format ("%s'S OPTIONS", characterName));

    row++;
    alphabet.drawString (gc, column, row++, "F)IGHT  S)PELL  P)ARRY");
    alphabet.drawString (gc, column, row++, "R)UN    U)SE    D)ISPELL");
  }

  // ---------------------------------------------------------------------------------//
  private void drawParty (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    int column = 1;
    int row = 16;

    alphabet.drawString (gc, column, row++, "# CHARACTER NAME  CLASS AC HITS STATUS");

    int position = 1;
    for (Character character : party)
    {
      String text = String.format ("%d %-15.15s %1.1s-%3.3s %2s  %3d%1.1s  %3d",  //
          position++,                                                             //
          character.name,                                                         //
          character.alignment,                                                    //
          character.characterClass,                                               //
          character.armourClass <= -10 ? "LO" : character.armourClass + "",       //
          character.hpLeft,                                                       //
          character.getRegenerationSign (),                                       //
          character.hpMax);

      alphabet.drawString (gc, column, row++, text);
    }
  }

  // ---------------------------------------------------------------------------------//
  private void drawTomb (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    int column = 2;
    int row = 2;

    graphics.drawString (gc, column, row++, "+,-.");
    graphics.drawString (gc, column, row++, "/012");
    graphics.drawString (gc, column, row++, "3456");
    graphics.drawString (gc, column, row++, "789:");
    graphics.drawString (gc, column, row++, ";<=>");
    graphics.drawString (gc, column, row++, "?XYZ");

    column++;
    row -= 2;
    alphabet.drawString (gc, column, row, "27");

    column += 4;
    row -= 3;
    alphabet.drawString (gc, column, row, "FRED");
  }

  // ---------------------------------------------------------------------------------//
  private void drawGrid (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    int column = 0;
    int row = 0;

    String line1 = "!XXXXXXXXXXX[XXXXXXXXXXXXXXXXXXXXXXXXXX#";
    String line2 = "$           \\                          $";
    String line3 = "$           ]XXXXXXXXXXXXXXXXXXXXXXXXXX(";
    String line4 = "'XXXXXXXXXXX^XXXXXXXXXXXXXXXXXXXXXXXXXX(";
    String line5 = "$                                      $";
    String line6 = "'XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX(";
    String line7 = "%XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX&";

    graphics.drawString (gc, column, row++, line1);
    for (int i = 0; i < 4; i++)
      graphics.drawString (gc, column, row++, line2);
    graphics.drawString (gc, column, row++, line3);
    for (int i = 0; i < 4; i++)
      graphics.drawString (gc, column, row++, line2);
    graphics.drawString (gc, column, row++, line4);
    for (int i = 0; i < 4; i++)
      graphics.drawString (gc, column, row++, line5);
    graphics.drawString (gc, column, row++, line6);
    for (int i = 0; i < 7; i++)
      graphics.drawString (gc, column, row++, line5);
    graphics.drawString (gc, column, row++, line7);
  }
}
