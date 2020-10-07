package me.main.yoni.ClassSystem.Abilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import me.main.yoni.API.FireworkEffectPlayer;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;


public class Beam {

	public static List<Entity> getNearbyEntites(Location l, int size) {
		List<Entity> entities = new ArrayList<Entity>();
		for(Entity ent : l.getWorld().getEntities()) {
			if(!(ent instanceof LivingEntity)) continue;
			if(!(ent instanceof Player)) continue;
			if(ent.isDead()) continue;
			if(ent == null) continue;
			if(!ent.getWorld().equals(l.getWorld())) continue;
			if(l.distance(ent.getLocation()) <= size) {
				entities.add(ent);
			}
		}
		return entities;
	}
	
	@SuppressWarnings("deprecation")
	public static void shoot(Player p,double damage) {
		for(Block b : p.getLineOfSight((HashSet<Byte>)null, 50)) {
			try {
				FireworkEffectPlayer.playFirework(
						p.getWorld(), b.getLocation(),
						FireworkEffect.builder().with(Type.BURST)
						.withColor(Color.WHITE).build());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			for(Entity ent : getNearbyEntites(b.getLocation(),2)) {
				if(ent instanceof Player) {
					Player nearby = (Player)ent;
					if(nearby == p) continue;
					if(TeamManager.getTeamByPlayer(p) != null) {
						if(TeamManager.getTeamByPlayer(p).getPlayers().contains(ent)) {
							continue;
						}
					}
					nearby.damage(damage,p);
				}
			}
		}
	}

}
