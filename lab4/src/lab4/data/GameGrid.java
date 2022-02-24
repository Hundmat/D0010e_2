package lab4.data;

import java.util.Observable;

import javax.print.attribute.Size2DSyntax;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{
	private int INROW = 5;
	
	private int size;
	
	public Player[][] grid;
	
	public static enum Player{
		EMPTY,
		ME,
		OTHER
	}
	
	/**
	 * Constructor
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		this.size = size;
		for(int x=0;x<this.size;x++){
			for(int y=0;y<this.size;y++){
				this.grid[x][y]=Player.EMPTY;
			}
		}
	}
	
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public Player getLocation(int x, int y){
		return grid[x][y];
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return grid.length;
	}
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, Player player){
		
		if(this.grid[x][y]!=Player.EMPTY){
			return false;
		}
		
		switch(player){
			case ME:
				this.grid[x][y]=Player.ME;
				setChanged();
				notifyObservers();
				return true;
			case OTHER:
				this.grid[x][y]=Player.OTHER;
				setChanged();
				notifyObservers();
				return true;
			default:
				return false;
		}
		
	}
	
	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid(){
		for(int x=0;x<this.size;x++){
			for(int y=0;y<this.size;y++){
				this.grid[x][y]=Player.EMPTY;
			}
		}
		setChanged();
		notifyObservers();
	}
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(Object player){
		return false;
		
	}
}	

