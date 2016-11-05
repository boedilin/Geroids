package ch.zhaw.soe.psit3.geroids.servlets;

import java.io.IOException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import ch.zhaw.soe.psit3.geroids.domain.ConsoleInput;
import ch.zhaw.soe.psit3.geroids.domain.Game;

@WebSocket
public class MyWebSocketHandler {
	
	private Session session;
	private Game game;

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) {
		System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
	}

	@OnWebSocketError
	public void onError(Throwable t) {
		System.out.println("Error: " + t.getMessage());
	}

	@OnWebSocketConnect
	public void onConnect(Session session) {
		this.session = session;
		session.setIdleTimeout(200000); // 200 second timeout
		
		//Wenn eine neue Verbindung aufgebaut wird, soll ein neues Game instanziert werden.
		//Dem Game wird die session übergeben, die die Verbidung zum Client repräsentiert.
		game = new Game(this);
		/*
		System.out.println("Connect: " + session.getRemoteAddress().getAddress());
		try {
			//getRemote() Return a reference to the RemoteEndpoint object representing the other end of this conversation.
			//sendString() Send a text message, blocking until all bytes of the message has been transmitted.
			session.getRemote().sendString("Hallo Client(from Server)!");
			new ConsoleInput("consoleInput", session).start();
			System.out.println(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}

	@OnWebSocketMessage
	public void onMessage(String message) {
		//System.out.println("Message: " + message);
		game.receiveMessage(message);
	}
	
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