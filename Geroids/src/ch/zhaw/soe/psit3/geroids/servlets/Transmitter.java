package ch.zhaw.soe.psit3.geroids.servlets;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.RemoteEndpoint;

import ch.zhaw.soe.psit3.geroids.db.Highscore;
import ch.zhaw.soe.psit3.geroids.domain.Figure;
import ch.zhaw.soe.psit3.geroids.domain.Parser;
import ch.zhaw.soe.psit3.geroids.domain.Playscore;

public class Transmitter {

	private RemoteEndpoint remoteEndpoint;

	public Transmitter(RemoteEndpoint remoteEndpoint){
		this.remoteEndpoint = remoteEndpoint;
	}

	public void sendScore(Playscore score) {
		try {
			remoteEndpoint.sendString(Parser.scoreToJsonString(score));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendCoordinates(Figure figure) {
		try {
			remoteEndpoint.sendString(Parser.figureToJsonString(figure));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendHighscore(Highscore highscore) {
		try {
			remoteEndpoint.sendString(Parser.highscoreToJsonString(highscore));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
