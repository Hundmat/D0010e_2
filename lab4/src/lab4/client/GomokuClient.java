package lab4.client;

public class GomokuClient extends java.util.Observable implements java.lang.Runnable {
	//The client has connected to another client
	static int CLIENT=0;
	//The client has received a connection from another client
    static int	SERVER=0;
    //The client has no connection
    static int UNCONNECTED=0;
    void disconnect(){
    	
    }
    
    void foundConnection(java.io.PrintWriter writer, java.io.BufferedReader reader, boolean amClient) {
    	
    }
    
    int	getConnectionStatus(){
    	
    }
    
	public void run() {
		
	}
	
	 boolean sendMoveMessage(int x, int y) {
		 
	 }
	 
	 boolean sendNewGameMessage() {
		 
		 
	 }
	 void setGameState(GomokuGameState gs) {
		 
	 }
}
