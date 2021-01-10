package id.ac.its.snakegame;

public class Poison extends Sprite{
	 
	 private boolean visible;

	 public Poison(int x, int y, boolean visible) {
	  super(x, y);
	    this.visible = visible;
	    initPoison();
	   
	 }

	 public Poison(boolean visible) {
	  super();
	  this.visible = visible;
	  initPoison();
	 }

	 private void initPoison() {
	  loadImage("img/poison.png");
	  getImageDimensions();
	 }

	 public void setVisible(boolean visible) {
	  this.visible = visible;
	 }

	 public boolean isVisible() {
	  return visible;
	 }

	}
