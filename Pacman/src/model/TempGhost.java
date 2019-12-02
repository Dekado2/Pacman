package model;

import java.awt.Image;

import javax.swing.ImageIcon;

import utils.E_Direction;

public class TempGhost extends Ghost implements Drawable, Comparable<TempGhost>{
    
	/**
	 *  the answer that the ghost represents
	 */
	private Answer answer;

	
	/**
	 * TempGhost Constructor
	 * @param tile
	 * @param animationDirection
	 * @param animationCount
	 * @param animationOrder
	 * @param answer
	 */
	public TempGhost(Tile tile, E_Direction animationDirection, int animationCount, int animationOrder, Answer answer) {
		super(tile,animationDirection, animationCount, animationOrder);
		this.answer=answer;
	}
	
	/**
	 * load images of temp ghost from drive to array
	 */
		@Override
		public void loadImages() {
			Image[] images = new Image[1];
			if (answer!=null){
				if (answer.getAID()==1)
			    images[0] = new ImageIcon("pacpix/WhisperWithA.png").getImage();
			else if (answer.getAID()==2)
				images[0] = new ImageIcon("pacpix/WhisperWithB.png").getImage();
			else if (answer.getAID()==3)
				images[0] = new ImageIcon("pacpix/WhisperWithC.png").getImage();
			else if (answer.getAID()==4)
				images[0] = new ImageIcon("pacpix/WhisperWithD.png").getImage();
			}
			this.setImages(images);
        }

		public Answer getAnswer() {
			return answer;
		}

		/**
		 * comparing ghosts by id, to order them correctly for display when question pops
		 */
		@Override
		public int compareTo(TempGhost tg) {
			if (answer.getAID()>tg.getAnswer().getAID())
				return 1;
			else if (answer.getAID()<tg.getAnswer().getAID())
				return -1;
			return 0;
		}
}
