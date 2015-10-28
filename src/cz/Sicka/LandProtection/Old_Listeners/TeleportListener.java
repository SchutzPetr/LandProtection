package cz.Sicka.LandProtection.Old_Listeners;

import org.bukkit.event.Listener;

public class TeleportListener implements Listener {
	
    /*@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if (!ListenersUtils.canSpawn(player, event.getTo())) {
            event.setCancelled(true);
            return;
        }
        if (AreaProtectionAPI.getAreaProtectionManager().allowAction(player, event.getTo(), FlagManager.TELEPORT)) {
            event.setCancelled(true);
            return;
        }
        cz.Sicka.AreaProtection.Utils.ListenersUtils.handleNewLocation(event.getPlayer(), event.getFrom(), event.getTo());
    }*/
}
