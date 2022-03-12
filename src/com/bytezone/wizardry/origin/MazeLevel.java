package com.bytezone.wizardry.origin;

import com.bytezone.wizardry.origin.Maze.Square;
import com.bytezone.wizardry.origin.Maze.Wall;

// -----------------------------------------------------------------------------------//
public class MazeLevel
// -----------------------------------------------------------------------------------//
{
  private static final String line =
      "-----------------------------------------------------------------\n";

  private int level;

  private Wall[][] west = new Wall[20][20];
  private Wall[][] south = new Wall[20][20];
  private Wall[][] east = new Wall[20][20];
  private Wall[][] north = new Wall[20][20];

  private boolean[][] fights = new boolean[20][20];
  private byte[][] sqrextra = new byte[20][20];
  private Square[] squares = new Square[16];

  private int[] aux0 = new int[16];
  private int[] aux1 = new int[16];
  private int[] aux2 = new int[16];

  // ---------------------------------------------------------------------------------//
  public MazeLevel (int level, byte[] buffer, int offset, int length)
  // ---------------------------------------------------------------------------------//
  {
    this.level = level;

    addWalls (west, buffer, offset);
    addWalls (south, buffer, offset + 120);
    addWalls (east, buffer, offset + 240);
    addWalls (north, buffer, offset + 360);

    addEncounters (fights, buffer, offset + 480);
    addExtras (sqrextra, buffer, offset + 560);
    addSquares (squares, buffer, offset + 760);

    addAux (aux0, buffer, offset + 768);
    addAux (aux1, buffer, offset + 800);
    addAux (aux2, buffer, offset + 832);

    System.out.println (this);
  }

  // ---------------------------------------------------------------------------------//
  private void addWalls (Wall[][] walls, byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    for (int col = 0; col < 20; col++)
    {
      int row = 0;
      for (int i = 0; i < 5; i++)
      {
        int val = buffer[ptr++] & 0xFF;
        for (int j = 0; j < 4; j++)
        {
          int wall = (val & 0x03);                   // right to left ordering
          walls[col][row++] = Wall.values ()[wall];
          val >>>= 2;
        }
      }

      assert buffer[ptr] == 0;
      ptr++;                                        // skip last byte
    }
  }

  // ---------------------------------------------------------------------------------//
  private void addEncounters (boolean[][] fights, byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    for (int col = 0; col < 20; col++)
    {
      int val = Utility.readTriple (buffer, ptr + col * 4);
      for (int row = 0; row < 20; row++)
      {
        fights[col][row] = (val & 0x01) == 1;
        val >>>= 1;
      }
    }
  }

  // ---------------------------------------------------------------------------------//
  private void addExtras (byte[][] sqrextra, byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    for (int col = 0; col < 20; col++)
      for (int row = 0; row < 20; row += 2)
      {
        sqrextra[col][row] = (byte) (buffer[ptr] & 0x0F);
        sqrextra[col][row + 1] = (byte) ((buffer[ptr] & 0xF0) >>> 4);
        ptr++;
      }
  }

  // ---------------------------------------------------------------------------------//
  private void addSquares (Square[] squares, byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    int pos = 0;
    for (int i = 0; i < 8; i++)
    {
      int value = buffer[ptr++] & 0xFF;
      squares[pos++] = Maze.Square.values ()[(value & 0x0F)];
      squares[pos++] = Maze.Square.values ()[(value & 0xF0) >>> 4];
    }
  }

  // ---------------------------------------------------------------------------------//
  private void addAux (int[] aux, byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < 16; i++)
    {
      aux[i] = Utility.getShort (buffer, ptr);
      ptr += 2;
    }
  }

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ("*******  Level " + level + "  *******\n\n");

    text.append ("West walls\n");
    appendWalls (text, west);

    text.append ("South walls\n");
    appendWalls (text, south);

    text.append ("East walls\n");
    appendWalls (text, east);

    text.append ("North walls\n");
    appendWalls (text, north);

    text.append ("Enemy lairs\n");
    text.append (line);
    for (int row = 19; row >= 0; row--)
    {
      text.append (String.format ("%2d |", row));
      for (int col = 0; col < 20; col++)
        if (fights[col][row])
          text.append (" * ");
        else
          text.append ("   ");

      text.append ("|\n");
    }
    appendGridBottom (text);

    text.append ("Special squares\n");
    text.append (line);
    for (int row = 19; row >= 0; row--)
    {
      text.append (String.format ("%2d |", row));
      for (int col = 0; col < 20; col++)
        if (sqrextra[col][row] == 0)
          text.append ("   ");
        else
          text.append (String.format (" %X ", sqrextra[col][row]));

      text.append ("|\n");
    }
    appendGridBottom (text);

    for (int i = 0; i < 16; i++)
      if (squares[i] != Maze.Square.NORMAL)
        text.append (String.format ("%X   %-12s %04X  %04X  %04X%n", i, squares[i], aux0[i],
            aux1[i], aux2[i]));

    return text.toString ();
  }

  // ---------------------------------------------------------------------------------//
  private void appendWalls (StringBuilder text, Wall[][] walls)
  // ---------------------------------------------------------------------------------//
  {
    text.append (line);

    for (int row = 19; row >= 0; row--)
    {
      text.append (String.format ("%2d |", row));
      for (int col = 0; col < 20; col++)
      {
        if (walls[col][row] != Wall.OPEN)
          text.append (String.format (" %d ", walls[col][row].ordinal ()));
        else
          text.append ("   ");
      }
      text.append ("|\n");
    }

    appendGridBottom (text);
  }

  // ---------------------------------------------------------------------------------//
  private void appendGridBottom (StringBuilder text)
  // ---------------------------------------------------------------------------------//
  {
    text.append (line);
    text.append ("   | 0  0  0  0  0  0  0  0  0  0  1  1  1  1  1  1  1  1  1  1 |\n");
    text.append ("   | 0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5  6  7  8  9 |\n");
    text.append (line);
    text.append ("\n");
  }
}
