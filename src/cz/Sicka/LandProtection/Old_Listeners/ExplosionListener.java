package cz.Sicka.LandProtection.Old_Listeners;

import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import cz.Sicka.LandProtection.Flags.Flag;
import cz.Sicka.LandProtection.Flags.FlagManager;
import cz.Sicka.LandProtection.AllowAction;

public class ExplosionListener implements Listener {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event) {
        Flag flag = null;
        if (event.getEntity() != null) {
            EntityType entity = event.getEntityType();
            flag = getFlag(entity);
            if (flag == null) {
                return;
            }
        } else {
            flag = FlagManager.BEDEXPLOSION;
        }
        Iterator<Block> it = event.blockList().iterator();
        while (it.hasNext()) {
            Location loc = it.next().getLocation();
            if (!AllowAction.allowAction(loc, flag)) {
                it.remove();
            }
        }
    }

    private Flag getFlag(EntityType entity) {
        switch (entity) {
            case WITHER:
            case WITHER_SKULL:
                return FlagManager.WITHEREXPLOSION;
            case PRIMED_TNT:
            case MINECART_TNT:
                return FlagManager.TNTEXPLOSION;
            case SMALL_FIREBALL:
            case FIREBALL:
                return FlagManager.FIREBALLEXPLOSION;
            case CREEPER:
                return FlagManager.CREEPEREXPLOSION;
            default:
                return null;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onWitherDoSomethingToBlock(EntityChangeBlockEvent event) {
        if (event.getEntityType() != EntityType.WITHER) {
            return;
        }
        if (!AllowAction.allowAction(event.getBlock().getLocation(), FlagManager.WITHEREXPLOSION)) {
            event.setCancelled(true);
        }
    }
}
