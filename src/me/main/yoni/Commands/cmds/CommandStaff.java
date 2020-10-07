package me.main.yoni.Commands.cmds;

import me.main.yoni.Main;
import me.main.yoni.API.Inventories;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.Listeners.onInventoryClick;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


@Command(name = "staff", usage = "/megawallsffa staff", playerOnly = true, permission = "megawallsffa.command.staff")
public class CommandStaff implements ZCommand {

	@Override
	public void onCommand(CmdArgs cmdArgs) {
		Player p = (Player) cmdArgs.getSender();
		if(Main.playing.contains(p.getName()) || Main.lobby.contains(p.getName())) {
			p.sendMessage(ChatColor.RED + "You must go to the main lobby to use this command!");
			return;
		}
		if(cmdArgs.getArgs().length == 0){
			Inventories.openSpecInventory(p);
			return;
		}
		Player target = Bukkit.getPlayer(cmdArgs.getArg(0));
		if(target == null) {
			p.sendMessage(ChatColor.RED + "Player not found!");
			return;
		}
		if(target == p) {
			p.sendMessage(ChatColor.RED + "You can't spectate yourself!");
			return;
		}
		onInventoryClick.specPlayer(p, target);
		return;
	}
}
