import javax.swing.JLabel;

public class UIThread implements Runnable{

    JLabel caught;
    JLabel missed;
    JLabel scr;
    Score score;
    boolean running = false;

    public UIThread(JLabel caught, JLabel missed, JLabel scr, Score score){
        //get GUI elements
        this.caught = caught;
        this.missed = missed;
        this.scr = scr;
        this.score = score;
        running = true;
    }

    public void run(){
        while(running){
            updateScores();
        }
    }

    public void setRunning(boolean state){
        running = state;
    }
 
    public void updateScores(){
		caught.setText("Caught: " + score.getCaught() + "    ");
	    missed.setText("Missed:" + score.getMissed()+ "    ");
	    scr.setText("Score:" + score.getScore()+ "    ");	
	}
}