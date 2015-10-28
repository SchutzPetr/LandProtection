package cz.Sicka.LandProtection;

import java.io.File;
import java.util.logging.Level;

import cz.Sicka.Core.Core;
import cz.Sicka.LandProtection.Land.LandData;

public class DataManager {
	public static final int LandFile_Version = 1;
	public static final int WorldAreaFile_Version = 2;
	public static final int SubzoneFile_Version = 1;
	
	private final File landFolder;
	private final File worldFolder;
	private final File subzoneFolder;
	
	public DataManager(){
		File dataF = LandProtection.getInstance().getDataFolder();
		File storageFolder = new File(dataF, "Storage");
		if (!storageFolder.isDirectory()) {
			storageFolder.mkdirs();
        }
		this.landFolder = new File(storageFolder, "Lands");
		if (!landFolder.isDirectory()) {
			landFolder.mkdirs();
        }
		this.worldFolder = new File(storageFolder, "Worlds");
		if (!worldFolder.isDirectory()) {
			worldFolder.mkdirs();
        }
		this.subzoneFolder = new File(storageFolder, "Subzones");
		if (!subzoneFolder.isDirectory()) {
			subzoneFolder.mkdirs();
        }
	}

	public File getLandFolder() {
		return landFolder;
	}
	
	
	public File getWorldFolder() {
		return worldFolder;
	}

	public File getSubzoneFolder() {
		return subzoneFolder;
	}

	public File getLandFile(String landName) {
		File landFile = new File(landFolder, landName + ".yml");
		if(landFile.exists()){
			return landFile;
		}else{
			Core.logMessage(LandProtection.getInstance(), Level.WARNING, "&4Chyba! &6Pozemek "  + landName + " neexistuje!");
			return null;
		}
	}
	
	public boolean isLandExist(String landName){
		File landFile = new File(landFolder, landName + ".yml");
		return landFile.exists();
	}
	
	public boolean renameLandFile(LandData landData, String newName){
		File newLandFile = new File(landFolder, newName + ".yml");
		if(newLandFile.exists()){
			Core.logMessage(LandProtection.getInstance(), Level.WARNING, "&4Chyba! &6Pozemek "  + newName + " již existuje!");
			Core.logMessage(LandProtection.getInstance(), Level.WARNING, "&4Chyba! &6Chyba vznikla bìhem pøejmenovávání pozemku!");
			return false;
		}
		return landData.getLandFile().renameTo(newLandFile);
	}
	
	public File getWorldFile(String worldName){
		File worldFile = new File(worldFolder, worldName + ".yml");
		if(worldFile.exists()){
			return worldFile;
		}else{
			Core.logMessage(LandProtection.getInstance(), Level.WARNING, "&4Chyba! &6Svìt "  + worldName + " neexistuje!");
			return null;
		}
	}
	
	public File getSubzoneFile(String subzoneFullName){
		File subzoneFile = new File(subzoneFolder, subzoneFullName + ".yml");
		if(subzoneFile.exists()){
			return subzoneFile;
		}else{
			Core.logMessage(LandProtection.getInstance(), Level.WARNING, "&4Chyba! &6Subzona "  + subzoneFullName + " neexistuje!");
			return null;
		}
	}
}
