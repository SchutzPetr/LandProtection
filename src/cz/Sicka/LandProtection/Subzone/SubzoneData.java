package cz.Sicka.LandProtection.Subzone;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;

import cz.Sicka.Core.User.OfflineUser;
import cz.Sicka.Core.Utils.Configuration.Configuration;
import cz.Sicka.LandProtection.LandProtection;
import cz.Sicka.LandProtection.Flags.Flag;
import cz.Sicka.LandProtection.Flags.FlagManager;
import cz.Sicka.LandProtection.Flags.Flags;
import cz.Sicka.LandProtection.Flags.LSFlags;
import cz.Sicka.LandProtection.Land.Land;

public abstract class SubzoneData {
	private final File subzoneFile;
	private final FileConfiguration config;
	private final Configuration configuration;
	
	private int highX;
	private int highY;
	private int highZ;
    
	private int lowX;
	private int lowY;
	private int lowZ;
	
	private String subzoneName;
	private final Land mainLand;
	
	private final LSFlags lsFlags;
	
	private String enterMessage;
	private String leaveMessage;
	private long creationDate;
	private String displayName;

	public SubzoneData(Land mainLand, String subzoneName){
		this.subzoneFile = LandProtection.getDataManager().getSubzoneFile(mainLand.getName() + "_" + subzoneName);
		configuration = new Configuration(subzoneFile);
		config = configuration.getConfig();
		
		this.subzoneName = subzoneName;
		this.mainLand = mainLand;
		
		this.displayName = config.getString("SubzoneName");
		
		this.highX = config.getInt("Location.HighX");
		this.highY = config.getInt("Location.HighY");
		this.highZ = config.getInt("Location.HighZ");
		
		this.lowX = config.getInt("Location.LowX");
		this.lowY = config.getInt("Location.LowY");
		this.lowZ = config.getInt("Location.LowZ");
		
		this.enterMessage = config.getString("EnterMessage");
		this.leaveMessage = config.getString("LeaveMessage");
		
		this.creationDate = config.getLong("CreationDate");
		
		Map<String, Object> arF = config.getConfigurationSection("AreaFlags").getValues(false);
		Map<Flag, Boolean> areaF = new HashMap<Flag, Boolean>();
		for(String flag : arF.keySet()){
			boolean bool = (boolean) arF.get(flag);
			areaF.put(FlagManager.getFlag(flag), bool);
		}
		Flags aFlags = new Flags(areaF);
		Map<UUID, Flags> playerFlags = new HashMap<UUID, Flags>();
		
		for(String playerUUID : config.getConfigurationSection("PlayersFlags").getKeys(false)){
			Map<String, Object> plF = config.getConfigurationSection("PlayersFlags." + playerUUID).getValues(false);
			Map<Flag, Boolean> playerF = new HashMap<Flag, Boolean>();
			for(String flag : plF.keySet()){
				boolean bool = (boolean) plF.get(flag);
				playerF.put(FlagManager.getFlag(flag), bool);
			}
			Flags pflags = new Flags(playerF);
			playerFlags.put(UUID.fromString(playerUUID), pflags);
		}
		
		
		lsFlags = new LSFlags(playerFlags, aFlags);
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getName() {
		return subzoneName;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public FileConfiguration getConfig() {
		return config;
	}
	
	public long getCreationDateMillis() {
		return creationDate;
	}

	public int getHighX() {
		return highX;
	}

	public int getHighY() {
		return highY;
	}

	public int getHighZ() {
		return highZ;
	}

	public int getLowX() {
		return lowX;
	}

	public int getLowY() {
		return lowY;
	}

	public int getLowZ() {
		return lowZ;
	}
	
	public Land getLand() {
		return mainLand;
	}

	public LSFlags getLSFlags() {
		return lsFlags;
	}
	
	public String getEnterMessage() {
		return enterMessage;
	}

	public String getLeaveMessage() {
		return leaveMessage;
	}
	
	//TODO: dokonèit!
	/**
	 * NOTE: Metoda neni dokoncena
	 */
	public boolean rename(String newName){
		return false;
	}
	
	public void setEnterMessage(String enterMessage) {
		this.enterMessage = enterMessage;
		
		this.config.set("EnterMessage", enterMessage);
		this.configuration.saveConfig();
	}

	public void setLeaveMessage(String leaveMessage) {
		this.leaveMessage = leaveMessage;
		
		this.config.set("LeaveMessage", leaveMessage);
		this.configuration.saveConfig();
	}

	protected void setHighX(int highX) {
		this.highX = highX;
		config.set("Location.HighX", highX);
		this.configuration.saveConfig();
	}

	protected void setHighY(int highY) {
		this.highY = highY;
		config.set("Location.HighY", highY);
		this.configuration.saveConfig();
	}

	protected void setHighZ(int highZ) {
		this.highZ = highZ;
		config.set("Location.HighZ", highZ);
		this.configuration.saveConfig();
	}

	protected void setLowX(int lowX) {
		this.lowX = lowX;
		config.set("Location.LowX", lowX);
		this.configuration.saveConfig();
	}

	protected void setLowY(int lowY) {
		this.lowY = lowY;
		config.set("Location.LowY", lowY);
		this.configuration.saveConfig();
	}

	protected void setLowZ(int lowZ) {
		this.lowZ = lowZ;
		config.set("Location.LowZ", lowZ);
		this.configuration.saveConfig();
	}
	
	public void setPlayerFlag(OfflineUser user, Flag flag, boolean value) {
		setPlayerFlag(user.getUniqueId(), flag, value);
	}
	
	public void removeAreaFlag(Flag flag){
		this.config.set("AreaFlags." + flag.getName(), null);
		this.getLSFlags().getAreaFlags().getFlagsAndValues().remove(flag);
		this.configuration.saveConfig();
	}
	
	public void removePlayerFlag(UUID user, Flag flag){
		this.config.set("PlayersFlags." + user.toString() + "." + flag.getName(), null);
		this.getLSFlags().getPlayerFlags(user).getFlagsAndValues().remove(flag);
		this.configuration.saveConfig();
	}
	
	public void setPlayerFlag(UUID user, Flag flag, boolean value) {
		this.config.set("PlayersFlags." + user.toString() + "." + flag.getName(), value);
		this.getLSFlags().setPlayerFlag(user, flag, value);
		this.configuration.saveConfig();
	}
	
	public void setAreaFlag(Flag flag, boolean value) {
		this.config.set("AreaFlags." + flag.getName(), value);
		this.getLSFlags().setAreaFlag(flag, value);
		this.configuration.saveConfig();
	}
}
