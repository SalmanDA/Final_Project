package id.ac.its.snakegame;

import java.security.SecureRandom;

public class MagicApple extends Sprite {

	private SecureRandom randomNumbers = new SecureRandom();
	int n;
	private boolean visible;
	
	public MagicApple(int x, int y, boolean visible) {
		super(x, y);
		n = randomNumbers.nextInt(10);
// 		if(n==7) {
 			this.visible = visible;
 			initMagicApple();
// 		}
			
	}

	public MagicApple(boolean visible) {
		super();
		this.visible = visible;
		initMagicApple();
	}

	private void initMagicApple() {
		loadImage("img/magicapple.png");
		getImageDimensions();
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

}