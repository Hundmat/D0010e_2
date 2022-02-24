package lab4.gui;
import java.util.Observable;
import java.util.Observer;

import java.awt.Container;
import java.awt.event.*;

import javax.swing.*;

import lab4.GomokuMain;
import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;


/*
 * The GUI class
 */

public class GomokuGUI implements Observer{
	private JFrame frame = new JFrame("Gomoku server");
	private JLabel messageLabel = new JLabel("Gomoku");
	
	private JButton connectButton = new JButton("Connect");
	private JButton newGameButton = new JButton("New Game");
	private JButton disconnectButton = new JButton("Connect");
	
	private GomokuClient client;
	private GomokuGameState gamestate;
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		this.client = c;
		this.gamestate = g;
		client.addObserver(this);
		gamestate.addObserver(this);
		
		
		//LABELS
		Box message = Box.createVerticalBox();
		message.add(messageLabel);
		
		//BUTTONS
		Box buttons = Box.createHorizontalBox();
		buttons.add(connectButton);
		buttons.add(newGameButton);
		buttons.add(disconnectButton);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
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
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
		
	}
	
}