package ch.zhaw.soe.psit3.geroids.domain;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

import org.eclipse.jetty.websocket.api.Session;

public class ConsoleInput extends Thread {

	private Scanner scanner;
	private Session session;
	
	public ConsoleInput(String str, Session session){
		super(str);
		this.session = session;
	}
	
	public void run(){
		scanner = new Scanner(System.in);
		while(true){
			System.out.println("Enter something for the Client: ");
			String input = scanner.nextLine();
			try {
				session.getRemote().sendString(input+ " " + new Date().getTime());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
