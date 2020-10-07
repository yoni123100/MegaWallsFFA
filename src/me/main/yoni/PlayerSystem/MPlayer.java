package me.main.yoni.PlayerSystem;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import me.main.yoni.Configuration;
import me.main.yoni.Main;
import me.main.yoni.ClassSystem.Class;
import me.main.yoni.ClassSystem.ClassManager;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class MPlayer {
	
	private Configuration config;
	private String name;
	private String uuid;
	
	public MPlayer(String name) {
		this.name = name;
		this.uuid = Bukkit.getOfflinePlayer(name).getUniqueId().toString();
		this.config = Configuration.getConfig("PlayerData");
	}
	
	public MPlayer(UUID uuid) {
		this.uuid = uuid.toString();
		this.name = Bukkit.getOfflinePlayer(uuid).getName();
		this.config = Configuration.getConfig("PlayerData");
	}
	
	public String getIp() {
		return config.getString("stats." + uuid + ".ip");
	}
	
	public String getName() {
		if(config.getString("stats." + uuid + ".name") == null) {
			config.set("stats." + uuid + ".name", Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getName());
			config.saveConfig();
		}
		return config.getString("stats." + uuid + ".name");
	}
	
	public boolean getMSGToggle() {
		if(!config.contains("stats." + uuid + ".msg") || config.get("stats." + uuid + ".msg") == null) {
			setMSGToggle(true);
			return true;
		}
		return config.getBoolean("stats." + uuid + ".msg");
	}
	
	public void setMSGToggle(boolean b) {
		config.set("stats." + uuid + ".msg", b);
		config.saveConfig();
	}
	
	public String getUUID() {
		return config.getString("stats." + uuid + ".uuid");
	}
	
	public int getTeamUpgrade() {
		return new Upgrade(getPlayer().getPlayer(), UpgradeType.TEAM).getCurrentUpgrade();
	}
	
	public int getCoinsForNextTeamUpgrade() {
		if(getTeamUpgrade() == 1) {
			return 20000;
		}
		if(getTeamUpgrade() == 2) {
			return 30000;
		}
		return 30000;
	}
	
	public int getCoinsForNextBlockUpgrade() {
		if(getBlocksUpgrade() == 1) {
			return 1500;
		}
		if(getBlocksUpgrade() == 2) {
			return 3000;
		}
		return 0;
	}

	public void upgradeTeam() {
		Upgrade u = new Upgrade(getPlayer().getPlayer(), UpgradeType.TEAM);
		u.upgrade();
	}
	
	public int getBlocksUpgrade() {
		return new Upgrade(getPlayer().getPlayer(), UpgradeType.BLOCKS).getCurrentUpgrade();
	}
	
	public void upgradeBlocksUpgrade() {
		Upgrade u = new Upgrade(getPlayer().getPlayer(), UpgradeType.BLOCKS);
		u.upgrade();
	}
	
	public OfflinePlayer getPlayer() {
		return Bukkit.getOfflinePlayer(UUID.fromString(uuid));
	}
	
	public ArrayList<String> getClasses() {
		return (ArrayList<String>) config.getStringList("stats." + uuid + ".classes");
	}
	
	public void addNetworkBoosters(int i) {
		setNetworkBoosters(getNetworkBoosters() + i);
	}
	
	public void removeNetworkBoosters(int i) {
		setNetworkBoosters(getNetworkBoosters() - i);
		if(getNetworkBoosters() < 0) {
			setNetworkBoosters(0);
		}
	}
	
	public void setNetworkBoosters(int i) {
		config.set("stats." + uuid + ".network_boosters", i);
		config.saveConfig();
	}
	
	public int getNetworkBoosters() {
		return config.getInt("stats." + uuid + ".network_boosters");
	}
	
	public int getWins() {
		return config.getInt("stats." + uuid + ".stats."+"wins");
	}
	
	public int getKills() {
		return config.getInt("stats." + uuid + ".stats."+"kills");
	}
	
	public boolean boughtTeam()  {
		return config.getBoolean("stats." + uuid + ".bought_team");
	}
	
	public void buyTeam() {
		if(boughtTeam()) return;
		config.set("stats." + uuid + ".bought_team", true);
		config.saveConfig();
	}
	
	public int getDeaths() {
		return config.getInt("stats." + uuid + ".stats."+"deaths");
	}
	
	public int getCoins() {
		return config.getInt("stats." + uuid + ".stats."+"coins");
	}
	
	public boolean hasClass(Class c) {
		if(getClasses().contains(c.getName())) {
			return true;
		}
		return false;
	}
	
	public void addClass(Class c) {
		ArrayList<String> list = getClasses();
		if(list.contains(c.getName())) return;
		list.add(c.getName());
		config.set("stats." + uuid + ".classes", list);
		config.saveConfig();
	}
	
	public void removeClass(Class c) {
		ArrayList<String> list = getClasses();
		if(!list.contains(c.getName())) return;
		list.remove(c.getName());
		config.set("stats." + uuid + ".classes", list);
		config.saveConfig();
	}

	public void removeCoins(int i) {
		setCoins(getCoins() - i);
	}
	
	public void setCoins(int i) {
		config.set("stats." + uuid + ".stats."+"coins", i);
		config.saveConfig();
	}
	
	public void setWins(int i) {
		config.set("stats." + uuid + ".stats."+"wins", i);
		config.saveConfig();
	}
	
	public void setKills(int i) {
		config.set("stats." + uuid + ".stats."+"kills", i);
		config.saveConfig();
	}
	
	public void addKills(int i) {
		setKills(getKills() + i);
	}
	
	public void setDeaths(int i) {
		config.set("stats." + uuid + ".stats."+"deaths", i);
		config.saveConfig();
	}
	
	public void addWins(int i) {
		setWins(getWins() + i);
	}
	 
	public void addDeaths(int i) {
		setDeaths(getDeaths() + i);
	}
	
	public void addCoins(int i) {
		setCoins(getCoins() + i);
	}
	
	public void setCurrentDuelClass(me.main.yoni.ClassSystem.Class mwClass){
		if(mwClass == null) {
			config.set("stats." + uuid + ".current_class_duel", "None");
			config.saveConfig();
			return;
		}
		config.set("stats." + uuid + ".current_class_duel", mwClass.getName());
		config.saveConfig();
	}
	
	public void setCurrentClass(me.main.yoni.ClassSystem.Class mwClass){
		if(mwClass == null) {
			config.set("stats." + uuid + ".current_class", "None");
			config.saveConfig();
			return;
		}
		config.set("stats." + uuid + ".current_class", mwClass.getName());
		config.saveConfig();
	}
	
	public me.main.yoni.ClassSystem.Class getCurrentClass() {
		if(getCurrentDuelClass() != null) {
			return getCurrentDuelClass();
		}
		if(ClassManager.getClass(config.getString("stats." + uuid + ".current_class")) == null) {
			return null;
		}
		return ClassManager.getClass(config.getString("stats." + uuid + ".current_class"));
	}
	
	public me.main.yoni.ClassSystem.Class getCurrentDuelClass() {
		if(ClassManager.getClass(config.getString("stats." + uuid + ".current_class_duel")) == null) {
			return null;
		}
		return ClassManager.getClass(config.getString("stats." + uuid + ".current_class_duel"));
	}
	
	public void applyDuelClass() {
		if(!getPlayer().isOnline()) return;
		if(getCurrentDuelClass() == null) {
			int random = new Random().nextInt(getClasses().size());
			setCurrentDuelClass(ClassManager.getClasses().get(random));
		}
		getCurrentDuelClass().apply(getPlayer().getPlayer());
		Main.updateScoreboard(getPlayer().getPlayer());
	}
	
	public void applyClass() {
		if(!getPlayer().isOnline()) return;
		if(getCurrentClass() == null) {
			int random = new Random().nextInt(getClasses().size());
			setCurrentClass(ClassManager.getClasses().get(random));
		}
		getCurrentClass().apply(getPlayer().getPlayer());
		Main.updateScoreboard(getPlayer().getPlayer());
	}

}
