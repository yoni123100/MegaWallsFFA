package me.main.yoni.ClassSystem.Abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.main.yoni.Main;
import me.main.yoni.API.ParticleEffect;
import me.main.yoni.TeamSystem.TeamManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;


public class Teleport {
	
	public static HashMap<Player, Integer> cd = new HashMap<>();
	
    public static Entity getTarget(Player p, int blocks) {
        Entity target;
     List<Entity> nearbyE = p.getNearbyEntities(blocks,blocks, blocks);
     ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();

     for (Entity e : nearbyE) {
         if (e instanceof Player) {
        	 if(!e.getWorld().equals(p.getWorld())) continue;
        	 	if(!Main.playing.contains(e.getName())) continue;
				if(TeamManager.getTeamByPlayer(p) != null) {
					if(TeamManager.getTeamByPlayer(p).getPlayers().contains(e)) {
						continue;
					}
				}
             livingE.add((LivingEntity) e);
         }
     }

     target = null;
     BlockIterator bItr = new BlockIterator(p, blocks);
     Block block;
     Location loc;
     int bx, by, bz;
     double ex, ey, ez;
     // loop through player's line of sight
     while (bItr.hasNext()) {
             block = bItr.next();
             bx = block.getX();
             by = block.getY();
             bz = block.getZ();
                     // check for entities near this block in the line of sight
                     for (LivingEntity e : livingE) {
                             loc = e.getLocation();
                             ex = loc.getX();
                             ey = loc.getY();
                             ez = loc.getZ();
                             if ((bx-.75 <= ex && ex <= bx+1.75) && (bz-.75 <= ez && ez <= bz+1.75) && (by-1 <= ey && ey <= by+2.5)) {
                                     // entity is close enough, set target and stop
                                     target = e;
                                     break;
                             }
                     }
             }
             return target;
         }
	
	public static void use(final Player p, int upgrade) {
		if(cd.containsKey(p)) {
			p.sendMessage(ChatColor.RED + "Your teleport is still on cooldown for " + ChatColor.AQUA + cd.get(p) + ChatColor.RED + " seconds!");
			return;
		}
		Entity target;
		int damage = 10;
		for(int i = 1; i < upgrade; i++) {
			if(damage == 10) {
				damage++;
				continue;
			}
			damage += 2;
		}
		int speed = 0;
		if(upgrade < 4) {
			speed = 1;
		}
		if(upgrade < 9 && upgrade > 3) {
			speed = 2;
		}
		if(upgrade == 9) {
			speed = 3;
		}
		if(getTarget(p, damage) != null) {
			target = getTarget(p, 25);
			Location loc = target.getLocation();
			loc.setPitch(((LivingEntity) target).getEyeLocation().getPitch());
			loc.setYaw(((LivingEntity) target).getEyeLocation().getYaw());
			p.teleport(loc);
			p.setLevel(0); p.setExp(0);
			for(PotionEffect pe : p.getActivePotionEffects()) {
				if(pe.getType() == PotionEffectType.SPEED) {
					p.removePotionEffect(pe.getType());
				}
			}
			p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,20*5,speed-1));
			ParticleEffect.SPELL_WITCH.display(0, 0, 0, 1, 8, p.getLocation(), 50);
			cd.put(p, 5);
			new BukkitRunnable() {
				
				@Override
				public void run() {
					cd.put(p, cd.get(p) - 1);
					if(cd.get(p) <= 0) {
						cd.remove(p);
						cancel();
						return;
					}
				}
			}.runTaskTimer(Main.getPlugin(), 20, 20);
		}
	}

}
