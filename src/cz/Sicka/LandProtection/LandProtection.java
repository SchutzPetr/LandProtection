package cz.Sicka.LandProtection;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import cz.Sicka.Core.Core;
import cz.Sicka.LandProtection.ChunkStorage.ChunkStorage;
import cz.Sicka.LandProtection.Commands.CommandManager;
import cz.Sicka.LandProtection.Convert.Convert;
import cz.Sicka.LandProtection.Flags.FlagManager;
import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Old_Listeners.BlockBreakListener;
import cz.Sicka.LandProtection.Old_Listeners.BlockPlaceListener;
import cz.Sicka.LandProtection.Old_Listeners.BucketListener;
import cz.Sicka.LandProtection.Old_Listeners.EndermanPickupListener;
import cz.Sicka.LandProtection.Old_Listeners.EntityDamageListener;
import cz.Sicka.LandProtection.Old_Listeners.ExplosionListener;
import cz.Sicka.LandProtection.Old_Listeners.FireListener;
import cz.Sicka.LandProtection.Old_Listeners.FlowListener;
import cz.Sicka.LandProtection.Old_Listeners.InteractListener;
import cz.Sicka.LandProtection.Old_Listeners.LoginLogoutListener;
import cz.Sicka.LandProtection.Old_Listeners.MoveListener;
import cz.Sicka.LandProtection.Old_Listeners.PistonListener;
import cz.Sicka.LandProtection.Old_Listeners.SpawnListener;
import cz.Sicka.LandProtection.Old_Listeners.TeleportListener;
import cz.Sicka.LandProtection.Old_Listeners.VehicleMoveListener;
import cz.Sicka.LandProtection.Old_Listeners.WorldListener;

public class LandProtection extends JavaPlugin{	
	private static LandProtection plugin;
	private static DataManager dataManager;
	private static ChunkStorage chunkStorage;
	private static Manager manager;
	private FlagManager flagManager;
	private static Settings settings;
	
	private BlockBreakListener blockBreak;
	private BlockPlaceListener blockPlace;
	private BucketListener bucket;
	private EndermanPickupListener endermanPickup;
	private EntityDamageListener entityDamage;
	private ExplosionListener explosion;
	private FireListener fire;
	private FlowListener flow;
	private InteractListener interact;
	private LoginLogoutListener loginLogout;
	private MoveListener move;
	private PistonListener piston;
	private SpawnListener spawn;
	private TeleportListener teleport;
	private VehicleMoveListener vehicleMove;
	private WorldListener world;
	private LandDynmap dynmap;
	
	@Override
	public void onEnable(){
		plugin = this;
		
		PluginManager pm = Bukkit.getPluginManager();
		
		Plugin creeplandsCorePlugin = pm.getPlugin("CreeplandsCore");
		Plugin dynmapPlugin = pm.getPlugin("dynmap");
		Plugin vault = pm.getPlugin("Vault");
		
		if(creeplandsCorePlugin == null || !creeplandsCorePlugin.isEnabled()){
			logMessage(Level.WARNING, "&cChyba!&6 Plugin CreeplandsCore nebyl nalezen!");
			this.setEnabled(false);
			return;
		}
		String version = creeplandsCorePlugin.getDescription().getVersion();
		if(!(version.equalsIgnoreCase("0.0.1-BETA_R0.8"))){
			logMessage(Level.WARNING, "&cChyba!&6 Nekompatibilní verze pluginu CreeplandsCore!");
			this.setEnabled(false);
			return;
		}
		if(dynmapPlugin == null || !dynmapPlugin.isEnabled()){
			logMessage(Level.WARNING, "&cChyba!&6 Plugin Dynmap nebyl nalezen!");
			this.setEnabled(false);
			return;
		}
		if(vault == null || !vault.isEnabled()){
			logMessage(Level.WARNING, "&cChyba!&6 Plugin Vault nebyl nalezen!");
			this.setEnabled(false);
			return;
		}
		
		logMessage("&9----------------------------------");
		logMessage("&eSpoustim plugin......");
		logMessage("&9----------------------------------");
		
		flagManager = new FlagManager(null);
		
		dataManager = new DataManager();
		chunkStorage = new ChunkStorage(this);
		
		settings = new Settings(this);
		
		getCommand("landprotection").setExecutor(new CommandManager());
		
		if(dataManager.getLandFolder().list().length == 0 && new File(getDataFolder().getParentFile().getPath()+File.separator+"AreaProtection").exists()){
			logMessage(Level.WARNING, "&6Konvertuji soubory&6.......");
			Convert.convertAllArea();
			logMessage(Level.WARNING, "&aKonverze dokoncena&6!");
		}
		manager = new Manager(this);
		
		logMessage(Level.INFO, "&eInicializuji eventy......");
		blockBreak = new BlockBreakListener();
		blockPlace = new BlockPlaceListener();
		bucket = new BucketListener();
		endermanPickup = new EndermanPickupListener();
		entityDamage = new EntityDamageListener();
		
		explosion = new ExplosionListener();
		fire = new FireListener();
		flow = new FlowListener();
		interact = new InteractListener();
		loginLogout = new LoginLogoutListener();
		
		move = new MoveListener();
		piston = new PistonListener();
		spawn = new SpawnListener();
		teleport = new TeleportListener();
		vehicleMove = new VehicleMoveListener();
		
		world = new WorldListener();
		logMessage(Level.INFO, "&aEventy byly uspesne inicializovany!");
		logMessage(Level.INFO, "&eRegistruji eventy.......");
		
		pm.registerEvents(blockBreak, this);
		pm.registerEvents(blockPlace, this);
		pm.registerEvents(bucket, this);
		pm.registerEvents(endermanPickup, this);
		pm.registerEvents(entityDamage, this);
		
		pm.registerEvents(explosion, this);
		pm.registerEvents(fire, this);
		pm.registerEvents(flow, this);
		pm.registerEvents(interact, this);
		pm.registerEvents(loginLogout, this);
		
		pm.registerEvents(move, this);
		pm.registerEvents(piston, this);
		pm.registerEvents(spawn, this);
		pm.registerEvents(teleport, this);
		pm.registerEvents(vehicleMove, this);
		
		pm.registerEvents(world, this);
		logMessage(Level.INFO, "&aEventy byly uspesne registrovany!");
		
		dynmap = new LandDynmap(this);
		
		logMessage(Level.INFO, "&9----------------------------------");
		logMessage(Level.INFO, "&aPlugin&7:&b " + plugin.getName());
		logMessage(Level.INFO, "&aAutor&7:&b " + plugin.getDescription().getAuthors());
		logMessage(Level.INFO, "&aVerze&7:&b " + plugin.getDescription().getVersion());
		logMessage(Level.INFO, "&9----------------------------------");
		logMessage(Level.INFO, "&aPlugin byl uspesne spusten!");
		logMessage(Level.INFO, "&9----------------------------------");
	}
	
	@Override
	public void onDisable(){
		getServer().getScheduler().cancelTasks(getInstance());
		for(Land land : getManager().getLands()){
			land.saveConfig();
		}
	}
	
	public LandDynmap getLandDynmap() {
		return dynmap;
	}

	public static void logMessage(Level level, String message){
		Core.logMessage(plugin, level, message);
	}
	
	public static void logMessage(String message){
		Core.logMessage(plugin, Level.INFO, message);
	}

	public FlagManager getFlagManager() {
		return flagManager;
	}

	public static LandProtection getInstance() {
		return plugin;
	}

	public final static Manager getManager() {
		return manager;
	}
	
	public final static DataManager getDataManager() {
		return dataManager;
	}

	public static ChunkStorage getChunkStorage() {
		return chunkStorage;
	}

	public static Settings getSettings() {
		return settings;
	}
}
