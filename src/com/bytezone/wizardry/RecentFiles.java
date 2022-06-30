package com.bytezone.wizardry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import com.bytezone.appbase.SaveState;

import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

// -----------------------------------------------------------------------------------//
public class RecentFiles implements SaveState
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
  File chooseFile (String title)
  // ---------------------------------------------------------------------------------//
  {
    FileChooser fileChooser = new FileChooser ();
    fileChooser.setTitle (title);

    String fileName = getLastFileName ();

    if (fileName.isEmpty ())
      fileChooser.setInitialDirectory (new File (System.getProperty ("user.home")));
    else
      fileChooser.setInitialDirectory (new File (fileName).getParentFile ());

    File file = fileChooser.showOpenDialog (null);

    if (file != null && file.getAbsolutePath ().equals (fileName))
      return null;

    return file;
  }

  // ---------------------------------------------------------------------------------//
  public String getLastFileName ()
  // ---------------------------------------------------------------------------------//
  {
    if (fileNames.size () > 0)
      return fileNames.get (0);

    return "";
  }

  // ---------------------------------------------------------------------------------//
  public void addLastFileName (String lastFileName)
  // ---------------------------------------------------------------------------------//
  {
    if (fileNames.size () > 0 && lastFileName.equals (fileNames.get (0)))
      return;

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
  @Override
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
  @Override
  public void restore (Preferences prefs)
  // ---------------------------------------------------------------------------------//
  {
    for (int i = 0; i < MAX_FILES; i++)
    {
      String fileName = prefs.get (String.format ("%s%02d", PREFS_FILE_NAME, i), "");

      if (fileName.isEmpty ())
        break;

      if (new File (fileName).exists () && !fileNames.contains (fileName))
        fileNames.add (fileName);
    }

    setMenuItems ();

    if (fileNames.size () > 0)
      notifyListeners (fileNames.get (0));
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
    notifyListeners ((String) menuItem.getUserData ());
  }

  // ---------------------------------------------------------------------------------//
  private void notifyListeners (String fileName)
  // ---------------------------------------------------------------------------------//
  {
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
