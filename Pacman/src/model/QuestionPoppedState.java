package model;

import java.util.Iterator;

import utils.Constants;

public class QuestionPoppedState implements BoardState {
    /**
     *  the board in which the question candy was eaten and thus lead to this state
     */
	private Board board;
	/**
	 *  the question that lead to this state
	 */
	private Question question;
	/**
	 * QuestionPoppedState Constructor
	 * @param board
	 */
	public QuestionPoppedState(Board board) {
		this.board=board;
	}
	
	/**
	 * questionPopped's state version of pacmanQuestionCandyCollision method
	 * stops previous temp ghosts and removes their threads, then spawning new ones relevant to 
	 * the new question popped
	 */
	@Override
	public void pacmanQuestionCandyCollision() { // replaces previous question with new one, previous question being wasted
		controlTempGhosts();
		
	}
	/**
	 *
	 * questionPopped's state version of pacman ghost collision
	 * includes consequences for collision with normal ghost, correct answer and wrong answer temp ghosts
	 */
	@Override
	public void pacmanGhostCollision() {
		Ghost ghost=null;
		TempGhost tempGhost=null;
		boolean isCollision=false;
		isCollision = board.getLayout()[board.getPlayer().getPacmanPlayer().getTile().getCoordX()][board.getPlayer().getPacmanPlayer().getTile().getCoordY()].checkPacmanGhostCollision();
		if (isCollision==true)
		{
			for (Entity ent: board.getLayout()[board.getPlayer().getPacmanPlayer().getTile().getCoordX()][board.getPlayer().getPacmanPlayer().getTile().getCoordY()].getEntities())
			{
				if (ent instanceof Ghost && (!(ent instanceof TempGhost)))
					ghost = (Ghost) ent;
				else if (ent instanceof TempGhost)
					tempGhost= (TempGhost) ent;
			}
			
			if (ghost!=null)
			{
				board.setBoardState(board.getNormalState()); // returning to normal state
				board.pacmanGhostCollision();
				board.controlTempGhosts();
			}
			
			else if (ghost==null && tempGhost!=null)
			{
				if (board.getLayout()[board.getPlayer().getPacmanPlayer().getTile().getCoordX()][board.getPlayer().getPacmanPlayer().getTile().getCoordY()].getCandy()!=null)
					board.eatCandy(board.getLayout()[board.getPlayer().getPacmanPlayer().getTile().getCoordX()][board.getPlayer().getPacmanPlayer().getTile().getCoordY()].getCandy());
				if (tempGhost.getAnswer().equals(question.getCorrectAnswer()))
				{
					switch (question.getDifficultyLevel().getDiff()) {
					case 1:
						board.getPlayer().getPacmanPlayer().setPoints(board.getPlayer().getPacmanPlayer().getPoints() + Constants.CORRECT_EASY_ANSWER);
						break;
					case 2:
						board.getPlayer().getPacmanPlayer().setPoints(board.getPlayer().getPacmanPlayer().getPoints() + Constants.CORRECT_MEDIUM_ANSWER);
						break;
					case 3:
						board.getPlayer().getPacmanPlayer().setPoints(board.getPlayer().getPacmanPlayer().getPoints() + Constants.CORRECT_HARD_ANSWER);
						break;
						default: 
					break;
					}
					board.setBoardState(board.getNormalState());
					board.controlTempGhosts();
				}
				else
				{
					switch (question.getDifficultyLevel().getDiff()) {
					case 1:
						board.getPlayer().getPacmanPlayer().setPoints(board.getPlayer().getPacmanPlayer().getPoints() - Constants.WRONG_EASY_ANSWER);
						break;
					case 2:
						board.getPlayer().getPacmanPlayer().setPoints(board.getPlayer().getPacmanPlayer().getPoints() - Constants.WRONG_MEDIUM_ANSWER);
						break;
					case 3:
						board.getPlayer().getPacmanPlayer().setPoints(board.getPlayer().getPacmanPlayer().getPoints() - Constants.WRONG_HARD_ANSWER);
						break;
						default: 
					break;
					}
					board.setBoardState(board.getNormalState());
					board.pacmanGhostCollision();
					board.controlTempGhosts();
				}
			}
		}
	}
	
/**
 *
 * questionPopped's state version of controlling temp ghosts
 * removing all temp ghosts from the ghosts array of board
 * then it spawns new temp ghosts in relation to the new question that popped
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
		question = board.initializeTempGhosts();
	}
	
	public Question getQuestion() {
		return question;
	}
	
}
