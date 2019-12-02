package model;

import java.awt.Image;

import utils.E_Direction;

public abstract class Entity {
	
	/**
	 *  help variable for running sequence of id numbers
	 */
	private static volatile int entitiesCounter;
	/**
	 *  the entity's id
	 */
	private int id;
	/**
	 *  the tile on which the entity is currently located
	 */
	private volatile Tile tile;
    /**
     * the entity's current direction
     */
    private E_Direction animationDirection;
    /**
     * the entity's current animation number out of overall amount of animations
     */
    private int animationCount;
    /**
     * the images representing the entity
     */
    private Image[] images;
    
    /**
     *  Entity constructor
     * @param tile
     * @param animationDirection
     * @param animationCount
     */
    public Entity (Tile tile, E_Direction animationDirection, int animationCount)
    {
    	this.id=++entitiesCounter;
    	this.tile=tile;
    	this.animationDirection=animationDirection;
    	this.animationCount=animationCount;
    }
    
    /**
     *  Entity constructor without known tile yet - for pacman
     * @param animationDirection
     */
    public Entity(E_Direction animationDirection) {
    	this.id=++entitiesCounter;
    	this.animationDirection=animationDirection;
    	this.animationCount=0;
	}

	/**
	 *  move the entity upwards
	 */
    protected void moveUp()
    { 
    	
    	for (int i=0;i<Game.getInstance().getBoards().length;i++)
    		if (Game.getInstance().getBoards()[i]!=null && (Game.getInstance().getBoards()[i].getPlayer().getPacmanPlayer().getId()==id || Game.getInstance().getBoards()[i].getGhosts().contains(this))) {
    	Game.getInstance().getBoards()[i].getLayout()[tile.getCoordX()][tile.getCoordY()-1].addEntity(this);
    	tile = Game.getInstance().getBoards()[i].getLayout()[tile.getCoordX()][tile.getCoordY()-1];
    	Game.getInstance().getBoards()[i].getLayout()[tile.getCoordX()][tile.getCoordY()+1].removeEntity(this); 
    		}
    	

    }
    
    /**
     *  move the entity leftwards
     */
    protected void moveLeft()
    {
    	for (int i=0;i<Game.getInstance().getBoards().length;i++)
    		if (Game.getInstance().getBoards()[i]!=null && (Game.getInstance().getBoards()[i].getPlayer().getPacmanPlayer().getId()==id || Game.getInstance().getBoards()[i].getGhosts().contains(this))) {
    	Game.getInstance().getBoards()[i].getLayout()[tile.getCoordX()-1][tile.getCoordY()].addEntity(this);
    	tile = Game.getInstance().getBoards()[i].getLayout()[tile.getCoordX()-1][tile.getCoordY()];
    	Game.getInstance().getBoards()[i].getLayout()[tile.getCoordX()+1][tile.getCoordY()].removeEntity(this);
    		}
    	
    }
    
    /**
     *  move the entity rightwards
     */
    protected void moveRight()
    {
    	for (int i=0;i<Game.getInstance().getBoards().length;i++)
    		if (Game.getInstance().getBoards()[i]!=null && (Game.getInstance().getBoards()[i].getPlayer().getPacmanPlayer().getId()==id || Game.getInstance().getBoards()[i].getGhosts().contains(this))) {
    	Game.getInstance().getBoards()[i].getLayout()[tile.getCoordX()+1][tile.getCoordY()].addEntity(this);
    	tile = Game.getInstance().getBoards()[i].getLayout()[tile.getCoordX()+1][tile.getCoordY()];
    	Game.getInstance().getBoards()[i].getLayout()[tile.getCoordX()-1][tile.getCoordY()].removeEntity(this);
    		}
    	
    }
    
    /**
     *  move the entity downwards
     */
    protected void moveDown()
    {
    	for (int i=0;i<Game.getInstance().getBoards().length;i++)
    		if (Game.getInstance().getBoards()[i]!=null && (Game.getInstance().getBoards()[i].getPlayer().getPacmanPlayer().getId()==id || Game.getInstance().getBoards()[i].getGhosts().contains(this))) {
    	Game.getInstance().getBoards()[i].getLayout()[tile.getCoordX()][tile.getCoordY()+1].addEntity(this);
    	tile = Game.getInstance().getBoards()[i].getLayout()[tile.getCoordX()][tile.getCoordY()+1];
    	Game.getInstance().getBoards()[i].getLayout()[tile.getCoordX()][tile.getCoordY()-1].removeEntity(this);
    		}
    	
    }
    
	public int getId() {
		return id;
	}
	public Tile getTile() {
		return tile;
	}
	public E_Direction getAnimationDirection() {
		return animationDirection;
	}
	public int getAnimationCount() {
		return animationCount;
	}
	public Image[] getImages() {
		return images;
	}
	
	protected void setImages(Image[] images) {
		this.images = images;
	}

	public void setAnimationCount(int animationCount) {
		this.animationCount = animationCount;
	}

	public synchronized void setAnimationDirection(E_Direction animationDirection) {
		this.animationDirection = animationDirection;
	}
	
	protected void setTile(Tile tile) {
		this.tile = tile;
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
		Entity other = (Entity) obj;
		if (id != other.id)
			return false;
		return true;
	}
    
}
