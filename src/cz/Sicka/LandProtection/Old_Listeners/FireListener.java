package cz.Sicka.LandProtection.Old_Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

import cz.Sicka.LandProtection.Flags.FlagManager;
import cz.Sicka.LandProtection.AllowAction;

public class FireListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockBurn(BlockBurnEvent event) {
        if (!AllowAction.allowAction(event.getBlock().getLocation(), FlagManager.FIRESPREAD)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (event.getCause() == IgniteCause.SPREAD) {
            if (!AllowAction.allowAction(event.getBlock().getLocation(), FlagManager.FIRESPREAD)) {
                event.setCancelled(true);
            }
            return;
        }
        if (event.getCause() == IgniteCause.FLINT_AND_STEEL) {
            Player player = event.getPlayer();
            if (!AllowAction.allowAction(event.getBlock().getLocation(), player, FlagManager.IGNITE)) {
                event.setCancelled(true);
            }
            return;
        }
        if (!AllowAction.allowAction(event.getBlock().getLocation(), FlagManager.IGNITE)) {
            event.setCancelled(true);
        }
    }
}
