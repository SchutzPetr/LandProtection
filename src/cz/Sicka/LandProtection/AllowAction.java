package cz.Sicka.LandProtection;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import cz.Sicka.LandProtection.Flags.Flag;
import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Subzone.Subzone;

public class AllowAction {
	
	public static boolean allowAction(Location loc, Flag flag){
		Land land = Manager.getLandByLocation(loc);
		if(land != null){
			Subzone sub = Manager.getSubzoneByLocation(loc, land);
			if(sub != null){
				return sub.allowAction(flag);
			}
			return land.allowAction(flag);
		}
		return LandProtection.getManager().getWorldArea(loc.getWorld()).allowAction(flag);
	}
	
	public static boolean allowAction(Location loc, UUID userUniqueId, Flag flag){
		if(Bukkit.getPlayer(userUniqueId).isOp()){
			return true;
		}
		Land land = Manager.getLandByLocation(loc);
		if(land != null){
			if(userUniqueId.equals(land.getOwner().getUniqueId())){
				return true;
			}
			Subzone sub = Manager.getSubzoneByLocation(loc, land);
			if(sub != null){
				return sub.allowAction(userUniqueId, flag);
			}
			return land.allowAction(userUniqueId, flag);
		}
		return LandProtection.getManager().getWorldArea(loc.getWorld()).allowAction(userUniqueId, flag);
	}
	
	public static boolean allowAction(Location loc, Player player, Flag flag){
		if(player.isOp()){
			return true;
		}
		return allowAction(loc, player.getUniqueId(), flag);
	}
}
