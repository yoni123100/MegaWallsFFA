package me.main.yoni.API;

import me.main.yoni.Main;
import me.main.yoni.API.ParticleEffect.OrdinaryColor;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class EffectUtils {

	public static void createMovingCircle(final Location location,final int ticks){
		new BukkitRunnable() {
			float radius = 2;
			float grow = .05f;
			double radials = Math.PI / 16;
			int circles = 3;
			int helixes = 4;
			int step = 0;
			int i = 0;
			public void run() {
				for (int x = 0; x < circles; x++) {
					for (int i = 0; i < helixes; i++) {
						double angle = step * radials + (2 * Math.PI * i / helixes);
						Vector v = new Vector(Math.cos(angle) * radius, step * grow, Math.sin(angle) * radius);
						VectorUtils.rotateAroundAxisX(v, (location.getPitch() + 90) * MathUtils.degreesToRadians);
						VectorUtils.rotateAroundAxisY(v, -location.getYaw() * MathUtils.degreesToRadians);
						location.add(v);
						ParticleEffect.SNOW_SHOVEL.display(0, 0, 0, 0, 3, location, 100);
						location.subtract(v);
					}
					step++;
				}
				i++;
				if(i >= ticks) cancel();
			}
		}.runTaskTimer(Main.getPlugin(), 1, 1);
	}
	
	public static void createCircle(final Location location,final float r,final int ticks){
		new BukkitRunnable() {
			int i = 0;
			float radius = r;
			public void run() {
				int particles = 50;
				for (int i = 0; i < particles; i++) {
					double angle, x, z;
					angle = 2 * Math.PI * i / particles;
					x = Math.cos(angle) * radius;
					z = Math.sin(angle) * radius;
					location.add(x, 0, z);
					ParticleEffect.FLAME.display(0, 0, 0, 0, 3, location, 100);
					location.subtract(x, 0, z);
				}
				i++;
				if(i >= ticks) cancel();
			}
		}.runTaskTimer(Main.getPlugin(), 1, 1);
	}
	
	

	public static void createCircle(final Location location,final int ticks,final boolean potion){
		new BukkitRunnable() {
			int i = 0;
			float radius = 0f;
			public void run() {
				int particles = 50;
				if(radius < 1.5) {
					radius+=0.5;
				}
				OrdinaryColor color = new OrdinaryColor(Color.AQUA);
				if(potion) {
					color = new OrdinaryColor(Color.BLACK);
				}
				for (int i = 0; i < particles; i++) {
					double angle, x, z;
					angle = 2 * Math.PI * i / particles;
					x = Math.cos(angle) * radius;
					z = Math.sin(angle) * radius;
					location.add(x, 0, z);
					ParticleEffect.REDSTONE.display(color, location, 100);
					location.subtract(x, 0, z);
				}
				i++;
				if(i >= ticks) cancel();
			}
		}.runTaskTimer(Main.getPlugin(), 1, 1);
	}

	public static void createHelix(final Location location,final int radius,final int ticks){
		final int strands = 5;
		final int particles = 25;
		final float curve = 2;
		final double rotation = Math.PI / 4;
		new BukkitRunnable() {
			int i = 0;
			public void run(){
				for (int i = 1; i <= strands; i++) {
					for (int j = 1; j <= particles; j++) {
						float ratio = (float) j / particles;
						double angle = curve * ratio * 2 * Math.PI / strands + (2 * Math.PI * i / strands) + rotation;
						double x = Math.cos(angle) * ratio * radius;
						double z = Math.sin(angle) * ratio * radius;
						location.add(x, 0, z);
						ParticleEffect.FLAME.display(0, 0, 0, 0, 3, location, 100);
						location.subtract(x, 0, z);
					}
				}
				i++;
				if(i >= ticks) cancel();
			}
		}.runTaskTimer(Main.getPlugin(), 0, 1);
	}

	public static void createSphere(final Player p, final Location location,final double radius,final int ticks){
		new BukkitRunnable() {
			int i = 0;
			public void run(){	
				if(!Main.playing.contains(p.getName())) {
					cancel();
					return;
				}
		        for (int i = 0; i < 50; i++) {
		            Vector vector = RandomUtils.getRandomVector().multiply(radius);
		            location.add(vector);
		    		ParticleEffect.FLAME.display(0, 0, 0, 0, 1, location, 100);
		            location.subtract(vector);
		        }
				i++;
				if(i >= ticks) cancel();
			}
		}.runTaskTimer(Main.getPlugin(), 0, 1);
	}
}
