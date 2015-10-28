package cz.Sicka.LandProtection.Commands.PlayerCommands.Base;

import cz.Sicka.Core.User.User;
import cz.Sicka.LandProtection.LandProtection;
import cz.Sicka.LandProtection.Manager;
import cz.Sicka.LandProtection.Commands.CommandMessage;
import cz.Sicka.LandProtection.Commands.CommandPermissions;
import cz.Sicka.LandProtection.Commands.CommandPermissions.CommandType;
import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Subzone.Subzone;

public class RemoveCommand {
	
	public static void remove(User user){
		Land land = Manager.getLandByLocation(user.getPlayer().getLocation());
		if(land != null){
			Subzone subzone = Manager.getSubzoneByLocation(user.getPlayer().getLocation(), land);
			if(subzone != null){
				remove(user, subzone);
				return;
			}else{
				remove(user, land);
				return;
			}
		}else{
			user.sendMessage(CommandMessage.NotLocatedOnLand);
		}
	}
	
	public static void remove(User user, String landOrSubzoneName){
		String lowerCaseName = landOrSubzoneName.toLowerCase();
		if(lowerCaseName.contains(".")){
			String[] split = lowerCaseName.split("\\.");
			String mainLand = split[0];
			String sub = split[1];
			Land land =  LandProtection.getManager().getLand(mainLand);
			if(land != null){
				Subzone subzone = LandProtection.getManager().getLand(mainLand).getSubzone(sub);
				if(subzone != null){
					remove(user, subzone);
					return;
				}else{
					user.sendMessage(CommandMessage.SubzoneDoesNotExist);
				}
			}else{
				user.sendMessage(CommandMessage.LandDoesNotExist);
			}
		}else{
			Land land =  LandProtection.getManager().getLand(landOrSubzoneName);
			if(land != null){
				remove(user, land);
				return;
			}else{
				user.sendMessage(CommandMessage.LandDoesNotExist);
			}
		}
	}
	
	private static void remove(User user, Subzone subzone) {
		if(!CommandPermissions.accesCommand(user, subzone, CommandType.REMOVE)){
			user.sendMessage(CommandMessage.NoPermissions);
			return;
		}
		LandProtection.getManager().remove(subzone);
		user.sendMessage(CommandMessage.SubzoneSuccesfulRemove);
	}

	public static void remove(User user, Land land){
		if(!CommandPermissions.accesCommand(user, land, CommandType.REMOVE)){
			user.sendMessage(CommandMessage.NoPermissions);
			return;
		}
		LandProtection.getManager().remove(land);
		user.getAccount().addBlocks(land.getExpanse());
		user.sendMessage(CommandMessage.LandSuccesfulRemove);
	}


}
