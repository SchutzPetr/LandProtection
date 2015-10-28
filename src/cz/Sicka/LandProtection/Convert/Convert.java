package cz.Sicka.LandProtection.Convert;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import cz.Sicka.Core.Utils.Configuration.Configuration;
import cz.Sicka.LandProtection.LandProtection;

public class Convert {
	
	public static void convertAllArea(){
		new FlagManager(null);
		new LoadArea();
		
		for(Area area : cz.Sicka.LandProtection.Convert.Manager.getAllAreas().values()){
			convert(area);
		}
	}
	
	
	public static void convert(Area area){
		File landFolder = LandProtection.getDataManager().getLandFolder();
		File landFile = new File(landFolder, area.getAreaName().toLowerCase() + ".yml");
		if(!landFile.exists()){
			try {
				landFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Configuration conf = new Configuration(landFile);
		FileConfiguration config = conf.getConfig();
		config.set("Version", 1);
		config.set("AreaName", area.getAreaName());
		
		config.set("Location.HighX", area.getHighX());
		config.set("Location.HighY", area.getHighY());
		config.set("Location.HighZ", area.getHighZ());
		
		config.set("Location.LowX", area.getLowX());
		config.set("Location.LowY", area.getLowY());
		config.set("Location.LowZ", area.getLowZ());
		config.set("Location.World", area.getWorldName());
		
		config.set("TPLocation.X", area.getTeleportLocation().getBlockX());
		config.set("TPLocation.Y", area.getTeleportLocation().getBlockY());
		config.set("TPLocation.Z", area.getTeleportLocation().getBlockZ());
				
		config.set("CreationDate", area.getCreationDate());
		config.set("Owner", area.getOwnerUniqueId().toString());
		config.set("EnterMessage", LandProtection.getSettings().getDefaultEnterMessage());
		config.set("LeaveMessage",LandProtection.getSettings().getDefaultLeaveMessage());
		
		config.createSection("AreaFlags", area.getAreaFlags().getFlagsAndValues());
		config.createSection("PlayersFlags");
		
		for(UUID uuid : area.getAreaPlayerFlags().getPlayerFlags().keySet()){
			config.createSection("PlayersFlags." + uuid.toString(), area.getAreaPlayerFlags().getPlayerFlags(uuid).getFlagsAndValues());
		}
		conf.saveConfig();
	}
}
