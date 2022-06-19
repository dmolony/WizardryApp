package com.bytezone.wizardry;

import com.bytezone.wizardry.data.Monster;
import com.bytezone.wizardry.data.WizardryData;
import com.bytezone.wizardry.graphics.ImageGraphic;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

// -----------------------------------------------------------------------------------//
public class MonsterPane extends DataPane
// -----------------------------------------------------------------------------------//
{
  private final Canvas canvas;
  private WizardryData wizardry;

  private MonsterPane1 monsterPane1 = new MonsterPane1 ();
  private MonsterPane2 monsterPane2 = new MonsterPane2 ();
  private MonsterPane3 monsterPane3 = new MonsterPane3 ();
  private MonsterPane4 monsterPane4 = new MonsterPane4 ();
  private MonsterPane5 monsterPane5 = new MonsterPane5 ();

  // ---------------------------------------------------------------------------------//
  public MonsterPane ()
  // ---------------------------------------------------------------------------------//
  {
    canvas = new Canvas (280, 200);

    setAllColumnConstraints (40, 20);                     // 40 columns x 20 pixels
    setAllRowConstraints (26, getRowHeight ());           // make all rows the same height
    setGridLinesVisible (false);
    setPadding (defaultInsets);

    setLayout (monsterPane1, 0, 0, 2, 9);
    setLayout (monsterPane2, 0, 10, 2, 14);
    setLayout (monsterPane3, 7, 21, 2, 5);
    setLayout (monsterPane4, 9, 10, 2, 8);
    setLayout (monsterPane5, 15, 10, 2, 8);

    GridPane.setConstraints (canvas, 10, 0);
    GridPane.setColumnSpan (canvas, 3);
    GridPane.setRowSpan (canvas, 7);

    getChildren ().addAll (monsterPane1, monsterPane2, monsterPane3, monsterPane4, monsterPane5);
    getChildren ().add (canvas);
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getRows ()
  // ---------------------------------------------------------------------------------//
  {
    return 30;
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public int getColumns ()
  // ---------------------------------------------------------------------------------//
  {
    return 40;
  }

  // ---------------------------------------------------------------------------------//
  public void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    monsterPane1.setWizardry (wizardry);
    monsterPane2.setWizardry (wizardry);
    monsterPane3.setWizardry (wizardry);
    monsterPane4.setWizardry (wizardry);
    monsterPane5.setWizardry (wizardry);

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
    monsterPane4.update (monster);
    monsterPane5.update (monster);

    if (wizardry.getScenarioId () > 3)
      return;

    ImageGraphic imageGraphic = new ImageGraphic (wizardry.getImage (monster.image));
    imageGraphic.setColor (Color.WHITE);
    imageGraphic.draw (canvas, 4);
  }
}
