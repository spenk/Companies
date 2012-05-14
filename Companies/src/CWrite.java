import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CWrite
{
  public String companyName(String player)
  {
    String cname = "";
    File file = new File("Company/players.txt");
    try
    {
      BufferedReader in = new BufferedReader(new FileReader(file));

      String line = in.readLine();

      while (line != null) {
        if (line.startsWith(player)) {
          cname = line;
        }
        line = in.readLine();
      }
      in.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return cname;
  }

  public double getStartCost() {
    double cost = 0;
    File file = new File("Company/settings.txt");
    try
    {
      BufferedReader in = new BufferedReader(new FileReader(file));

      String line = in.readLine();

      while (line != null) {
        if (line.startsWith("startCost")) {
          String[] l = line.split("=");
          cost = Double.valueOf(l[1]).doubleValue();
        }
        line = in.readLine();
      }
      in.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return cost;
  }

  public int getTopRank()
  {
    int trank = 0;
    File file = new File("Company/settings.txt");
    try
    {
      BufferedReader in = new BufferedReader(new FileReader(file));

      String line = in.readLine();

      while (line != null) {
        if (line.startsWith("TopRank")) {
          String[] l = line.split("=");
          trank = Integer.valueOf(l[1]).intValue();
        }
        line = in.readLine();
      }
      in.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return trank;
  }

  public String getcompanyName(String name) {
    String cname = "";
    File file = new File("Company/companies.txt");
    try
    {
      BufferedReader in = new BufferedReader(new FileReader(file));

      String line = in.readLine();

      while (line != null) {
        if (line.startsWith(name + "=")) {
          cname = line;
        }
        line = in.readLine();
      }
      in.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return cname;
  }
  public int pamount(Player player, String name) {
    int plist = 0;
    File file = new File("Company/players.txt");
    try
    {
      BufferedReader in = new BufferedReader(new FileReader(file));

      String line = in.readLine();

      while (line != null) {
        if (line.endsWith("=" + name)) {
          plist++;
        }
        line = in.readLine();
      }
      in.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return plist;
  }
  public String plist(Player player, String name) {
    String plist = "";
    File file = new File("Company/players.txt");
    try
    {
      BufferedReader in = new BufferedReader(new FileReader(file));

      String line = in.readLine();

      while (line != null) {
        if (line.endsWith("=" + name)) {
          String[] l = line.split("=");
          if (plist.equals(""))
            plist = l[0];
          else {
            plist = plist + ", " + l[0];
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
    return plist;
  }
  public String clist(Player player) {
    String clist = "";
    File file = new File("Company/companies.txt");
    try
    {
      BufferedReader in = new BufferedReader(new FileReader(file));

      String line = in.readLine();

      while (line != null) {
        if (line.contains("=")) {
          String[] l = line.split("=");
          if (clist.equals(""))
            clist = l[0];
          else {
            clist = clist + ", " + l[0];
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
    return clist;
  }
  public int companyRank(String name) {
    int rank = 0;
    File file = new File("Company/companies.txt");
    try
    {
      BufferedReader in = new BufferedReader(new FileReader(file));

      String line = in.readLine();

      while (line != null) {
        if (line.startsWith(name)) {
          String[] l = line.split("=");
          rank = Integer.valueOf(l[1]).intValue();
        }
        line = in.readLine();
      }
      in.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return rank;
  }
  public String getRank(int rank) {
    String rname = "";
    File file = new File("Company/ranks.txt");
    try
    {
      BufferedReader in = new BufferedReader(new FileReader(file));

      String line = in.readLine();

      while (line != null) {
        if (line.startsWith(Integer.toString(rank))) {
          String[] l = line.split("=");
          rname = l[1];
        }
        line = in.readLine();
      }
      in.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return rname;
  }
}