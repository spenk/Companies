import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Filer
{
  public String[] readLine(String find, String filename)
  {
    String lr = "";
    File file = new File(filename + ".txt");
    try
    {
      BufferedReader in = new BufferedReader(new FileReader(file));

      String line = in.readLine();

      while (line != null) {
        if (line.contains(find)) {
          if (lr.equals(""))
            lr = line;
          else {
            lr = lr + " " + line;
          }
        }
        line = in.readLine();
      }
      in.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    String[] lineresult = lr.split(" ");
    return lineresult;
  }

  public void writeLine(String line, String filename)
  {
    try {
      FileWriter fstream = new FileWriter(filename + ".txt", true);
      BufferedWriter out = new BufferedWriter(fstream);
      out.write(line);
      out.newLine();
      out.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }

  public void deleteLine(String deleteline, String filename) {
    try {
      File file = new File(filename + ".txt");
      if (!file.isFile()) {
        System.out.println("Parameter is not an existing file");
      }

      File tempFile = new File(filename + ".tmp");
      FileInputStream fstreamer = new FileInputStream(filename + ".txt");
      DataInputStream in = new DataInputStream(fstreamer);
      BufferedReader br = new BufferedReader(new InputStreamReader(in));
      FileWriter fstream = new FileWriter(filename + ".tmp");
      BufferedWriter out = new BufferedWriter(fstream);

      String line = null;

      while ((line = br.readLine()) != null)
      {
        if (!line.trim().contains(deleteline)) {
          out.write(line);
          out.newLine();
          out.flush();
        }
      }
      out.close();
      br.close();

      if (!file.delete()) {
        System.out.println("Could not delete file");
      }
      else {
        file.delete();
      }

      if (!tempFile.renameTo(file));
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
    catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}