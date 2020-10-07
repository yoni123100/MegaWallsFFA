package me.main.yoni.Listeners;

import me.main.yoni.Main;
import me.main.yoni.API.Utils;
import me.main.yoni.ArenaSystem.ArenaManager;
import me.main.yoni.ClassSystem.ClassManager;
import me.main.yoni.ClassSystem.HitType;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.Team;
import me.main.yoni.TeamSystem.TeamManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class onDamage implements Listener {
	
	@EventHandler
	public void fire(ProjectileLaunchEvent e) {
		if(e.getEntity() instanceof Fireball) {
			e.getEntity().setFireTicks(0);
		}
	}
	
	@EventHandler
	public void onSelfDamage(EntityDamageByEntityEvent e) {
		if(e.isCancelled()) return;
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Projectile) {
			Player p = (Player)e.getEntity();
			Projectile proj = (Projectile)e.getDamager();
			if(proj.getShooter() instanceof Player) {
				if(p == proj.getShooter()) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void damage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Projectile) {
			Player p = (Player)e.getEntity();
			Projectile proj = (Projectile)e.getDamager();
			if(proj.getShooter() instanceof Player) {
				Player shooter = (Player) proj.getShooter();
				if(TeamManager.getTeamByPlayer(shooter) != null) {
					if(TeamManager.getTeamByPlayer(shooter).getPlayers().contains(p)) {
						e.setCancelled(true);
						shooter.sendMessage(ChatColor.GRAY + "You can't hit " + p.getName() + "!");
					}
				}
				if(ArenaManager.getArenaByPlayer(shooter) != null || ArenaManager.getArenaByPlayer(p) != null) return;
				if(!Main.playing.contains(p.getName())) {
					e.setCancelled(true);
					shooter.sendMessage(ChatColor.RED + "This player didn't choose a Class!");
					return;
				} else if(!Main.playing.contains(shooter.getName())) {
					e.setCancelled(true);
					shooter.sendMessage(ChatColor.RED + "You didn't choose a Class!");
					return;
				}
			}
		}
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			Player p = (Player)e.getEntity();
			Player d = (Player)e.getDamager();
			p.setNoDamageTicks(10);
			if(TeamManager.getInstance().isInTeam(d) && TeamManager.getInstance().isInTeam(p)) {
				Team t = TeamManager.getInstance().getTeamByPlayer(d);
				Team t2 = TeamManager.getInstance().getTeamByPlayer(p);
				if(t == t2) {
					e.setCancelled(true);
					d.sendMessage(ChatColor.GRAY + "You can't hit " + p.getName() + "!");
				}
			}
			if(ArenaManager.getArenaByPlayer(d) != null || ArenaManager.getArenaByPlayer(p) != null) return;
			if(!Main.playing.contains(p.getName())) {
				e.setCancelled(true);
				d.sendMessage(ChatColor.RED + "This player didn't choose a Class!");
				return;
			} else if(!Main.playing.contains(d.getName())) {
				e.setCancelled(true);
				d.sendMessage(ChatColor.RED + "You didn't choose a Class!");
				return;
			}
		}
	}
	
	@EventHandler
	public void arrowDamage(final EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player && e.getDamager() instanceof Arrow) {
			final Arrow a = (Arrow)e.getDamager();
			if(a.getShooter() instanceof Player) {
				if(!Main.playing.contains(e.getEntity().getName())) return;
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					
					@Override
					public void run() {
						Player noob = (Player) e.getEntity();
						Player shooter = (Player)a.getShooter();
						if(!Main.playing.contains(noob.getName())) return;
						if(noob == shooter) return;
						int health = (int) noob.getHealth();
						float hp = health / (float) 2;
						Utils.sendActionBar(shooter, "§6"+noob.getName() + " §3is now at §6"+hp+"§c❤");
						
					}
				}, 1);
			}
		}
	}

	@EventHandler
	public void xp(EntityDamageByEntityEvent e) {
		if(e.isCancelled()) return;
		if(e.getDamager() instanceof Arrow && e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			Arrow a = (Arrow)e.getDamager();
			if(a.getShooter() instanceof Player) {
				Player damager = (Player)a.getShooter();
				if(damager == p) return;
				if(!Main.playing.contains(p.getName()) || !Main.playing.contains(damager.getName())) return;
				if(TeamManager.getTeamByPlayer(damager) != null) {
					if(TeamManager.getTeamByPlayer(damager).getPlayers().contains(p)) {
						return;
					}
				}
				if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != null && MPlayerManager.getMPlayer(damager.getName()).getCurrentClass() != null) {
					if(ClassManager.getClass(MPlayerManager.getMPlayer(damager.getName()).getCurrentClass().getName()).getHitType() == HitType.TIMER) return;
					Utils.addLevel(damager, ClassManager.getClass(MPlayerManager.getMPlayer(damager.getName()).getCurrentClass().getName()).getXPPerHit());
				}
			}
		}
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			Player damager = (Player) e.getDamager();
			if(!Main.playing.contains(p.getName()) || !Main.playing.contains(damager.getName())) return;
			if(TeamManager.getTeamByPlayer(damager) != null) {
				if(TeamManager.getTeamByPlayer(damager).getPlayers().contains(p)) {
					return;
				}
			}
			if(MPlayerManager.getMPlayer(p.getName()).getCurrentClass() != null && MPlayerManager.getMPlayer(damager.getName()).getCurrentClass() != null) {
				if(damager.getLevel() >= 100) return;
				if(ClassManager.getClass(MPlayerManager.getMPlayer(damager.getName()).getCurrentClass().getName()).getHitType() == HitType.TIMER) return;
				if(ClassManager.getClass(MPlayerManager.getMPlayer(damager.getName()).getCurrentClass().getName()).getHitType() == HitType.PROJECTILE) return;
				Utils.addLevel(damager, ClassManager.getClass(MPlayerManager.getMPlayer(damager.getName()).getCurrentClass().getName()).getXPPerHit());
			}
		}
	}
}
