package id.ac.its.snakegame;

public class Poison extends Sprite{
	 
	//Atribut
	 private boolean visible;

	 //Constructor Poison dari parent class Sprite
	 public Poison(int x, int y, boolean visible) {
	  super(x, y);
	    this.visible = visible;
	    initPoison();
	   
	 }
	 //Constructor Poison 
	 public Poison(boolean visible) {
	  super();
	  this.visible = visible;
	  initPoison();
	 }

	 //Untuk memuat gambar poison dan dimensinya
	 private void initPoison() {
	  loadImage("img/poison.png");
	  getImageDimensions();
	 }
	 
	 //Untuk mengatur visibilitas
	 public void setVisible(boolean visible) {
	  this.visible = visible;
	 }
	 
	 //Untuk men-check apabila poison terlihat/tidak
	 public boolean isVisible() {
	  return visible;
	 }

	}
