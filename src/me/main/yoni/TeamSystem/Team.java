package me.main.yoni.TeamSystem;

import java.util.ArrayList;

import me.main.yoni.Configuration;
import me.main.yoni.Main;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Team {
	
	private Player leader;
	private ArrayList<Player> players;
	private Configuration config;

	public Team(Player leader, ArrayList<Player> list) {
		this.leader = leader;
		this.players = list;
		this.players.add(leader);
		this.config = Configuration.getConfig(leader.getName(), "Teams");
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public Configuration getConfig() {
		return config;
	}
	
	public Player getLeader() {
		return leader;
	}
	
	public void addPlayer(Player p) {
		for(Player ps : getPlayers()) {
			if(ps == getLeader()) continue;
			ps.sendMessage("======----------======"); 
			ps.sendMessage(ChatColor.GREEN + p.getName() + " has joined the Team!");
			ps.sendMessage("======----------======");
		}
		this.players.add(p);
		TeamManager.create(this);
	}
	
	public void kickPlayer(Player p) {
		this.players.remove(p);
		TeamManager.create(this);
		Main.updateScoreboard(getLeader());
		for(Player ps : getPlayers()) {
			if(ps == getLeader()) continue;
			ps.sendMessage("======----------======"); 
			ps.sendMessage(ChatColor.RED + p.getName() + " has been kicked from the Team!");
			ps.sendMessage("======----------======");
			Main.updateScoreboard(ps);
		}
	}
	
	public void removePlayer(Player p) {
		this.players.remove(p);
		TeamManager.create(this);
		getLeader().sendMessage("======----------======");
		getLeader().sendMessage(ChatColor.RED + p.getName() + " left your Team!");
		getLeader().sendMessage("======----------======");
		Main.updateScoreboard(getLeader());
		for(Player ps : getPlayers()) {
			if(ps == getLeader()) continue;
			ps.sendMessage("======----------======");
			ps.sendMessage(ChatColor.RED + p.getName() + " left the Team!");
			ps.sendMessage("======----------======");
			Main.updateScoreboard(ps);
		}
	}
	
	public void destroy() {
		for(Player ps : getPlayers()) {
			Main.updateScoreboard(ps);
			if(ps == getLeader()) continue;
			ps.sendMessage("======----------======");
			ps.sendMessage(ChatColor.RED + getLeader().getName() + " disbanded the Team!");
			ps.sendMessage("======----------======");
		}
		config.getFile().delete();
		TeamManager.setup();
		for(Player online : Bukkit.getOnlinePlayers()) {
			Main.updateScoreboard(online);
		}
	}
}
