package cz.Sicka.LandProtection.Utils.Selection;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import cz.Sicka.Core.User.User;

public class SelectionManager {
	private static Map<User, Selection> userSelection = new HashMap<User, Selection>();
	public static String SelectionTool = ChatColor.RED + "AreaProtection" + ChatColor.WHITE + " - " + ChatColor.AQUA + "Selection tool";
	
	public static boolean alreadyInSelection(User user){
		return userSelection.containsKey(user);
	}
	
	public static boolean isSelectionComplete(User user){
		return getSelection(user).isSelectionComplete();
	}
	
	public static Selection getSelection(User user){
		return userSelection.get(user);
	}
	
	public static void addSelection(User user, Selection selection){
		if(userSelection.containsKey(user)){
			userSelection.remove(user);
		}
		userSelection.put(user, selection);
	}

	public static Map<User, Selection> getUsersSelections() {
		return userSelection;
	}
	
	public static void clearAll(){
		for(Selection selection : userSelection.values()){
			selection.clear();
		}
	}
	
	public static void SelectFirstPosition(User user, Location location) {
		Selection sel = getSelection(user);
		if(sel != null){
			getSelection(user).selectFirstPoint(location);
		}else{
			sel = new Selection(user);
			sel.selectFirstPoint(location);
			addSelection(user, sel);
		}
	}
	
	public static void SelectSecondPosition(User user, Location location) {
		Selection sel = getSelection(user);
		if(sel != null){
			getSelection(user).selectSecondPoint(location);
		}else{
			sel = new Selection(user);
			sel.selectSecondPoint(location);
			addSelection(user, sel);
		}
	}
}
