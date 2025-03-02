//package skeletonCodeAssgnmt2;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;


import java.util.Scanner;
import java.util.concurrent.*;
//model is separate from the view.

public class WordApp {
//shared variables
	static int noWords=4;
	static int totalWords;

   	static int frameX=1000;
	static int frameY=600;
	static int yLimit=480;

	static WordDictionary dict = new WordDictionary(); //use default dictionary, to read from file eventually

	static WordRecord[] words;
	static volatile boolean done;  //must be volatile
	static Score score = new Score();
	//Create threadpool for GUI elements
	static Thread[] threadPool;
	//Create pool of animation objects
	static Animate[] animationPool;

	//Create UI Thread object
	static UIThread uiThread;

	static WordPanel w;
	static JPanel txt;
	static JFrame frame;

	//state for if reset or not
	static boolean reset = false;
	//state to indicate run status
	static boolean running = false;
	//State to indicate if the win message has been shown
	static boolean dialogShown = false;

	//Make GUI elements global
	static JLabel caught;
	static JLabel missed;
	static JLabel scr;
	static JPanel g;

	public static void setupGUI(int frameX,int frameY,int yLimit) {
		// Frame init and dimensions
    	frame = new JFrame("WordGame"); 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(frameX, frameY);
    	
      	g = new JPanel();
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
      	g.setSize(frameX,frameY);
 
    	
		w = new WordPanel(words,yLimit);
		w.setSize(frameX,yLimit+100);
	    g.add(w);
	    
	    
	    txt = new JPanel();
	    txt.setLayout(new BoxLayout(txt, BoxLayout.LINE_AXIS)); 
	    caught =new JLabel("Caught: " + score.getCaught() + "    ");
	    missed =new JLabel("Missed:" + score.getMissed()+ "    ");
	    scr =new JLabel("Score:" + score.getScore()+ "    ");    
	    txt.add(caught);
	    txt.add(missed);
	    txt.add(scr);
	    //[snip]
  
	    final JTextField textEntry = new JTextField("",20);
	    textEntry.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent evt){
	          String text = textEntry.getText();
			  //[snip]
			  //If text.equals(TextOnScreen) - Remove textOnScreen

			  /*
			  for(int i=0; i<noWords; i++){
			  	if(words[i].matchWord(text)){
					System.out.println("You scored!");
					score.caughtWord(words[i].getWord().length());
					w.repaint();
				}
			  }*/
			  (new Thread(new WordMatch(text, w, score))).start();

	          textEntry.setText("");
	          textEntry.requestFocus();
	      }
	    });
	   
	   txt.add(textEntry);
	   txt.setMaximumSize( txt.getPreferredSize() );
	   g.add(txt);
	    
	    JPanel b = new JPanel();
        b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS)); 
	   	JButton startB = new JButton("Start");;
		
			// add the listener to the jbutton to handle the "pressed" event
			startB.addActionListener(new ActionListener()
		    {
		      public void actionPerformed(ActionEvent e)
		      {
				  //[snip]
				if(!running){
					//If restarting
					if(reset){
						score.resetScore();
						w.setRunning(true);
						(new Thread(w)).start();
						for (WordRecord word : w.getWords()) {
							word.resetWord();
						}
						for (Animate animate : animationPool) {
							animate.setRunning(true);
						}
						for (int i=0; i<noWords;i++) {
							threadPool[i] = new Thread(animationPool[i]);
						} 		
					}
					if (!reset) {
						//At first start, set reset state to true so reset will occur when hit again
						reset = true;
					}
					running = true;
					for (Thread thread : threadPool){
						thread.start();
				  	}
				}
		    	  textEntry.requestFocus();  //return focus to the text entry field
		      }
		    });
		JButton endB = new JButton("End");;
			
				// add the listener to the jbutton to handle the "pressed" event
				endB.addActionListener(new ActionListener()
			    {
			      public void actionPerformed(ActionEvent e)
			      {
					  	//[snip]
						running = false;
						stopThreads();
						score.resetScore();
						resetStates();
			      }
				});
				
		JButton quitB = new JButton("Quit");;
				quitB.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						running = false;
						stopThreads();
						System.exit(0);
					}
				});

		b.add(startB);
		b.add(endB);
		b.add(quitB);
		
		g.add(b);
    	
      	frame.setLocationRelativeTo(null);  // Center window on screen.
      	frame.add(g); //add contents to window
        frame.setContentPane(g);     
       	//frame.pack();  // don't do this - packs it into small space
        frame.setVisible(true);

		
	}

	
public static String[] getDictFromFile(String filename) {
		String [] dictStr = null;
		try {
			Scanner dictReader = new Scanner(new FileInputStream(filename));
			int dictLength = dictReader.nextInt();
			//System.out.println("read '" + dictLength+"'");

			dictStr=new String[dictLength];
			for (int i=0;i<dictLength;i++) {
				dictStr[i]=new String(dictReader.next());
				//System.out.println(i+ " read '" + dictStr[i]+"'"); //for checking
			}
			dictReader.close();
		} catch (IOException e) {
	        System.err.println("Problem reading file " + filename + " default dictionary will be used");
	    }
		return dictStr;

	}

	public static void main(String[] args) {
    	
		//deal with command line arguments
		totalWords=Integer.parseInt(args[0]);  //total words to fall
		noWords=Integer.parseInt(args[1]); // total words falling at any point
		assert(totalWords>=noWords); // this could be done more neatly
		String[] tmpDict=getDictFromFile("./input/"+args[2]); //file of words
		if (tmpDict!=null)
			dict= new WordDictionary(tmpDict);
		
		WordRecord.dict=dict; //set the class dictionary for the words.
		
		words = new WordRecord[noWords];  //shared array of current words
		
		//[snip]
		
		setupGUI(frameX, frameY, yLimit);  
    	//Start WordPanel thread - for redrawing animation
		(new Thread(w)).start();

		int x_inc=(int)frameX/noWords;
	  	//initialize shared array of current words

		for (int i=0;i<noWords;i++) {
			words[i]=new WordRecord(dict.getNewWord(),i*x_inc,yLimit);
		}

		threadPool = new Thread[noWords];
		animationPool = new Animate[noWords];
		//uiThread = new UIThread(caught, missed, scr, score);

		//instanciate the objects with threads
		for (int i=0; i<noWords;i++) {
			//Create animation object
			animationPool[i] = new Animate(w, score, i);
			//Add animation to thread
			threadPool[i] = new Thread(animationPool[i]);
		}
		//threadPool[noWords] = new Thread(uiThread);

		while(true){
			updateScores();
			if(score.getMissed()>=totalWords){
				updateScores();
				stopThreads();
				if(!dialogShown){
					JOptionPane.showMessageDialog(frame, "Congratulations!, You got a score of "+score.getScore(), "Congratulations!",1);
					dialogShown = true;
				}
			}
		}
	}

	public static void stopThreads(){
		for(Animate animation : animationPool){
			animation.setRunning(false);
		}
		w.setRunning(false);
	}

	public static void resetStates(){
		dialogShown = false;
		running = false;
		//reset = false;
	}

	public static void updateScores(){
		caught.setText("Caught: " + score.getCaught() + "    ");
	    missed.setText("Missed:" + score.getMissed()+ "    ");
	    scr.setText("Score:" + score.getScore()+ "    ");	
	}

}
