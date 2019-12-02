package tests;

import static org.junit.Assert.*;

import model.Answer;
import model.Board;
import model.CandyNormal;
import model.CandyPoison;
import model.PacmanPlayer;
import model.PacmanPlayerAdapter;
import model.Question;
import model.SysData;
import model.Tile;
import model.Timer;
import utils.E_DifficultyLevel;
import utils.E_Direction;

public class Test {
	
	SysData instance = SysData.getInstance();
	/**
	 * test if loadQuestions function is working
	 */
	@org.junit.Test
	public void loadQuestionsTest() {
		assertEquals(true, instance.loadQuestions());
	}
	/**
	 * test if saveQuestions function is working
	 */
	@org.junit.Test
	public void saveQuestionsTest() {
		assertEquals(true, instance.saveQuestions());
	}

	//variables declaration
	CandyNormal c = new CandyNormal(null,1);
	Tile t = new Tile(1, 1, false, c);
	PacmanPlayer p = new PacmanPlayer( E_Direction.UP, "a", 0, 3, 1, new Timer(0, 0, false));
	PacmanPlayerAdapter playerA= new PacmanPlayerAdapter(p);
	Board b= new Board(playerA);

	/**
	 * test candy eating - should verify if pacman points will be updated after eating a candy
	 */
	@org.junit.Test
	public void eatCandyTest(){
		int points = p.getPoints();
		b.eatCandy(c);
		assertEquals(points+c.getPoints(), b.getPlayer().getPacmanPlayer().getPoints());
	}
	/**
	 * test poison candy eating - should verify if pacman life points will be updated after eating a candy
	 */
	@org.junit.Test
	public void eatPoisonCandyTest(){
		int lPoints = p.getLifePoints();
		CandyPoison c = new CandyPoison(null,-1);
		b.eatCandy(c);
		assertEquals(lPoints-1, b.getPlayer().getPacmanPlayer().getLifePoints());
	}
	/**
	 * test poison candy eating - should verify if pacman points will be updated after eating a candy
	 */
	@org.junit.Test
	public void eatPoisonCandyTest2(){
		int points = p.getPoints();
		CandyPoison c = new CandyPoison(null,-1);
		b.eatCandy(c);
		assertEquals(points-1, b.getPlayer().getPacmanPlayer().getPoints());
	}

	//variables declaration
	Question q = new Question( "q", "q", E_DifficultyLevel.HARD);
	Answer a = new Answer(1, "a");
	@org.junit.Test
	public void addAnswerTOQuestionTest(){
		assertTrue(q.addAnswer(a));
	}

	/**
	 * test remove an answer from a question - should verify that we can't remove an answer from a question with less then 2 answers
	 */
	@org.junit.Test
	public void removeAnswerFromQuestionTest(){
		q.addAnswer(a);
		//should not remove answer because there is a number of minimum answers per questions
		assertFalse(q.removeAnswer(a));
	}
	/**
	 * test remove an answer from a question - should verify that the answer was removed
	 */
	@org.junit.Test
	public void removeAnswerFromQuestionTest2(){
		q.addAnswer(a);
		Answer b = new Answer(2, "a");
		Answer c = new Answer(3, "a");
		Answer d = new Answer(4, "a");
		assertFalse(q.removeAnswer(a));
	}



}
