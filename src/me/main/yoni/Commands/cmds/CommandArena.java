package me.main.yoni.Commands.cmds;

import java.util.ArrayList;

import me.main.yoni.ArenaSystem.Arena;
import me.main.yoni.ArenaSystem.ArenaManager;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Command(name = "arena", usage = "/megawallsffa arena", playerOnly = true, permission = "megawallsffa.command.arena")
public class CommandArena implements ZCommand {
	
	@Override
	public void onCommand(CmdArgs args) {
		Player sender = (Player) args.getSender();	
		if(args.getArgs().length == 0) {
			sender.sendMessage(ChatColor.RED + "Error: /arena <Create/Delete/Setpos1/Setpos2> <name>");
			return;
		}
		if(args.getArg(0).equalsIgnoreCase("list")) {
			sender.sendMessage("Arenas: ");
			int i = 1;
			for(Arena a : ArenaManager.getArenas()){
				sender.sendMessage("#"+i+" " + a.getName());
				i++;
			}
			return;
		}
		if(args.getArg(0).equalsIgnoreCase("setpos2")) {
			String name = args.getArg(1);
			if(ArenaManager.getArena(name) == null) {
				sender.sendMessage("Error: Arena doesn't exists!");
				return;
			}
			Player p = (Player)sender;
			ArenaManager.getArena(name).setPos2(p.getLocation());
			p.sendMessage("Pos2 has been set! for arena " + name);
			return;
		}
		if(args.getArg(0).equalsIgnoreCase("setpos1")) {
			String name = args.getArg(1);
			if(ArenaManager.getArena(name) == null) {
				sender.sendMessage("Error: Arena doesn't exists!");
				return;
			}
			Player p = (Player)sender;
			ArenaManager.getArena(name).setPos1(p.getLocation());
			p.sendMessage("Pos1 has been set! for arena " + name);
			return;
		}
		if(args.getArg(0).equalsIgnoreCase("create")) {
			String name = args.getArg(1);
			if(ArenaManager.getArena(name) != null) {
				sender.sendMessage("Error: Arena already exists!");
				return;
			}
			Player p = (Player)sender;
			Arena a = new Arena(name, p.getLocation(), p.getLocation().add(10, 0, 0), false, new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());
			ArenaManager.create(a);
			p.sendMessage("You created the " + a.getName() + " arena!");
			return;
		}
		if(args.getArg(0).equalsIgnoreCase("delete")) {
			String name = args.getArg(1);
			if(ArenaManager.getArena(name) == null) {
				sender.sendMessage("Error: Arena does not exists!");
				return;
			}
			Player p = (Player)sender;
			Arena a = ArenaManager.getArena(name);
			a.Destroy();
			p.sendMessage("You deleted the " + a.getName() + " arena!");
			return;
		}
	}

}
