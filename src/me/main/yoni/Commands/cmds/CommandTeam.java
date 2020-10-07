package me.main.yoni.Commands.cmds;

import java.util.ArrayList;
import java.util.HashMap;

import me.main.yoni.Main;
import me.main.yoni.Commands.CmdArgs;
import me.main.yoni.Commands.Command;
import me.main.yoni.Commands.ZCommand;
import me.main.yoni.Listeners.OneOnOneListener;
import me.main.yoni.PlayerSystem.MPlayerManager;
import me.main.yoni.TeamSystem.Team;
import me.main.yoni.TeamSystem.TeamManager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@Command(name = "team", usage = "/megawallsffa team", playerOnly = true, permission = "megawallsffa.command.team")
public class CommandTeam implements ZCommand {
	
	public static HashMap<Player, Player> accept = new HashMap<>();
	public static ArrayList<String> chat = new ArrayList<>();
	
	public void sendBar(Player p) {
		p.sendMessage("§8§m----------------------------");
	}
	
	@Override
	public void onCommand(CmdArgs cmdArgs) {
		final Player p = (Player) cmdArgs.getSender();	
		if(cmdArgs.getArgs().length == 0) {
			sendBar(p);
			p.sendMessage("/team info - Show's you the information about your team.");
			p.sendMessage("/team create - Creates you a team");
			p.sendMessage("/team disband - Disbands the team you created");
			p.sendMessage("/team leave - Leave your current Team");
			p.sendMessage("/team chat toggle/<message> - Talk with your team members");
			p.sendMessage("/team invite <Player> - Invite more players to your team");
			p.sendMessage("/team kick <Player> - Kick player from your team");
			p.sendMessage("/team accept <Player> - Accept Team invite from another player");
			sendBar(p);
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("chat")) {
			if(TeamManager.getTeamByPlayer(p) == null) {
				sendBar(p);
				p.sendMessage(ChatColor.RED+"You're not in a Team!");
				sendBar(p);
				return;
			}
			if(cmdArgs.getArgs().length == 1) {
				if(chat.contains(p.getName())) {
					sendBar(p);
					p.sendMessage(ChatColor.RED+"Team chat disabled!");
					sendBar(p);
					chat.remove(p.getName());
					return;
				} else {
					sendBar(p);
					p.sendMessage(ChatColor.GREEN+"Team chat enabled!");
					sendBar(p);
					chat.add(p.getName());
				}
				return;
			}
			String msg = "";
			for(int i = 1; i<cmdArgs.getArgs().length; i++) {
				msg += cmdArgs.getArg(i) + " ";
			}
			for(Player ps : TeamManager.getTeamByPlayer(p).getPlayers()) {
				ps.sendMessage(ChatColor.DARK_AQUA + "Team> " + p.getDisplayName() + ChatColor.WHITE + ": " + ChatColor.YELLOW + msg);
			}
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("accept")) {
			if(cmdArgs.getArgs().length < 2) {
				sendBar(p);
				p.sendMessage(ChatColor.RED+"Usage: /Team accept <Player>");
				sendBar(p);
				return;
			}
			if(TeamManager.isInTeam(p)) {
				sendBar(p);
				p.sendMessage(ChatColor.RED+"You're already in a Team!");
				sendBar(p);
				return;
			}
			Player target = Bukkit.getPlayer(cmdArgs.getArg(1));
			if(target == null) {
				sendBar(p);
				p.sendMessage(ChatColor.RED + "Player not online!");
				sendBar(p);
				return;
			}
			if(accept.get(target) == null) {
				sendBar(p);
				p.sendMessage(ChatColor.RED+"This player didn't invited you to his Team!");
				sendBar(p);
				return;
			}
			if(accept.containsKey(target)) {
				if(accept.get(target) != p) {
					sendBar(p);
					p.sendMessage(ChatColor.RED+"This player didn't invited you to his Team!");
					sendBar(p);
					return;
				}
			}
			Team m = TeamManager.getInstance().getTeamByPlayer(target);
			if(m == null) {
				sendBar(p);
				p.sendMessage(ChatColor.RED + "Team could not be found!");
				if(accept.containsKey(target)) {
					accept.remove(target);
				}
				sendBar(p);
				return;
			}
			if(OneOnOneListener.queue.contains(p)) {
				p.sendMessage(ChatColor.RED + "You can't accept team requests! (You're in a Queue!)");
				return;
			}
			sendBar(p);
			p.sendMessage(ChatColor.GREEN + "You have joined " + target.getName() + " Team!");
			sendBar(p);
			sendBar(target);
			target.sendMessage(ChatColor.GREEN + p.getName() + " has joined your Team!");
			sendBar(target);
			m.addPlayer(p);
			for(Player online : m.getPlayers()) {
				Main.updateScoreboard(online);
			}
			if(accept.containsKey(target)) {
				accept.remove(target);
			}
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("kick")) {
			if(cmdArgs.getArgs().length < 2) {
				sendBar(p);
				p.sendMessage(ChatColor.RED+"Usage: /Team kick <Player>");
				sendBar(p);
				return;
			}
			if(!TeamManager.isInTeam(p)) {
				sendBar(p);
				p.sendMessage(ChatColor.RED+"You're not in a Team!");
				sendBar(p);
				return;
			}
			Team m = TeamManager.getInstance().getTeamByPlayer(p);
			if(m.getLeader() != p) {
				sendBar(p);
				p.sendMessage(ChatColor.RED + "You're not the leader!");
				sendBar(p);
				return;
			}
			Player target = Bukkit.getPlayer(cmdArgs.getArg(1));
			if(target == null) {
				sendBar(p);
				p.sendMessage(ChatColor.RED + "Player not online!");
				sendBar(p);
				return;
			}
			if(TeamManager.getTeamByPlayer(target) != m) {
				sendBar(p);
				p.sendMessage(ChatColor.RED + "This player is not in your team!");
				sendBar(p);
				return;
			}
			if(m.getLeader() == target) {
				sendBar(p);
				p.sendMessage(ChatColor.RED + "You can't kick yourself!");
				sendBar(p);
				return;
			}
			sendBar(p);
			p.sendMessage(ChatColor.RED + "You kicked " + target.getName() + " from the Team!");
			sendBar(p);
			sendBar(target);
			target.sendMessage(ChatColor.RED + "You have been kicked from the Team!");
			sendBar(target);
			m.kickPlayer(target);
			Main.updateScoreboard(target);
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("invite")) {
			if(cmdArgs.getArgs().length < 2) {
				sendBar(p);
				p.sendMessage(ChatColor.RED+"Usage: /Team invite <Player>");
				sendBar(p);
				return;
			}
			if(!TeamManager.isInTeam(p)) {
				sendBar(p);
				p.sendMessage(ChatColor.RED+"You're not in a Team!");
				sendBar(p);
				return;
			}
			Team m = TeamManager.getInstance().getTeamByPlayer(p);
			if(m.getLeader() != p) {
				sendBar(p);
				p.sendMessage(ChatColor.RED + "You're not the leader!");
				sendBar(p);
				return;
			}
			final Player target = Bukkit.getPlayer(cmdArgs.getArg(1));
			if(target == null) {
				sendBar(p);
				p.sendMessage(ChatColor.RED + "Player not online!");
				sendBar(p);
				return;
			}
			if(TeamManager.getTeamByPlayer(target) != null) {
				sendBar(p);
				p.sendMessage(ChatColor.RED + "This player is already in a Team!");
				sendBar(p);
				return;
			}
			if(accept.containsKey(p)) {
				sendBar(p);
				p.sendMessage(ChatColor.RED + "You already invited a player to your team!!");
				sendBar(p);
				return;
			}
			if(MPlayerManager.getMPlayer(p.getName()).getTeamUpgrade() + 1 < TeamManager.getTeamByPlayer(p).getPlayers().size() + 1) {
				p.sendMessage(ChatColor.RED + "You must upgrade your Team Option to invite more players to your team!");
				return;
			}
			if(OneOnOneListener.queue.contains(target)) {
				p.sendMessage(ChatColor.RED + "You can't invite this player! (He's in a Queue!)");
				return;
			}
			accept.put(p, target);
			sendBar(target);
			target.sendMessage(ChatColor.GOLD + "Type /team accept " + p.getName() + " to join!");
			sendBar(target);
			sendBar(p);
			p.sendMessage(ChatColor.GOLD + "You invited " + target.getName() + " to your Team!");
			sendBar(p);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					if(accept.containsKey(p)) {
							sendBar(target);
							target.sendMessage(ChatColor.RED + "Your Team request from " + p.getName() + " has been canceled!");
							sendBar(target);
							sendBar(p);
							p.sendMessage(ChatColor.RED + "Your Team request to " + target.getName() + " has been canceled!");
							sendBar(p);
							if(accept.containsKey(p)) {
								accept.remove(p);
							}
						}
					}
			}, 20 * 15);
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("leave")) {
			if(!TeamManager.isInTeam(p)) {
				sendBar(p);
				p.sendMessage(ChatColor.RED+"You're not in a Team!");
				sendBar(p);
				return;
			}
			Team m = TeamManager.getInstance().getTeamByPlayer(p);
			if(m.getLeader() == p) {
				sendBar(p);
				p.sendMessage(ChatColor.RED + "You can't leave your own Team!");
				p.sendMessage(ChatColor.RED + "You can type /Team disband to disband it!");
				sendBar(p);
				return;
			}
			sendBar(p);
			p.sendMessage(ChatColor.RED + "You left the Team!");
			sendBar(p);
			m.removePlayer(p);
			Main.updateScoreboard(p);
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("info")) {
			if(!TeamManager.isInTeam(p)) {
				sendBar(p);
				p.sendMessage(ChatColor.RED+"You're not in a Team!");
				sendBar(p);
				return;
			}
			Team m = TeamManager.getInstance().getTeamByPlayer(p);
			sendBar(p);
			p.sendMessage("Leader: " + m.getLeader().getName());
			ArrayList<String> list = new ArrayList<>();
			for(Player ps : m.getPlayers()) {
				if(ps == m.getLeader()) continue;
				list.add(ps.getName());
			}
			p.sendMessage("Players: " + list);
			sendBar(p);
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("create")) {
			if(!MPlayerManager.getMPlayer(p.getName()).boughtTeam()) {
				p.sendMessage(ChatColor.RED + "You must buy the Team Option first!");
				return;
			}
			if(TeamManager.isInTeam(p)) {
				sendBar(p);
				p.sendMessage(ChatColor.RED+"You're already created a Team!");
				sendBar(p);
				return;
			}
			Team m = new Team(p,new ArrayList<Player>());
			TeamManager.create(m);
			sendBar(p);
			p.sendMessage(ChatColor.GREEN + "You created a Team!");
			p.sendMessage(ChatColor.GOLD + "Type /Team info for more info");
			sendBar(p);
			for(Player ps : Bukkit.getOnlinePlayers()) {
				Main.updateScoreboard(ps);
			}
			return;
		}
		if(cmdArgs.getArg(0).equalsIgnoreCase("disband")) {
			if(!TeamManager.isInTeam(p)) {
				sendBar(p);
				p.sendMessage(ChatColor.RED+"You're not in a Team!");
				sendBar(p);
				return;
			}
			Team m = TeamManager.getTeamByPlayer(p);
			if(m.getLeader() != p) {
				sendBar(p);
				p.sendMessage(ChatColor.RED + "You're not the leader of the Team!");
				sendBar(p);
				return;
			}
			sendBar(p);
			p.sendMessage(ChatColor.RED + "You disbanded the Team!");
			sendBar(p);
			m.destroy();
			for(Player online : Bukkit.getOnlinePlayers()) {
				Main.updateScoreboard(online);
			}
			return;
		}
		return;
	}

}
