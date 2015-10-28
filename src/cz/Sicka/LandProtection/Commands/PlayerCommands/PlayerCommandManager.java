package cz.Sicka.LandProtection.Commands.PlayerCommands;

import org.bukkit.command.Command;

import cz.Sicka.Core.User.User;
import cz.Sicka.Core.Utils.CoreUtils;
import cz.Sicka.LandProtection.Commands.PlayerCommands.Base.FlagSetCommands;
import cz.Sicka.LandProtection.Commands.PlayerCommands.Base.InfoCommand;
import cz.Sicka.LandProtection.Commands.CommandFancyMessage;
import cz.Sicka.LandProtection.Commands.CommandMessage;
import cz.Sicka.LandProtection.Commands.PlayerCommands.Base.ClaimCommand;
import cz.Sicka.LandProtection.Commands.PlayerCommands.Base.RemoveCommand;
import cz.Sicka.LandProtection.Commands.PlayerCommands.Base.RenameCommand;
import cz.Sicka.LandProtection.Commands.PlayerCommands.Base.SelectCommand;
import cz.Sicka.LandProtection.Commands.PlayerCommands.Base.TeleportCommand;

public class PlayerCommandManager {
	public static void executeCommand(User user, Command cmds, String commandLabel, String[] args){
		if((args == null) || (args.length < 1)){
			user.sendMessage("&7++++&2--------[ &6" + "LandProtection - Prikazy" + "&2 ]--------&7++++");
			user.sendRawMessage(CommandFancyMessage.landPlayerSetCommand());
			user.sendRawMessage(CommandFancyMessage.landSetCommand());
			user.sendRawMessage(CommandFancyMessage.landInfoCommand());
			user.sendRawMessage(CommandFancyMessage.landRemoveCommand());
			user.sendRawMessage(CommandFancyMessage.landSelectCommand());
			user.sendRawMessage(CommandFancyMessage.landRenameCommand());
			user.sendRawMessage(CommandFancyMessage.landTeleportCommand());
			user.sendRawMessage(CommandFancyMessage.landTeleportSetCommand());
			user.sendRawMessage(CommandFancyMessage.landClaimCommand());
			user.sendMessage("&7++++&2------------------------&7++++");
		}else if(args[0].equalsIgnoreCase("tp")){
			if(args.length == 2){
				TeleportCommand.teleport(user, args[1]);
			}else{
				user.sendMessage("&7++++&2--------[ &6" + "LandProtection" + "&2 ]--------&7++++");
				user.sendMessage(CommandMessage.IncorrectCommandArgs);
				user.sendRawMessage(CommandFancyMessage.landTeleportCommand());
			}
		}else if(args[0].equalsIgnoreCase("tpset")){
			if(args.length == 1){
				TeleportCommand.tpSet(user);
			}else{
				user.sendMessage("&7++++&2--------[ &6" + "LandProtection" + "&2 ]--------&7++++");
				user.sendMessage(CommandMessage.IncorrectCommandArgs);
				user.sendRawMessage(CommandFancyMessage.landTeleportSetCommand());
			}
		}else if(args[0].equalsIgnoreCase("claim")){
			if(args.length == 2){
				ClaimCommand.claim(user, args[1]);
			}else{
				user.sendMessage("&7++++&2--------[ &6" + "LandProtection" + "&2 ]--------&7++++");
				user.sendMessage(CommandMessage.IncorrectCommandArgs);
				user.sendRawMessage(CommandFancyMessage.landClaimCommand());
			}
		}else if(args[0].equalsIgnoreCase("pset")){
			if(args.length == 4){
				FlagSetCommands.pset(user, args[1], args[2], args[3]);
			}else if(args.length == 5){
				FlagSetCommands.pset(user, args[2], args[1], args[3], args[4]);
			}else{
				user.sendMessage("&7++++&2--------[ &6" + "LandProtection" + "&2 ]--------&7++++");
		 		user.sendMessage(CommandMessage.IncorrectCommandArgs);
				user.sendRawMessage(CommandFancyMessage.landPlayerSetCommand());
			}
			/*
			 * -     0    1     2      3
			 *land pser <user> <flag> <boolean>
			 * -    0     1      2     3     4
			 *land pser <land> <user> <flag> <boolean>
			 */
			
		}else if(args[0].equalsIgnoreCase("set")){
			if(args.length == 3){
				FlagSetCommands.set(user, args[1], args[2]);
			}else if(args.length == 4){
				FlagSetCommands.set(user, args[1], args[2], args[3]);
			}else{
				user.sendMessage("&7++++&2--------[ &6" + "LandProtection" + "&2 ]--------&7++++");
				user.sendMessage(CommandMessage.IncorrectCommandArgs);
				user.sendRawMessage(CommandFancyMessage.landSetCommand());
			}
			/*
			 * -     0    1     2   
			 *land set <flag> <boolean>
			 * -    0     1      2     3    
			 *land set <land> <flag> <boolean>
			 */
		}else if(args[0].equalsIgnoreCase("info")){
			if(args.length == 1){
				InfoCommand.infoCommand(user);
			}else if(args.length == 2){
				InfoCommand.infoCommand(user, args[1]);
			}else{
				user.sendMessage("&7++++&2--------[ &6" + "LandProtection" + "&2 ]--------&7++++");
				user.sendMessage(CommandMessage.IncorrectCommandArgs);
				user.sendRawMessage(CommandFancyMessage.landInfoCommand());
			}
		}else if(args[0].equalsIgnoreCase("remove")){
			if(args.length == 1){
				RemoveCommand.remove(user);
			}else if(args.length == 2){
				RemoveCommand.remove(user, args[1]);
			}else{
				user.sendMessage("&7++++&2--------[ &6" + "LandProtection" + "&2 ]--------&7++++");
				user.sendMessage(CommandMessage.IncorrectCommandArgs);
				user.sendRawMessage(CommandFancyMessage.landRemoveCommand());
			}
		}else if(args[0].equalsIgnoreCase("select")){
			if(args.length == 2){
				if(CoreUtils.isInteger(args[1])){
					SelectCommand.select(user, Integer.parseInt(args[1]));
				}else if(args[1].equalsIgnoreCase("vert")){
					
				}else{
					user.sendMessage("&7++++&2--------[ &6" + "LandProtection" + "&2 ]--------&7++++");
					user.sendMessage("&cPozor! &eHodnota <Radius> musi byt cislo!");
				}
			}else{
				user.sendMessage("&7++++&2--------[ &6" + "LandProtection" + "&2 ]--------&7++++");
				user.sendMessage(CommandMessage.IncorrectCommandArgs);
				user.sendRawMessage(CommandFancyMessage.landSelectCommand());
			}
		}else if(args[0].equalsIgnoreCase("rename")){
			if(args.length == 2){
				RenameCommand.rename(user, args[1]);
			}else if(args.length == 2){
				RenameCommand.rename(user, args[1], args[2]);
			}else{
				user.sendMessage("&7++++&2--------[ &6" + "LandProtection" + "&2 ]--------&7++++");
				user.sendMessage(CommandMessage.IncorrectCommandArgs);
				user.sendRawMessage(CommandFancyMessage.landRenameCommand());
			}
		}
	}
}
