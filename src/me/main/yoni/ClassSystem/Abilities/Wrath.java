package me.main.yoni.ClassSystem.Abilities;

import me.main.yoni.API.Utils;
import me.main.yoni.TeamSystem.TeamManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Wrath {
	
	public static void use(Player p, int upgrade) {
		double damage = -0.5;
		for(int i = 0; i < upgrade; i++) {
			damage += 0.5;
		}
		boolean found = false;
		for(Entity ent : p.getNearbyEntities(3, 3, 3)) {
			if(ent == p) continue;
			if(ent instanceof LivingEntity && !ent.isDead() && ent instanceof Player) {
				if(TeamManager.getTeamByPlayer(p) != null) {
					if(TeamManager.getTeamByPlayer(p).getPlayers().contains(ent)) {
						continue;
					}
				}
				ent.getWorld().strikeLightningEffect(ent.getLocation());
				Utils.realDamage(ent, p, damage);
				found = true;
			}
		}
		if(!found) {
			p.sendMessage(ChatColor.RED + "No player within range to target");
			return;
		}
		p.setLevel(0);
		p.setExp(0);
	}

}
