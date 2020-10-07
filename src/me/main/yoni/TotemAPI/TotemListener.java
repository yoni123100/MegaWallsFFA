package me.main.yoni.TotemAPI;

import java.util.ArrayList;
import java.util.HashMap;

import me.main.yoni.Main;
import me.main.yoni.API.Utils;
import me.main.yoni.ClassSystem.Class;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.PlayerSystem.MPlayerManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class TotemListener implements Listener {
	
	ArrayList<String> cd = new ArrayList<>();
	
	HashMap<Player, Block> sblock = new HashMap<>(); 
	
	public static boolean isTotem(Block b) {
		if(b == null || b.getType() == Material.AIR) return false;
		if(b.getType() == Material.STAINED_CLAY && b.getLocation().subtract(0, 1, 0).getBlock().getType() == Material.COBBLE_WALL 
				&& b.getLocation().subtract(0, 2, 0).getBlock().getType() == Material.STONE) {
			return true;
		}
		return false;
	}
	
	public void sendBlock(final Location loc, final Player p) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
			
			@Override
			public void run() {
				p.sendBlockChange(loc, Material.STAINED_CLAY, (byte) 14);
				
			}
		}, 1);
	}
	
	public void autoArmor(Player p) {
		Class c = MPlayerManager.getMPlayer(p.getName()).getCurrentClass();
		Upgrade upgrade = new Upgrade(p, c,UpgradeType.KIT);
		for(ItemStack item : c.getStartingItems(upgrade.getCurrentUpgrade()).values()) { 
			if(item == null || item.getType() == Material.AIR) continue;
			if(item.getType().toString().contains("HELMET")) {
				p.getInventory().setHelmet(item);
				p.getInventory().remove(item);
			}
			if(item.getType().toString().contains("CHESTPLATE")) {
				p.getInventory().setChestplate(item);
				p.getInventory().remove(item);
			}
			if(item.getType().toString().contains("LEGGINGS")) {
				p.getInventory().setLeggings(item);
				p.getInventory().remove(item);
			}
			if(item.getType().toString().contains("BOOTS")) {
				p.getInventory().setBoots(item);
				p.getInventory().remove(item);
			}
		}
		if(p.getInventory().getHelmet() == null) {
			p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		}
		if(p.getInventory().getChestplate() == null) {
			p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		}
		if(p.getInventory().getLeggings() == null) {
			p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		}
		if(p.getInventory().getBoots() == null) {
			p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		}
		p.updateInventory();
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void click(final PlayerInteractEvent e) {
		if(e.getAction().name().contains("RIGHT") || e.getAction().name().contains("LEFT")) {
			if(isTotem(e.getClickedBlock())) {
				if(!cd.contains(e.getPlayer().getName())) {
					e.getPlayer().sendMessage(ChatColor.GRAY + "Block Refill!");
					for(ItemStack block : e.getPlayer().getInventory().getContents()) {
						if(block == null || block.getType() == Material.AIR || block.getType() == Material.COMMAND) continue;
						if(block.getType().isBlock()) {
							e.getPlayer().getInventory().remove(block);
						}
					}
					e.getPlayer().updateInventory();
					new BukkitRunnable() {
						
						Class c = MPlayerManager.getMPlayer(e.getPlayer().getName()).getCurrentClass();
						int i = 0;
						@Override
						public void run() {
							if(!Main.playing.contains(e.getPlayer().getName())) {
								cancel();
								return;
							}
							if(i >= (MPlayerManager.getMPlayer(e.getPlayer().getName()).getBlocksUpgrade() + 1) * 16) {
								e.getPlayer().sendMessage(ChatColor.GREEN + "Block Swap Completed!");
								autoArmor(e.getPlayer());
								if(c.getName().equalsIgnoreCase("skeleton") || c.getName().equalsIgnoreCase("hunter") || c.getName().equalsIgnoreCase("blaze")) {
									for(ItemStack item : e.getPlayer().getInventory().getContents()){
										if(item == null || item.getType() != Material.ARROW) continue;
										e.getPlayer().getInventory().remove(item);
									}
									e.getPlayer().getInventory().addItem(new ItemStack(Material.ARROW,32));
								}
								cancel();
								return;
							}
							if(i <= 4) {
								if(MPlayerManager.getMPlayer(e.getPlayer().getName()).getCurrentClass() != null)
								e.getPlayer().getInventory().addItem(Utils.getSteaks(1, MPlayerManager.getMPlayer(e.getPlayer().getName()).getCurrentClass().getName()));
							}
							if(c.getName().equalsIgnoreCase("blaze") || c.getName().equalsIgnoreCase("arcanist")) {
								e.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_ORE,1));
							} else if(c.getName().equalsIgnoreCase("skeleton")) {
								e.getPlayer().getInventory().addItem(new ItemStack(Material.WOOD,1));
							} else {
								e.getPlayer().getInventory().addItem(new ItemStack(Material.COBBLESTONE,1));
							}
							i++;
						}
					}.runTaskTimer(Main.getPlugin(), 2, 2);
					cd.add(e.getPlayer().getName());
					sblock.put(e.getPlayer(), e.getClickedBlock());
					sendBlock(e.getClickedBlock().getLocation(), e.getPlayer());
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
						
						@Override
						public void run() {
							cd.remove(e.getPlayer().getName());
							e.getPlayer().sendBlockChange(e.getClickedBlock().getLocation(), Material.STAINED_CLAY, (byte) 5);
							sblock.remove(e.getPlayer());
							e.getPlayer().sendMessage(ChatColor.GREEN + "Your totem cooldown is over!");
						}
					}, 20 * 35);
					return;
				}
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.GRAY+ "You totem is still on cooldown!");
				if(sblock.containsKey(e.getPlayer())) {
					if(sblock.get(e.getPlayer()).equals(e.getClickedBlock())) {
						sendBlock(e.getClickedBlock().getLocation(), e.getPlayer());
					}
				}
			}
		}
	}

}
