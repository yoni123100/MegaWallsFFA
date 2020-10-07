package me.main.yoni.Commands.cmds;

import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.MuteSystem.MuteManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "unmute", usage = "/megawallsffa unmute <Player>", playerOnly = false, permission = "megawallsffa.command.mute")
public class CommandUnmute implements ZCommand {
	
	@Override
	public void onCommand(CmdArgs args) {
		CommandSender sender = (CommandSender) args.getSender();
		if(args.getArgs().length < 1) {
			sender.sendMessage(MuteManager.getPrefix + ChatColor.RED+"/unmute <Player>");
			return;
		}
		OfflinePlayer target = Bukkit.getOfflinePlayer(args.getArg(0));
		if(MuteManager.getMute(target) == null) {
			sender.sendMessage(MuteManager.getPrefix + ChatColor.RED + target.getName() + " is not muted!");
			return;
		}
		MuteManager.getMute(target).unMute();
		MuteManager.Setup();
		sender.sendMessage(MuteManager.getPrefix + ChatColor.GREEN + "You have unmuted " + target.getName() + "!");
		if(target.isOnline()) {
			if(sender instanceof Player){
				target.getPlayer().sendMessage(MuteManager.getPrefix + ChatColor.GREEN + "You have been unmuted by " + ((Player) sender).getDisplayName()  + ChatColor.GREEN + "!");
			} else {
				target.getPlayer().sendMessage(MuteManager.getPrefix + ChatColor.GREEN + "You have been unmuted by " + sender.getName()  + ChatColor.GREEN + "!");
			}
		}
		for(Player online : Bukkit.getOnlinePlayers()) {
			if(online == sender) continue;
			if(online.hasPermission("megawallsffa.command.mute")) {
				online.sendMessage(MuteManager.getPrefix + ChatColor.GREEN + target.getName() + " has been unmuted by " + sender.getName() + "!");
			}
		}
		return;
	}

}
