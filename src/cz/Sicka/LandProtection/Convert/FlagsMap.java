package cz.Sicka.LandProtection.Convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FlagsMap {
	private Map<String, Boolean> flags;
	
	public FlagsMap(Map<String, Boolean> flags){
		this.flags = flags;
	}
	
	public FlagsMap(Flag flag, boolean value){
		flags = new HashMap<String, Boolean>();
		this.flags.put(flag.getName().toLowerCase(), value);
	}
	
	public FlagsMap(String flag, boolean value){
		flags = new HashMap<String, Boolean>();
		this.flags.put(flag.toLowerCase(), value);
	}
	
	public FlagsMap(){
		flags = new HashMap<String, Boolean>();
	}
	
	public void addFlag(Flag flag, boolean value){
		addFlag(flag.getName().toLowerCase(), value);
	}
	
	public void addFlag(String flag, boolean value){
		if(this.flags.containsKey(flag.toLowerCase())){
			this.flags.remove(flag.toLowerCase());
		}
		this.flags.put(flag.toLowerCase(), value);
	}
	
	public void removeFlag(Flag flag){
		removeFlag(flag.getName().toLowerCase());
	}
	
	public void removeFlag(String flag){
		if(this.flags.containsKey(flag.toLowerCase())){
			this.flags.remove(flag.toLowerCase());
		}
	}
	
	public ArrayList<String> getFlags(){
		return new ArrayList<String>(this.flags.keySet());
	}
	
	public boolean getFlagValue(String name){
		return this.flags.get(name.toLowerCase());
	}
	
	public Map<String, Boolean> getFlagsAndValues(){
		return this.flags;
	}
	
	public Map<Flag, Boolean> getAreaFlags(){
		Map<Flag, Boolean> fl = new HashMap<Flag, Boolean>();
		for(String flagName : this.flags.keySet()){
			fl.put(FlagManager.getFlag(flagName), this.getFlagValue(flagName));
		}
		return fl;
	}
}
