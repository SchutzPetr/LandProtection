package cz.Sicka.LandProtection.Utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class LandProtectionUtils {
	public Location getCenter(Location loc1, Location loc2) {
        return new Location(loc1.getWorld(), (loc1.getBlockX() + loc2.getBlockX()) / 2, (loc1.getBlockY() + loc2.getBlockY()) / 2, (loc1.getBlockZ() + loc2.getBlockZ()) / 2);
    }
	
	public static Location getLocationAboveGround(Location loc){
		Location cLoc = loc.clone();
		cLoc.setY(0);
		boolean ground = false;
		for(int x = 0; x <= 256; x++){
			cLoc.setY(x);
			if(cLoc.getBlock().getType() == Material.AIR){
				for(int y = 0; y <= 20; y++){
					if(cLoc.getBlock().getType() != Material.AIR){
						ground = false;
						break;
					}
					if(y == 20){
						ground = true;
						break;
					}
				}
			}
			if(ground){
				return cLoc;
			}
		}
		cLoc.setY(100);
		return cLoc;
	}
	
	public static Location getDefaultTeleportLocation(SelectionArea selArea){
		return getLocationAboveGround(selArea.getCenter());
	}

	public static boolean isAnimal(EntityType type) {
        return type == EntityType.BAT || type == EntityType.CHICKEN || type == EntityType.COW
                || type == EntityType.HORSE || type == EntityType.IRON_GOLEM || type == EntityType.MUSHROOM_COW
                || type == EntityType.OCELOT || type == EntityType.PIG || type == EntityType.SHEEP
                || type == EntityType.SNOWMAN || type == EntityType.SQUID || type == EntityType.VILLAGER
                || type == EntityType.WOLF;
    }
}
