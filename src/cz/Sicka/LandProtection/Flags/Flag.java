package cz.Sicka.LandProtection.Flags;

import java.util.ArrayList;
import java.util.List;

public class Flag {
	private final String flagName;
	private final String flagRealName;
	private final FlagType type;
	private final Flag parent;
	private List<Flag> childrens = new ArrayList<Flag>();
	private final boolean isParent;
	private boolean defaultValue;
	private final String description;
	
	public Flag(String flagName, FlagType type, Flag parent, String description, boolean defaultValue){
		this.flagName = flagName.toLowerCase();
		this.flagRealName = flagName;
		this.type = type;
		this.parent = parent;
		if(parent == null){
			isParent = true;
		}else{
			isParent = false;
		}
		this.defaultValue = defaultValue;
		this.description = description;
	}

	/**
	 * @return the children
	 */
	public List<Flag> getChildrens() {
		return childrens;
	}

	/**
	 * @param childrenFlag the children to set
	 */
	public void addChildren(Flag childrenFlag) {
		this.childrens.add(childrenFlag);
	}

	/**
	 * @return the isParent
	 */
	public boolean isParent() {
		return isParent;
	}

	/**
	 * @return the parent
	 */
	public Flag getParent() {
		return parent;
	}

	/**
	 * @return the type
	 */
	public FlagType getType() {
		return type;
	}

	/**
	 * @return the flagRealName
	 */
	public String getRealName() {
		return flagRealName;
	}

	/**
	 * @return the flagName
	 */
	public String getName() {
		return flagName;
	}

	/**
	 * @return the defaultValue
	 */
	public boolean getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
}
