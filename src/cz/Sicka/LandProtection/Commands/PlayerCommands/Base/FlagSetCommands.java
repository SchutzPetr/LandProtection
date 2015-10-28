package cz.Sicka.LandProtection.Commands.PlayerCommands.Base;

import java.util.UUID;

import cz.Sicka.Core.Core;
import cz.Sicka.Core.User.User;
import cz.Sicka.LandProtection.LandProtection;
import cz.Sicka.LandProtection.Manager;
import cz.Sicka.LandProtection.Commands.CommandMessage;
import cz.Sicka.LandProtection.Commands.CommandPermissions;
import cz.Sicka.LandProtection.Commands.CommandPermissions.CommandType;
import cz.Sicka.LandProtection.Flags.Flag;
import cz.Sicka.LandProtection.Flags.FlagManager;
import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Subzone.Subzone;

public class FlagSetCommands {
	
	public static void pset(User user, String playerName, String flagName, String flagValue) {
		Land land = Manager.getLandByLocation(user.getPlayer().getLocation());
		if(land != null){
			Subzone subzone = Manager.getSubzoneByLocation(user.getPlayer().getLocation(), land);
			if(subzone != null){
				pset(user, playerName, subzone, flagName, flagValue);
				return;
			}else{
				pset(user, playerName, land, flagName, flagValue);
				return;
			}
		}else{
			user.sendMessage(CommandMessage.NotLocatedOnLand);
		}
	}
	
	public static void pset(User user, String playerName, String landOrSubzoneName, String flagName, String flagValue){
		String lowerCaseName = landOrSubzoneName.toLowerCase();
		if(lowerCaseName.contains(".")){
			String[] split = lowerCaseName.split("\\.");
			String mainLand = split[0];
			String sub = split[1];
			Land land =  LandProtection.getManager().getLand(mainLand);
			if(land != null){
				Subzone subzone = LandProtection.getManager().getLand(mainLand).getSubzone(sub);
				if(subzone != null){
					pset(user, playerName, subzone, flagName, flagValue);
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
				pset(user, playerName, land, flagName, flagValue);
				return;
			}else{
				user.sendMessage(CommandMessage.LandDoesNotExist);
			}
		}
	}
	
	private static void pset(User user, String playerName, Land land, String flagName, String flagValue){
		if(!CommandPermissions.accesCommand(user, land, CommandType.PSET)){
			user.sendMessage(CommandMessage.NoPermissions);
			return;
		}
		UUID playerUUID = Core.getUserCatche().getCachedUUID(playerName);
		if(playerUUID == null){
			user.sendMessage(CommandMessage.UnknownPlayer);
			return;
		}
		Flag flag = FlagManager.getFlag(flagName);
		if(flag == null){
			user.sendMessage(CommandMessage.UnknownFlag);
			return;
		}
		if(flagValue.equalsIgnoreCase("r") || flagValue.equalsIgnoreCase("remove")){
			land.removePlayerFlag(playerUUID, flag);
			user.sendMessage(CommandMessage.FlagSuccesfulRemoved);
		}else if(flagValue.equalsIgnoreCase("t") || flagValue.equalsIgnoreCase("true")){
			land.setPlayerFlag(playerUUID, flag, true);
			user.sendMessage(CommandMessage.FlagSuccesfulTrusted);
		}else if(flagValue.equalsIgnoreCase("f") || flagValue.equalsIgnoreCase("false")){
			land.setPlayerFlag(playerUUID, flag, false);
			user.sendMessage(CommandMessage.FlagSuccesfulBanned);
		}else{
			user.sendMessage(CommandMessage.UnknownBooleanValue);
			return;
		}
	}
	
	private static void pset(User user, String playerName, Subzone subzone, String flagName, String flagValue){
		if(!CommandPermissions.accesCommand(user, subzone, CommandType.PSET)){
			user.sendMessage(CommandMessage.NoPermissions);
			return;
		}
		UUID playerUUID = Core.getUserCatche().getCachedUUID(playerName);
		if(playerUUID == null){
			user.sendMessage(CommandMessage.UnknownPlayer);
			return;
		}
		Flag flag = FlagManager.getFlag(flagName);
		if(flag == null){
			user.sendMessage(CommandMessage.UnknownFlag);
			return;
		}
		if(flagValue.equalsIgnoreCase("r") || flagValue.equalsIgnoreCase("remove")){
			subzone.removePlayerFlag(playerUUID, flag);
			user.sendMessage(CommandMessage.FlagSuccesfulRemoved);
		}else if(flagValue.equalsIgnoreCase("t") || flagValue.equalsIgnoreCase("true")){
			subzone.setPlayerFlag(playerUUID, flag, true);
			user.sendMessage(CommandMessage.FlagSuccesfulTrusted);
		}else if(flagValue.equalsIgnoreCase("f") || flagValue.equalsIgnoreCase("false")){
			subzone.setPlayerFlag(playerUUID, flag, false);
			user.sendMessage(CommandMessage.FlagSuccesfulBanned);
		}else{
			user.sendMessage(CommandMessage.UnknownBooleanValue);
			return;
		}
	}
	
	public static void set(User user, String flagName, String flagValue){
		Land land = Manager.getLandByLocation(user.getPlayer().getLocation());
		if(land != null){
			Subzone subzone = Manager.getSubzoneByLocation(user.getPlayer().getLocation(), land);
			if(subzone != null){
				set(user, subzone, flagName, flagValue);
				return;
			}else{
				set(user, land, flagName, flagValue);
				return;
			}
		}else{
			user.sendMessage(CommandMessage.NotLocatedOnLand);
		}
	}
	
	public static void set(User user, String landOrSubzoneName, String flagName, String flagValue){
		String lowerCaseName = landOrSubzoneName.toLowerCase();
		if(lowerCaseName.contains(".")){
			String[] split = lowerCaseName.split("\\.");
			String mainLand = split[0];
			String sub = split[1];
			Land land =  LandProtection.getManager().getLand(mainLand);
			if(land != null){
				Subzone subzone = LandProtection.getManager().getLand(mainLand).getSubzone(sub);
				if(subzone != null){
					set(user, subzone, flagName, flagValue);
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
				set(user, land, flagName, flagValue);
				return;
			}else{
				user.sendMessage(CommandMessage.LandDoesNotExist);
			}
		}
	}
	
	private static void set(User user, Land land, String flagName, String flagValue){
		if(!CommandPermissions.accesCommand(user, land, CommandType.SET)){
			user.sendMessage(CommandMessage.NoPermissions);
			return;
		}
		Flag flag = FlagManager.getFlag(flagName);
		if(flag == null){
			user.sendMessage(CommandMessage.UnknownFlag);
			return;
		}
		if(flagValue.equalsIgnoreCase("r") || flagValue.equalsIgnoreCase("remove")){
			land.removeAreaFlag(flag);
			user.sendMessage(CommandMessage.FlagSuccesfulRemoved);
		}else if(flagValue.equalsIgnoreCase("t") || flagValue.equalsIgnoreCase("true")){
			land.setAreaFlag(flag, true);
			user.sendMessage(CommandMessage.FlagSuccesfulTrusted);
		}else if(flagValue.equalsIgnoreCase("f") || flagValue.equalsIgnoreCase("false")){
			land.setAreaFlag(flag, false);
			user.sendMessage(CommandMessage.FlagSuccesfulBanned);
		}else{
			user.sendMessage(CommandMessage.UnknownBooleanValue);
			return;
		}
	}
	
	private static void set(User user, Subzone subzone, String flagName, String flagValue){
		if(!CommandPermissions.accesCommand(user, subzone, CommandType.SET)){
			user.sendMessage(CommandMessage.NoPermissions);
			return;
		}
		Flag flag = FlagManager.getFlag(flagName);
		if(flag == null){
			user.sendMessage(CommandMessage.UnknownFlag);
			return;
		}
		if(flagValue.equalsIgnoreCase("r") || flagValue.equalsIgnoreCase("remove")){
			subzone.removeAreaFlag(flag);
			user.sendMessage(CommandMessage.FlagSuccesfulRemoved);
		}else if(flagValue.equalsIgnoreCase("t") || flagValue.equalsIgnoreCase("true")){
			subzone.setAreaFlag(flag, true);
			user.sendMessage(CommandMessage.FlagSuccesfulTrusted);
		}else if(flagValue.equalsIgnoreCase("f") || flagValue.equalsIgnoreCase("false")){
			subzone.setAreaFlag(flag, false);
			user.sendMessage(CommandMessage.FlagSuccesfulBanned);
		}else{
			user.sendMessage(CommandMessage.UnknownBooleanValue);
			return;
		}
	}
}
