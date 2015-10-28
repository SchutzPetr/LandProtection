package cz.Sicka.LandProtection.Utils;

import org.bukkit.Location;

public class Serialize {

	private int highX;
	private int lowX;
	private int highY;
	private int lowY;
	private int highZ;
	private int lowZ;

	public Serialize(int loc_1_x, int loc_1_y, int loc_1_z, int loc_2_x, int loc_2_y, int loc_2_z){
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
	
	public Serialize(Location firstPoint, Location secondPoint){
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

	public int getHighX() {
		return highX;
	}

	public int getLowX() {
		return lowX;
	}

	public int getHighY() {
		return highY;
	}

	public int getLowY() {
		return lowY;
	}

	public int getHighZ() {
		return highZ;
	}

	public int getLowZ() {
		return lowZ;
	}
}
