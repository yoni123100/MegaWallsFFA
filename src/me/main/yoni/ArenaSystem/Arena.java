package me.main.yoni.ArenaSystem;


import java.util.ArrayList;

import me.main.yoni.Configuration;
import me.main.yoni.Main;
import me.main.yoni.API.Utils;
import me.main.yoni.Listeners.OneOnOneListener;
import me.main.yoni.PlayerSystem.MPlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Arena {

	private Configuration config;
	private String name;
	private Location pos1;
	private Location pos2;
	private boolean full;
	private ArrayList<String> team_1;
	private ArrayList<String> team_2;
	private ArrayList<String> spec;
	
	public Arena(String name,Location loc1,Location loc2, boolean full, ArrayList<String> team1,ArrayList<String> team2
			,ArrayList<String> team3) {
		this.config = Configuration.getConfig(name,"Arenas");
		this.name = name;
		this.pos1 = loc1;
		this.pos2 = loc2;
		this.full = full;
		this.team_1 = team1;
		this.team_2 = team2;
		this.spec = team3;
	}
	
	@SuppressWarnings("deprecation")
	public void BroadCast(String msg) {
		for(String p : team_1) {
			if(Bukkit.getOfflinePlayer(p).isOnline()) {
				Bukkit.getOfflinePlayer(p).getPlayer().sendMessage(msg);
			}
		}
		for(String p : team_2) {
			if(Bukkit.getOfflinePlayer(p).isOnline()) {
				Bukkit.getOfflinePlayer(p).getPlayer().sendMessage(msg);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void clearAllTeams(boolean bool) {
		if(bool) {
			for(String p : team_1) {
				if(Bukkit.getOfflinePlayer(p).isOnline()) {
					if(bool) {
						Bukkit.getPlayer(p).getInventory().clear();
						Bukkit.getPlayer(p).updateInventory();
						Utils.give1v1Items(Bukkit.getOfflinePlayer(p).getPlayer());
					}
				}
			}
			for(String p : spec) {
				if(Bukkit.getOfflinePlayer(p).isOnline()) {
					if(bool) {
						Bukkit.getPlayer(p).getInventory().clear();
						Bukkit.getPlayer(p).updateInventory();
						Utils.give1v1Items(Bukkit.getOfflinePlayer(p).getPlayer());
					}
				}
			}
			for(String p : team_2) {
				if(Bukkit.getOfflinePlayer(p).isOnline()) {
					if(bool) {
						Bukkit.getPlayer(p).getInventory().clear();
						Bukkit.getPlayer(p).updateInventory();
						Utils.give1v1Items(Bukkit.getOfflinePlayer(p).getPlayer());
					}
				}
			}
		}
		team_1.clear();
		team_2.clear();
		spec.clear();
		setFull(false);
		ArenaManager.create(this);
	}
	
	public ArrayList<String> getSpec() {
		return this.spec;
	}
	
	public void addPlayerSpec(String p) {
		if(this.spec.contains(p)) return;
		this.spec.add(p);
		ArenaManager.create(this);
	}
	
	public ArrayList<String> getTeam_1() {
		return this.team_1;
	}
	
	public ArrayList<String> getTeam_2() {
		return this.team_2;
	}
	
	public void addPlayer(Player p, int team) {
		if(team != 1 && team != 2) return;
		if(team == 1) {
			if(this.team_1.contains(p.getName())) return;
			this.team_1.add(p.getName());
			ArenaManager.create(this);
		}
		if(team == 2) {
			if(this.team_1.contains(p.getName())) return;
			this.team_2.add(p.getName());
			ArenaManager.create(this);
		}
	}
	
	public void removePlayer(String p) {
		addPlayerSpec(p);
		ArenaManager.create(this);
	}
	
	public void setPos1(Location loc) {
		this.pos1 = loc;
		ArenaManager.create(this);
	}
	
	public void setPos2(Location loc) {
		this.pos2 = loc;
		ArenaManager.create(this);
	}
	
	public String getName() {
		return name;
	}
	
	public Location getPos1() {
		return pos1;
	}
	
	public Location getPos2() {
		return pos2;
	}
	
	public void Destroy() {
		config.getFile().delete();
		ArenaManager.Setup();
	}
	
	public void setFull(boolean lol) {
		this.full = lol;
		ArenaManager.create(this);
	}

	public boolean isFull() {
		return this.full;
	}
	
	@SuppressWarnings("deprecation")
	public void startFight() {
		for(String s : getTeam_1()) {
			if(OneOnOneListener.queue.contains(s)) {
				OneOnOneListener.queue.remove(s);
			}
			ArenaManager.move.add(s);
			Bukkit.getOfflinePlayer(s).getPlayer().teleport(getPos1());
			Bukkit.getOfflinePlayer(s).getPlayer().getInventory().clear();
		}
		for(String s : getTeam_2()) {
			if(OneOnOneListener.queue.contains(s)) {
				OneOnOneListener.queue.remove(s);
			}
			ArenaManager.move.add(s);
			Bukkit.getOfflinePlayer(s).getPlayer().teleport(getPos2());
			Bukkit.getOfflinePlayer(s).getPlayer().getInventory().clear();
		}
		new BukkitRunnable() {
			
			int i = 5;
			@Override
			public void run() {
				if(i <= 0) {
					for(String s : getTeam_1()) {
						if(ArenaManager.move.contains(s)) {
							ArenaManager.move.remove(s);
						}
						MPlayerManager.getMPlayer(s).applyDuelClass();
						Bukkit.getOfflinePlayer(s).getPlayer().sendMessage("Good luck!");
					}
					for(String s : getTeam_2()) {
						if(ArenaManager.move.contains(s)) {
							ArenaManager.move.remove(s);
						}
						MPlayerManager.getMPlayer(s).applyDuelClass();
						Bukkit.getOfflinePlayer(s).getPlayer().sendMessage("Good luck!");
					}
					cancel();
					return;
				}
				BroadCast("The Duel is starting in " + ChatColor.AQUA + i + ChatColor.WHITE + " seconds!");
				i--;
			}
		}.runTaskTimer(Main.getPlugin(), 20, 20);
	}
	
}
