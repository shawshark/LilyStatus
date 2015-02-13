package me.shawshark.lilystatus;

import lilypad.client.connect.api.Connect;
import lombok.Getter;
import lombok.Setter;

import me.shawshark.lilystatus.scoreboard.ScoreBoardManager;

import org.bukkit.plugin.java.JavaPlugin;

public class LilyStatus extends JavaPlugin {

	@Getter@Setter public Connect connect;
	@Getter@Setter public ScoreBoardManager manager;
	@Getter@Setter public CoreManager CoreManager;
	
	public void onEnable() {
		
		saveDefaultConfig();
		saveConfig();
		
		setConnect(getServer().getServicesManager().getRegistration(Connect.class).getProvider());
		getConnect().registerEvents(new LilyListener(this));
        setManager(new ScoreBoardManager(this));
        setCoreManager(new CoreManager(this));
	}
	
	public void onDisable() {
		getCoreManager().cancel();
		getManager().getUpdater().cancel();
		getCoreManager().shutdownrequest();
		setConnect(null);
		setManager(null);
		setCoreManager(null);
	}
}
