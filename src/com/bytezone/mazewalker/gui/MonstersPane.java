package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
  private static final int HP_DICE = 1;
  private static final int GROUP_DICE = 2;
  private static final int PARTNER = 3;
  private static final int PARTNER_PCT = 4;
  private static final int BREATHE = 5;
  private static final int AC = 6;
  private static final int RECSN = 7;
  private static final int MAGE_LEVEL = 8;
  private static final int PRIEST_LEVEL = 9;
  private static final int DRAIN = 10;
  private static final int HEAL = 11;
  private static final int RESIST1 = 12;
  private static final int RESIST2 = 13;
  private static final int ABILITY = 14;
  private static final int TOTAL = 15;
  private static final int MONSTER_CLASS = 16;
  private static final int ARMOUR_CLASS = 17;
  private static final int GOLD_REWARDS = 18;
  private static final int CHEST_REWARDS = 19;

  String[] labelText = { "Generic name", "Hit points", "Group size", "Partner", "Partner odds",
      "Breathe", "Armour class", "Damage # dice", "Mage level", "Priest level", "Level drain",
      "Heal", "Resist 1", "Resist 2", "Abilities", "Experience", "Monster class", "Armour class",
      "Gold rewards", "Chest rewards" };

  Label[] labels = new Label[labelText.length];
  TextField[] textOut = new TextField[labelText.length];
  ComboBox<Monster> monsters = new ComboBox<> ();

  GridPane gridPane = new GridPane ();
  WizardryOrigin wizardry;

  // ---------------------------------------------------------------------------------//
  public MonstersPane (WizardryOrigin wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    build ();

    gridPane.getColumnConstraints ().add (new ColumnConstraints (135));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (80));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (60));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (80));
    gridPane.getColumnConstraints ().add (new ColumnConstraints (60));

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
    gridPane.getChildren ().addAll (monsterLabel, monsters);
    monsters.setVisibleRowCount (30);
    GridPane.setColumnSpan (monsters, 2);
    GridPane.setHalignment (monsterLabel, HPos.RIGHT);

    ObservableList<Monster> list = FXCollections.observableArrayList ();
    list.addAll (wizardry.getMonsters ());

    monsters.setItems (list);

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
            textOut[AC].setText (monster.armourClass + "");
            textOut[RECSN].setText (monster.recsn + "");
            textOut[MAGE_LEVEL].setText (monster.mageSpells + "");
            textOut[PRIEST_LEVEL].setText (monster.priestSpells + "");
            textOut[DRAIN].setText (monster.drainAmt + "");
            textOut[HEAL].setText (monster.healPts + "");
            textOut[RESIST1].setText (monster.unaffect + "");
            textOut[RESIST2].setText (monster.flags1 + "");
            textOut[ABILITY].setText (monster.flags2 + "");
            textOut[TOTAL].setText (getText (monster.expamt));
            textOut[MONSTER_CLASS].setText (getText (monster.monsterClass));
            textOut[ARMOUR_CLASS].setText (getText (monster.armourClass));
            textOut[GOLD_REWARDS].setText (getText (monster.reward1));
            textOut[CHEST_REWARDS].setText (getText (monster.reward2));

            textOut[PARTNER].setText (wizardry.getMonsters ().get (monster.enemyTeam).name);
            textOut[PARTNER_PCT].setText (getText (monster.teamPercentage));
          }
        });

    int col = 0;
    int row = 1;
    for (int i = 0; i < labels.length; i++)
    {
      labels[i] = new Label (labelText[i]);
      textOut[i] = new TextField ();

      row++;
      if (i == 12)
      {
        col += 3;
        row = 6;
      }

      GridPane.setConstraints (labels[i], col, row);
      GridPane.setConstraints (textOut[i], col + 1, row);

      textOut[i].setEditable (false);
      textOut[i].setFocusTraversable (false);

      if (i <= 3)
      {
        textOut[i].setAlignment (Pos.CENTER_LEFT);
        GridPane.setColumnSpan (textOut[i], 2);
      }
      else
        textOut[i].setAlignment (Pos.CENTER_RIGHT);

      GridPane.setHalignment (labels[i], HPos.RIGHT);

      gridPane.getChildren ().add (labels[i]);
      gridPane.getChildren ().add (textOut[i]);
    }
  }

  // ---------------------------------------------------------------------------------//
  private String getText (int value)
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%,7d", value);
  }
}
