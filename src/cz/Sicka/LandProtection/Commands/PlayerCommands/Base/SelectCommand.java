package cz.Sicka.LandProtection.Commands.PlayerCommands.Base;

import org.bukkit.Location;
import cz.Sicka.Core.User.User;
import cz.Sicka.LandProtection.Settings;
import cz.Sicka.LandProtection.Commands.CommandMessage;
import cz.Sicka.LandProtection.Utils.Selection.Selection;
import cz.Sicka.LandProtection.Utils.Selection.SelectionManager;

public class SelectCommand {
	
	public static void select(User user, int radius){
		Location loc = user.getPlayer().getLocation();
		Location hloc = new Location(loc.getWorld(), loc.getBlockX() + radius, Settings.getMaxY(), loc.getBlockZ() + radius);
	    Location lloc = new Location(loc.getWorld(), loc.getBlockX() - radius, Settings.getMinY(), loc.getBlockZ() - radius);
	    SelectionManager.SelectFirstPosition(user, hloc);
	    SelectionManager.SelectSecondPosition(user, lloc);
	    
	    SelectionManager.getSelection(user).print();
	}
	
	public static void selectVert(User user){
		Selection sel = SelectionManager.getSelection(user);
		if(sel == null){
			user.sendMessage(CommandMessage.IncompleteSelection);
			return;
		}
		if(!sel.isSelectionComplete()){
			user.sendMessage(CommandMessage.IncompleteSelection);
			return;
		}
		sel.getFirstLocation().setY(Settings.getMaxY());
		sel.getSecondLocation().setY(Settings.getMinY());
		
		SelectionManager.getSelection(user).print();
	}
	
}
