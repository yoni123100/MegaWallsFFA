package me.main.yoni.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdArgs {

	public CmdArgs(CommandSender sender2, String[] args2) {
		setSender(sender2);
		setArgs(args2);
	}

	public CommandSender getSender() {
		return sender;
	}

	public void setSender(CommandSender sender) {
		this.sender = sender;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	private CommandSender sender;
	private String[] args;

	public String[] getArgs() {
		return filterFlags(args);
	}

	private String[] filterFlags(String[] a){//removes the flags
		int x = 0;
		int removed = 0;
		String[] newArgs = new String[a.length];
		for(int i = 0; i < a.length; i++){
			String s = a[i];
			if(!s.startsWith("-")){
				newArgs[x] = s;
				x++;
			}
			else{
				removed++;
			}
		}
		String[] xArgs = new String[a.length - removed];
		for(int i = 0; i < newArgs.length; i++){
			xArgs[i] = newArgs[i];
		}
		return xArgs;
	}



	public String getArg(int index){
		return args[index];
	}

}
