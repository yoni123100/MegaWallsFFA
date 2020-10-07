package me.main.yoni.Listeners;

import java.util.HashMap;

import me.main.yoni.Main;
import me.main.yoni.API.Utils;
import me.main.yoni.ArenaSystem.ArenaManager;
import me.main.yoni.BoosterSystem.BoosterManager;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.TeamManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;


public class onDeath implements Listener {
	
	public static HashMap<Player, Integer> ks = new HashMap<>();
	
	@EventHandler
	public void join(final PlayerJoinEvent e) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				Utils.giveItems(e.getPlayer());
				
			}
		}, 5);
	}
	
	@EventHandler
	public void death(PlayerDeathEvent e) {
		e.setDeathMessage(null);
		e.setDroppedExp(0);
		e.getDrops().clear();
		if(Assists.Added.containsKey(e.getEntity())) {
			for(Player ps : Assists.Added.get(e.getEntity())) {
				if(!ps.isOnline()) continue;
				if(e.getEntity().getKiller() instanceof Player) {
					Player killer = (Player)e.getEntity().getKiller();
					if(ps.getUniqueId().toString().equalsIgnoreCase(killer.getUniqueId().toString())){
						continue;
					}
				}
				int coins = Utils.getCoinsForKill(ps);
	        	if(BoosterManager.getBoosters().size() > 0) {
	        		coins = coins * 2;
	        	}
				if(Main.isTripleCoins()) {
					coins = coins * 2;
				}
	        	if(BoosterManager.getBoosters().size() > 0) {
	        		ps.sendMessage(ChatColor.AQUA +""+ChatColor.BOLD+BoosterManager.getBoosters().get(0).getPlayer().getName() + "'s Network Booster!");
	        	}
				ps.sendMessage("§7You received §6"+coins+" §7coins for assisting §a"+e.getEntity().getName());
				MPlayerManager.getMPlayer(ps.getName()).addCoins(coins);
				Main.updateScoreboard(ps);
			}
			Assists.Added.remove(e.getEntity());
		}
		for(Player ps : Bukkit.getOnlinePlayers()) {
			HashMap<Player, Player> hash = new HashMap<>();
			hash.put(ps, e.getEntity());
			if(Assists.DoneTo.keySet().contains(hash)) {
				Assists.DoneTo.remove(hash);
			}
			hash.clear();
		}
		if(!(e.getEntity() instanceof Player)) return;
		if(e.getEntity().getKiller() instanceof Player) {
			final Player killer = (Player)e.getEntity().getKiller();
			Player noob = (Player)e.getEntity();
			if(ks.containsKey(noob)) {
				ks.remove(noob);
			}
			if(ks.containsKey(killer)) {
				ks.put(killer, ks.get(killer) + 1);
			} else {
				ks.put(killer, 1);
			}
			MPlayerManager.getMPlayer(killer.getName()).addKills(1);
			MPlayerManager.getMPlayer(noob.getName()).addDeaths(1);
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					if(!killer.isDead() || killer.isOnline()) {
						Main.updateScoreboard(killer);
					}
				}
			}, 20*2);
			if(!killer.isDead() || killer.isOnline()) {
				killer.setHealth(killer.getHealth() + 16 >= killer.getMaxHealth() ? killer.getMaxHealth() : killer.getHealth() + 16);
			}
			if(noob.isOnline()) {
				noob.sendMessage("§7You have been killed by " + "§c"+killer.getName());
			}	
			if(TeamManager.getTeamByPlayer(killer) != null) {
				if(TeamManager.getTeamByPlayer(killer).getPlayers().size() > 1) {
					int coins = Utils.getCoinsForKill(killer);
		        	if(BoosterManager.getBoosters().size() > 0) {
		        		coins = coins * 2;
		        	}
					if(Main.isTripleCoins()) {
						coins = coins * 2;
					}
		        	if(BoosterManager.getBoosters().size() > 0) {
		        		killer.sendMessage(ChatColor.AQUA +""+ChatColor.BOLD+BoosterManager.getBoosters().get(0).getPlayer().getName() + "'s Network Booster!");
		        	}
					coins = coins/TeamManager.getTeamByPlayer(killer).getPlayers().size();
					killer.sendMessage(ChatColor.GREEN + killer.getName() + ChatColor.GRAY + " killed " + ChatColor.RED + noob.getName() + ChatColor.GRAY + "!");
					killer.sendMessage(ChatColor.GRAY + "You received " + ChatColor.GREEN + coins + ChatColor.GRAY + " for it! [Coins/"+TeamManager.getTeamByPlayer(killer).getPlayers().size()+"]");
					MPlayerManager.getMPlayer(killer.getName()).addCoins(coins);
					Main.updateScoreboard(killer);
					for(Player ps : TeamManager.getTeamByPlayer(killer).getPlayers()) {
						if(!ps.isOnline()) continue;
						if(ps == killer) continue;
						ps.sendMessage(ChatColor.GREEN + killer.getName() + ChatColor.GRAY + " killed " + ChatColor.RED + noob.getName() + ChatColor.GRAY + "!");
						ps.sendMessage(ChatColor.GRAY + "You received " + ChatColor.GREEN + coins + ChatColor.GRAY + " for it! [Coins/"+TeamManager.getTeamByPlayer(killer).getPlayers().size()+"]");
						MPlayerManager.getMPlayer(ps.getName()).addCoins(coins);
						Main.updateScoreboard(ps);
					}
					return;
				}
			}
			int c = Utils.getCoinsForKill(killer);
        	if(BoosterManager.getBoosters().size() > 0) {
				c = c * 2;
        	}
			if(Main.isTripleCoins()) {
				c = c * 2;
			}
			MPlayerManager.getMPlayer(killer.getName()).addCoins(c);
			if(killer.isOnline()) {
	        	if(BoosterManager.getBoosters().size() > 0) {
	        		killer.sendMessage(ChatColor.AQUA +""+ChatColor.BOLD+BoosterManager.getBoosters().get(0).getPlayer().getName() + "'s Network Booster!");
	        	}
				killer.sendMessage("§7You received §6" + c + " §7coins for killing §a" + e.getEntity().getName()+"§7!");
				Main.updateScoreboard(killer);
			}
			return;
		}
		MPlayerManager.getMPlayer(e.getEntity().getName()).addDeaths(1);
	}
	
	@EventHandler
	public void levelChanged(PlayerLevelChangeEvent e) {
		Player p = e.getPlayer();
		if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != null) {
			if(e.getPlayer().getLevel() > 100) {
				e.getPlayer().setLevel(100);
			}
			float xp = (float)((double) p.getLevel() / 100);
			p.setExp(xp);
		}
	}
	
}
