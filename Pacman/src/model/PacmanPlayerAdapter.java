package model;

import utils.E_Direction;
/**
 * pacman player adapter interface to allow pacman to recieve a direction and move based on it
 */
public class PacmanPlayerAdapter implements Movable {
	
	private PacmanPlayer pacmanPlayer;
	
	private E_Direction direction;
	
	/**
	 * constructor for PacmanPlayer Adapter
	 * @param pacmanPlayer
	 */
	public PacmanPlayerAdapter (PacmanPlayer pacmanPlayer) {
		this.pacmanPlayer=pacmanPlayer;
	}
    /**
     * adjust move entity from interface to accept an input to decide in which direction will pacman move
     */
	@Override
	public void moveEntity() {
		pacmanPlayer.moveEntity(direction);
		
	}

	public PacmanPlayer getPacmanPlayer() {
		return pacmanPlayer;
	}
	
	protected E_Direction getDirection() {
		return direction;
	}
	
	
	
	

}
