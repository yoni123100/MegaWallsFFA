package me.main.yoni.API;

import java.util.ArrayList;
import java.util.Collections;

import me.main.yoni.Main;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class TopKills implements Listener {
	
	public static enum TopType{
		KILLS,DEATHS,COINS;
	}
	
	@EventHandler
	public void place(SignChangeEvent e) {
		if(e.getLine(0).equalsIgnoreCase("[TopDeaths]")) {
			int i = 0;
			try {
				i = Integer.valueOf(e.getLine(1));
			} catch (Exception e2) {
				e.getPlayer().sendMessage("Please type a number in the second line!");
				return;
			}
			e.setLine(0, "#" + i);
			e.setLine(2, ChatColor.DARK_GRAY + "deaths");
			Main.addSignLocaiton(e.getBlock().getLocation());
			return;
		}
		if(e.getLine(0).equalsIgnoreCase("[TopKills]")) {
			int i = 0;
			try {
				i = Integer.valueOf(e.getLine(1));
			} catch (Exception e2) {
				e.getPlayer().sendMessage("Please type a number in the second line!");
				return;
			}
			e.setLine(0, "#" + i);
			e.setLine(2, ChatColor.DARK_GRAY + "kills");
			Main.addSignLocaiton(e.getBlock().getLocation());
			return;
		}
		if(e.getLine(0).equalsIgnoreCase("[TopCoins]")) {
			int i = 0;
			try {
				i = Integer.valueOf(e.getLine(1));
			} catch (Exception e2) {
				e.getPlayer().sendMessage("Please type a number in the second line!");
				return;
			}
			e.setLine(0, "#" + i);
			e.setLine(2, ChatColor.DARK_GRAY + "coins");
			Main.addSignLocaiton(e.getBlock().getLocation());
			return;
		}
		if(e.getLine(0).equalsIgnoreCase("[TopWins]")) {
			int i = 0;
			try {
				i = Integer.valueOf(e.getLine(1));
			} catch (Exception e2) {
				e.getPlayer().sendMessage("Please type a number in the second line!");
				return;
			}
			e.setLine(0, "#" + i);
			e.setLine(2, ChatColor.DARK_GRAY + "wins");
			Main.addSignLocaiton(e.getBlock().getLocation());
			return;
		}
	}
	
	public static int getNumberByPlayer(OfflinePlayer player, TopType type, int i) {
		if(getTopPlayerByNumber(i, type) == null) {
			return 0;
		}
		MPlayer p = MPlayerManager.getMPlayer(getTopPlayerByNumber(i, type).getName());
		if(type.equals(TopType.COINS)) {
			 return p.getCoins();
		}
		if(type.equals(TopType.KILLS)) {
			return p.getKills();
		}
		if(type.equals(TopType.DEATHS)) {
			return p.getDeaths();
		}
		return 0;
	}
	
	public static OfflinePlayer getTopPlayerByNumber(int number, TopType type) {
		ArrayList<Integer> list = new ArrayList<>();
		for(MPlayer m : MPlayerManager.getMPlayers()) {
			if(type.equals(TopType.COINS)) {
				list.add(m.getCoins());
			}
			if(type.equals(TopType.KILLS)) {
				list.add(m.getKills());
			}
			if(type.equals(TopType.DEATHS)) {
				list.add(m.getDeaths());
			}
		}
		Collections.sort(list);
		int i = list.size() + 1;
		ArrayList<MPlayer> used = new ArrayList<>();
		for(Integer num : list) {
			i--;
			for(MPlayer m : MPlayerManager.getMPlayers()) {
				if(type == TopType.DEATHS) {
					if(m.getDeaths() == num && !used.contains(m)) {
						used.add(m);
						if(number == i) {
							return m.getPlayer();
						}
						break;
					}	
				}
				if(type == TopType.KILLS) {
					if(m.getKills() == num && !used.contains(m)) {
						used.add(m);
						if(number == i) {
							return m.getPlayer();
						}
						break;
					}	
				}
				if(m.getCoins() == num && !used.contains(m)) {
					used.add(m);
					if(number == i) {
						return m.getPlayer();
					}
					break;
				}
			}
		}
		return null;
	}

}
