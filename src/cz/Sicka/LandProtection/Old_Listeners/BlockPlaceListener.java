package cz.Sicka.LandProtection.Old_Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import cz.Sicka.LandProtection.Flags.FlagManager;
import cz.Sicka.LandProtection.AllowAction;


public class BlockPlaceListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onBlockPlaceEvent(BlockPlaceEvent event){
		if(!AllowAction.allowAction(event.getBlock().getLocation(), event.getPlayer(), FlagManager.PLACE)){
			event.setCancelled(true);
		}
	}
}
