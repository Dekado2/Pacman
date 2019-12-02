package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.Controller;
import javafx.application.Platform;
import model.Candy;
import model.CandyNormal;
import model.CandyPoison;
import model.CandyQuestion;
import model.CandyShiny;
import model.CandySilver;
import model.Game;
import model.Ghost;
import model.Leaderboard;
import model.PacmanPlayerAdapter;
import model.QuestionPoppedState;
import model.SysData;
import model.TempGhost;
import utils.Constants;
import utils.E_DifficultyLevel;
import utils.E_Direction;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;



public class PlayerBoard extends JPanel implements ActionListener{
	
	private Dimension d = new Dimension(1158,732);
    private boolean pacmanMovementBegan=false, ghostInitialSpawn=true, pacmanAscendingAnimation=true, distanceGhost=false, isSizeIncreased=false,
    		isGameOver=false, isMyTurn, countDownOver=false, isOpponentDone=false, isShinyShown=true;
	private Object[][] ghostInitialPositions=new Object[Constants.PERM_GHOSTS_NUMBER][3];

    private Object[][] tempGhostInitialPositions=new Object[Constants.MAX_ANSWERS_NUMBER][3];
    private int pacman_x, pacman_y, savedKey;
    private ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(1);
    private Ghost collidedGhost;
    private Future<?> future;
    private int boardNum, countDownNum = 3*Constants.GAME_SPEED*5, gameOverOpponentNotificationLength =  3*Constants.GAME_SPEED*5;
    private JComboBox menu = new JComboBox();
    private HashSet<CandyShiny> shinyCandy;
    /**
     * constructor for single player
     * @param player
     * @wbp.parser.constructor
     */
    public PlayerBoard(PacmanPlayerAdapter player){
    	
    	this.boardNum=0;
    	this.isMyTurn=true;
    	addKeyListener(new TAdapter());
    	setFocusable(true);
    	GridBagLayout gridBagLayout = new GridBagLayout();
    	gridBagLayout.columnWidths = new int[]{0, 0};
    	gridBagLayout.rowHeights = new int[]{0, 0};
    	gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
    	gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
    	setLayout(gridBagLayout);
    	menu.setMaximumRowCount(1);
    	
    	menu.setBackground(Color.BLACK);
    	menu.setVisible(false);
    	menu.setForeground(new Color(197,191,252));
    	menu.setFont(new Font("Baskerville Old Face", Font.BOLD, 20));
    	menu.setModel(new DefaultComboBoxModel(new String[] {"Exit"}));
    	removeButton(menu);
    	
    	menu.addActionListener (new ActionListener () {
    	    public void actionPerformed(ActionEvent e) {
    	        exitGame();
    	    }
    	});
    	
    	this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	menu.transferFocus();
                PlayerBoard.this.requestFocusInWindow();
            }
        });
    	
    	GridBagConstraints gbc_comboBox = new GridBagConstraints();
    	gbc_comboBox.anchor = GridBagConstraints.NORTHWEST;
    	gbc_comboBox.gridx = 0;
    	gbc_comboBox.gridy = 0;
    	add(menu, gbc_comboBox);
    	initializeSinglePlayerGame(player);
    	loadImages();
    	pacman_x = Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordX()*32+6;
        pacman_y = Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordY()*32+38;
    }
    
    /**
     * constructor for multiplayer
     * @param player
     * @param boardNum
     * @param isMyTurn
     */
    public PlayerBoard(PacmanPlayerAdapter player, PacmanPlayerAdapter opponent, int boardNum, boolean isMyTurn){
    	
    	this.boardNum=boardNum;
    	this.isMyTurn=isMyTurn;
    	addKeyListener(new TAdapter());
    	setFocusable(true);
    	GridBagLayout gridBagLayout = new GridBagLayout();
    	gridBagLayout.columnWidths = new int[]{0, 0};
    	gridBagLayout.rowHeights = new int[]{0, 0};
    	gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
    	gridBagLayout.rowWeights = new double[]{0.0, Double.MIN_VALUE};
    	setLayout(gridBagLayout);
    	
    	menu.setBackground(Color.BLACK);
    	menu.setVisible(false);
    	menu.setForeground(new Color(197,191,252));
    	menu.setFont(new Font("Baskerville Old Face", Font.BOLD, 20));
    	menu.setModel(new DefaultComboBoxModel(new String[] {"Exit"}));
    	removeButton(menu);
    	
    	menu.addActionListener (new ActionListener () {
    	    public void actionPerformed(ActionEvent e) {
    	        exitGame();
    	    }
    	});
    	
    	this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	menu.transferFocus();
                PlayerBoard.this.requestFocusInWindow();
            }
        });
    	
    	GridBagConstraints gbc_comboBox = new GridBagConstraints();
    	gbc_comboBox.anchor = GridBagConstraints.NORTHWEST;
    	gbc_comboBox.gridx = 0;
    	gbc_comboBox.gridy = 0;
    	add(menu, gbc_comboBox);
    	if (boardNum==0)
    	initializeMultiPlayerGame(player, opponent);
    	loadImages();
    	pacman_x = Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordX()*32+6;
        pacman_y = Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordY()*32+38;
    }
    
    /**
     * initializes schedule at fixed rate so repaint work every Constants.GameSpeed milliseconds
     * @param player
     */
    private void initializeSinglePlayerGame(PacmanPlayerAdapter player) {
    	
    	shinyCandy=new HashSet<CandyShiny>();
    	for (int row=0;row<Constants.BOARDSIZE_X;row++)
      	  for (int col=0;col<Constants.BOARDSIZE_Y;col++)
      		  if (Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy()!=null && Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy() instanceof CandyShiny)
      			 shinyCandy.add((CandyShiny) Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy());


    	future = threadPool.scheduleAtFixedRate(new Runnable() {
    		@Override
    		public void run() {
    	    	if (!player.getPacmanPlayer().getGameLength().isRunning() && countDownOver==true)
    	        	player.getPacmanPlayer().getGameLength().start();
    	    	if (countDownOver==true) {
    	    		for (CandyShiny cs : shinyCandy)
    	    			if (!cs.getCandyTimer().isRunning())
    	    				cs.getCandyTimer().start();
    	    	}
    			if (isMyTurn)
    			repaint();
    			else
    			{
    				future.cancel(true);
    				player.getPacmanPlayer().getGameLength().stop();
    			}
    		}
    	}, 1, Constants.GAME_SPEED, TimeUnit.MILLISECONDS);
    }

    /**
     * initializes schedule at fixed rate so repaint work every Constants.GameSpeed milliseconds
     * swapping between players every 40 seconds
     * @param player
     * @param opponent
     */
	protected void initializeMultiPlayerGame(PacmanPlayerAdapter player, PacmanPlayerAdapter opponent) {
		long beforeContinue=player.getPacmanPlayer().getGameLength().getElapsedInMillis();
    	
    	countDownOver=false;
    	
    	
    	shinyCandy=new HashSet<CandyShiny>();
    	for (int row=0;row<Constants.BOARDSIZE_X;row++)
      	  for (int col=0;col<Constants.BOARDSIZE_Y;col++)
      		  if (Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy()!=null && Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy() instanceof CandyShiny)
      			 shinyCandy.add((CandyShiny) Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy());
			
    	future = threadPool.scheduleAtFixedRate(new Runnable() {
    		@Override
    		public void run() {
    			if (!player.getPacmanPlayer().getGameLength().isRunning() && countDownOver==true)
    			player.getPacmanPlayer().getGameLength().start();
    			if (countDownOver==true) {
    	    		for (CandyShiny cs : shinyCandy)
    	    			if (!cs.getCandyTimer().isRunning())
    	    				cs.getCandyTimer().start();
    	    	}
    			Component gtPanel=null;
    			if ( player.getPacmanPlayer().getGameLength().getElapsedInMillis()>beforeContinue+1000 && (player.getPacmanPlayer().getGameLength().getElapsedInMillis()/1000)%Constants.TURN_DURATION==0 && opponent.getPacmanPlayer().getLifePoints()>0) {
    				isMyTurn=false;
    				if (boardNum==0) {
    					for (Component c : ((GameSession) PlayerBoard.this.getTopLevelAncestor()).getCardPanel().getComponents()) {
    	    				if (c.getName()=="player2game") {
        						gtPanel=c;
    	    					((PlayerBoard) c).setMyTurn(true);
    	    					((PlayerBoard) c).initializeMultiPlayerGame(opponent, player);
    	    					if (Game.getInstance().getBoards()[1].getBoardState()==Game.getInstance().getBoards()[1].getQuestionPoppedState())
    	    						((PlayerBoard) c).increasePanelSize();
    	    					else
    	    						((PlayerBoard) c).decreasePanelSize();
    	    				}
    					}
    				 ((GameSession) PlayerBoard.this.getTopLevelAncestor()).getCardLayout().show(((GameSession) PlayerBoard.this.getTopLevelAncestor()).getCardPanel(), "player2game");
    				 ((PlayerBoard) gtPanel).requestFocusInWindow();
    				}
    				else if (boardNum==1) {
    					for (Component c : ((GameSession) PlayerBoard.this.getTopLevelAncestor()).getCardPanel().getComponents()) {
    	    				if (c.getName()=="player1game") {
        						gtPanel=c;
    	    					((PlayerBoard) c).setMyTurn(true);
    	    					((PlayerBoard) c).initializeMultiPlayerGame(opponent, player);
    	    					if (Game.getInstance().getBoards()[0].getBoardState()==Game.getInstance().getBoards()[0].getQuestionPoppedState())
    	    						((PlayerBoard) c).increasePanelSize();
    	    					else
    	    						((PlayerBoard) c).decreasePanelSize();
    	    				}
    					}
        				((GameSession) PlayerBoard.this.getTopLevelAncestor()).getCardLayout().show(((GameSession) PlayerBoard.this.getTopLevelAncestor()).getCardPanel(), "player1game");
        				 ((PlayerBoard) gtPanel).requestFocusInWindow();
    				}
    			}
    			if (isMyTurn)
    			repaint();
    			else
    			{
    				future.cancel(true);
    				player.getPacmanPlayer().getGameLength().stop();
    				for (CandyShiny cs : shinyCandy)
    	    			if (cs.getCandyTimer().isRunning())
    	    				cs.getCandyTimer().stop();
    			}
    		}
    	}, 1, Constants.GAME_SPEED, TimeUnit.MILLISECONDS);
    }
    
	/**
	 * when board is cleared of candies, player moves to the next level
	 * @param g2d
	 * @param p
	 */
    private void continueLevelPlayer(Graphics g2d, PacmanPlayerAdapter p) {
    	Game.getInstance().continueLevel(p);
    	loadImages();
    	ghostInitialSpawn=true;
	    pacmanMovementBegan=false;
	    pacman_x = Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordX()*32+6;
        pacman_y = Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordY()*32+38;
        decreasePanelSize();
        countDownOver=false;
        shinyCandy=new HashSet<CandyShiny>();
    	for (int row=0;row<Constants.BOARDSIZE_X;row++)
      	  for (int col=0;col<Constants.BOARDSIZE_Y;col++)
      		  if (Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy()!=null && Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy() instanceof CandyShiny)
      			 shinyCandy.add((CandyShiny) Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy());
        
    }
    
    /**
     * loading all the images of entities, candies and board
     */
    private void loadImages(){

    	Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().loadImages();
    	for (Ghost ghost: Game.getInstance().getBoards()[boardNum].getGhosts())
			ghost.loadImages();
		for (Candy candy: Game.getInstance().getBoards()[boardNum].getCandies())
			if (candy instanceof CandyNormal)
				((CandyNormal) candy).loadImages();
			else if (candy instanceof CandySilver)
				((CandySilver) candy).loadImages();
			else if (candy instanceof CandyPoison)
				((CandyPoison) candy).loadImages();
			else if (candy instanceof CandyQuestion)
				((CandyQuestion) candy).loadImages();
			else if (candy instanceof CandyShiny)
				((CandyShiny) candy).loadImages();
		Game.getInstance().getBoards()[boardNum].loadImages();
    }
    
    
    /**
     * draws the current state of the board and it's inhabitants
     * @param g
     */
	private void doDrawing(Graphics g) {
		if (isMyTurn) {
	    double pacmanRowResult, pacmanColResult;
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);
        
        g2d.drawImage(Game.getInstance().getBoards()[boardNum].getBoardImage(), 0, 0, this);
        
		if (isSizeIncreased==true) {
			drawQuestion(g2d);
		}
		
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
		g2d.setColor(new Color(197,191,252));
		
        drawPacmanLifePoints(g2d);
        drawScore(g2d);
        drawLevel(g2d);
        drawGameLength(g2d);
        drawPacmanNickName(g2d);
        
        	if (Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getLifePoints()>0)
        	{
        		
      for (int row=0;row<Constants.BOARDSIZE_X;row++)
    	  for (int col=0;col<Constants.BOARDSIZE_Y;col++)
    		  if (Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy()!=null) {
    			  if (Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy() instanceof CandyShiny) {	
    				  if (isShinyShown==false) {
    				      ((CandyShiny) Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy()).setImage(null);
    				      isShinyShown=true;
    				  }
    				  else
    				  {
    					  ((CandyShiny) Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy()).setImage((new ImageIcon("pacpix/ShinyCandy.png")).getImage());
    					  isShinyShown=false;
    				  }
    				  g2d.setFont(new Font("TimesRoman", Font.PLAIN, 10));
    				  long timeRemaining = (15000-((CandyShiny) Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy()).getCandyTimer().getElapsedInMillis())/1000;
    				  g2d.drawString(String.valueOf(timeRemaining), row*32+15, col*32+53);
    				  if (timeRemaining==0) {
    					  Game.getInstance().getBoards()[boardNum].getCandies().remove(Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy());
    					  Game.getInstance().getBoards()[boardNum].getLayout()[row][col].setCandy(null);
    					  
    				  }
    			  }
    			  if (Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy()!=null)
    			  g2d.drawImage(Game.getInstance().getBoards()[boardNum].getLayout()[row][col].getCandy().getImage(), row*32+6, col*32+38, this);
    		  }
      
      if (isOpponentDone==true) {
      drawPlayerGameOver(g2d, gameOverOpponentNotificationLength--);
      if (gameOverOpponentNotificationLength==0) {
    	  isOpponentDone=false;
        }
      }
      
      else if (countDownOver==false) {
  		
  		doPacmanAnimation(g2d);
  		doPermGhostAnimation(g2d);
  		if (Game.getInstance().getBoards()[boardNum].getBoardState()==Game.getInstance().getBoards()[boardNum].getQuestionPoppedState())
  		for (int i=0;i<tempGhostInitialPositions.length;i++)
  			if (tempGhostInitialPositions[i][0]!=null)
  				g2d.drawImage(((TempGhost) tempGhostInitialPositions[i][0]).getImages()[0], (int) tempGhostInitialPositions[i][1], (int) tempGhostInitialPositions[i][2], this);
  		drawPlayerCountDown(g2d, countDownNum--); // cant see images under counter, if put before drawing other images, they stick out in an ugly way.
  		if (countDownNum==0) {
  			countDownOver=true;
  			countDownNum = 3*Constants.GAME_SPEED*5;
  			if (menu!=null)
  	  			menu.setVisible(true);
  		}
	}
    		  else {
		if (Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getLifePoints()==0)
		    ghostInitialSpawn=false;
      if (ghostInitialSpawn==true)
      {
    	  int i=0,j;
      for (Ghost ghost: Game.getInstance().getBoards()[boardNum].getGhosts())
        {
    	  j=0;
    	  ghostInitialPositions[i][j]=ghost;
    	  ghostInitialPositions[i][++j]=ghost.getTile().getCoordX()*32+6;
    	  ghostInitialPositions[i][++j]=ghost.getTile().getCoordY()*32+38;
    	  doPermGhostAnimation(g2d);
		  i++;
        }
      ghostInitialSpawn=false;
      }
      else {
    	  double rowResult=0.0, colResult=0.0;
		  int tempRow=0, tempCol=0;
		  Iterator<Ghost> iter = Game.getInstance().getBoards()[boardNum].getGhosts().iterator();
			while (iter.hasNext()){
				Ghost ghost = iter.next();
    	  if (!(ghost instanceof TempGhost)){
    	  for(int i=0;i<ghostInitialPositions.length;i++)
    		  if (ghost.equals(ghostInitialPositions[i][0])){
    	  switch (ghost.getAnimationDirection()) {
    	  case LEFT:
    		  ghostInitialPositions[i][1]=((int)ghostInitialPositions[i][1])-1;
    		  rowResult = (double) ((int) ghostInitialPositions[i][1]-6) / 32;
    		  if (rowResult % 1==0)
    			  ghost.moveEntity();
    		  break;
    	  case RIGHT:
    		  ghostInitialPositions[i][1]=((int)ghostInitialPositions[i][1])+1;
    		  rowResult = (double) ((int) ghostInitialPositions[i][1]-6) / 32;
    		  if (rowResult % 1==0)
    			  ghost.moveEntity();
    	  break;
    	  case DOWN:
    		  ghostInitialPositions[i][2]=((int)ghostInitialPositions[i][2])+1;
    		  colResult = (double) ((int) ghostInitialPositions[i][2]-38) / 32;
    		  if (colResult % 1==0)
    			  ghost.moveEntity();
    		  break;
    	  case UP:
    		  ghostInitialPositions[i][2]=((int)ghostInitialPositions[i][2])-1;
    		  colResult = (double) ((int) ghostInitialPositions[i][2]-38) / 32;
    		  if (colResult % 1==0)
    			  ghost.moveEntity();
    		  break;
    	      }
    	  doPermGhostAnimation(g2d);
    	  
    	  if ((Math.abs(pacman_x-(int)ghostInitialPositions[i][1])<=7 && (Math.abs(pacman_y-(int)ghostInitialPositions[i][2])<=1)) || ((Math.abs(pacman_y-(int)ghostInitialPositions[i][2])<=7) && (Math.abs(pacman_x-(int)ghostInitialPositions[i][1])<=1))) {
    		  collidedGhost = (Ghost)ghostInitialPositions[i][0];
    		  Game.getInstance().getBoards()[boardNum].getLayout()[Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordX()][Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordY()].addEntity(collidedGhost);
    		  distanceGhost=true;
    	  }
    		  
    	  
    	  
    	     }
          }
    	  else
    	  {
    		  ghost.loadImages();
    		  boolean flag=false;
    		  int temp=0;
    		 for (int i=0;i<tempGhostInitialPositions.length;i++){
    			 if (ghost.equals(tempGhostInitialPositions[i][0])){
    			        flag=true;
    			        temp=i;
    			   }
    		    }
    		           if (flag==true){
    				 switch (ghost.getAnimationDirection()) {
    		    	  case LEFT:
    		    		  tempGhostInitialPositions[temp][1]=((int)tempGhostInitialPositions[temp][1])-1;
    		    		  rowResult = (double) ((int) tempGhostInitialPositions[temp][1]-6) / 32;
    		    		  if (rowResult % 1==0)
    		    			  ghost.moveEntity();
    		    		  break;
    		    	  case RIGHT:
    		    		  tempGhostInitialPositions[temp][1]=((int)tempGhostInitialPositions[temp][1])+1;
    		    		  rowResult = (double) ((int) tempGhostInitialPositions[temp][1]-6) / 32;
    		    		  if (rowResult % 1==0)
    		    			  ghost.moveEntity();
    		    	  break;
    		    	  case DOWN:
    		    		  tempGhostInitialPositions[temp][2]=((int)tempGhostInitialPositions[temp][2])+1;
    		    		  colResult = (double) ((int) tempGhostInitialPositions[temp][2]-38) / 32;
    		    		  if (colResult % 1==0)
    		    			  ghost.moveEntity();
    		    		  break;
    		    	  case UP:
    		    		  tempGhostInitialPositions[temp][2]=((int)tempGhostInitialPositions[temp][2])-1;
    		    		  colResult = (double) ((int) tempGhostInitialPositions[temp][2]-38) / 32;
    		    		  if (colResult % 1==0)
    		    			  ghost.moveEntity();
    		    		  break;
    		    	    }
    		    	  g2d.drawImage(ghost.getImages()[0], (int) tempGhostInitialPositions[temp][1], (int) tempGhostInitialPositions[temp][2], this);
    		    	  
    		    	  if ((Math.abs(pacman_x-(int)tempGhostInitialPositions[temp][1])<=7 && (Math.abs(pacman_y-(int)tempGhostInitialPositions[temp][2])<=1)) || ((Math.abs(pacman_y-(int)tempGhostInitialPositions[temp][2])<=7) && (Math.abs(pacman_x-(int)tempGhostInitialPositions[temp][1])<=1))) {
    		    		  collidedGhost = (TempGhost)tempGhostInitialPositions[temp][0];
    		    		  Game.getInstance().getBoards()[boardNum].getLayout()[Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordX()][Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordY()].addEntity(collidedGhost);
    		    		  distanceGhost=true;
     		    	  }
    			       }
    		           else 
    		           {
    		        	  tempCol=0;
    		        	  tempGhostInitialPositions[tempRow][tempCol]=ghost;
    		        	  tempGhostInitialPositions[tempRow][++tempCol]=ghost.getTile().getCoordX()*32+6;
    		        	  tempGhostInitialPositions[tempRow][++tempCol]=ghost.getTile().getCoordY()*32+38;
    	    			  g2d.drawImage(ghost.getImages()[0], (int) tempGhostInitialPositions[tempRow][1], (int) tempGhostInitialPositions[tempRow][2], this);
    	    		      tempRow++;
    	    		   }
    		           flag=false;
     		    	 
    	  }
        }
      }
      
      doPacmanAnimation(g2d);
      
      if (Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection()!=null && pacmanMovementBegan==true){
      switch (Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection()) {
	  case LEFT:
		  outerloop:
		  for (int i=0;i<Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections().length;i++) {
          	if (E_Direction.LEFT.equals(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections()[i]))
          	{
		  pacman_x--;
		  pacmanRowResult = (double) ((int) pacman_x-6) / 32;
		  if (pacmanRowResult % 1==0) {
			  
			  Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().moveEntity(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection());
			  E_Direction dir = savedKeyToDirection(savedKey);
			  for (int j=0;j<Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections().length;j++){
				  if (Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections()[j]==dir){
					  Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().setAnimationDirection(dir);
					  break outerloop;
				  }
			  }
		  }
          	}
		  }
		  break;
	  case RIGHT:
		  outerloop:
		  for (int i=0;i<Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections().length;i++){
          	if (E_Direction.RIGHT.equals(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections()[i]))
          	{
		  pacman_x++;
		  pacmanRowResult = (double) ((int) pacman_x-6) / 32;
		  if (pacmanRowResult % 1==0) {
			  Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().moveEntity(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection());
			  E_Direction dir = savedKeyToDirection(savedKey);
			  for (int j=0;j<Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections().length;j++){
				  if (Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections()[j]==dir){
					  Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().setAnimationDirection(dir);
					  break outerloop;
				  }
			  }
		  }
          	}
		  }
	  break;
	  case DOWN:
		  outerloop:
		  for (int i=0;i<Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections().length;i++) {
          	if (E_Direction.DOWN.equals(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections()[i]))
          	{
		  pacman_y++;
		  pacmanColResult = (double) ((int) pacman_y-38) / 32;
		  if (pacmanColResult % 1==0) {
			  Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().moveEntity(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection());
			  E_Direction dir = savedKeyToDirection(savedKey);
			  for (int j=0;j<Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections().length;j++){
				  if (Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections()[j]==dir){
					  Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().setAnimationDirection(dir);
					  break outerloop;
				  }
			  }
		  }
          	}
		  }
		  break;
	  case UP:
		  outerloop: 
		  for (int i=0;i<Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections().length;i++) {
          	if (E_Direction.UP.equals(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections()[i]))
          	{
		  pacman_y--;
		  pacmanColResult = (double) ((int) pacman_y-38) / 32;
		  if (pacmanColResult % 1==0) {
  
			  Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().moveEntity(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection());
			  E_Direction dir = savedKeyToDirection(savedKey);
			  for (int j=0;j<Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections().length;j++){
				  if (Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections()[j]==dir){
					  Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().setAnimationDirection(dir);
					  break outerloop;
				  }
			  }
		  }
          	}
		  }
		  break;
	      }
      }
      
      if (distanceGhost==true){
			int beforeCollisionPoints = Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getPoints();
			int collisionCoordX = Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordX();
			int collisionCoordY = Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordY();
			Game.getInstance().getBoards()[boardNum].pacmanGhostCollision(); 
			Game.getInstance().getBoards()[boardNum].getLayout()[collisionCoordX][collisionCoordY].removeEntity(collidedGhost);
			decreasePanelSize();
			pacman_x = Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordX()*32+6;
	        pacman_y = Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordY()*32+38;
			if (Game.getInstance().getBoards()[boardNum].getBoardState()==Game.getInstance().getBoards()[boardNum].getNormalState()){
			    ghostInitialSpawn=true;
			    pacmanMovementBegan=false;
			}
			if (beforeCollisionPoints<Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getPoints())
				pacmanMovementBegan=true;
			distanceGhost=false;
		}
      else
		{
		boolean boolCandy=Game.getInstance().getBoards()[boardNum].getLayout()[Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordX()][Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordY()].checkPacmanCandyCollision();
		if (boolCandy==true){
			if (Game.getInstance().getBoards()[boardNum].getLayout()[Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordX()][Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordY()].getCandy() instanceof CandyPoison){
			    ghostInitialSpawn=true;
			    pacmanMovementBegan=false;
			    decreasePanelSize();
			}
			else if (Game.getInstance().getBoards()[boardNum].getLayout()[Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordX()][Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordY()].getCandy() instanceof CandyQuestion) {
				tempGhostInitialPositions=new Object[Constants.MAX_ANSWERS_NUMBER][3];
			}
			Game.getInstance().getBoards()[boardNum].eatCandy(Game.getInstance().getBoards()[boardNum].getLayout()[Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordX()][Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordY()].getCandy());
			if (Game.getInstance().getBoards()[boardNum].getBoardState()==Game.getInstance().getBoards()[boardNum].getQuestionPoppedState() && isSizeIncreased==false) {
				increasePanelSize();
			}
			pacman_x = Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordX()*32+6;
	        pacman_y = Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getCoordY()*32+38;
		}
		
		}
      
      
    	  boolean nextLevel = Game.getInstance().checkMaze(Game.getInstance().getBoards()[boardNum]); 
    	  if (nextLevel==true){
    		  continueLevelPlayer(g2d, Game.getInstance().getBoards()[boardNum].getPlayer());
    		  
    	  }
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
        		}
        	}
        	else
        	 {
        		if (isGameOver==false) {
        			decreasePanelSize();
        		future.cancel(true);
        		//threadPool.shutdown();
        		Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getGameLength().stop();
        		if ((boardNum==0 && (Game.getInstance().getBoards()[1]==null || (Game.getInstance().getBoards()[1]!=null && Game.getInstance().getBoards()[1].getPlayer().getPacmanPlayer().getLifePoints()<=0))) ||
        				boardNum==1 && ((Game.getInstance().getBoards()[0]!=null && Game.getInstance().getBoards()[0].getPlayer().getPacmanPlayer().getLifePoints()<=0))) {
        		for (int i=0;i<Game.getInstance().getBoards().length;i++) {
        			if (Game.getInstance().getBoards()[i]!=null) {
        		Leaderboard gameEnded = new Leaderboard(Game.getInstance().getBoards()[i].getPlayer().getPacmanPlayer());
        		boolean isTopPlayer=SysData.getInstance().checkEndGameStats(gameEnded); // move to controller probably
        		if (isTopPlayer==true){
        			SysData.getInstance().removeGame();
        			SysData.getInstance().addGame(gameEnded);
        			SysData.getInstance().saveLeaderboardData();
        		 }
        		}
        	}
        		GameOver go;
        		if (Game.getInstance().getBoards()[1]!=null)
        		     go = new GameOver(Game.getInstance().getBoards()[0].getPlayer(), Game.getInstance().getBoards()[1].getPlayer(), this);
        		else
        			 go = new GameOver(Game.getInstance().getBoards()[0].getPlayer(), null, this);
        		((GameSession) this.getTopLevelAncestor()).setSize(633, 532);
        		((GameSession) this.getTopLevelAncestor()).getCardPanel().add(go, "gameOverScreen");
        		((GameSession) this.getTopLevelAncestor()).getCardLayout().show(((GameSession) this.getTopLevelAncestor()).getCardPanel(), "gameOverScreen");
        		isGameOver=true;
        			
        		  }
        		else
        		{
        			Component gtPanel=null;
        			if (boardNum==0) {
        			for (Component c : ((GameSession) PlayerBoard.this.getTopLevelAncestor()).getCardPanel().getComponents()) {
	    				if (c.getName()=="player2game") {
    						gtPanel=c;
	    					((PlayerBoard) c).setMyTurn(true);
	    					((PlayerBoard) c).setOpponentDone(true);
	    					((PlayerBoard) c).initializeMultiPlayerGame(Game.getInstance().getBoards()[1].getPlayer(), Game.getInstance().getBoards()[0].getPlayer());
	    					if (Game.getInstance().getBoards()[1].getBoardState()==Game.getInstance().getBoards()[1].getQuestionPoppedState())
	    						((PlayerBoard) c).increasePanelSize();
	    					else
	    						((PlayerBoard) c).decreasePanelSize();
	    				}
					}
				 ((GameSession) PlayerBoard.this.getTopLevelAncestor()).getCardLayout().show(((GameSession) PlayerBoard.this.getTopLevelAncestor()).getCardPanel(), "player2game");
				 ((PlayerBoard) gtPanel).requestFocusInWindow();
        		}
        			else if (boardNum==1) {
    					for (Component c : ((GameSession) PlayerBoard.this.getTopLevelAncestor()).getCardPanel().getComponents()) {
    	    				if (c.getName()=="player1game") {
        						gtPanel=c;
    	    					((PlayerBoard) c).setMyTurn(true);
    	    					((PlayerBoard) c).setOpponentDone(true);
    	    					((PlayerBoard) c).initializeMultiPlayerGame(Game.getInstance().getBoards()[0].getPlayer(), Game.getInstance().getBoards()[1].getPlayer());
    	    					if (Game.getInstance().getBoards()[0].getBoardState()==Game.getInstance().getBoards()[0].getQuestionPoppedState())
    	    						((PlayerBoard) c).increasePanelSize();
    	    					else
    	    						((PlayerBoard) c).decreasePanelSize();
    	    				}
    					}
        				((GameSession) PlayerBoard.this.getTopLevelAncestor()).getCardLayout().show(((GameSession) PlayerBoard.this.getTopLevelAncestor()).getCardPanel(), "player1game");
        				 ((PlayerBoard) gtPanel).requestFocusInWindow();
    				}
        		}
        	   }
        	 }
		}
    }

	/**
	 * display the game length
	 * @param g2d
	 */
	private void drawGameLength(Graphics2D g2d) {
		int coord_x = 560; // coord_x of board for positioning
		int coord_y = 24; // coord_y of board for positioning
			if (Game.getInstance().getBoards()[boardNum]!=null && Game.getInstance().getBoards()[boardNum].getPlayer()!=null){
				g2d.drawString(String.valueOf(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getGameLength().getElapsed()), coord_x, coord_y);
			}	
	}
	
	/**
	 * display the current level that the player is playing in
	 * @param g2d
	 */
	private void drawLevel(Graphics2D g2d) {
		int coord_x = 345; // coord_x of board for positioning
		int coord_y = 695; // coord_y of board for positioning
			if (Game.getInstance().getBoards()[boardNum]!=null && Game.getInstance().getBoards()[boardNum].getPlayer()!=null){
				g2d.drawString(String.valueOf(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getLevel()), coord_x, coord_y);
			}	
	}
	
	/**
	 * display the score of the player
	 * @param g2d
	 */
	private void drawScore(Graphics2D g2d) {
		int coord_x = 570; // coord_x of board for positioning
		int coord_y = 695; // coord_y of board for positioning
			if (Game.getInstance().getBoards()[boardNum]!=null && Game.getInstance().getBoards()[boardNum].getPlayer()!=null){
				g2d.drawString(String.valueOf(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getPoints()), coord_x, coord_y);
			}	
	}

	/**
	 * draw pacman life points as icons in the bottom left corner
	 * @param g2d
	 */
	private void drawPacmanLifePoints(Graphics2D g2d) {
		int coord_x = 6; // coord_x of board for positioning
		int coord_y = 677; // coord_y of board for positioning
			if (Game.getInstance().getBoards()[boardNum]!=null && Game.getInstance().getBoards()[boardNum].getPlayer()!=null){
				for (int j=0;j<Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getLifePoints();j++)
				{
					g2d.drawImage(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getImages()[8], coord_x, coord_y, this);
					coord_x += 27; // distance between 2 pacman icons horizontally
				}
			}
	}
	
	/**
	 * draw pacman nickname on the upper part of board
	 * @param g2d
	 */
	private void drawPacmanNickName(Graphics2D g2d) {
		int len = Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getNickName().length();
		int coord_x = 275; // coord_x of board for positioning
		int coord_y = 24; // coord_y of board for positioning
			if (Game.getInstance().getBoards()[boardNum]!=null && Game.getInstance().getBoards()[boardNum].getPlayer()!=null){
					g2d.drawString(String.valueOf(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getNickName()), coord_x-(len*3), coord_y);
			}
	}
	
	/**
	 * draw countdown for current player
	 * @param g2d
	 */
	private void drawPlayerCountDown(Graphics2D g2d, int num) {
		int len = Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getNickName().length();
		int coord_x = 260; // coord_x of board for positioning
		int coord_y = 240; // coord_y of board for positioning
		int temp = (num/Constants.GAME_SPEED/5)+1;
		g2d.setColor(Color.black);
		g2d.fillRect(180, 200, 280, 300);
		g2d.setColor(new Color(197,191,252));
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
		if (Game.getInstance().getBoards()[boardNum]!=null && Game.getInstance().getBoards()[boardNum].getPlayer()!=null){
			if (boardNum==0 && Game.getInstance().getBoards()[1]==null)
				g2d.drawString("Game starts in...", coord_x, coord_y);
			else
			g2d.drawString(String.valueOf(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getNickName()) + "'s Turn in...", coord_x-(len*3), coord_y);
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 200));
			coord_x=270;
			coord_y+=180;
			g2d.drawString(String.valueOf(temp), coord_x, coord_y);
		}
	}
	
	/**
	 * draw gameover for current player
	 * in case of multiplayer, when only one player reached 0 life points
	 * @param g2d
	 */
	private void drawPlayerGameOver(Graphics2D g2d, int num) {
		int len;
		if (boardNum==0)
		    len = Game.getInstance().getBoards()[1].getPlayer().getPacmanPlayer().getNickName().length();
		else
			len = Game.getInstance().getBoards()[0].getPlayer().getPacmanPlayer().getNickName().length();
		int coord_x = 290; // coord_x of board for positioning
		int coord_y = 270; // coord_y of board for positioning
		g2d.setColor(Color.black);
		g2d.fillRect(160, 200, 320, 210);
		g2d.setColor(new Color(197,191,252));
		g2d.setFont(new Font("TimesRoman", Font.PLAIN, 30)); 
		if (Game.getInstance().getBoards()[boardNum]!=null && Game.getInstance().getBoards()[boardNum].getPlayer()!=null){
			if (boardNum==0)
			    g2d.drawString(String.valueOf(Game.getInstance().getBoards()[1].getPlayer().getPacmanPlayer().getNickName()), coord_x-(len*4), coord_y);
			else
				g2d.drawString(String.valueOf(Game.getInstance().getBoards()[0].getPlayer().getPacmanPlayer().getNickName()), coord_x-(len*4), coord_y);
			g2d.setFont(new Font("TimesRoman", Font.PLAIN, 28));
			coord_x-=115;
			coord_y+=55;
			g2d.drawString("Has run out of life points!", coord_x, coord_y);
		}
	}

	/**
	 *  responsible for animating pacman
	 * @param g2d
	 */
	private void doPacmanAnimation(Graphics g2d) {
			if (Game.getInstance().getBoards()[boardNum]!=null && Game.getInstance().getBoards()[boardNum].getPlayer()!=null){
				if (Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationCount()==Constants.PACMAN_DIRECTION_ANIMATION_IMAGES)
					pacmanAscendingAnimation=false;
				else if (Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationCount()==0)
					pacmanAscendingAnimation=true;
			    if (pacmanAscendingAnimation==true)
			         Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().setAnimationCount(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationCount()+1);
			    else
				     Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().setAnimationCount(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationCount()-1);
			
			
			if (Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationCount()==0)
				g2d.drawImage(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getImages()[0], pacman_x, pacman_y, this);
			else {
			if (Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection()==E_Direction.UP)
				g2d.drawImage(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getImages()[Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationCount()], pacman_x, pacman_y, this);
			else if (Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection()==E_Direction.DOWN)
				g2d.drawImage(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getImages()[Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationCount()+3], pacman_x, pacman_y, this);
			else if (Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection()==E_Direction.LEFT)
				g2d.drawImage(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getImages()[Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationCount()+6], pacman_x, pacman_y, this);
			else if (Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection()==E_Direction.RIGHT)
				g2d.drawImage(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getImages()[Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationCount()+9], pacman_x, pacman_y, this);
			}
		  }
			
	}
	
	/**
	 *  responsible for animating permanent ghosts
	 * @param g2d
	 */
	private void doPermGhostAnimation(Graphics g2d) {
			if (Game.getInstance().getBoards()[boardNum]!=null && Game.getInstance().getBoards()[boardNum].getGhosts()!=null){
				for (Ghost ghost: Game.getInstance().getBoards()[boardNum].getGhosts()){
			    if (ghost.getAnimationCount()==Constants.GAME_SPEED*25){
			         ghost.setAnimationCount(0);
			    }
			    else
				     ghost.setAnimationCount(ghost.getAnimationCount()+1);
			    
			    for (int j=0;j<ghostInitialPositions.length;j++)
			    	if (ghost.equals(ghostInitialPositions[j][0]))
			    		if (ghost.getAnimationCount()>=0 && ghost.getAnimationCount()<=Constants.GAME_SPEED*15)
			           g2d.drawImage(ghost.getImages()[0], (int) ghostInitialPositions[j][1], (int) ghostInitialPositions[j][2], this);
			    		else if (ghost.getAnimationCount()>=Constants.GAME_SPEED*15+1 && ghost.getAnimationCount()<=Constants.GAME_SPEED*18+4)
			    			g2d.drawImage(ghost.getImages()[1], (int) ghostInitialPositions[j][1], (int) ghostInitialPositions[j][2], this);
			    		else if (ghost.getAnimationCount()>=Constants.GAME_SPEED*18+5 && ghost.getAnimationCount()<=Constants.GAME_SPEED*21+8)
			    			g2d.drawImage(ghost.getImages()[2], (int) ghostInitialPositions[j][1], (int) ghostInitialPositions[j][2], this);
			    		else if (ghost.getAnimationCount()>=Constants.GAME_SPEED*21+9 && ghost.getAnimationCount()<=Constants.GAME_SPEED*25)
			    			g2d.drawImage(ghost.getImages()[3], (int) ghostInitialPositions[j][1], (int) ghostInitialPositions[j][2], this);
		    }
		  }
	}

	
	/**
	 *  responsible for pacman movement input from player
	 * 
	 *
	 */
	private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();
            double rowResult, colResult;
            
            rowResult = (double) ((int) pacman_x-6) / 32;
            colResult = (double) ((int) pacman_y-38) / 32;
            
            if (rowResult % 1==0 && colResult % 1==0)
            {
                if (key == KeyEvent.VK_LEFT && Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection()!=E_Direction.LEFT) {
                	Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().setAnimationDirection(E_Direction.LEFT);
                	for (int i=0;i<Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections().length;i++)
                	if (E_Direction.LEFT.equals(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections()[i]))
                	{
                	pacman_x--;
                	rowResult = (double) ((int) pacman_x-6) / 32;
		    		  if (rowResult % 1==0)
		    			  Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().moveEntity(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection());
                	}
                } else if (key == KeyEvent.VK_RIGHT && Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection()!=E_Direction.RIGHT) {
                	Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().setAnimationDirection(E_Direction.RIGHT);
                	for (int i=0;i<Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections().length;i++)
                    	if (E_Direction.RIGHT.equals(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections()[i]))
                    	{
                	pacman_x++;
                	rowResult = (double) ((int) pacman_x-6) / 32;
		    		  if (rowResult % 1==0)
		    			  Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().moveEntity(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection());
                    	}
                } else if (key == KeyEvent.VK_UP && Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection()!=E_Direction.UP) {
                    Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().setAnimationDirection(E_Direction.UP);
                    for (int i=0;i<Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections().length;i++)
                    	if (E_Direction.UP.equals(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections()[i]))
                    	{
                    pacman_y--;
                    colResult = (double) ((int) pacman_y-38) / 32;
		    		  if (colResult % 1==0)
		    			  Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().moveEntity(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection());
                    	}
                } else if (key == KeyEvent.VK_DOWN && Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection()!=E_Direction.DOWN) {
                    Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().setAnimationDirection(E_Direction.DOWN);
                    for (int i=0;i<Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections().length;i++)
                    	if (E_Direction.DOWN.equals(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getTile().getPossibleDirections()[i]))
                    	{
                    pacman_y++;
                    colResult = (double) ((int) pacman_y-38) / 32;
		    		  if (colResult % 1==0)
		    			  Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().moveEntity(Game.getInstance().getBoards()[boardNum].getPlayer().getPacmanPlayer().getAnimationDirection());
                    	}
                }
                E_Direction dir = savedKeyToDirection(key);
                if (pacmanMovementBegan==false && dir!=null)
                pacmanMovementBegan=true;
            }
                savedKey = e.getKeyCode();
        }
    }
	
	/**
	 * translates KeyEvent to Direction
	 * @param key
	 * @return
	 */
	private E_Direction savedKeyToDirection (int key) {
		if (key == KeyEvent.VK_LEFT)
			return E_Direction.LEFT;
		else if (key == KeyEvent.VK_RIGHT)
			return E_Direction.RIGHT;
		else if (key == KeyEvent.VK_UP)
			return E_Direction.UP;
		else if (key == KeyEvent.VK_DOWN)
			return E_Direction.DOWN;
		return null;
	}
	
	/**
	 * increase board size to display popped question and possible answers
	 */
	private void increasePanelSize() {
		
		Container c = this.getTopLevelAncestor();
       if (c instanceof JFrame) {
            JFrame f = (JFrame) c;
            f.pack();
            f.setSize(1142, 702);
            isSizeIncreased=true;
        }
	}
	
	/**
	 * decrease board size to default size
	 */
	private void decreasePanelSize() {
		
		Container c = this.getTopLevelAncestor();
        if (c instanceof JFrame) {
            JFrame f = (JFrame) c;
            f.pack();
            f.setSize(642, 702);
            isSizeIncreased=false;
        }
	}
	
	/**
	 * draw the question text and the possible answers
	 * @param g2d
	 */
	private void drawQuestion(Graphics2D g2d) {
			int coord_x=665, coord_y=50;
			boolean isFirstPart = true;
	        g2d.setFont(new Font("TimesRoman", Font.BOLD, 20)); 
			g2d.setColor(new Color(197,191,252));
			String str = "Q.  " + ((QuestionPoppedState) Game.getInstance().getBoards()[boardNum].getQuestionPoppedState()).getQuestion().getQText();
			List<String> stringParts = getParts(str, ((int)(51/Double.valueOf(Constants.SCALING_SIZE)-3)));
			for (String s : stringParts) {
				if (isFirstPart==true) {
		           g2d.drawString( s , coord_x, coord_y);
		           isFirstPart=false;
				}
				else {
					g2d.drawString( "       " + s , coord_x, coord_y);
				}
		        coord_y+=20;
			}
			coord_y+=50;
			
			TreeSet<TempGhost> sortedTempGhosts = new TreeSet<TempGhost>();
			for (int i=0; i<tempGhostInitialPositions.length; i++) {
				if (tempGhostInitialPositions[i][0]!=null)
				sortedTempGhosts.add((TempGhost) tempGhostInitialPositions[i][0]);
			}
			
			
			for (TempGhost g: sortedTempGhosts) {
				if (g!=null) {
				   g2d.drawImage(g.getImages()[0] , coord_x, coord_y, this);
				   coord_x+=40;
				   coord_y+=15;
			       g2d.drawString(g.getAnswer().getAText() , coord_x, coord_y);
			       coord_y+=30;
			       coord_x-=20;
				}
				coord_x-=20;
			}
			coord_x = 795;
			coord_y = 400;
			g2d.drawString("Difficulty: " + String.valueOf(((QuestionPoppedState) Game.getInstance().getBoards()[boardNum].getQuestionPoppedState()).getQuestion().getDifficultyLevel()), coord_x, coord_y);
			coord_x-= 30;
			coord_y+= 35;
			if (((QuestionPoppedState) Game.getInstance().getBoards()[boardNum].getQuestionPoppedState()).getQuestion().getDifficultyLevel().equals(E_DifficultyLevel.EASY))
			{
				g2d.drawString("Correct Answer: +" + Constants.CORRECT_EASY_ANSWER + " Points", coord_x, coord_y);
				coord_x+= 5;
				coord_y+= 30;
				g2d.drawString("Wrong Answer: -" + Constants.WRONG_EASY_ANSWER + " Points", coord_x, coord_y);
			}
			else if (((QuestionPoppedState) Game.getInstance().getBoards()[boardNum].getQuestionPoppedState()).getQuestion().getDifficultyLevel().equals(E_DifficultyLevel.MEDIUM))
			{
				g2d.drawString("Correct Answer: +" + Constants.CORRECT_MEDIUM_ANSWER + " Points", coord_x, coord_y);
				coord_x+= 5;
				coord_y+= 30;
				g2d.drawString("Wrong Answer: -" + Constants.WRONG_MEDIUM_ANSWER + " Points", coord_x, coord_y);
			}
			else if (((QuestionPoppedState) Game.getInstance().getBoards()[boardNum].getQuestionPoppedState()).getQuestion().getDifficultyLevel().equals(E_DifficultyLevel.HARD))
			{
				g2d.drawString("Correct Answer: +" + Constants.CORRECT_HARD_ANSWER + " Points", coord_x, coord_y);
				coord_x+= 5;
				coord_y+= 30;
				g2d.drawString("Wrong Answer: -" + Constants.WRONG_HARD_ANSWER + " Points", coord_x, coord_y);
			}
	}
	
	/**
	 * break lines in question properly without words being cut
	 * @param s
	 * @param limit
	 * @return
	 */
	public static List<String> getParts(String s, int limit) 
	{
	    List<String> parts = new ArrayList<String>();
	    while(s.length() > limit)
	    {
	        int splitAt = limit-1;
	        for(;splitAt>0 && !Character.isWhitespace(s.charAt(splitAt)); splitAt--);           
	        if(splitAt == 0) 
	            return parts; // can't be split
	        parts.add(s.substring(0, splitAt));
	        s = s.substring(splitAt+1);
	    }
	    parts.add(s);
	    return parts;
	}
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}

    public boolean isMyTurn() {
		return isMyTurn;
	}

	public void setMyTurn(boolean isMyTurn) {
		this.isMyTurn = isMyTurn;
	}

	public boolean isOpponentDone() {
		return isOpponentDone;
	}

	public void setOpponentDone(boolean isOpponentDone) {
		this.isOpponentDone = isOpponentDone;
	}
	
	/**
	 * removes the arrow from combobox
	 * @param container
	 */
	private static void removeButton(Container container) {
	      Component[] components = container.getComponents();
	      for (Component component : components) {
	         if (component instanceof AbstractButton) {
	            container.remove(component);
	         }
	      }
	   }
	
	/**
	 * gives the option to return to the main menu or exit the game completely
	 */
	private void exitGame() {
	Object[] options = {"Yes", "No"};
    int n = JOptionPane.showOptionDialog(this, "Would you like to return to the main menu?",
     "Exiting Game",
    JOptionPane.YES_NO_OPTION,
    JOptionPane.QUESTION_MESSAGE,
    null,     
    options, 
    options[0]);
    if (n==0) {
    	Platform.runLater(new Runnable(){

			@Override
			public void run() {
				Controller.loadStage("/view/mainStyle.fxml");
			}
			});
		((JFrame) this.getTopLevelAncestor()).dispose();
    }
    else if (n==1) {
    	Platform.setImplicitExit(true);
    	Platform.exit();
    	System.exit(1);
    }
    else if (n == JOptionPane.CLOSED_OPTION) {
    	menu.transferFocus();
        PlayerBoard.this.requestFocusInWindow();
    }
	}

}
