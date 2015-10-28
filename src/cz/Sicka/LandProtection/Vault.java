package cz.Sicka.LandProtection;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import org.bukkit.plugin.RegisteredServiceProvider;

public class Vault {
	private LandProtection plugin;
	public static Economy econ = null;
    public static Permission perms = null;
    public static Chat chat = null;
	
	public Vault(LandProtection plugin) {
		this.plugin = plugin;
		setupEconomy();
		setupChat();
		setupPermissions();
	}
	
	private boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = plugin.getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = plugin.getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

	/**
	 * @return the econ
	 */
	public static Economy getEcon() {
		return econ;
	}

	/**
	 * @return the perms
	 */
	public static Permission getPerms() {
		return perms;
	}

	/**
	 * @return the chat
	 */
	public static Chat getChat() {
		return chat;
	}
}
