package cz.Sicka.LandProtection.WorldArea;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;

import cz.Sicka.Core.Utils.Configuration.Configuration;
import cz.Sicka.LandProtection.LandProtection;
import cz.Sicka.LandProtection.Flags.Flag;
import cz.Sicka.LandProtection.Flags.FlagManager;
import cz.Sicka.LandProtection.Flags.Flags;

public class WorldData {
	private final File worldFile;
	private final FileConfiguration config;
	private final Configuration configuration;
	
	private final Flags flags;
	private String worldName;
	private boolean disableClaim;
	
	private int buildPerInterval_Interval;
	private int buildPerInterval_Blocks;
	private boolean buildPerInterval_Enabled;
	
	private String enterMessage;
	private String leaveMessage;
	
	public WorldData(String worldName){
		this.worldFile = LandProtection.getDataManager().getWorldFile(worldName);
		configuration = new Configuration(worldFile);
		config = configuration.getConfig();
		
		Map<String, Object> arF = config.getConfigurationSection("Flags").getValues(false);
		Map<Flag, Boolean> areaF = new HashMap<Flag, Boolean>();
		for(String flag : arF.keySet()){
			boolean bool = (boolean) arF.get(flag);
			areaF.put(FlagManager.getFlag(flag), bool);
		}
		this.flags = new Flags(areaF);
		
		this.worldName = config.getString("WorldName", worldName);
		this.disableClaim = config.getBoolean("DisableClaim", false);
		
		this.buildPerInterval_Enabled = config.getBoolean("BuildPerInterval.Enable", true);
		this.buildPerInterval_Interval = config.getInt("BuildPerInterval.Interval", 0);
		this.buildPerInterval_Blocks = config.getInt("BuildPerInterval.Blocks", 0);
		
		this.enterMessage = this.config.getString("EnterMessage", "Nyni se nachazite ve volne prirode");
		this.leaveMessage = this.config.getString("LeaveMessage",  "Opoustite volnou prirodu");
		
	}
	
	public String getLeaveMessage() {
		return leaveMessage;
	}

	public String getEnterMessage() {
		return enterMessage;
	}

	public Flags getFlags() {
		return flags;
	}

	public File getWorldFile() {
		return worldFile;
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public String getWorldName() {
		return worldName;
	}

	public boolean isDisableClaim() {
		return disableClaim;
	}
	
	public boolean isEnableBuildPerInterval() {
		return buildPerInterval_Enabled;
	}

	public int getBuildPerInterval_Blocks() {
		return buildPerInterval_Blocks;
	}

	public int getBuildPerInterval_Interval() {
		return buildPerInterval_Interval;
	}
	
	public void setWorldAreaName(String worldName) {
		this.worldName = worldName;
		config.set("WorldName", worldName);
		this.configuration.saveConfig();
	}
	
	public void setEnableBuildPerInterval(boolean enabled) {
		this.buildPerInterval_Enabled = enabled;
		config.set("BuildPerInterval.Enable", enabled);
		this.configuration.saveConfig();
	}

	public void setDisableClaim(boolean disableClaim) {
		this.disableClaim = disableClaim;
		config.set("DisableClaim", disableClaim);
		this.configuration.saveConfig();
	}

	public void setBuildPerInterval_Blocks(int buildPerInterval_Blocks) {
		this.buildPerInterval_Blocks = buildPerInterval_Blocks;
		config.set("BuildPerInterval.Blocks", buildPerInterval_Blocks);
		this.configuration.saveConfig();
	}

	public void setBuildPerInterval_Interval(int interval) {
		this.buildPerInterval_Interval = interval;
		config.set("BuildPerInterval.Interval", interval);
		this.configuration.saveConfig();
	}
	
	public void setFlag(Flag flag, boolean value) {
		this.config.set("Flags." + flag.getName(), value);
		this.getFlags().setFlag(flag, value);
	}
	
}
