package cz.Sicka.LandProtection.Commands;

public class CommandMessage {
	public final static String NoPermissions = "&cNemate opravneni k provedeni prikazu!";
	public final static String NoPermissionsToTeleport = "&cNemate opravneni k teleportování na tuto oblast!";
	public final static String LandDoesNotExist = "&cParcela s timto jmenem neexistuje!";
	public final static String SubzoneDoesNotExist = "&cSubzona s timto jmenem neexistuje!";
	public final static String LandSuccesTeleport = "&aByl jste uspesne teleportovan na pozemek &5";
	public final static String LandTeleportFailed = "&cZ nejakeho duvodu jste nemoh byt teleportovan na pozemek &5";
	public final static String LandSuccesfulTeleportSet = "&aTeleportacni lokace byla uspesne zmenena";
	public final static String IncorrectCommandArgs = "&cNespravne zadane argumenty!&e Zkontrolujte prosim jejich spravnost!";
	public final static String LandAlreadyExist = "&cPozemek s timto jmenem jiz existuje!&e Zvolte prosim jine jmeno!";
	public final static String WorldDisableCreation = "&cNa tomto svete neni mozne vytvareni pozemku!"
			+ "<New_Line>&ePokud si chcete vytvorit pozemek, jdete na svet se spawnem!";
	public final static String IncompleteSelection = "&cDefinujte prosim oblast pozemku!";
	public final static String LandCollision = "&4Pozor! &ePozemek zasahuje do jineho pozemku!"
			+ "<New_Line>&e Prosím upravte vybranou oblast tak, aby nezasahovala do dalsich pozemku.";
	
	public final static String LandSuccesfulRemove = "&aOblast byla uspesne smazana";
	public final static String FlagSuccesfulBanned = "&aFlag byl uspesne zakazan";
	public final static String FlagSuccesfulTrusted = "&aFlag byl uspesne povolena";
	public final static String FlagSuccesfulRemoved = "&aFlag byl uspesne odstranen";
	public final static String SubzoneSuccesfulRemove = "&aSubzona byla uspesne smazana";
	public final static String LandSuccesfulRename = "&aOblast byla uspesne prejmenovana";
	public final static String LandRenameFailed = "&4Pozor! &cOblast z nejakeho duvodu nemohla byt prejmenovana! &eKontaktujte prosim admina";
	
	public final static String TooLarge = "&4Pozor! &ePrekrocil jste maximalni povolenou rozlohu!";
	public final static String TooNarrow = "&4Pozor! &ePrekrocil jste minimalni povolenou sirku oblasti!";
	public final static String TooShort = "&4Pozor! &ePrekrocil jste minimalni povolenou delku oblasti!";
	public final static String TooLow = "&4Pozor! &ePrekrocil jste minimalni povolenou vysku oblasti!";
	public final static String TooManyLands = "&4Pozor! &eNemuzete zabirat zadne dalsi pozemky!";
	public final static String LandSuccesfulOccupied = "&ePozemek byl uspesne zabran!";
	public final static String NotLocatedOnLand = "Nenachazite se na zadnem pozemku!";
	
	public final static String UseAlphaNumeric = "&4Pozor! &cChybny nazev pozemku! &ePouzivejte prosim alfanumerické znaky";
	
	public final static String UnknownPlayer = "&cHrac s timto jmenem nebyl nalezen!";
	public final static String UnknownFlag = "&cTento flag neexistuje!";
	public final static String UnknownBooleanValue = "&cChybne zadana hodnota flagu! &ePouzijte:"
			+ "<New_Line>&e'&br&e' nebo '&bremove&e' pro odstraneni flagu"
			+ "<New_Line>&e'&at&e' nebo '&atrue&e' pro povoleni flagu"
			+ "<New_Line>&e'&4f&e' nebo '&4false&e' pro zakazani flagu";
}
