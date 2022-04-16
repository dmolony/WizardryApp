package com.bytezone.wizardry.origin;

import com.bytezone.wizardry.origin.Walls.Wall;

import javafx.scene.canvas.GraphicsContext;

// -----------------------------------------------------------------------------------//
public class MazeLevel
// -----------------------------------------------------------------------------------//
{
  private static final String line =
      "---+------------------------------------------------------------+\n";

  private int level;
  private WizardryOrigin wizardry;

  private MazeCell[][] mazeCells = new MazeCell[20][20];

  boolean[][] lair = new boolean[20][20];
  byte[][] sqrextra = new byte[20][20];

  Extra[] extra = new Extra[16];

  EnemyCalc[] enemyCalc = new EnemyCalc[3];

  // ---------------------------------------------------------------------------------//
  public MazeLevel (WizardryOrigin wizardry, int level, DataBlock dataBlock)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;
    this.level = level;

    byte[] buffer = dataBlock.buffer;
    int offset = dataBlock.offset;

    Wall[][] west = new Wall[20][20];
    Wall[][] south = new Wall[20][20];
    Wall[][] east = new Wall[20][20];
    Wall[][] north = new Wall[20][20];

    addWalls (west, buffer, offset);
    addWalls (south, buffer, offset + 0x78);
    addWalls (east, buffer, offset + 0xF0);
    addWalls (north, buffer, offset + 0x168);

    addLairs (buffer, offset + 0x1E0);
    addSquareExtras (buffer, offset + 0x230);

    addExtra (buffer, offset + 0x2F8);

    addEnemyCalc (buffer, offset + 0x360);

    for (int col = 0; col < 20; col++)
      for (int row = 0; row < 20; row++)
      {
        Location location = new Location (level, col, row);
        Walls walls = new Walls (west[col][row], south[col][row], east[col][row], north[col][row]);
        MazeCell mazeCell = new MazeCell (location, walls, lair[col][row]);

        int index = sqrextra[col][row];
        extra[index].addLocation (location);

        if (index != 0)
          mazeCell.addExtra (extra[index]);

        mazeCells[col][row] = mazeCell;
      }
  }

  // ---------------------------------------------------------------------------------//
  public void draw (GraphicsContext gc)
  // ---------------------------------------------------------------------------------//
  {
    for (int col = 0; col < 20; col++)
      for (int row = 0; row < 20; row++)
        mazeCells[col][row].draw (gc);
  }

  // ---------------------------------------------------------------------------------//
  public MazeCell getMazeCell (Location location)
  // ---------------------------------------------------------------------------------//
  {
    if (location.getLevel () != this.level)
    {
      System.out.println ("Wrong level");
      return null;
    }

    return mazeCells[location.getColumn ()][location.getRow ()];
  }

  // ---------------------------------------------------------------------------------//
  public MazeCell getMazeCell (int column, int row)
  // ---------------------------------------------------------------------------------//
  {
    while (column < 0)
      column += 20;
    while (column > 19)
      column -= 20;
    while (row < 0)
      row += 20;
    while (row > 19)
      row -= 20;

    return mazeCells[column][row];
  }

  // ---------------------------------------------------------------------------------//
  public Extra[] getExtra ()
  // ---------------------------------------------------------------------------------//
  {
    return extra;
  }

  // ---------------------------------------------------------------------------------//
  public EnemyCalc[] getEnemyCalc ()
  // ---------------------------------------------------------------------------------//
  {
    return enemyCalc;
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
          walls[col][row++] = Wall.values ()[val & 0x03];
          val >>>= 2;
        }
      }

      ptr++;                                        // skip last byte
    }
  }

  // ---------------------------------------------------------------------------------//
  private void addLairs (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    for (int col = 0; col < 20; col++)
    {
      int val = Utility.readTriple (buffer, ptr + col * 4) & 0x0FFFFF;
      for (int row = 0; row < 20; row++)
      {
        lair[col][row] = (val & 0x01) == 1;
        val >>>= 1;
      }
    }
  }

  // ---------------------------------------------------------------------------------//
  private void addSquareExtras (byte[] buffer, int ptr)
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
  private void addExtra (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    int pos = 0;
    for (int i = 0; i < 8; i++)
    {
      extra[pos] = new Extra (wizardry, pos++, buffer, ptr);
      extra[pos] = new Extra (wizardry, pos++, buffer, ptr);
    }
  }

  // ---------------------------------------------------------------------------------//
  private void addEnemyCalc (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < 3; i++)
    {
      enemyCalc[i] = new EnemyCalc (buffer, ptr);
      ptr += 10;
    }
  }

  // ---------------------------------------------------------------------------------//
  public String getText ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ("*******  Level " + level + "  *******\n\n");

    text.append ("West walls\n");
    appendWalls (text, Walls.WEST);

    text.append ("South walls\n");
    appendWalls (text, Walls.SOUTH);

    text.append ("East walls\n");
    appendWalls (text, Walls.EAST);

    text.append ("North walls\n");
    appendWalls (text, Walls.NORTH);

    text.append ("Enemy lairs\n");
    text.append (line);
    for (int row = 19; row >= 0; row--)
    {
      text.append (String.format ("%2d |", row));
      for (int col = 0; col < 20; col++)
      {
        MazeCell mazeCell = mazeCells[col][row];
        text.append (mazeCell.getLair () ? " * " : "   ");
      }

      text.append ("|\n");
    }
    appendGridBottom (text);

    text.append ("Special squares\n");
    text.append (line);
    for (int row = 19; row >= 0; row--)
    {
      text.append (String.format ("%2d |", row));
      for (int col = 0; col < 20; col++)
      {
        //        MazeCell mazeCell = mazeCells[col][row];
        //        Extra extra = mazeCell.getExtra ();
        if (sqrextra[col][row] == 0)
          text.append ("   ");
        else
          text.append (String.format (" %X ", sqrextra[col][row]));
        //        if (extra == null)
        //          text.append ("   ");
        //        else
        //          text.append (String.format (" %X ", extra.square.ordinal ()));
      }

      text.append ("|\n");
    }
    appendGridBottom (text);

    for (int i = 0; i < 16; i++)
      text.append (String.format ("%X  %s%n", i, extra[i]));

    return text.toString ();
  }

  // ---------------------------------------------------------------------------------//
  private void appendWalls (StringBuilder text, int direction)
  // ---------------------------------------------------------------------------------//
  {
    text.append (line);

    for (int row = 19; row >= 0; row--)
    {
      text.append (String.format ("%2d |", row));
      for (int col = 0; col < 20; col++)
      {
        MazeCell mazeCell = mazeCells[col][row];
        Wall wall = mazeCell.getWalls ().walls[direction];
        text.append (wall == Wall.OPEN ? "   " : String.format (" %d ", wall.ordinal ()));
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

  // ---------------------------------------------------------------------------------//
  @Override
  public String toString ()
  // ---------------------------------------------------------------------------------//
  {
    return "Maze Level " + level;
  }
}
