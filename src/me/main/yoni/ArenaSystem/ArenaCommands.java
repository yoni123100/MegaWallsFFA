package me.main.yoni.ArenaSystem;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String tag,
			String[] args) {
		if(tag.equalsIgnoreCase("arena")) {
			if(args.length == 0) {
				sender.sendMessage("Error: /arena <Create/Delete/Setpos1/Setpos2> <name>");
				return true;
			}
			if(args[0].equalsIgnoreCase("setpos2")) {
				String name = args[1];
				if(ArenaManager.getArena(name) == null) {
					sender.sendMessage("Error: Arena doesn't exists!");
					return true;
				}
				Player p = (Player)sender;
				ArenaManager.getArena(name).setPos2(p.getLocation());
				p.sendMessage("Pos2 has been set! for arena " + name);
				return true;
			}
			if(args[0].equalsIgnoreCase("setpos1")) {
				String name = args[1];
				if(ArenaManager.getArena(name) == null) {
					sender.sendMessage("Error: Arena doesn't exists!");
					return true;
				}
				Player p = (Player)sender;
				ArenaManager.getArena(name).setPos1(p.getLocation());
				p.sendMessage("Pos1 has been set! for arena " + name);
				return true;
			}
			if(args[0].equalsIgnoreCase("create")) {
				String name = args[1];
				if(ArenaManager.getArena(name) != null) {
					sender.sendMessage("Error: Arena already exists!");
					return true;
				}
				Player p = (Player)sender;
				Arena a = new Arena(name, p.getLocation(), p.getLocation().add(10, 0, 0), false,new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());
				ArenaManager.create(a);
				p.sendMessage("You created the " + a.getName() + " arena!");
				return true;
			}
			if(args[0].equalsIgnoreCase("delete")) {
				String name = args[1];
				if(ArenaManager.getArena(name) == null) {
					sender.sendMessage("Error: Arena does not exists!");
					return true;
				}
				Player p = (Player)sender;
				Arena a = ArenaManager.getArena(name);
				a.Destroy();
				p.sendMessage("You deleted the " + a.getName() + " arena!");
				return true;
			}
		}
		return false;
	}
	
	

}
