package me.main.yoni.Commands.cmds;

import me.main.yoni.Main;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;


@Command(name = "lockchat", usage = "/megawallsffa lockchat", playerOnly = false, permission = "megawallsffa.command.lockchat")
public class CommandLockChat implements ZCommand {

	@Override
	public void onCommand(CmdArgs cmdArgs) {
		if(!Main.getChatLock()) {
			Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Alert " + ChatColor.RESET + ChatColor.DARK_GRAY + "» " + ChatColor.RED + "Server chat has been disabled by " + cmdArgs.getSender().getName() + ".");
			cmdArgs.getSender().sendMessage(ChatColor.YELLOW + "You locked the chat.");
			Main.setChatLock(true);
		} else {
			Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Alert " + ChatColor.RESET + ChatColor.DARK_GRAY + "» " + ChatColor.GREEN + "Server chat has been enabled by " + cmdArgs.getSender().getName() + ".");
			cmdArgs.getSender().sendMessage(ChatColor.YELLOW + "You unlocked the chat.");
			Main.setChatLock(false);
		}
		return;
	}
}
