package me.shawshark.lilystatus;

import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;
import lombok.Getter;
import lombok.Setter;

import org.bukkit.event.Listener;

public class LilyListener implements Listener {
	
	@Getter@Setter private LilyStatus instance;
	
	public LilyListener(LilyStatus instance) {
		setInstance(instance);
	}
	
	@EventListener
	public void onMessage(MessageEvent event) {
		
		if(event.getChannel().equalsIgnoreCase(getInstance().getCoreManager().getShutdownChannel())) {
			if(getInstance().getCoreManager().getUsername().equalsIgnoreCase(event.getSender()))
				return;
			
			getInstance().getCoreManager().getServerManager().process(event.getSender(), 0, 0, false);
			return;
		}
		
		if(!event.getChannel().equalsIgnoreCase(getInstance().getCoreManager().getChannel()))
			return;
		
		String message = null;
		String[] data = null;
		try {
			
			message = event.getMessageAsString();
			data = message.split(getInstance().getCoreManager().getChannelSplit());
			
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		String username = data[0];
		int online = Integer.parseInt(data[1]);
		int max = Integer.parseInt(data[2]);
		getInstance().getCoreManager().getServerManager().process(username, online, max, true);
	}
}
