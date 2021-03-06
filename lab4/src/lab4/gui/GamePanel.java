package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;
import lab4.data.GameGrid.Player;

/**
 * @author Anton Follinger
 * 
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer{
	
	private final int UNIT_SIZE = 20;
	public GameGrid grid;
	
	/**
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid){
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
	}

	/**
	 * Returns a grid position given pixel coordinates
	 * of the panel
	 * 
	 * @param x the x coordinates
	 * @param y the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y){
		return new int[] {x / UNIT_SIZE, y / UNIT_SIZE};
	}
	
	/**
	 * Repaint the grid
	 * 
	 * @param arg0 the x coordinates
	 * @param arg1 the y coordinates
	 */
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	/**
	 * Paint the grid
	 * 
	 * @param g the graphic object
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
        g.fillRect(0, 0, grid.getSize() * UNIT_SIZE, grid.getSize() * UNIT_SIZE);
        
        for(int y = 0; y < grid.getSize(); y++) {
    	    for(int x = 0; x < grid.getSize(); x++) {
    	    	switch (grid.getLocation(x, y)) {
	    	    	case OTHER:
	    	    		g.setColor(Color.RED);
	    	    		g.fillRect(x * UNIT_SIZE, y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
	    			case ME:
	    				g.setColor(Color.GREEN);
	    				g.fillRect(x * UNIT_SIZE, y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
	    			case EMPTY:
	    				g.setColor(Color.black);
	    				g.drawRect(x * UNIT_SIZE, y * UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
	    			default:
	    			   break;
    	    	}
    	    }
        }
	}
}

	

