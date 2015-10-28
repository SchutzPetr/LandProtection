package cz.Sicka.LandProtection.Commands.PlayerCommands.Base;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.ChatColor;

import cz.Sicka.Core.User.OfflineUser;
import cz.Sicka.Core.User.UManager;
import cz.Sicka.Core.User.User;
import cz.Sicka.Core.Utils.Replacer;
import cz.Sicka.LandProtection.LandProtection;
import cz.Sicka.LandProtection.Manager;
import cz.Sicka.LandProtection.Commands.CommandMessage;
import cz.Sicka.LandProtection.Flags.Flag;
import cz.Sicka.LandProtection.Flags.Flags;
import cz.Sicka.LandProtection.Land.Land;
import cz.Sicka.LandProtection.Subzone.Subzone;
import cz.Sicka.LandProtection.WorldArea.WorldArea;

public class InfoCommand {
	
	public static void infoCommand(User user, String landOrSubzoneName){
		String lowerCaseName = landOrSubzoneName.toLowerCase();
		if(lowerCaseName.contains(".")){
			String[] split = lowerCaseName.split("\\.");
			String mainLand = split[0];
			String sub = split[1];
			Land land =  LandProtection.getManager().getLand(mainLand);
			if(land != null){
				Subzone subzone = LandProtection.getManager().getLand(mainLand).getSubzone(sub);
				if(subzone != null){
					print(user, getInfo(user, subzone));
					return;
				}else{
					user.sendMessage(CommandMessage.SubzoneDoesNotExist);
				}
			}else{
				user.sendMessage(CommandMessage.LandDoesNotExist);
			}
		}else{
			Land land =  LandProtection.getManager().getLand(landOrSubzoneName);
			if(land != null){
				print(user, getInfo(user, land));
				return;
			}else{
				user.sendMessage(CommandMessage.LandDoesNotExist);
			}
		}
	}
	
	public static void infoCommand(User user){
		Land land = Manager.getLandByLocation(user.getPlayer().getLocation());
		if(land != null){
			Subzone subzone = Manager.getSubzoneByLocation(user.getPlayer().getLocation(), land);
			if(subzone != null){
				print(user, getInfo(user, subzone));
				return;
			}else{
				print(user, getInfo(user, land));
				return;
			}
		}else{
			print(user, getInfo(user, LandProtection.getManager().getWorldArea(user.getPlayer().getWorld())));
			return;
		}
	}
	
	private static void print(User user, List<String> infoMsg) {
		for(String msg : infoMsg){
			user.sendMessage(msg);
		}
	}
	
	public static List<String> getInfo(User user, WorldArea wa) {
		List<String> infoList = new ArrayList<String>();
		String line_0 = "&7++++&2--------[ &6" + "Informace o svete: &a" + wa.getWorldName() + "&2 ]--------&7++++";
		String line_1 = "&6Jmeno sveta&7: &b" + wa.getWorldName();
		String line_2 = "&6Nastaveni flagu sveta&7: &b" + getAreaFlagsInfo(wa.getFlags());
		String line_3 = "&6Je dovoleno zabirat pozemky&7: &b" + !wa.isDisableClaim();
		infoList.add(line_0);
		infoList.add(line_1);
		infoList.add(line_2);
		infoList.add(line_3);
		if(wa.isEnableBuildPerInterval()){
			String line_4 = "&6Kolik bloku muzete znicit nebo postavit za dany interval&7: &b" + wa.getBuildPerInterval_Blocks();
			String line_5 = "&6Interval&7: &b" + wa.getBuildPerInterval_Interval();
			infoList.add(line_4);
			infoList.add(line_5);
		}
		return infoList;
	}
	
	public static List<String> getInfo(User user, Subzone subzone) {
		List<String> infoList = new ArrayList<String>();
		String line_0 = "&7++++&2--------[ &6" + "Informace o subzone: &a" + subzone.getFullName() + "&2 ]--------&7++++";
		String line_1 = "&6Jmeno subzony&7: &b" + subzone.getName();
		String line_2 = "&6Jmeno hlavniho pozemku&7: &b" + subzone.getLand().getDisplayName();
		String line_3 = "&6Vlastnik subzony&7: &b" + subzone.getLand().getOwner().getName();
		String line_4 = "&6Nastaveni flagu subzony&7: &b" + getAreaFlagsInfo(subzone.getLSFlags().getAreaFlags());
		String line_5 = "&6Tve nastaveni flagu&7: &b" + getPlayerFlagsInfo(subzone, user);
		String line_6 = "&6Subzona byla vytvorena&7: &b" + Replacer.getDateByCurrentTimeMillis(subzone.getCreationDateMillis());
		String line_7 = "&6Vstupni zprava&7: &b" + getReplacedEnterMessage(subzone);
		String line_8 = "&6Opousteci zprava&7: &b" + getReplacedLeaveMessage(subzone);
		String line_9 = "&6Rozloha subzony&7: &b" + subzone.getExpanse();
		infoList.add(line_0);
		infoList.add(line_1);
		infoList.add(line_2);
		infoList.add(line_3);
		infoList.add(line_4);
		infoList.add(line_5);
		for(String str : getPlayersFlagsInfo(subzone)){
			infoList.add(str);
		}
		infoList.add(line_6);
		infoList.add(line_7);
		infoList.add(line_8);
		infoList.add(line_9);
		return infoList;
	}
	
	public static List<String> getInfo(User user, Land land) {
		List<String> infoList = new ArrayList<String>();
		String line_0 = "&7++++&2--------[ &6" + "Informace o pozemku: &a" + land.getDisplayName() + "&2 ]--------&7++++";
		String line_1 = "&6Jmeno pozemku&7: &b" + land.getDisplayName();
		String line_2 = "&6Vlastnik pozemku&7: &b" + land.getOwner().getName();
		String line_3 = "&6Nastaveni flagu pozemku&7: &b" + getAreaFlagsInfo(land.getLSFlags().getAreaFlags());
		String line_4 = "&6Tve nastaveni flagu&7: &b" + getPlayerFlagsInfo(land, user);
		String line_5 = "&6Pozemek byl vytvoren&7: &b" + Replacer.getDateByCurrentTimeMillis(land.getCreationDateMillis());
		String line_6 = "&6Vstupni zprava&7: &b" + getReplacedEnterMessage(land);
		String line_7 = "&6Opousteci zprava&7: &b" + getReplacedLeaveMessage(land);
		String line_8 = "&6Rozloha pozemku&7: &b" + land.getExpanse();
		infoList.add(line_0);
		infoList.add(line_1);
		infoList.add(line_2);
		infoList.add(line_3);
		infoList.add(line_4);
		for(String str : getPlayersFlagsInfo(land)){
			infoList.add(str);
		}
		infoList.add(line_5);
		infoList.add(line_6);
		infoList.add(line_7);
		infoList.add(line_8);
		return infoList;
	}
	
	public static String getReplacedEnterMessage(Land land){
		String enterMsg = land.getEnterMessage();
		enterMsg = enterMsg.replace("<New_Land>", land.getDisplayName());
		enterMsg = enterMsg.replace("<Owner>", land.getOwner().getName());
		return enterMsg;
	}
	
	public static String getReplacedLeaveMessage(Land land){
		String leaveMsg = land.getLeaveMessage();
		leaveMsg = leaveMsg.replace("<Old_Land>", land.getDisplayName());
		leaveMsg = leaveMsg.replace("<Owner>", land.getOwner().getName());
		return leaveMsg;
	}
	
	public static String getReplacedEnterMessage(Subzone subzone){
		String enterMsg = subzone.getEnterMessage();
		enterMsg = enterMsg.replace("<New_Land>", subzone.getDisplayName());
		enterMsg = enterMsg.replace("<Owner>", subzone.getLand().getOwner().getName());
		return enterMsg;
	}
	
	public static String getReplacedLeaveMessage(Subzone subzone){
		String leaveMsg = subzone.getLeaveMessage();
		leaveMsg = leaveMsg.replace("<Old_Land>", subzone.getDisplayName());
		leaveMsg = leaveMsg.replace("<Owner>", subzone.getLand().getOwner().getName());
		return leaveMsg;
	}
	
	private static String getAreaFlagsInfo(Flags flags){
		StringBuilder flagsinfo = new StringBuilder();
		for(Flag flag : flags.getFlags()){
			if(flagsinfo.length() > 0){
				flagsinfo.append(ChatColor.WHITE + ", ");
			}
			if(flags.getFlag(flag)){
				flagsinfo.append(ChatColor.GREEN + "+ " + flag.getName());
			}else{
				flagsinfo.append(ChatColor.RED + "- " + flag.getName());
			}
		}
		return flagsinfo.toString();
	}
	
	private static List<String> getPlayersFlagsInfo(Subzone subzone){
		List<String> ulits = new ArrayList<String>();
		Set<UUID> players = subzone.getLSFlags().getPlayers();
		if(players.isEmpty() || players == null | players.size() == 0){
			return ulits;
		}
		ulits.add("&6Nastaveni flagu hracu&7: ");
		for(UUID uuid : players){
			OfflineUser offUser = UManager.getOfflineUser(uuid);
			ulits.add("   " + ChatColor.AQUA + offUser.getName() + ": " + getPlayerFlagsInfo(subzone, offUser));
		}
		return ulits;
	}
	
	private static List<String> getPlayersFlagsInfo(Land land){
		List<String> ulits = new ArrayList<String>();
		Set<UUID> players = land.getLSFlags().getPlayers();
		if(players.isEmpty() || players == null | players.size() == 0){
			return ulits;
		}
		ulits.add("&6Nastaveni flagu hracu&7: ");
		for(UUID uuid : players){
			OfflineUser offUser = UManager.getOfflineUser(uuid);
			ulits.add("   " + ChatColor.AQUA + offUser.getName() + ": " + getPlayerFlagsInfo(land, offUser));
		}
		return ulits;
	}
	
	private static String getPlayerFlagsInfo(Land land, OfflineUser sender){
		if(land.getOwner().getUniqueId().equals(sender.getUniqueId())){
			return ChatColor.GREEN + "Jsi vlastnik pozemku! Proto mas vsechna opravneni.";
		}
		if(!land.getLSFlags().containsPlayer(sender.getUniqueId())){
			return ChatColor.RED + "Zadne";
		}
		StringBuilder flagsinfo = new StringBuilder();
		Flags flags = land.getLSFlags().getPlayerFlags(sender.getUniqueId());
		for(Flag flag : flags.getFlags()){
			if(flagsinfo.length() > 0){
				flagsinfo.append(ChatColor.WHITE + ", ");
			}
			if(flags.getFlag(flag)){
				flagsinfo.append(ChatColor.GREEN + "+ " + flag.getName());
			}else{
				flagsinfo.append(ChatColor.RED + "- " + flag.getName());
			}
		}
		return flagsinfo.toString();
	}
	
	private static String getPlayerFlagsInfo(Subzone subzone, OfflineUser sender){
		if(subzone.getLand().getOwner().getUniqueId().equals(sender.getUniqueId())){
			return ChatColor.GREEN + "Jsi vlastnik subzony! Proto mas vsechna opravneni.";
		}
		if(!subzone.getLSFlags().containsPlayer(sender.getUniqueId())){
			return ChatColor.RED + "Zadne";
		}
		StringBuilder flagsinfo = new StringBuilder();
		Flags flags = subzone.getLSFlags().getPlayerFlags(sender.getUniqueId());
		for(Flag flag : flags.getFlags()){
			if(flagsinfo.length() > 0){
				flagsinfo.append(ChatColor.WHITE + ", ");
			}
			if(flags.getFlag(flag)){
				flagsinfo.append(ChatColor.GREEN + "+ " + flag.getName());
			}else{
				flagsinfo.append(ChatColor.RED + "- " + flag.getName());
			}
		}
		return flagsinfo.toString();
	}	
}

