package ch.zhaw.soe.psit3.geroids.domain;

import java.util.ArrayList;

public class Account {

	private String nickname;
	private String password;
	private String eMail;
	private String prename;
	private String surname;
	private ArrayList<Microtransaction> mytransactions;
	private Skin activeSkin = Skin defaultSkin;
	private Type activeType = Type defaultType;

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}
	
	private void setActiveType(Type type){
		if(mytransactions.contains(type)){
			activeType = type;
		}
	}
	
	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getPrename() {
		return prename;
	}

	public void setPrename(String prename) {
		this.prename = prename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public ArrayList<Microtransaction> getMytransactions() {
		return mytransactions;
	}

	public void setMytransactions(ArrayList<Microtransaction> mytransactions) {
		this.mytransactions = mytransactions;
	}

	public Skin getActiveSkin() {
		return activeSkin;
	}

	public Type getActiveType() {
		return activeType;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	private void setActiveSkin(Skin skin){
		if(mytransactions.contains(skin)){
			activeSkin = skin;
		}
	}

}
