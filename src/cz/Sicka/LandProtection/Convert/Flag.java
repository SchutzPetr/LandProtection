package cz.Sicka.LandProtection.Convert;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("unused")
public class Flag {
	private String name;
    private Flag parent;
    private FlagType type;
    //private Permission perm;
	private String description;
    private boolean defaultAreaFlagValue;
    private boolean playerOnly;
    private List<Flag> children = new ArrayList<Flag>();
    
    public Flag(String flag, FlagType type, Flag parent, String description) {
    	this.name = flag;
    	this.type = type;
    	this.parent = parent;
    	this.description = description;
    	this.playerOnly = true;
    }
    
    public Flag(String flag, FlagType type, Flag parent, String description, boolean defaultAreaFlagValue) {
    	this.name = flag;
    	this.type = type;
    	this.parent = parent;
    	this.description = description;
    	this.defaultAreaFlagValue = defaultAreaFlagValue;
    }
    
    public void setDefaultAreaFlagValue(boolean value){
    	this.defaultAreaFlagValue = value;
    }
    
    public void addChildrenFlag(Flag f){
    	children.add(f);
    }
    
    public List<Flag> getChildrenFlags(){
    	return this.children;
    }
    
    public boolean getDefaultAreaFlagValue(){
    	if(this.playerOnly){
    		//TODO: error log
    	}
    	return this.defaultAreaFlagValue;
    }

	public String getName() {
		return name;
	}
	
	public FlagType getFlagType() {
		return type;
	}
	
	public Flag getParent() {
		return parent;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public enum FlagType {
        PLAYER_ONLY, AREA_ONLY, ANY;
    }
}
