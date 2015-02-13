package me.shawshark.lilystatus.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import lombok.Getter;
import lombok.Setter;
import me.shawshark.lilystatus.server.LilyServer;

public class Updater extends BukkitRunnable {
	
	@Getter@Setter private ScoreBoardManager manager;
	@Getter@Setter private ScoreboardManager scoreboard;
	@Getter@Setter private Scoreboard sb;
	@Getter@Setter private Objective ob;
	
	public Updater(ScoreBoardManager manager) {
		setManager(manager);
		setScoreboard(getManager().getInstance().getServer().getScoreboardManager());
		setSb(scoreboard.getNewScoreboard());
		setOb(sb.registerNewObjective("Scoreboard", "dummy"));
		runTaskTimer(manager.getInstance(), manager.getRefresh() * 20, manager.getRefresh() * 20);
	}

	@Override
	public void run() {		
		
		ob.setDisplayName(ChatColor.translateAlternateColorCodes('&', getManager().getScoreboardTitle()));
		ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		try {
			
			for(LilyServer server : getManager().getInstance().getCoreManager().getServerManager().getServers().values()) {
				
				if(!getManager().getAllowedservers().contains(server.getUsername()))
					continue;
				
				if(getManager().isRemoveofflineserver()) {
					if(!server.isStatus())
						continue;
					Score text = ob.getScore(ChatColor.translateAlternateColorCodes('&', getManager().getFormat().replace("{server}", server.getUsername())));
					text.setScore(server.getOnline());
				} else {
					Score text = ob.getScore(ChatColor.translateAlternateColorCodes('&', getManager().getFormat().replace("{server}", server.getUsername())));
					text.setScore(server.getOnline());
				}
			}
			
			for(Player player : getManager().getInstance().getServer().getOnlinePlayers())
				player.setScoreboard(sb);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
