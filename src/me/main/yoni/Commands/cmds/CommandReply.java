package me.main.yoni.Commands.cmds;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.MuteSystem.MuteManager;
import me.main.yoni.PlayerSystem.MPlayerManager;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Command(name = "Reply", usage = "/megawallsffa Reply <Message>", playerOnly = true, permission = "megawallsffa.command.msg")
public class CommandReply implements ZCommand {
	
	@Override
	public void onCommand(CmdArgs cmdArgs) {
		final Player p = (Player) cmdArgs.getSender();	
		if(cmdArgs.getArgs().length == 0) {
			p.sendMessage(ChatColor.RED + "Error: /Reply <Message>");
			return;
		}
		if(!CommandMsg.msg.containsKey(p)) {
			p.sendMessage(ChatColor.RED + "Nobody sent you a message!");
			return;
		}
		if(!CommandMsg.msg.get(p).isOnline()) {
			p.sendMessage(ChatColor.RED + "This player is not online!");
			return;
		}
		if(!MPlayerManager.getMPlayer(CommandMsg.msg.get(p).getName()).getMSGToggle()) {
			if(!p.hasPermission("megawallsffa.command.tmsg")) {
				p.sendMessage(ChatColor.RED + "You cant send private messages to this player!");
				return;
			}
		}
		if(MuteManager.getMute(p) != null) {
			p.sendMessage(ChatColor.RED + "You cant send private messages to other players while muted!");
			return;
		}
 		String message = "";
		for(int i = 0;i<cmdArgs.getArgs().length;i++) {
			message += cmdArgs.getArg(i) + " ";
		}
		if(!p.hasPermission("megawallsffa.advertise")) {
			Pattern pattern = Pattern.compile("(?i)(((([a-zA-Z0-9-]+\\.)+(de|eu|com|net|to|gs|me|co|info|biz|tv))|([0-9]{1,3}\\.){3}[0-9]{1,3})(\\:[0-9]{2,5})?)");
			 
			Matcher matcher = pattern.matcher(message);
			while (matcher.find()) {
				p.sendMessage(ChatColor.RED + "Please dont advertise links!");
				return;
			}
		}
		p.sendMessage(ChatColor.LIGHT_PURPLE+"To " + CommandMsg.msg.get(p).getDisplayName() + ": " + ChatColor.GRAY + message);
		CommandMsg.msg.get(p).sendMessage(ChatColor.LIGHT_PURPLE+"From " + p.getDisplayName() + ": " + ChatColor.GRAY + message);
		CommandMsg.msg.put(CommandMsg.msg.get(p), p);
		return;
	}

}
