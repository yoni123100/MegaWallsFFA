package me.main.yoni.Commands;

import java.util.ArrayList;
import java.util.List;

import me.main.yoni.Main;
import me.main.yoni.Commands.cmds.CommandAlert;
import me.main.yoni.Commands.cmds.CommandArena;
import me.main.yoni.Commands.cmds.CommandBooster;
import me.main.yoni.Commands.cmds.CommandBoosters;
import me.main.yoni.Commands.cmds.CommandClearChat;
import me.main.yoni.Commands.cmds.CommandCoins;
import me.main.yoni.Commands.cmds.CommandDuel;
import me.main.yoni.Commands.cmds.CommandGame;
import me.main.yoni.Commands.cmds.CommandLobby;
import me.main.yoni.Commands.cmds.CommandLockChat;
import me.main.yoni.Commands.cmds.CommandMPlayer;
import me.main.yoni.Commands.cmds.CommandMsg;
import me.main.yoni.Commands.cmds.CommandMute;
import me.main.yoni.Commands.cmds.CommandMuteCheck;
import me.main.yoni.Commands.cmds.CommandOneonOne;
import me.main.yoni.Commands.cmds.CommandReply;
import me.main.yoni.Commands.cmds.CommandResetClass;
import me.main.yoni.Commands.cmds.CommandSetSpawns;
import me.main.yoni.Commands.cmds.CommandStaff;
import me.main.yoni.Commands.cmds.CommandStaffChat;
import me.main.yoni.Commands.cmds.CommandTeam;
import me.main.yoni.Commands.cmds.CommandTip;
import me.main.yoni.Commands.cmds.CommandTotem;
import me.main.yoni.Commands.cmds.CommandTriple;
import me.main.yoni.Commands.cmds.CommandUnmute;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandHandler implements CommandExecutor {

	private List<ZCommand> commands;
	private Main javaPlugin;

	public CommandHandler() {
		this.commands = new ArrayList<>();
		this.javaPlugin = Main.getPlugin();

		javaPlugin.getCommand("megawallsffa").setExecutor(this);
		registerCommand(new CommandStaffChat(), true);
		registerCommand(new me.main.yoni.Commands.cmds.CommandHelp(), true);
		registerCommand(new CommandMPlayer(), false);
		registerCommand(new CommandCoins(), false);
		registerCommand(new CommandBoosters(), true);
		registerCommand(new CommandStaff(), true);
		registerCommand(new CommandLockChat(), true);
		registerCommand(new CommandTriple(), false);
		registerCommand(new CommandGame(), false);
		registerCommand(new CommandTip(), true);
		registerCommand(new CommandOneonOne(), true);
		registerCommand(new CommandClearChat(), true);
		registerCommand(new CommandAlert(), true);
		registerCommand(new CommandMsg(), true);
		registerCommand(new CommandReply(), true);
		registerCommand(new CommandSetSpawns(), false);
		registerCommand(new CommandArena(), true);
		registerCommand(new me.main.yoni.Commands.cmds.CommandStats(), true);
		registerCommand(new CommandTeam(), true);
		registerCommand(new CommandResetClass(), true);
		registerCommand(new CommandLobby(), true);
		registerCommand(new CommandTotem(), false);
		registerCommand(new CommandDuel(), true);
		registerCommand(new CommandMute(), true);
		registerCommand(new CommandUnmute(), true);
		registerCommand(new CommandMuteCheck(), true);
		registerCommand(new CommandBooster(), false);
	}

	public List<ZCommand> getCommands() {
		return commands;
	}

	public void setCommands(List<ZCommand> commands) {
		this.commands = commands;
	}

	public Main getJavaPlugin() {
		return javaPlugin;
	}

	private void registerCommand(ZCommand cmd,boolean single){
		if(!commands.contains(cmd)) {
			commands.add(cmd);
			if(single){
				if (cmd.getClass().isAnnotationPresent(Command.class)) {
					Command command = cmd.getClass().getAnnotation(Command.class);
					javaPlugin.getCommand(command.name()).setExecutor(this);
				}
			}
		}
	}

	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String s, String[] args) {
		if(cmd.getName().equalsIgnoreCase("megawallsffa")){
			if(args.length > 0){
				String q = args[0];
				for(ZCommand c : commands){
					if(c.getClass().isAnnotationPresent(Command.class)) {
						Command cc = c.getClass().getAnnotation(Command.class);
						if(cc.name().equalsIgnoreCase(q)){
							String[] newArgs = new String[args.length - 1];
							int x = 0;
							for(int i = 1; i < args.length; i++){
								newArgs[x] = args[i];
								x++;
							}
							args = newArgs;
							if (!sender.hasPermission(cc.permission()) && !cc.permission().equals("")) {
								sender.sendMessage(cc.noPerm());
								return true;
							}
							if (args.length < cc.minArgs()) {
								sender.sendMessage(Main.getPrefix()+ChatColor.RED + "Usage: " + cc.usage());
								return true;
							}
							if (cc.playerOnly() && !(sender instanceof Player)) {
								sender.sendMessage(Main.getPrefix()+ChatColor.RED + "This is a player only command.");
								return true;
							}
							c.onCommand(new CmdArgs(sender,args));
							return true;
						}
					}
				}
			}
			else{
				for(ZCommand c : commands){
					if(c.getClass().isAnnotationPresent(Command.class)) {
						Command cc = c.getClass().getAnnotation(Command.class);
						if(cc.name().equalsIgnoreCase("help")){
							c.onCommand(new CmdArgs(sender,args));
							return true;
						}
					}
				}
			}
		}
		for(ZCommand pCmd : commands){
			if(pCmd.getClass().isAnnotationPresent(Command.class)){
				Command command = pCmd.getClass().getAnnotation(Command.class);
				if(command.name().equalsIgnoreCase(cmd.getName())) {
					if (!sender.hasPermission(command.permission()) && !command.permission().equals("")) {
						sender.sendMessage(Main.getPrefix()+command.noPerm());
						return true;
					}
					if (args.length < command.minArgs()) {
						sender.sendMessage(Main.getPrefix()+ChatColor.RED + "Usage: " + command.usage());
						return true;
					}
					if (command.playerOnly() && !(sender instanceof Player)) {
						sender.sendMessage(Main.getPrefix()+ChatColor.RED + "This is a player only command.");
						return true;
					}

					pCmd.onCommand(new CmdArgs(sender, args));
					return true;
				}
			}
		}
		return true;
	}
}
