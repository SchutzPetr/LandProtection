package cz.Sicka.LandProtection.Utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import cz.Sicka.Core.User.UManager;
import cz.Sicka.Core.Utils.Replacer;
import cz.Sicka.LandProtection.LandProtection;
import cz.Sicka.LandProtection.Manager;
import cz.Sicka.LandProtection.Land.Land;

public class HandleNewlocationUtils {
	private static Map<Player, Land> lastLand = new HashMap<Player, Land>();
	
	public static void handleNewLocation(Player player, Location from, Location to){
		Land fromLand;
		Land toLand = Manager.getLandByLocation(from);
		if(lastLand.containsKey(player)){
			fromLand = lastLand.get(player);
			lastLand.replace(player, toLand);
			if(fromLand != null && toLand != null){
				if(!fromLand.equals(toLand)){
					sendMessage(player, fromLand, toLand);
				}else{
					return;
				}
			}else if(fromLand != null || toLand != null){
				sendMessage(player, fromLand, toLand);
			}else{
				return;
			}
		}else{
			fromLand = Manager.getLandByLocation(from);
			lastLand.put(player, fromLand);
		}
	}

	private static void sendMessage(Player player, Land fromLand, Land toLand) {
		//BarAPI.sendTitle(player, 2, 2, 2, getFrom(player, fromLand), getTo(player, toLand));
		UManager.getUser(player.getUniqueId()).sendMessage(getFrom(player, fromLand));
		UManager.getUser(player.getUniqueId()).sendMessage(getTo(player, toLand));
	}
	
	public static String getFrom(Player player, Land fromLand){
		if(fromLand == null){
			return LandProtection.getManager().getWorldArea(player.getWorld()).getLeaveMessage();
		}else{
			String toReplace = fromLand.getLeaveMessage();
			toReplace = toReplace.replace("<Old_Land>", fromLand.getDisplayName());
			toReplace = toReplace.replace("<Owner>", fromLand.getOwner().getName());
			return Replacer.replace(player, toReplace);
		}
	}
	
	public static String getTo(Player player, Land toLand){
		if(toLand == null){
			return LandProtection.getManager().getWorldArea(player.getWorld()).getEnterMessage();
		}else{
			String toReplace = toLand.getEnterMessage();
			toReplace = toReplace.replace("<New_Land>", toLand.getDisplayName());
			toReplace = toReplace.replace("<Owner>", toLand.getOwner().getName());
			return Replacer.replace(player, toReplace);
		}
	}
}
