package me.main.yoni.ArenaSystem;

import java.util.ArrayList;

import me.main.yoni.Configuration;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ArenaManager {
	
	public static ArenaManager instance = new ArenaManager();
	
	public static ArrayList<String> move = new ArrayList<>();
	
	public static ArenaManager getInstace() {
		return instance;
	}
	
	public static ArrayList<Arena> arenas;
	
	public static void Setup() {
		arenas = new ArrayList<Arena>();
		
		if(Configuration.scanFolder("Arenas") == null) return;
		for(String c : Configuration.scanFolder("Arenas")) {
			if(c == null) return;
			if(c.contains("dont_touch")) continue;
			arenas.add(getArenaFromConfig(c));
		}
	}
	
	public static Arena getArenaFromConfig(String c) {
		if(Configuration.getConfig(c, "Arenas") == null) {
			new Configuration(c,"Arenas");
		}
		new Configuration(c,"Arenas");
		Configuration config = Configuration.getConfig(c, "Arenas");
		Location loc1 = Configuration.convertToLocation(config.getString("location_1"));
		Location loc2 = Configuration.convertToLocation(config.getString("location_2"));
		ArrayList<String> team_1 = (ArrayList<String>) config.getStringList("team_1");
		ArrayList<String> team_2 = (ArrayList<String>) config.getStringList("team_2");
		ArrayList<String> spec = (ArrayList<String>) config.getStringList("spec");
		boolean full = config.getBoolean("full");
		return new Arena(c, loc1, loc2,full,team_1,team_2,spec);
	}
	
	public static Arena getArena(String name) {
		for(Arena a : arenas) {
			if(a.getName().equalsIgnoreCase(name)) {
				return a;
			}
		}
		return null;
	}
	
	public static Arena getArenaByPlayer(Player p) {
		for(Arena a : arenas) {
			if(a.getTeam_1().contains(p.getName()) || a.getTeam_2().contains(p.getName()) || a.getSpec().contains(p.getName())) {
				return a;
			}
		}
		return null;
	}
	
	public static ArrayList<Arena> getArenas() {
		return arenas;
	}
	
	public static void resetArenas() {
		if(arenas == null) return;
		for(Arena a : arenas) {
			a.clearAllTeams(false);
		}
	}
	
	public static void create(Arena a) {
		if(Configuration.getConfig(a.getName(),"Arenas") == null) {
			new Configuration(a.getName(),"Arenas");
		}
		new Configuration(a.getName(),"Arenas");
		Configuration config = Configuration.getConfig(a.getName(),"Arenas");
		config.set("location_1", Configuration.convertLocation(a.getPos1()));
		config.set("location_2", Configuration.convertLocation(a.getPos2()));
		config.set("team_1", a.getTeam_1());
		config.set("team_2", a.getTeam_2());
		config.set("spec", a.getSpec());
		config.set("full", a.isFull());
		config.saveConfig();
		if(arenas != null) {
			boolean yes = false;
			int count = 0;
			for(Arena aa : arenas) {
				if(aa.getName().equalsIgnoreCase(a.getName())) {
					yes = true;
					break;
				}
				count++;
			}
			if(yes) {
				arenas.remove(count);
			}
		}
		arenas.add(getArenaFromConfig(config.getName()));
	}
	
}
