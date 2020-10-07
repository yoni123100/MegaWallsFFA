package me.main.yoni.Listeners;

import java.util.ArrayList;
import java.util.HashMap;

import me.main.yoni.Main;
import me.main.yoni.API.ItemStackCreator;
import me.main.yoni.API.Utils;
import me.main.yoni.ArenaSystem.Arena;
import me.main.yoni.ArenaSystem.ArenaManager;
import me.main.yoni.ArenaSystem.Duel;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.defaults.GiveCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class OneOnOneListener implements Listener {
	
	public static HashMap<Player, Player> accept = new HashMap<>();
	public static HashMap<Player, ArrayList<Player>> accepted = new HashMap<>();
	public static ArrayList<String> queue = new ArrayList<>();
	public static ArrayList<String> cd = new ArrayList<>();
	
	@EventHandler
	public void respawn(final PlayerRespawnEvent e) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				if(Main.lobby.contains(e.getPlayer().getName())) {
					Utils.give1v1Items(e.getPlayer());
				} else{
					Utils.giveItems(e.getPlayer());
				}
				
			}
		}, 10);
	}
	
	@EventHandler
	public void interact(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction().name().contains("RIGHT") == false) return;
		if(p.getItemInHand() == null) return;
		if(p.getItemInHand().getType() == null) return;
		if(p.getItemInHand().getItemMeta() == null) return;
		if(p.getItemInHand().getItemMeta().getDisplayName() == null) return;
		if(p.getItemInHand().getItemMeta().getDisplayName().contains("§6Queue")){
			e.setCancelled(true);
			if(TeamManager.getTeamByPlayer(p) != null) {
				p.sendMessage(ChatColor.RED + "You can't enter the queue while in Team!");
				return;
			}
			Arena arena = null;
			for(Arena a : ArenaManager.getArenas()) {
				if(a == null || a.isFull()) continue;
				arena = a;
				break;
			}
			if(arena == null) {
				p.sendMessage(ChatColor.RED + "All the arenas are full!");
				return;
			}
			if(OneOnOneListener.accepted.containsKey(p)) {
				OneOnOneListener.accepted.remove(p);
			}
			if(queue.contains(p.getName())) {
				queue.remove(p.getName());
				p.setItemInHand(ItemStackCreator.createItem(new ItemStack(Material.getMaterial(351),1,(byte)8), "§6Queue (§cOFF§6)", 1));
			} else {
				queue.add(p.getName());
				p.setItemInHand(ItemStackCreator.createItem(new ItemStack(Material.getMaterial(351),1,(byte)10), "§6Queue (§aON§6)", 1));
				if(queue.size() == 2) {
					Duel.sendTheAcceptingGUI(Bukkit.getPlayer(queue.get(0)), Bukkit.getPlayer(queue.get(1)), new ArrayList<Player>(), new ArrayList<Player>(),arena);
					queue.clear();
				}
			}
			return;
		}
	}
	
	@EventHandler
	public void deathRemake(PlayerDeathEvent e) {
		final Player p = (Player)e.getEntity();
		if(ArenaManager.getArenaByPlayer(p) == null){
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					p.setHealth(20);
					
				}
			}, 1);
			p.setGameMode(GameMode.SPECTATOR);
			Utils.sendTitle(p, "§c§lYOU DIED!",0,80,0);
			Utils.sendSubTitle(p, "§7Respawn in §b"+"3"+" §7seconds!");
			new BukkitRunnable() {
				
				int time = 3;
				@Override
				public void run() {
					if(time <= 0) {
						Utils.sendSubTitle(p, "§7Respawn now!");
						if(Main.lobby.contains(p.getName())) {
							Utils.give1v1Items(p);
						} else{
							Utils.giveItems(p);
						}
						cancel();
						return;
					}
					Utils.sendSubTitle(p, "§7Respawning in §b"+time+" §7seconds!");
					time--;
				}
			}.runTaskTimer(Main.getPlugin(), 0, 20);
			return;
		}
		Arena a = ArenaManager.getArenaByPlayer(p);
		if(a == null) return;
		final Location loc = p.getLocation();
		a.removePlayer(p.getName());
		p.setHealth(20);
		p.setGameMode(GameMode.SPECTATOR);
		p.teleport(loc);
		int t1 = 0;
		int t2 = 0;
		for(String s : a.getSpec()){
			if(a.getTeam_1().contains(s)) {
				t1++;
			}
			if(a.getTeam_2().contains(s)) {
				t2++;
			}
		}
		if(a.getTeam_2().size() == t2) {
			//Team 1 Won
			for(String pp : a.getTeam_1()) {
				MPlayerManager.getMPlayer(Bukkit.getOfflinePlayer(pp).getName()).addWins(1);
			}
			a.BroadCast(ChatColor.GREEN + "" + a.getTeam_1() + ChatColor.GOLD + " has won vs " + ChatColor.RED + a.getTeam_2());
			a.clearAllTeams(true);
			a.setFull(false);
		}
		if(a.getTeam_1().size() == t1) {
			//Team 2 Won
			for(String pp : a.getTeam_2()) {
				MPlayerManager.getMPlayer(Bukkit.getOfflinePlayer(pp).getName()).addWins(1);
			}
			a.BroadCast(ChatColor.GREEN + "" + a.getTeam_2() + ChatColor.GOLD + " has won vs " + ChatColor.RED + a.getTeam_1());
			a.clearAllTeams(true);
			a.setFull(false);
		}
	}
	
	@EventHandler
	public void click(PlayerInteractEntityEvent e) {
		if(Main.playing.contains(e.getPlayer().getName())) return;
		if(!Main.lobby.contains(e.getPlayer().getName())) return;
		if(!(e.getRightClicked() instanceof Player)) return;
		if(e.getPlayer().getItemInHand().getType() != Material.DIAMOND_SWORD) return;
		final Player target = (Player)e.getRightClicked();
		final Player p = (Player)e.getPlayer();
		if(!Main.lobby.contains(p.getName())) {
			p.sendMessage(ChatColor.RED + "You're not in the 1v1 lobby!");
			return;
		}
		if(queue.contains(p)) {
			p.sendMessage(ChatColor.RED + "You must leave the queue first!");
			return;
		}
		if(!Main.lobby.contains(target.getName())) {
			p.sendMessage(ChatColor.RED + "This player is not in the 1v1 lobby!");
			return;
		}
		if(cd.contains(p.getName())) return;
		Arena arena = null;
		for(Arena a : ArenaManager.getArenas()) {
			if(!a.isFull() || a.getTeam_1().size() == 0 || a.getTeam_2().size() == 0) {
				arena = a;
			}
		}
		if(arena == null) {
			p.sendMessage(ChatColor.RED + "All the arenas are full!");
			return;
		}
		if(accepted.containsKey(p)) return;
		if(TeamManager.getTeamByPlayer(p) != null) {
			if(TeamManager.getTeamByPlayer(p).getPlayers().contains(target)) {
				p.sendMessage(ChatColor.RED + "This player is in your team!");
				return;
			}
		}
		if(accept.containsKey(p)) {
			if(accept.get(p) == target) {
				p.sendMessage(ChatColor.RED + "You already invited this player!");
				return;
			}
		}
		if(queue.contains(target)) {
			p.sendMessage(ChatColor.RED + "This player is in a Queue!");
			return;
		}
		if(accept.containsKey(target)) {
			if(accept.get(target) == p) {
				accept.remove(target);	
				Duel.sendTheAcceptingGUI(p, target, new ArrayList<Player>(), new ArrayList<Player>(),arena);
				return;
			}
		}
		if(TeamManager.getTeamByPlayer(p) != null && TeamManager.getTeamByPlayer(p).getLeader() != p) {
			p.sendMessage(ChatColor.RED + "You can't 1v1 while you're not the team leader!");
			return;
		}
		if(TeamManager.getTeamByPlayer(target) != null && TeamManager.getTeamByPlayer(target).getLeader() != target) {
			p.sendMessage(ChatColor.RED + "You can't invite this player, because he is not the team leader!");
			return;
		}
		if(TeamManager.getTeamByPlayer(p)!=null) {
			for(Player ps : TeamManager.getTeamByPlayer(p).getPlayers()) {
				if(!Main.lobby.contains(ps.getName())) {
					p.sendMessage(ChatColor.RED + "You can't invite other players until all of your teammates will be in lobby.");
					return;
				}
			}
		}
		accept.put(p, target);
		p.sendMessage(ChatColor.GREEN + "You invited " + target.getName() + "!");
		target.sendMessage(ChatColor.GREEN + p.getName() + " invited you to a duel!");
		if(TeamManager.getTeamByPlayer(p) != null) {
			ArrayList<Player> list = TeamManager.getTeamByPlayer(p).getPlayers();
			ArrayList<String> list2 = new ArrayList<>();
			for(Player ps : list) {
				list2.add(ps.getName());
			}
			target.sendMessage(ChatColor.YELLOW + "This player is in a Team with: " + ChatColor.BOLD + list2);
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				if(accept.containsKey(p)) {
					if(accept.get(p) == target) {
						accept.remove(p);
						p.sendMessage(ChatColor.RED + "Your duel request to " + target.getName() + " canceled!");
						target.sendMessage(ChatColor.RED + "Your duel request from " + p.getName() + " canceled!");
					}
				}
			}
		}, 20 * 10);
	}

}
