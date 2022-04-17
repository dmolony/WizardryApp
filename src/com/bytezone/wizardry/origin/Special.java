package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

import com.bytezone.wizardry.origin.WizardryOrigin.Square;
import com.bytezone.wizardry.origin.WizardryOrigin.Trade;

// -----------------------------------------------------------------------------------//
public class Special
// -----------------------------------------------------------------------------------//
{
  public static final String[] auxTypes = { "", "", "TRYGET", "WHOWADE", "DOSEARCH", "ITM2PASS",
      "CHKALIGN", "CHKAUX0", "BCK2SHOP", "LOOKOUT", "RIDDLES", "FEEIS", "", "PICTMESS", "ITMORTEL",
      "SPCMONST(CE)", "SPCMONST(CG)" };
  private final WizardryOrigin wizardry;

  public final Square square;
  public final int[] aux = new int[3];

  public final List<Location> locations = new ArrayList<> ();

  // ---------------------------------------------------------------------------------//
  public Special (WizardryOrigin wizardry, int index, byte[] buffer, int offset)
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
  public boolean isMessage ()
  // ---------------------------------------------------------------------------------//
  {
    return square == Square.SCNMSG && aux[2] <= 13;
  }

  // ---------------------------------------------------------------------------------//
  public String getLocationText ()
  // ---------------------------------------------------------------------------------//
  {
    if (square == Square.NORMAL || locations.size () == 0)
      return "";
    return locations.get (0).toString ().substring (5);
  }

  // ---------------------------------------------------------------------------------//
  public String getText ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder description = new StringBuilder ();

    switch (square)
    {
      case SCNMSG:
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

          case 4:                                 // GETYN / DOSEARCH
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
            // at least one party member must be carrying the item
            description.append ("Access requires : " + wizardry.getItemName (aux[0]));
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

          case 13:                              // PICTMESS
            if (aux[0] > 0)
              description.append ("Requires : " + wizardry.getItemName (aux[0]));
            else
              description.append ("PICTMESS but aux[0] = 0");
            break;

          case 14:                              // ITMORTEL
            int east = aux[0] / 100;
            int north = aux[0] % 100;
            String item = wizardry.getItemName (aux[1]);
            description
                .append (String.format ("Required : %s else teleport N%d E%d", item, north, east));
            break;

          case 15:                              // SPCMONST( CRYSEVIL)
            description.append ("SPCMONST (CRYSEVIL) : " + wizardry.getMonster (aux[1]));
            break;

          case 16:                              // SPCMONST( CRYSGOOD)
            description.append ("SPCMONST (CRYSGOOD) : " + wizardry.getMonster (aux[1]));
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
        description.append ("Spells fizzle out");
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
    String extra = locations.size () == 1 ? "" : "(" + locations.size () + ")";
    return String.format ("%-8s  %5d  %4d  %4d  %s", square, aux[0], aux[1], aux[2], extra);
  }
}
