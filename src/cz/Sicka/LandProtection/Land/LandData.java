package cz.Sicka.LandProtection.Land;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import cz.Sicka.Core.Core;
import cz.Sicka.Core.User.OfflineUser;
import cz.Sicka.Core.User.UManager;
import cz.Sicka.Core.Utils.Configuration.Configuration;
import cz.Sicka.LandProtection.LandProtection;
import cz.Sicka.LandProtection.Flags.Flag;
import cz.Sicka.LandProtection.Flags.FlagManager;
import cz.Sicka.LandProtection.Flags.Flags;
import cz.Sicka.LandProtection.Flags.LSFlags;

public abstract class LandData {
	private File landFile;
	private FileConfiguration config;
	private Configuration configuration;
	
	private final List<String> subzoneList;
	
	private int highX;
	private int highY;
	private int highZ;
    
	private int lowX;
	private int lowY;
	private int lowZ;
	
	private final String worldName;
	
	private Location teleportLocation;
	
	private String enterMessage;
	private String leaveMessage;
	
	private OfflineUser owner;
	
	private final LSFlags lsFlags;
	
	private String landName;
	private final long creationDate;
	
	private String displayName;
	
	public LandData(String landName){
		this.landName = landName;
		landFile = LandProtection.getDataManager().getLandFile(landName);
		configuration = new Configuration(landFile);
		
		config = configuration.getConfig();
		
		this.displayName = config.getString("AreaName");
		
		this.highX = config.getInt("Location.HighX");
		this.highY = config.getInt("Location.HighY");
		this.highZ = config.getInt("Location.HighZ");
		
		this.lowX = config.getInt("Location.LowX");
		this.lowY = config.getInt("Location.LowY");
		this.lowZ = config.getInt("Location.LowZ");
		
		this.worldName = config.getString("Location.World");
		
		this.teleportLocation = new Location(LandProtection.getInstance().getServer().getWorld(this.worldName), config.getInt("TPLocation.X"), config.getInt("TPLocation.Y"), config.getInt("TPLocation.Z"));
		
		this.creationDate = config.getLong("CreationDate");
		
		this.owner = UManager.getOfflineUser(UUID.fromString(config.getString("Owner")));
		
		this.enterMessage = config.getString("EnterMessage");
		this.leaveMessage = config.getString("LeaveMessage");
		
		this.subzoneList = config.getStringList("Subzones");
		
		Map<String, Object> arF = config.getConfigurationSection("AreaFlags").getValues(false);
		Map<Flag, Boolean> areaF = new HashMap<Flag, Boolean>();
		for(String flag : arF.keySet()){
			boolean bool = (boolean) arF.get(flag);
			if(!FlagManager.isFlagExist(flag)){
				Core.logMessage(LandProtection.getInstance(), Level.WARNING, "&4Chyba! &6Vlajka "  + flag + " neexistuje!");
			}else{
				areaF.put(FlagManager.getFlag(flag), bool);
			}
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
	
	public void saveConfig(){
		this.configuration.saveConfig();
	}

	public File getLandFile() {
		return landFile;
	}

	public FileConfiguration getConfig() {
		return config;
	}
	
	public Configuration getConfiguration() {
		return configuration;
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

	public String getWorldName() {
		return worldName;
	}

	public String getEnterMessage() {
		return enterMessage;
	}

	public String getLeaveMessage() {
		return leaveMessage;
	}

	public OfflineUser getOwner() {
		return owner;
	}

	public LSFlags getLSFlags() {
		return lsFlags;
	}
	
	public String getName() {
		return landName;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public Location getTeleportLocation() {
		return teleportLocation;
	}

	public long getCreationDateMillis() {
		return creationDate;
	}

	public void setTeleportLocation(Location teleportLocation) {
		this.teleportLocation = teleportLocation;
		
		this.config.set("TPLocation.X", teleportLocation.getBlockX());
		this.config.set("TPLocation.Y", teleportLocation.getBlockY());
		this.config.set("TPLocation.Z", teleportLocation.getBlockZ());
		this.configuration.saveConfig();
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

	public void setOwner(OfflineUser owner) {
		this.owner = owner;
		
		this.config.set("Owner", owner.getUniqueId());
		this.configuration.saveConfig();
	}

	public boolean rename(String landName) {
		if(LandProtection.getDataManager().renameLandFile(this, landName)){
			this.landName = landName.toLowerCase();
			landFile = LandProtection.getDataManager().getLandFile(this.landName);
			configuration = new Configuration(landFile);
			config = configuration.getConfig();
			this.displayName = landName;
			this.config.set("AreaName", landName);
			this.configuration.saveConfig();
			return true;
		}
		return false;
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
	
	public List<String> getListOfSubzones(){
		return this.subzoneList;
	}
	
	protected void addNewSubzone(String subzoneName){
		this.subzoneList.add(subzoneName);
		this.getConfig().set("Subzones", this.subzoneList);
		this.configuration.saveConfig();
	}
}
