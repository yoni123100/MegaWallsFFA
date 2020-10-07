package me.main.yoni.Listeners;

import me.main.yoni.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class onItemConsume implements Listener{

	@EventHandler
	public void onItemCosume(final PlayerItemConsumeEvent e) {
		if(e.getPlayer().getItemInHand().hasItemMeta() == false) return;
		if(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("Potion of Heal")) {
			String[] strings = e.getPlayer().getItemInHand().getItemMeta().getDisplayName().split(" ");
			String sNum = strings[3].replace("(", "");
			sNum = ChatColor.stripColor(sNum);
			sNum = sNum.replace("❤)", "");
			int num = Integer.valueOf(sNum)*2;
			double health = ((Damageable) e.getPlayer()).getHealth();
			Damageable damageable = (Damageable)e.getPlayer();
			e.setCancelled(true);
			ItemStack item = e.getItem();
			if(item.getAmount() < 2) {
				e.getPlayer().setItemInHand(new ItemStack(Material.AIR));
			} else {
				item.setAmount(item.getAmount() - 1);
				e.getPlayer().setItemInHand(item);
			}
			e.getPlayer().updateInventory();
			e.getPlayer().setHealth(health + num > damageable.getMaxHealth() ? damageable.getMaxHealth() : health + num);
			return;
		}
		if(e.getItem().getType() == Material.POTION) {
			ItemStack item = e.getItem();
			if(item.getAmount() == 0) return;
			item.setAmount(item.getAmount() - 1);
			e.setItem(item);
			e.getPlayer().setItemInHand(item);
			e.getPlayer().updateInventory();
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					if(!e.getPlayer().isOnline()) return;
					e.getPlayer().updateInventory();
					
				}
			}, 1);
		}
	}
}
