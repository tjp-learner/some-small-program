package com.tjp.snake;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Snake {
	private List<Food> body;

	public Snake() {
		body = new ArrayList<>();
		Food head = new Food(100, 160, Color.red, true);
		Food f1 = new Food(80, 160, Color.black, true);
		Food f2 = new Food(60, 160, Color.black, true);
		Food f3 = new Food(40, 160, Color.black, true);
		body.add(head);
		body.add(f1);
		body.add(f2);
		body.add(f3);
	}

	public List<Food> getBody() {
		return body;
	}

	public void setBody(List<Food> body) {
		this.body = body;
	}

	public void move(char direction) {
		for (int i = body.size() - 1; i > 0; i--) {
			body.get(i).setX(body.get(i - 1).getX());
			body.get(i).setY(body.get(i - 1).getY());
		}
		Food head = body.get(0);
		switch (direction) {
		case 'u':
			head.setY(head.getY() - 20);
			break;
		case 'd':
			head.setY(head.getY() + 20);
			break;
		case 'l':
			head.setX(head.getX() - 20);
			break;
		case 'r':
			head.setX(head.getX() + 20);
			break;
		}
	}
}
