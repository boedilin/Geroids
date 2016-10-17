package ch.zhaw.soe.psit3.geroids.db;

import java.util.HashMap;

import ch.zhaw.soe.psit3.geroids.domain.Account;

public class Highscore {

	private int score;
	private HashMap<Integer, Integer> highScoreList;

	public boolean checkConnectionToDB() {
		return false;
	}

	public boolean addScore(long score) {
		return false;
	}

	public HashMap getTopTen() {
		return null;
	}

	public HashMap getMyPosition() {
		return null;
	}

	public HashMap getPositionForaccount(Account account) {
		return null;
	}

	public HashMap getRange(int fromRange, int toRange) {
		return null;
	}

	public int getCountOfScore() {
		return score;
	}
}
