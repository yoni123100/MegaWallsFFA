package me.main.yoni.ClassSystem.Abilities;

import me.main.yoni.Main;
import me.main.yoni.API.ParticleEffect;
import me.main.yoni.API.Utils;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.TeamManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Detonate {
	
	public static void use(final Player p, final int upgrade) {
		new BukkitRunnable() {
			
			int i = 4;
			double damage = 1.25;
			@Override
			public void run() {
				if(!Main.playing.contains(p.getName())) {
					cancel();
					return;
				}
				p.playSound(p.getLocation(), Sound.CREEPER_HISS, 5, 5);
				for(Entity ps : p.getNearbyEntities(10, 10, 10)) {
					if(ps == p) continue;
					if(ps instanceof Player) {
						((Player) ps).playSound(p.getLocation(), Sound.CREEPER_HISS, 5, 5);
					}
				}	
				i--;
				if(i == 0) {
					for(int i = 0; i < upgrade; i++) {
						damage += 0.75;
					}
					p.playSound(p.getLocation(), Sound.EXPLODE, 5, 5);
					for(Entity ps : p.getNearbyEntities(10, 10, 10)) {
						if(ps == p) continue;
						if(ps instanceof Player) {
							((Player) ps).playSound(p.getLocation(), Sound.EXPLODE, 5, 5);
						}
					}	
					for(Entity ent : p.getNearbyEntities(3, 3, 3)) {
						if(ent instanceof LivingEntity) {
							if(ent == p) continue;
							if(TeamManager.getTeamByPlayer(p) != null) {
								if(TeamManager.getTeamByPlayer(p).getPlayers().contains(ent)) {
									continue;
								}
							}
							if(ent instanceof Player) {
								Player enp = (Player)ent;
								MPlayer player = MPlayerManager.getMPlayer(enp.getName());
								if(player.getCurrentClass().getName().equalsIgnoreCase("Creeper")) {
									Utils.realDamage(ent, p, damage/3);
									continue;
								}
								if(player.getCurrentClass().getName().equalsIgnoreCase("Arcanist")) {
									Utils.realDamage(ent, p, damage/2);
									continue;
								}
							}
							Utils.realDamage(ent, p, damage);
						}
					}
					//Break some near blocks
					p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "BOOM!");
					cancel();
					return;
				}
				ParticleEffect.VILLAGER_ANGRY.display(0, 0, 0, 3, 1, p.getLocation(), 100);
				p.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You gonna explode in " + i + " seconds!");
			}
		}.runTaskTimer(Main.getPlugin(), 0, 20);
	}

}
