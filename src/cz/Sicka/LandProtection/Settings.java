package cz.Sicka.LandProtection;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;

import cz.Sicka.Core.Core;
import cz.Sicka.Core.Utils.Configuration.Configuration;
import cz.Sicka.Core.Utils.Configuration.DefaultConfig;
import cz.Sicka.LandProtection.Flags.Flag;
import cz.Sicka.LandProtection.Flags.FlagManager;

public class Settings {
	private static int MaxY = 255;
	private static int MinY = 0;
	
	private int maxExpanse = 25921;
	private int minWidth = 40;
	private int minLength = 40;
	private int minHeight = 60;
	
	private Configuration conf;
	private FileConfiguration config;
	
	private String defaultEnterMessage;
	private String defaultLeaveMessage;
	
	private Map<Flag, Boolean> deffFlags;
	private Map<String, Boolean> defFlags = new HashMap<String, Boolean>();
	
	public Settings(LandProtection instance){
		DefaultConfig configuration = new DefaultConfig(instance, "Configuration.yml");
		configuration.saveDefaultConfig();
		this.conf = new Configuration(new File(LandProtection.getInstance().getDataFolder(), "Configuration.yml"));
		this.config = conf.getConfig();

		Map<String, Object> arF = config.getConfigurationSection("DefaultAreaSettings.AreaFlags").getValues(false);
		deffFlags = new HashMap<Flag, Boolean>();
		for(String flag : arF.keySet()){
			boolean bool = (boolean) arF.get(flag);
			if(!FlagManager.isFlagExist(flag)){
				Core.logMessage(LandProtection.getInstance(), Level.WARNING, "&4Chyba! &6Vlajka "  + flag + " neexistuje!");
			}else{
				deffFlags.put(FlagManager.getFlag(flag), bool);
				defFlags.put(flag, bool);
			}
		}
		
		this.defaultEnterMessage = this.config.getString("DefaultAreaSettings.DefaultEnterMessage");
		this.defaultLeaveMessage = this.config.getString("DefaultAreaSettings.DefaultLeaveMessage");
		
		this.minHeight = this.config.getInt("DefaultAreaSettings.MinHeight");
		this.minLength = this.config.getInt("DefaultAreaSettings.MinLength");
		this.minWidth = this.config.getInt("DefaultAreaSettings.MinWidth");
	}
	
	public Map<Flag, Boolean> getDefaultAreaFlags(){
		return this.deffFlags;
	}
	
	public Map<String, Boolean> getDefaultAreaFlagsSetings(){
		return this.defFlags;
	}

	public String getDefaultEnterMessage() {
		return defaultEnterMessage;
	}

	public String getDefaultLeaveMessage() {
		return defaultLeaveMessage;
	}

	public int getMinWidth() {
		return minWidth;
	}

	public int getMinLength() {
		return minLength;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public int getMaxExpanse() {
		return maxExpanse;
	}
	
	public static int getMinY() {
		return MinY;
	}
	
	public static int getMaxY() {
		return MaxY;
	}
}
