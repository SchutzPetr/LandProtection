package cz.Sicka.LandProtection.Old_Listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import cz.Sicka.Core.User.UManager;
import cz.Sicka.Core.User.User;
import cz.Sicka.LandProtection.Flags.Flag;
import cz.Sicka.LandProtection.Flags.FlagManager;
import cz.Sicka.LandProtection.Utils.Selection.SelectionManager;
import cz.Sicka.LandProtection.AllowAction;

public class InteractListener implements Listener{
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		if(player.getItemInHand().getType() == Material.STICK){
			select(player, event);
		}
		/*if(player.getItemInHand().getType() == Material.STICK && player.getItemInHand().hasItemMeta()){
			if(player.getItemInHand().getItemMeta().getDisplayName().contains(Tools.SelectionTool)){
				select(player, event);
			}else if(player.getItemInHand().getItemMeta().getDisplayName().contains(Tools.InfoTool)){
				info(player, block, event);
			}
		}*/
		if (block == null) {
            return;
        }
        if (event.getAction() != Action.LEFT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            pressure(player, block, event);
            return;
        }
        if (event.isCancelled()) {
            return;
        }
        if (player.getItemInHand().getType() == Material.ARMOR_STAND){
        	armorStand(player, block, event);
        }
        interact(player, block, event);
	}
	
	private void pressure(Player player, Block block, PlayerInteractEvent event) {
		if (event.getAction() != Action.PHYSICAL) {
            return;
        }
        Material mat = block.getType();
        if (mat == Material.SOIL || mat == Material.SOUL_SAND) {
            if (!AllowAction.allowAction(block.getLocation(), player, FlagManager.TRAMPLE)) {
                event.setCancelled(true);
                return;
            }
            return;
        }
        if (block.getType() != Material.STONE_PLATE && block.getType() != Material.WOOD_PLATE
                && block.getType() != Material.GOLD_PLATE && block.getType() != Material.IRON_PLATE) {
            return;
        }
        if (!AllowAction.allowAction(block.getLocation(), player, FlagManager.PRESSUREPLATE)) {
            event.setCancelled(true);
        }
	}

	private void interact(Player player, Block block, PlayerInteractEvent event) {
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		Flag flag = getFlag(block.getType());
		if (flag == null) {
            return;
        }
		if (!AllowAction.allowAction(block.getLocation(), player, flag)) {
            event.setCancelled(true);
        }else{
        }
	}

	private void armorStand(Player player, Block block, Cancellable cancellable) {
		if(!AllowAction.allowAction(block.getLocation(), player, FlagManager.ARMORSTAND)){
			cancellable.setCancelled(true);
		}
	}

	@SuppressWarnings("unused")
	private void info(Player player, Block block, PlayerInteractEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void select(Player player, PlayerInteractEvent event){
		User user = UManager.getUser(player.getUniqueId());
		if(event.getAction() ==  Action.LEFT_CLICK_BLOCK){
			player.sendMessage("F");
			SelectionManager.SelectFirstPosition(user, event.getClickedBlock().getLocation());
		}else if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
			player.sendMessage("S");
			SelectionManager.SelectSecondPosition(user, event.getClickedBlock().getLocation());
		}
		event.setCancelled(true);
		
	}
	
	private Flag getFlag(Material mat) {
        switch (mat) {
            case CHEST:
            case TRAPPED_CHEST:
                return FlagManager.CHEST;
            case FURNACE:
            case BURNING_FURNACE:
                return FlagManager.FURNACE;
            case BREWING_STAND:
                return FlagManager.BREW;
            case STONE_BUTTON:
            case WOOD_BUTTON:
                return FlagManager.BUTTON;
            case LEVER:
                return FlagManager.LEVER;
            case DIODE_BLOCK_OFF:
            case DIODE_BLOCK_ON:
            case DIODE:
                return FlagManager.DIODE;
            case CAKE_BLOCK:
                return FlagManager.CAKE;
            case DRAGON_EGG:
                return FlagManager.DRAGONEGG;
            case FENCE_GATE:
            case SPRUCE_FENCE_GATE:
            case BIRCH_FENCE_GATE:
            case JUNGLE_FENCE_GATE:
            case ACACIA_FENCE_GATE:
            case DARK_OAK_FENCE_GATE:
                return FlagManager.FENCEGATE;
            case WOODEN_DOOR:
            case IRON_DOOR_BLOCK:
            case SPRUCE_DOOR:
            case BIRCH_DOOR:
            case JUNGLE_DOOR:
            case ACACIA_DOOR:
            case DARK_OAK_DOOR:
                return FlagManager.HINGEDDOOR;
            case TRAP_DOOR:
                return FlagManager.TRAPDOOR;
            case ANVIL:
                return FlagManager.ANVIL;
            case BED:
            case BED_BLOCK:
                return FlagManager.BED;
            case ENCHANTMENT_TABLE:
                return FlagManager.ENCHANTMENTTABLE;
            case ENDER_CHEST:
                return FlagManager.ENDERCHEST;
            case WORKBENCH:
                return FlagManager.WORKBENCH;
            case HOPPER:
                return FlagManager.HOPPER;
            case DROPPER:
                return FlagManager.DROPPER;
            case DISPENSER:
                return FlagManager.DISPENSER;
            default:
                return null;
        }
    }
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (entity == null) {
            return;
        }
        if (entity.getType() != EntityType.ITEM_FRAME && entity.getType() != EntityType.ARMOR_STAND) {
            return;
        }
        if(entity.getType() == EntityType.ITEM_FRAME){
        	if (!AllowAction.allowAction(entity.getLocation(), player, FlagManager.ITEMFRAME)) {
                event.setCancelled(true);
            }
        }else{
        	if (!AllowAction.allowAction(entity.getLocation(), player, FlagManager.ARMORSTAND)) {
                event.setCancelled(true);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteractAtEntityEven(PlayerInteractAtEntityEvent event){
    	Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (entity == null) {
            return;
        }
        if (entity.getType() != EntityType.ARMOR_STAND) {
            return;
        }
        if (!AllowAction.allowAction(entity.getLocation(), player, FlagManager.ARMORSTAND)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityInteract(EntityInteractEvent event) {
        Block block = event.getBlock();
        Material mat = block.getType();
        Entity entity = event.getEntity();
        if ((entity.getType() == EntityType.FALLING_BLOCK) || !(mat == Material.SOIL || mat == Material.SOUL_SAND)) {
            return;
        }
        if (!AllowAction.allowAction(block.getLocation(), FlagManager.TRAMPLE)) {
            event.setCancelled(true);
        }
    }
}
