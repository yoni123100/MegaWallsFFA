package me.main.yoni.Commands.cmds;

import me.main.yoni.Configuration;
import me.main.yoni.Main;
import me.main.yoni.API.Timeformatter;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.MuteSystem.Mute;
import me.main.yoni.MuteSystem.MuteManager;
import me.main.yoni.PlayerSystem.MPlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "mute", usage = "/megawallsffa mute <Player>", playerOnly = false, permission = "megawallsffa.command.mute")
public class CommandMute implements ZCommand {
	
	@Override
	public void onCommand(CmdArgs args) {
		final CommandSender p = (CommandSender) args.getSender();
		if(args.getArgs().length == 1) {
	 		if(args.getArg(0).equalsIgnoreCase("list")){
				if(MuteManager.getMutes().size() == 0) {
					p.sendMessage(ChatColor.RED + "There is no muted players!");
					return;
				}
				p.sendMessage(MuteManager.getPrefix + "Loading...");
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
					
					int i = 0;
					@Override
					public void run() {
						for(Mute m : MuteManager.getMutes()) {
							i++;
							p.sendMessage(MuteManager.getPrefix + "#"+i+ChatColor.RED + " " + m.getPlayer().getName());
						}
					}
				}, 20);
			}
	 		return;
		}
		if(args.getArgs().length < 4) {
			p.sendMessage(MuteManager.getPrefix + ChatColor.RED + "/mute <Player> <Time> <Time-Word> <Reason>");
			return;
		}
		OfflinePlayer target = Bukkit.getOfflinePlayer(args.getArg(0));
		if(MPlayerManager.getMPlayer(target.getName()) == null) {
			p.sendMessage(MuteManager.getPrefix + ChatColor.RED + "Are you sure there is a player called " + target.getName() + "?");
			return;
		}
		if(MuteManager.getMute(target) != null) {
			p.sendMessage(MuteManager.getPrefix + ChatColor.RED + target.getName() + " is already muted!");
			return;
		}
		int i = 0;
		try {
			i = Integer.valueOf(args.getArg(1));
		} catch (NumberFormatException e) {
			p.sendMessage(MuteManager.getPrefix + ChatColor.RED + "please type a number!");
		}
		String word = args.getArg(2);
		if(word.equalsIgnoreCase("s") || word.equalsIgnoreCase("seconds") || word.equalsIgnoreCase("second")) {
			i = i;
		}
		if(word.equalsIgnoreCase("m") || word.equalsIgnoreCase("minute") || word.equalsIgnoreCase("minutes")) {
			i = i * 60;
		}
		if(word.equalsIgnoreCase("h") || word.equalsIgnoreCase("hour") || word.equalsIgnoreCase("hours")) {
			i = i * 60 * 60;
		}
		if(word.equalsIgnoreCase("d") || word.equalsIgnoreCase("day") || word.equalsIgnoreCase("days")) {
			i = i * 60 * 60 * 24;
		}
		if(word.equalsIgnoreCase("w") || word.equalsIgnoreCase("week") || word.equalsIgnoreCase("weeks")) {
			i = i * 60 * 60 * 24 * 7;
		}
		if(word.equalsIgnoreCase("month") || word.equalsIgnoreCase("months")) {
			i = i * 60 * 60 * 24 * 30;
		}
		if(word.equalsIgnoreCase("y") || word.equalsIgnoreCase("year") || word.equalsIgnoreCase("years")) {
			i = i * 60 * 60 * 24 * 30 * 12;
		}
		if(!(word.equalsIgnoreCase("weeks") ||word.equalsIgnoreCase("week") || word.equalsIgnoreCase("w") ||word.equalsIgnoreCase("years") ||word.equalsIgnoreCase("year") ||word.equalsIgnoreCase("months") ||word.equalsIgnoreCase("month") ||word.equalsIgnoreCase("y") || word.equalsIgnoreCase("s") || word.equalsIgnoreCase("m") || word.equalsIgnoreCase("p") || word.equalsIgnoreCase("h") || word.equalsIgnoreCase("d")
				|| word.equalsIgnoreCase("seconds") || word.equalsIgnoreCase("second") || word.equalsIgnoreCase("minute") || word.equalsIgnoreCase("minutes")
				|| word.equalsIgnoreCase("hour") || word.equalsIgnoreCase("hours") || word.equalsIgnoreCase("day") || word.equalsIgnoreCase("days"))) {
			p.sendMessage("please typed time word!");
			return;
		}
		if(!target.isOnline()){
			p.sendMessage(ChatColor.RED + target.getName() + " must be online!");
			return;
		}
		if(target.getPlayer().hasPermission("megawallsffa.command.mute")) {
			p.sendMessage(ChatColor.RED + "You cant mute this player!");
			return;
		}
		StringBuilder sb = new StringBuilder();
		for(int j = 3; j < args.getArgs().length; j++) {
			if(j == args.getArgs().length - 1) {
				sb.append(args.getArg(j));
				break;
			}
			sb.append(args.getArg(j) + " ");
		}
		Mute m = new Mute(target,p.getName(), sb.toString(), i);
		m.Mute();
		MuteManager.Setup();
		if(target.isOnline()) {
			if(p instanceof Player) {
				target.getPlayer().sendMessage(MuteManager.getPrefix + ChatColor.RED + "You have been muted by " + ((Player) p).getDisplayName() + ChatColor.RED + "!");
			} else {
				target.getPlayer().sendMessage(MuteManager.getPrefix + ChatColor.RED + "You have been muted by " + p.getName() + ChatColor.RED + "!");
			}
			target.getPlayer().sendMessage(MuteManager.getPrefix + ChatColor.RED + "" + ChatColor.BOLD + "Reason: " + sb.toString());
			target.getPlayer().sendMessage(MuteManager.getPrefix + ChatColor.RED + "Time left: " + Timeformatter.formatTime(i));
		}
		p.sendMessage(MuteManager.getPrefix + ChatColor.RED + "You have muted " + target.getName() + "!");
		p.sendMessage(MuteManager.getPrefix + ChatColor.RED  + "Reason: " + sb.toString());
		p.sendMessage(MuteManager.getPrefix + ChatColor.RED + "Time left: " + Timeformatter.formatTime(i));
		for(Player online : Bukkit.getOnlinePlayers()) {
			if(online == p) continue;
			if(online.hasPermission("megawallsffa.command.mute")) {
				if(p instanceof Player) {
					online.sendMessage(MuteManager.getPrefix + ChatColor.RED + target.getName() + " has been muted by " + ((Player) p).getDisplayName() + ChatColor.RED + "!");
				} else {
					online.sendMessage(MuteManager.getPrefix + ChatColor.RED + target.getName() + " has been muted by " + p.getName() + ChatColor.RED + "!");
				}
				online.sendMessage(MuteManager.getPrefix + ChatColor.RED + "Time left: " + Timeformatter.formatTime(i));
			}
		}
		return;
	}

}
