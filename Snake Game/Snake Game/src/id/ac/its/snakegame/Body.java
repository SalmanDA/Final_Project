package id.ac.its.snakegame;

public class Body extends Sprite {

	//Constructor Body dari parent Sprite
	public Body(int x, int y) {
		super(x, y);
		initBody();
	}
	
	//Untuk memuat gambar badan ular 
	private void initBody() {
		loadImage("img/snakeBody.png");
		getImageDimensions();

	}

}