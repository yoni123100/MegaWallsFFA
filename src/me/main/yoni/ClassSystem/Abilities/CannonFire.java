package me.main.yoni.ClassSystem.Abilities;

import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;


public class CannonFire {
	
	public static void use(Player p) {
		WitherSkull ws = p.launchProjectile(WitherSkull.class);
		ws.setShooter(p);
		ws.setVelocity(p.getEyeLocation().getDirection().multiply(1));
	}

}
