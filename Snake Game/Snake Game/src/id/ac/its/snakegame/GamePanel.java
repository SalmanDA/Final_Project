package id.ac.its.snakegame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 500;
	public static final int UNIT_SIZE = 25;
	public static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
	static final int DELAY = 175;
	final int x = 0;
	final int y = 0;
	int bodyParts = 10;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = true;
	Timer timer;
	Random random;
	private Apple apples;
	private Head snake;

	public GamePanel() {
		random = new Random();
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		initGamePanel();
	}

	private void initGamePanel() {
//	     System.out.println("jalan");
		snake = new Head(x, y);
		newApple();
		running = true;
//	        spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);
//	        initAliens();
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
		// System.out.println("jalan");
		if (running) {

			// System.out.println("jalan");
			g.drawImage(apples.getImage(), apples.getX(), apples.getY(), this);
			// System.out.println("jalan");
			for (int i = 0; i < snake.bodyLength; i++) {
//	    System.out.println("jalan");
				if (i == 0) {
//	     System.out.println("jalan");
					g.drawImage(snake.getImage(), snake.getX(), snake.getY(), this);
					g.drawImage(snake.bodyParts[i].getImage(), snake.bodyParts[i].getX(), snake.bodyParts[i].getY(),
							this);
				} else {
					g.drawImage(snake.bodyParts[i].getImage(), snake.bodyParts[i].getX(), snake.bodyParts[i].getY(),
							this);
				}
			}
			g.setColor(Color.WHITE);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2,
					g.getFont().getSize());
		} else {
			gameOver(g);
		}

	}

	public void newApple() {
		appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
		apples = new Apple(appleX, appleY);
	}

	// public void move(){
	// snake.move();
	// }
	public void checkApple() {
		// System.out.println("lewat");
		if ((snake.getX() == appleX) && (snake.getY() == appleY)) {
			// apples=null;
			// snake.bodyLength++;
			snake.grow();
			applesEaten++;
			newApple();
		}
	}

	public void checkCollisions() {
		running = snake.checkCollisions();

		if (!running) {
			timer.stop();
		}
	}

	public void gameOver(Graphics g) {
		// Score
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2,
				g.getFont().getSize());
		// Game Over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (running) {
			// move();
			checkApple();
			snake.move();
			// move();
			checkCollisions();
		}
		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			snake.keyPressed(e);
		}
	}

}
