package cz.Sicka.LandProtection.Old_Listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import cz.Sicka.LandProtection.AllowAction;
import cz.Sicka.LandProtection.Flags.FlagManager;

public class EndermanPickupListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEndermanChangeBlock(EntityChangeBlockEvent event) {
        if (event.getEntityType() != EntityType.ENDERMAN) {
            return;
        }
        if (!AllowAction.allowAction(event.getBlock().getLocation(), FlagManager.ENDERMANPICKUP)) {
            event.setCancelled(true);
        }
    }
}
