package ch.zhaw.soe.psit3.geroids.servlets;

import java.io.IOException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import ch.zhaw.soe.psit3.geroids.domain.Game;

@WebSocket
public class MyWebSocketHandler {
	
	private Session session;
	private Game game;
	
	/**
	 * Print status Code and reason to console
	 */
	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
	}

	/**
	 * Print error to console
	 */
	@OnWebSocketError
	public void onError(Throwable t) {
		System.out.println("Error: " + t.getMessage());
	}

	/**
	 * create a new game on connection and set connection timeout to 200 seconds
	 */
	@OnWebSocketConnect
	public void onConnect(Session session) {
		this.session = session;
		session.setIdleTimeout(200000); // 200 second timeout
		
		//Wenn eine neue Verbindung aufgebaut wird, soll ein neues Game instanziert werden.
		//Dem Game wird der Websockethandler übergeben, der die Verbidung zum Client handelt.
		game = new Game(this);
		game.startGame();
	}
	
	/**
	 * send message from client to the game
	 */
	@OnWebSocketMessage
	public void onMessage(String message) {
		game.receiveMessage(message);
	}
	
	/**
	 * send message from game to client
	 */
	//think about blocking and non-blocking sending
	public void sendMessage(String message){
		try {
			session.getRemote().sendString(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}