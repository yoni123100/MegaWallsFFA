package me.main.yoni.Commands.cmds;

import me.main.yoni.Main;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;

import org.bukkit.command.CommandSender;

@Command(name = "coins", usage = "/megawallsffa coins <add/remove/set> <player> <amount>", playerOnly = false, permission = "megawallsffa.command.coins")
public class CommandCoins implements ZCommand {

	@Override
	public void onCommand(CmdArgs cmdArgs) {
		CommandSender p = (CommandSender) cmdArgs.getSender();
		if(cmdArgs.getArgs().length == 0){
			p.sendMessage(Main.getPrefix()+"§c/megawallsffa coins <add/remove/set> <player> <amount>");
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("add")) {
			MPlayer target = MPlayerManager.getMPlayer(cmdArgs.getArg(1));
			if(target == null) {
				p.sendMessage(Main.getPrefix()+"§cThis player has never played on this server!");
				return;
			}
			int i = 0;
			try {
				i = Integer.valueOf(cmdArgs.getArg(2));
			} catch (NumberFormatException e) {
				p.sendMessage(Main.getPrefix()+"§cInvaid number!");
				return;
			}
			if(i < 1){
				p.sendMessage(Main.getPrefix()+"§cAmount cannot be negative.");
				return;
			}
			target.addCoins(i);
			p.sendMessage(Main.getPrefix()+"§7You've added §6" + i + " §7coins to §6" + target.getPlayer().getName() + " §7 account!");
			if(target.getPlayer().isOnline()) 
				target.getPlayer().getPlayer().sendMessage(Main.getPrefix()+"§7You recived §6" + i + " §7coins!");
			Main.updateScoreboard(target.getPlayer().getPlayer());
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("remove")) {
			MPlayer target = MPlayerManager.getMPlayer(cmdArgs.getArg(1));
			if(target == null) {
				p.sendMessage(Main.getPrefix()+"§cThis player has never played on this server!");
				return;
			}
			int i = 0;
			try {
				i = Integer.valueOf(cmdArgs.getArg(2));
			} catch (NumberFormatException e) {
				p.sendMessage(Main.getPrefix()+"§cInvaid number!");
				return;
			}
			if(i < 0){
				p.sendMessage(Main.getPrefix()+"§cAmount cannot be negative.");
				return;
			}
			target.removeCoins(i);
			p.sendMessage(Main.getPrefix()+"§7You've removed §6" + i + " §7coins from §6" + target.getPlayer().getName() + " §7 account!");
			Main.updateScoreboard(target.getPlayer().getPlayer());
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("set")) {
			MPlayer target = MPlayerManager.getMPlayer(cmdArgs.getArg(1));
			if(target == null) {
				p.sendMessage(Main.getPrefix()+"§cThis player has never played on this server!");
				return;
			}
			int i = 0;
			try {
				i = Integer.valueOf(cmdArgs.getArg(2));
			} catch (NumberFormatException e) {
				p.sendMessage(Main.getPrefix()+"§cInvaid number!");
				return;
			}
			if(i < 0){
				p.sendMessage(Main.getPrefix()+"§cAmount cannot be negative.");
				return;
			}
			target.setCoins(i);
			p.sendMessage(Main.getPrefix()+"§7You've set §6" + i + " §7coins to §6" + target.getPlayer().getName() + " §7 account!");
			Main.updateScoreboard(target.getPlayer().getPlayer());
			return;
		}
	}

}
