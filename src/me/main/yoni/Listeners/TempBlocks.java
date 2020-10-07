package me.main.yoni.Listeners;

import java.util.HashMap;

import me.main.yoni.Main;
import me.main.yoni.API.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class TempBlocks implements Listener {
	
	public static HashMap<Block, Player> playerBlock = new HashMap<>();
	
	@EventHandler
	public void breakBlock(BlockBreakEvent e) {
		if(e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		if(!playerBlock.containsKey(e.getBlock())) {
			e.setCancelled(true);
			return;
		}
		e.setCancelled(true);
		playerBlock.remove(e.getBlock());
		for(Player ps : Bukkit.getOnlinePlayers()) {
			if(ps == e.getPlayer()) continue;
			ps.playEffect(e.getBlock().getLocation(), Effect.STEP_SOUND, Material.getMaterial(e.getBlock().getTypeId()));
		}
		e.getBlock().setType(Material.AIR);
	}
	
	@EventHandler
	public void place(final BlockPlaceEvent e) {
		if(e.getPlayer().getGameMode() == GameMode.CREATIVE) return;
		if(e.getBlockReplacedState().getType().name().contains("WATER") || e.getBlockReplacedState().getType().name().contains("LAVA")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(ChatColor.RED + "You can't build on water/lava!");
			return;
		}
		playerBlock.put(e.getBlock(), e.getPlayer());
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				if(e.getBlock() == null) return;
				for(Player ps : Bukkit.getOnlinePlayers()) {
					ps.playEffect(e.getBlock().getLocation(), Effect.STEP_SOUND, Material.getMaterial(e.getBlock().getTypeId()));
				}
				e.getBlock().setType(Material.AIR);
				playerBlock.remove(e.getBlock());
			}
		}, 20 * Inventories.getTimeBlocks(e.getPlayer()));
	}

}
