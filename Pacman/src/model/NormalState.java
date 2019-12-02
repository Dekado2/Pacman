package model;

import java.util.HashSet;
import java.util.Iterator;

public class NormalState implements BoardState {
/**
 * the board associated with this state
 */
	private Board board;
	/**
	 * state constructor
	 * @param board
	 */
	public NormalState(Board board) {
		this.board=board;
	}
	
	/**
	 * normal's state version of pacmanQuestionCandyCollision method
	 * sets to question popped state
	 */
	@Override
	public void pacmanQuestionCandyCollision() {
		board.setBoardState(board.getQuestionPoppedState());
	}
	
	/**
	 * normal's state version of pacman ghost collision
	 */
	@Override
	public void pacmanGhostCollision() {
		board.getPlayer().getPacmanPlayer().setLifePoints(board.getPlayer().getPacmanPlayer().getLifePoints()-1);
		if (board.getPlayer().getPacmanPlayer().getLifePoints()>0)
		{
		board.getLayout()[board.getPlayer().getPacmanPlayer().getTile().getCoordX()][board.getPlayer().getPacmanPlayer().getTile().getCoordY()].removeEntity(board.getPlayer().getPacmanPlayer());
		board.getPlayer().getPacmanPlayer().getTile().removeEntity(board.getPlayer().getPacmanPlayer());
		board.clearPermGhosts();
		board.initializePermGhosts();
		board.initializePacman();
		}
	}
/**
 * normal's state version of controlling temp ghosts
 * removing all threads of temp ghosts and from the ghosts array of board
	 *  remove temp ghosts when returning to normal state, making them despawn
	 */
	@Override
	public void controlTempGhosts() { 
		Iterator<Ghost> iter = board.getGhosts().iterator();
		while (iter.hasNext()){
			Ghost g = iter.next();
			if (g instanceof TempGhost){
			board.getLayout()[g.getTile().getCoordX()][g.getTile().getCoordY()].removeEntity(g);
			iter.remove();
			}
		}		
	}

}
