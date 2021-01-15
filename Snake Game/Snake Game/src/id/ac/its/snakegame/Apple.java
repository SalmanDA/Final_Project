package id.ac.its.snakegame;

public class Apple extends Sprite {
	// Constructor
	public Apple(int x, int y) {
		super(x, y);
		initApple();
	}

	// Untuk memuat gambar apple
	private void initApple() {
		loadImage("img/apple.png");
		getImageDimensions();
	}

}