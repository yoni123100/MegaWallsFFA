package me.main.yoni.Listeners;

import me.main.yoni.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class onMove implements Listener {
	
	@EventHandler
	public void food(FoodLevelChangeEvent e) {
		if(!Main.playing.contains(e.getEntity().getName())) {
			e.setFoodLevel(20);
			e.setCancelled(true);
		}
	}

}
