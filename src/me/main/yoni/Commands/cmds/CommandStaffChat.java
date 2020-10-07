package me.main.yoni.Commands.cmds;

import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Command(name = "staffchat", usage = "/megawallsffa staffchat <Message>", playerOnly = true, permission = "megawallsffa.command.staffchat")
public class CommandStaffChat implements ZCommand {
	
	@Override
	public void onCommand(CmdArgs cmdArgs) {
		final Player p = (Player) cmdArgs.getSender();	
		if(cmdArgs.getArgs().length == 0) {
			p.sendMessage(ChatColor.RED + "Error: please use /sc <Message>");
			return;
		}
		String msg = "";
		for(int i = 0; i<cmdArgs.getArgs().length; i++) {
			msg += cmdArgs.getArg(i) + " ";
		}
		for(Player ps : Bukkit.getOnlinePlayers()) {
			if(ps.hasPermission("megawallsffa.command.staffchat")) {
				ps.sendMessage(ChatColor.AQUA + "[STAFF] " + ChatColor.RESET + p.getDisplayName() + ChatColor.DARK_GRAY + " » " + ChatColor.YELLOW +  msg);
			}
		}
		return;
	}

}
