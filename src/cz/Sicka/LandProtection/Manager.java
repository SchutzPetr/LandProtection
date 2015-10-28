package cz.Sicka.LandProtection;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import cz.Sicka.Core.User.User;
import cz.Sicka.Core.Utils.Configuration.Configuration;
import cz.Sicka.LandProtection.ChunkStorage.Chunk;
import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Subzone.Subzone;
import cz.Sicka.LandProtection.Utils.LandProtectionUtils;
import cz.Sicka.LandProtection.Utils.SelectionArea;
import cz.Sicka.LandProtection.WorldArea.WorldArea;

public class Manager {
	protected final LandProtection plugin;
	private final Map<String, Land> landList = new HashMap<String, Land>();
	private final Map<World, WorldArea> worldAreaList = new HashMap<World, WorldArea>();
	
	public Manager(LandProtection instance){
		this.plugin = instance;
		loadLansSubzonesAndWorldAreas();
	}
	
	private void loadLand(String landName){
		landName = landName.toLowerCase();
		Land loadLand = new Land(landName);
		this.landList.put(landName, loadLand);
		loadLand.loadSubzones();
	}
	
	private void loadLansSubzonesAndWorldAreas(){
		for(File file : LandProtection.getDataManager().getLandFolder().listFiles()){
			String landName = FilenameUtils.removeExtension(file.getName());
			Land loadLand = new Land(landName);
			this.landList.put(landName, loadLand);
			loadLand.loadSubzones();
		}
		for(World world : LandProtection.getInstance().getServer().getWorlds()){
			File worldFile = new File(LandProtection.getDataManager().getWorldFolder(), world.getName() + ".yml");
			if(!worldFile.exists()){
				try {
					worldFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Configuration conf = new Configuration(worldFile);
				FileConfiguration config = conf.getConfig();
				
				config.set("Version", 2);
				config.set("WorldName", world.getName());
				config.set("DisableClaim", false);
				config.set("BuildPerInterval.Enable", true);
				config.set("BuildPerInterval.Interval", 10);
				config.set("BuildPerInterval.Blocks", 20);
				config.set("EnterMessage", "Nyni se nachazite ve volne prirode");
				config.set("LeaveMessage", "Opoustite volnou prirodu");
				config.createSection("Flags");
				
				conf.saveConfig();
			}else{
				Configuration conf = new Configuration(worldFile);
				FileConfiguration config = conf.getConfig();
				if(config.getInt("Version") < 2){
					worldFile.delete();
					
					if(!worldFile.exists()){
						try {
							worldFile.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						conf = new Configuration(worldFile);
						config = conf.getConfig();
						
						config.set("Version", 2);
						config.set("WorldName", world.getName());
						config.set("DisableClaim", false);
						config.set("BuildPerInterval.Enable", true);
						config.set("BuildPerInterval.Interval", 10);
						config.set("BuildPerInterval.Blocks", 20);
						config.set("EnterMessage", "&5Nyni se nachazite ve volne prirode");
						config.set("LeaveMessage", "&5Opoustite volnou prirodu");
						config.createSection("Flags");
						
						conf.saveConfig();
					}
				}
			}
			WorldArea wa = new WorldArea(world);
			this.worldAreaList.put(world, wa);
		}
	}
	
	public Boolean rename(Land land, String newName){
		String oldName = land.getName();
		if(land.rename(newName)){
			this.landList.remove(oldName);
			this.landList.put(newName.toLowerCase(), land);
			return true;
		}
		return false;
	}
	
	public Boolean isLandExist(String landName){
		return this.landList.containsKey(landName.toLowerCase());
	}
	
	public Land getLand(String landName){
		return this.landList.get(landName.toLowerCase());
	}
	
	public WorldArea getWorldArea(String world){
		return this.worldAreaList.get(LandProtection.getInstance().getServer().getWorld(world));
	}
	public WorldArea getWorldArea(World world){
		return this.worldAreaList.get(world);
	}
	
	public Collection<Land> getLands() {
		return this.landList.values();
	}
	
	public void claimLand(User owner, String landName, SelectionArea selArea){
		File landFolder = LandProtection.getDataManager().getLandFolder();
		File landFile = new File(landFolder, landName.toLowerCase() + ".yml");
		try {
			landFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Configuration conf = new Configuration(landFile);
		FileConfiguration config = conf.getConfig();
		config.set("Version", 1);
		config.set("AreaName", landName);
		
		config.set("Location.HighX", selArea.getHighX());
		config.set("Location.HighY", selArea.getHighY());
		config.set("Location.HighZ", selArea.getHighZ());
		
		config.set("Location.LowX", selArea.getLowX());
		config.set("Location.LowY", selArea.getLowY());
		config.set("Location.LowZ", selArea.getLowZ());
		config.set("Location.World", selArea.getWorld().getName());
		
		Location tel = LandProtectionUtils.getDefaultTeleportLocation(selArea);
		
		config.set("TPLocation.X", tel.getBlockX());
		config.set("TPLocation.Y", tel.getBlockY());
		config.set("TPLocation.Z", tel.getBlockZ());
				
		config.set("CreationDate", System.currentTimeMillis());
		config.set("Owner", owner.getUniqueId().toString());
		config.set("EnterMessage", LandProtection.getSettings().getDefaultEnterMessage());
		config.set("LeaveMessage",  LandProtection.getSettings().getDefaultLeaveMessage());
		
		config.createSection("AreaFlags", LandProtection.getSettings().getDefaultAreaFlagsSetings());
		config.createSection("PlayersFlags");
		
		conf.saveConfig();
		
		this.loadLand(landName);
		this.plugin.getLandDynmap().show(this.getLand(landName));
	}
	
	public void remove(Land land){
		land.getOwner().removeLand(land.getName());
		LandProtection.getDataManager().getLandFile(land.getName().toLowerCase()).delete();
		this.landList.remove(land.getName().toLowerCase());
		LandProtection.getChunkStorage().getStorageByWorld(land.getWorld()).remove(land);
		this.plugin.getLandDynmap().removeFromDynmap(land);
	}
	
	public void remove(Subzone subzone){
		LandProtection.getDataManager().getSubzoneFile(subzone.getName().toLowerCase()).delete();
		subzone.getLand().removeSubzone(subzone);
		LandProtection.getChunkStorage().getStorageByWorld(subzone.getLand().getWorld()).remove(subzone);
		this.plugin.getLandDynmap().removeFromDynmap(subzone);
	}
	
	public static Land getLandByLocation(Location loc){
		Chunk chunk = LandProtection.getChunkStorage().getStorageByWorld(loc.getWorld()).getChunk(loc.getChunk().getX() + ", " + loc.getChunk().getZ());
		if(chunk != null){
			for(Land l : chunk.getLands()){
				if(!l.containsLocation(loc)){
					continue;
				}
				return l;
			}
		}
		return null;
	}
	
	public static Subzone getSubzoneByLocation(Location loc, Land land){
		for(Subzone sub : land.getSubzones()){
			if(!sub.containsLocation(loc)){
				continue;
			}
			return sub;
		}
		return null;
	}
}
