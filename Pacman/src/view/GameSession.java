package view;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Game;
import model.PacmanPlayerAdapter;

public class GameSession extends JFrame {
	
	private JPanel cardPanel;
	private CardLayout cardLayout = new CardLayout();
	
    public GameSession(PacmanPlayerAdapter player1, PacmanPlayerAdapter player2) {
        initUI(player1, player2);
    }

    /**
     * initialize game boards
     * either for single or multiplayer
     * @param player1
     * @param player2
     */
    private void initUI(PacmanPlayerAdapter player1, PacmanPlayerAdapter player2) {
    	
    	int boardNum=0;
    	cardPanel = new JPanel();
    	setContentPane(cardPanel);
    	cardPanel.setLayout(cardLayout);
    	PlayerBoard gt;
    	PlayerBoard gt2;
    	Game.getInstance().initiateGame(player1, player2);
    	setUndecorated(true);
    	if (player2==null) {
           gt = new PlayerBoard(player1);
           cardPanel.add(gt, "player1game");
    	}
    	else
    	{
    		gt = new PlayerBoard(player1, player2, boardNum++, true);
    		gt2 = new PlayerBoard(player2, player1, boardNum, false);
    		gt.setName("player1game");
    		gt2.setName("player2game");
    		cardPanel.add(gt, "player1game");
    		cardPanel.add(gt2, "player2game");
    	}
    	
    setTitle("Pacman-Viper");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(642, 702);
    setLocationRelativeTo(null);
    setVisible(true);        
    setResizable(false);
    }

	public JPanel getCardPanel() {
		return cardPanel;
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}

	
	
}
