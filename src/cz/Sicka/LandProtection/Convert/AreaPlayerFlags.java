package cz.Sicka.LandProtection.Convert;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

public class AreaPlayerFlags {
	Map<UUID, FlagsMap> areapFlags = new HashMap<UUID, FlagsMap>();
	
	public AreaPlayerFlags(Map<UUID, FlagsMap> areapFlags){
		this.areapFlags = areapFlags;
	}
	
	public AreaPlayerFlags(){
	}
	
	public void addPlayerFlag(UUID playerUniqueId, Flag flag, boolean value){
		if(this.areapFlags.containsKey(playerUniqueId)){
			this.areapFlags.get(playerUniqueId).addFlag(flag, value);
		}else{
			this.areapFlags.put(playerUniqueId, new FlagsMap(flag, value));
		}
	}
	
	public void removePlayerFlag(UUID playerUniqueId, Flag flag){
		if(this.areapFlags.containsKey(playerUniqueId)){
			this.areapFlags.get(playerUniqueId).removeFlag(flag);
		}
	}
	
	public FlagsMap getPlayerFlags(UUID playerUniqueId){
		if(this.areapFlags.containsKey(playerUniqueId)){
			return this.areapFlags.get(playerUniqueId);
		}else{
			 return null;
		}
	}
	
	public Map<UUID, FlagsMap> getPlayerFlags(){
		return this.areapFlags;
	}
	
	public boolean containsPlayer(Player p){
		if(this.areapFlags.containsKey(p.getUniqueId())){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean containsPlayer(UUID uuid){
		if(this.areapFlags.containsKey(uuid)){
			return true;
		}else{
			return false;
		}
	}
}
