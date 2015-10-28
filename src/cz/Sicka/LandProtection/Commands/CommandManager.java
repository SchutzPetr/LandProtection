package cz.Sicka.LandProtection.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import cz.Sicka.Core.User.UManager;
import cz.Sicka.LandProtection.Commands.PlayerCommands.PlayerCommandManager;

public class CommandManager implements CommandExecutor{
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(sender instanceof Player){
			Player player = (Player) sender;
			if(cmd.getName().equalsIgnoreCase("landprotection")){
				PlayerCommandManager.executeCommand(UManager.getUser(player.getUniqueId()), cmd, commandLabel, args);
			}
		}
		return false;
	}
}
