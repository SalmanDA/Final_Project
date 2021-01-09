package id.ac.its.snakegame;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Sprite {

 protected int x;
 protected int y;
 protected int width = GamePanel.UNIT_SIZE;
 protected int height = GamePanel.UNIT_SIZE;
//    protected Color color;
 protected Image image;

 public Sprite() {
		x=-100;
		y=-100;
	}
 
 public Sprite(int x, int y) {
  this.x = x;
  this.y = y;

//  this.color = color;
 }

 protected void getImageDimensions() {

  width = image.getWidth(null);
  height = image.getHeight(null);
 }

 protected void loadImage(String imageName) {

  ImageIcon ii = new ImageIcon(imageName);
  image = ii.getImage();
 }

 public Image getImage() {
  return image;
 }

 public int getX() {
  return x;
 }

 public int getY() {
  return y;
 }

 public void setX(int x) {
  this.x = x;
 }

 public void setY(int y) {
  this.y = y;
 }
 public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}