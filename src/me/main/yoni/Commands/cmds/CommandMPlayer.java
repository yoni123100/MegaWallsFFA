package me.main.yoni.Commands.cmds;

import java.util.ArrayList;

import me.main.yoni.Configuration;
import me.main.yoni.ClassSystem.ClassManager;
import me.main.yoni.ClassSystem.Upgrades.Upgrade;
import me.main.yoni.ClassSystem.Upgrades.UpgradeType;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.PlayerSystem.MPlayer;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command(name = "mplayer", usage = "/megawallsffa mplayer <Player>", playerOnly = false, permission = "megawallsffa.command.mplayer")
public class CommandMPlayer implements ZCommand {
	
	public static ArrayList<String> cd = new ArrayList<>();
	
	@Override
	public void onCommand(CmdArgs cmdArgs) {
		final CommandSender p = (CommandSender) cmdArgs.getSender();	
		if(cmdArgs.getArgs().length == 0) {
			p.sendMessage("Error: /mplayer <Player>");
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("fix") && cmdArgs.getArgs().length == 2) {
			Player target = Bukkit.getPlayer(cmdArgs.getArg(1));
			if(!(target.isOnline())) {
				p.sendMessage(ChatColor.RED + "This player must be online!");
				return;
			}
			if(Configuration.getConfig(target.getUniqueId().toString(), "PlayerData") == null) {
				p.sendMessage(ChatColor.RED + "PlayerDataSystem: I cant find this player file!!! ;(");
				return;
			}
			new Configuration(target.getUniqueId().toString(), "PlayerData");
			new Configuration(target.getUniqueId().toString(), "Layout");
			Configuration config = Configuration.getConfig(target.getUniqueId().toString(),"PlayerData");
			ArrayList<String> classes = new ArrayList<>();
			if(config.getStringList("classes") != null) {
				classes = (ArrayList<String>) config.getStringList("classes");
				if(!classes.contains(ClassManager.getClass("zombie").getName())) {
					classes.add(ClassManager.getClass("zombie").getName());
				}
			}
			config.set("stats."+"kills", config.getInt("stats."+"kills"));
			config.set("stats."+"deaths", config.getInt("stats."+"deaths"));
			config.set("stats."+"coins", config.getInt("stats."+"coins"));
			config.set("stats."+"wins", config.getInt("stats."+"wins"));
			config.set("uuid", target.getUniqueId().toString());
			config.set("ip", target.getAddress().getAddress().getHostAddress());
			config.set("classes", classes);
			for(UpgradeType type : UpgradeType.values()){
				for(me.main.yoni.ClassSystem.Class c : ClassManager.getClasses()){
					new Upgrade(target, c, type).reset();
					
					for(int i = 0; i<8;i++) {
						if(type == UpgradeType.BLOCKS || type == UpgradeType.TEAM) continue;
						new Upgrade((OfflinePlayer) p, c,type).upgrade();
					}
				}
			}
			config.saveConfig();
			MPlayerManager.mplayers.add(MPlayerManager.getmplayerByConfig(config.getName()));
			MPlayerManager.refresh(target);
			p.sendMessage(ChatColor.GREEN + "You fixed this player data! ("+target.getName()+")");
			return;
		}
		OfflinePlayer target = Bukkit.getOfflinePlayer(cmdArgs.getArg(0));
		if(MPlayerManager.getMPlayer(target.getUniqueId()) == null) {
			p.sendMessage("This player never played on our server!");
			return;
		}
		MPlayer player = MPlayerManager.getMPlayer(target.getUniqueId());
		p.sendMessage("Information about " + target.getName());
		p.sendMessage("UUID: " + player.getUUID());
		p.sendMessage("IP: " + player.getIp());
		p.sendMessage("Classes: " + player.getClasses());
		p.sendMessage("Current Class: "+player.getCurrentClass().getName());
		p.sendMessage("Kills: "+player.getKills());
		p.sendMessage("Deaths: "+player.getDeaths());
		p.sendMessage("Coins: "+player.getCoins());
		p.sendMessage("Wins: "+player.getWins());
		p.sendMessage("Network Boosters: "+player.getNetworkBoosters());
		if(player.getPlayer().isOnline()) {
			p.sendMessage("Status: " + ChatColor.GREEN + "ONLINE");
		} else {
			p.sendMessage("Status: " + ChatColor.RED + "OFFLINE");
		}
		if(!target.isOnline()) return;
		if(TeamManager.getTeamByPlayer((Player) target) != null) {
			p.sendMessage("Team: " + TeamManager.getTeamByPlayer((Player) target).getPlayers());
		} else {
			p.sendMessage("Team: None");
		}
		return;
	}

}
