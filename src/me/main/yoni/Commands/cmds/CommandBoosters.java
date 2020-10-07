package me.main.yoni.Commands.cmds;

import me.main.yoni.API.Inventories;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;

import org.bukkit.entity.Player;


@Command(name = "boosters", usage = "/megawallsffa boosters", playerOnly = true, permission = "megawallsffa.command.boosters")
public class CommandBoosters implements ZCommand {

	@Override
	public void onCommand(CmdArgs cmdArgs) {
		Player p = (Player) cmdArgs.getSender();
		Inventories.openBoosterInventory(p);
	}
}
