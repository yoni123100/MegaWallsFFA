package me.main.yoni.Commands.cmds;

import me.main.yoni.Main;
import me.main.yoni.API.Utils;
import me.main.yoni.ClassSystem.Class;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.Listeners.onDeath;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Command(name = "resetclass", usage = "/megawallsffa resetclass <Player>", playerOnly = true, permission = "megawallsffa.command.resetclass")
public class CommandResetClass implements ZCommand {
	
	@Override
	public void onCommand(CmdArgs cmdArgs) {
		Player p = (Player) cmdArgs.getSender();	
		if(cmdArgs.getArgs().length == 0) {
			if(!Main.playing.contains(p.getName())) {
				p.sendMessage(Main.getPrefix()+ChatColor.RED + "You didn't choose a class!");
				return;
			}
			if(onDeath.ks.containsKey(p)) {
				onDeath.ks.remove(p);
			}
			Class.resetClass(p);
			return;
		}
		Player target = Bukkit.getPlayer(cmdArgs.getArg(0));
		if(target == null) {
			p.sendMessage(Main.getPrefix()+ChatColor.RED+"Player not online!");
			return;
		}
		if(!Main.playing.contains(target.getName())) {
			p.sendMessage(Main.getPrefix()+ChatColor.RED + "This player didn't choose a class!");
			return;
		}
		if(Utils.cd.contains(target.getName())) {
			p.sendMessage(Main.getPrefix()+ChatColor.RED+"Spam Protection! (3 Seconds)");
			return;
		}
		if(onDeath.ks.containsKey(target)) {
			onDeath.ks.remove(target);
		}
		Class.resetClass(target);
		target.sendMessage(ChatColor.YELLOW + "Your Class has been reset by a staff member!");
		return;
	}

}
