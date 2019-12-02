package model;

import java.awt.Image;

import javax.swing.ImageIcon;

import utils.Constants;

public class CandyShiny extends Candy implements Drawable {
	
	private Timer candyTimer; 

	public CandyShiny(Image image, int points, Timer candyTimer) {
		super(image, points);
		this.candyTimer=candyTimer;
		
	}
	
	public CandyShiny(Image image) {
		super(image, Constants.SHINY_CANDY_POINTS);
		this.candyTimer=new Timer(0, 0, false);
	}

	@Override
	public void loadImages() {
		super.setImage((new ImageIcon("pacpix/ShinyCandy.png")).getImage());
		
	}

	public Timer getCandyTimer() {
		return candyTimer;
	}
}
