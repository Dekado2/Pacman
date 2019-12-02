package model;

import java.awt.Image;

import javax.swing.ImageIcon;

import utils.Constants;

public class CandyQuestion extends Candy implements Drawable {
	

	/**
	 * Question Candy Constructor
	 * @param image
	 * @param points
	 */
	public CandyQuestion(Image image, int points) {
		super(image, points);
		setQuestion(new PopsQuestion());
	}
	
	/**
	 * Question Candy Constructor with current points rule
	 * @param image
	 */
		public CandyQuestion(Image image) {
			super(image, Constants.QUESTION_CANDY_INITIAL_POINTS);
			setQuestion(new PopsQuestion());
		}
	
	/**
	 *  load question candy image from drive to object
	 */
	@Override
	public void loadImages() {
		super.setImage((new ImageIcon("pacpix/QuestionCandy.png")).getImage());	
	}

}
