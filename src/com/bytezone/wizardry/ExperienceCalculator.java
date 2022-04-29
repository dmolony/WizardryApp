package com.bytezone.wizardry;

import com.bytezone.wizardry.origin.Monster;
import com.bytezone.wizardry.origin.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

// -----------------------------------------------------------------------------------//
public class ExperienceCalculator extends DataPane
// -----------------------------------------------------------------------------------//
{
  private static final int HP_DICE = 0;
  private static final int HP_SIDES = 1;
  private static final int BREATHE = 2;
  private static final int AC = 3;
  private static final int RECSN = 4;
  private static final int MAGE_LEVEL = 5;
  private static final int PRIEST_LEVEL = 6;
  private static final int DRAIN = 7;
  private static final int HEAL = 8;
  private static final int MAGIC_RESISTANCE = 9;
  private static final int RESISTANCE = 10;
  private static final int ABILITY = 11;
  private static final int TOTAL = 12;

  String[] labelText =
      { "HP # dice", "HP # sides", "Breathe", "Armour class", "Damage # dice", "Mage level",
          "Priest level", "Level drain", "Regen", "Resist 1", "Resist 2", "Abilities", "Total" };

  TextField[] textIn = new TextField[labelText.length];
  TextField[] textOut = new TextField[labelText.length];

  ComboBox<Monster> monsters = new ComboBox<> ();
  WizardryData wizardry;        // this hasn't been set yet - NPE

  // ---------------------------------------------------------------------------------//
  public ExperienceCalculator ()
  // ---------------------------------------------------------------------------------//
  {
    //    super (wizardry, stage);

    setColumnConstraints (125, 60, 80);

    LabelPlacement lp0 = new LabelPlacement (0, 0, HPos.RIGHT, 1);
    DataPlacement dp0 = new DataPlacement (1, 0, Pos.CENTER_LEFT, 1);
    createComboBox ("Monster", monsters, wizardry.getMonsters (),
        (options, oldValue, newValue) -> update (newValue), lp0, dp0);
    GridPane.setColumnSpan (monsters, 2);

    for (int i = 0; i < textIn.length; i++)
    {
      Label label = new Label (labelText[i]);
      textIn[i] = new TextField ();
      textOut[i] = new TextField ();

      GridPane.setConstraints (label, 0, i + 1);
      GridPane.setConstraints (textIn[i], 1, i + 1);
      GridPane.setConstraints (textOut[i], 2, i + 1);

      textOut[i].setEditable (false);
      textOut[i].setFocusTraversable (false);
      textOut[i].setAlignment (Pos.CENTER_RIGHT);
      GridPane.setHalignment (label, HPos.RIGHT);

      gridPane.getChildren ().add (label);
      if (i < textIn.length - 1)
        gridPane.getChildren ().add (textIn[i]);
      if (i != HP_DICE && i != BREATHE)
        gridPane.getChildren ().add (textOut[i]);

      textIn[i].setOnKeyTyped (e -> keyTyped (e));
    }

    monsters.getSelectionModel ().select (0);
  }

  // ---------------------------------------------------------------------------------//
  private void update (Monster monster)
  // ---------------------------------------------------------------------------------//
  {
    textIn[HP_DICE].setText (monster.hitPoints.level + "");
    textIn[HP_SIDES].setText (monster.hitPoints.faces + "");
    textIn[BREATHE].setText (monster.breathe + "");
    textIn[AC].setText (monster.armourClass + "");
    textIn[RECSN].setText (monster.damageDiceSize + "");
    textIn[MAGE_LEVEL].setText (monster.mageSpells + "");
    textIn[PRIEST_LEVEL].setText (monster.priestSpells + "");
    textIn[DRAIN].setText (monster.drain + "");
    textIn[HEAL].setText (monster.regen + "");
    textIn[MAGIC_RESISTANCE].setText (monster.unaffect + "");
    textIn[RESISTANCE].setText (monster.resistance + "");
    textIn[ABILITY].setText (monster.properties + "");

    if (wizardry.getScenarioId () > 1)
      textOut[TOTAL].setText (getText (monster.experiencePoints));
    else
      setExperienceTotal ();
  }

  // ---------------------------------------------------------------------------------//
  private void keyTyped (KeyEvent evt)
  // ---------------------------------------------------------------------------------//
  {
    if (evt.isShortcutDown () || evt.isControlDown () || evt.isMetaDown ())
      return;

    setExperienceTotal ();
  }

  // ---------------------------------------------------------------------------------//
  private void setExperienceTotal ()
  // ---------------------------------------------------------------------------------//
  {
    if (wizardry.getScenarioId () > 1)
      return;

    int[] values = new int[textIn.length];
    for (int i = 0; i < textIn.length; i++)
    {
      try
      {
        boolean negative = false;
        String text = textIn[i].getText ();
        if (text == null || text.isBlank ())
          text = "0";

        if (text.equals ("-"))
          continue;

        if (text.startsWith ("-"))
        {
          text = text.substring (1);
          negative = true;
        }

        values[i] = Integer.parseInt (text);
        if (negative)
          values[i] *= -1;
      }
      catch (NumberFormatException e)
      {
        System.out.printf ("rejected: [%s]%n", textIn[i].getText ());
        textIn[i].clear ();
      }
    }

    int expHitPoints = values[HP_DICE] * values[HP_SIDES] * (values[BREATHE] == 0 ? 20 : 40);
    int expAc = 40 * (11 - values[AC]);

    int expMage = getBonus (35, values[MAGE_LEVEL]);
    int expPriest = getBonus (35, values[PRIEST_LEVEL]);
    int expDrain = getBonus (200, values[DRAIN]);
    int expHeal = getBonus (90, values[HEAL]);

    int expDamage = values[RECSN] <= 1 ? 0 : getBonus (30, values[RECSN]);
    int expUnaffect =
        values[MAGIC_RESISTANCE] == 0 ? 0 : getBonus (40, (values[MAGIC_RESISTANCE] / 10 + 1));

    int expFlags1 = getBonus (35, Integer.bitCount (values[RESISTANCE] & 0x7E));    // 6 bits
    int expFlags2 = getBonus (40, Integer.bitCount (values[ABILITY] & 0x7F));       // 7 bits

    int total = expHitPoints + expAc + expMage + expPriest + expDrain + expHeal + expDamage
        + expUnaffect + expFlags1 + expFlags2;

    textOut[HP_SIDES].setText (getText (expHitPoints));
    textOut[AC].setText (getText (expAc));
    textOut[MAGE_LEVEL].setText (getText (expMage));
    textOut[PRIEST_LEVEL].setText (getText (expPriest));
    textOut[DRAIN].setText (getText (expDrain));
    textOut[HEAL].setText (getText (expHeal));
    textOut[RECSN].setText (getText (expDamage));
    textOut[MAGIC_RESISTANCE].setText (getText (expUnaffect));
    textOut[RESISTANCE].setText (getText (expFlags1));
    textOut[ABILITY].setText (getText (expFlags2));

    textOut[TOTAL].setText (getText (total));
  }

  // ---------------------------------------------------------------------------------//
  private int getBonus (int base, int multiplier)
  // ---------------------------------------------------------------------------------//
  {
    if (multiplier == 0)
      return 0;

    int total = base;
    while (multiplier > 1)
    {
      int part = total % 10000;   // get the last 4 digits

      multiplier--;
      total += total;             // double the value

      if (part >= 5000)           // mimics the wizardry bug
        total += 10000;           // yay, free points
    }

    return total;
  }
}
