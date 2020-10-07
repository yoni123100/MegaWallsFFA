package me.main.yoni.Commands.cmds;

import java.util.ArrayList;

import me.main.yoni.Main;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@Command(name = "game", usage = "/megawallsffa game", playerOnly = true, permission = "megawallsffa.command.game")
public class CommandGame implements ZCommand {
	
	@Override
	public void onCommand(CmdArgs cmdArgs) {
		final Player p = (Player) cmdArgs.getSender();	
		if(Main.getMainConfig().getBoolean("game")) {
			p.sendMessage(ChatColor.RED + "Game already started!");
			return;
		}
		Main.getMainConfig().set("game", true);
		p.sendMessage(ChatColor.GREEN + "You started the game!");
		final ArrayList<String> list = new ArrayList<>();
		list.add(ChatColor.RED + "Server §8» §eHello guys :)");
		list.add(ChatColor.RED + "Server §8» §eLets play a game!");
		list.add(ChatColor.RED + "Server §8» §eI call this game 'Type it fast'.");
		list.add(ChatColor.RED + "Server §8» §eThe rules are simple.");
		list.add(ChatColor.RED + "Server §8» §eI will run a random number between 1 - 100");
		list.add(ChatColor.RED + "Server §8» §eAnd you need to guess it. Thats it.");
		list.add(ChatColor.RED + "Server §8» §eLets start.");
		new BukkitRunnable() {
			
			int i = 0;
			@Override
			public void run() {
				Bukkit.broadcastMessage(list.get(i));
				i++;
				if(i == list.size()) {
					Bukkit.broadcastMessage(ChatColor.RED + "Server §8» §eStart guessing!");
					Main.runRandomNumber();
					cancel();
				}
			}
		}.runTaskTimer(Main.getPlugin(), 0, 20*2);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				if(Main.getMainConfig().getBoolean("game") && Main.getRandomNumber() != 0) {
					Bukkit.broadcastMessage(ChatColor.RED + "Server §8» §eWell, no one guessed the number :(");
					Bukkit.broadcastMessage(ChatColor.RED + "Server §8» §eThe number was " + ChatColor.AQUA + Main.getRandomNumber());
					Main.setRandomNumber();
					Main.getMainConfig().set("game", false);
					Main.getPlugin().saveConfig();
				}
			}
		}, 20*60*5);
		return;
	}

}
