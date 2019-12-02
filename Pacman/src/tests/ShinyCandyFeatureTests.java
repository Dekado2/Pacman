package tests;


import org.junit.Test;

import model.Board;
import model.PacmanPlayer;
import model.PacmanPlayerAdapter;
import model.Timer;
import utils.E_Direction;

public class ShinyCandyFeatureTests {
	
	PacmanPlayer p = new PacmanPlayer( E_Direction.UP, "a", 0, 3, 1, new Timer(0, 0, false));
	PacmanPlayerAdapter playerA= new PacmanPlayerAdapter(p);
	Board b= new Board(playerA);

	/** 
	 * testing whether shiny candy doubles the points of pacman or resets to 0 if he has a negative number
	 */
	@Test
	public void ShinyCandyEaten() {
	
	}

}
