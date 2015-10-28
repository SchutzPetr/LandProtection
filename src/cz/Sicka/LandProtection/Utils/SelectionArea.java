package cz.Sicka.LandProtection.Utils;

import org.bukkit.Location;
import org.bukkit.World;

import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Subzone.Subzone;

public class SelectionArea {
	private int highX;
	private int highY;
	private int highZ;
    
	private int lowX;
	private int lowY;
	private int lowZ;
	
	private World world;
	
	public SelectionArea(Land land){
		Location firstPoint = land.getLowLocation();
		Location secondPoint = land.getHighLocation();
		this.world = firstPoint.getWorld();
		if(firstPoint.getBlockX() > secondPoint.getBlockX()){
			highX = firstPoint.getBlockX();
			lowX = secondPoint.getBlockX();
		}else{
			highX = secondPoint.getBlockX();
			lowX = firstPoint.getBlockX();
		}
		if(firstPoint.getBlockY() > secondPoint.getBlockY()){
			highY = firstPoint.getBlockY();
			lowY = secondPoint.getBlockY();
		}else{
			highY = secondPoint.getBlockY();
			lowY = firstPoint.getBlockY();
		}
		if(firstPoint.getBlockZ() > secondPoint.getBlockZ()){
			highZ = firstPoint.getBlockZ();
			lowZ = secondPoint.getBlockZ();
		}else{
			highZ = secondPoint.getBlockZ();
			lowZ = firstPoint.getBlockZ();
		}
	}
	
	public SelectionArea(Subzone sub){
		Location firstPoint = sub.getLowLocation();
		Location secondPoint = sub.getHighLocation();
		this.world = firstPoint.getWorld();
		if(firstPoint.getBlockX() > secondPoint.getBlockX()){
			highX = firstPoint.getBlockX();
			lowX = secondPoint.getBlockX();
		}else{
			highX = secondPoint.getBlockX();
			lowX = firstPoint.getBlockX();
		}
		if(firstPoint.getBlockY() > secondPoint.getBlockY()){
			highY = firstPoint.getBlockY();
			lowY = secondPoint.getBlockY();
		}else{
			highY = secondPoint.getBlockY();
			lowY = firstPoint.getBlockY();
		}
		if(firstPoint.getBlockZ() > secondPoint.getBlockZ()){
			highZ = firstPoint.getBlockZ();
			lowZ = secondPoint.getBlockZ();
		}else{
			highZ = secondPoint.getBlockZ();
			lowZ = firstPoint.getBlockZ();
		}
	}
	
	public SelectionArea(Location firstPoint, Location secondPoint){
		this.world = firstPoint.getWorld();
		if(firstPoint.getBlockX() > secondPoint.getBlockX()){
			highX = firstPoint.getBlockX();
			lowX = secondPoint.getBlockX();
		}else{
			highX = secondPoint.getBlockX();
			lowX = firstPoint.getBlockX();
		}
		if(firstPoint.getBlockY() > secondPoint.getBlockY()){
			highY = firstPoint.getBlockY();
			lowY = secondPoint.getBlockY();
		}else{
			highY = secondPoint.getBlockY();
			lowY = firstPoint.getBlockY();
		}
		if(firstPoint.getBlockZ() > secondPoint.getBlockZ()){
			highZ = firstPoint.getBlockZ();
			lowZ = secondPoint.getBlockZ();
		}else{
			highZ = secondPoint.getBlockZ();
			lowZ = firstPoint.getBlockZ();
		}
	}
	
	public Location getCenter() {
        return new Location(getWorld(), (getHighX() + getLowX()) / 2, (getHighY() + getLowY()) / 2, (getHighZ() + getLowZ()) / 2);
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
	
	public World getWorld() {
		return world;
	}
	
	public int getExpanse(){
		int xsize = (this.getHighX() - this.getLowX() ) + 1;
	    int zsize = (this.getHighZ()  - this.getLowZ() ) + 1;
	    return xsize * zsize;
	}
	
	public int getSize(){
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
		return new Location(this.getWorld(), this.getHighX() , this.getHighY() , this.getHighZ() );
	}

	public Location getLowLocation() {
		return new Location(this.getWorld(), this.getLowX() , this.getLowY() , this.getLowZ() );
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
}
