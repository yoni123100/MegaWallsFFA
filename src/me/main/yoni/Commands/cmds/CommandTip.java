package me.main.yoni.Commands.cmds;

import me.main.yoni.Main;
import me.main.yoni.BoosterSystem.BoosterManager;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.entity.Player;


@Command(name = "tip", usage = "/megawallsffa tip", playerOnly = false, permission = "megawallsffa.command.tip")
public class CommandTip implements ZCommand {

	@Override
	public void onCommand(CmdArgs cmdArgs) {
		Player p = (Player) cmdArgs.getSender();
		if(cmdArgs.getArgs().length == 0) {
			if(BoosterManager.getBoosters().size() < 1) {
				p.sendMessage(ChatColor.RED + "No one activated a booster!");
				return;
			}
			MPlayer target = MPlayerManager.getMPlayer(BoosterManager.getBoosters().get(0).getPlayer().getName());
			if(target == null)return;
			if(BoosterManager.getBooster(target.getPlayer()) == null) {
				p.sendMessage(ChatColor.RED + "This player didnt activated a network booster!");
				return;
			}
			if(target.getPlayer().getUniqueId().equals(p.getUniqueId())) {
				p.sendMessage(ChatColor.RED + "You cant tip yourself!");
				return;
			}
			if(BoosterManager.getBooster(target.getPlayer()).getTipped().contains(p.getName())) {
				p.sendMessage(ChatColor.RED + "You already tipped this player!");
				return;
			}
			p.sendMessage(ChatColor.GREEN + "You sent a tip of 100 coins to " + target.getPlayer().getName()+"!");
			p.sendMessage(ChatColor.GREEN + "And you received 25 coins for being generous.");
			MPlayerManager.getMPlayer(p.getName()).addCoins(25);
			target.addCoins(100);
			Main.updateScoreboard(p);
			BoosterManager.getBooster(target.getPlayer()).addTip(p.getName());
			if(target.getPlayer().isOnline()) {
				target.getPlayer().getPlayer().sendMessage(ChatColor.YELLOW + p.getName()  + ChatColor.GREEN + " just sent you 100 coins at a tip.");
			}
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("list")) {
			if(BoosterManager.getBooster(p) == null) {
				p.sendMessage(ChatColor.RED + "You didn't activated a booster!");
				return;
			}
			p.sendMessage(ChatColor.YELLOW + "People that tipped you: ");
			int i = 0;
			for(String s : BoosterManager.getBooster(p).getTipped()) {
				i++;
				p.sendMessage("§a#§b"+i+"§a - §e"+s);
			}
			p.sendMessage(ChatColor.YELLOW + "To sum up, " + ChatColor.AQUA + i + ChatColor.YELLOW + " people tipped you.");
			return;
		}
	}
}
