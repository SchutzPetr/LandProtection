package cz.Sicka.LandProtection.Convert;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import cz.Sicka.Core.User.OfflineUser;
import cz.Sicka.Core.User.UManager;

public class Area {
	private String areaName;
	
	private List<String> chunks;
	
	private int highX;
	private int highY;
	private int highZ;
    
	private int lowX;
	private int lowY;
	private int lowZ;
	
	private Location teleportLocation;
	
	private String world;
	
	private UUID owner;
	private AreaPlayerFlags areaPlayerFlags;
	
	private long CreationDate;
	private FlagsMap areaFlags;
	
	private String enterMessage;
	private String leaveMessage;

	public Area(String areaName, String owner, int loc_1_x, int loc_1_y, int loc_1_z, int loc_2_x, int loc_2_y, int loc_2_z, String worldName){
		this.areaName = areaName;
		this.owner = UUID.fromString(owner);
		serialize(loc_1_x, loc_1_y, loc_1_z, loc_2_x, loc_2_y, loc_2_z);
		
		this.world = worldName;
		
		this.areaPlayerFlags = new AreaPlayerFlags();
	}
	
	private void serialize(int loc_1_x, int loc_1_y, int loc_1_z, int loc_2_x, int loc_2_y, int loc_2_z){
		if(loc_1_x > loc_2_x){
			highX = loc_1_x;
			lowX = loc_2_x;
		}else{
			highX = loc_2_x;
			lowX = loc_1_x;
		}
		if(loc_1_y > loc_2_y){
			highY = loc_1_y;
			lowY = loc_2_y;
		}else{
			highY = loc_2_y;
			lowY = loc_1_y;
		}
		if(loc_1_z > loc_2_z){
			highZ = loc_1_z;
			lowZ = loc_2_z;
		}else{
			highZ = loc_2_z;
			lowZ = loc_1_z;
		}
	}
	
	public int getExpanse(){
		 int xsize = (highX - lowX) + 1;
	     int zsize = (highZ - lowZ) + 1;
	        return xsize * zsize;
	}

	public int getSize(){
        int xsize = (highX - lowX) + 1;
        int ysize = (highY - lowY) + 1;
        int zsize = (highZ - lowZ) + 1;
        return xsize * ysize * zsize;
    }
	
	public int getXSize() {
        return (highX - lowX) + 1;
    }

    public int getYSize() {
        return (highY - lowY) + 1;
    }

    public int getZSize() {
        return (highZ - lowZ) + 1;
    }

    public Location getHighLocation() {
        return new Location(Bukkit.getWorld(world), highX, highY, highZ);
    }

    public Location getLowLocation() {
        return new Location(Bukkit.getWorld(world), lowX, lowY, lowZ);
    }

	public int getHighX() {
        return highX;
    }

    public int getHighY() {
        return highY;
    }

    public int getHighZ() {
        return highZ;
    }

    public int getLowX() {
        return lowX;
    }

    public int getLowY() {
        return lowY;
    }

    public int getLowZ() {
        return lowZ;
    }
    
    public boolean containsLocation(Location loc) {
		return containsLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
    
    public boolean containsLocation(int x, int y, int z) {
        if (lowX <= x && highX >= x) {
            if (lowZ <= z && highZ >= z) {
                if (lowY <= y && highY >= y) {
                    return true;
                }
            }
        }
        return false;
    }
	
	public String getWorldName(){
		return world;
	}

	public String getAreaName() {
		return areaName;
	}

	public AreaPlayerFlags getAreaPlayerFlags() {
		return areaPlayerFlags;
	}

	public void setAreaPlayerFlags(AreaPlayerFlags areaPlayerFlags) {
		this.areaPlayerFlags = areaPlayerFlags;
	}

	public long getCreationDate() {
		return CreationDate;
	}

	public void setCreationDate(long creationDate) {
		CreationDate = creationDate;
	}

	public FlagsMap getAreaFlags() {
		return areaFlags;
	}

	public void setAreaFlags(FlagsMap areaFlags) {
		this.areaFlags = areaFlags;
	}

	public OfflineUser getOwner() {
		return UManager.getOfflineUser(owner);
	}
	
	public UUID getOwnerUniqueId() {
		return this.owner;
	}

	public List<String> getChunks() {
		return chunks;
	}

	public void setChunks(List<String> chunkNames) {
		this.chunks = chunkNames;
	}
	
	public Location getCenter() {
        return new Location(Bukkit.getWorld(getWorldName()), (getHighX() + getLowX()) / 2, (getHighY() + getLowY()) / 2, (getHighZ() + getLowZ()) / 2);
    }
	
	private boolean allowActionPlayerFlag(UUID uuid, Flag flag){
		Flag origFlag = flag;
		Boolean bool;
		FlagsMap fm = getAreaPlayerFlags().getPlayerFlags(uuid);
		while(true){
			if(fm != null){
				bool = fm.getAreaFlags().get(flag);
	            if (bool != null) {
	                return bool;
	            }
			}
			/*bool = areaFlags.getAreaFlags().get(flag);
            if (bool != null) {
                return bool;
            }*/
            if(flag.getParent() == null){
            	return allowAction(origFlag);
            	//return FlagManager.getAreaFlag(origFlag.getName()).getDefaultAreaFlagValue();
            }else{
            	flag = flag.getParent();
            }
		}

	}
	
    public boolean allowAction(Flag flag) {
        Flag origFlag = flag;
        while (true) {
            Boolean bool = areaFlags.getAreaFlags().get(flag);
            if (bool != null) {
                return bool;
            }
            if (flag.getParent() != null) {
                flag = flag.getParent();
            } else {
            	return FlagManager.getAreaFlag(origFlag.getName()).getDefaultAreaFlagValue();
            }
        }
    }
	
	@Deprecated
	public boolean allowAction(UUID player, Flag flag){
		if(isOwner(player)){
			return true;
		}else{
			return allowActionPlayerFlag(player, flag);
		}
	}
	
	public boolean isOwner(UUID player){
		return getOwnerUniqueId().equals(player);
	}

	/**
	 * @return the leaveMessage
	 */
	public String getLeaveMessage() {
		return leaveMessage;
	}

	/**
	 * @param leaveMessage the leaveMessage to set
	 */
	public void setLeaveMessage(String leaveMessage) {
		this.leaveMessage = leaveMessage;
	}

	/**
	 * @return the enterMessage
	 */
	public String getEnterMessage() {
		return enterMessage;
	}

	/**
	 * @param enterMessage the enterMessage to set
	 */
	public void setEnterMessage(String enterMessage) {
		this.enterMessage = enterMessage;
	}

	/**
	 * @return the teleportLocation
	 */
	public Location getTeleportLocation() {
		return teleportLocation;
	}

	/**
	 * @param teleportLocation the teleportLocation to set
	 */
	public void setTeleportLocation(Location teleportLocation) {
		this.teleportLocation = teleportLocation;
	}
}
