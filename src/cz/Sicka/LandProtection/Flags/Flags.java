package cz.Sicka.LandProtection.Flags;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Flags {
	private Map<Flag, Boolean> flags;
	
	public Flags(Map<Flag, Boolean> flags){
		if(flags == null){
			this.flags = new HashMap<Flag, Boolean>();
		}else{
			this.flags = flags;
		}
	}
	
	public Boolean getFlag(Flag flag){
		return this.flags.get(flag);
	}
	
	public boolean remove(Flag flag){
		return this.flags.remove(flag);
	}
	
	public boolean contains(Flag flag){
		return this.flags.containsKey(flag);
	}
	
	public Map<Flag, Boolean> getFlagsAndValues(){
		return this.flags;
	}
	
	public Set<Flag> getFlags(){
		return this.flags.keySet();
	}
	
	public void setFlag(Flag flag, Boolean value){
		this.flags.put(flag, value);
	}
}
