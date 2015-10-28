package cz.Sicka.LandProtection.ChunkStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;

import cz.Sicka.LandProtection.LandProtection;
import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Subzone.Subzone;
import cz.Sicka.LandProtection.Utils.CalculateChunks;
import cz.Sicka.LandProtection.Utils.SelectionArea;

public class ChunkStorage {
	private final Map<String, StorageByWorld> storageByWorld = new HashMap<String, StorageByWorld>();
	
	public ChunkStorage(LandProtection instance){
		for(World world : LandProtection.getInstance().getServer().getWorlds()){
			this.storageByWorld.putIfAbsent(world.getName(), new StorageByWorld(world));
		}
	}
	
	public void add(Land land){
		this.storageByWorld.get(land.getWorldName()).add(land);
	}

	
	public void add(Subzone subzone){
		this.storageByWorld.get(subzone.getLand().getWorldName()).add(subzone);
	}
	
	public StorageByWorld getStorageByWorld(World world){
		return this.storageByWorld.get(world.getName());
	}
	
	public StorageByWorld getStorageByWorld(String world){
		return this.storageByWorld.get(world);
	}
	
	public List<Land> getPotentialCollisionLand(Location loc1, Location loc2){
		List<Land> lands = new ArrayList<Land>();
		List<String> calculateChunks = CalculateChunks.calculateChunks(loc1, loc2);
		for(String chunkName: calculateChunks){
			for(Land land : getStorageByWorld(loc1.getWorld()).getChunk(chunkName, true).getLands()){
				if(lands.contains(land)){
					continue;
				}else{
					lands.add(land);
				}
			}
		}
		return lands;
	}
	
	public List<Land> getCollisionLands(SelectionArea selArea){
		List<Land> lands = new ArrayList<Land>();
		for(Land land : getPotentialCollisionLand(selArea.getLowLocation(), selArea.getHighLocation())){
			if(!selArea.checkCollision(land)){
				continue;
			}else{
				lands.add(land);
			}
		}
		return lands;
	}
}
