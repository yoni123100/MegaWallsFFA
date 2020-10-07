package me.main.yoni.ClassSystem.Upgrades;

import me.main.yoni.Configuration;
import me.main.yoni.API.Timeformatter;

import org.bukkit.OfflinePlayer;


public class Upgrade
{
	public me.main.yoni.ClassSystem.Class c;
	private Configuration config;
	private int currentUpgrade;
	private OfflinePlayer p;
	private UpgradeType type;
	private boolean enderchest;
	private boolean prestige;

	public Upgrade(OfflinePlayer p, UpgradeType upgradeType) {
		this.p = p;
		this.type = upgradeType;
		this.config = Configuration.getConfig("PlayerData");
		if (config.get("stats." + p.getUniqueId().toString() + ".upgrades."+upgradeType.name()) == null){
			this.currentUpgrade = 1;
			return;
		}
		this.currentUpgrade = this.config.getInt("stats." + p.getUniqueId().toString() + ".upgrades."+upgradeType.name());
	}
	
	public Upgrade(OfflinePlayer p, me.main.yoni.ClassSystem.Class c,UpgradeType upgradeType)
	{
		this.p = p;
		this.c = c;
		this.type = upgradeType;
		this.config = Configuration.getConfig("PlayerData");
		if (config.get("stats." + p.getUniqueId().toString() + ".upgrades."+upgradeType.name()+"." + c.getName()) == null){
			this.currentUpgrade = 1;
			this.enderchest = false;
			this.prestige = false;
			return;
		}
		this.enderchest = config.getBoolean("stats." + p.getUniqueId().toString() + ".upgrades.enderchest." + c.getName());
		this.prestige = config.getBoolean("stats." + p.getUniqueId().toString() + ".upgrades.prestige." + c.getName());
		this.currentUpgrade = this.config.getInt("stats." + p.getUniqueId().toString() + ".upgrades."+upgradeType.name()+"." + c.getName());
	}

	public int getCurrentUpgrade(){return this.currentUpgrade;}

	public void upgrade(){
		if(type == UpgradeType.BLOCKS || type == UpgradeType.TEAM) {
			if (this.currentUpgrade == 3) return;
			this.currentUpgrade += 1;
			this.config.set("stats." + p.getUniqueId().toString() + ".upgrades."+type.name(), currentUpgrade);
			this.config.saveConfig();
			return;
		}
		if (this.currentUpgrade == 9) return;
		this.currentUpgrade += 1;
		this.config.set("stats." + p.getUniqueId().toString() + ".upgrades."+type.name()+"." + c.getName(), currentUpgrade);
		this.config.saveConfig();
	}

	public void addEnderchest(){
		this.enderchest = true;
		this.config.set("stats." + p.getUniqueId().toString() + ".upgrades.enderchest." + c.getName(), true);
		this.config.saveConfig();
	}
	
	public void addPrestige(){
		this.enderchest = true;
		this.config.set("stats." + p.getUniqueId().toString() + ".upgrades.prestige." + c.getName(), true);
		this.config.saveConfig();
	}

	public String getUpgradeName(){return getUpgradeName(this.currentUpgrade);}
	public String getUpgradeName(int currentUpgrade){return Timeformatter.formatForUpgradeLvls(currentUpgrade);}
	public Configuration getConfig(){return this.config;}
	public boolean hasPrestige(){return prestige;}

	public void reset(){
		if(type == UpgradeType.BLOCKS || type == UpgradeType.TEAM) {
			this.config.set("stats." + p.getUniqueId().toString() + ".upgrades."+type.name(), 1);
			this.config.saveConfig();
			this.currentUpgrade = config.getInt("stats." + p.getUniqueId().toString() + ".upgrades."+type.name());
			return;
		}
		this.config.set("stats." + p.getUniqueId().toString() + ".upgrades."+type.name()+"." + c.getName(), 9);
		this.config.set("stats." + p.getUniqueId().toString() + ".upgrades.prestige." + c.getName(), false);
		this.config.saveConfig();
		this.currentUpgrade = config.getInt("stats." + p.getUniqueId().toString() + ".upgrades." + c.getName());
		this.prestige = config.getBoolean("stats." + p.getUniqueId().toString() + ".upgrades.prestige." + c.getName());
	}

	public int getNextUpgrade(){
		if(type == UpgradeType.BLOCKS || type == UpgradeType.TEAM) {
			if (this.currentUpgrade == 3) return 3;
		}
		if (this.currentUpgrade == 9) return 9;
		return this.currentUpgrade + 1;
	}

	public boolean isUnlocked(int upgrade){
		if (currentUpgrade >= upgrade)
			return true;
		return false;
	}

}
