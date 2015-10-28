package cz.Sicka.LandProtection.Commands.PlayerCommands.Base;

import cz.Sicka.Core.User.User;
import cz.Sicka.Core.Utils.CoreUtils;
import cz.Sicka.LandProtection.LandProtection;
import cz.Sicka.LandProtection.Manager;
import cz.Sicka.LandProtection.Commands.CommandMessage;
import cz.Sicka.LandProtection.Commands.CommandPermissions;
import cz.Sicka.LandProtection.Commands.CommandPermissions.CommandType;
import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Subzone.Subzone;

public class RenameCommand {
	
	public static void rename(User user, String oldName, String newName){
		String lowerCaseName = oldName.toLowerCase();
		if(lowerCaseName.contains(".")){
			String[] split = lowerCaseName.split("\\.");
			String mainLand = split[0];
			String sub = split[1];
			Land land =  LandProtection.getManager().getLand(mainLand);
			if(land != null){
				Subzone subzone = LandProtection.getManager().getLand(mainLand).getSubzone(sub);
				if(subzone != null){
					rename(user, subzone, newName);
					return;
				}else{
					user.sendMessage(CommandMessage.SubzoneDoesNotExist);
				}
			}else{
				user.sendMessage(CommandMessage.LandDoesNotExist);
			}
		}else{
			Land land =  LandProtection.getManager().getLand(oldName);
			if(land != null){
				rename(user, land, newName);
				return;
			}else{
				user.sendMessage(CommandMessage.LandDoesNotExist);
			}
		}
	}
	
	public static void rename(User user, String newName){
		Land land = Manager.getLandByLocation(user.getPlayer().getLocation());
		if(land != null){
			Subzone subzone = Manager.getSubzoneByLocation(user.getPlayer().getLocation(), land);
			if(subzone != null){
				rename(user, subzone, newName);
				return;
			}else{
				rename(user, land, newName);
				return;
			}
		}else{
			user.sendMessage(CommandMessage.NotLocatedOnLand);
		}
	}
	
	private static void rename(User user, Land land, String newName){
		if(!CommandPermissions.accesCommand(user, land, CommandType.RENAME)){
			user.sendMessage(CommandMessage.NoPermissions);
			return;
		}
		if(LandProtection.getManager().isLandExist(newName)){
			user.sendMessage(CommandMessage.LandAlreadyExist);
			return;
		}
		if(!CoreUtils.isAlphaNumeric(newName)){
			user.sendMessage(CommandMessage.UseAlphaNumeric);
			return;
		}
		if(LandProtection.getManager().rename(land, newName)){
			user.sendMessage(CommandMessage.LandSuccesfulRename);
		}else{
			user.sendMessage(CommandMessage.LandRenameFailed);
		}
	}
	
	private static void rename(User user, Subzone subzone, String newName){
		if(!CommandPermissions.accesCommand(user, subzone, CommandType.RENAME)){
			user.sendMessage(CommandMessage.NoPermissions);
			return;
		}
		if(subzone.getLand().getSubzone(newName) != null){
			//TODO: already exist
			return;
		}
		if(!CoreUtils.isAlphaNumeric(newName)){
			user.sendMessage(CommandMessage.UseAlphaNumeric);
		}
	}
}
