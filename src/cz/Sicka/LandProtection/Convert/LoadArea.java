package cz.Sicka.LandProtection.Convert;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import cz.Sicka.Core.Utils.Configuration.Configuration;
import cz.Sicka.LandProtection.DataManager;
import cz.Sicka.LandProtection.LandProtection;

public class LoadArea {
	private Map<String, Configuration> worldConfig = new HashMap<String, Configuration>();
	
	public LoadArea(){
		File plugins = LandProtection.getInstance().getDataFolder().getParentFile();
		File dataF = new File(plugins.getPath()+File.separator+"AreaProtection");
		File worldFolder = new File(new File(dataF, "Save"), "Worlds");
		if (!worldFolder.isDirectory()) {
            worldFolder.mkdirs();
        }
		for(World world : LandProtection.getInstance().getServer().getWorlds()){
			File worldFile = new File(worldFolder, "areas_" + world.getName() + ".yml");
			boolean newFile = false;
			if(!worldFile.exists()){
				try {
					worldFile.createNewFile();
					newFile = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try{
				Configuration conf = new Configuration(worldFile);
				if(newFile) {
					conf.getConfig().set("Version", DataManager.LandFile_Version);
					conf.getConfig().createSection("Areas");
					conf.saveConfig();
				}
				this.worldConfig.put(world.getName(), conf);
			} catch(Exception e){
				
			}
		}
		
		loadAllAreas();
	}
	
	public void loadAllAreas() {
		for(String world : this.worldConfig.keySet()){
			loadAreas(this.worldConfig.get(world));
		}	
	}

	public void loadAreas(Configuration conf){
		for(String areaName : conf.getConfig().getConfigurationSection("Areas").getKeys(false)){
			loadArea(areaName, conf);
		}
	}
	
	public void loadArea(String areaName, String world){
		loadArea(areaName, this.worldConfig.get(world));
	}
	
	public void loadArea(String areaName, Configuration conf){
		try{
			int x1 = conf.getConfig().getInt("Areas." + areaName + ".Data.Location.LowX");
			int y1 = conf.getConfig().getInt("Areas." + areaName + ".Data.Location.LowY");
			int z1 = conf.getConfig().getInt("Areas." + areaName + ".Data.Location.LowZ");
			
			int x2 = conf.getConfig().getInt("Areas." + areaName + ".Data.Location.HighX");
			int y2 = conf.getConfig().getInt("Areas." + areaName + ".Data.Location.HighY");
			int z2 = conf.getConfig().getInt("Areas." + areaName + ".Data.Location.HighZ");
			
			int telx = conf.getConfig().getInt("Areas." + areaName + ".Data.TPLocation.X");
			int tely = conf.getConfig().getInt("Areas." + areaName + ".Data.TPLocation.Y");
			int telz = conf.getConfig().getInt("Areas." + areaName + ".Data.TPLocation.Z");
			
			String world = conf.getConfig().getString("Areas." + areaName + ".Data.Location.World");
			
			Location teleportLocation = new Location(Bukkit.getWorld(world), telx, tely, telz);

			String owner = conf.getConfig().getString("Areas." + areaName + ".Data.Owner");
			
			String enterMessage = conf.getConfig().getString("Areas." + areaName + ".Data.EnterMessage");
			String leaveMessage = conf.getConfig().getString("Areas." + areaName + ".Data.LeaveMessage");
			
			Area area = new Area(areaName, owner, x1, y1, z1, x2, y2, z2, world);
			
			area.setEnterMessage(enterMessage);
			area.setLeaveMessage(leaveMessage);
			
			area.setTeleportLocation(teleportLocation);
			
			area.setCreationDate(conf.getConfig().getLong("Areas." + areaName + ".Data.CreationDate"));
			
			FlagsMap areaFlags = new FlagsMap();
			AreaPlayerFlags areaPlayerFlags = new AreaPlayerFlags();
			for(String aflags : conf.getConfig().getConfigurationSection("Areas." + areaName + ".Flags").getKeys(false)){
				Flag f = FlagManager.getFlag(aflags);
				areaFlags.addFlag(f, conf.getConfig().getBoolean("Areas." + areaName + ".Flags." + aflags ));
			}
			for(String players : conf.getConfig().getConfigurationSection("Areas." + areaName + ".Players").getKeys(false)){
				for(String plflags : conf.getConfig().getConfigurationSection("Areas." + areaName + ".Players." + players).getKeys(false)){
					Flag f = FlagManager.getFlag(plflags);
					areaPlayerFlags.addPlayerFlag(UUID.fromString(players), f, conf.getConfig().getBoolean("Areas." + areaName + ".Players." + players + "." + plflags));
				}
			}
			area.setAreaFlags(areaFlags);
			area.setAreaPlayerFlags(areaPlayerFlags);
			
			if(!Manager.getAllAreas().containsKey(areaName)){
				Manager.addArena(area);
			}else{
				return;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public Map<String, Configuration> getSaveAreasFiles() {
		return worldConfig;
	}
}
