package me.main.yoni.BoosterSystem;

import java.util.ArrayList;

import me.main.yoni.Configuration;

import org.bukkit.OfflinePlayer;

public class Booster {

	private Configuration config;
	private OfflinePlayer player;
	private int time;
	private ArrayList<String> tipped;
	
	public Booster(OfflinePlayer player, int time,ArrayList<String> tipped) {
		this.config = Configuration.getConfig(player.getUniqueId().toString(), "Boosters");
		this.player = player;
		this.time = time;
		this.tipped = tipped;
		if(this.time > 60*60) {
			this.time = 60*60;
		}
	}
	
	public OfflinePlayer getPlayer() {
		return player;
	}
	
	public int getTime() {
		return time;
	}
	
	public ArrayList<String> getTipped() {
		return tipped;
	}
	
	public void addTip(String name) {
		this.tipped.add(name);
		BoosterManager.create(this);
	}
	
	public void setTime(int i) {
		this.time = i;
		config.set("time", time);
		config.saveConfig();
	}
	
	public void Destory() {
		config.getFile().delete();
		BoosterManager.setup();
		//Setup
	}
}
