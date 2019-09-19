public class Animate implements Runnable{

    //Global variables
    Score scr;
    WordPanel w;
    boolean running;
    int index;

    //Constructor
    Animate(WordPanel w, Score scr, int index){
        //Assign objects to local objects
        this.scr = scr;
        this.w = w;
        this.index = index;
        //Set object to running when instantiated
        this.running = true;
    }

    public void setRunning (boolean state) {
        running = state;
    }

    public void run(){
        while(running){
            WordRecord currentWords[] = w.getWords();
            //delay to make it human readable
            for (int delay=0; delay<currentWords[index].getSpeed()*500; delay++){
                //delays it for some time
            }
            
            //drop the given word by 1 incr.
            currentWords[index].drop(1);
            if(currentWords[index].dropped()){
                currentWords[index].resetWord();
                scr.missedWord();
            }
        }
    }
}