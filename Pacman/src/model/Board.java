package model;

import java.awt.Image;
import java.util.HashSet;
import java.util.Random;

import javax.swing.ImageIcon;

import utils.Constants;
import utils.E_Direction;

public class Board implements Drawable {
	
	/**
	 *  help variable for running sequence of id numbers
	 */
	private static volatile int boardsCounter;
	/**
	 * the board's id
	 */
	private int id;
	/**
	 * the game board, constructed out of dimensionSizeX * dimensionSizeY tiles
	 */
	private Tile[][] layout;
	/**
	 * player playing in this board
	 */
	private PacmanPlayerAdapter player;
	/**
	 * list of ghosts in current game
	 */
	private HashSet<Ghost> ghosts;
	/**
	 * list of candies in current game
	 */
	private HashSet<Candy> candies;
	/**
	 *  potential normal state of the board
	 */
	private BoardState normalState;
	/**
	 *  potential question popped state of the board
	 */
	private BoardState questionPoppedState;
	/**
	 *  the actual current state of the board
	 */
	private BoardState boardState;
	/**
	 *  the board's image
	 */
	private Image boardImage;
	
	/**
	 * Board Constructor with a player assigned to it
	 * @param player
	 */
		public Board(PacmanPlayerAdapter player)
		{
			this.id=boardsCounter++;
			this.player=player;
			ghosts = new HashSet<Ghost>();
			candies = new HashSet<Candy>();
			normalState = new NormalState(this);
			questionPoppedState = new QuestionPoppedState(this);
			boardState = normalState;
			initializeBoard();
			initializePacman();
			initializePermGhosts();
			initializeAllCandies();
		}
	
    /**
     * initialize the board by filling it with tiles
     */
	private void initializeBoard() {
		
		layout = new Tile[Constants.BOARDSIZE_X][Constants.BOARDSIZE_Y];
		for (int row=0;row<Constants.BOARDSIZE_X;row++)
			for (int col=0;col<Constants.BOARDSIZE_Y;col++)
				 layout[row][col] = new Tile(row, col, true, null);
	}
	
	/**
	 *  spawns pacman at a certain tile, always the same one - upon new game/new level/pacman 1 life point loss
	 */
	protected void initializePacman() {
		
		layout[Constants.PACMAN_STARTING_POSITION_X][Constants.PACMAN_STARTING_POSITION_Y].addEntity(player.getPacmanPlayer());
		player.getPacmanPlayer().setTile(layout[Constants.PACMAN_STARTING_POSITION_X][Constants.PACMAN_STARTING_POSITION_Y]);
		player.getPacmanPlayer().setAnimationDirection(E_Direction.RIGHT);
		
	}
	
	/**
	 *  spawns ghosts at least 3 tiles away from pacman on available tiles only while making sure they can't spawn on top of each other
	 */
	protected void initializePermGhosts() {
		int value_x, value_y;
		boolean isWall = false;
		Random random = new Random();
		E_Direction direction = null;
		for (int i=0;i<Constants.PERM_GHOSTS_NUMBER;i++) {
			do {
			    value_x = random.nextInt(Constants.BOARDSIZE_X);
			    value_y = random.nextInt(Constants.BOARDSIZE_Y);
			     isWall = layout[value_x][value_y].isWalledOff();
			}
			while ((value_x==Constants.PACMAN_STARTING_POSITION_X &&
            (value_y>Constants.PACMAN_STARTING_POSITION_Y-Constants.MINIMAL_STEPS_FROM_PACMAN &&
            value_y<Constants.PACMAN_STARTING_POSITION_Y+Constants.MINIMAL_STEPS_FROM_PACMAN)) ||
			(value_y==Constants.PACMAN_STARTING_POSITION_Y &&
			(value_x>Constants.PACMAN_STARTING_POSITION_X-Constants.MINIMAL_STEPS_FROM_PACMAN &&
			value_x<Constants.PACMAN_STARTING_POSITION_X+Constants.MINIMAL_STEPS_FROM_PACMAN)) ||
			(value_x==Constants.PACMAN_STARTING_POSITION_X-1 &&
			(value_y>Constants.PACMAN_STARTING_POSITION_Y-Constants.MINIMAL_STEPS_FROM_PACMAN+1 &&
			value_y<Constants.PACMAN_STARTING_POSITION_Y+Constants.MINIMAL_STEPS_FROM_PACMAN-1)) ||
			(value_x==Constants.PACMAN_STARTING_POSITION_X+1 &&
			(value_y>Constants.PACMAN_STARTING_POSITION_Y-Constants.MINIMAL_STEPS_FROM_PACMAN+1 &&
			value_y<Constants.PACMAN_STARTING_POSITION_Y+Constants.MINIMAL_STEPS_FROM_PACMAN-1)) ||
			isWall==true || (!layout[value_x][value_y].getEntities().isEmpty()));
			direction=randomizeGhostDirection(value_x,value_y);
			Ghost ghost = new Ghost(layout[value_x][value_y], direction, 0, i+1); // fix variables
			layout[value_x][value_y].addEntity(ghost);
			ghosts.add(ghost);
			
		}
	}
	
	/**
	 *  randomizes the ghost movement direction according to the possible directions from the tile it currently is on
	 * @param coord_x
	 * @param coord_y
	 * @return
	 */
	protected E_Direction randomizeGhostDirection (int coord_x, int coord_y)
	{
		Random random = new Random();
		int dir;
		E_Direction direction = null;
		if (layout[coord_x][coord_y].getPossibleDirections().length>1)
		{
			dir = random.nextInt(layout[coord_x][coord_y].getPossibleDirections().length);
			direction = layout[coord_x][coord_y].getPossibleDirections()[dir];
			return direction;
		}
		else
		{
			direction = layout[coord_x][coord_y].getPossibleDirections()[0];
			return direction;
		}
	}
	
	/**
	 *  spawns ghosts at least 3 tiles away from pacman on available tiles only while making sure they can't spawn on top of each other and returns the related question
	 * @return
	 */
		protected Question initializeTempGhosts() {
			int value_x, value_y;
			boolean isWall = false;
			Random random = new Random();
			E_Direction direction = null;
			Question question = layout[player.getPacmanPlayer().getTile().getCoordX()][player.getPacmanPlayer().getTile().getCoordY()].getCandy().getQuestion().popQuestion();
			for (int i=0;i<question.getPossibleAnswers().size();i++) {
				do {
				    value_x = random.nextInt(Constants.BOARDSIZE_X);
				    value_y = random.nextInt(Constants.BOARDSIZE_Y);
				     isWall = layout[value_x][value_y].isWalledOff();
				}
				while ((value_x==player.getPacmanPlayer().getTile().getCoordX() &&
	            (value_y>player.getPacmanPlayer().getTile().getCoordY()-Constants.MINIMAL_STEPS_FROM_PACMAN &&
	            value_y<player.getPacmanPlayer().getTile().getCoordY()+Constants.MINIMAL_STEPS_FROM_PACMAN)) ||
				(value_y==player.getPacmanPlayer().getTile().getCoordY() &&
				(value_x>player.getPacmanPlayer().getTile().getCoordX()-Constants.MINIMAL_STEPS_FROM_PACMAN &&
				value_x<player.getPacmanPlayer().getTile().getCoordX()+Constants.MINIMAL_STEPS_FROM_PACMAN)) ||
				(value_x==player.getPacmanPlayer().getTile().getCoordX()-1 &&
				(value_y>player.getPacmanPlayer().getTile().getCoordY()-Constants.MINIMAL_STEPS_FROM_PACMAN+1 &&
				value_y<player.getPacmanPlayer().getTile().getCoordY()+Constants.MINIMAL_STEPS_FROM_PACMAN-1)) ||
				(value_x==player.getPacmanPlayer().getTile().getCoordX()+1 &&
				(value_y>player.getPacmanPlayer().getTile().getCoordY()-Constants.MINIMAL_STEPS_FROM_PACMAN+1 &&
				value_y<player.getPacmanPlayer().getTile().getCoordY()+Constants.MINIMAL_STEPS_FROM_PACMAN-1)) ||
				isWall==true || (!layout[value_x][value_y].getEntities().isEmpty()));
				direction=randomizeGhostDirection(value_x,value_y);
				TempGhost ghost = new TempGhost(layout[value_x][value_y], direction, 0, 0, question.getPossibleAnswers().get(i)); // fix variables
				layout[value_x][value_y].addEntity(ghost);
				ghosts.add(ghost);
			}
			return question;
		}
	
	/**
	 *  initialize all candies on the board
	 */
	private void initializeAllCandies () {
		initializeSilverCandies(); // spawn silver candies
		initializeSpecialCandiesType(Constants.POISON_CANDY_NUMBER, new CandyPoison(null)); // spawn a poison candy
		initializeSpecialCandiesType(Constants.QUESTION_CANDY_NUMBER, new CandyQuestion(null)); // spawn a question candy
		initializeSpecialCandiesType(Constants.SHINY_CANDY_NUMBER, new CandyShiny(null)); // spawn a shiny candy
		initializeNormalCandies(); // spawn normal candies
	}
	
	/**
	 *  initialize silver candies, separate method due to 4 silver candies always spawning in dead ends and last point uses initializeSpecialCandiesType method
	 */
	private void initializeSilverCandies () {
		// 4 silver candies, 1 at each dead end
		layout[Constants.MAP_A_TOP_LEFT_DEAD_END_COORD_X][Constants.MAP_A_TOP_LEFT_DEAD_END_COORD_Y].setCandy(new CandySilver(null));
		candies.add(layout[Constants.MAP_A_TOP_LEFT_DEAD_END_COORD_X][Constants.MAP_A_TOP_LEFT_DEAD_END_COORD_Y].getCandy());
		layout[Constants.MAP_A_TOP_RIGHT_DEAD_END_COORD_X][Constants.MAP_A_TOP_RIGHT_DEAD_END_COORD_Y].setCandy(new CandySilver(null));
		candies.add(layout[Constants.MAP_A_TOP_RIGHT_DEAD_END_COORD_X][Constants.MAP_A_TOP_RIGHT_DEAD_END_COORD_Y].getCandy());
		layout[Constants.MAP_A_MID_LEFT_DEAD_END_COORD_X][Constants.MAP_A_MID_LEFT_DEAD_END_COORD_Y].setCandy(new CandySilver(null));
		candies.add(layout[Constants.MAP_A_MID_LEFT_DEAD_END_COORD_X][Constants.MAP_A_MID_LEFT_DEAD_END_COORD_Y].getCandy());
		layout[Constants.MAP_A_MID_RIGHT_DEAD_END_COORD_X][Constants.MAP_A_MID_RIGHT_DEAD_END_COORD_Y].setCandy(new CandySilver(null));
		candies.add(layout[Constants.MAP_A_MID_RIGHT_DEAD_END_COORD_X][Constants.MAP_A_MID_RIGHT_DEAD_END_COORD_Y].getCandy());
		// last 5th candy gets a randomized position, except on pathway to and out of dead ends
		initializeSpecialCandiesType(Constants.SILVER_CANDY_NUMBER-4, new CandySilver(null));
		// question candy gets a randomized position, except on pathway to and out of dead ends and where a silver/poison candies already reside
	}
	
	/**
	 * initialize different types of candies (non normal) on the boards depending on input, including the amount of it desired on the board
	 * @param amountOnBoard
	 * @param candyType
	 */
	private void initializeSpecialCandiesType (int amountOnBoard, Candy candyType) {
		int value_x, value_y;
		boolean isWall = false;
		Random random = new Random();
		for (int i=0;i<amountOnBoard;i++){ // repeats the process of spawning a candy depending on how much there should be on the board of this type
		// poison candy gets a randomized position, except on pathway to and out of dead ends and where a silver candy already resides
				do {
					value_x = random.nextInt(Constants.BOARDSIZE_X);
					value_y = random.nextInt(Constants.BOARDSIZE_Y);
				     isWall = layout[value_x][value_y].isWalledOff();
				}
				while (((value_y==3 || value_y==11)  && ((value_x>=2 && value_x<=5) || (value_x>=14 && value_x<=17))) || isWall==true
						|| layout[value_x][value_y].getCandy()!=null || (value_x==Constants.PACMAN_STARTING_POSITION_X && value_y==Constants.PACMAN_STARTING_POSITION_Y));
				
				layout[value_x][value_y].setCandy(candyType);
				candies.add(candyType);
				if (candyType instanceof CandyPoison)
					candyType = new CandyPoison(null);
				else if (candyType instanceof CandySilver)
					candyType = new CandySilver(null);
				else if (candyType instanceof CandyQuestion)
					candyType = new CandyQuestion(null);
				else if (candyType instanceof CandyShiny)
					candyType = new CandyShiny(null);
		}
	}
	
	/**
	 *  spawns normal candies on the board, after considering all other available tiles that the special candies have taken
	 */
	private void initializeNormalCandies () {
		boolean isWall = true;
		for (int row=0;row<Constants.BOARDSIZE_X;row++){
			for (int col=0;col<Constants.BOARDSIZE_Y;col++){
				isWall = layout[row][col].isWalledOff();
				if (isWall==false && layout[row][col].getCandy()==null && (!(row==Constants.PACMAN_STARTING_POSITION_X && col==Constants.PACMAN_STARTING_POSITION_Y))){
					layout[row][col].setCandy(new CandyNormal(null));
					candies.add(layout[row][col].getCandy());
				}
			}
		}
	}
	
	
	
	/**
	 *  when candy gets eaten by pacman, update score and remove candy from the correct tile of the board
	 * @param candy
	 */
		public void eatCandy (Candy candy)
		{
			int points = player.getPacmanPlayer().getPoints();
			points = points + candy.getPoints();
			if (candy instanceof CandyShiny)
				points = points + Math.abs(player.getPacmanPlayer().getPoints() * (Constants.MULTIPLIER_CONSTANT-1));
			player.getPacmanPlayer().setPoints(points);
			candies.remove(candy);
			if (candy instanceof CandyQuestion)
			{
				boardState.pacmanQuestionCandyCollision();
				boardState.controlTempGhosts();
			}
			layout[player.getPacmanPlayer().getTile().getCoordX()][player.getPacmanPlayer().getTile().getCoordY()].setCandy(null);
			// if candies is empty, continue level
			if (candy instanceof CandyPoison)
			{
				player.getPacmanPlayer().setLifePoints(player.getPacmanPlayer().getLifePoints() + candy.affectLifePoints(Constants.PCANDY_LIVES_TAKEN_NUMBER));
				if (player.getPacmanPlayer().getLifePoints()>0)
				{
					player.getPacmanPlayer().getTile().removeEntity(player.getPacmanPlayer());
					layout[player.getPacmanPlayer().getTile().getCoordX()][player.getPacmanPlayer().getTile().getCoordY()].removeEntity(player.getPacmanPlayer());
					setBoardState(normalState);
					clearPermGhosts();
					controlTempGhosts();
					initializePacman();
					initializePermGhosts();
					// if collides with Poison during questionstate, return to normal state
				}
			}
			
		}
		
		/**
		 * terminates all types of ghost and removes them from the board
		 */
		public void clearPermGhosts() {
			for (Ghost g : ghosts)
				layout[g.getTile().getCoordX()][g.getTile().getCoordY()].removeEntity(g);
			ghosts = new HashSet<Ghost>();
		}
		
		/**
		 * loads the image of the board
		 */
		@Override
		public void loadImages() {
			boardImage = new ImageIcon("pacpix/Maze1ReadyUpdated.png").getImage();
		}

	public Image getBoardImage() {
			return boardImage;
		}

	public int getId() {
		return id;
	}

	public Tile[][] getLayout() {
		return layout;
	}

	public PacmanPlayerAdapter getPlayer() {
		return player;
	}

	public HashSet<Ghost> getGhosts() {
		return ghosts;
	}

	public HashSet<Candy> getCandies() {
		return candies;
	}

	public BoardState getBoardState() {
		return boardState;
	}

	protected void setPlayer(PacmanPlayerAdapter player) {
		this.player = player;
		initializePacman();
	}
	
	protected void setBoardState (BoardState newBoardState) {
		boardState = newBoardState;
	}
	
	protected void pacmanQuestionCandyCollision() {
		boardState.pacmanQuestionCandyCollision();
	}
	
	public void pacmanGhostCollision() {
		boardState.pacmanGhostCollision();
	}
	
	protected void controlTempGhosts() {
		boardState.controlTempGhosts();
	}
	
	public BoardState getNormalState() {
		return normalState;
	}
	
	public BoardState getQuestionPoppedState() {
		return questionPoppedState;
	}

	protected void setGhosts(HashSet<Ghost> ghosts) {
		this.ghosts = ghosts;
	}

	
	
	
	
}
