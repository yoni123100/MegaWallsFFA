package me.main.yoni.MuteSystem;

import java.util.ArrayList;
import java.util.UUID;

import me.main.yoni.Configuration;
import me.main.yoni.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MuteManager {
	
	private static MuteManager instance = new MuteManager();
	
	public static MuteManager getInstance() {
		return instance;
	}
	
	public static String getPrefix = ChatColor.RED + "" + ChatColor.BOLD + "MuteSystem " + ChatColor.DARK_GRAY + "» "; 

	public static ArrayList<Mute> mutes;
	
	public static void Setup() {
		mutes = new ArrayList<Mute>();
		
		if(Configuration.scanFolder("Mutes") == null) return;
		for(String c : Configuration.scanFolder("Mutes")) {
			if(c == null) return;
			if(c.contains("dont_touch")) continue;
			mutes.add(getMuteFromConfig(c));
		}
	}
	
	public static Mute getMuteFromConfig(String player) {
		OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(player));
		new Configuration(p.getUniqueId().toString(),"Mutes");
		if(Configuration.getConfig(p.getUniqueId().toString(),"Mutes") == null) return null;
		Configuration config = Configuration.getConfig(p.getUniqueId().toString(),"Mutes");
		String reason = config.getString("reason");
		String by = config.getString("by");
		int time = config.getInt("time");
		return new Mute(p,by, reason, time);
	}
	
	public static ArrayList<Mute> getMutes() {
		return mutes;
	}
	
	public static Mute getMute(OfflinePlayer p) {
		for(Mute m : mutes) {
			if(m.getPlayer() == p) {
				return m;
			}
			if(m.getPlayer().getUniqueId().equals(p.getUniqueId())) { 
				return m;
			}
		}
		return null;
	}
	
	public static void runMutes() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Mute m : mutes) {
					if(m.getTime() > 0) {
						m.setTime(m.getTime() - 1);
					}
					if(m.getTime() <= 0) {
						for(Player ps : Bukkit.getOnlinePlayers()) {
							if(ps.hasPermission("megawallsffa.command.mute")) {
								ps.sendMessage(getPrefix + ChatColor.GREEN + m.getPlayer().getName() + " mute is over!");
							}
						}
						if(m.getPlayer().isOnline()) {
							m.getPlayer().getPlayer().sendMessage(getPrefix + ChatColor.GREEN+"Your mute is over!");
						}
						m.unMute();
					}
				}
			}
		}.runTaskTimer(Main.getPlugin(), 20, 20);
	}

}
