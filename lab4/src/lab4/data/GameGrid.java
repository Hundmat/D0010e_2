package lab4.data;

import java.util.Observable;

import javax.print.attribute.Size2DSyntax;

/**
 * @author Anton Follinger
 * 
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
		grid = new Player[size][size];
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
	 * Controls if the position is taken
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y){
		
		if(this.grid[x][y]==Player.EMPTY){
			setChanged();
			notifyObservers();
			return true;
		}
		else {
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
	public boolean isWinner(Player player) {

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (this.grid[x][y] == player.ME) {
                    int currentInRow = 0;

                    for (int i = 0; i < this.INROW; i++) {
                        boolean check = CheckUp(x, y, player);
                        if (check == true) {
                            currentInRow += 1;
                        } else {
                            currentInRow = 0;
                            break;
                        }
                        return true;
                    }

                    for (int i = 0; i < this.INROW; i++) {
                        boolean check = CheckRight(x, y, player);
                        if (check == true) {
                            currentInRow += 1;
                        } else {
                            currentInRow = 0;
                            break;
                        }
                        return true;
                    }

                    for (int i = 0; i < this.INROW; i++) {
                        boolean check = CheckDiagonalRight(x, y, player);
                        if (check == true) {
                            currentInRow += 1;
                        } else {
                            currentInRow = 0;
                            break;
                        }
                        return true;
                    }

                    for (int i = 0; i < this.INROW; i++) {
                        boolean check = CheckDiagonalLeft(x, y, player);
                        if (check == true) {
                            currentInRow += 1;
                        } else {
                            currentInRow = 0;
                            break;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean CheckUp(int x, int y, Player player) {
        if (y == size) {
            return false;
        }

        if (this.grid[x][y + 1] == player.ME) {
            return true;
        } else {
            return false;
        }
    }

    private boolean CheckRight(int x, int y, Player player) {
        if (x == size) {
            return false;
        }

        if (this.grid[x + 1][y] == player.ME) {
            return true;
        } else {
            return false;
        }
    }

    private boolean CheckDiagonalRight(int x, int y, Player player) {

        if (x == size || y == size) {
            return false;
        }

        if (this.grid[x + 1][y + 1] == player.ME) {
            return true;
        } else {
            return false;
        }
    }

    private boolean CheckDiagonalLeft(int x, int y, Player player) {
        if (x == 0 || y == size) {
            return false;
        }

        if (this.grid[x - 1][y + 1] == player.ME) {
            return true;
        } else {
            return false;
        }
    }

}


