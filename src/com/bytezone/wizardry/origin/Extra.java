package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.wizardry.origin.WizardryOrigin.Square;
import com.bytezone.wizardry.origin.WizardryOrigin.Trade;

// -----------------------------------------------------------------------------------//
public class Extra
// -----------------------------------------------------------------------------------//
{
  private final WizardryOrigin wizardry;

  public final Square square;
  public final int[] aux = new int[3];
  public final List<Location> locations = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  public Extra (WizardryOrigin wizardry, int index, byte[] buffer, int offset)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;

    byte b = buffer[offset + (index) / 2];
    int val = index % 2 == 0 ? b & 0x0F : (b & 0xF0) >>> 4;

    square = WizardryOrigin.Square.values ()[val];
    aux[0] = Utility.getSignedShort (buffer, offset + 8 + index * 2);
    aux[1] = Utility.getSignedShort (buffer, offset + 40 + index * 2);
    aux[2] = Utility.getSignedShort (buffer, offset + 72 + index * 2);
  }

  // ---------------------------------------------------------------------------------//
  void addLocation (Location location)
  // ---------------------------------------------------------------------------------//
  {
    locations.add (location);
  }

  // ---------------------------------------------------------------------------------//
  public Square getSquare ()
  // ---------------------------------------------------------------------------------//
  {
    return square;
  }

  // ---------------------------------------------------------------------------------//
  public int[] getAux ()
  // ---------------------------------------------------------------------------------//
  {
    return aux;
  }

  // ---------------------------------------------------------------------------------//
  public boolean is (Square square)
  // ---------------------------------------------------------------------------------//
  {
    return this.square == square;
  }

  // ---------------------------------------------------------------------------------//
  public String getText ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder description = new StringBuilder ();

    switch (square)
    {
      case SCNMSG:
        //        setText (textOut6[i], aux[1]);

        switch (aux[2])
        {
          case 1:                                 // conditional
            switch (aux[0])
            {
              case 0:
                description.append ("Never shown");
                break;
              case -1:
                description.append ("Always shown");
                break;
              default:
                description.append (aux[0] + " left");
                break;
            }
            break;

          case 2:                                 // TRYGET               
            // special obtain (only blue ribbon so far)
            description.append ("Obtain : " + wizardry.getItem (aux[0]));
            break;

          case 3:                                 // WHOWADE
            if (aux[0] > 0)
              description.append ("Wade : " + wizardry.getItemName (aux[0]));
            break;

          case 4:                                 // GETYN
            if (aux[0] >= 0)
              description.append ("Encounter : " + wizardry.getMonster (aux[0]));
            else if (aux[0] > -1200)
              description.append ("Obtain : " + wizardry.getItem (aux[0] * -1 - 1000));
            else
            {
              Trade trade = wizardry.getItemTrade (aux[0]);
              description.append ("Trade : " + wizardry.getItemName (trade.item1 ()) + " for "
                  + wizardry.getItemName (trade.item2 ()));
            }
            break;

          case 5:                               // ITM2PASS
            description.append ("Requires : " + wizardry.getItemName (aux[0]));
            break;

          case 6:                               // CHKALIGN
            description.append ("Check alignment");
            break;

          case 7:                               // CHKAUX0
            description.append ("Check AUX0");
            break;

          case 8:                               // BCK2SHOP
            description.append ("Return to castle");
            break;

          case 9:                               // LOOKOUT
            description.append (String.format ("Look out : surrounded by fights"));
            break;

          case 10:                              // RIDDLES
            description.append ("Answer : " + wizardry.getMessageText (aux[0]));
            break;

          case 11:                              // FEEIS
            description.append ("Pay : " + wizardry.getMessageText (aux[0]));
            break;

          case 12:
            description.append ("12 = ??");
            break;

          case 13:
            if (aux[0] > 0)
              description.append ("Requires : " + wizardry.getItemName (aux[0]));
            else
              description.append ("13 = ??");
            break;

          case 14:
            description.append ("14 = ??");
            break;

          case 15:
            description.append ("15 = ??");
            break;

          case 16:
            description.append ("16 = ??");
            break;
        }
        break;

      case STAIRS:
        Location location = new Location (aux);
        description.append (String.format ("Stairs to : %s", location));
        break;

      case PIT:
        Damage damage = new Damage (aux);
        description.append (String.format ("Pit - %s", damage));
        break;

      case CHUTE:
        location = new Location (aux);
        description.append (String.format ("Chute to : %s", location));
        break;

      case SPINNER:
        description.append ("Spinner");
        break;

      case DARK:
        break;

      case TRANSFER:
        location = new Location (aux);
        description.append (String.format ("Teleport to : %s", location));
        break;

      case OUCHY:
        break;

      case BUTTONZ:
        description.append (String.format ("Elevator levels : %d to %d", aux[2], aux[1]));
        break;

      case ROCKWATE:
        description.append ("Rock/water");
        break;

      case FIZZLE:
        description.append ("Spells cannot be cast here");
        break;

      case ENCOUNTE:
        Monster monster = wizardry.getMonster (aux[2]);
        String when = aux[0] == -1 ? "always" : aux[0] + " left";
        description.append (String.format ("%s (%s)", monster, when));
        //          if (!lair)
        //            description.append ("\n\nError - room is not a LAIR");
        break;

      case NORMAL:
        break;
    }
    return description.toString ();
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return String.format ("%-8s  %5d  %4d  %4d  (%2d)", square, aux[0], aux[1], aux[2],
        locations.size ());
  }
}
