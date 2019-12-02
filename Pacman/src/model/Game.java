package model;

import utils.Constants;

public class Game {
	
	/**
	 *  single instance of a game at a time
	 */
	private static Game instance;
	/**
	 * list of boards that are part of the game, 1 or 2 depending on game mode
	 */
	private Board[] boards;
	
	/**
	 *  Game Constructor
	 */
	private Game()
	{
		boards = new Board[2];
	}
	
	/**
	 *  1 instance of a Game at a time with 1/2 boards
	 * @return
	 */
	public static Game getInstance()
	{
		if (instance == null)
			instance = new Game();
		return instance;
	}
	
	/**
	 * initialize game and boards depending if single player or multiplayer
	 * @param player1
	 * @param player2
	 */
	public void initiateGame(PacmanPlayerAdapter player1, PacmanPlayerAdapter player2)
	{
		//if multiplayer, initialize both boards, else first, same for adding players
		if (player2==null)
		boards[0] = new Board(player1);
		else 
		{
		boards[0] = new Board(player1);
		boards[1] = new Board(player2);
		}
	}
	
	/**
	 * check if the level has ended
	 * @param board
	 * @return
	 */
	public boolean checkMaze(Board board)
	{
		
		if (board!=null){
			for (Ghost g : board.getGhosts())
				if (g instanceof TempGhost)
			        return false;
		if (board.getCandies().size()==0)
			return true;
		else if (board.getCandies().size()==1) {
			for (Candy candy: board.getCandies())
				if (candy instanceof CandyPoison)
					return true;
		 } 
		}
			return false;
			
	}
	
	/**
	 *  continue to next level
	 * @param p
	 */
	public void continueLevel(PacmanPlayerAdapter p)
	{
		for (int i=0;i<boards.length;i++)
			if (boards[i]!=null && boards[i].getPlayer().equals(p)){
			boards[i] = new Board(p);
			boards[i].getPlayer().getPacmanPlayer().setPoints(p.getPacmanPlayer().getPoints()+Constants.BONUS_POINTS_END_LEVEL);
			boards[i].getPlayer().getPacmanPlayer().setLevel(p.getPacmanPlayer().getLevel()+1);
			}
	}

	public Board[] getBoards() {
		return boards;
	}

}
