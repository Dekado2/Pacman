package model;

import java.util.ArrayList;

import utils.E_DifficultyLevel;

public class Question {
	
	/**
	 *  help variable for running sequence of id numbers
	 */
	private static volatile int questionsCounter;
	/**
	 *  the question's id
	 */
	public int qID;
	/**
	 *  the team that created the question
	 */
	private String teamName;
	/**
	 *  the question itself
	 */
	private String qText;
	/**
	 * the question's level of difficulty
	 */
	private E_DifficultyLevel difficultyLevel;
	/**
	 *  the question's set of possible answers
	 */
	private ArrayList<Answer> possibleAnswers;
	/**
	 * the correct answer for this question
	 */
	private Answer correctAnswer;

	/**
	 * Question constructor
	 * @param teamName
	 * @param qText
	 * @param difficultyLevel
	 */
	public Question( String teamName, String qText, E_DifficultyLevel difficultyLevel)
	{
		
		this.qID=++questionsCounter;
		this.teamName=teamName;
		this.qText=qText;
		this.difficultyLevel=difficultyLevel;
		possibleAnswers = new ArrayList<Answer>();
	}
	/**
	 * Question constructor
	 * @param qID
	 */
		public Question(int qID)
		{
			this.qID=qID;
		}
	/**
	 *  add a possible answer to the question, up to 4 possible answers
	 * @param answer
	 * @return
	 */
	public boolean addAnswer(Answer answer)
	{
		if (possibleAnswers.size()<4 && !possibleAnswers.contains(answer))
		{
			possibleAnswers.add(answer);
			return true;
		}
		return false;
	}

	/**
	 *  edit an existing possible answer of the question
	 * @param answer
	 * @return
	 */
	public boolean editAnswer(Answer answer)
	{
		Answer ansToUpdate = possibleAnswers.get(possibleAnswers.indexOf(answer));
		if (ansToUpdate!=null)
		{
			ansToUpdate.setAText(answer.getAText());
			return true;
		}
		return false;
	}
	/**
	 * 
	 *  remove a possible answer from the question, minimum of 2 possible answers
	 * @param answer
	 * @return
	 */
	public boolean removeAnswer(Answer answer)
	{
		if (possibleAnswers.size()>2 && possibleAnswers.contains(answer))
		{
			possibleAnswers.remove(answer);
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @return qID
	 */
	public int getQID() {
		return qID;
	}

	/**
	 * 
	 * @return teamName
	 */
	public String getTeamName() {
		return teamName;
	}
	/**
	 * 
	 * @return qText
	 */
	public String getQText() {
		return qText;
	}
	/**
	 * 
	 * @return difficultyLevel
	 */
	public E_DifficultyLevel getDifficultyLevel() {
		return difficultyLevel;
	}
	/**
	 * 
	 * @return possibleAnswers
	 */
	public ArrayList<Answer> getPossibleAnswers() {
		return possibleAnswers;
	}
	/**
	 * 
	 * @return correctAnswer
	 */
	public Answer getCorrectAnswer() {
		return correctAnswer;
	}
	/**
	 * set Correct Answer
	 * @param correctAnswer
	 */
	protected void setCorrectAnswer(Answer correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	@Override
	public String toString() {
		return "Question [qID=" + qID + ", teamName=" + teamName + ", qText=" + qText + ", difficultyLevel="
				+difficultyLevel + ", possibleAnswers=" + possibleAnswers + ", correctAnswer= " + correctAnswer + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + qID;
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
		Question other = (Question) obj;
		if (qID != other.qID)
			return false;
		return true;
	}


}
