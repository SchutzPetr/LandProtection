package cz.Sicka.LandProtection.Utils.Selection;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import cz.Sicka.Core.User.User;
import cz.Sicka.Core.Utils.Replacer;
import cz.Sicka.LandProtection.LandProtection;

public class Selection {
	private User user;
	
	private Hologram firstHologram;
	private Hologram secondHologram;
	
	private Location firstLocation;
	private Location secondLocation;
	
	private Block firstBlock;
	private Block secondBlock;

	private int taskID;
	
	public Selection(User user){
		this.user =  user;
	}
	
	protected void selectFirstPoint(Location firstLocation){
		if(this.firstLocation != null){
			firstBlock.getState().update();
			firstHologram.delete();
		}
		if(this.secondLocation != null){
			if(this.secondLocation.equals(firstLocation)){
				return;
			}
		}
		firstHologram = HologramsAPI.createHologram(LandProtection.getInstance(), firstLocation.clone().add(0, 3, 0));
		firstHologram.appendTextLine(Replacer.replace(user, "&5LandProtection"));
		firstHologram.appendTextLine(Replacer.replace(user, "&aPrvni vyberovy bod"));
		firstHologram.appendTextLine(Replacer.replace(user, "&3X: " + firstLocation.getBlockX()));
		firstHologram.appendTextLine(Replacer.replace(user, "&3Y: " + firstLocation.getBlockY()));
		firstHologram.appendTextLine(Replacer.replace(user, "&3Z: " + firstLocation.getBlockZ()));
		this.firstLocation = firstLocation;
		this.firstBlock = firstLocation.getBlock();
		delayBlockChange(firstLocation, Material.GLOWSTONE);
		user.getPlayer().playSound(user.getPlayer().getLocation(), Sound.LEVEL_UP, 20, 1);
		delayClear();
	}
	
	protected void selectSecondPoint(Location secondLocation){
		if(this.secondLocation != null){
			secondBlock.getState().update();
			secondHologram.delete();
		}
		if(this.firstLocation != null){
			if(this.firstLocation.equals(secondLocation)){
				return;
			}
		}
		secondHologram = HologramsAPI.createHologram(LandProtection.getInstance(), secondLocation.clone().add(0, 3, 0));
		secondHologram.appendTextLine(Replacer.replace(user, "&5LandProtection"));
		secondHologram.appendTextLine(Replacer.replace(user, "&aDruhy vyberovy bod"));
		secondHologram.appendTextLine(Replacer.replace(user, "&3X: " + secondLocation.getBlockX()));
		secondHologram.appendTextLine(Replacer.replace(user, "&3Y: " + secondLocation.getBlockY()));
		secondHologram.appendTextLine(Replacer.replace(user, "&3Z: " + secondLocation.getBlockZ()));
		this.secondLocation = secondLocation;
		this.secondBlock = secondLocation.getBlock();
		delayBlockChange(secondLocation, Material.GLOWSTONE);
		user.getPlayer().playSound(user.getPlayer().getLocation(), Sound.LEVEL_UP, 20, 1);
		delayClear();
	}
	
	public boolean isSelectionComplete(){
		if(this.firstLocation != null && this.secondLocation != null){
			return this.firstLocation.getWorld().getName().equalsIgnoreCase(this.secondLocation.getWorld().getName());
		}else{
			return false;
		}
	}
	
	public void print(){
		if(isSelectionComplete()){
			user.sendMessage("&aPozemek byl uspesne definovan!");
			//TODO: Hologram infoBoad
		}else{
			if(this.firstLocation != null){
				user.sendMessage("&eNyni zbyva vybrat druhy bod!");
			}else if(this.secondLocation != null){
				user.sendMessage("&eNyni zbyva vybrat prvni bod!");
			}
		}
	}
	
	public Location getFirstLocation() {
		return firstLocation;
	}

	public Location getSecondLocation() {
		return secondLocation;
	}

	private void delayBlockChange(final Location loc, final Material mat){
		delayBlockChange(loc, mat, (byte)0);
	}
	
	private void delayBlockChange(final Location loc, final Material mat, final byte data){
		Bukkit.getScheduler().scheduleSyncDelayedTask(LandProtection.getInstance(), new Runnable(){

			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				user.getPlayer().sendBlockChange(loc, mat, data);
			}

		}, 5L);
	}
	
	private void delayClear(){
		if(Bukkit.getScheduler().isQueued(taskID)){
			Bukkit.getScheduler().cancelTask(taskID);
		}
		taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(LandProtection.getInstance(), new Runnable(){

			@Override
			public void run() {
				clear();
			}

		}, 30*20L);
	}
	
	public void clear(){
		this.firstBlock.getState().update();
		this.secondBlock.getState().update();
		if(firstHologram != null)firstHologram.delete();
		if(secondHologram != null)secondHologram.delete();
	}
}
