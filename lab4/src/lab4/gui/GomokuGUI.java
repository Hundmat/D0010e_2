package lab4.gui;
import java.util.Observable;
import java.util.Observer;

import java.awt.Container;
import java.awt.event.*;

import javax.swing.*;

import lab4.GomokuMain;
import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GameGrid.Player;
import lab4.data.GomokuGameState;


/**
 *  @author Anton Follinger
 *  
 *  This class create the window for the game with buttons and the grid
 */

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{
	private JFrame f = new JFrame("Gomoku server");
	private JLabel messageLabel = new JLabel("Gomoku");
	
	private JButton connectButton = new JButton("Connect");
	private JButton newGameButton = new JButton("New Game");
	private JButton disconnectButton = new JButton("disconnect");
	
	private GomokuClient client;
	private GomokuGameState gameState;
	private GamePanel gamePanel;
	
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		this.client = c;
		this.gameState = g;
		client.addObserver(this);
		gameState.addObserver(this);
		gamePanel = new GamePanel(gameState.getGameGrid());
		Container contain = f.getContentPane();
		//Box is a layout manager that allows multiple components to be laid out either vertically or horizontally
		
		//LABELS
		Box message = Box.createHorizontalBox();
		message.add(messageLabel);
		
		//BUTTONS
		Box buttons = Box.createHorizontalBox();
		buttons.add(connectButton);
		buttons.add(newGameButton);
		buttons.add(disconnectButton);
		
		//All things
		Box verBox = Box.createVerticalBox();
		verBox.add(gamePanel);
		verBox.add(message);
		verBox.add(buttons);
		
		contain.add(verBox);
		
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//the pack method sizes the frame so that all its contents are at or above their preferred sizes
		f.pack();
		f.setVisible(true);
		
		gamePanel.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int[] position = gamePanel.getGridPosition(e.getX(), e.getY());
				
				gameState.move(position[0], position[1]);
				gamePanel.grid = gameState.getGameGrid();
			}
		});
		
		connectButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ConnectionWindow connectionWindow = new ConnectionWindow(c);
				
			}
		});
		
		disconnectButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameState.disconnect();	
			}
		});

		newGameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameState.newGame();
			}
		});
	}
	
	
	
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gameState){
			messageLabel.setText(gameState.getMessageString());
		}
		
	}
	
}