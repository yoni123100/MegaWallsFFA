package me.main.yoni.ClassSystem.Abilities;

import java.util.HashMap;

import me.main.yoni.Main;
import me.main.yoni.API.ParticleEffect;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class ExplosiveArrow {
	
	public static HashMap<Arrow, Player> exArrow = new HashMap<>();

	public static void use(final Player p, final int upgrade) {
		final Arrow a = p.launchProjectile(Arrow.class);
		a.setVelocity(p.getEyeLocation().getDirection().multiply(2.5));
		a.setShooter(p);
		exArrow.put(a, p);
		new BukkitRunnable() {
			public void run() {
				if(a.isDead() || a == null){ cancel(); return;}
				if(a.isOnGround() || a.isInsideVehicle() || a.isCritical() || a.isDead()) {
					cancel();
					return;
				}
				ParticleEffect.CRIT_MAGIC.display(0,  0,  0,  15,  50,  a.getLocation(),  1000);
			}
		}.runTaskTimer(Main.getPlugin(), 0, 1);
	}
	
}
