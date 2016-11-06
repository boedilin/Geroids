package ch.zhaw.soe.psit3.geroids.domain;

import java.awt.Image;

public abstract class Skin {

	private String name;
	private Image pic;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Image getPic() {
		return pic;
	}
	public void setPic(Image pic) {
		this.pic = pic;
	}
	
	
}
