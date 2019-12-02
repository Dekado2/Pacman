package model;

import java.awt.Image;

import javax.swing.ImageIcon;

import utils.Constants;

public class CandySilver extends Candy implements Drawable {

	/**
	 * Silver Candy Constructor
	 * @param image
	 * @param points
	 */
	public CandySilver(Image image, int points) {
		super(image, points);
	}
	
	/**
	 * Silver Candy Constructor with current points rule
	 * @param image
	 */
		public CandySilver(Image image) {
			super(image, Constants.SILVER_CANDY_POINTS);
		}

		/**
		 *  load silver candy image from drive to object
		 */
		@Override
		public void loadImages() {
			super.setImage((new ImageIcon("pacpix/SilverCandy.png")).getImage());	
		}

}
