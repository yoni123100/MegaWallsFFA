package me.main.yoni.Commands.cmds;

import me.main.yoni.API.Inventories;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@Command(name = "booster", usage = "/megawallsffa booster", playerOnly = false, permission = "megawallsffa.command.booster")
public class CommandBooster implements ZCommand {

	@Override
	public void onCommand(CmdArgs cmdArgs) {
		CommandSender p = (CommandSender) cmdArgs.getSender();
		if(cmdArgs.getArgs().length == 0){
			p.sendMessage(ChatColor.RED + "/mw booster add/remove/set/list (Player) (Amount) - Will add network boosters.");
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("list")) {
			Inventories.openBoosterInventory((Player) p);
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("add")) {
			MPlayer target = MPlayerManager.getMPlayer(cmdArgs.getArg(1));
			if(target == null) {
				p.sendMessage(ChatColor.RED + "Error: This target never played on this server!");
				return;
			}
			int i = 0;
			try {
				i = Integer.valueOf(cmdArgs.getArg(2));
			} catch (Exception e) {
				p.sendMessage(ChatColor.RED + "Error: Please type an amount!");
				return;
			}
			target.addNetworkBoosters(i);
			if(target.getPlayer().isOnline()) {
				target.getPlayer().getPlayer().sendMessage(ChatColor.YELLOW + "" + i + ChatColor.GREEN + " boosters have been added to your account!");
			}
			p.sendMessage(ChatColor.GREEN + "You've added " + i + " boosters to " + target.getPlayer().getName() + " account!");
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("remove")) {
			MPlayer target = MPlayerManager.getMPlayer(cmdArgs.getArg(1));
			if(target == null) {
				p.sendMessage(ChatColor.RED + "Error: This target never played on this server!");
				return;
			}
			int i = 0;
			try {
				i = Integer.valueOf(cmdArgs.getArg(2));
				if(i > target.getNetworkBoosters()) {
					i = target.getNetworkBoosters();
				}
			} catch (Exception e) {
				p.sendMessage(ChatColor.RED + "Error: Please type an amount!");
				return;
			}
			target.removeNetworkBoosters(i);
			if(target.getPlayer().isOnline()) {
				target.getPlayer().getPlayer().sendMessage(ChatColor.YELLOW + "" + i + ChatColor.GREEN + " boosters have been removed from your account!");
			}
			p.sendMessage(ChatColor.GREEN + "You've removed " + i + " boosters from " + target.getPlayer().getName() + " account!");
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("set")) {
			MPlayer target = MPlayerManager.getMPlayer(cmdArgs.getArg(1));
			if(target == null) {
				p.sendMessage(ChatColor.RED + "Error: This target never played on this server!");
				return;
			}
			int i = 0;
			try {
				i = Integer.valueOf(cmdArgs.getArg(2));
			} catch (Exception e) {
				p.sendMessage(ChatColor.RED + "Error: Please type an amount!");
				return;
			}
			target.setNetworkBoosters(i);
			if(target.getPlayer().isOnline()) {
				target.getPlayer().getPlayer().sendMessage(ChatColor.YELLOW + "" + i + ChatColor.GREEN + " boosters have been set to your account!");
			}
			p.sendMessage(ChatColor.GREEN + "You've set " + i + " boosters to " + target.getPlayer().getName() + "!");
			return;
		}
	}
}
