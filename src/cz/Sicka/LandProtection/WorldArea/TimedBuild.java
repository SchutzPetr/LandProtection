package cz.Sicka.LandProtection.WorldArea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cz.Sicka.Core.User.User;

public class TimedBuild {
	private HashMap<UUID, Integer> userBlocks;
	private List<UUID> catcheUser;
	private WorldArea area;
	
	public TimedBuild(WorldArea area){
		this.area = area;
		this.userBlocks = new HashMap<UUID, Integer>();
		this.catcheUser = new ArrayList<UUID>(); 
	}
	
	public Boolean canBuild(User user){
		return !catcheUser.contains(user.getUniqueId());
	}
	
	public Boolean canBuild(User user, boolean breakeOrPlaceBlock){
		return canBuild(user.getUniqueId(), breakeOrPlaceBlock);
	}
	
	public Boolean canBuild(UUID userUniqueId, boolean breakeOrPlaceBlock){
		if(breakeOrPlaceBlock){
			breakeOrPlaceBlock(userUniqueId);
		}
		return !catcheUser.contains(userUniqueId);
	}
	
	public Boolean canBuild(UUID userUniqueId){
		return !catcheUser.contains(userUniqueId);
	}
	
	public void removeFromCatche(User user){
		catcheUser.remove(user.getUniqueId());
	}
	
	public Boolean breakeOrPlaceBlock(UUID userUniqueId){
		if(catcheUser.contains(userUniqueId)){
			return false;
		}
		Integer blocks = userBlocks.get(userUniqueId);
		if(blocks == null){
			userBlocks.put(userUniqueId, 1);
			return true;
		}
		if(blocks >= area.getBuildPerInterval_Blocks()){
			catcheUser.add(userUniqueId);
			return false;
		}else{
			blocks++;
			userBlocks.replace(userUniqueId, blocks);
			return true;
		}
	}
	
	public void refresh(){
		this.catcheUser.clear();
		this.userBlocks.clear();
	}
}
