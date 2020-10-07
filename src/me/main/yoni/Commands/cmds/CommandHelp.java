package me.main.yoni.Commands.cmds;

import me.main.yoni.Main;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;

import org.bukkit.entity.Player;


@Command(name = "help", usage = "/megawallsffa help", playerOnly = true, permission = "megawallsffa.command.help")
public class CommandHelp implements ZCommand {

	@Override
	public void onCommand(CmdArgs cmdArgs) {
		Player p = (Player) cmdArgs.getSender();
		if(cmdArgs.getArgs().length == 0){
			  for(ZCommand command : Main.getCommandHandler().getCommands()){
		            if(command.getClass().isAnnotationPresent(Command.class)){
		                Command cmd = command.getClass().getAnnotation(Command.class);
		                p.sendMessage(Main.getPrefix()+"§6/megawallsffa "+cmd.name());
		            }
		        }
		}
	}
}
