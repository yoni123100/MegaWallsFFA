package me.main.yoni.PlayerSystem;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MPlayerCommad implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String tag,
			String[] args) {
		if(tag.equalsIgnoreCase("mplayer") || tag.equalsIgnoreCase("mp")) {
			Player ps = (Player)sender;
			if(!sender.isOp()) return true;
			if(args.length == 0) {
				sender.sendMessage("/MPlayer <Player> - Check if exists.");
				sender.sendMessage("/MPlayer list - Prints all the players.");
				return true;
			}
			if(args[0].equalsIgnoreCase("list")) {
				sender.sendMessage("MPlayers: ");
				for(MPlayer yps : MPlayerManager.getMPlayers()) {
					sender.sendMessage(yps.getPlayer().isOnline()?"- "+yps.getPlayer().getName() + " - §aONLINE":"- "+yps.getPlayer().getName() + " - §cOFFLINE");
				}
				sender.sendMessage("To sum all, " + "there is " + MPlayerManager.getMPlayers().size() + " MPlayers.");
				return true;
			}
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			if(MPlayerManager.getMPlayer(target.getName()) == null) {
				sender.sendMessage("This MPlayer doesn't exists!");
				return true;
			}
			MPlayer p = MPlayerManager.getMPlayer(target.getName());
			sender.sendMessage("Kills: "+p.getKills());
			sender.sendMessage("Deaths: "+p.getDeaths());
			sender.sendMessage("Coins: "+p.getCoins());
			sender.sendMessage("Classes that he has: "+ p.getClasses());
			sender.sendMessage(target.isOnline()?target.getName() + " - Status - §aONLINE":target.getName() + " - Status - §cOFFLINE");
			return true;
		}
		return false;
	}
}
