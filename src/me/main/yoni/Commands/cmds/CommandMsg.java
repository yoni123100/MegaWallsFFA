package me.main.yoni.Commands.cmds;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.MuteSystem.MuteManager;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Command(name = "msg", usage = "/megawallsffa msg <Player> <Message>", playerOnly = true, permission = "megawallsffa.command.msg")
public class CommandMsg implements ZCommand {
	
	public static HashMap<Player, Player> msg = new HashMap<>();
	
	@Override
	public void onCommand(CmdArgs cmdArgs) {
		final Player p = (Player) cmdArgs.getSender();	
		if(cmdArgs.getArgs().length == 0) {
			p.sendMessage(ChatColor.RED + "Error: /msg <Player> <Message>");
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("toggle")) {
			if(p.hasPermission("megawallsffa.command.tmsg")) {
				MPlayer player = MPlayerManager.getMPlayer(p.getName());
				if(player.getMSGToggle()) {
					player.setMSGToggle(false);
					p.sendMessage(ChatColor.RED + "Private messages toggle off!");
					p.sendMessage(ChatColor.RED + "Now only staff members can send you private messages.");
				} else {
					player.setMSGToggle(true);
					p.sendMessage(ChatColor.GREEN + "Private messages toggle on!");
					p.sendMessage(ChatColor.GREEN + "Now only everyone can send you private messages.");
				}
			} else {
				p.sendMessage(ChatColor.RED + "You cant use this command!");
			}
			return;
		}
		Player target = Bukkit.getPlayer(cmdArgs.getArg(0));
		if(target == null) {
			p.sendMessage(ChatColor.RED + "This player is not online!");
			return;
		}
		if(target == p) {
			p.sendMessage(ChatColor.RED + "You cant msg yourself!");
			return;
		}
		if(!(cmdArgs.getArgs().length > 1)) {
			p.sendMessage(ChatColor.RED + "Error: /msg <Player> <Message>");
			return;
		}
		if(!MPlayerManager.getMPlayer(target.getName()).getMSGToggle()) {
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
		for(int i = 1;i<cmdArgs.getArgs().length;i++) {
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
		msg.put(target,p);
		p.sendMessage(ChatColor.LIGHT_PURPLE+"To " + target.getDisplayName() + ": " + ChatColor.GRAY + message);
		target.sendMessage(ChatColor.LIGHT_PURPLE+"From " + p.getDisplayName() + ": " + ChatColor.GRAY + message);
		if(MuteManager.getMute(target) != null) {
			p.sendMessage(ChatColor.RED + "This player is muted! so he cant reply to you.");
		}
		return;
	}

}
