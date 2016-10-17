package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;

public class Account {

	private String nickname;
	private String password;
	private String eMail;
	private String prename;
	private String surname;
	private ArrayList<Microtransaction> mytransactions;

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}

}
