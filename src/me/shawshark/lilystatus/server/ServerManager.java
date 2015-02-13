package me.shawshark.lilystatus.server;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;
import me.shawshark.lilystatus.LilyStatus;

public class ServerManager {

	@Getter@Setter public LilyStatus instance;
	@Getter public HashMap<String, LilyServer> servers;
	public ServerManager(LilyStatus instance) {
		setInstance(instance);
		servers = new HashMap<>();
	}
	
	public void process(String username, int online, int max, boolean status) {
		
		LilyServer server = null;
		if(getServers().containsKey(username)) {
			server = getServers().get(username);
			server.setOnline(online);
			server.setMax(max);
			server.setStatus(status);
			server.setUsername(username);
			return;
		}
		
		server = new LilyServer(status, username, online, max);
		getServers().put(username, server);
	}
}
