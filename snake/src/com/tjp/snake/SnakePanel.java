package com.tjp.snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SnakePanel extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
	private Snake snake;
	private long delay;
	private Food current;
	private char direction;
	private int score;
	private int status; // 1开始 0暂停 -1结束
	private String message;
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public SnakePanel() {
		snake = new Snake();
		Random random = new Random();
		current = new Food(random.nextInt(40) * 20, random.nextInt(25) * 20 + 100, Color.BLACK, false);
		direction = 'd';
		delay = 200;
		status = 1;
		score = 0;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// 整个窗口上面留出一百的空间，只用500的高度
		g.setFont(new Font("宋体", 0, 50));
		g.drawString("得分：" + score, 50, 80);
		g.setFont(new Font("宋体", 0, 20));
		g.drawString("7分以下建议去医院看看", 250, 40);
		g.drawString("10分以上的都是高手", 250, 80);
		g.setFont(new Font("宋体", 0, 50));
		g.drawString("空格可暂停", 500, 70);
		g.setColor(new Color(0, 0, 0, 30));
		for (int i = 0; i < 500 / 20; i++) {
			g.drawLine(0, i * 20 + 100, 800, i * 20 + 100);
		}
		for (int i = 0; i < 800 / 20; i++) {
			g.drawLine(i * 20, 100, i * 20, 600);
		}
		drawSnake(g);
		if (!current.isInBody()) {
			g.setColor(current.getColor());
			g.fillRect(current.getX(), current.getY(), 20, 20);
		} else {
			Random random = new Random();
			current = new Food(random.nextInt(40) * 20, random.nextInt(25) * 20 + 100, Color.BLACK, false);
		}

	}

	public void drawSnake(Graphics g) {
		for (int i = 0; i < snake.getBody().size(); i++) {
			Food f = snake.getBody().get(i);
			Random random = new Random();
			g.setColor(Color.RED);
			if (i > 1) {
				g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
			}
			g.fillRect(f.getX(), f.getY(), 20, 20);
		}
	}

	// 判断是否吃到食物
	public boolean isEat(Food f) {
		int x = snake.getBody().get(0).getX();
		int y = snake.getBody().get(0).getY();
		if (x == f.getX() && y == f.getY()) {
			return true;
		}
		return false;
	}

	/* 判断游戏是否结束：蛇撞四面墙 或者 蛇头碰到自己的身体 */
	public boolean isOver() {
		int x = snake.getBody().get(0).getX();
		int y = snake.getBody().get(0).getY();
		// 判断是否撞墙
		if (x > 780 || x < 0 || y > 580 || y < 100) {
			return true;
		}
		// 判断蛇头是否碰到自己的身体
		for (int i = 4; i < snake.getBody().size(); i++) {
			Food f = snake.getBody().get(i);
			if (x == f.getX() && y == f.getY()) {
				return true;
			}
		}
		return false;
	}

	public void action() {
		while (true) {
			if (status == 1) {
				snake.move(direction);
				if (isEat(current)) {
					current.setInBody(true);
					Food last = snake.getBody().get(snake.getBody().size() - 1);
					Food add = new Food();
					add.setColor(Color.BLACK);
					add.setInBody(true);
					switch (direction) {
					case 'u':
						add.setX(last.getX());
						add.setY(last.getY() + 20);
						break;
					case 'd':
						add.setX(last.getX());
						add.setY(last.getY() - 20);
						break;
					case 'l':
						add.setX(last.getX() + 20);
						add.setY(last.getY());
						break;
					case 'r':
						add.setX(last.getX() - 20);
						add.setY(last.getY());
						break;
					}
					snake.getBody().add(add);
					delay -= 10;
					score++;
				}
			} else if (status == 0) {

			} else {
				break;
			}
			if (isOver()) {
				status = -1;
				if (score < 8) {
					message = "游戏结束，兄弟，去医院看看吧";
				} else if (score < 10) {
					message = "游戏结束，你达标了";
				} else if (score < 12) {
					message = "游戏结束，你真厉害";
				} else if (score < 14) {
					message = "游戏结束，系统判断你单身至少20年";
				} else {
					message = "游戏结束，请问你是哪个星球上的宇宙人";
				}
				JLabel label = new JLabel(message);
				label.setFont(new Font("宋体", 0, 20));
				JOptionPane.showMessageDialog(null, label, "game over", JOptionPane.WARNING_MESSAGE);
			}
			repaint();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 32:
			if (status == 1) {
				status = 0;
			} else {
				status = 1;
			}
			break;
		case 37:
			if (direction != 'r') {
				direction = 'l';
			}
			break;
		case 38:
			if (direction != 'd') {
				direction = 'u';
			}
			break;
		case 39:
			if (direction != 'l') {
				direction = 'r';
			}
			break;
		case 40:
			if (direction != 'u') {
				direction = 'd';
			}
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}
}
