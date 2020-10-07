package me.main.yoni.ClassSystem.Abilities;

import me.main.yoni.API.EffectUtils;
import me.main.yoni.API.Utils;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;


public class SquidSplash {
	
	public static void use(Player p,double precent) {
		double total = 0;
		for(Entity ent : p.getNearbyEntities(5, 5, 5)) {
			if(ent instanceof LivingEntity && !ent.isDead() && ent instanceof Player) {
				if(ent == p) continue;
				if(TeamManager.getTeamByPlayer(p) != null) {
					if(TeamManager.getTeamByPlayer(p).getPlayers().contains(ent)) {
						continue;
					}
				}
				Utils.realDamage(ent, p, 3);
				((LivingEntity)ent).setVelocity(p.getLocation().toVector().subtract(ent.getLocation().toVector()).normalize().multiply(0.8));
				EffectUtils.createCircle(ent.getLocation(), 20*3,false);
				total+=3;
			}
		}
		double heal = total * precent / 100;
		if(heal > 8) {
			heal = 8;
		}
		p.playSound(p.getLocation(), Sound.SPLASH, 5, 5);
		for(Entity ent : p.getNearbyEntities(10, 10, 10)) {
			if(ent == p) continue;
			if(ent instanceof Player) {
				Player enp = (Player)ent;
				enp.playSound(p.getLocation(), Sound.SPLASH, 5, 5);
			}
		}
		p.setHealth(p.getHealth() + heal >= p.getMaxHealth() ? p.getMaxHealth() : p.getHealth() + heal);
		p.setLevel(0);
		p.setExp(0);
	}

}
