package com.bytezone.wizardry.disk;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import com.bytezone.filesystem.AppleFile;
import com.bytezone.filesystem.AppleFileSystem;
import com.bytezone.filesystem.FileSystemFactory;
import com.bytezone.filesystem.FsPascal;

// -----------------------------------------------------------------------------------//
public class WizardryDisk
// -----------------------------------------------------------------------------------//
{
  FsPascal fs;
  List<AppleFile> files;

  // ---------------------------------------------------------------------------------//
  public WizardryDisk (String fileName)
  // ---------------------------------------------------------------------------------//
  {
    File file = new File (fileName);
    if (!file.exists () || !file.isFile ())
    {
      System.out.println ("File does not exist");
      return;
    }

    try
    {
      byte[] diskBuffer = Files.readAllBytes (file.toPath ());
      for (AppleFileSystem fs : FileSystemFactory.getFileSystems ("Wizardry", diskBuffer))
        if (fs instanceof FsPascal pascal)
        {
          this.fs = pascal;
          break;
        }
    }
    catch (IOException e)
    {
      e.printStackTrace ();
    }

    if (fs == null)
      return;

    files = fs.getFiles ();
  }

  // ---------------------------------------------------------------------------------//
  public byte[] getScenarioData ()
  // ---------------------------------------------------------------------------------//
  {
    for (AppleFile appleFile : files)
      if ("SCENARIO.DATA".equals (appleFile.getName ()))
        return appleFile.read ();

    return null;
  }

  // ---------------------------------------------------------------------------------//
  public byte[] getScenarioMessages ()
  // ---------------------------------------------------------------------------------//
  {
    for (AppleFile appleFile : files)
      if ("SCENARIO.MESGS".equals (appleFile.getName ()))
        return appleFile.read ();

    return null;
  }
}
