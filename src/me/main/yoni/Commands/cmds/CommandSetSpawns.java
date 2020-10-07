package me.main.yoni.Commands.cmds;

import me.main.yoni.Configuration;
import me.main.yoni.Main;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Command(name = "spawn", usage = "/megawallsffa spawn <id>", playerOnly = true, permission = "megawallzffa.command.spawn")
public class CommandSetSpawns implements ZCommand {
	
	@Override
	public void onCommand(CmdArgs cmdArgs) {
		Player p = (Player) cmdArgs.getSender();	
		if(cmdArgs.getArgs().length == 0) {
			p.sendMessage(Main.getPrefix()+"/megawallsffa spawn add/remove/list <id>");
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("set")) {
			p.sendMessage(ChatColor.GREEN + "You set the main FFA lobby!");
			Main.setFFALobby(p.getLocation());
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("add")) {
			Main.addLocation(p.getLocation());
			p.sendMessage(Main.getPrefix()+ChatColor.GREEN+"You added the " + Main.getLocations().size() + " id location!");
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("remove")) {
			int i = 0;
			try {
				i = Integer.valueOf(cmdArgs.getArg(1));
				if(i <= 0) {
					p.sendMessage(Main.getPrefix()+ChatColor.RED+"Id must be more than 0!");
					return;
				}
			} catch (Exception e) {
				p.sendMessage(Main.getPrefix()+ChatColor.RED+"Invaild Id!");
				return;
			}
			if(Main.getLocations().size() < i) {
				p.sendMessage(Main.getPrefix()+ChatColor.RED+"This spawn point does not exists!");
				return;
			}
			p.sendMessage(Main.getPrefix()+ChatColor.GREEN+"You remove the " + i + " id location!");
			Location loc = Configuration.convertToLocation(Main.getLocations().get(i-1));
			Main.removeLocation(loc);
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("list")) {
			p.sendMessage(Main.getPrefix()+ChatColor.GREEN+"Locations: ");
			int i = 1;
			for(String s : Main.getLocations()){
				p.sendMessage(Main.getPrefix()+"#"+i+": " + s);
				i++;
			}
			return;
		}
	}

}
