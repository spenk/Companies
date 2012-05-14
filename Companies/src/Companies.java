import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Companies extends Plugin
{
  private Logger log;
  private Companies.Portall listener = new Companies.Portall();

  public static String name = "Companies";
  public static String version = "1.0";
  String comname;
  ArrayList<String> sr = new ArrayList<String>();

  public void disable() {
    etc.getInstance().addCommand("/co ?", "- shows help for the company plugin");
  }

  public void enable() {
    etc.getInstance().removeCommand("/co ?");
  }
  public void initialize() {
    this.log = Logger.getLogger("Minecraft");
    this.log.info(name + " version " + version + " initialized.");
    etc.getLoader().addListener(PluginLoader.Hook.COMMAND, this.listener, this, PluginListener.Priority.MEDIUM);
    Filer f = new Filer();
    new File("Company/").mkdir();
    File players = new File("Company/players.txt");
    File companies = new File("Company/companies.txt");
    File settings = new File("Company/settings.txt");
    File ranks = new File("Company/ranks.txt");
    if (!players.exists()) {
      f.writeLine("Do not write in this file!", "Company/players");
    }
    if (!companies.exists()) {
      f.writeLine("Do not write in this file!", "Company/companies");
    }
    if (!settings.exists()) {
      f.writeLine("Settings:", "Company/settings");
      f.writeLine("startCost=100", "Company/settings");
      f.writeLine("TopRank=10", "Company/settings");
    }if (!ranks.exists())
      f.writeLine("Write ranks like: ranknumber=rankname", "Company/ranks"); 
  }
  private class Portall extends PluginListener {
    private Portall() {
    }

    public boolean onCommand(Player player, String[] split) {
      if (split[0].equalsIgnoreCase("/co")) {
        String compName = "";
        for (int i = 1; i < split.length; i++) {
          if (compName.equals(""))
            compName = split[i];
          else {
            compName = compName + " " + split[i];
          }
        }
        String coName = "";

        if ((split.length > 2) && (!split[1].equalsIgnoreCase("add")) && (!split[1].equalsIgnoreCase("kick")))
        {
          for (int i = 2; i < split.length; i++) {
            if (coName.equals(""))
              coName = split[i];
            else {
              coName = coName + " " + split[i];
            }
          }

        }

        CWrite cw = new CWrite();
        try {
          if (split[1] != null)
          {
            Filer f = new Filer();
            if (!cw.getcompanyName(compName).equals("")) {
              String check = cw.getcompanyName(compName);
              String[] p = check.split("=");
              int crank = cw.companyRank(p[0]);
              String rank = cw.getRank(crank);
              player.sendMessage("§6----[§2" + p[0] + "§6" + "]----");
              player.sendMessage("§6Size: §2" + cw.pamount(player, p[0]));
              player.sendMessage("§6PlayerList: §2" + cw.plist(player, p[0]));
              player.sendMessage("§6Rank:§2 " + rank);
              return true;
            }
            
            if ((split[1].equalsIgnoreCase("help")) || (split[1].equalsIgnoreCase("?"))) {
              player.sendMessage("§6Company commands:");
              player.sendMessage("§6/co");
              player.sendMessage("§6/co name");
              if (player.canUseCommand("/company")) {
                player.sendMessage("§6/co start name");
                player.sendMessage("§6/co delete name");
                player.sendMessage("§6/co add playername");
                player.sendMessage("§6/co kick playername");
              }
              if (player.isAdmin()) {
                player.sendMessage("§6/co rankup name");
                player.sendMessage("§6/co rankdown name");
              }
              player.sendMessage("§6/co rank name");
              player.sendMessage("§6/co showrank");
              player.sendMessage("§6/co leave");
              player.sendMessage("§6/co clist");
              player.sendMessage("§6/co plist name");
              return true;
            }
            
            if ((split[1].equalsIgnoreCase("start")) && (player.canUseCommand("/company"))) {
              try {
                if (split[2] != null) {
                  String check = cw.companyName(player.getName());
                  String ccheck = cw.getcompanyName(coName);
                  if (check.equals("")) {
                    if (ccheck.equals(""))
                    {
                      double scost = cw.getStartCost();
                      double balance = (Double)etc.getLoader().callCustomHook("dCBalance", new Object[] { "Player-Balance", player.getName()});
                      if (balance >= scost) {
                    	  etc.getLoader().callCustomHook("dCBalance", new Object[] { "Player-Charge", player.getName(), (Double) (double)scost});
                    	  double nmoney = balance-scost;
                        player.sendMessage("§3Your new balance is :"+nmoney);
                        f.writeLine(coName + "=1", "Company/companies");
                        f.writeLine(player.getName() + "=" + coName, "Company/players");
                        player.sendMessage("§6Company " + coName + " created!");
                      } else {
                        player.sendMessage("§6You do not have enough to buy a company!");
                      }
                    } else {
                      player.sendMessage("§6Company " + coName + " already exists!");
                    }
                  }
                  else player.sendMessage("§6You are already in a company!");
                }

                return true;
              } catch (Exception e) {
                player.sendMessage("§6Incorrect syntax: /co start name");
                return true;
              }
            }
            
            if (split[1].equalsIgnoreCase("showrank")) {
              String check = cw.companyName(player.getName());

              if (check.equals("")) {
                player.sendMessage("§6You are not in a company!");
              } else {
                String[] m = check.split("=");
                int crank = cw.companyRank(m[1]);
                String rank = cw.getRank(crank);
                if (Companies.this.sr.contains(player.getName())) {
                  Companies.this.sr.remove(player.getName());
                  player.setPrefix("");
                  player.sendMessage("§6Stopped showing your company rank : {" + rank + "}");
                } else {
                  Companies.this.sr.add(player.getName());
                  player.setPrefix(" {" + rank + "} " + player.getColor());
                  player.sendMessage("§6Showing your company rank : {" + rank + "}");
                }
              }
              return true;
            }
            if (split[1].equalsIgnoreCase("rank")) {
              try {
                if (split[2] != null) {
                  String check = cw.getcompanyName(coName);
                  if (check.equals("")) {
                    player.sendMessage("§6That company doesnt exist!");
                  } else {
                    int crank = cw.companyRank(coName);
                    String rank = cw.getRank(crank);

                    player.sendMessage("§6" + coName + "'s rank is {" + rank + "}");
                  }
                }
                return true;
              } catch (Exception e) {
                String check = cw.companyName(player.getName());
                if (check.equalsIgnoreCase("")) {
                  player.sendMessage("§6Incorrect Syntax: /co rank name");
                } else {
                  String[] p = check.split("=");
                  int crank = cw.companyRank(p[1]);
                  String rank = cw.getRank(crank);
                  player.sendMessage("§6" + p[1] + "'s rank is {" + rank + "}");
                }
                return true;
              }
            }
            if ((split[1].equalsIgnoreCase("delete")) && (player.canUseCommand("/company"))) {
              try {
                if (split[2] != null) {
                  String ccheck = cw.getcompanyName(coName);
                  if (ccheck.equals("")) {
                    player.sendMessage("§6Company " + coName + " doesnt exist.");
                  }
                  else if (player.isAdmin()) {
                    f.deleteLine(coName, "Company/companies");
                    f.deleteLine(coName, "Company/players");
                    player.sendMessage("§6You deleted company " + coName);
                  } else {
                    String check = cw.companyName(player.getName());
                    if (check.equals("")) {
                      player.sendMessage("§6You arent in a company!");
                    } else {
                      String[] p = check.split("=");
                      if (p[1].equalsIgnoreCase(coName)) {
                        f.deleteLine(coName, "Company/companies");
                        f.deleteLine(coName, "Company/players");
                        player.sendMessage("§6You deleted company " + coName);
                      } else {
                        player.sendMessage("§6You are not in company " + coName);
                      }
                    }
                  }
                }

                return true;
              } catch (Exception e) {
                player.sendMessage("§6Incorrect syntax: /co delete name");
                return true;
              }
            }
            if ((split[1].equalsIgnoreCase("add")) && (player.canUseCommand("/company"))) {
              try {
                String playerName = split[2];
                String check = cw.companyName(player.getName());

                String ocheck = cw.companyName(playerName);
                if (check.equals("")) {
                  player.sendMessage("§6You are not in a company!");
                }
                else if (!ocheck.equals("")) {
                  player.sendMessage("§6" + playerName + " is already in a company!");
                } else {
                  String[] m = check.split("=");
                  f.writeLine(playerName + "=" + m[1], "Company/players");
                  player.sendMessage("§6" + playerName + " was added to Company: " + m[1]);
                  Player p = etc.getServer().matchPlayer(playerName);
                  if (p.isConnected()) {
                    p.sendMessage("§6You where added to Company: " + m[1]);
                  }
                }

                return true;
              } catch (Exception e) {
                player.sendMessage("§6Incorrect syntax: /co add playername");
                return true;
              }
            }
            if (split[1].equalsIgnoreCase("leave")) {
              try {
                String check = cw.companyName(player.getName());
                if (check.equals("")) {
                  player.sendMessage("§6You are not in a company!");
                } else {
                  f.deleteLine(player.getName(), "Company/players");
                  player.sendMessage("§6You left your company!");
                }
                return true;
              } catch (Exception e) {
                player.sendMessage("§6Incorrect syntax: /co leave");
                return true;
              }
            }
            if (split[1].equalsIgnoreCase("clist")) {
              player.sendMessage("§6Company list: §f" + cw.clist(player));
              return true;
            }
            if (split[1].equalsIgnoreCase("plist")) {
              try {
                String check = cw.getcompanyName(split[2]);
                if (check.equals(""))
                  player.sendMessage("§6Company " + coName + " doesnt exist!");
                else {
                  player.sendMessage("§6Player list for " + coName + ": " + "§f" + cw.plist(player, split[2]));
                }
                return true;
              } catch (Exception e) {
                player.sendMessage("§6Incorrect syntax: /co plist name");
                return true;
              }
            }
            if ((split[1].equalsIgnoreCase("rankup")) && (player.isAdmin())) {
              try {
                String check = cw.getcompanyName(split[2]);
                if (check.equals("")) {
                  player.sendMessage("§6Company " + coName + " doesnt exist!");
                } else {
                  String[] c = check.split("=");
                  int rank = Integer.valueOf(c[1]).intValue() + 1;

                  Integer toprank = Integer.valueOf(cw.getTopRank());
                  if (rank <= toprank.intValue()) {
                    f.deleteLine(coName, "Company/companies");
                    f.writeLine(coName + "=" + rank, "Company/companies");
                    String srank = cw.getRank(rank);
                    player.sendMessage("§6Company " + coName + "'s new rank is " + srank);
                  } else {
                    player.sendMessage("§6That company has hit the top rank!");
                  }
                }
                return true;
              } catch (Exception e) {
                player.sendMessage("§6Incorrect syntax: /co rankup name");
                return true;
              }
            }
            if ((split[1].equalsIgnoreCase("rankdown")) && (player.isAdmin())) {
              try {
                String check = cw.getcompanyName(coName);
                if (check.equals("")) {
                  player.sendMessage("§6Company " + coName + " doesnt exist!");
                } else {
                  String[] c = check.split("=");
                  int rank = Integer.valueOf(c[1]).intValue() - 1;

                  if (rank > 0) {
                    f.deleteLine(coName, "Company/companies");
                    f.writeLine(coName + "=" + rank, "Company/companies");
                    String srank = cw.getRank(rank);
                    player.sendMessage("§6Company " + coName + "'s new rank is " + srank);
                  } else {
                    player.sendMessage("§6That company has hit the bottom rank!");
                  }
                }
              } catch (Exception e) {
                player.sendMessage("§6Incorrect syntax: /co rankdown name");
                return true;
              }
              return true;
            }

            if ((split[1].equalsIgnoreCase("kick")) && (player.canUseCommand("/company")))
              try
              {
                String playerName = split[2];
                String check = cw.companyName(player.getName());

                String ocheck = cw.companyName(playerName);
                if (check.equals("")) {
                  player.sendMessage("§6You are not in a company!");
                }
                else if (ocheck.equals("")) {
                  player.sendMessage("§6" + playerName + " isnt in a company!");
                } else {
                  String[] m = check.split("=");
                  String[] om = ocheck.split("=");
                  if (om[1].equalsIgnoreCase(m[1])) {
                    f.deleteLine(coName, "Company/players");
                    player.sendMessage("§6" + playerName + " was kicked from company " + m[1]);
                  } else {
                    player.sendMessage("§6" + playerName + " isnt in your company!");
                  }
                }

                return true;
              } catch (Exception e) {
                player.sendMessage("§6Incorrect syntax: /co kick playername");
                return true;
              }
          }
        }
        catch (Exception e) {
          String check = cw.companyName(player.getName());
          if (!check.equals(""))
          {
            String[] p = check.split("=");
            int crank = cw.companyRank(p[1]);
            String rank = cw.getRank(crank);
            player.sendMessage("§6----[§2" + p[1] + "§6" + "]----");
            player.sendMessage("§6Size: §2" + cw.pamount(player, p[1]));
            player.sendMessage("§6PlayerList: §2" + cw.plist(player, p[1]));
            player.sendMessage("§6Rank:§2 " + rank);
            return true;
          }
        }
      }
      return false;
    }
  }
}
