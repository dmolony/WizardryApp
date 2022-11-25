package com.bytezone.wizardry;

import com.bytezone.appbase.DataPane;
import com.bytezone.wizardry.data.Monster;
import com.bytezone.wizardry.data.WizardryData;
import com.bytezone.wizardry.graphics.ImageGraphic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class MonstersRootPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private final Canvas canvas;
  private WizardryData wizardry;

  private MonsterPane1 monsterPane1 = new MonsterPane1 ();
  private MonsterPane2 monsterPane2 = new MonsterPane2 ();
  private MonsterPane3 monsterPane3 = new MonsterPane3 ();
  private ResistancePane resistancePane = new ResistancePane ();
  private PropertyPane propertyPane = new PropertyPane ();

  // ---------------------------------------------------------------------------------//
  public MonstersRootPane ()
  // ---------------------------------------------------------------------------------//
  {
    super (40, 26);                             // columns, rows

    setAllColumnConstraints (20);               // all columns 20 pixels wide
    setPadding (defaultInsets);                 // only the root pane has insets

    setLayout (monsterPane1, 0, 0);
    setLayout (monsterPane2, 0, 10);
    setLayout (monsterPane3, 7, 21);
    setLayout (resistancePane, 9, 10);
    setLayout (propertyPane, 15, 10);

    getChildren ().addAll (monsterPane1, monsterPane2, monsterPane3, resistancePane, propertyPane);

    canvas = new Canvas (280, 200);

    GridPane.setConstraints (canvas, 10, 0);
    GridPane.setColumnSpan (canvas, 3);
    GridPane.setRowSpan (canvas, 7);

    getChildren ().add (canvas);
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    monsterPane1.setWizardry (wizardry);
    monsterPane2.setWizardry (wizardry);
    monsterPane3.setWizardry (wizardry);
    resistancePane.setWizardry (wizardry);
    propertyPane.setWizardry (wizardry);

    GraphicsContext gc = canvas.getGraphicsContext2D ();
    gc.setFill (Color.BLACK);
    gc.fillRect (0, 0, canvas.getWidth (), canvas.getHeight ());
  }

  // ---------------------------------------------------------------------------------//
  void update (Monster monster)
  // ---------------------------------------------------------------------------------//
  {
    monsterPane1.update (monster);
    monsterPane2.update (monster);
    monsterPane3.update (monster);
    resistancePane.update (monster.resistance);
    propertyPane.update (monster.properties);

    if (wizardry.getScenarioId () <= 3)
    {
      ImageGraphic imageGraphic = new ImageGraphic (wizardry.getImage (monster.image));
      imageGraphic.setColor (Color.WHITE);
      imageGraphic.draw (canvas, 4);
    }
  }
}
