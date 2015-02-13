package me.shawshark.lilystatus.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class LilyServer {
	
	@Getter@Setter public boolean status;
	@Getter@Setter public String Username;
	@Getter@Setter public int online;
	@Getter@Setter public int max;
	
}
