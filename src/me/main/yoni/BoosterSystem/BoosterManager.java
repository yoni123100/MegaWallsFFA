package me.main.yoni.BoosterSystem;

import java.util.ArrayList;
import java.util.UUID;

import me.main.yoni.Configuration;
import me.main.yoni.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

public class BoosterManager {

	//Personal - 1,2,3,6,24,24*3;
	//Network - 1
	
	public static BoosterManager instance = new BoosterManager();
	
	public static BoosterManager getInstance() {
		return instance;
	}
	
	public static ArrayList<Booster> boosters = new ArrayList<>();
	
	public static void setup() {
		boosters = new ArrayList<Booster>();
		boosters.clear();
		if(Configuration.scanFolder("Boosters") == null) return;
		
		for(String c : Configuration.scanFolder("Boosters")) {
			if(c == null) return;
			if(c.contains("dont_touch")) continue;
			new Configuration(c,"Boosters");
			boosters.add(getmplayerByConfig(c));
		}
	}

	public static Booster getmplayerByConfig(String c) {
		if(Configuration.getConfig(c,"Boosters") == null) return null;
		new Configuration(c,"Boosters");
		Configuration config = Configuration.getConfig(c,"Boosters");
		int time = config.getInt("time");
		ArrayList<String> list = (ArrayList<String>) config.getStringList("tipped");
		return new Booster(Bukkit.getOfflinePlayer(UUID.fromString(c)), time,list);
	}
	
	public static void create(Booster booster) {
		if(Configuration.getConfig(booster.getPlayer().getUniqueId().toString(),"Boosters") == null) {
			new Configuration(booster.getPlayer().getUniqueId().toString(),"Boosters");
		}
		new Configuration(booster.getPlayer().getUniqueId().toString(),"Boosters");
		Configuration config = Configuration.getConfig(booster.getPlayer().getUniqueId().toString(),"Boosters");
		config.set("time", booster.getTime());
		config.set("tipped", booster.getTipped());
		config.saveConfig();
		setup();
	}
	
	public static ArrayList<Booster> getBoosters() {
		return boosters;
	}
	
	public static Booster getBooster(OfflinePlayer p) {
		for(Booster m : boosters) {
			if(m.getPlayer().getUniqueId().equals(p.getUniqueId())) {
				return m;
			}
		}
		return null;
	}
	
	public static void runBoosters() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(boosters == null) return;
				for(Booster booster : boosters) {
					if(booster.getTime() <= 0) {
						if(booster.getPlayer().isOnline()) {
							booster.getPlayer().getPlayer().sendMessage(ChatColor.RED + "Your " + "Network"
									+ " booster has been expired!");
							Main.updateScoreboard(booster.getPlayer().getPlayer());
						}
						booster.Destory();
					} else {
						booster.setTime(booster.getTime() - 1);
						if(booster.getPlayer().isOnline()) {
							if(booster.getTime() % 5 == 0) {
								Main.updateScoreboard(booster.getPlayer().getPlayer());
							}
						}
					}
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0, 20);
	}
	
}
