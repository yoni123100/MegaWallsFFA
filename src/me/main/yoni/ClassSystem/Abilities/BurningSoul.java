package me.main.yoni.ClassSystem.Abilities;

import java.util.ArrayList;
import java.util.List;

import me.main.yoni.Main;
import me.main.yoni.API.EffectUtils;
import me.main.yoni.API.Utils;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;


public class BurningSoul {
	
	public static List<Entity> getNearbyEntites(Location l, int size) {
		List<Entity> entities = new ArrayList<Entity>();
		
		for(Entity ent : l.getWorld().getEntities()) {
			if(ent == null) continue;
			if(ent.isDead()) continue;
			if(!(ent instanceof LivingEntity)) continue;
			if(!ent.getWorld().equals(l.getWorld())) continue;
			if(l.distance(ent.getLocation()) <= size) {
				entities.add(ent);
				
			}
		}
		return entities;
	}
	
	public static void spawnBubble(final Player p, final double damage, final int seconds, double Sseconds) {
		p.setLevel(0);
		p.setExp(0);
		final Location loc = p.getLocation();
		EffectUtils.createSphere(p,loc, 5, 20*seconds);
		double time = 20 * Sseconds;
		p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,(int) time,0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS,(int) time,10));
		new BukkitRunnable() {
			
			int i = seconds+1;
			@Override
			public void run() {
				if(!Main.playing.contains(p.getName())) {
					cancel();
					return;
				}
				i--;
				if(i == 0) {
					cancel();
					return;
				}
				for(Entity ent : getNearbyEntites(loc, 5)) {
					if(ent == p) continue;
					if(ent instanceof LivingEntity){
						if(TeamManager.getTeamByPlayer(p) != null) {
							if(TeamManager.getTeamByPlayer(p).getPlayers().contains(ent)) {
								continue;
							}
						}
						Utils.realDamage(ent, p, damage);
					}
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0, 20);
	}
	
	public static void use(Player p, int upgrade) {
		double damage = 0.875;
		double Sseconds = 0.875;
		int seconds = 3;
		for(int i = 0; i < upgrade; i++) {
			damage += 0.125;
			Sseconds += 0.125;
		}
		if(upgrade < 5) {
			seconds = 3;
		}
		if(upgrade < 9 && upgrade >= 5) {
			seconds = 4;
		}
		if(upgrade == 9) {
			seconds = 5;
		}
		spawnBubble(p, damage, seconds, Sseconds);
	}
	
}
