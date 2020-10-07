package me.main.yoni.Commands.cmds;

import java.text.DecimalFormat;
import java.util.ArrayList;

import me.main.yoni.Main;
import me.main.yoni.API.Utils;
import me.main.yoni.ArenaSystem.ArenaManager;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.Listeners.onDeath;
import me.main.yoni.Listeners.onInventoryClick;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Command(name = "lobby", usage = "/megawallsffa lobby <Player>", playerOnly = true, permission = "megawallsffa.command.lobby")
public class CommandLobby implements ZCommand {
	
	public static ArrayList<String> cd = new ArrayList<>();
	
	@Override
	public void onCommand(CmdArgs cmdArgs) {
		final Player p = (Player) cmdArgs.getSender();	
		if(onInventoryClick.spec.contains(p.getName())) {
			onInventoryClick.spec.remove(p.getName());
			p.sendMessage(ChatColor.RED + "You have left the spectator mode!");
			p.setGameMode(GameMode.ADVENTURE);
			Utils.giveItems(p);
			return;
		}
		if(Main.lobby.contains(p.getName())) {
			if(ArenaManager.getArenaByPlayer(p) != null) {
				p.sendMessage(ChatColor.RED + "You can't teleport to the lobby because you still in an arena!");
				return;
			}
			p.teleport(Main.getFFALobby());
			Main.lobby.remove(p.getName());
			if(onDeath.ks.containsKey(p)) {
				onDeath.ks.remove(p);
			}
			Utils.giveItems(p);
			p.sendMessage(ChatColor.RED + "You left the 1v1 lobby!");
		} else {
			if(Main.playing.contains(p.getName())) {
				if(cd.contains(p.getName())) {
					p.sendMessage(ChatColor.RED + "Be patient!");
					return;
				}
				p.sendMessage(ChatColor.GREEN + "Wait 5 seconds until you will get teleport!");
				cd.add(p.getName());
				new BukkitRunnable() {
					
					Location loc = p.getLocation();
					double i = 5;
					@Override
					public void run() {
						if(p.getLocation().getBlockX() != loc.getBlockX() || p.getLocation().getBlockZ() != loc.getBlockZ()) {
							p.sendMessage(ChatColor.RED + "Teleportation has been canceled! (You moved)");
							cd.remove(p.getName());
							cancel();
							return;
						}
						Utils.sendActionBar(p, ChatColor.GREEN + "Teleport in " + ChatColor.AQUA + new DecimalFormat("0.0").format(i) + ChatColor.GREEN + " seconds!");
						i-=0.1;
						if(i <= 0) {
							cd.remove(p.getName());
							p.sendMessage(ChatColor.GREEN + "You have been teleported to lobby!");
							p.teleport(Main.getFFALobby());
							Main.playing.remove(p.getName());
							if(onDeath.ks.containsKey(p)) {
								onDeath.ks.remove(p);
							}
							Utils.giveItems(p);
							cancel();
						}
					}
				}.runTaskTimer(Main.getPlugin(), 2, 2);
			} else {
				p.sendMessage(ChatColor.RED + "You already in the lobby!");
			}
			return;
		}
		return;
	}

}
