package me.main.yoni.LeaderBoardSystem;

import java.util.ArrayList;
import java.util.Collections;

import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Sign;

public class LeaderBoard {
	
	private Sign sign;
	private int place;
	public enum TopType{
		KILLS,DEATHS,COINS,WINS;
	}
	private TopType type;
	
	public LeaderBoard(Sign s, TopType type, int place) {
		this.sign = s;
		this.type = type;
		this.place = place;
	}
	
	public int getAmountByPlayer(OfflinePlayer p) {
		if(type == TopType.KILLS) {
			ArrayList<String> used = new ArrayList<>();
			for(MPlayer player : MPlayerManager.getMPlayers()) {
				if(used.contains(player.getUUID())) continue;
				if(player.getPlayer().equals(p)) {
					used.add(player.getUUID());
					return player.getKills();
				}
			}
		}
		if(type == TopType.DEATHS) {
			ArrayList<String> used = new ArrayList<>();
			for(MPlayer player : MPlayerManager.getMPlayers()) {
				if(used.contains(player.getUUID())) continue;
				if(player.getPlayer().equals(p)) {
					used.add(player.getUUID());
					return player.getDeaths();
				}
			}
		}
		if(type == TopType.COINS) {
			ArrayList<String> used = new ArrayList<>();
			for(MPlayer player : MPlayerManager.getMPlayers()) {
				if(used.contains(player.getUUID())) continue;
				if(player.getPlayer().equals(p)) {
					used.add(player.getUUID());
					return player.getCoins();
				}
			}
		}
		if(type == TopType.WINS) {
			ArrayList<String> used = new ArrayList<>();
			for(MPlayer player : MPlayerManager.getMPlayers()) {
				if(used.contains(player.getUUID())) continue;
				if(player.getPlayer().equals(p)) {
					used.add(player.getUUID());
					return player.getWins();
				}
			}
		}
		return 0;
	}
	
	public String getPlayerName(int place) {
		if(type == TopType.KILLS) {
			String name = "";
			ArrayList<Integer> list = new ArrayList<>();
			ArrayList<String> used = new ArrayList<>();
			for(MPlayer player : MPlayerManager.getMPlayers()) {
				list.add(player.getKills());
			}
			Collections.sort(list);
			int count = list.size();
			int kills = 0;
			for(Integer i : list) {
				if(count == place) {
					kills = i;
					break;
				}
				count--;
			}
			for(MPlayer player : MPlayerManager.getMPlayers()) {
				if(player.getKills() == kills && !used.contains(player.getUUID())) {
					used.add(player.getUUID());
					name = player.getPlayer().getName();
					break;
				}
			}
			used.clear();
			return name;
		}
		if(type == TopType.DEATHS) {
			String name = "";
			ArrayList<Integer> list = new ArrayList<>();
			ArrayList<String> used = new ArrayList<>();
			for(MPlayer player : MPlayerManager.getMPlayers()) {
				list.add(player.getDeaths());
			}
			Collections.sort(list);
			int count = list.size();
			int deaths = 0;
			for(Integer i : list) {
				if(count == place) {
					deaths = i;
					break;
				}
				count--;
			}
			for(MPlayer player : MPlayerManager.getMPlayers()) {
				if(player.getDeaths() == deaths && !used.contains(player.getUUID())) {
					used.add(player.getUUID());
					name = player.getPlayer().getName();
					break;
				}
			}
			used.clear();
			return name;
		}
		if(type == TopType.COINS) {
			String name = "";
			ArrayList<Integer> list = new ArrayList<>();
			ArrayList<String> used = new ArrayList<>();
			for(MPlayer player : MPlayerManager.getMPlayers()) {
				list.add(player.getCoins());
			}
			Collections.sort(list);
			int count = list.size();
			int coins = 0;
			for(Integer i : list) {
				if(count == place) {
					coins = i;
					break;
				}
				count--;
			}
			for(MPlayer player : MPlayerManager.getMPlayers()) {
				if(player.getCoins() == coins && !used.contains(player.getUUID())) {
					used.add(player.getUUID());
					name = player.getPlayer().getName();
					break;
				}
			}
			used.clear();
			return name;
		}
		if(type == TopType.WINS) {
			String name = "";
			ArrayList<Integer> list = new ArrayList<>();
			ArrayList<String> used = new ArrayList<>();
			for(MPlayer player : MPlayerManager.getMPlayers()) {
				list.add(player.getWins());
			}
			Collections.sort(list);
			int count = list.size();
			int wins = 0;
			for(Integer i : list) {
				if(count == place) {
					wins = i;
					break;
				}
				count--;
			}
			for(MPlayer player : MPlayerManager.getMPlayers()) {
				if(player.getWins() == wins && !used.contains(player.getUUID())) {
					name = player.getPlayer().getName();
					used.add(player.getUUID());
					break;
				}
			}
			used.clear();
			return name;
		}
		return null;
	}
	
	public void update() {
		sign.setLine(0, "#"+place);
		if(getPlayerName(place) == null) {
			sign.setLine(1,ChatColor.RED + "No one"); //Player name
			sign.setLine(2, type.toString().toLowerCase());
			sign.setLine(3,"" + ChatColor.DARK_RED + 0);
			sign.update(true);
			return;
		}
		sign.setLine(1,ChatColor.DARK_BLUE + "" + ChatColor.BOLD + getPlayerName(place)); //Player name
		sign.setLine(2,ChatColor.DARK_GRAY + type.toString().toLowerCase());
		sign.setLine(3,"" + ChatColor.DARK_RED + "" + ChatColor.BOLD + getAmountByPlayer(Bukkit.getOfflinePlayer(ChatColor.stripColor(sign.getLine(1)))));
		sign.update(true);
	}

}
