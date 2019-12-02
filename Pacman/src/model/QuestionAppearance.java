package model;

import java.util.Random;
/**
 * 
 * interface for question appearance option
 * made for collision with question candy, to determine whether and which question pops upon collision
 *
 */
public interface QuestionAppearance {

   public Question popQuestion();
}
/**
 * 
 * upon collision with question candy, selects a random question from the question pool
 *
 */
class PopsQuestion implements QuestionAppearance {

	
	public Question popQuestion() {
			Random random = new Random();
			int value = random.nextInt(SysData.getInstance().getQuestionsList().size());
			return SysData.getInstance().getQuestionsList().get(value);
		}
	}
