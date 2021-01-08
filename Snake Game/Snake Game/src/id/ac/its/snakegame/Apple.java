package id.ac.its.snakegame;

public class Apple extends Sprite{
 
 public Apple(int x, int y) {
  super(x, y);
  // TODO Auto-generated constructor stub
  initApple();
 }

 private void initApple() {
  loadImage("img/apple.png");
        getImageDimensions();
 }

}