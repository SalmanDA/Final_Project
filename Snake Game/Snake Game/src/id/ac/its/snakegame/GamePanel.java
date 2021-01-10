package id.ac.its.snakegame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {

	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 500;
	public static final int UNIT_SIZE = 25;
	public static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / (UNIT_SIZE * UNIT_SIZE);
	static final int DELAY = 175;

	int point = 0;
	int appleX;
	int appleY;
	int mAppleX;
	int mAppleY;
	boolean applePlace;
	boolean mAppleTrig = false;
	int mAppleCountDown = 0;
	int poisonX;
	int poisonY;
	boolean poisonON = false;
	int poisonCountDown = 0;
	
	
	boolean running = true;
	Timer timer;
	Random random;
	private Apple apples;
	private Head snake;
	private MagicApple mApple = new MagicApple(false);
	private List<Poison> poisons;
	
	
	public GamePanel() {
		random = new Random();
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		initGamePanel();
	}

	private void initGamePanel() {
//    	System.out.println("jalan");
		snake = new Head(0, 0);
//    	newApple();
		apples = new Apple(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
//		System.out.println("jalan");
		if (running) {

			g.drawImage(apples.getImage(), apples.getX(), apples.getY(), this);

			if (mApple.isVisible()) {
				g.drawImage(mApple.getImage(), mApple.getX(), mApple.getY(), this);
			}
			
			if(poisonON) {
			    for(int i=0;i<poisons.size();i++) {
			     g.drawImage(poisons.get(i).getImage(), poisons.get(i).getX(), poisons.get(i).getY(), this);
			    }
			   }
			
//			System.out.println("jalan");
			for (int i = 0; i < snake.bodyLength; i++) {
//				System.out.println("jalan");
				if (i == 0) {
//					System.out.println("jalan");
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
			g.drawString("Score: " + point, (SCREEN_WIDTH - metrics.stringWidth("Score: " + point)) / 2,
					g.getFont().getSize());
		} else {
			gameOver(g);
		}

	}

	public void newApple() {
		do {
			appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
			appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
			for (int i = 0; i < snake.bodyLength; i++) {
				if (appleX == snake.bodyParts[i].getX() && appleY == snake.bodyParts[i].getY()) {
					applePlace = false;
					break;
				} else
					applePlace = true;
			}
		} while (!applePlace);

		apples.setX(appleX);
		apples.setY(appleY);
	}

	public void newMagicApple() {
		do {
			mAppleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
			mAppleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
			for (int i = 0; i < snake.bodyLength; i++) {
				if (mAppleX == snake.bodyParts[i].getY() && mAppleY == snake.bodyParts[i].getY()) {
					applePlace = false;
					break;
				} else
					applePlace = true;
			}
			if (mAppleX == appleX && mAppleY == appleY) {
				applePlace = false;
			} else
				applePlace = true;
		} while (!applePlace);

		mApple = new MagicApple(mAppleX, mAppleY, true);
	}

	public void newPoison() {
		poisons = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			do {
				poisonX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
				poisonY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
				for (int j = 0; j < snake.bodyLength; j++) {
					if (poisonX == snake.bodyParts[i].getY() && poisonY == snake.bodyParts[i].getY()) {
						applePlace = false;
						break;
					} else
						applePlace = true;
				}
				if (poisonX == appleX && poisonY == appleY && applePlace) {
					applePlace = false;
				} else if (applePlace) // supaya gambar tidak saling menimpa dengan badan ular
					applePlace = true;
				if (mApple.isVisible() && poisonX == mAppleX && poisonY == mAppleY && applePlace) {
					applePlace = false;
				} else if (applePlace) // supaya gambar tidak saling menimpa dengan badan ular
					applePlace = true;
			} while (!applePlace);
			poisons.add(new Poison(poisonX, poisonY, true));
		}
	}

	public void checkCollisions() {
		running = snake.checkCollisions();
		if (!running) {
			timer.stop();
		}

		Rectangle rs = snake.getBounds();
		Rectangle ra = apples.getBounds();
		if (rs.intersects(ra)) {
			snake.grow();
			point += 1;
			newApple();
		}

		if (mApple.isVisible()) {
			Rectangle rma = mApple.getBounds();
			if (rs.intersects(rma)) {
				snake.grow();
				point += 10;
				mApple.setVisible(false);
				mAppleTrig = false;
				mAppleCountDown = 0;
			}
		}

		if (poisonON) {
			for (int i = 0; i < poisons.size(); i++) {
				Rectangle rp = poisons.get(i).getBounds();
				if (rs.intersects(rp)) {
					point -= 5;
					if (point < 0)
						point = 0;
					poisons.remove(i);
				}
			}
			if (poisons.isEmpty()) {
				poisonCountDown = 0;
				poisonON = false;
			}
		}

	}

	public void gameOver(Graphics g) {
		// Score
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + point, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + point)) / 2,
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
//			checkCollisions();
			if (point % 4 == 0 && point != 0 && !mApple.isVisible() && !mAppleTrig) {
				mAppleTrig = true;
				newMagicApple();
			} else if (point % 4 != 0) {
				mAppleTrig = false;
			}
			if (mApple.isVisible()) {
				if (e.getSource() == timer) {
					mAppleCountDown++;
					System.out.println("timer: " + mAppleCountDown);
				}
				if (mAppleCountDown == 60) {
					mAppleCountDown = 0;
					mApple.setVisible(false);
				}
			}

			if (point % 6 == 0 && point != 0 && !poisonON) {
				newPoison();
				poisonON = true;
			}
			if (poisonON) {
				if (e.getSource() == timer) {
					poisonCountDown++;
					System.out.println("poison: " + poisonCountDown);
				}
				if (poisonCountDown == 150) {
					poisonCountDown = 0;
					poisonON = false;
					for (int i = 0; i < poisons.size(); i++) {
						poisons.remove(i);
					}
				}
			}

			checkCollisions();
			snake.move();
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