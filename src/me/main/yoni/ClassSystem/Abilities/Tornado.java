package me.main.yoni.ClassSystem.Abilities;

import java.util.ArrayList;

import me.main.yoni.Main;
import me.main.yoni.API.ParticleEffect;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;


public class Tornado {
	
	private static ArrayList<Vector> createCircle(double y, double radius) {
		double amount = radius * 64;
		double inc = (2 * Math.PI) / amount;
		ArrayList<Vector> vecs = new ArrayList<Vector>();
		for (int i = 0; i < amount; i++) {
			double angle = i * inc;
			double x = radius * Math.cos(angle);
			double z = radius * Math.sin(angle);
			Vector v = new Vector(x, y, z);
			vecs.add(v);
		}
		return vecs;
	}
	
	public static void createTornado(Player player) {
		final Location loc = player.getLocation();
		new BukkitRunnable() {
			int i = 0;
			public void run() {
				Location l = loc.add(0, 0, 0);
				Location t = l.clone().add(0, 0, 0);
				double r = .30 * (2 * (2.35 / 5));
				for (double y = 0; y < 4; y += .375) {
					double fr = r * y;
					if (fr > 2) {
						fr = 2;
					}
					for (Vector v : createCircle(y, fr)) {
						ParticleEffect.SNOW_SHOVEL.display(0, 0, 0, 0, 1, t.add(v), 100);
						t.subtract(v);
					}
				}
				i++;
				if(i >= 20) cancel();
			}
		}.runTaskTimer(Main.getPlugin(), 4, 4);
	}
	
	public static void use(final Player p, final int upgrade) {
		final Location loc = p.getLocation();
        createTornado(p);
		new BukkitRunnable() {
			
			int i = 0;
			@Override
			public void run() {
				double damage = 5.5;
				for(int i = 0; i<upgrade; i++) {
					damage+=0.5;
				}
				for(Entity ent : BurningSoul.getNearbyEntites(loc, 4)) {
					if(ent instanceof LivingEntity) {
						if(ent == p) continue;
						if(TeamManager.getTeamByPlayer(p) != null) {
							if(TeamManager.getTeamByPlayer(p).getPlayers().contains(ent)) {
								continue;
							}
						}
						((LivingEntity)ent).damage(damage,p);
					}
				}
				i++;
				if(i >= 5) {
					cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0, 20);
	}

}
