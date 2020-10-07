package me.main.yoni.Commands.cmds;

import java.util.ArrayList;

import me.main.yoni.Main;
import me.main.yoni.ArenaSystem.Arena;
import me.main.yoni.ArenaSystem.ArenaManager;
import me.main.yoni.ArenaSystem.Duel;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.Listeners.OneOnOneListener;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Command(name = "duel", usage = "/megawallsffa duel <Player>", playerOnly = true, permission = "megawallsffa.command.duel")
public class CommandDuel implements ZCommand {
	
	@Override
	public void onCommand(CmdArgs cmdArgs) {
		final Player p = (Player) cmdArgs.getSender();	
		if(cmdArgs.getArgs().length == 0) {
			if(!Main.lobby.contains(p.getName())) {
				p.sendMessage(ChatColor.RED+ "You're not in the 1v1 lobby!");
				p.sendMessage(ChatColor.RED + "You can do that by typing /1v1");
				return;
			}
			p.sendMessage(ChatColor.RED + "Error: /duel <Player>");
			return;
		}
		final Player target = Bukkit.getPlayer(cmdArgs.getArg(0));
		if(target==null) {
			p.sendMessage(ChatColor.RED + target.getName() + " is not online!");
			return;
		}
		if(target == p) {
			p.sendMessage(ChatColor.RED + "You can't invite yourself!");
			return;
		}
		if(!Main.lobby.contains(target.getName())) {
			p.sendMessage(ChatColor.RED+ "This player is not in the 1v1 lobby!");
			return;
		}
		Arena arena = null;
		for(Arena a : ArenaManager.getArenas()) {
			if(a == null || a.isFull()) continue;
			arena = a;
			break;
		}
		if(arena == null) {
			p.sendMessage(ChatColor.RED + "All the arenas are full!");
			return;
		}
		if(ArenaManager.getArenaByPlayer(p) != null) {
			p.sendMessage(ChatColor.RED + "Concentrate on your match!");
			return;
		}
		if(ArenaManager.getArenaByPlayer(target) != null) {
			p.sendMessage(ChatColor.RED + target.getName() + " is in a duel!");
			return;
		}
		if(TeamManager.getTeamByPlayer(p) != null) {
			if(TeamManager.getTeamByPlayer(p).getPlayers().contains(target)) {
				p.sendMessage(ChatColor.RED + "This player is in your team!");
				return;
			}
		}
		if(OneOnOneListener.accept.containsKey(p)) {
			if(OneOnOneListener.accept.get(p) == target) {
				p.sendMessage(ChatColor.RED + "You already invited this player!");
				return;
			}
		}
		if(OneOnOneListener.queue.contains(target)) {
			p.sendMessage(ChatColor.RED + "This player is in a Queue!");
			return;
		}
		if(OneOnOneListener.accept.containsKey(target)) {
			if(OneOnOneListener.accept.get(target) == p) {
				OneOnOneListener.accept.remove(target);	
				
				//I already check up there ^^^^ line 50
//				Arena arena = null;
//				for(Arena a : ArenaManager.getArenas()) {
//					if(!a.isFull()) {
//						arena = a;
//					}
//				}
//				if(arena == null) {
//					p.sendMessage(ChatColor.RED + "All the arenas are full!");
//					target.sendMessage(ChatColor.RED + "All the arenas are full!");
//					return;
//				}
				Duel.sendTheAcceptingGUI(p, target, new ArrayList<Player>(), new ArrayList<Player>(),arena);
				return;
			}
		}
		if(TeamManager.getTeamByPlayer(p) != null && TeamManager.getTeamByPlayer(p).getLeader() != p) {
			p.sendMessage(ChatColor.RED + "You can't 1v1 while you're not the team leader!");
			return;
		}
		if(TeamManager.getTeamByPlayer(target) != null && TeamManager.getTeamByPlayer(target).getLeader() != target) {
			p.sendMessage(ChatColor.RED + "You can't invite this player, because he is not the team leader!");
			return;
		}
		if(TeamManager.getTeamByPlayer(p)!=null) {
			for(Player ps : TeamManager.getTeamByPlayer(p).getPlayers()) {
				if(!Main.lobby.contains(ps.getName())) {
					p.sendMessage(ChatColor.RED + "You can't invite other players until all of your teammates will be in lobby.");
					return;
				}
			}
		}
		OneOnOneListener.accept.put(p, target);
		p.sendMessage(ChatColor.GREEN + "You invited " + target.getName() + "!");
		target.sendMessage(ChatColor.GREEN + p.getName() + " invited you to a duel!");
		if(TeamManager.getTeamByPlayer(p) != null) {
			ArrayList<Player> list = TeamManager.getTeamByPlayer(p).getPlayers();
			ArrayList<String> list2 = new ArrayList<>();
			for(Player ps : list) {
				list2.add(ps.getName());
			}
			target.sendMessage(ChatColor.YELLOW + "This player is in a Team with: " + ChatColor.BOLD + list2);
		}
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
			
			@Override
			public void run() {
				if(OneOnOneListener.accept.containsKey(p)) {
					if(OneOnOneListener.accept.get(p) == target) {
						OneOnOneListener.accept.remove(p);
						p.sendMessage(ChatColor.RED + "Your duel request to " + target.getName() + " canceled!");
						target.sendMessage(ChatColor.RED + "Your duel request from " + p.getName() + " canceled!");
					}
				}
			}
		}, 20 * 10);
		return;
	}

}
