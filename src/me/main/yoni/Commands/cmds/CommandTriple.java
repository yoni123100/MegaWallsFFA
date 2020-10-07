package me.main.yoni.Commands.cmds;

import me.main.yoni.Main;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


@Command(name = "double", usage = "/megawallsffa double", playerOnly = true, permission = "megawallsffa.command.double")
public class CommandTriple implements ZCommand {

	@Override
	public void onCommand(CmdArgs cmdArgs) {
		Player p = (Player) cmdArgs.getSender();
		if(Main.isTripleCoins()) {
			p.sendMessage(ChatColor.RED + "You've disabled the Double coins on this server!");
			Main.Alert(ChatColor.RED + "Double Coins is now over!");
			Main.setTripleCoins(false);
			for(Player ps : Bukkit.getOnlinePlayers()) {
				Main.updateScoreboard(ps);
			}
			return;
		} else {
			Main.setTripleCoins(true);
			p.sendMessage(ChatColor.GREEN + "You've enabled the Double coins on this server!");
			Main.Alert(ChatColor.GREEN + "An Admin has activeted a Double Coins!");
			for(Player ps : Bukkit.getOnlinePlayers()) {
				Main.updateScoreboard(ps);
			}
		}
	}
}
