package com.bytezone.wizardry.origin;

import java.util.Random;

import com.bytezone.wizardry.origin.Walls.Wall;

import javafx.scene.canvas.GraphicsContext;

// -----------------------------------------------------------------------------------//
public class MazeLevel
// -----------------------------------------------------------------------------------//
{
  private static final String line =
      "---+------------------------------------------------------------+\n";
  private static final Random random = new Random ();

  public final int displayLevel;                 // starts at 1
  private WizardryOrigin wizardry;

  private MazeCell[][] mazeCells = new MazeCell[20][20];

  private boolean[][] lair;
  private byte[][] sqrextra;
  private Special[] specials;
  private EnemyOdds[] enemyOdds;

  // ---------------------------------------------------------------------------------//
  public MazeLevel (WizardryOrigin wizardry, int displayLevel, DataBlock dataBlock)
  // ---------------------------------------------------------------------------------//
  {
    this.wizardry = wizardry;
    this.displayLevel = displayLevel;

    byte[] buffer = dataBlock.buffer;
    int offset = dataBlock.offset;

    Wall[][] west = addWalls (buffer, offset);
    Wall[][] south = addWalls (buffer, offset + 0x78);
    Wall[][] east = addWalls (buffer, offset + 0xF0);
    Wall[][] north = addWalls (buffer, offset + 0x168);

    lair = addLairs (buffer, offset + 0x1E0);
    sqrextra = getSquareExtras (buffer, offset + 0x230);
    specials = getSpecials (buffer, offset + 0x2F8);
    enemyOdds = getEnemyOdds (buffer, offset + 0x360);

    for (int col = 0; col < 20; col++)
      for (int row = 0; row < 20; row++)
      {
        Location location = new Location (displayLevel, col, row);
        Walls walls = new Walls (west[col][row], south[col][row], east[col][row], north[col][row]);
        MazeCell mazeCell = new MazeCell (location, walls, lair[col][row]);

        int index = sqrextra[col][row];
        specials[index].addLocation (location);

        if (index != 0)
          mazeCell.addExtra (specials[index]);

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
    if (location.getLevel () != this.displayLevel)
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
  public Special getSpecial (int index)
  // ---------------------------------------------------------------------------------//
  {
    return specials[index];
  }

  // ---------------------------------------------------------------------------------//
  public Special[] getSpecials ()
  // ---------------------------------------------------------------------------------//
  {
    return specials;
  }

  // ---------------------------------------------------------------------------------//
  public EnemyOdds[] getEnemyOdds ()
  // ---------------------------------------------------------------------------------//
  {
    return enemyOdds;
  }

  // ---------------------------------------------------------------------------------//
  public int getRandomMonster ()
  // ---------------------------------------------------------------------------------//
  {
    int encounterType = 0;
    while (random.nextInt (4) == 2 && encounterType < 2)
      ++encounterType;

    return enemyOdds[encounterType].getRandomMonster ();
  }

  // ---------------------------------------------------------------------------------//
  public int validateGroupSize (int howMany)
  // ---------------------------------------------------------------------------------//
  {
    if (howMany > displayLevel + 4)
      howMany = displayLevel + 4;

    if (howMany > 9)
      howMany = 9;

    if (howMany < 1)
      howMany = 1;

    return howMany;
  }

  // ---------------------------------------------------------------------------------//
  private Wall[][] addWalls (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    Wall[][] walls = new Wall[20][20];

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

    return walls;
  }

  // ---------------------------------------------------------------------------------//
  private boolean[][] addLairs (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    boolean[][] lair = new boolean[20][20];

    for (int col = 0; col < 20; col++)
    {
      int val = Utility.readTriple (buffer, ptr + col * 4) & 0x0FFFFF;
      for (int row = 0; row < 20; row++)
      {
        lair[col][row] = (val & 0x01) == 1;
        val >>>= 1;
      }
    }

    return lair;
  }

  // ---------------------------------------------------------------------------------//
  private byte[][] getSquareExtras (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    byte[][] sqrextra = new byte[20][20];

    for (int col = 0; col < 20; col++)
      for (int row = 0; row < 20; row += 2)
      {
        sqrextra[col][row] = (byte) (buffer[ptr] & 0x0F);
        sqrextra[col][row + 1] = (byte) ((buffer[ptr] & 0xF0) >>> 4);
        ptr++;
      }

    return sqrextra;
  }

  // ---------------------------------------------------------------------------------//
  private Special[] getSpecials (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    Special[] specials = new Special[16];

    int pos = 0;
    for (int i = 0; i < 8; i++)
    {
      specials[pos] = new Special (wizardry, pos++, buffer, ptr);
      specials[pos] = new Special (wizardry, pos++, buffer, ptr);
    }

    return specials;
  }

  // ---------------------------------------------------------------------------------//
  private EnemyOdds[] getEnemyOdds (byte[] buffer, int ptr)
  // ---------------------------------------------------------------------------------//
  {
    EnemyOdds[] enemyCalc = new EnemyOdds[3];

    for (int i = 0; i < 3; i++)
    {
      enemyCalc[i] = new EnemyOdds (buffer, ptr);
      ptr += 10;
    }

    return enemyCalc;
  }

  // ---------------------------------------------------------------------------------//
  public void showOdds ()
  // ---------------------------------------------------------------------------------//
  {
    System.out.println ("+--------------------------------------------+");
    System.out.printf ("|                  Level %2d                  |%n", displayLevel);
    System.out.println ("+--------------------------------------------+");

    int group = 1;
    for (EnemyOdds odds : enemyOdds)
    {
      System.out.printf ("Group %d%n", group++);
      System.out.printf ("-------%n");
      odds.showOdds ();
      System.out.println ();
    }
  }

  // ---------------------------------------------------------------------------------//
  public String getText ()
  // ---------------------------------------------------------------------------------//
  {
    StringBuilder text = new StringBuilder ("*******  Level " + displayLevel + "  *******\n\n");

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
        if (sqrextra[col][row] == 0)
          text.append ("   ");
        else
          text.append (String.format (" %X ", sqrextra[col][row]));

      text.append ("|\n");
    }
    appendGridBottom (text);

    for (int i = 0; i < 16; i++)
      text.append (String.format ("%X  %s%n", i, specials[i]));

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
    return "Maze Level " + displayLevel;
  }
  /*
  BEGIN
    ENCB4RUN := TRUE;
    CLRRECT( 1, 11, 38, 4);
    MVCURSOR( 14, 12);
    PRINTSTR( 'AN ENCOUNTER');
    ENCTYPE := 1;
    
    WHILE ((RANDOM MOD 4) = 2) AND (ENCTYPE < 3) DO
      ENCTYPE := ENCTYPE + 1;
      
    WITH MAZE.ENMYCALC[ ENCTYPE] DO
      BEGIN
        ENCCALC := 0;
        
        WHILE ((RANDOM MOD 100) < PERCWORS) AND (ENCCALC < WORSE01) DO
          ENCCALC := ENCCALC + 1;
          
        ENEMYI := MINENEMY + (RANDOM MOD RANGE0N) + (MULTWORS * ENCCALC);
        
        IF CHSTALRM = 1 THEN
          ATTK012 := 2
        ELSE
          IF MAZE.FIGHTS[ MAZEX][ MAZEY] = 1 THEN
            IF FIGHTMAP[ MAZEX][ MAZEY] THEN
              ATTK012 := 2
            ELSE
              ATTK012 := 1
          ELSE
            ATTK012 := 0;
            
        ENEMYINX := ENEMYI;
        XGOTO := XCOMBAT;
        EXIT( RUNNER)
      END
  END;  (* ENCOUNTR *)
  */
}
