package model;
/**
 * 
 * 
 * possible board states interface, with methods related to those states
 */
public interface BoardState {
	
	public void pacmanQuestionCandyCollision();
	
	public void pacmanGhostCollision();
	
	public void controlTempGhosts();

}
