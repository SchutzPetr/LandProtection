package cz.Sicka.LandProtection.Old_Listeners;

import cz.Sicka.LandProtection.Utils.HandleNewlocationUtils;

public class MoveListener implements org.bukkit.event.Listener {
	
	@org.bukkit.event.EventHandler(priority = org.bukkit.event.EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
		org.bukkit.Location to = event.getTo();
		org.bukkit.Location from = event.getFrom();
		if(from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()){
			HandleNewlocationUtils.handleNewLocation(event.getPlayer(), from, to);
		}else{
			
		}
	}
}
