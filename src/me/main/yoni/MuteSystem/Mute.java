package me.main.yoni.MuteSystem;

import me.main.yoni.Configuration;

import org.bukkit.OfflinePlayer;

public class Mute {
	
	private int time;
	private String reason;
	private OfflinePlayer p;
	private String by;
	private Configuration config;
	
	public Mute(OfflinePlayer p,String by, String reason,int time) {
		new Configuration(p.getUniqueId().toString(),"Mutes");
		this.config = Configuration.getConfig(p.getUniqueId().toString(), "Mutes");
		this.p = p;
		this.time = time;
		this.reason = reason;
		this.by = by;
	}
	
	public String getBy() {
		return by;
	}
	
	public String getReason() {
		return config.getString("reason");
	}
	
	public String getName() {
		return config.getName();
	}
	
	public int getTime() {
		return config.getInt("time");
	}
	
	public void setTime(int i) {
		time = i;
		config.set("time", time);
		config.saveConfig();
	}
	
	public OfflinePlayer getPlayer() {
		return p;
	}
	
	public void Mute() {
		config.set("by", by);
		config.set("reason", reason);
		config.set("time", time);
		config.saveConfig();
		MuteManager.Setup();
	}
	
	public void unMute() {
		config.getFile().delete();
		MuteManager.Setup();
	}
	
}
