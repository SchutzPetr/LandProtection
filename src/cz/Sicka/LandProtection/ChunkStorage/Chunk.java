package cz.Sicka.LandProtection.ChunkStorage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Subzone.Subzone;

public class Chunk {
	private final int x;
	private final int z;
	private final String chunkName;
	
	private final List<Land> landList = new ArrayList<Land>();
	private final List<Subzone> subzoneList = new ArrayList<Subzone>();
	
	public Chunk(String chunkName){
		String[] s = chunkName.split(", ");
		this.x = Integer.valueOf(s[0]);
		this.z = Integer.valueOf(s[1]);
		this.chunkName = chunkName;
	}
	
	public Chunk(int x, int z){
		this.x = x;
		this.z = z;
		this.chunkName = x + ", " + z;
	}

	public String getName() {
		return chunkName;
	}
	
	public List<Land> getLands() {
		return landList;
	}

	public List<Subzone> getSubzones() {
		return subzoneList;
	}

	public boolean add(Land land){
		if(this.landList.contains(land)){
			return false;
		}
		return this.landList.add(land);
	}
	
	public boolean add(Subzone subzone){
		if(this.subzoneList.contains(subzone)){
			return false;
		}
		return this.subzoneList.add(subzone);
	}
	
	public boolean remove(Subzone subzone){
		return this.subzoneList.remove(subzone);
	}
	
	public boolean remove(Land land){
		return this.landList.remove(land);
	}
	
	public boolean contains(Subzone subzone){
		return this.subzoneList.remove(subzone);
	}
	
	public boolean contains(Land land){
		return this.landList.contains(land);
	}
	
	public Land getLandAreaByLocation(Location loc){
		for(Land land: landList){
			if(land.containsLocation(loc)){
				return land;
			}else{
				continue;
			}
		}
		return null;
	}
	
	public Subzone getSubzoneByLocation(Location loc){
		for(Subzone subzone: subzoneList){
			if(subzone.containsLocation(loc)){
				return subzone;
			}else{
				continue;
			}
		}
		return null;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}
}
