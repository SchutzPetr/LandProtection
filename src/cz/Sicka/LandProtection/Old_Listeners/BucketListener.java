package cz.Sicka.LandProtection.Old_Listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

import cz.Sicka.LandProtection.Flags.Flag;
import cz.Sicka.LandProtection.Flags.FlagManager;
import cz.Sicka.LandProtection.AllowAction;

public class BucketListener implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        Flag flag = null;
        if (event.getBlockClicked().getType() == Material.WATER) {
            flag = FlagManager.WATERBUCKET;
        }
        if (event.getBlockClicked().getType() == Material.LAVA) {
            flag = FlagManager.LAVABUCKET;
        }
        if (flag == null) {
            return;
        }
        if (!AllowAction.allowAction(event.getBlockClicked().getLocation(), event.getPlayer(), flag)){
            event.setCancelled(true);
        }
    }
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        Flag flag = null;
        if (event.getBucket() == Material.LAVA_BUCKET) {
            flag = FlagManager.LAVABUCKET;
        }
        if (event.getBucket() == Material.WATER_BUCKET) {
            flag = FlagManager.WATERBUCKET;
        }
        if (flag == null) {
            return;
        }
        BlockFace face = event.getBlockFace();
        Location blockLocation = event.getBlockClicked().getLocation().add(face.getModX(), face.getModY(), face.getModZ());
        if (!AllowAction.allowAction(blockLocation, event.getPlayer(), flag)) {
            event.setCancelled(true);
        }
    }
}
