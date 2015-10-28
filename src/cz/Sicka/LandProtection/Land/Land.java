package cz.Sicka.LandProtection.Land;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;

import cz.Sicka.Core.User.User;
import cz.Sicka.LandProtection.LandProtection;
import cz.Sicka.LandProtection.Flags.Flag;
import cz.Sicka.LandProtection.Flags.FlagType;
import cz.Sicka.LandProtection.Flags.Flags;
import cz.Sicka.LandProtection.Subzone.Subzone;
import cz.Sicka.LandProtection.Utils.LandProtectionUtils;
import cz.Sicka.LandProtection.Utils.SelectionArea;
import cz.Sicka.LandProtection.Utils.Serialize;

public class Land extends LandData{
	private final Map<String, Subzone> subzones = new HashMap<String, Subzone>();
	
	public Land(String landName) {
		super(landName);
		LandProtection.getChunkStorage().add(this);
	}
	
	public World getWorld(){
		return LandProtection.getInstance().getServer().getWorld(getWorldName());
	}
	
	public void addSubzone(Subzone subzone){
		this.subzones.put(subzone.getName(), subzone);
	}
	
	public void removeSubzone(Subzone subzone){
		this.subzones.remove(subzone.getName());
	}
	
	public void removeSubzone(String subzoneName){
		this.subzones.remove(subzoneName);
	}
	
	public void newSubzone(Subzone subzone){
		this.addSubzone(subzone);
		this.addNewSubzone(subzone.getName());
	}
	
	public Subzone getSubzone(String subzoneName){
		return this.subzones.get(subzoneName);
	}
	
	public void loadSubzones(){
		for(String sub : this.getListOfSubzones()){
			Subzone loadsub = new Subzone(this, sub);
			this.addSubzone(loadsub);
		}
	}
	
	public Collection<Subzone> getSubzones(){
		return this.subzones.values();
	}
	
	public boolean subzoneExist(String subzoneName){
		return this.subzones.containsKey(subzoneName);
	}
	
	public void editLand(Location firstPoint, Location secondPoint){
		Serialize ser = new Serialize(firstPoint, secondPoint);
		
		this.setHighX(ser.getHighX());
		this.setLowX(ser.getLowX());
		
		this.setHighY(ser.getHighY());
		this.setLowY(ser.getLowY());
		
		this.setHighZ(ser.getHighZ());
		this.setLowZ(ser.getLowZ());
		ser = null;
	}
	
	public boolean allowAction(UUID userUniqueId, Flag flag){
		Flag origFlag = flag;
		Boolean flagValue;
		Flags playerFlags = this.getLSFlags().getPlayerFlags(userUniqueId);
		while(true){
			if(playerFlags != null){
				flagValue = playerFlags.getFlag(flag);
				if (flagValue != null) {
	                return flagValue;
	            }
			}
			if(flag.getParent() != null){
				flag = flag.getParent();
			}else{
				if(origFlag.getType() == FlagType.ANY || origFlag.getType() == FlagType.AREA_ONLY){
					return allowAction(origFlag);
				}else{
					return origFlag.getDefaultValue();
				}
			}
		}
		
	}
	
	public boolean allowAction(User user, Flag flag){
		Flag origFlag = flag;
		Boolean flagValue;
		Flags playerFlags = this.getLSFlags().getPlayerFlags(user.getUniqueId());
		while(true){
			if(playerFlags != null){
				flagValue = playerFlags.getFlag(flag);
				if (flagValue != null) {
	                return flagValue;
	            }
			}
			if(flag.getParent() != null){
				flag = flag.getParent();
			}else{
				if(origFlag.getType() == FlagType.ANY || origFlag.getType() == FlagType.AREA_ONLY){
					return allowAction(origFlag);
				}else{
					return origFlag.getDefaultValue();
				}
			}
		}
		
	}
	
	public boolean allowAction(Flag flag){
		Flag origFlag = flag;
		Boolean flagValue;
		while(true){
			flagValue = this.getLSFlags().getAreaFlag(flag);
			if (flagValue != null) {
                return flagValue;
            }
			if(flag.getParent() != null){
				flag = flag.getParent();
			}else{
				return origFlag.getDefaultValue();
			}
		}
	}
	
	public long getExpanse(){
		int xsize = (this.getHighX() - this.getLowX() ) + 1;
	    int zsize = (this.getHighZ()  - this.getLowZ() ) + 1;
	    return xsize * zsize;
	}
	
	public long getSize(){
		int xsize = (this.getHighX()  - this.getLowX() ) + 1;
        int ysize = (this.getHighY()  - this.getLowY() ) + 1;
        int zsize = (this.getHighZ()  - this.getLowZ() ) + 1;
        return xsize * ysize * zsize;
	}
	
	public int getXSize() {
		return (this.getHighX()  - this.getLowX() ) + 1;
	}

	public int getYSize() {
		return (this.getHighY()  - this.getLowY() ) + 1;
	}

	public int getZSize() {
		return (this.getHighZ()  - this.getLowZ() ) + 1;
	}

	public Location getHighLocation() {
		return new Location(LandProtection.getInstance().getServer().getWorld(this.getWorldName()), this.getHighX() , this.getHighY() , this.getHighZ() );
	}

	public Location getLowLocation() {
		return new Location(LandProtection.getInstance().getServer().getWorld(this.getWorldName()), this.getLowX() , this.getLowY() , this.getLowZ() );
	}
   
	public boolean containsLocation(Location loc) {
		return containsLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	public boolean containsLocation(int x, int y, int z) {
		if (getLowX() <= x && getHighX() >= x) {
			if (getLowZ() <= z && getHighZ() >= z) {
				if (getLowY() <= y && getHighY() >= y) {
					return true;
				}
			}
		}
        return false;
	}
	
	public boolean checkCollision(Land land) {
		if (land.containsLocation(getHighX(), getHighY(), getHighZ()) || land.containsLocation(getLowX(), getLowY(), getLowZ()) || this.containsLocation(land.getHighX(), land.getHighY(), land.getHighZ()) || this.containsLocation(land.getLowX(), land.getLowY(), land.getLowZ())) {
			return true;
        }
        return advLandCheckCollision(land);
	}
	
	private boolean advLandCheckCollision(Land land) {
		if ((getHighX() >= land.getLowX() && getHighX() <= land.getHighX()) || (getLowX() >= land.getLowX() && getLowX() <= land.getHighX()) || (land.getHighX() >= getLowX() && land.getHighX() <= getHighX()) || (land.getLowX() >= getLowX() && land.getLowX() <= getHighX())) {
			if ((getHighY() >= land.getLowY() && getHighY() <= land.getHighY()) || (getLowY() >= land.getLowY() && getLowY() <= land.getHighY()) || (land.getHighY() >= getLowY() && land.getHighY() <= getHighY()) || (land.getLowY() >= getLowY() && land.getLowY() <= getHighY())) {
				if ((getHighZ() >= land.getLowZ() && getHighZ() <= land.getHighZ()) || (getLowZ() >= land.getLowZ() && getLowZ() <= land.getHighZ()) || (land.getHighZ() >= getLowZ() && land.getHighZ() <= getHighZ()) || (land.getLowZ() >= getLowZ() && land.getLowZ() <= getHighZ())) {
					return true;
				}
			}
		}
        return false;
	}
	
	public Location getCenter() {
        return new Location(getWorld(), (getHighX() + getLowX()) / 2, (getHighY() + getLowY()) / 2, (getHighZ() + getLowZ()) / 2);
    }
	
	public void setdefaultValues(){
		getConfig().set("AreaFlags", null);
		getConfig().set("PlayersFlags", null);
		
		for(Subzone sub : this.getSubzones()){
			this.removeSubzone(sub);
		}
		
		Location tel = LandProtectionUtils.getDefaultTeleportLocation(new SelectionArea(this));
		
		getConfig().set("TPLocation.X", tel.getBlockX());
		getConfig().set("TPLocation.Y", tel.getBlockY());
		getConfig().set("TPLocation.Z", tel.getBlockZ());
		
		getConfig().set("EnterMessage", LandProtection.getSettings().getDefaultEnterMessage());
		getConfig().set("LeaveMessage",  LandProtection.getSettings().getDefaultLeaveMessage());
		getConfig().createSection("AreaFlags", LandProtection.getSettings().getDefaultAreaFlags());
	}
}
