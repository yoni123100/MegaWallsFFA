package me.main.yoni.ClassSystem.Abilities;

import me.main.yoni.Main;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ImmolatingBurst {
	
	public static void shoot(final Player p) {
		new BukkitRunnable() {
			
			int i = 0;
			@Override
			public void run() {
				if(!Main.playing.contains(p.getName())) {
					cancel();
					return;
				}
				Fireball fb = p.launchProjectile(Fireball.class);
				fb.setShooter(p);
				fb.setDirection(p.getEyeLocation().getDirection());
				fb.setVelocity(p.getEyeLocation().getDirection().multiply(1.5));
				fb.setYield(0);
				fb.setFireTicks(0);
				i++;
				if(i == 3) {
					cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0, 10);
	}
	
	public static void use(final Player p, int upgrade) {
		new BukkitRunnable() {
			
			int i = 3;
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				p.playSound(p.getLocation(), Sound.BLAZE_BREATH, 5, 5);
				for(Entity ent : p.getNearbyEntities(15, 15, 15)) {
					if(ent == p) continue;
					if(ent instanceof Player) { 
						Player enp = (Player)ent;
						enp.playSound(p.getLocation(), Sound.BLAZE_BREATH, 5, 5);
					}
				}
				i--;
				if(i == 0) {
					shoot(p);
					cancel();
					return;
				}
				//Other particles
				p.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 5);
				for(Entity ent : p.getNearbyEntities(30, 30, 30)) {
					if(ent == p) continue;
					if(ent instanceof Player) { 
						Player enp = (Player)ent;
						enp.playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 5);
					}
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0, 20);
	}

}
