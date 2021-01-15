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
	int DELAY = 175;

	int level;
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

	int running; // case of running {0 main menu, 1 game running, 2 Game Over, 5 highScore, 4
					// Credit, 3 level
	Timer timer;
	Random random;
	private Apple apples;
	private Head snake;
	private MagicApple mApple = new MagicApple(false);
	private List<Poison> poisons;
	String[] options = { "Easy", "Hard", "Expert", "Impossible" };
	HighscoreManager easyHighscore = new HighscoreManager("easyLevel.dat");
	HighscoreManager hardHighscore = new HighscoreManager("hardLevel.dat");
	HighscoreManager expertHighscore = new HighscoreManager("expertLevel.dat");
	HighscoreManager impossibleHighscore = new HighscoreManager("impossibleLevel.dat");

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

		level = JOptionPane.showOptionDialog(null, "Game Level", "Click a button", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		DELAY /= level + 1;

		running = 1;
		timer = new Timer(DELAY, this);
		timer.start();

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {
//		System.out.println("jalan");
		if (running == 1) {

			g.drawImage(apples.getImage(), apples.getX(), apples.getY(), this);

			if (mApple.isVisible()) {
				g.drawImage(mApple.getImage(), mApple.getX(), mApple.getY(), this);
			}

			if (poisonON) {
				for (int i = 0; i < poisons.size(); i++) {
					g.drawImage(poisons.get(i).getImage(), poisons.get(i).getX(), poisons.get(i).getY(), this);
				}
			}

//			System.out.println("jalan");
			for (int i = 0; i < snake.getBodyLength(); i++) {
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
		} else if (running == 2) {
			gameOver(g);
		} else if (running == 3) {
			highscore(g);
		} else if (running == 4) {
			credit(g);
		}

	}

	public void newApple() {
		do {
			appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
			appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
			for (int i = 0; i < snake.getBodyLength(); i++) {
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
			for (int i = 0; i < snake.getBodyLength(); i++) {
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
				for (int j = 0; j < snake.getBodyLength(); j++) {
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
		if (!snake.checkCollisions()) {
			running = 2;
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

	public void start() {
		point = 0;
		snake.setBodyLength(4);
		DELAY = 175;
		mAppleTrig = false;
		poisonON = false;
		mAppleCountDown = 0;
		poisonCountDown = 0;
		initGamePanel();
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

	public void highscore(Graphics g) {
		int scoreY, scoreX, titleY, tabWidth = 10;
		String[] title = { "Easy level Highscore", "Hard level Highscore", "Expert level Highscore",
				"Impossible level Highscore" };

		Font fontTitle = new Font("Helvetica", Font.BOLD, 36);
		FontMetrics fmTitle = getFontMetrics(fontTitle);
		Font fontScore = new Font("Helvetica", Font.BOLD, 18);
		FontMetrics fmScore = getFontMetrics(fontScore);

		g.setColor(Color.white);

		titleY = (SCREEN_HEIGHT - fmTitle.getHeight() - 20 - 5 * fmScore.getHeight()) / 2;
		g.setFont(fontTitle);
		g.drawString(title[level], (SCREEN_WIDTH - fmTitle.stringWidth(title[level])) / 2, titleY);

		scoreY = titleY + 20 + fmTitle.getHeight();
		g.setFont(fontScore);

		if (level == 0) {
			for (String line : easyHighscore.getHighscoreString().split("\n")) {
				scoreX = (SCREEN_WIDTH - fmScore.stringWidth(line) - 2 * tabWidth) / 2;
				for (String word : line.split("\t")) {
					g.drawString(word, scoreX, scoreY);
					scoreX += tabWidth + fmScore.stringWidth(word);
				}
				scoreY += fmScore.getHeight();
			}
		} else if (level == 1) {
			for (String line : hardHighscore.getHighscoreString().split("\n")) {
				scoreX = (SCREEN_WIDTH - fmScore.stringWidth(line) - 2 * tabWidth) / 2;
				for (String word : line.split("\t")) {
					g.drawString(word, scoreX, scoreY);
					scoreX += tabWidth + fmScore.stringWidth(word);
				}
				scoreY += fmScore.getHeight();
			}
		} else if (level == 2) {
			for (String line : expertHighscore.getHighscoreString().split("\n")) {
				scoreX = (SCREEN_WIDTH - fmScore.stringWidth(line) - 2 * tabWidth) / 2;
				for (String word : line.split("\t")) {
					g.drawString(word, scoreX, scoreY);
					scoreX += tabWidth + fmScore.stringWidth(word);
				}
				scoreY += fmScore.getHeight();
			}
		} else {
			for (String line : impossibleHighscore.getHighscoreString().split("\n")) {
				scoreX = (SCREEN_WIDTH - fmScore.stringWidth(line) - 2 * tabWidth) / 2;
				for (String word : line.split("\t")) {
					g.drawString(word, scoreX, scoreY);
					scoreX += tabWidth + fmScore.stringWidth(word);
				}
				scoreY += fmScore.getHeight();
			}
		}
	}

	public void credit(Graphics g) {
		int collaboratorTitleY, collaboratorY, dosenY, dosenTitleY, referencesTitleY, referencesY, tabWidth = 10;

		g.setColor(Color.WHITE);
		g.setFont(new Font("Calibri", 1, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Collaborator:\n", (SCREEN_WIDTH - metrics1.stringWidth("Collaborator:")) / 2,
				g.getFont().getSize());

		g.setFont(new Font("Calibri", 1, 30));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		collaboratorY = 35 + metrics1.getHeight();
		String[] collaborator = { "Salman Damai Alfariq 159", "Muhammad Fikri Sandi Pratama 195",
				"Muhammad Rizky Widodo 216" };
		for (int i = 0; i < collaborator.length; i++) {
			g.drawString(collaborator[i] + "\n", (SCREEN_WIDTH - metrics2.stringWidth(collaborator[i])) / 2,
					collaboratorY + metrics2.getHeight() * i);
		}

		g.setFont(new Font("Calibri", 1, 40));
		dosenTitleY = 20 + collaboratorY + metrics2.getHeight() * (collaborator.length);
		g.drawString("Dosen Pembimbing:\n", (SCREEN_WIDTH - metrics1.stringWidth("Dosen Pembimbing:\n")) / 2,
				dosenTitleY);
		g.setFont(new Font("Calibri", 1, 30));
		dosenY = dosenTitleY + metrics1.getHeight();
		g.drawString("Abdul Munif, S.Kom., M.Sc.\n",
				(SCREEN_WIDTH - metrics2.stringWidth("Abdul Munif, S.Kom., M.Sc.")) / 2, dosenY);

		g.setFont(new Font("Calibri", 1, 40));
		referencesTitleY = dosenY + metrics2.getHeight() + 20;
		g.drawString("Referensi:\n", (SCREEN_WIDTH - metrics1.stringWidth("Referensi\n")) / 2, referencesTitleY);
		String[] references = { "http://zetcode.com/javagames/snake/",
				"http://forum.codecall.net/topic/50071-making-a-simple-high-score-system/" };
		g.setFont(new Font("Calibri", 1, 30));
		for (int i = 0; i < references.length; i++) {
			g.drawString(references[i] + "\n", (SCREEN_WIDTH - metrics2.stringWidth(references[i])) / 2,
					referencesTitleY + metrics2.getHeight() * i + 40);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (running == 1) {
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
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_SPACE) {
				if (running == 2) {
					String playerName = JOptionPane.showInputDialog("Enter your name");
					if (level == 0) {
						easyHighscore.addScore(playerName, point);
					} else if (level == 1) {
						hardHighscore.addScore(playerName, point);
					} else if (level == 2) {
						expertHighscore.addScore(playerName, point);
					} else {
						impossibleHighscore.addScore(playerName, point);
					}
					running = 3;
				} else if (running == 3) {
					running = 4;
				} else if (running == 4) {
					timer.stop();
					start();
				}
			}
		}
	}

}