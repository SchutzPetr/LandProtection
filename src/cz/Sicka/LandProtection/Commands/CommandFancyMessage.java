package cz.Sicka.LandProtection.Commands;

import static org.bukkit.ChatColor.*;

import cz.Sicka.Core.Utils.fanciful.FancyMessage;

public class CommandFancyMessage {
	
	public static String landClaimCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "land claim")
				.tooltip(
						AQUA
								+ ""
								+ BOLD
								+ "/land claim <Jmeno pozemku> \n\n"
								+ GRAY
								+ "Zaberete vybranou oblast\n"
								+ GRAY
								+ "Jmeno musi byt jedinecne a musi obsahovat alfanumericke znaky!\n\n"
								+ GOLD + "Priklad: " + AQUA + ITALIC + BOLD + "/land claim testClaim")
				.suggest("/land claim ").then(GRAY + "" + BOLD + "  <--- Pro vice info najed mysi!").toJSONString();
	}

	public static String landPlayerSetCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "land pset ")
				.tooltip(
						AQUA
								+ ""
								+ BOLD
								+ "/land pset [Jmeno pozemku] <Jmeno Hrace> <Flag> <Hodnota> \n\n"
								+ GRAY
								+ "Nastavi na pozemku opravneni pro daneho hrace\n\n"
								+ GREEN + BOLD + "Hodnoty:\n"
								+ YELLOW + "'" + AQUA + BOLD + "r" + YELLOW + "'" + GRAY + " nebo " + YELLOW + "'" + AQUA + BOLD + "remove" + YELLOW + "'" 
								+ GRAY 
								+ "  - Odstrani flag a pouzije vychozi hodnoty \n"
								+ YELLOW + "'" + GREEN + BOLD + "t" + YELLOW + "'" + GRAY + " nebo " + YELLOW + "'" + GREEN + BOLD + "true" + YELLOW + "'" 
								+ GRAY 
								+ "  - Povoli flag\n"
								+ YELLOW + "'" + DARK_RED + BOLD + "f" + YELLOW + "'" + GRAY + " nebo " + YELLOW + "'" + DARK_RED + BOLD + "false" + YELLOW + "'" 
								+ GRAY 
								+ "  - Zakaze flag\n\n"
								+ GREEN + BOLD
								+ "TIP: "
								+ GRAY
								+ "Pokud chcete zmenic nastaveni v oblasti, na níz pravestojite,\n"
								+ GRAY
								+ "nemusite vypisovat nazev oblasti do prikazu.\n\n"
								+ GOLD + "Priklad: " + AQUA + ITALIC + BOLD + "/land pset jmenoHrace build true")
				.suggest("/land pset ").then(GRAY + "" + BOLD + "  <--- Pro vice info najed mysi!").toJSONString();
	}
	
	public static String landSetCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "land set")
				.tooltip(
						AQUA
								+ "" + BOLD
								+ "/land set [Jmeno pozemku] <Flag> <Hodnota> \n\n"
								+ GRAY
								+ "Nastavi na pozemku opravneni pro vsechny hrace\n\n"
								+ GREEN + BOLD + "Hodnoty:\n"
								+ YELLOW + "'" + AQUA + BOLD + "r" + YELLOW + "'" + GRAY + " nebo " + YELLOW + "'" + AQUA + BOLD + "remove" + YELLOW + "'" 
								+ GRAY 
								+ "  - Odstrani dane nastaveni a pouzije vychozi hodnoty \n"
								+ YELLOW + "'" + GREEN + BOLD + "t" + YELLOW + "'" + GRAY + " nebo " + YELLOW + "'" + GREEN + BOLD + "true" + YELLOW + "'" 
								+ GRAY 
								+ "  - Povoli flag\n"
								+ YELLOW + "'" + DARK_RED + BOLD + "f" + YELLOW + "'" + GRAY + " nebo " + YELLOW + "'" + DARK_RED + BOLD + "false" + YELLOW + "'" 
								+ GRAY 
								+ "  - Zakaze flag\n\n"
								+ GREEN + BOLD
								+ "TIP: "
								+ GRAY
								+ "Pokud chcete zmenic nastaveni v oblasti, na níz pravestojite,\n"
								+ GRAY
								+ "nemusite vypisovat nazev oblasti do prikazu.\n\n"
								+ GOLD + "Priklad: " + AQUA + ITALIC + BOLD + "/land set build true")
				.suggest("/land set ").then(GRAY + "" + BOLD + "  <--- Pro vice info najed mysi!").toJSONString();
	}
	
	public static String landInfoCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "land info")
				.tooltip(
						AQUA    
						        + "" 
						        + BOLD + "/land info\n\n"
								+ GRAY + "Zobrazi informace o miste, kde prave stojite\n\n"
				                + AQUA + BOLD + "/land info [Jmeno pozemku]\n\n"
								+ GRAY + "Zobrazi informace o danem pozemku\n\n"
								+ GOLD + "Priklad: " + AQUA + ITALIC + BOLD + "/land info testLand\n\n"
				                + AQUA + BOLD + "/land info [Jmeno pozemku].[Jmeno subzony] \n\n"
				                + GRAY + "Zobrazi informace o dane subzone\n\n"
				                + GOLD + "Priklad: " + AQUA + ITALIC + BOLD + "/land info testLand.testSubzone")
				.suggest("/land info ").then(GRAY + "" + BOLD + "  <--- Pro vice info najed mysi!").toJSONString();
	}
	
	public static String landRemoveCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "land remove")
				.tooltip(
						AQUA    
				                + "" 
				                + BOLD + "/land remove\n\n"
						        + GRAY + "Odstrani pozemek na nìmž prave stojite\n\n"
						        + AQUA 
						        + "" + BOLD + "/land remove <Jmeno pozemku>\n\n"
								+ GRAY + "Odstrani pozemek s danym jmenem \n\n"
								+ DARK_RED + "Pozor! " + GRAY
								+ "Tato akce je nevratná!\n\n" + GOLD
								+ GOLD + "Priklad: " + AQUA + ITALIC + BOLD + "/land remove testLand")
				.suggest("/land remove ").then(GRAY + "" + BOLD + "  <--- Pro vice info najed mysi!").toJSONString();
	}
	
	public static String landSelectCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "land select")
		.tooltip(
				AQUA
						+ ""
						+ BOLD
						+ "/land select <radius>\n\n"
						+ GRAY
						+ "Vybere vertikální oblast. Radius = vzdalenost mezi hranici oblasti a støedem\n\n"
		                + GOLD + "Priklad: " + AQUA + ITALIC + BOLD + "/land select 50")
		.suggest("/land select ").then(GRAY + "" + BOLD + "  <--- Pro vice info najed mysi!").toJSONString();
	}
	
	public static String landRenameCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "land rename")
				.tooltip(
						AQUA    
				                + "" 
				                + BOLD + "/land rename <Nove jmeno pozemku/subzony>\n\n"
						        + GRAY + "Prejmenuje pozemek na nìmž prave stojite\n\n"
						        + GOLD + "Priklad: " + AQUA + ITALIC + BOLD + "/land rename novyPozemek\n\n"
						        + AQUA 
						        + "" + BOLD + "/land rename [Aktualni jmeno pozemku] <Nove jmeno pozemku>\n\n"
								+ GRAY + "Prejmenuje pozemek s danym jmenem\n\n"
								+ GOLD + "Priklad: " + AQUA + ITALIC + BOLD + "/land rename staryPozemek novyPozemek")
				.suggest("/land rename ").then(GRAY + "" + BOLD + "  <--- Pro vice info najed mysi!").toJSONString();
	}

	public static String landTeleportCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "land tp")
				.tooltip(
						AQUA    
				                + "" 
				                + BOLD + "/land tp <Jmeno pozemku>\n\n"
								+ GRAY + "Teleportuje vas na dany pozemek\n\n"
								+ GOLD + "Priklad: " + AQUA + ITALIC + BOLD + "/land tp mujDomov")
				.suggest("/land tp ").then(GRAY + "" + BOLD + "  <--- Pro vice info najed mysi!").toJSONString();
	}

	public static String landTeleportSetCommand() {
		return new FancyMessage(DARK_GREEN + "/" + GOLD + "land tpset")
				.tooltip(
						AQUA    
				                + "" 
				                + BOLD + "/land tpset\n\n"
								+ GRAY + "Nastavi teleportacni lokaci na miste, kde prave stojite\n\n"
								+ DARK_RED + "Pozor! " + GRAY
				                + "Lokace musí byt vzdy na pozemku!\n\n" 
								+ GOLD + "Priklad: " + AQUA + ITALIC + BOLD + "/land tpset")
				.suggest("/land tpset ").then(GRAY + "" + BOLD + "  <--- Pro vice info najed mysi!").toJSONString();
	}
}
