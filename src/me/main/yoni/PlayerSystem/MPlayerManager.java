package me.main.yoni.PlayerSystem;

import java.util.ArrayList;
import java.util.UUID;

import me.main.yoni.Configuration;
import me.main.yoni.API.Utils;
import me.main.yoni.ClassSystem.ClassManager;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class MPlayerManager {
	 
	public static ArrayList<MPlayer> mplayers;
	
	public static void setup() {
		mplayers = new ArrayList<MPlayer>();
		mplayers.clear();
		if(Configuration.getConfig("PlayerData").getConfigurationSection("stats") == null) return;
		for(String c : Configuration.getConfig("PlayerData").getConfigurationSection("stats").getKeys(false)) {
			if(c == null) return;
			if(c.contains("dont_touch")) continue;
			mplayers.add(getmplayerByConfig(c));
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "mplayer has been loaded " + c);
		}
	}
	
	public static MPlayer getmplayerByConfig(String igal) {
		return new MPlayer(UUID.fromString(igal));
	}
	
	public static void setItem(Player p, me.main.yoni.ClassSystem.Class c,int item,int slot) {
		Configuration config2 = Configuration.getConfig(p.getUniqueId().toString(),"PlayerLayouts");
		config2.set(c.getName()+"."+""+item, slot);
		config2.saveConfig();
	}
	
	public static void resetLayout(Player p, me.main.yoni.ClassSystem.Class c) {
		Configuration config2 = Configuration.getConfig(p.getUniqueId().toString(),"PlayerLayouts");
		Upgrade upgrade = new Upgrade(p, c, UpgradeType.KIT);
		for(String s : config2.getConfigurationSection(c.getName()+".").getKeys(false)) {
			config2.set(c.getName()+"."+s, null);
			config2.saveConfig();
		}
		int count = 36;
		for(int j = 0; j<Utils.getNewStartingItems(p, c).size();j++) {
			if(count == 45) {
				count = 9;
			}
			if(Utils.isArmor(Utils.getNewStartingItems(p, c).get(j))) continue;
			config2.set(c.getName()+"."+""+j, count);
			config2.saveConfig();
			count++;
		}
	}
	
	public static void refresh(Player p) {
		Configuration config2 = Configuration.getConfig(p.getUniqueId().toString(),"PlayerLayouts");
		for(me.main.yoni.ClassSystem.Class c : ClassManager.getClasses()) {
			int i = 36;
			for(int j = 0; j<Utils.getNewStartingItems(p, c).size();j++) {
				if(config2.getString(c.getName()+"."+""+j) != null)continue;
				if(Utils.isArmor(Utils.getNewStartingItems(p, c).get(j))) continue;
				if(i > 44) {
					i=9;
				}
				config2.set(c.getName()+"."+""+j, i);
				config2.saveConfig();
				i++;
			}
		}
	}
	
	public static void create(Player p) {
		new Configuration(p.getUniqueId().toString(), "PlayerLayouts");
		String uuid = p.getUniqueId().toString();
		Configuration.getConfig("PlayerData").set("stats." + uuid + ".name", p.getName());
		Configuration.getConfig("PlayerData").saveConfig();;
		if(getMPlayer(p.getUniqueId()) != null) {
			refresh(p);
			return;
		}
		Configuration config = Configuration.getConfig("PlayerData");
		ArrayList<String> classes = new ArrayList<>();
		classes.add(ClassManager.getClass("Zombie").getName());
		config.set("stats." + uuid + ".stats."+"kills", 0);
		config.set("stats." + uuid + ".stats."+"deaths", 0);
		config.set("stats." + uuid + ".stats."+"coins", 0);
		config.set("stats." + uuid + ".stats."+"wins", 0);
		config.set("stats." + uuid + ".uuid", p.getUniqueId().toString());
		config.set("stats." + uuid + ".ip", p.getAddress().getAddress().getHostAddress());
		config.set("stats." + uuid + ".classes", classes);
		for(UpgradeType type : UpgradeType.values()){
			for(me.main.yoni.ClassSystem.Class c : ClassManager.getClasses()){
				new Upgrade(p, c, type).reset();
			}
		}
		config.saveConfig();
		mplayers.add(getmplayerByConfig(uuid));
		refresh(p);
		getMPlayer(p.getName()).setCurrentClass(ClassManager.getClass("Zombie"));
		p.updateInventory();
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "New player has joined the server - " + uuid);
	}
	
	public static ArrayList<MPlayer> getMPlayers() {
		return mplayers;
	}
	
	public static MPlayer getMPlayer(UUID uuid) {
		if(mplayers == null) return null;
		for(MPlayer sgplayer : mplayers) {	
			if(uuid.toString().equals(sgplayer.getUUID())) {
				return sgplayer;
			}
		}
		return null;
	}
	public static MPlayer getMPlayer(String p) {
		if(mplayers == null) return null;
		for(MPlayer sgplayer : mplayers)  {
			if(sgplayer.getName() == null) continue;
			if(sgplayer.getName().equalsIgnoreCase(p)) 
				return sgplayer;
		}
		return null;
	}

}
