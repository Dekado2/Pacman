package utils;

public class Constants {
	
	// the board's row length
	public static final int BOARDSIZE_X = 20;
	//the board's column length
	public static final int BOARDSIZE_Y = 20;
    //number of permanent ghosts spawning on board at the start of a game
	public static final int PERM_GHOSTS_NUMBER = 3;
	//number of lives that pacman begins the game with
	public static final int PACMAN_STARTING_LIVES = 3;
	//amount of poisonous candies allowed on board on a single level
	public static final int POISON_CANDY_NUMBER = 1;
	//amount of silver candies allowed on board on a single level
	public static final int SILVER_CANDY_NUMBER = 5;
	//amount of question candies allowed on board on a single level
	public static final int QUESTION_CANDY_NUMBER = 4;
	//amount of points the normal candy awards
	public static final int NORMAL_CANDY_POINTS = 1;
	//amount of points the silver candy awards
	public static final int SILVER_CANDY_POINTS = 50;
	//amount of points the poison candy awards
	public static final int POISON_CANDY_POINTS = 0;
	//amount of points the question candy awards upon being eaten by pacman - initial points, regardless of the answer to the question afterwards 
	public static final int QUESTION_CANDY_INITIAL_POINTS = 0;
	//amount of points awarded for answering an easy question correctly
	public static final int CORRECT_EASY_ANSWER = 100;
	//amount of points awarded for answering a medium question correctly
	public static final int CORRECT_MEDIUM_ANSWER = 200;
	//amount of points awarded for answering a hard question correctly
	public static final int CORRECT_HARD_ANSWER = 500;
	//amount of points subtracted for answering an easy question incorrectly
	public static final int WRONG_EASY_ANSWER = 250;
	//amount of points subtracted for answering a medium question incorrectly
	public static final int WRONG_MEDIUM_ANSWER = 100;
	//amount of points subtracted for answering a hard question incorrectly
	public static final int WRONG_HARD_ANSWER = 50;
	//amounts of points rewarded for finishing a level
	public static final int BONUS_POINTS_END_LEVEL = 100;
	//number of lives the poison candy takes from pacman upon being eaten
	public static final int PCANDY_LIVES_TAKEN_NUMBER = 1;
	//number of lives taken from pacman upon answering a question incorrectly
	public static final int QCANDY_WRONG_ANSWER_LIVES_TAKEN_NUMBER = 1;
	//minimum amount of possible answers to a question
	public static final int MIN_ANSWERS_NUMBER = 2;
	//maximum amount of possible answers to a question
	public static final int MAX_ANSWERS_NUMBER = 4;
	//max play time per player turn in seconds on multiplayer mode
	public static final int GAME_INTERVAL = 40;
	//amount of the player's initial points at the start of a new game
	public static final int STARTING_POINTS = 0;
	//the minimal amount of tiles the ghosts have to spawn away from pacman
	public static final int MINIMAL_STEPS_FROM_PACMAN = 3;
	//the row coord of pacman's starting position at the start of a new stage 
	public static final int PACMAN_STARTING_POSITION_X = 9;
	//the col coord of pacman's starting position at the start of a new stage 
	public static final int PACMAN_STARTING_POSITION_Y = 17;
	//board of map A's top left dead end coord x for silver candy placement
	public static final int MAP_A_TOP_LEFT_DEAD_END_COORD_X = 2;
	//board of map A's top left dead end coord y for silver candy placement
	public static final int MAP_A_TOP_LEFT_DEAD_END_COORD_Y = 3;
	//board of map A's top right dead end coord x for silver candy placement
	public static final int MAP_A_TOP_RIGHT_DEAD_END_COORD_X = 17;
	//board of map A's top right dead end coord y for silver candy placement
	public static final int MAP_A_TOP_RIGHT_DEAD_END_COORD_Y = 3;
	//board of map A's mid left dead end coord x for silver candy placement
	public static final int MAP_A_MID_LEFT_DEAD_END_COORD_X = 5;
	//board of map A's mid left dead end coord y for silver candy placement
	public static final int MAP_A_MID_LEFT_DEAD_END_COORD_Y = 11;
	//board of map A's mid right dead end coord x for silver candy placement
	public static final int MAP_A_MID_RIGHT_DEAD_END_COORD_X = 14;
	//board of map A's mid right dead end coord y for silver candy placement
	public static final int MAP_A_MID_RIGHT_DEAD_END_COORD_Y = 11;
	//number of images of pacman for animation creation
	public static final int PACMAN_DIRECTION_ANIMATION_IMAGES = 3;
	//number of images of permanent ghosts for animation creation
	public static final int PERMGHOST_ANIMATION_IMAGES = 4;
	//number of images of temporary ghosts for animation creation
	public static final int TEMPGHOST_ANIMATION_IMAGES = 4;
	//amount of players appearing in Leaderboard list
	public static final int LEADERBOARD_LIST_LENGTH = 10;
	//overall animation speed of the game - changeable in game
	public static int GAME_SPEED = 12;
	//board game screen scaling size - changeable in game
	public static String SCALING_SIZE = "1.0";
	//seconds after which a board is swapped in multiplayer
	public static final int TURN_DURATION = 40;
	//amount of points the shiny candy awards
	public static final int SHINY_CANDY_POINTS = 0;
	//amount of shiny candies allowed on board on a single level
	public static final int SHINY_CANDY_NUMBER = 1;
	//the number by which the current number of points is multiplied
	public static final int MULTIPLIER_CONSTANT = 2;
	
}
