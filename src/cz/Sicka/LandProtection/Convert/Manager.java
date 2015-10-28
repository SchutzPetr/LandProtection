package cz.Sicka.LandProtection.Convert;

import java.util.HashMap;
import java.util.Map;

public class Manager {
	private static Map<String, Area> areas = new HashMap<String, Area>();
	
	/**
	 * Note: Init when server start!
	 */
	public static void Initialization(){
		getAllAreas().clear();
	}
	
	public static void addArena(Area area){
		addAreaToList(area);
	}
	
	private static void addAreaToList(Area area){
		String lowerCaseName = area.getAreaName().toLowerCase();
		if(!getAllAreas().containsKey(lowerCaseName)){
			getAllAreas().put(lowerCaseName, area);
		}
	}

	public static Map<String, Area> getAllAreas() {
		return areas;
	}
}
