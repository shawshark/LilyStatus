package me.shawshark.lilystatus.scoreboard;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import me.shawshark.lilystatus.LilyStatus;

public class ScoreBoardManager {
	
	@Getter@Setter private LilyStatus instance;
	@Getter@Setter private Updater updater;
	@Getter@Setter public String format, scoreboardTitle;
	@Getter@Setter public int refresh;
	@Getter@Setter public List<String> allowedservers = new ArrayList<>();
	@Getter@Setter public boolean showScoreboard = false, removeofflineserver;
	
	public ScoreBoardManager(LilyStatus instance) {
		setInstance(instance);
		init();
		if(showScoreboard)
			updater = new Updater(this);
	}
	
	private void init() {
		
		setFormat(getInstance().getConfig().getString("format"));
		setRefresh(getInstance().getConfig().getInt("refresh-time"));
		setScoreboardTitle(getInstance().getConfig().getString("scoreboard-title"));
		setShowScoreboard(getInstance().getConfig().getBoolean("show-scoreboard"));
		setRemoveofflineserver(getInstance().getConfig().getBoolean("remove-offline-server"));
		
		for(String server : getInstance().getConfig().getStringList("allowed-servers"))
			getAllowedservers().add(server);
		
	}
}
