package me.main.yoni.Commands.cmds;

import me.main.yoni.Main;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


@Command(name = "alert", usage = "/megawallsffa alert <Message>", playerOnly = false, permission = "megawallsffa.command.alert")
public class CommandAlert implements ZCommand {

	@Override
	public void onCommand(CmdArgs cmdArgs) {
		CommandSender p = (CommandSender) cmdArgs.getSender();
		if(cmdArgs.getArgs().length == 0){
			p.sendMessage(ChatColor.RED + "/alert <Message>");
			return;
		}
		String msg = "";
		for(int i = 0; i<cmdArgs.getArgs().length; i++) {
			msg += cmdArgs.getArg(i) + " ";
		}
		Main.Alert(msg);
		return;
	}
}
