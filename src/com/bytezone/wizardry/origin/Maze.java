package com.bytezone.wizardry.origin;

import java.util.ArrayList;
import java.util.List;

// -----------------------------------------------------------------------------------//
public class Maze
// -----------------------------------------------------------------------------------//
{
  private static final int LEVEL_SIZE = 1024;
  private static final String[] squareType =
      { "Normal", "Stairs", "Pit", "Chute", "Spinner", "Darkness", "Teleport", "Ouch", "Elevator",
          "Rock/Water", "Fizzle", "Message/Item", "Monster" };

  enum Square
  {
    NORMAL, STAIRS, PIT, CHUTE, SPINNER, DARK, TRANSFER, OUCHY, BUTTONZ, ROCKWATE, FIZZLE, SCNMSG,
    ENCOUNTE
  }

  public enum Direction
  {
    NORTH, SOUTH, EAST, WEST
  }

  public List<MazeLevel> mazeLevels;

  // ---------------------------------------------------------------------------------//
  public Maze (byte[] buffer, int offset, int length)
  // ---------------------------------------------------------------------------------//
  {
    int levels = length / LEVEL_SIZE;
    mazeLevels = new ArrayList<> (levels);

    for (int i = 0; i < levels; i++)
      mazeLevels.add (new MazeLevel (i + 1, buffer, offset + i * LEVEL_SIZE, LEVEL_SIZE));
  }
}
