package cz.Sicka.LandProtection.Flags;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LSFlags{
	private Map<UUID, Flags> playersFlags;
	private Flags areaFlags;
	
	public LSFlags(){
		this.playersFlags = new HashMap<UUID, Flags>();
		this.areaFlags = new Flags(null);
	}
	
	public LSFlags(Map<UUID, Flags> playerFlags, Flags areaFlags){
		this.playersFlags = playerFlags;
		this.areaFlags = areaFlags;
	}
	
	public Set<UUID> getPlayers(){
		return this.playersFlags.keySet();
	}
	
	public void setPlayerFlag(UUID player, Flag flag, boolean value){
		Flags playerFlags = this.playersFlags.get(player);
		if(playerFlags == null){
			playerFlags = new Flags(null);
		}
		playerFlags.setFlag(flag, value);
		this.playersFlags.put(player, playerFlags);
	}
	
	public Flags getAreaFlags(){
		return areaFlags;
	}
	
	public boolean getPlayerFlag(UUID player, Flag flag){
		return this.playersFlags.get(player).getFlag(flag);
	}
	
	public Flags getPlayerFlags(UUID player){
		return this.playersFlags.get(player);
	}
	
	public boolean containsPlayer(UUID player){
		return this.playersFlags.containsKey(player);
	}
	
	public boolean containsAreaFlag(Flag flag){
		return this.areaFlags.contains(flag);
	}
	
	public Boolean getAreaFlag(Flag flag){
		return this.areaFlags.getFlag(flag);
	}
	
	public void setAreaFlag(Flag flag, boolean value){
		this.areaFlags.setFlag(flag, value);
	}
}