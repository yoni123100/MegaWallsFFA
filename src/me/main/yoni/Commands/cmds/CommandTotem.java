package me.main.yoni.Commands.cmds;

import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.TotemAPI.Totem;

import org.bukkit.entity.Player;

@Command(name = "totem", usage = "/megawallsffa totem", playerOnly = true, permission = "megawallsffa.command.totem")
public class CommandTotem implements ZCommand {
	
	@Override
	public void onCommand(CmdArgs cmdArgs) {
		Player p = (Player) cmdArgs.getSender();	
		Totem t = new Totem(p.getLocation());
		t.create();
		return;
	}

}
