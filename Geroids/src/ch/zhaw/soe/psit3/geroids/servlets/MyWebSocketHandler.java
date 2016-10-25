package ch.zhaw.soe.psit3.geroids.servlets;

import java.io.IOException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import ch.zhaw.soe.psit3.geroids.domain.ConsoleInput;

@WebSocket
public class MyWebSocketHandler {

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
		System.out.println("Connect: " + session.getRemoteAddress().getAddress());
		try {
			//getRemote() Return a reference to the RemoteEndpoint object representing the other end of this conversation.
			//sendString() Send a text message, blocking until all bytes of the message has been transmitted.
			session.getRemote().sendString("Hallo Client(from Server)!");
			new ConsoleInput("consoleInput", session).start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnWebSocketMessage
	public void onMessage(String message) {
		System.out.println("Message: " + message);
	}
}