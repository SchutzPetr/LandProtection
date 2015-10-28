package cz.Sicka.LandProtection.Commands.PlayerCommands.Base;

import java.util.List;

import org.bukkit.Location;

import cz.Sicka.Core.User.User;
import cz.Sicka.Core.Utils.CoreUtils;
import cz.Sicka.LandProtection.LandProtection;
import cz.Sicka.LandProtection.Commands.CommandMessage;
import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Utils.SelectionArea;
import cz.Sicka.LandProtection.Utils.Selection.Selection;
import cz.Sicka.LandProtection.Utils.Selection.SelectionManager;

public class ClaimCommand {
	
	public static void claim(User owner, String landName){
		if(!CoreUtils.isAlphaNumeric(landName)){
			owner.sendMessage(CommandMessage.UseAlphaNumeric);
		}
		if(LandProtection.getManager().isLandExist(landName)){
			owner.sendMessage(CommandMessage.LandAlreadyExist);
			return;
		}
		if(LandProtection.getManager().getWorldArea(owner.getPlayer().getWorld()).isDisableClaim()){
			owner.sendMessage(CommandMessage.WorldDisableCreation);
			return;
		}
		Selection sel = SelectionManager.getSelection(owner);
		if(sel == null){
			owner.sendMessage(CommandMessage.IncompleteSelection);
			return;
		}
		if(!sel.isSelectionComplete()){
			owner.sendMessage(CommandMessage.IncompleteSelection);
			return;
		}
		Location fLocation = sel.getFirstLocation();
		Location sLocation = sel.getSecondLocation();
		SelectionArea selarea = new SelectionArea(fLocation, sLocation);
		List<Land> lands = LandProtection.getChunkStorage().getCollisionLands(selarea);
		if(lands == null || !lands.isEmpty() || lands.size() != 0){
			owner.sendMessage(CommandMessage.LandCollision);
			for(Land land : lands){
				owner.sendMessage("&cKolizujici pozemek&f: &e" + land.getDisplayName());
			}
			return;
		}
		long userBlockToClaim = owner.getAccount().getBlocks();
		
		if(selarea.getExpanse() > userBlockToClaim){
			owner.sendMessage("&4Chyba! &6Nemas dostatek volnych bloku k vytvoreni oblasti!");
			return;
		}
		if(selarea.getExpanse() > LandProtection.getSettings().getMaxExpanse()){
			owner.sendMessage(CommandMessage.TooLarge);
			owner.sendMessage("&eMaximalni povolena rozloha je &b" + LandProtection.getSettings().getMaxExpanse() + "&e bloku.");
		}
		if(selarea.getXSize() < LandProtection.getSettings().getMinWidth()){
			owner.sendMessage(CommandMessage.TooNarrow);
			owner.sendMessage("&eMinimalni povolena sirka je &b" + LandProtection.getSettings().getMinWidth() + "&e bloku.");
		}
		if(selarea.getZSize() < LandProtection.getSettings().getMinLength()){
			owner.sendMessage(CommandMessage.TooLow);
			owner.sendMessage("&eMinimalni povolena delka je &b" + LandProtection.getSettings().getMinLength() + "&e bloku.");
		}
		if(selarea.getYSize() < LandProtection.getSettings().getMinHeight()){
			owner.sendMessage(CommandMessage.TooLow);
			owner.sendMessage("&eMinimalni povolena vyska je &b" + LandProtection.getSettings().getMinHeight() + "&e bloku.");
		}
		
		if(owner.getLands().size() >= owner.getAccount().getSlotsForLand()){
			owner.sendMessage(CommandMessage.TooManyLands);
			return;
		}

		if(owner.getAccount().subtractBlocks(selarea.getExpanse())){
			LandProtection.getManager().claimLand(owner, landName, selarea);
			owner.sendMessage(CommandMessage.LandSuccesfulOccupied);
		}else{
			owner.sendMessage("&4Chyba! &csubtractFromBlocksToClaim failed! &eNahlaste tuto chybu adminovi!&a Dekujeme");
		}
	}
}
