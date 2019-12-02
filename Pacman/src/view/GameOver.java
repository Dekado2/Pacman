package view;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;

import controller.Controller;
import javafx.application.Platform;
import model.Leaderboard;
import model.PacmanPlayer;
import model.PacmanPlayerAdapter;
import model.SysData;
import utils.E_Direction;

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameOver extends JPanel {

	/**
	 * Create the panel.
	 * displaying score of player 1 and player 2 if it was a multiplayer game
	 * if either players reached top 10 in leaderboard, a message is displayed
	 */
	public GameOver(PacmanPlayerAdapter player1, PacmanPlayerAdapter player2, PlayerBoard gt) {
		setBackground(Color.BLACK);
		setForeground(Color.BLACK);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Game Over!");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 47));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(110, 43, 412, 68);
		add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel(player1.getPacmanPlayer().getNickName() + ":");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(41, 198, 136, 26);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel(player1.getPacmanPlayer().getPoints() + " Points , ");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(187, 203, 153, 18);
		add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel(player1.getPacmanPlayer().getLevel() + " Level , ");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_4.setForeground(Color.WHITE);
		lblNewLabel_4.setBounds(328, 203, 124, 18);
		add(lblNewLabel_4);
		
		JLabel lblPlaytime = new JLabel(player1.getPacmanPlayer().getGameLength().getElapsed() + "  Playtime");
		lblPlaytime.setForeground(Color.WHITE);
		lblPlaytime.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlaytime.setBounds(414, 199, 213, 25);
		add(lblPlaytime);
		
		JButton btnTryAgain = new JButton("Try Again");
		btnTryAgain.setBounds(165, 416, 110, 23);
		add(btnTryAgain);
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setBounds(374, 416, 110, 23);
		add(btnQuit);
		Leaderboard lb = new Leaderboard(player1.getPacmanPlayer());
		int top = SysData.getInstance().getLeaderboardList().headSet(lb).size()+1;
		JLabel lblPlayerHas;
		if (top<11) {
		lblPlayerHas = new JLabel(player1.getPacmanPlayer().getNickName() + " Has Reached Top " + top + " Leaderboard Score!");
		lblPlayerHas.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerHas.setForeground(Color.WHITE);
		lblPlayerHas.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayerHas.setBounds(84, 305, 469, 23);
		add(lblPlayerHas);
		}
		
		if (player2!=null) {
			
		Leaderboard lb2 = new Leaderboard(player2.getPacmanPlayer());
		int top2 = SysData.getInstance().getLeaderboardList().headSet(lb2).size()+1;
		JLabel lblPlayerHas_1;
		if (top2<11) {
		lblPlayerHas_1 = new JLabel(player2.getPacmanPlayer().getNickName() + " Has Reached Top " + top2 + " Leaderboard Score!");
		lblPlayerHas_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlayerHas_1.setForeground(Color.WHITE);
		lblPlayerHas_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayerHas_1.setBounds(84, 345, 469, 23);
		add(lblPlayerHas_1);
		}
		
		JLabel lblNewLabel_1;
		int res = lb.compareTo(lb2);
		if (res==-1)	
		    lblNewLabel_1 = new JLabel(player1.getPacmanPlayer().getNickName() + " Wins!");
		else if (res==1)
		    lblNewLabel_1 = new JLabel(player2.getPacmanPlayer().getNickName() + " Wins!");
		    else
		    lblNewLabel_1 = new JLabel("Draw!");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 28));
		lblNewLabel_1.setBounds(150 ,122, 325, 34);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel_1);
		
		
		JLabel lblPlayer = new JLabel(player2.getPacmanPlayer().getNickName() + ":");
		lblPlayer.setForeground(Color.WHITE);
		lblPlayer.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlayer.setBounds(41, 235, 136, 26);
		add(lblPlayer);
		
		JLabel lblPoints = new JLabel(player2.getPacmanPlayer().getPoints() + " Points , ");
		lblPoints.setForeground(Color.WHITE);
		lblPoints.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPoints.setBounds(187, 240, 153, 18);
		add(lblPoints);
		
		JLabel lblLevel = new JLabel(player2.getPacmanPlayer().getLevel() + " Level , ");
		lblLevel.setForeground(Color.WHITE);
		lblLevel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblLevel.setBounds(328, 240, 124, 18);
		add(lblLevel);
		
		JLabel lblPlaytime_1 = new JLabel(player2.getPacmanPlayer().getGameLength().getElapsed() + "  Playtime");
		lblPlaytime_1.setForeground(Color.WHITE);
		lblPlaytime_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPlaytime_1.setBounds(414, 236, 213, 25);
		add(lblPlaytime_1);
		}
		
		btnTryAgain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				PacmanPlayer p = new PacmanPlayer(E_Direction.RIGHT, player1.getPacmanPlayer().getNickName());
	    	    PacmanPlayerAdapter pa = new PacmanPlayerAdapter(p);
	    	    PacmanPlayer p2 = null;
	    	    PacmanPlayerAdapter pa2 = null;
	    	    if (player2!=null) {
	    	    p2 = new PacmanPlayer(E_Direction.RIGHT, player2.getPacmanPlayer().getNickName());
	    	    pa2 = new PacmanPlayerAdapter(p2);
	    	    }
				gt.setPreferredSize(new Dimension(642, 702));
				GameSession pt = new GameSession(pa, pa2);
				pt.setVisible(true);
				
        		((JFrame) GameOver.this.getTopLevelAncestor()).dispose();
        	
			}
		});
		
		btnQuit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				Platform.runLater(new Runnable(){

					@Override
					public void run() {
				    Controller.loadStage("/view/mainStyle.fxml");
					}
					});
				((JFrame) GameOver.this.getTopLevelAncestor()).dispose();
			}
		});
	}
}
