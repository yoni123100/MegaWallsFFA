package me.main.yoni.Commands.cmds;

import me.main.yoni.Main;
import me.main.yoni.API.Utils;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Command(name = "1v1", usage = "/megawallsffa 1v1", playerOnly = true, permission = "megawallsffa.command.1v1")
public class CommandOneonOne implements ZCommand {
	
	@Override
	public void onCommand(CmdArgs args) {
		Player p = (Player) args.getSender();	
		if(args.getArgs().length == 0) {
			if(Main.get1v1Lobby() == null) {
				p.sendMessage(ChatColor.RED + "There is no 1v1 lobby!");
				return;
			}
			if(Main.playing.contains(p.getName())) {
				p.sendMessage(ChatColor.RED + "You can't join the 1v1 lobby because you choose a class!");
				return;
			}
			if(Utils.cd.contains(p.getName())) {
				p.sendMessage(ChatColor.RED + "Spam Protection! (3 Seconds)");
				return;
			}
			if(Main.lobby.contains(p.getName())) {
				p.sendMessage(ChatColor.RED + "You left the 1v1 lobby!");
				Main.lobby.remove(p.getName());
				p.teleport(Main.getFFALobby());
				Utils.giveItems(p);
				return;
			}
			if(Main.getFFALobby() == null) {
				p.sendMessage(ChatColor.RED + "There is no FFA lobby so you cant spawn with your class!");
				return;
			}
			p.sendMessage(ChatColor.GOLD + "You were sent to the 1v1 lobby!");
			p.teleport(Main.get1v1Lobby());
			Main.lobby.add(p.getName());
			Utils.give1v1Items(p);
			return;
		}
		if(args.getArg(0).equalsIgnoreCase("setlobby")) {
			p.sendMessage("You set the 1v1 lobby!");
			Main.set1v1Lobby(p.getLocation());
			return;
		}
	}

}
