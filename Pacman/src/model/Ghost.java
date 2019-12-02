package model;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

import utils.E_Direction;

public class Ghost extends Entity implements Movable, Drawable{
    
	/**
	 * number of ghost out of maximum ghost number
	 */
	private int animationOrder;
	
	/**
	 * Ghost constructor
	 * @param tile
	 * @param animationDirection
	 * @param animationCount
	 * @param animationOrder
	 */
	public Ghost(Tile tile, E_Direction animationDirection, int animationCount, int animationOrder) {
		super(tile, animationDirection, animationCount);
		this.animationOrder=animationOrder;
		loadImages();
	}

	/**
	 * load images of ghost from drive to array
	 */
	@Override
	public void loadImages() {
		Image[] images = new Image[4];
		if (animationOrder==1)
		{
		    images[0] = new ImageIcon("pacpix/PermGhostALightBlue.png").getImage();
		    images[1] = new ImageIcon("pacpix/PermGhostBLightBlue.png").getImage();
		    images[2] = new ImageIcon("pacpix/PermGhostCLightBlue.png").getImage();
		    images[3] = new ImageIcon("pacpix/PermGhostDLightBlue.png").getImage();
		}
		else if (animationOrder==2)
		{
			images[0] = new ImageIcon("pacpix/PermGhostAPink.png").getImage();
			images[1] = new ImageIcon("pacpix/PermGhostBPink.png").getImage();
			images[2] = new ImageIcon("pacpix/PermGhostCPink.png").getImage();
			images[3] = new ImageIcon("pacpix/PermGhostDPink.png").getImage();
		}
		else if (animationOrder==3)
		{
			images[0] = new ImageIcon("pacpix/PermGhostAOrange.png").getImage();
			images[1] = new ImageIcon("pacpix/PermGhostBOrange.png").getImage();
			images[2] = new ImageIcon("pacpix/PermGhostCOrange.png").getImage();
			images[3] = new ImageIcon("pacpix/PermGhostDOrange.png").getImage();
		}
		else {
			images[0] = new ImageIcon("pacpix/PermGhostA.png").getImage();
			images[1] = new ImageIcon("pacpix/PermGhostB.png").getImage();
			images[2] = new ImageIcon("pacpix/PermGhostC.png").getImage();
			images[3] = new ImageIcon("pacpix/PermGhostD.png").getImage();
		}
		this.setImages(images);
		
	}

	/**
	 *  moves the ghost in a random direction
	 */
	@Override
	public void moveEntity() {
		Random random = new Random();
		int num;
		boolean isPreviousDirectionPossible=false;
		switch (super.getAnimationDirection()) { 
		case DOWN:
			moveDown();
			break;
		case LEFT:
			moveLeft();
			break;
		case RIGHT:
			moveRight();
			break;
		case UP:
			moveUp();
			break;
		}
		E_Direction dir = null;
		for (int b=0;b<Game.getInstance().getBoards().length;b++)
    		if (Game.getInstance().getBoards()[b]!=null && Game.getInstance().getBoards()[b].getGhosts().contains(this)) {
		dir=Game.getInstance().getBoards()[b].randomizeGhostDirection(super.getTile().getCoordX(),super.getTile().getCoordY());
		
		for (int i=0;i<Game.getInstance().getBoards()[b].getLayout()[super.getTile().getCoordX()]
				[super.getTile().getCoordY()].getPossibleDirections().length;i++)
		{
		  if (Game.getInstance().getBoards()[b].getLayout()[super.getTile().getCoordX()]
				  [super.getTile().getCoordY()].getPossibleDirections()[i].equals(super.getAnimationDirection()))
		  {
			  isPreviousDirectionPossible=true;
		      num = random.nextInt(3);
		      if (num==2)
			      setAnimationDirection(dir);
		      else
			      setAnimationDirection(super.getAnimationDirection());
		      break;
		  }
		}
    		}
		if (isPreviousDirectionPossible==false)
		setAnimationDirection(dir);
	}

}
