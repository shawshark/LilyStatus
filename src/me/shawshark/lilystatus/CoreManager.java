package me.shawshark.lilystatus;

import java.io.UnsupportedEncodingException;
import java.util.Collections;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import lilypad.client.connect.api.request.RequestException;
import lilypad.client.connect.api.request.impl.MessageRequest;
import lombok.Getter;
import lombok.Setter;
import me.shawshark.lilystatus.server.ServerManager;

public class CoreManager extends BukkitRunnable {
	
	@Getter@Setter private LilyStatus instance;
	@Getter@Setter public String Channel = "LilyStatus", ChannelSplit = "//:0://", Username, shutdownChannel = "LilyStatus.shutdown";
	@Getter@Setter public ServerManager serverManager;
	
	public CoreManager(LilyStatus instance) {
		setInstance(instance);
		setUsername(getInstance().getConnect().getSettings().getUsername());
		runTaskTimer(instance, 10, 60);
		setServerManager(new ServerManager(instance));
		setUsername(instance.getConnect().getSettings().getUsername());
	}
	
	public void sendMessage(String channel, String message) {
		try {
			
			if(!getInstance().getConnect().isConnected()) {
				System.out.println("[LilyStatus] Not connected to the cloud, Can't send message request!");
				return;
			}
			
			MessageRequest request = new MessageRequest(Collections.<String> emptyList(), channel, message);
			getInstance().getConnect().request(request); 
		} catch (UnsupportedEncodingException | RequestException e) {
			e.printStackTrace();
		} 
	}
	
	public void shutdownrequest() {
		sendMessage(getChannel(), getUsername() + getChannelSplit() + "SHUTDOWN");
	}

	@Override
	public void run() {
		
		StringBuilder builder = new StringBuilder();
		builder.append(getUsername());
		builder.append(getChannelSplit());
		builder.append(getInstance().getServer().getOnlinePlayers().size());
		builder.append(getChannelSplit());
		builder.append(getInstance().getServer().getMaxPlayers());
		builder.append(getChannelSplit());
		
		String t = "null";
		
		for(Player player : getInstance().getServer().getOnlinePlayers()) {
			if(t.equalsIgnoreCase("null")) {
				t = "notnull";
				builder.append(player.getName());
			} else {
				builder.append(",,," + player.getName());
			}
		}
		sendMessage(getChannel(), builder.toString());
	}
}
