package model;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

import utils.Constants;
import utils.E_Direction;

public class PacmanPlayer extends Entity implements Drawable {
	
	/**
	 *  the nickname of the player
	 */
	private String nickName;
    /**
     *  the current amount of points the player has gathered
     */
	private int points;
	/**
	 *  the amount of lives the player has before it's game over
	 */
	private int lifePoints;
	/**
	 *  the current stage that the player has reached
	 */
	private int level;
	/**
	 *  length of game
	 */
	private Timer gameLength;
	
	/**
	 *  PacmanPlayer constructor
	 * @param animationDirection
	 * @param nickName
	 * @param points
	 * @param lifePoints
	 * @param level
	 * @param gameLength
	 */
	public PacmanPlayer(E_Direction animationDirection, String nickName, int points, int lifePoints, int level, Timer gameLength) {
		super(animationDirection);
		this.nickName=nickName;
		this.points=points; // 0
		this.lifePoints=lifePoints; // 3
		this.level=level; // 1
		this.gameLength=gameLength; // 0
		loadImages();
	}
	
	/**
	 *  PacmanPlayer constructor with current rules
	 * @param animationDirection
	 * @param nickName
	 */
		public PacmanPlayer(E_Direction animationDirection, String nickName) {
			super(animationDirection);
			this.nickName=nickName;
			this.points=0;
			this.lifePoints=Constants.PACMAN_STARTING_LIVES;
			this.level=1;
			this.gameLength=new Timer(0, 0, false); // 0
			loadImages();
		}

	/**
	 *  load images of pacman from drive to array
	 */
	@Override
	public void loadImages() {
		Image[] images = new Image[13]; 
		images[0] = new ImageIcon("pacpix/PacMan1.png").getImage();
		images[1] = new ImageIcon("pacpix/PacMan2up.png").getImage();
		images[2] = new ImageIcon("pacpix/PacMan3up.png").getImage();
		images[3] = new ImageIcon("pacpix/PacMan4up.png").getImage();
		images[4] = new ImageIcon("pacpix/PacMan2down.png").getImage();
		images[5] = new ImageIcon("pacpix/PacMan3down.png").getImage();
		images[6] = new ImageIcon("pacpix/PacMan4down.png").getImage();
		images[7] = new ImageIcon("pacpix/PacMan2left.png").getImage();
		images[8] = new ImageIcon("pacpix/PacMan3left.png").getImage();
		images[9] = new ImageIcon("pacpix/PacMan4left.png").getImage();
		images[10] = new ImageIcon("pacpix/PacMan2right.png").getImage();
		images[11] = new ImageIcon("pacpix/PacMan3right.png").getImage();
		images[12] = new ImageIcon("pacpix/PacMan4right.png").getImage();
		this.setImages(images);
	}

	/**
	 *  move pacman to the next tile on the board according to player input
	 * @param dir
	 */
	public void moveEntity(E_Direction dir) {
		
		if (dir == E_Direction.LEFT) {
			moveLeft();
			setAnimationDirection(E_Direction.LEFT);
        } else if (dir == E_Direction.RIGHT) {
        	moveRight();
			setAnimationDirection(E_Direction.RIGHT);
        } else if (dir == E_Direction.UP) {
        	moveUp();
			setAnimationDirection(E_Direction.UP);
        } else if (dir == E_Direction.DOWN) {
        	moveDown();
			setAnimationDirection(E_Direction.DOWN);
        }
	}

	public String getNickName() {
		return nickName;
	}

	public int getPoints() {
		return points;
	}

	public int getLifePoints() {
		return lifePoints;
	}

	public int getLevel() {
		return level;
	}

	public Timer getGameLength() {
		return gameLength;
	}
	
	protected void setNickName(String nickName) {
		this.nickName = nickName;
	}

	protected void setPoints(int points) {
		this.points = points;
	}

	protected void setLifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}

	protected void setLevel(int level) {
		this.level = level;
	}

	protected void setGameLength(Timer gameLength) {
		this.gameLength = gameLength;
	}
	
}
