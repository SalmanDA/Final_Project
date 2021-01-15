package id.ac.its.snakegame;

import java.security.SecureRandom;

public class MagicApple extends Sprite {

	private SecureRandom randomNumbers = new SecureRandom();
	int n;
	private boolean visible;
	
	// Constructor
	public MagicApple(int x, int y, boolean visible) {
		super(x, y);
		n = randomNumbers.nextInt(10);
		System.out.println(n+"\n");
 		if(n%2==0) {
 			this.visible = visible;
 			initMagicApple();
 		} else
 			this.visible = false;
			
	}

	// Constructor
	public MagicApple(boolean visible) {
		super();
		this.visible = visible;
		initMagicApple();
	}

	// Untuk memuat gambar MagicApple
	private void initMagicApple() {
		loadImage("img/magicapple.png");
		getImageDimensions();
	}

	// Setter getter
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

}