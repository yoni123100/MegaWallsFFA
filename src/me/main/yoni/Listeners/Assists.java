package me.main.yoni.Listeners;

import java.util.ArrayList;
import java.util.HashMap;

import me.main.yoni.PlayerSystem.MPlayerManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Assists implements Listener {
	
	public static HashMap<Player, ArrayList<Player>> Added = new HashMap<>();
	public static HashMap<HashMap<Player, Player>, Integer> DoneTo = new HashMap<>();

//	@EventHandler
//	public static void death(PlayerDeathEvent e) {
//		if(Added.containsKey(e.getEntity())) {
//			for(Player ps : Added.get(e.getEntity())) {
//				if(!ps.isOnline()) continue;
//				if(ps.getUniqueId().equals(e.getEntity().getKiller().getUniqueId())){
//					continue;
//				}
//				int coins = Utils.getCoinsForKill(ps);
//	        	if(BoosterManager.getBoosters().size() > 0) {
//	        		coins = coins * 2;
//	        	}
//				if(Main.isTripleCoins()) {
//					coins = coins * 2;
//				}
//	        	if(BoosterManager.getBoosters().size() > 0) {
//	        		ps.sendMessage(ChatColor.AQUA +""+ChatColor.BOLD+BoosterManager.getBoosters().get(0).getPlayer().getName() + "'s Network Booster!");
//	        	}
//				ps.sendMessage("§7You received §6"+coins+" §7coins for assisting §a"+e.getEntity().getName());
//				MPlayerManager.getMPlayer(ps.getName()).addCoins(coins);
//				Main.updateScoreboard(ps);
//			}
//			Added.remove(e.getEntity());
//		}
//		for(Player ps : Bukkit.getOnlinePlayers()) {
//			HashMap<Player, Player> hash = new HashMap<>();
//			hash.put(ps, e.getEntity());
//			if(DoneTo.keySet().contains(hash)) {
//				DoneTo.remove(hash);
//			}
//			hash.clear();
//		}
//	}
	
	@EventHandler
	public static void test(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Arrow && e.getEntity() instanceof Player) {
			Player noob = (Player)e.getEntity();
			Arrow a = (Arrow)e.getDamager();
			if(a.getShooter() instanceof Player) {
				Player damager = (Player)a.getShooter();
				HashMap<Player, Player> hash = new HashMap<>();
				hash.put(damager, noob);
				if(DoneTo.containsKey(hash)) {
					DoneTo.put(hash, (int) (DoneTo.get(hash) + e.getDamage()));
				} else {
					DoneTo.put(hash, (int)e.getDamage());
				}
				if(DoneTo.get(hash) >= 20) {
					if(Added.containsKey(noob)) {
						ArrayList<Player> list = Added.get(noob);
						if(list.contains(damager)) return;
						list.add(damager);
						Added.put(noob, list);
					} else {
						ArrayList<Player> list = new ArrayList<>();
						list.add(damager);
						Added.put(noob, list);
					}
				}
			}
		}
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player noob = (Player)e.getEntity();
			Player damager = (Player)e.getDamager();
			HashMap<Player, Player> hash = new HashMap<>();
			hash.put(damager, noob);
			if(DoneTo.containsKey(hash)) {
				DoneTo.put(hash, (int) (DoneTo.get(hash) + e.getDamage()));
			} else {
				DoneTo.put(hash, (int)e.getDamage());
			}
			if(DoneTo.get(hash) >= 20) {
				if(Added.containsKey(noob)) {
					ArrayList<Player> list = Added.get(noob);
					if(list.contains(damager)) return;
					list.add(damager);
					Added.put(noob, list);
				} else {
					ArrayList<Player> list = new ArrayList<>();
					list.add(damager);
					Added.put(noob, list);
				}
			}
		}
	}


}
