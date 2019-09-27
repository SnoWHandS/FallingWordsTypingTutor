//package skeletonCodeAssgnmt2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JPanel;

public class WordPanel extends JPanel implements Runnable {
	public static volatile boolean done;
	private WordRecord[] words;
	private int noWords;
	private int maxY;
	boolean running;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		g.clearRect(0,0,width,height);
		g.setColor(Color.red);
		g.fillRect(0,maxY-10,width,height);
		g.setColor(Color.black);
		g.setFont(new Font("Helvetica", Font.PLAIN, 26));
		//draw the words
		for (int i=0;i<noWords;i++){	    	
			g.drawString(words[i].getWord(),words[i].getX(),words[i].getY());	
		}
		
	}
	
	WordPanel(WordRecord[] words, int maxY) {
		this.words=words; //will this work?
		noWords = words.length;
		done=false;
		this.maxY=maxY;
		running = true;
	}

	public void setRunning(boolean state){
		running = state;
	}

	public WordRecord[] getWords () {
		return this.words;
	}

	public void run() {
		//add in code to animate this
		while(running){
			repaint();
		}
	}

}


