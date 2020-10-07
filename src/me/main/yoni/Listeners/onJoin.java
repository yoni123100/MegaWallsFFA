package me.main.yoni.Listeners;

import java.util.ArrayList;

import me.main.yoni.Configuration;
import me.main.yoni.Main;
import me.main.yoni.ArenaSystem.ArenaManager;
import me.main.yoni.ClassSystem.ClassManager;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.Team;
import me.main.yoni.TeamSystem.TeamManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class onJoin implements Listener {
	
	@EventHandler
	public void mvoe(PlayerMoveEvent e) {
		if(ArenaManager.move.contains(e.getPlayer().getName())) {
			e.setTo(e.getFrom());
		}
	}
	
	@EventHandler
	public void left(final PlayerQuitEvent e) {
		if(!e.getPlayer().hasPermission("megawallsffa.command.staffchat")) return;
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				for(Player ps : Bukkit.getOnlinePlayers()) {
					if(ps.hasPermission("megawallsffa.command.staffchat")) {
						ps.sendMessage(ChatColor.AQUA + "[STAFF] " + ChatColor.RESET + e.getPlayer().getDisplayName() + ChatColor.YELLOW + " left.");
					}
				}
			}
		}, 5);
	}
	
	public static void checkIfOldPlayer(Player p) {
		if(Configuration.scanFolder("PlayerData") == null){
			MPlayerManager.create(p);
			return;
		}
		boolean found = false;
		for(String s : Configuration.scanFolder("PlayerData")) {
			if(s.equalsIgnoreCase(p.getUniqueId().toString())) {
				found = true;
			}
		}
		if(!found) {
			MPlayerManager.create(p);
			return;
		}
		String uuid = p.getUniqueId().toString();
		Configuration.getConfig("PlayerData").set("stats." + uuid + ".name", p.getName());
		Configuration.getConfig("PlayerData").saveConfig();;
		Configuration copy = Configuration.getConfig(uuid,"PlayerData");
		Configuration config = Configuration.getConfig("PlayerData");
		config.set("stats." + uuid + ".stats."+"kills", copy.getInt("stats."+"kills"));
		config.set("stats." + uuid + ".stats."+"deaths", copy.getInt("stats."+"deaths"));
		config.set("stats." + uuid + ".stats."+"coins", copy.getInt("stats."+"coins"));
		config.set("stats." + uuid + ".stats."+"wins", copy.getInt("stats."+"wins"));
		config.set("stats." + uuid + ".uuid", p.getUniqueId().toString());
		config.set("stats." + uuid + ".ip", p.getAddress().getAddress().getHostAddress());
		config.set("stats." + uuid + ".classes", copy.getStringList("classes"));
		for(UpgradeType type : UpgradeType.values()){
			for(me.main.yoni.ClassSystem.Class c : ClassManager.getClasses()){
				new Upgrade(p, c, type).reset();
				
				for(int i = 0; i<copy.getInt("stats." + uuid + ".upgrades.BLOCKS"); i++) {
					new Upgrade(p, UpgradeType.BLOCKS).upgrade();
				}
				for(int i = 0; i<copy.getInt("stats." + uuid + ".upgrades.TEAM"); i++) {
					new Upgrade(p, UpgradeType.TEAM).upgrade();
				}
			}
		}
		config.saveConfig();
		MPlayerManager.mplayers.add(MPlayerManager.getmplayerByConfig(uuid));
		MPlayerManager.refresh(p);
		MPlayerManager.getMPlayer(p.getName()).setCurrentClass(ClassManager.getClass("Zombie"));
		copy.getFile().delete();
		p.updateInventory();
		p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "NOTICE: we changed our player system, there may be bugs.");
	}
	
	@EventHandler
	public void join(final PlayerJoinEvent e) {
		checkIfOldPlayer(e.getPlayer());
		if(Main.getFFALobby() != null) {
			e.getPlayer().teleport(Main.getFFALobby());
		}
		if(!e.getPlayer().hasPermission("megawallsffa.command.staffchat")) return;
		final ArrayList<Player> list = new ArrayList<>();
		for(Player ps : Bukkit.getOnlinePlayers()) {
			if(ps.hasPermission("megawallsffa.command.staffchat")) {
				list.add(ps);
			}
		}
		if(list.size() == 0) {
			list.clear();
			return;
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
			
			@Override
			public void run() {
				for(Player ps : list) {
					if(ps == null || !ps.isOnline()) continue;
					ps.sendMessage(ChatColor.AQUA + "[STAFF] " + ChatColor.RESET + e.getPlayer().getDisplayName() + ChatColor.YELLOW + " joined.");
				}
				list.clear();
			}
		}, 10);
	}
	
	@EventHandler
	public void join(PlayerQuitEvent e) {
		if(TeamManager.getTeamByPlayer(e.getPlayer()) != null) {
			Team t = TeamManager.getTeamByPlayer(e.getPlayer());
			if(t.getLeader() == e.getPlayer()) {
				t.destroy();
			} else {
				t.removePlayer(e.getPlayer());
			}
		}
		e.setQuitMessage(ChatColor.YELLOW + e.getPlayer().getName() + " left.");
	}

}
