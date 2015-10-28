package cz.Sicka.LandProtection.Subzone;

import java.util.UUID;

import org.bukkit.Location;

import cz.Sicka.Core.User.User;
import cz.Sicka.LandProtection.LandProtection;
import cz.Sicka.LandProtection.Flags.Flag;
import cz.Sicka.LandProtection.Flags.FlagType;
import cz.Sicka.LandProtection.Flags.Flags;
import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Utils.Serialize;

public class Subzone extends SubzoneData{
	
	public Subzone(Land mainLand, String subzoneName){
		super(mainLand, subzoneName);
		LandProtection.getChunkStorage().add(this);
	}
	
	public void editSubzone(Location firstPoint, Location secondPoint){
		Serialize ser = new Serialize(firstPoint, secondPoint);
		
		this.setHighX(ser.getHighX());
		this.setLowX(ser.getLowX());
		
		this.setHighY(ser.getHighY());
		this.setLowY(ser.getLowY());
		
		this.setHighZ(ser.getHighZ());
		this.setLowZ(ser.getLowZ());
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
	
	public int getExpanse(){
		 int xsize = (this.getHighX() - this.getLowX() ) + 1;
	     int zsize = (this.getHighZ() - this.getLowZ() ) + 1;
	        return xsize * zsize;
	}

	public int getSize(){
       int xsize = (this.getHighX()  - this.getLowX() ) + 1;
       int ysize = (this.getHighY()  - this.getLowY() ) + 1;
       int zsize = (this.getHighZ()  - this.getLowZ() ) + 1;
       return xsize * ysize * zsize;
   }
	
	public int getXSize() {
       return (this.getHighX() - this.getLowX() ) + 1;
   }

   public int getYSize() {
       return (this.getHighY() - this.getLowY() ) + 1;
   }

   public int getZSize() {
       return (this.getHighZ() - this.getLowZ() ) + 1;
   }

   public Location getHighLocation() {
       return new Location(LandProtection.getInstance().getServer().getWorld(this.getLand().getWorldName()), this.getHighX() , this.getHighY() , this.getHighZ() );
   }

   public Location getLowLocation() {
       return new Location(LandProtection.getInstance().getServer().getWorld(this.getLand().getWorldName()), this.getLowX() , this.getLowY() , this.getLowZ() );
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
   
   public String getFullName(){
	   return this.getLand().getName() + "_" + this.getName();
   }
}
