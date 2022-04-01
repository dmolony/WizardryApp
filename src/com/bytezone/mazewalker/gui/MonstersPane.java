package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

// -----------------------------------------------------------------------------------//
public class MonstersPane extends Pane
// -----------------------------------------------------------------------------------//
{
  private static final int GENERIC_NAME = 0;
  private static final int MONSTER_CLASS = 1;
  private static final int PARTNER = 2;
  private static final int GROUP_DICE = 3;
  private static final int HP_DICE = 4;
  private static final int RECSN = 5;
  private static final int RESISTANCE = 6;
  private static final int ABILITY = 7;
  private static final int ID = 8;
  private static final int MAGE_LEVEL = 9;
  private static final int PRIEST_LEVEL = 10;
  private static final int PARTNER_PCT = 11;
  private static final int MAGIC_RESIST = 12;
  private static final int BREATHE = 13;
  private static final int DRAIN = 14;
  private static final int REGEN = 15;
  private static final int TOTAL = 16;
  private static final int ARMOUR_CLASS = 17;
  private static final int GOLD_REWARDS = 18;
  private static final int CHEST_REWARDS = 19;
  private static final int UNIQUE = 20;
  private static final int IMAGE = 21;

  // Scenario #1 values
  private static int[] experience = {                                     //
      55, 235, 415, 230, 380, 620, 840, 520, 550, 350,                    // 00-09
      475, 515, 920, 600, 735, 520, 795, 780, 990, 795,                   // 10-19
      1360, 1320, 1275, 680, 960, 600, 755, 1120, 2075, 870,              // 20-29
      960, 600, 1120, 2435, 1080, 2280, 975, 875, 1135, 1200,             // 30-39
      620, 740, 1460, 1245, 960, 1405, 1040, 1220, 1520, 1000,            // 40-49
      960, 2340, 2160, 2395, 790, 1140, 1235, 1790, 1720, 2240,           // 50-59
      1475, 1540, 1720, 1900, 1240, 1220, 1020, 20435, 5100, 3515,        // 60-69
      2115, 2920, 2060, 2140, 1400, 1640, 1280, 4450, 42840, 3300,        // 70-79
      40875, 5000, 3300, 2395, 1935, 1600, 3330, 44090, 40840, 5200,      // 80-89
      4155, 3000, 9200, 3160, 7460, 7320, 15880, 1600, 2200, 1000,        // 90-99
      1900                                                                // 100 
  };

  String[] labelText = { "Generic name", "Monster class", "Partner", "Appear dice", "Hits dice",
      "Damage dice", "Resistance", "Properties", "ID", "Mage level", "Priest level", "Partner odds",
      "Magic resistance", "Breathe", "Level drain", "Regen", "Experience", "Armour class",
      "Gold rewards", "Chest rewards", "# Encs", "Image" };

  Label[] labels = new Label[labelText.length];
  TextField[] textOut = new TextField[labelText.length];
  ComboBox<Monster> monsters = new ComboBox<> ();

  GridPane gridPane = new GridPane ();
  WizardryOrigin wizardry;
  public Canvas canvas = new Canvas (210, 150);      // wh

  // ---------------------------------------------------------------------------------//
  public MonstersPane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    build ();

    gridPane.getColumnConstraints ().add (new ColumnConstraints (110));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (230));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (110));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (65));

    gridPane.setHgap (12);
    gridPane.setVgap (7);
    gridPane.setPadding (new Insets (15, 10, 12, 10));      // trbl

    getChildren ().add (new BorderPane (gridPane));
  }

  // ---------------------------------------------------------------------------------//
  private void build ()
  // ---------------------------------------------------------------------------------//
  {
    Label monsterLabel = new Label ("Monster");
    GridPane.setConstraints (monsterLabel, 0, 0);
    GridPane.setConstraints (monsters, 1, 0);
    GridPane.setHalignment (monsterLabel, HPos.RIGHT);

    GridPane.setConstraints (canvas, 1, 9);
    GraphicsContext gc = canvas.getGraphicsContext2D ();
    //    gc.setFill (Color.BLACK);
    //    gc.fillRect (0, 0, 140, 100);
    GridPane.setColumnSpan (canvas, 2);
    GridPane.setRowSpan (canvas, 5);

    gridPane.getChildren ().addAll (monsterLabel, monsters, canvas);

    ObservableList<Monster> list = FXCollections.observableArrayList ();
    list.addAll (wizardry.getMonsters ());

    monsters.setItems (list);
    monsters.setVisibleRowCount (30);

    monsters.getSelectionModel ().selectedItemProperty ()
        .addListener ( (options, oldValue, newValue) ->
        {
          Monster monster = newValue;
          if (monster != null)
          {
            textOut[GENERIC_NAME].setText (monster.genericName);
            textOut[HP_DICE].setText (monster.hitPoints.toString ());
            textOut[GROUP_DICE].setText (monster.groupSize.toString ());
            textOut[BREATHE].setText (monster.breathe + "");
            textOut[RECSN].setText (monster.damageDice);
            textOut[ID].setText (getText (monster.id));
            textOut[MAGE_LEVEL].setText (monster.mageSpells + "");
            textOut[PRIEST_LEVEL].setText (monster.priestSpells + "");
            textOut[DRAIN].setText (monster.drainAmt + "");
            textOut[REGEN].setText (monster.healPts + "");
            textOut[MAGIC_RESIST].setText (monster.unaffect + "%");
            textOut[RESISTANCE].setText (monster.resistanceText);
            textOut[ABILITY].setText (monster.propertyText);
            if (wizardry.getScenarioId () == 1)
              textOut[TOTAL].setText (getText (experience[monster.id]));
            else
              textOut[TOTAL].setText (getText (monster.expamt));
            textOut[MONSTER_CLASS].setText (WizardryOrigin.monsterClass[monster.monsterClass]);
            textOut[ARMOUR_CLASS].setText (getText (monster.armourClass));
            textOut[GOLD_REWARDS].setText (getText (monster.reward1));
            textOut[CHEST_REWARDS].setText (getText (monster.reward2));
            textOut[UNIQUE].setText (getText (monster.unique));
            textOut[IMAGE].setText (getText (monster.image));

            textOut[PARTNER].setText (wizardry.getMonsters ().get (monster.enemyTeam).name);
            textOut[PARTNER_PCT].setText (monster.teamPercentage + "%");

            wizardry.getImage (monster.image).draw (canvas.getGraphicsContext2D ());
          }
        });

    int col = 0;
    int row = 0;
    for (int i = 0; i < labels.length; i++)
    {
      row++;
      if (i == 8)
      {
        col += 2;
        row = 0;
      }

      labels[i] = new Label (labelText[i]);
      textOut[i] = new TextField ();

      GridPane.setConstraints (labels[i], col, row);
      GridPane.setConstraints (textOut[i], col + 1, row);

      textOut[i].setEditable (false);
      textOut[i].setFocusTraversable (false);

      if (i <= 7)
        textOut[i].setAlignment (Pos.CENTER_LEFT);
      else
        textOut[i].setAlignment (Pos.CENTER_RIGHT);

      GridPane.setHalignment (labels[i], HPos.RIGHT);

      gridPane.getChildren ().addAll (labels[i], textOut[i]);
    }
  }

  // ---------------------------------------------------------------------------------//
  private String getText (int value)
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%,7d", value);
  }
}
