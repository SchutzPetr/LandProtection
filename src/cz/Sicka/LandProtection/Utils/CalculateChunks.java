package cz.Sicka.LandProtection.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class CalculateChunks {
	
	public static List<String> calculateChunks(int x1, int z1, int x2, int z2){
		List<String> listOfChunks = new ArrayList<String>();
		if(x1 > x2){
			for(int u = x1 >> 4; u >= x2 >> 4; u--){
				if(z1 > z2){
					for(int uu = z1 >> 4; uu >= z2 >> 4; uu--){
						listOfChunks.add(u + ", " + uu);
					}
				}else{
					for(int uu = z1 >> 4; uu <= z2 >> 4; uu++){
						listOfChunks.add(u + ", " + uu);
					}
				}
			}
		}else{
			for(int u = x1 >> 4; u <= x2 >> 4; u++){
				if(z1 > z2){
					for(int uu = z1 >> 4; uu <= z2 >> 4; uu--){
						listOfChunks.add(u + ", " + uu);
					}
				}else{
					for(int uu = z1 >> 4; uu <= z2 >> 4; uu++){
						listOfChunks.add(u + ", " + uu);
					}
				}
			}
		}
		return listOfChunks;
	}
	
	public static List<String> calculateChunks(Location loc1, Location loc2){
		List<String> listOfChunks = new ArrayList<String>();
		int x1 = loc1.getBlockX();
		int z1 = loc1.getBlockZ();
		int x2 = loc2.getBlockX();
		int z2 = loc2.getBlockZ();
		if(x1 > x2){
			for(int u = x1 >> 4; u >= x2 >> 4; u--){
				if(z1 > z2){
					for(int uu = z1 >> 4; uu >= z2 >> 4; uu--){
						listOfChunks.add(u + ", " + uu);
					}
				}else{
					for(int uu = z1 >> 4; uu <= z2 >> 4; uu++){
						listOfChunks.add(u + ", " + uu);
					}
				}
			}
		}else{
			for(int u = x1 >> 4; u <= x2 >> 4; u++){
				if(z1 > z2){
					for(int uu = z1 >> 4; uu <= z2 >> 4; uu--){
						listOfChunks.add(u + ", " + uu);
					}
				}else{
					for(int uu = z1 >> 4; uu <= z2 >> 4; uu++){
						listOfChunks.add(u + ", " + uu);
					}
				}
			}
		}
		return listOfChunks;
	}
}
