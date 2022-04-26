package com.bytezone.mazewalker;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

// -----------------------------------------------------------------------------------//
public class RecentFiles
// -----------------------------------------------------------------------------------//
{
  private static final String PREFS_FILE_NAME = "recentfiles";
  private static final int MAX_FILES = 10;

  private final List<FileNameSelectedListener> listeners = new ArrayList<> ();
  private final List<String> fileNames = new ArrayList<> ();
  private Menu menu;

  // ---------------------------------------------------------------------------------//
  public RecentFiles (Menu menu)
  // ---------------------------------------------------------------------------------//
  {
    this.menu = menu;
  }

  // ---------------------------------------------------------------------------------//
  public String getLastFileName ()
  // ---------------------------------------------------------------------------------//
  {
    if (fileNames.size () > 0)
      return fileNames.get (0);

    return null;
  }

  // ---------------------------------------------------------------------------------//
  public void addLastFileName (String lastFileName)
  // ---------------------------------------------------------------------------------//
  {
    int index = -1;
    for (int i = 0; i < fileNames.size (); i++)
    {
      if (fileNames.get (i).equals (lastFileName))
      {
        index = i;
        break;
      }
    }

    if (index >= 0)
      fileNames.remove (index);

    fileNames.add (0, lastFileName);
    setMenuItems ();
  }

  // ---------------------------------------------------------------------------------//
  public void save (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < MAX_FILES; i++)
    {
      String key = String.format ("%s%02d", PREFS_FILE_NAME, i);
      if (i < fileNames.size ())
        prefs.put (key, fileNames.get (i));
      else
        prefs.remove (key);          // erase any previous entries
    }
  }

  // ---------------------------------------------------------------------------------//
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < MAX_FILES; i++)
    {
      String fileName = prefs.get (String.format ("%s%02d", PREFS_FILE_NAME, i), "");

      if (fileName.isEmpty ())
        break;

      if (new File (fileName).exists ())
        fileNames.add (fileName);
    }

    setMenuItems ();
  }

  // ---------------------------------------------------------------------------------//
  private void setMenuItems ()
  // ---------------------------------------------------------------------------------//
  {
    menu.getItems ().clear ();

    for (String fileName : fileNames)
    {
      File file = new File (fileName);
      if (file.exists ())
      {
        MenuItem menuItem = new MenuItem (file.getName ());
        menu.getItems ().add (menuItem);
        menuItem.setUserData (fileName);
        menuItem.setOnAction (e -> select (e));
      }
    }
  }

  // ---------------------------------------------------------------------------------//
  private void select (ActionEvent e)
  // ---------------------------------------------------------------------------------//
  {
    MenuItem menuItem = (MenuItem) e.getSource ();
    String fileName = (String) menuItem.getUserData ();

    for (FileNameSelectedListener listener : listeners)
      listener.fileNameSelected (fileName);
  }

  // ---------------------------------------------------------------------------------//
  public void addListener (FileNameSelectedListener listener)
  // ---------------------------------------------------------------------------------//
  {
    if (!listeners.contains (listener))
      listeners.add (listener);
  }

  // ---------------------------------------------------------------------------------//
  public interface FileNameSelectedListener
  // ---------------------------------------------------------------------------------//
  {
    public void fileNameSelected (String fileName);
  }
}
