package com.tjp.snake;

import javax.swing.JFrame;

public class MainWindow {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		//窗口总大小为：800*600
		window.setBounds(300, 50, 800, 600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setUndecorated(true);
		window.setTitle("贪吃蛇");
		
		SnakePanel panel = new SnakePanel();
		window.add(panel);
		window.addKeyListener(panel);
		
		
		window.setVisible(true);
		panel.action();

	}
}

