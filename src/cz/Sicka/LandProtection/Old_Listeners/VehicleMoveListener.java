package cz.Sicka.LandProtection.Old_Listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import cz.Sicka.LandProtection.Utils.HandleNewlocationUtils;

public class VehicleMoveListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onVehicleMove(VehicleMoveEvent event) {
        Entity vehicle = event.getVehicle();
        Entity passenger = vehicle.getPassenger();
        if (!(passenger instanceof Player)) {
            return;
        }
        Player player = (Player) passenger;
        Location loc = event.getTo();
        /*Area area = AreaProtectionAPI.getAreaProtectionManager().getAreaByLocation(event.getTo());
        if (area == null) {
        	 cz.Sicka.AreaProtection.Utils.ListenersUtils.handleNewLocation(player, event.getFrom(), event.getTo());
            return;
        }
        if (!AllowAction.allowAction(player, event.getTo(), area, FlagManager.VEHICLEMOVE)) {
            vehicle.setVelocity(new Vector(0,0,0));
            vehicle.teleport(event.getFrom());
            return;
        }*/

        HandleNewlocationUtils.handleNewLocation(player, event.getFrom(), loc);
    }
}
