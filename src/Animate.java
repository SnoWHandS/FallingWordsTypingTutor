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
            for (int delay=0; delay<currentWords[index].getSpeed()/100; delay++){
                //delays it for some time
                try{
                    //System.out.println(currentWords[index].getSpeed()/10);
                    Thread.sleep(1);
                    //Thread.sleep(currentWords[index].getSpeed()/10);
                }catch (InterruptedException e){
                    System.out.println(e);
                }
            }
            /*
            try{
                //System.out.println(currentWords[index].getSpeed()/10);
                Thread.sleep(currentWords[index].getSpeed()/10);
            }catch (InterruptedException e){
                System.out.println(e);
            }*/

            //drop the given word by 1 incr.
            currentWords[index].drop(1);
            //System.out.println(currentWords[index].getWord()+"Dropped");
            if(currentWords[index].dropped()){
                currentWords[index].resetWord();
                scr.missedWord();
            }
            /* For added slowness; but actually makes it look less smooth
            if(currentWords[index].getY()==250){
                int newSpeed = currentWords[index].getSpeed()+500;
                currentWords[index].setSpeed(newSpeed);
            }*/
            if(currentWords[index].getY()>330){
                currentWords[index].setSpeed(1500);
            }
        }
    }
}