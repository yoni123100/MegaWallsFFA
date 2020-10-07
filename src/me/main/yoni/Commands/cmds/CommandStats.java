package me.main.yoni.Commands.cmds;

import me.main.yoni.Main;
import me.main.yoni.API.Inventories;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Command(name = "stats", usage = "/megawallsffa stats <Player>", playerOnly = true, permission = "megawallsffa.command.stats")
public class CommandStats implements ZCommand {
	
	@Override
	public void onCommand(CmdArgs cmdArgs) {
		Player p = (Player) cmdArgs.getSender();	
		if(cmdArgs.getArgs().length == 0) {
			Inventories.openStats(p, p);
			return;
		}
		MPlayer target = MPlayerManager.getMPlayer(cmdArgs.getArg(0));
		if(target == null) {
			p.sendMessage(Main.getPrefix()+ChatColor.RED+"This player never played on this server!");
			return;
		}
		Inventories.openStats(p, target.getPlayer());
		return;
	}

}
