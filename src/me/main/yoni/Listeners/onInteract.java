package me.main.yoni.Listeners;

import java.text.DecimalFormat;
import java.util.ArrayList;

import me.main.yoni.Main;
import me.main.yoni.API.Inventories;
import me.main.yoni.API.Utils;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class onInteract implements Listener {
	
	public static ArrayList<String> list = new ArrayList<>();
	
	@EventHandler
	public void click(final PlayerInteractEvent e){
		if(e.getPlayer().getItemInHand().getType() == Material.COMPASS) {
			if(!Main.playing.contains(e.getPlayer().getName())) return;
			e.setCancelled(true);
			if(list.contains(e.getPlayer().getName())) {
				Utils.sendActionBar(e.getPlayer(), ChatColor.RED + "Please don't spam the compass!");
				return;
			}
			Player target = null;
			for(Entity ent : e.getPlayer().getWorld().getEntities()) {
				if(!(ent instanceof Player)) continue;
				if(ent == e.getPlayer()) continue;
				if(!Main.playing.contains(ent.getName())) continue;
				if(!ent.getWorld().equals(e.getPlayer().getWorld())) continue;
				if(target == null || ent.getLocation().distance(e.getPlayer().getLocation()) < target.getLocation().distance(e.getPlayer().getLocation())) {
					target = (Player) ent;
				}
			}
			if(target == null) {
				Utils.sendActionBar(e.getPlayer(),ChatColor.RED + "There is no target!");
				return;
			}
			double dis = target.getLocation().distance(e.getPlayer().getLocation());
			e.getPlayer().setCompassTarget(target.getLocation());
			Utils.sendActionBar(e.getPlayer(), "Tracking: " + ChatColor.GREEN + target.getName() + ChatColor.WHITE + " - " + "Distance: " + ChatColor.GREEN + new DecimalFormat("0.0").format(dis));
			list.add(e.getPlayer().getName());
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					list.remove(e.getPlayer().getName());
					
				}
			}, 20 * 3);
		}
	}

	@EventHandler
	public void drop(PlayerDropItemEvent e) {
		if(e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
	}
	
	@EventHandler
	public void interact(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction().name().contains("RIGHT") == false) return;
		if(p.getItemInHand() == null) return;
		if(p.getItemInHand().getType() == null) return;
		if(p.getItemInHand().getItemMeta() == null) return;
		if(p.getItemInHand().getItemMeta().getDisplayName() == null) return;
		if(p.getItemInHand().getItemMeta().getDisplayName().contains("§cReturn to lobby")){
			e.setCancelled(true);
			if(Utils.cd.contains(p.getName())) {
				p.sendMessage(ChatColor.RED + "Please wait!");
				return;
			}
			if(Main.getFFALobby() == null) {
				p.sendMessage(ChatColor.RED + "There is no FFA lobby so you cant spawn with your class!");
				return;
			}
			if(Main.lobby.contains(p.getName())) {
				Main.lobby.remove(p.getName());
			}
			p.teleport(Main.getFFALobby());
			Utils.giveItems(p);
			p.sendMessage(ChatColor.RED + "You left the 1v1 lobby!");
			return;
		}
		if(p.getItemInHand().getItemMeta().getDisplayName().contains(ChatColor.GREEN + "My Profile " + org.bukkit.ChatColor.GRAY + "(Right Click)")){
			e.setCancelled(true);
			Inventories.openStats(p, p);
		}
		if(p.getItemInHand().getItemMeta().getDisplayName().contains("§aStaff Spectate Item§7 (Right Click)")){
			e.setCancelled(true);
			if(Utils.cd.contains(p.getName())) {
				p.sendMessage(ChatColor.RED + "Please wait!");
				return;
			}
			Inventories.openSpecInventory(p);
		}
		if(p.getItemInHand().getItemMeta().getDisplayName().contains("Game Menu")){
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "Coming Soon...");
		}
		if(p.getItemInHand().getItemMeta().getDisplayName().contains("Class Selector")){
			e.setCancelled(true);
			Inventories.openClassSelector(p);
		}
		if(p.getItemInHand().getItemMeta().getDisplayName().contains("Shop")){
			e.setCancelled(true);
			Inventories.openShop(p);
		}
		if(p.getItemInHand().getItemMeta().getDisplayName().contains("PLAY!")){
			e.setCancelled(true);
			if(Utils.cd.contains(p.getName())) {
				p.sendMessage(ChatColor.RED + "Please wait!");
				return;
			}
			Main.randomSpawn(p);
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		Player p = e.getPlayer();
		ItemStack item = e.getItemDrop().getItemStack();
		if(item.getType() == null) return;
		if(item.getItemMeta() == null) return;
		if(item.getItemMeta().getDisplayName() == null) return;
		if(item.getItemMeta().getDisplayName().contains("Class Selector")){
			e.setCancelled(true);
			p.updateInventory();
		}
	}

}
