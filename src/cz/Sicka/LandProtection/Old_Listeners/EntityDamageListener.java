package cz.Sicka.LandProtection.Old_Listeners;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;

import cz.Sicka.LandProtection.AllowAction;
import cz.Sicka.LandProtection.Flags.FlagManager;

import java.util.Iterator;

public class EntityDamageListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSplashPotion(PotionSplashEvent event) {
        Entity ent = event.getEntity();
        boolean srcpvp = AllowAction.allowAction(ent.getLocation(), FlagManager.PVP);
        Iterator<LivingEntity> it = event.getAffectedEntities().iterator();
        while (it.hasNext()) {
            LivingEntity target = it.next();
            if (target.getType() == EntityType.PLAYER) {
                if (!srcpvp || !AllowAction.allowAction(target.getLocation(), FlagManager.PVP)) {
                    event.setIntensity(target, 0);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity ent = event.getEntity();
        if (!AllowAction.allowAction(ent.getLocation(), FlagManager.DAMAGE)) {
            event.setCancelled(true);
            if (event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                ent.setFireTicks(0);
            }
            return;
        }
        if (event instanceof EntityDamageByEntityEvent) {
            onEntityDamageByEntity((EntityDamageByEntityEvent) event, ent.getLocation());
        }
    }

    public void onEntityDamageByEntity(EntityDamageByEntityEvent event, Location location) {
        Entity ent = event.getEntity();
        if (ent.hasMetadata("NPC")) {
            return;
        }
        if (ent.getType() == EntityType.ITEM_FRAME) {
            Entity damager = event.getDamager();
            Player player = getPlayer(damager);
            if (player == null) {
                return;
            }
            if (!AllowAction.allowAction(location, player, FlagManager.ITEMFRAME)) {
                event.setCancelled(true);
            }
            return;
        }
        if (ent.getType() == EntityType.ARMOR_STAND) {
        	 Entity damager = event.getDamager();
             Player player = getPlayer(damager);
             if (player == null) {
                 return;
             }
             if (!AllowAction.allowAction(location, player, FlagManager.ARMORSTAND)) {
                 event.setCancelled(true);
             }
             return;
        }
        Player receiver = getPlayer(ent);
        if (receiver == null) {
            return;
        }
        Entity damager = event.getDamager();
        Player player = getPlayer(damager);
        if (player == null) {
            return;
        }
        if (!AllowAction.allowAction(location, FlagManager.PVP)) {
            event.setCancelled(true);
            return;
        }
        if (!AllowAction.allowAction(player.getLocation(), player, FlagManager.PVP)) {
            event.setCancelled(true);
            return;
        }
        if (damager == player) {
            return;
        }
        if (!AllowAction.allowAction(damager.getLocation(), FlagManager.PVP)) {
            event.setCancelled(true);
            return;
        }
    }

    public Player getPlayer(Entity entity) {
        if (entity instanceof Player) {
            return (Player) entity;
        }
        if (entity instanceof Wolf && ((Wolf) entity).isTamed()) {
            return ((Wolf) entity).getKiller();
        }
        if (entity instanceof Projectile && ((Projectile) entity).getShooter() instanceof Player) {
            return (Player) ((Projectile) entity).getShooter();
        }
        return null;
    }
}
