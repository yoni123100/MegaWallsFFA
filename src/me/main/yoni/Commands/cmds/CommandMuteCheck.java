package me.main.yoni.Commands.cmds;

import me.main.yoni.API.Timeformatter;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.MuteSystem.MuteManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

@Command(name = "mcheck", usage = "/megawallsffa mcheck <Player>", playerOnly = false, permission = "megawallsffa.command.mute")
public class CommandMuteCheck implements ZCommand {
	
	@Override
	public void onCommand(CmdArgs args) {
		CommandSender sender = (CommandSender) args.getSender();
		if(args.getArgs().length < 1) {
			sender.sendMessage(MuteManager.getPrefix + ChatColor.RED+"/mcheck <Player>");
			return;
		}
		OfflinePlayer target = Bukkit.getOfflinePlayer(args.getArg(0));
		if(MuteManager.getMute(target) == null) {
			sender.sendMessage(MuteManager.getPrefix + ChatColor.RED + target.getName() + " is not muted!");
			return;
		}
		sender.sendMessage(ChatColor.GRAY + "------******------");
		sender.sendMessage(MuteManager.getPrefix + ChatColor.RED + target.getName() + " is muted!");
		sender.sendMessage(MuteManager.getPrefix + ChatColor.RED + "By: " + MuteManager.getMute(target).getBy());
		sender.sendMessage(MuteManager.getPrefix + ChatColor.RED + "" + ChatColor.BOLD + "Reason: " + MuteManager.getMute(target).getReason());
		sender.sendMessage(MuteManager.getPrefix + ChatColor.RED + "Time left: " + Timeformatter.formatTime(MuteManager.getMute(target).getTime()));
		sender.sendMessage(ChatColor.GRAY + "------******------");
		return;
	}

}
