package cz.Sicka.LandProtection.Commands.PlayerCommands.Base;

import cz.Sicka.Core.User.User;
import cz.Sicka.LandProtection.LandProtection;
import cz.Sicka.LandProtection.Manager;
import cz.Sicka.LandProtection.Commands.CommandMessage;
import cz.Sicka.LandProtection.Commands.CommandPermissions;
import cz.Sicka.LandProtection.Commands.CommandPermissions.CommandType;
import cz.Sicka.LandProtection.Flags.FlagManager;
import cz.Sicka.LandProtection.Land.Land;

public class TeleportCommand {
	
	public static void teleport(User user, String areaName){
		Land teleportTo = LandProtection.getManager().getLand(areaName);
		if(teleportTo != null){
			if(teleportTo.allowAction(user, FlagManager.TELEPORT)){
				if(user.getPlayer().teleport(teleportTo.getTeleportLocation())){
					user.sendMessage(CommandMessage.LandSuccesTeleport + teleportTo.getDisplayName() + "&2.");
				}else{
					user.sendMessage(CommandMessage.LandTeleportFailed + teleportTo.getDisplayName() + "&2.");
				}
			}else{
				user.sendMessage(CommandMessage.NoPermissionsToTeleport);
			}
		}else{
			user.sendMessage(CommandMessage.LandDoesNotExist);
		}
	}
	
	public static void tpSet(User user){
		Land land = Manager.getLandByLocation(user.getPlayer().getLocation());
		if(land != null){
			if(CommandPermissions.accesCommand(user, land, CommandType.TELEPORT_SET)){
				land.setTeleportLocation(user.getPlayer().getLocation());
				user.sendMessage(CommandMessage.LandSuccesfulTeleportSet);
			}else{
				user.sendMessage(CommandMessage.NoPermissions);
			}
		}else{
			user.sendMessage(CommandMessage.NotLocatedOnLand);
		}
	}
}
