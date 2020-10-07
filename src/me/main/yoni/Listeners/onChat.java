package me.main.yoni.Listeners;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.main.yoni.Main;
import me.main.yoni.API.Timeformatter;
import me.main.yoni.Commands.cmds.CommandTeam;
import me.main.yoni.MuteSystem.MuteManager;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onChat implements Listener {
	
	@EventHandler
	public void youtube(EntityExplodeEvent e) {
		e.setCancelled(true);
 	}

	@EventHandler
	public void chat(final AsyncPlayerChatEvent e) {
		if(!e.getPlayer().hasPermission("megawallsffa.advertise") && MuteManager.getMute(e.getPlayer()) == null) {
				String message = ChatColor.stripColor(e.getMessage());
				Pattern pattern = Pattern.compile("(?i)(((([a-zA-Z0-9-]+\\.)+(de|eu|com|net|to|gs|me|co|info|biz|tv))|([0-9]{1,3}\\.){3}[0-9]{1,3})(\\:[0-9]{2,5})?)");
				 
				Matcher matcher = pattern.matcher(message);
				while (matcher.find()) {
				    e.setCancelled(true);
				    e.getPlayer().sendMessage(ChatColor.RED + "Next dont advertise links!");
				    for(Player ps : Bukkit.getOnlinePlayers()) {
				    	if(!(ps.isOnline())) continue;
				    	if(ps.hasPermission("megawallsffa.command.staffchat")) {
				    		ps.sendMessage(ChatColor.AQUA + "[STAFF] " + ChatColor.RED + e.getPlayer().getName() + ChatColor.YELLOW + " just tried to post a link!");
				    		ps.sendMessage(ChatColor.AQUA + "[STAFF] " + ChatColor.YELLOW + "His message: " + ChatColor.GREEN + e.getMessage());
				    	}
				    }
				    return;
				}
		}
		if(Main.getMainConfig().getBoolean("game") && Main.getRandomNumber() != 0) {
				int i = 0;
				try {
					i = Integer.valueOf(e.getMessage());
					if(i > 100 || i < 1) {
						e.getPlayer().sendMessage(ChatColor.RED + "Server §8» §cYou must type a number between 1 - 100!");
					}
					if(i < Main.getRandomNumber()) {
						e.getPlayer().sendMessage(ChatColor.RED + "Server §8» §cThe number you typed is lower than the random number, try again.");
					}
					if(i > Main.getRandomNumber()) {
						e.getPlayer().sendMessage(ChatColor.RED + "Server §8» §cThe number you typed is bigger than the random number, try again.");
					}
					if(i == Main.getRandomNumber()) {
						e.getPlayer().sendMessage(ChatColor.RED + "Server §8» §aYou've won! - You earned 50 coins for it!");
						Bukkit.broadcastMessage(ChatColor.RED + "Server §8» §a"+e.getPlayer().getName() + " has won the game!");
						Bukkit.broadcastMessage(ChatColor.RED + "Server §8» §a"+"The number was " + ChatColor.AQUA + Main.getRandomNumber() + "§a.");
						MPlayerManager.getMPlayer(e.getPlayer().getName()).addCoins(50);
						Main.setRandomNumber();
						Main.getMainConfig().set("game", false);
						Main.getPlugin().saveConfig();
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
							
							@Override
							public void run() {
								Main.updateScoreboard(e.getPlayer());
								
							}
						}, 10);
					}
					e.setCancelled(true);
				} catch (Exception e2) {

				}
		}
		if(Main.getChatLock()) {
			if(!e.getPlayer().hasPermission("megawallsffa.command.lockchat.speak")) {
				e.setCancelled(true);
				e.getPlayer().sendMessage(ChatColor.RED + "Chat is currently disabled!");
			}
			return;
		}
		if(MuteManager.getMute(e.getPlayer()) != null) {
			e.setCancelled(true);
			Player target = e.getPlayer();
			target.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have been muted for " + ChatColor.GRAY + "" + ChatColor.BOLD + Timeformatter.formatTime(MuteManager.getMute(e.getPlayer()).getTime()) + ChatColor.RED + "" + ChatColor.BOLD + " for §7§l"+MuteManager.getMute(e.getPlayer()).getReason());
			target.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Next time watch rules: " + ChatColor.AQUA + "" + ChatColor.BOLD + "www.MegaWallsFFA.com/rules");
			return;
		}
		if(CommandTeam.chat.contains(e.getPlayer().getName())) {
			if(TeamManager.getTeamByPlayer(e.getPlayer()) == null) {
				CommandTeam.chat.remove(e.getPlayer().getName());
				return;
			}
			e.setCancelled(true);
			for(Player ps : TeamManager.getTeamByPlayer(e.getPlayer()).getPlayers()) {
				ps.sendMessage(ChatColor.DARK_AQUA + "Team> " + e.getPlayer().getDisplayName() + ChatColor.WHITE + ": " + ChatColor.YELLOW + e.getMessage());
			}
		}
	}

}
