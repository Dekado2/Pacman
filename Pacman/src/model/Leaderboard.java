package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Leaderboard implements Comparable<Leaderboard> {
	
	
	/**
	 *  the various stats included in player - nickname, score, level, time
	 */
	private PacmanPlayer player;
	/**
	 *  the date in which the player has reached this score
	 */
	private String date;
	
	/**
	 * Leaderboard Constructor
	 * @param player
	 * @param date
	 */
		public Leaderboard(PacmanPlayer player, String date)
		{
			this.player=player;
			this.date=date;
			
		}
	
	/**
	 * Leaderboard Constructor with today's date
	 * @param player
	 */
	public Leaderboard(PacmanPlayer player)
	{
		this.player=player;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date date=Calendar.getInstance().getTime();
		this.date=sdf.format(date);
		
	}
/**
 * compares leaderboards first by points (who has higher)
 * then by level (who reached a higher level)
 * lastly, by shorter game length
 */
	@Override
	public int compareTo(Leaderboard o) {
		if (this.player.getPoints()>o.getPlayer().getPoints())
			return -1;
		else if (this.player.getPoints()<o.getPlayer().getPoints())
			return 1;
		else 
			if (this.player.getLevel()>o.getPlayer().getLevel())
				return -1;
			else if (this.player.getLevel()<o.getPlayer().getLevel())
				return 1;
			else
				if (this.player.getGameLength().getElapsedInMillis()<o.getPlayer().getGameLength().getElapsedInMillis() && Math.abs(this.player.getGameLength().getElapsedInMillis()-o.getPlayer().getGameLength().getElapsedInMillis())>500)
					return -1;
				else if (this.player.getGameLength().getElapsedInMillis()>o.getPlayer().getGameLength().getElapsedInMillis()  && Math.abs(this.player.getGameLength().getElapsedInMillis()-o.getPlayer().getGameLength().getElapsedInMillis())>500)
					return 1;
		return 0;		
	}

	public PacmanPlayer getPlayer() {
		return player;
	}

	public String getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Leaderboard [Nickname=" + player.getNickName() + ", Score=" + player.getPoints() + ", Level=" + player.getLevel() + ", Game Duration=" + player.getGameLength().getElapsed() + ", date=" + date + "]";
	}	
	

}
