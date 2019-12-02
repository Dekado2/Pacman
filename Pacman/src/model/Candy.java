package model;

import java.awt.Image;

public abstract class Candy {
	
	/**
	 *  help variable for running sequence of id numbers
	 */
		private static volatile int candiesCounter;
	/**
	 *  the candy's id
	 */
	private int id;
	/**
	 *  the candy's image
	 */
	private Image image;
	/**
	 *  the amount of points the candy awards
	 */
	private int points;
    /**
     * determines whether pacman gets extra lives upon eating candy or loses them and how many
     */
	private AffectsPacmanLifePoints pacmanLives;
	/**
	 * determines whether a question pops when the candy is eaten and which one
	 */
	private QuestionAppearance question;
	

	/**
	 * Candy Constructor
	 * @param image
	 * @param points
	 */
	public Candy(Image image, int points)
	{
		this.id=++candiesCounter;
		this.image=image;
		this.points=points;
	}
	/**
	 * get id
	 * @return
	 */
	public int getId() {
		return id;
	}
    /**
     * get image
     * @return
     */
	public Image getImage() {
		return image;
	}
/**
 * set image
 * @param image
 */
	public void setImage(Image image) {
		this.image = image;
	}
/**
 * get points
 * @return
 */
	public int getPoints() {
		return points;
	}
	
	/**
	 * get pacman lives
	 * @return
	 */
	public AffectsPacmanLifePoints getPacmanLives() {
		return pacmanLives;
	}
 /**
  * set pacman lives
  * @param pacmanLives
  */
	protected void setPacmanLives(AffectsPacmanLifePoints pacmanLives) {
		this.pacmanLives = pacmanLives;
	}
/**
 * get question
 * @return
 */
	public QuestionAppearance getQuestion() {
		return question;
	}
/**
 * set question
 * @param question
 */
	protected void setQuestion(QuestionAppearance question) {
		this.question = question;
	}
/**
 * affect life points of pacman
 * @param num
 * @return
 */
	public int affectLifePoints(int num) {
		return pacmanLives.affectLifePoints(num);
	}
	/**
	 * set life points effect
	 * made for collision with candy, potentially decrease a life point or increase
	 * @param newLifePointsAffect
	 */
	public void setLifePointEffect(AffectsPacmanLifePoints newLifePointsAffect) {
		pacmanLives = newLifePointsAffect;
	}
	/**
	 * pop question upon collision with candy
	 * selects question randomly from the pool of questions in json
	 * @return
	 */
	public Question popQuestion () {
		return question.popQuestion();
	}
	/**
	 * determines whether a candy pops a question upon collision
	 * @param newQuestionAppearanceDecision
	 */
	public void setQuestionAppearance(QuestionAppearance newQuestionAppearanceDecision) {
		question = newQuestionAppearanceDecision;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Candy other = (Candy) obj;
		if (id != other.id)
			return false;
		return true;
	}
/**
 * toString of candy
 */
	@Override
	public String toString() {
		return "Candy [id=" + id + ", points=" + points + "]";
	}
	
}
