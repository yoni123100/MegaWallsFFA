package me.main.yoni.Commands.cmds;

import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;


@Command(name = "clearchat", usage = "/megawallsffa clearchat", playerOnly = false, permission = "megawallsffa.command.clearchat")
public class CommandClearChat implements ZCommand {

	@Override
	public void onCommand(CmdArgs cmdArgs) {
		for(int i = 0; i<200; i++) {
			Bukkit.broadcastMessage("");
		}
		Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Alert " + ChatColor.RESET + ChatColor.DARK_GRAY + "» " + ChatColor.YELLOW + "Chat has been cleared.");
		cmdArgs.getSender().sendMessage(ChatColor.YELLOW + "You cleared the chat.");
		return;
	}
}
