package model;

import java.awt.Image;

import javax.swing.ImageIcon;

import utils.Constants;

public class CandyNormal extends Candy implements Drawable {

	
	/**
	 * Normal Candy Constructor
	 * @param image
	 * @param points
	 */
	public CandyNormal(Image image, int points) {
		super(image, points);
	}
	
	/**
	 * Normal Candy Constructor with current points rule
	 * @param image
	 */
		public CandyNormal(Image image) {
			super(image, Constants.NORMAL_CANDY_POINTS);
		}
		
		/**
		 *  load normal candy image from drive to object
		 */
		@Override
		public void loadImages() {
			super.setImage((new ImageIcon("pacpix/NormalCandy.png")).getImage());
		}

}
