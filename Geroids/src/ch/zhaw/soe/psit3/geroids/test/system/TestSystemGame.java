//TODO remove syso's in while loops. Atm neccessairy because of missing synchronized variables.

package ch.zhaw.soe.psit3.geroids.test.system;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import ch.zhaw.soe.psit3.geroids.domain.Figure;
import ch.zhaw.soe.psit3.geroids.domain.Game;
import ch.zhaw.soe.psit3.geroids.domain.Geroid;
import ch.zhaw.soe.psit3.geroids.domain.Position;
import ch.zhaw.soe.psit3.geroids.servlets.MyWebSocketHandler;

public class TestSystemGame {
	private final int FIGURE_X_POS = 500;
	private final int FIGURE_Y_POS = 898;
	private final int FIGURE_X_LENGTH = 61;
	private final int FIGURE_Y_LENGTH = 90;
	private Figure figure;;
	private MyWebSocketHandler myWebSocketHandler;
	private Game game;

	@Before
	public void setUp() throws Exception {
		figure = new Figure(new Position(FIGURE_X_POS, FIGURE_Y_POS, FIGURE_X_LENGTH, FIGURE_Y_LENGTH));
		myWebSocketHandler = new MyWebSocketHandler();
		game = new Game(myWebSocketHandler);
		game.setFigure(figure);
	}

	/**
	 * Starts a game, moves to the X Coordiante of the nearest geroid and waits
	 * for the collision and checks if game is over.
	 * 
	 */
	@Test
	public void testGameOver() {
		ArrayList<Geroid> geroids = game.getGeroids();
		Geroid nearestXGeroid;
		game.startGame();
		if (!waitForEntryInGeroids()) {
			nearestXGeroid = game.getGeroids().get(0);
			nearestXGeroid = chooseNearestGeroidsOnXAxis(geroids, nearestXGeroid);
			moveIntoCollidingXCoordinates(nearestXGeroid);
			waitUntilCollisionWithGeroid(nearestXGeroid);
		}
		assertFalse(game.isRunning());
	}

	/**
	 * Starts a game, moves to the X Coordiante of the nearest geroid shoots it
	 * down and check immediately the playscore.
	 */
	@Test
	public void testPlayscore() {
		ArrayList<Geroid> geroids = game.getGeroids();
		Geroid nearestXGeroid;
		game.startGame();

		waitForEntryInGeroids();
		nearestXGeroid = game.getGeroids().get(0);
		nearestXGeroid = chooseNearestGeroidsOnXAxis(geroids, nearestXGeroid);
		moveIntoCollidingXCoordinates(nearestXGeroid);
		game.getProjectiles().add(figure.shoot());
		while (game.getScore().getScore() == 0) {
			System.out.print("");
		}
		assertNotEquals(game.getScore().getScore(), 0);
	}

	private void waitUntilCollisionWithGeroid(Geroid nearestXGeroid) {

		while (game.isRunning()) {
			System.out.print("");
		}
	}

	private void moveIntoCollidingXCoordinates(Geroid nearestXGeroid) {
		if (nearestXGeroid.getPosition().getxCoordiante() > FIGURE_X_POS) {

			for (int i = FIGURE_X_POS; i < nearestXGeroid.getPosition().getxCoordiante(); i += 10) {
				game.receiveMessage("68");
			}
			game.receiveMessage("68");

		} else {
			for (int i = FIGURE_X_POS; i > nearestXGeroid.getPosition().getxCoordiante(); i -= 10) {
				game.receiveMessage("65");

			}
			game.receiveMessage("65");

		}
	}

	private Geroid chooseNearestGeroidsOnXAxis(ArrayList<Geroid> geroids, Geroid nearestXGeroid) {
		Iterator<Geroid> iteratorGeroid = geroids.iterator();
		while (iteratorGeroid.hasNext()) {
			Geroid next = iteratorGeroid.next();

			if (Math.abs(next.getPosition().getxCoordiante() - FIGURE_X_POS) < Math
					.abs(nearestXGeroid.getPosition().getxCoordiante() - FIGURE_X_POS)) {

				nearestXGeroid = next;
			}
		}

		return nearestXGeroid;
	}

	private boolean waitForEntryInGeroids() {
		boolean hit = false;

		while (game.getGeroids().size() < 4) {
			if (!game.isRunning()) {
				hit = true;
				break;
			}
			System.out.print("");
		}

		return hit;
	}

}
