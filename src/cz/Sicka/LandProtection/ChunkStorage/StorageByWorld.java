package cz.Sicka.LandProtection.ChunkStorage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.World;

import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Subzone.Subzone;
import cz.Sicka.LandProtection.Utils.CalculateChunks;

public class StorageByWorld {
	private final Map<String, Chunk> chunks = new HashMap<String, Chunk>();
	private final World world;
	
	public StorageByWorld(World world){
		this.world = world;
	}

	public World getWorld() {
		return world;
	}
	
	public void add(Land land){
		List<String> calculateChunks = CalculateChunks.calculateChunks(land.getHighX(), land.getHighZ(), land.getLowX(), land.getLowZ());
		for(String chunkName : calculateChunks){
			getChunk(chunkName, true).add(land);
		}
		calculateChunks = null;
	}
	
	public void add(Subzone subzone){
		List<String> calculateChunks = CalculateChunks.calculateChunks(subzone.getHighX(), subzone.getHighZ(), subzone.getLowX(), subzone.getLowZ());
		for(String chunkName : calculateChunks){
			getChunk(chunkName, true).add(subzone);
		}
		calculateChunks = null;
	}
	
	public void remove(Land land){
		List<String> calculateChunks = CalculateChunks.calculateChunks(land.getHighX(), land.getHighZ(), land.getLowX(), land.getLowZ());
		for(String chunkName : calculateChunks){
			getChunk(chunkName, true).remove(land);
		}
		calculateChunks = null;
	}
	
	public void remove(Subzone subzone){
		List<String> calculateChunks = CalculateChunks.calculateChunks(subzone.getHighX(), subzone.getHighZ(), subzone.getLowX(), subzone.getLowZ());
		for(String chunkName : calculateChunks){
			getChunk(chunkName, true).remove(subzone);
		}
		calculateChunks = null;
	}
	
	public Chunk getChunk(String chunkName, boolean putIfAbsent){
		if(putIfAbsent){
			this.chunks.putIfAbsent(chunkName, new Chunk(chunkName));
		}
		return this.chunks.get(chunkName);
	}
	
	public Chunk getChunk(String chunkName){
		return getChunk(chunkName, false);
	}
}
