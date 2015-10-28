package cz.Sicka.LandProtection.PermissionsArea;

import cz.Sicka.Core.User.User;
import cz.Sicka.LandProtection.Flags.Flag;
import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Subzone.Subzone;
import cz.Sicka.LandProtection.WorldArea.WorldArea;

public class PermissionsArea {
	private Subzone subzone = null;
	private Land land = null;
	private WorldArea worldArea = null;
	
	public PermissionsArea(Subzone subzone){
		this.subzone = subzone;
	}
	
	public PermissionsArea(Land land){
		this.land = land;
	}
	
	public PermissionsArea(WorldArea worldArea){
		this.worldArea = worldArea;
	}
	
	public boolean allowAction(Flag flag){
		if(this.subzone != null){
			return this.subzone.allowAction(flag);
		}else if(this.land != null){
			return this.land.allowAction(flag);
		}else{
			return this.worldArea.allowAction(flag);
		}
	}
	
	public boolean allowAction(User user, Flag flag){
		if(this.subzone != null){
			return this.subzone.allowAction(user, flag);
		}else if(this.land != null){
			return this.land.allowAction(user, flag);
		}else{
			return this.worldArea.allowAction(user, flag);
		}
	}
	
	public PermissionsAreaType getPermissionsAreaType(){
		if(this.subzone != null){
			return PermissionsAreaType.SUBZONE;
		}else if(this.land != null){
			return PermissionsAreaType.LAND;
		}else{
			return PermissionsAreaType.WORLD;
		}
	}
}
