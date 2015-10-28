package cz.Sicka.LandProtection.Commands;

import cz.Sicka.Core.Group.Group;
import cz.Sicka.Core.User.User;
import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Subzone.Subzone;

public class CommandPermissions {
	public enum CommandType{
		SET, PSET, REMOVE, RENAME, TELEPORT, TELEPORT_SET;
	}
	
	public static boolean accesCommand(User sender, Land land, CommandType type){
		if(sender.getUniqueId().equals(land.getOwner().getUniqueId())){
			return true;
		}else if(sender.isOp() || sender.getGroup().equals(Group.Admin)){
			return true;
		}else if(sender.getGroup().equals(Group.Moderator)){
			return false;
		}
		return false;
	}

	public static boolean accesCommand(User user, Subzone subzone, CommandType pset) {
		// TODO Auto-generated method stub
		return false;
	}
}
