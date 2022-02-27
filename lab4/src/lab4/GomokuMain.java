package lab4;
import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.*;
/**
 *  @author Anton Follinger
 *  
 * Main method where we create all objects for the game
 */
public class GomokuMain{
	static void	main(String[] args){
		
		GomokuClient client;
		
        if(args.length != 1){
        	client = new GomokuClient(5555);          
        }
        else{
        	try {
        		client = new GomokuClient(Integer.parseInt(args[0]));
        	}
        	catch (NumberFormatException e) {
        		client = new GomokuClient(5555);  
			}
        }

        GomokuGameState gameState = new GomokuGameState(client);
        GomokuGUI gui = new GomokuGUI(gameState, client);
	}
}
