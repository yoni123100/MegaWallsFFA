package me.main.yoni.ArenaSystem;

import java.util.ArrayList;

import me.main.yoni.Main;
import me.main.yoni.API.Inventories;
import me.main.yoni.API.ItemStackCreator;
import me.main.yoni.Listeners.OneOnOneListener;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Duel {
	
	public static ItemStack getInfoItem(ArrayList<Player> list1 , ArrayList<Player> list2) {
		ArrayList<String> lore = new ArrayList<>();
		lore.add(ChatColor.BLUE + "Group 1: ");
		for(Player ps : list1) {
			if(MPlayerManager.getMPlayer(ps.getName()).getCurrentDuelClass() == null) {
				lore.add(ChatColor.RED + ps.getName() + " - NOT READY");
			} else {
				lore.add(ChatColor.GREEN + ps.getName() + " - READY - " + MPlayerManager.getMPlayer(ps.getName()).getCurrentDuelClass().getName());
			}
		}
		lore.add(ChatColor.BLUE + "Group 2: ");
		for(Player ps : list2) {
			if(MPlayerManager.getMPlayer(ps.getName()).getCurrentDuelClass() == null) {
				lore.add(ChatColor.RED + ps.getName() + " - NOT READY");
			} else {
				lore.add(ChatColor.GREEN + ps.getName() + " - READY - " + MPlayerManager.getMPlayer(ps.getName()).getCurrentDuelClass().getName());
			}
		}
		return ItemStackCreator.createItem(new ItemStack(Material.BOAT), "§6Information", 1,lore);
	}
	
	public static void sendTheAcceptingGUI(final Player p, final Player target, final ArrayList<Player> list1,final ArrayList<Player> list2,final Arena arena) {
		if(!p.isOnline() || !target.isOnline())return;
		list1.add(p);
		list2.add(target);
		if(TeamManager.getTeamByPlayer(p) != null && TeamManager.getTeamByPlayer(p).getLeader() == p) {
			for(Player ps : TeamManager.getTeamByPlayer(p).getPlayers()) {
				if(ps == p) continue;
				list1.add(ps);
			}
		}
		if(TeamManager.getTeamByPlayer(target) != null && TeamManager.getTeamByPlayer(target).getLeader() == target) {
			for(Player ps : TeamManager.getTeamByPlayer(target).getPlayers()) {
				if(ps == target) continue;
				list2.add(ps);
			}
		}
		OneOnOneListener.accepted.put(p, list1);
		OneOnOneListener.accepted.put(target, list2);
		MPlayerManager.getMPlayer(target.getName()).setCurrentDuelClass(null);
		MPlayerManager.getMPlayer(p.getName()).setCurrentDuelClass(null);
		Main.updateScoreboard(target);
		Main.updateScoreboard(p);
		arena.setFull(true);
		final ArrayList<Player> total = new ArrayList<>();
		for(Player ps : list1) {
			arena.addPlayer(ps, 1);
			MPlayerManager.getMPlayer(ps.getName()).setCurrentDuelClass(null);
			Main.updateScoreboard(ps);
			total.add(ps);
		}
		for(Player ps : list2) {
			arena.addPlayer(ps, 2);
			MPlayerManager.getMPlayer(ps.getName()).setCurrentDuelClass(null);
			Main.updateScoreboard(ps);
			total.add(ps);
		}
		new BukkitRunnable() {
			
			@Override
			public void run() {
				int i = 0;
				if(!OneOnOneListener.accepted.containsKey(p) || !OneOnOneListener.accepted.containsKey(target)) {
					if(OneOnOneListener.accepted.containsKey(p)) {
						OneOnOneListener.accepted.remove(p);
					}
					if(OneOnOneListener.accepted.containsKey(target)) {
						OneOnOneListener.accepted.remove(target);
					}
					for(Player ps : total) {
						ps.sendMessage(ChatColor.RED + "Duel has been canceled!");
						ps.closeInventory();
					}
					for(Player ps : total) {
						arena.clearAllTeams(false);
					}
					total.clear();
					list1.clear();
					list2.clear();
					cancel();
					return;
				}
				for(Player ps : total) {
					if(!ps.isOnline()) {
						total.remove(ps);
						for(Player pps : total) {
							pps.sendMessage(ChatColor.RED + ps.getName() + " has left the duel!");
						}
						if(OneOnOneListener.accepted.containsKey(ps)) {
							OneOnOneListener.accepted.remove(ps);
						}
					}
					if(!ps.getOpenInventory().getTopInventory().getName().equalsIgnoreCase("Class Interface")) {
						Inventories.OneOnOneGUI(ps,list1,list2);
					}
					ps.getOpenInventory().setItem(53, getInfoItem(list1, list2));
					if(MPlayerManager.getMPlayer(ps.getName()).getCurrentDuelClass() == null) continue;
					i++;
				}
				if(i == total.size()) {
					arena.startFight();
					cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0, 10);
	}

}
