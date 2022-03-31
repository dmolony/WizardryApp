package com.bytezone.mazewalker.gui;

import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.WizardryOrigin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

// -----------------------------------------------------------------------------------//
public class MonstersPane extends Pane
// -----------------------------------------------------------------------------------//
{
  String[] labelText = { "HP # dice", "HP # sides", "Breathe", "Armour class", "Damage # dice",
      "Mage level", "Priest level", "Level drain", "Heal", "Resist 1", "Resist 2", "Abilities",
      "Experience" };

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
  }
}
