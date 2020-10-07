package me.main.yoni.API;


import java.lang.reflect.Method;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkEffectPlayer {

	private static Method world_getHandle = null;
	private static Method nms_world_broadcastEntityEffect = null;
	private static Method firework_getHandle = null;

	public static void playFirework(World world, Location loc, FireworkEffect fe) throws Exception {
		Firework fw = (Firework) world.spawn(loc, Firework.class);
		Object nms_world = null;
		Object nms_firework = null;
		if(world_getHandle == null) {
			world_getHandle = getMethod(world.getClass(), "getHandle");
			firework_getHandle = getMethod(fw.getClass(), "getHandle");
		}
		nms_world = world_getHandle.invoke(world, (Object[]) null);
		nms_firework = firework_getHandle.invoke(fw, (Object[]) null);
		if(nms_world_broadcastEntityEffect == null) {
			nms_world_broadcastEntityEffect = getMethod(nms_world.getClass(), "broadcastEntityEffect");
		}
		FireworkMeta data = (FireworkMeta) fw.getFireworkMeta();
		data.clearEffects();
		data.setPower(1);
		data.addEffect(fe);
		fw.setFireworkMeta(data);
		nms_world_broadcastEntityEffect.invoke(nms_world, new Object[] {nms_firework, (byte) 17});
		fw.remove();
	}

	private static Method getMethod(Class<?> cl, String method) {
		for(Method m : cl.getMethods()) {
			if(m.getName().equals(method)) {
				return m;
			}
		}
		return null;
	}
}