package com.bytezone.wizardry;

import javafx.geometry.Pos;

// -----------------------------------------------------------------------------------//
public class DataLayout
// -----------------------------------------------------------------------------------//
{
  int column;
  int row;
  int rows;
  Pos alignment;
  int columnSpan;
  String[] labels;

  // ---------------------------------------------------------------------------------//
  public DataLayout (int column, int row, int rows, Pos alignment)
  // ---------------------------------------------------------------------------------//
  {
    this (column, row, rows, alignment, 1);
  }

  // ---------------------------------------------------------------------------------//
  public DataLayout (int column, int row, int rows, Pos alignment, int columnSpan)
  // ---------------------------------------------------------------------------------//
  {
    this.column = column;
    this.row = row;
    this.rows = rows;
    this.alignment = alignment;
    this.columnSpan = columnSpan;
  }
}
