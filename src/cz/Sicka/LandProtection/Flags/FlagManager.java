package cz.Sicka.LandProtection.Flags;

import java.util.HashMap;
import java.util.Map;

public class FlagManager {
	public static final Flag HIDDEN = new Flag("Hidden", FlagType.AREA_ONLY, null, "Hidden", false);
    public static final Flag ADMIN = new Flag("Admin", FlagType.PLAYER_ONLY, null, "Admin", false);
    public static final Flag HEALING = new Flag("Healing", FlagType.AREA_ONLY, null, "Healing", true);
    public static final Flag DAMAGE = new Flag("Damage", FlagType.AREA_ONLY, null, "Damage", true);
    public static final Flag PVP = new Flag("PVP", FlagType.AREA_ONLY, DAMAGE, "PVP", false);

    public static final Flag MEMBER = new Flag("Member", FlagType.PLAYER_ONLY, null, "Member", false);
    
    public static final Flag BUILD = new Flag("Build", FlagType.ANY, MEMBER, "Build", false);
    public static final Flag USE = new Flag("Use", FlagType.ANY, MEMBER, "Use", false);
    public static final Flag CONTAINER = new Flag("Container", FlagType.ANY, MEMBER, "Container", false);
    
    public static final Flag PLACE = new Flag("Place", FlagType.ANY, BUILD, "Place", false);
    public static final Flag DESTROY = new Flag("Destroy", FlagType.ANY, BUILD, "Destroy", false);

    public static final Flag ENDERMANPICKUP = new Flag("EndermanPickup", FlagType.AREA_ONLY, BUILD, "EndermanPickup", false);
    public static final Flag TRAMPLE = new Flag("Trample", FlagType.ANY, BUILD, "Trample", false);

    public static final Flag BUCKET = new Flag("Bucket", FlagType.ANY, BUILD, "Bucket", false);
    public static final Flag LAVABUCKET = new Flag("LavaBucket", FlagType.ANY, BUCKET, "LavaBucket", false);
    public static final Flag WATERBUCKET = new Flag("WaterBucket", FlagType.ANY, BUCKET, "WaterBucket", false);

    public static final Flag FIRESPREAD = new Flag("FireSpread", FlagType.AREA_ONLY, null, "FireSpread", false);
    public static final Flag IGNITE = new Flag("Ignite", FlagType.ANY, null, "Ignite", false);
    public static final Flag PISTON = new Flag("Piston", FlagType.AREA_ONLY, null, "Piston", true);

    public static final Flag REDSTONE = new Flag("Redstone", FlagType.ANY, USE, "Redstone", false);
    public static final Flag BUTTON = new Flag("Button", FlagType.ANY, REDSTONE, "Button", false);
    public static final Flag PRESSUREPLATE = new Flag("PressurePlate", FlagType.ANY, REDSTONE, "PressurePlate", false);
    public static final Flag LEVER = new Flag("Lever", FlagType.ANY, REDSTONE, "Lever", false);
    public static final Flag DIODE = new Flag("Diode", FlagType.ANY, REDSTONE, "Diode", false);

    public static final Flag CAKE = new Flag("Cake", FlagType.ANY, USE, "Cake", false);
    public static final Flag DRAGONEGG = new Flag("DragonEgg", FlagType.ANY, USE, "DragonEgg", false);

    public static final Flag DOOR = new Flag("Door", FlagType.ANY, USE, "Door", false);
    public static final Flag FENCEGATE = new Flag("FenceGate", FlagType.ANY, DOOR, "FenceGate", false);
    public static final Flag HINGEDDOOR = new Flag("HingedDoor", FlagType.ANY, DOOR, "HingedDoor", false);
    public static final Flag TRAPDOOR = new Flag("TrapDoor", FlagType.ANY, DOOR, "TrapDoor", false);

    public static final Flag UTILITY = new Flag("Utility", FlagType.ANY, USE, "Utility", false);
    public static final Flag ANVIL = new Flag("Anvil", FlagType.ANY, UTILITY, "Anvil", false);
    public static final Flag BEACON = new Flag("Beacon", FlagType.ANY, UTILITY, "Beacon", false);
    public static final Flag BED = new Flag("Bed", FlagType.ANY, UTILITY, "Bed", false);
    public static final Flag ENCHANTMENTTABLE = new Flag("EnchantmentTable", FlagType.ANY, UTILITY, "EnchantmentTable", false);
    public static final Flag ENDERCHEST = new Flag("EnderChest", FlagType.ANY, UTILITY, "EnderChest", false);
    public static final Flag WORKBENCH = new Flag("WorkBench", FlagType.ANY, UTILITY, "WorkBench", false);
    
    public static final Flag ITEMFRAME = new Flag("ItemFrame", FlagType.ANY, CONTAINER, "ItemFrame", false);
    public static final Flag CHEST = new Flag("Chest", FlagType.ANY, CONTAINER, "Chest", false);
    public static final Flag FURNACE = new Flag("Furnace", FlagType.ANY, CONTAINER, "Furnace", false);
    public static final Flag BREW = new Flag("Brew", FlagType.ANY, CONTAINER, "Brew", false);
    public static final Flag HOPPER = new Flag("Hopper", FlagType.ANY, CONTAINER, "Hopper", false);
    public static final Flag DROPPER = new Flag("Dropper", FlagType.ANY, CONTAINER, "Dropper", false);
    public static final Flag DISPENSER = new Flag("Dispenser", FlagType.ANY, CONTAINER, "Dispenser", false);
    public static final Flag ARMORSTAND = new Flag("ArmorStand", FlagType.ANY, CONTAINER, "ArmorStand", false);

    public static final Flag FLOW = new Flag("Flow", FlagType.AREA_ONLY, null, "Flow", true);
    public static final Flag LAVAFLOW = new Flag("LavaFlow", FlagType.AREA_ONLY, FLOW, "LavaFlow", true);
    public static final Flag WATERFLOW = new Flag("WaterFlow", FlagType.AREA_ONLY, FLOW, "WaterFlow", true);

    public static final Flag SPAWN = new Flag("Spawn", FlagType.AREA_ONLY, null, "Spawn", true);
    public static final Flag MONSTERS = new Flag("Monsters", FlagType.AREA_ONLY, SPAWN, "Monster", true);
    public static final Flag ANIMALS = new Flag("Animals", FlagType.AREA_ONLY, SPAWN, "Animal", true);

    public static final Flag EXPLOSION = new Flag("Explosion", FlagType.AREA_ONLY, null, "Explosion", false);
    public static final Flag BEDEXPLOSION = new Flag("BedExplosion", FlagType.AREA_ONLY, EXPLOSION, "BedExplosion", false);
    public static final Flag CREEPEREXPLOSION = new Flag("Creeper", FlagType.AREA_ONLY, EXPLOSION, "Creeper", false);
    public static final Flag FIREBALLEXPLOSION = new Flag("Fireball", FlagType.AREA_ONLY, EXPLOSION, "Fireball", false);
    public static final Flag TNTEXPLOSION = new Flag("TNT", FlagType.AREA_ONLY, EXPLOSION, "TNT", false);
    public static final Flag WITHEREXPLOSION = new Flag("WitherExplosion", FlagType.AREA_ONLY, EXPLOSION, "WitherExplosion", false);

    public static final Flag MOVE = new Flag("Move", FlagType.ANY, null, "Move", true);
    public static final Flag VEHICLEMOVE = new Flag("VehicleMove", FlagType.ANY, MOVE, "VehicleMove", true);
    public static final Flag TELEPORT = new Flag("TP", FlagType.ANY, MOVE, "TP", true);
	
	private static Map<String, Flag> validFlags;
	private static Map<String, Flag> areaFlags;
	
	private static Map<String, Boolean> defaultFlagsMap;

	public static Flag getFlag(String flag) {
        return validFlags.get(flag.toLowerCase());
    }
	
	public static Flag getAreaFlag(String flag) {
        return areaFlags.get(flag.toLowerCase());
    }
	
	public static Map<String, Flag> getAreaFlags() {
        return areaFlags;
    }
	
	public static Boolean isFlagExist(String flag){
		return validFlags.containsKey(flag);
	}
	
	public static void addFlag(Flag flag) {
		if(flag.getParent() !=  null){
			flag.getParent().addChildren(flag);
		}
		if(flag.getType() == FlagType.ANY || flag.getType() == FlagType.AREA_ONLY){
			areaFlags.put(flag.getName(), flag);
		}
        validFlags.put(flag.getName(), flag);
    }
	
	public static Map<String, Flag> getFlags(){
		return validFlags;
	}
	
	public FlagManager(Map<String, Boolean> map){
		if(map != null)setDefaultFlags(map);
		validFlags = new HashMap<String, Flag>();
		areaFlags = new HashMap<String, Flag>();
		
		addFlag(HIDDEN);
        addFlag(ADMIN);
        addFlag(HEALING);
        
        addFlag(MEMBER);

        addFlag(DAMAGE);
        addFlag(PVP);

        addFlag(BUILD);
        addFlag(PLACE);
        addFlag(DESTROY);

        addFlag(ENDERMANPICKUP);
        addFlag(TRAMPLE);

        addFlag(BUCKET);
        addFlag(LAVABUCKET);
        addFlag(WATERBUCKET);

        addFlag(FIRESPREAD);
        addFlag(IGNITE);
        addFlag(PISTON);

        addFlag(USE);

        addFlag(REDSTONE);
        addFlag(BUTTON);
        addFlag(PRESSUREPLATE);
        addFlag(LEVER);
        addFlag(DIODE);

        addFlag(CAKE);
        addFlag(DRAGONEGG);

        addFlag(DOOR);
        addFlag(FENCEGATE);
        addFlag(HINGEDDOOR);
        addFlag(TRAPDOOR);

        addFlag(UTILITY);
        addFlag(ANVIL);
        addFlag(BEACON);
        addFlag(BED);
        addFlag(ENCHANTMENTTABLE);
        addFlag(ENDERCHEST);
        addFlag(WORKBENCH);

        addFlag(CONTAINER);
        addFlag(ITEMFRAME);
        addFlag(CHEST);
        addFlag(FURNACE);
        addFlag(BREW);
        addFlag(HOPPER);
        addFlag(DROPPER);
        addFlag(DISPENSER);
        addFlag(ARMORSTAND);

        addFlag(FLOW);
        addFlag(WATERFLOW);
        addFlag(LAVAFLOW);

        addFlag(SPAWN);
        addFlag(MONSTERS);
        addFlag(ANIMALS);

        addFlag(EXPLOSION);
        addFlag(BEDEXPLOSION);
        addFlag(CREEPEREXPLOSION);
        addFlag(FIREBALLEXPLOSION);
        addFlag(TNTEXPLOSION);
        addFlag(WITHEREXPLOSION);

        addFlag(MOVE);
        addFlag(TELEPORT);
		
	}

	public static Map<String, Boolean> getDefaultFlags() {
		return defaultFlagsMap;
	}

	private static void setDefaultFlags(Map<String, Boolean> defaultFlags) {
		defaultFlagsMap = defaultFlags;
		for(String f : defaultFlags.keySet()){
			getFlag(f).setDefaultValue(defaultFlags.get(f));
		}
	}

}