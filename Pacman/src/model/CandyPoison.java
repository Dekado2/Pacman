package model;

import java.awt.Image;

import javax.swing.ImageIcon;

import utils.Constants;

public class CandyPoison extends Candy implements Drawable {

	/**
	 * Poisonous Candy Constructor
	 * @param image
	 * @param points
	 */
	public CandyPoison(Image image, int points) {
		super(image, points);
		setPacmanLives(new DecreaseLifePoints());
	}
	
	/**
	 * Poisonous Candy Constructor with current points rules
	 * @param image
	 */
		public CandyPoison(Image image) {
			super(image, Constants.POISON_CANDY_POINTS);
			setPacmanLives(new DecreaseLifePoints());
		}

	/**
	 *  load poisonous candy image from drive to object
	 */
	@Override
	public void loadImages() {
		super.setImage((new ImageIcon("pacpix/PoisonCandy.png")).getImage());
	}	
	
}
