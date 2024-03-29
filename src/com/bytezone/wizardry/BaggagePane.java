package com.bytezone.wizardry;

import com.bytezone.appbase.BorderedDataPane;
import com.bytezone.appbase.DataLayout;
import com.bytezone.wizardry.data.Character;
import com.bytezone.wizardry.data.Character.Possession;
import com.bytezone.wizardry.data.Item;
import com.bytezone.wizardry.data.WizardryData;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

// -----------------------------------------------------------------------------------//
public class BaggagePane extends BorderedDataPane
// -----------------------------------------------------------------------------------//
{
  private TextField[] items;
  private TextField[] values;

  private CheckBox[] equipped;
  private CheckBox[] cursed;
  private CheckBox[] identified;

  private WizardryData wizardry;

  // ---------------------------------------------------------------------------------//
  public BaggagePane ()
  // ---------------------------------------------------------------------------------//
  {
    super (6, 9);                             // columns, rows

    setColumnConstraints (70, 155, 20, 20, 20, 80);

    String[] headings = { "Item", "Eq", "Cu", "Id", "Value" };

    String[] possessionLabels = new String[8];
    for (int i = 0; i < possessionLabels.length; i++)
      possessionLabels[i] = "# " + (i + 1);

    createLabelsHorizontal (headings, 1, 0, HPos.CENTER);
    createLabelsVertical (possessionLabels, 0, 1, HPos.RIGHT);

    DataLayout dataLayout =
        new DataLayout (1, 1, possessionLabels.length, Pos.CENTER_LEFT, false);

    items = createTextFields (dataLayout);
    equipped = createCheckBoxes (dataLayout, Pos.CENTER);
    cursed = createCheckBoxes (dataLayout);
    identified = createCheckBoxes (dataLayout);
    values = createTextFields (dataLayout, Pos.CENTER_RIGHT);
  }

  // ---------------------------------------------------------------------------------//
  void setWizardry (WizardryData wizardry)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    reset (items);
    reset (values);
    reset (equipped);
    reset (cursed);
    reset (identified);
  }

  // ---------------------------------------------------------------------------------//
  void update (Character character)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < 8; i++)
      if (i < character.possessionsCount)
      {
        Possession possession = character.possessions.get (i);
        Item item = wizardry.getItem (possession.id ());
        if (item == null)
        {
          System.out.printf ("%d null item #%d ", character.id, possession.id ());
          break;
        }

        setText (items[i], item.name);
        setText (values[i], item.price);    // formatted as number

        equipped[i].setSelected (possession.equipped ());
        cursed[i].setSelected (possession.cursed ());
        identified[i].setSelected (possession.identified ());

        cursed[i].setIndeterminate (false);
      }
      else
      {
        setText (items[i], "");
        setText (values[i], "");

        equipped[i].setSelected (false);
        cursed[i].setSelected (false);
        identified[i].setSelected (false);

        cursed[i].setIndeterminate (false);
      }
  }
}
