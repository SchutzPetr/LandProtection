package cz.Sicka.LandProtection.Old_Listeners;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import cz.Sicka.LandProtection.Flags.FlagManager;
import cz.Sicka.LandProtection.AllowAction;

public class PistonListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockPistonRetract(BlockPistonRetractEvent event) {
        if (!AllowAction.allowAction(event.getBlock().getLocation(), FlagManager.PISTON)) {
            event.setCancelled(true);
            return;
        }
        if (!event.isSticky()) {
            return;
        }
        if (!AllowAction.allowAction(event.getRetractLocation(), FlagManager.PISTON)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onBlockPistonExtend(BlockPistonExtendEvent event) {
        if (!AllowAction.allowAction(event.getBlock().getLocation(), FlagManager.PISTON)) {
            event.setCancelled(true);
            return;
        }
        for (Block block : event.getBlocks()) {
            if (!AllowAction.allowAction(block.getLocation(), FlagManager.PISTON)) {
                event.setCancelled(true);
                return;
            }
            Location blockto = block.getLocation();
            blockto.setX(blockto.getX() + event.getDirection().getModX());
            blockto.setY(blockto.getY() + event.getDirection().getModY());
            blockto.setZ(blockto.getZ() + event.getDirection().getModZ());
            if (!AllowAction.allowAction(blockto, FlagManager.PISTON)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
