package model;

/**
 * 
 * 
 * interface that affects pacman life points in the form of eating candies
 */
public interface AffectsPacmanLifePoints {
	
	public int affectLifePoints(int num);

}

/**
 * 
 *
 * decides how many life points are reduced from pacman
 * made for collision with poison candy
 */
class DecreaseLifePoints implements AffectsPacmanLifePoints {

	public int affectLifePoints(int num) {
		return -num;
	}
}
