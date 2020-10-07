package me.main.yoni.TeamSystem;

import java.util.ArrayList;

import me.main.yoni.Configuration;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TeamManager {
	
	public static TeamManager instance = new TeamManager();
	
	public static TeamManager getInstance() {
		return instance;
	}
	 
	public static ArrayList<Team> Teams;
	
	public static void setup() {
		Teams = new ArrayList<Team>();
		
		if(Configuration.scanFolder("Teams") == null) return;
		
		for(String c : Configuration.scanFolder("Teams")) {
			if(c == null) return;
			if(c.contains("dont_touch")) continue;
			new Configuration(c,"Teams");
			Teams.add(getTeamByConfig(c));
		}
	}
	
	public static ArrayList<Team> getTeams() {
		return Teams;
	}
	
	public static Team getTeamByPlayer(Player p) {
		if(Teams == null) return null;
		for(Team t : Teams) {
			if(t.getPlayers().contains(p)) {
				return t;
			}
			if(t.getLeader().equals(p)) {
				return t;
			}
		}
		return null;
	}
	
	public static Team getTeamByConfig(String igal) {
		if(Configuration.getConfig(igal,"Teams") == null) return null;
		Configuration config = Configuration.getConfig(igal,"Teams");
		String name = config.getName();
		ArrayList<Player> list = new ArrayList<>();
		ArrayList<String> list2= (ArrayList<String>) config.getStringList("players");
		for(String s : list2) {
			if(s.equalsIgnoreCase(config.getString("leader"))) continue;
			list.add(Bukkit.getOfflinePlayer(s).getPlayer());
		}
		return new Team(Bukkit.getOfflinePlayer(igal).getPlayer(),list);
	}
	
	public static void create(Team team) {
		new Configuration(team.getLeader().getName(), "Teams");
		if(Configuration.getConfig(team.getLeader().getName(), "Teams") == null) {
			new Configuration(team.getLeader().getName(), "Teams");
		}
		Configuration config = Configuration.getConfig(team.getLeader().getName(),"Teams");
		config.set("leader", team.getLeader().getName());
		ArrayList<String> list = new ArrayList<>();
		for(Player p : team.getPlayers()) {
			list.add(p.getName());
		}
		config.set("players", list);
		config.saveConfig();
		setup();
	}
	
	public static boolean isInTeam(Player p) {
		if(Teams == null) return false;
		for(Team t : Teams) {
			if(t.getLeader() == p) {
				return true;
			}
			if(t.getPlayers().contains(p)) {
				return true;
			}
		}
		return false;
	}
	
	public static void deleteTeamFiles() {
		if(Configuration.scanFolder("Teams") == null) return; 
		for(String s : Configuration.scanFolder("Teams")) {
			Configuration config = new Configuration(s, "Teams");
			config.getFile().delete();
		}
	}

}
