package model;

import java.util.HashSet;

import utils.E_Direction;

public class Tile {
	
	/**
	 *  the tile's row number
	 */
	private  int coordX;
	/**
	 *  the tile's column number
	 */
	private  int coordY;
	/**
	 *  determines whether the tile is a wall (must check if it should be isWall or isReachable for areas that are blocked by walls) , might be redundant
	 */
	private boolean isWall;
	/**
	 *  the type of candy that this tile contains (maybe volatile? to make sure different threads realize candy was eaten, therefore ghosts might become prey)
	 */
	private Candy candy;
	/**
	 *  the entities that are currently standing on this tile
	 */
	private volatile HashSet<Entity> entities;
	/**
	 *  the directions in which it is possible to move to from this tile
	 */
	private  E_Direction[] possibleDirections;
	
	/**
	 *  constructor for Tile
	 * @param coordX
	 * @param coordY
	 * @param isWall
	 * @param candy
	 */
	public Tile (int coordX, int coordY, boolean isWall, Candy candy)
	{
		this.coordX=coordX;
		this.coordY=coordY;
		this.isWall=isWall;
		this.candy=candy;
		entities = new HashSet<Entity>();
		initDirections();
	}

	/**
	 *  initialize possible directions to move to from this tile
	 */
	private void initDirections() {
		switch (coordX) {
		case 0:
			if (coordY==0)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.RIGHT;
			}
			else if ((coordY>=1 && coordY<=5) || coordY==7 || (coordY>=9 && coordY<=16) || coordY==18)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.UP;
			}
			else if (coordY==6 || coordY==8 || coordY==17)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.UP;
				possibleDirections[2] = E_Direction.RIGHT;
			}
			else if (coordY==19)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.UP;
				possibleDirections[1] = E_Direction.RIGHT;
			}
			break;
		case 1:
		case 18:
			if (coordY==0 || coordY==6 || coordY==8 || coordY == 17 || coordY==19)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.LEFT;
				possibleDirections[1] = E_Direction.RIGHT;
			}
			break;
		case 2:
			if (coordY==0 || coordY==6 || coordY==19)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.LEFT;
				possibleDirections[1] = E_Direction.RIGHT;
			}
			else if (coordY==3)
			{
				possibleDirections = new E_Direction[1];
				possibleDirections[0] = E_Direction.RIGHT;
			}
			else if (coordY==8)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.LEFT;
				possibleDirections[1] = E_Direction.DOWN;
			}
			else if (coordY==9 || coordY==11 || coordY==13 || coordY==15)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.UP;
				possibleDirections[2] = E_Direction.RIGHT;
			}
			else if (coordY==10 || coordY==12 || coordY==14 || coordY==16)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.UP;
			}
			else if (coordY==17)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.LEFT;
				possibleDirections[1] = E_Direction.UP;
				possibleDirections[2] = E_Direction.RIGHT;
			}
			break;
		case 3:
		case 4:
		case 15:
		case 16:
			if (coordY==0 || coordY==3 || coordY==6 || coordY==9 || coordY==11 || coordY==13 || coordY==15 || coordY==17 || coordY==19)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.LEFT;
				possibleDirections[1] = E_Direction.RIGHT;
			}
			break;
		case 5:
			if (coordY==0 || coordY == 13 || coordY==17)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.LEFT;
				possibleDirections[2] = E_Direction.RIGHT;
			}
			else if ((coordY>=1 && coordY<=2) || (coordY>=4 && coordY<=5) || (coordY>=7 && coordY<=8) || coordY==14 || coordY==18)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.UP;
			}
			else if (coordY==3 || coordY==6)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.LEFT;
				possibleDirections[2] = E_Direction.UP;
			}
			else if (coordY==9 || coordY==15 || coordY==19)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.UP;
				possibleDirections[1] = E_Direction.LEFT;
				possibleDirections[2] = E_Direction.RIGHT;
			}
			else if (coordY==11)
			{
				possibleDirections = new E_Direction[1];
				possibleDirections[0] = E_Direction.LEFT;
			}
			break;
		case 6:
		case 13:
			if (coordY==0 || coordY==9 || coordY==13 || coordY==19)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.LEFT;
				possibleDirections[1] = E_Direction.RIGHT;
			}
			else if (coordY==15)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.LEFT;
				possibleDirections[2] = E_Direction.RIGHT;
			}
			else if (coordY==16)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.UP;
			}
			else if (coordY==17)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.UP;
				possibleDirections[1] = E_Direction.LEFT;
				possibleDirections[2] = E_Direction.RIGHT;
			}
			break;
		case 7:
			if (coordY==0)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.LEFT;
				possibleDirections[2] = E_Direction.RIGHT;
			}
			else if ((coordY>=1 && coordY<=8) || (coordY>=10 && coordY<=12))
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.UP;
			}
			else if (coordY==9)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.LEFT;
				possibleDirections[2] = E_Direction.UP;
			}
			else if (coordY==13)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.RIGHT;
				possibleDirections[1] = E_Direction.LEFT;
				possibleDirections[2] = E_Direction.UP;
			}
			else if (coordY==15 || coordY==17 || coordY==19)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.LEFT;
				possibleDirections[1] = E_Direction.RIGHT;
			}
			break;
		case 8:
		case 11:
			if (coordY==0 || coordY==17 || coordY==19)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.LEFT;
				possibleDirections[1] = E_Direction.RIGHT;
			}
			else if (coordY==13)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.RIGHT;
				possibleDirections[1] = E_Direction.LEFT;
				possibleDirections[2] = E_Direction.DOWN;
			}
			else if (coordY==14)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.UP;
			}
			else if (coordY==15)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.RIGHT;
				possibleDirections[1] = E_Direction.LEFT;
				possibleDirections[2] = E_Direction.UP;
			}
			break;
		case 9:
		case 10:
			if (coordY==0 || coordY==13 || coordY==15 || coordY==17 || coordY==19)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.LEFT;
				possibleDirections[1] = E_Direction.RIGHT;
			}
			break;
		case 12:
			if (coordY==0)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.LEFT;
				possibleDirections[2] = E_Direction.RIGHT;
			}
			else if ((coordY>=1 && coordY<=8) || (coordY>=10 && coordY<=12))
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.UP;
			}
			else if (coordY==9)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.RIGHT;
				possibleDirections[2] = E_Direction.UP;
			}
			else if (coordY==13)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.RIGHT;
				possibleDirections[1] = E_Direction.LEFT;
				possibleDirections[2] = E_Direction.UP;
			}
			else if (coordY==15 || coordY==17 || coordY==19)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.LEFT;
				possibleDirections[1] = E_Direction.RIGHT;
			}
			break;
		case 14:
			if (coordY==0 || coordY == 13 || coordY==17)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.LEFT;
				possibleDirections[2] = E_Direction.RIGHT;
			}
			else if ((coordY>=1 && coordY<=2) || (coordY>=4 && coordY<=5) || (coordY>=7 && coordY<=8) || coordY==14 || coordY==18)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.UP;
			}
			else if (coordY==3 || coordY==6)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.RIGHT;
				possibleDirections[2] = E_Direction.UP;
			}
			else if (coordY==9 || coordY==15 || coordY==19)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.UP;
				possibleDirections[1] = E_Direction.LEFT;
				possibleDirections[2] = E_Direction.RIGHT;
			}
			else if (coordY==11)
			{
				possibleDirections = new E_Direction[1];
				possibleDirections[0] = E_Direction.RIGHT;
			}
			break;
		case 17:
			if (coordY==0 || coordY==6 || coordY==19)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.LEFT;
				possibleDirections[1] = E_Direction.RIGHT;
			}
			else if (coordY==3)
			{
				possibleDirections = new E_Direction[1];
				possibleDirections[0] = E_Direction.LEFT;
			}
			else if (coordY==8)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.RIGHT;
				possibleDirections[1] = E_Direction.DOWN;
			}
			else if (coordY==9 || coordY==11 || coordY==13 || coordY==15)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.UP;
				possibleDirections[2] = E_Direction.LEFT;
			}
			else if (coordY==10 || coordY==12 || coordY==14 || coordY==16)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.UP;
			}
			else if (coordY==17)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.LEFT;
				possibleDirections[1] = E_Direction.UP;
				possibleDirections[2] = E_Direction.RIGHT;
			}
			break;
		case 19:
			if (coordY==0)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.LEFT;
			}
			else if ((coordY>=1 && coordY<=5) || coordY==7 || (coordY>=9 && coordY<=16) || coordY==18)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.UP;
			}
			else if (coordY==6 || coordY==8 || coordY==17)
			{
				possibleDirections = new E_Direction[3];
				possibleDirections[0] = E_Direction.DOWN;
				possibleDirections[1] = E_Direction.UP;
				possibleDirections[2] = E_Direction.LEFT;
			}
			else if (coordY==19)
			{
				possibleDirections = new E_Direction[2];
				possibleDirections[0] = E_Direction.UP;
				possibleDirections[1] = E_Direction.LEFT;
			}
			break;
		}
	}
	
	/**
	 *  add entity to hashset if it doesn't contain it yet
	 * @param entity
	 * @return
	 */
	public synchronized boolean addEntity (Entity entity)
	{
		if (!entities.contains(entity))
		{
			entities.add(entity);
			return true;
		}
		return false;
	}
	
	/**
	 *  remove entity from hashset if it contains it
	 * @param entity
	 * @return
	 */
	public synchronized boolean removeEntity (Entity entity)
	{
		if (entities.contains(entity))
		{
			entities.remove(entity);
			return true;
		}
		return false;
	}
	
	/**
	 *  returns true if this tile is walled off, false otherwise
	 * @return
	 */
	protected boolean isWalledOff ()
	{
		switch (coordX) {
		case 1:
		case 18:
			if ((coordY>=1 && coordY<=5) || coordY==7 || (coordY>=9 && coordY<=16) || coordY==18)
				return true;
			break;
		case 2:
		case 17:
			if ((coordY>=1 && coordY<=2) || (coordY>=4 && coordY<=5) || coordY==7 || coordY==18)
				return true;
			break;
		case 3:
		case 4:
		case 15:
		case 16:
			if ((coordY>=1 && coordY<=2) || (coordY>=4 && coordY<=5) || (coordY>=7 && coordY<=8) || coordY==10 || coordY==12 || coordY==14 || coordY==16 || coordY==18)
				return true;
			break;
		case 5:
		case 14:
			if (coordY==10 || coordY==12 || coordY==16)
				return true;
			break;
		case 6:
		case 13:
			if ((coordY>=1 && coordY<=8) || (coordY>=10 && coordY<=12) || coordY==14 || coordY==18)
				return true;
			break;
		case 7:
		case 12:
			if (coordY==14 || coordY==16 || coordY==18)
				return true;
			break;
		case 8:
		case 11:
			if ((coordY>=1 && coordY<=12) || coordY==16 || coordY==18)
				return true;
			break;
		case 9:
		case 10:
			if ((coordY>=1 && coordY<=12) || coordY==14 || coordY==16 || coordY==18)
				return true;
			break;	
		}
			
		return false;
	}
	
	/**
	 *  checks if there is a collision between pacman and a ghost
	 * @return
	 */
	public synchronized boolean checkPacmanGhostCollision()
	{
		for (Entity entity : entities)
			if (entity instanceof PacmanPlayer)
			{
				for (Entity entity2 : entities)
					if (entity2 instanceof Ghost)
						return true;
				return false;
			}
		return false;			
	}
	
	/**
	 *  checks if there is a collision between pacman and a candy
	 * @return
	 */
		public boolean checkPacmanCandyCollision()
		{
			if (candy!=null)
			for (Entity entity : entities)
				if (entity instanceof PacmanPlayer)
							return true;
					return false;			
		}
	
	public int getCoordX() {
		return coordX;
	}

	public int getCoordY() {
		return coordY;
	}

	public boolean isWall() {
		return isWall;
	}

	public Candy getCandy() {
		return candy;
	}

	public HashSet<Entity> getEntities() {
		return entities;
	}

	public E_Direction[] getPossibleDirections() {
		return possibleDirections;
	}

	public void setCandy(Candy candy) {
		this.candy = candy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + coordX;
		result = prime * result + coordY;
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
		Tile other = (Tile) obj;
		if (coordX != other.coordX)
			return false;
		if (coordY != other.coordY)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tile [coordX=" + coordX + ", coordY=" + coordY + "]";
	}
	
	
	
	

}
