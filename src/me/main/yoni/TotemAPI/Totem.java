package me.main.yoni.TotemAPI;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Totem {
	
	private Location loc;
	
	public Totem(Location loc) {
		this.loc = loc;
	}
	
	public Location getLocation() {
		return loc;
	}
	
	public void create() {
		//Creating the blocks.
		Location place = loc;
		place.getBlock().setType(Material.STONE);
		place.add(0, 1, 0).getBlock().setType(Material.COBBLE_WALL);
		place.add(0, 1, 0).getBlock().setType(Material.STAINED_CLAY);
		place.add(0, 0, 0).getBlock().setData((byte)5);
		for(Player online : Bukkit.getOnlinePlayers()) {
			online.playEffect(loc, Effect.STEP_SOUND, Material.getMaterial(loc.getBlock().getTypeId()));
			online.playEffect(loc.add(0, 1, 0), Effect.STEP_SOUND, Material.getMaterial(loc.add(0, 1, 0).getBlock().getTypeId()));
			online.playEffect(loc.add(0, 1, 0), Effect.STEP_SOUND, Material.getMaterial(loc.add(0, 1, 0).getBlock().getTypeId()));
		}
	}

}
