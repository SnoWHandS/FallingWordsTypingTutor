public class WordMatch implements Runnable{
    private String text;
    private WordPanel wPanel;
    private Score scr;

    
    public WordMatch(String text, WordPanel wPanel, Score scr){
        this.text = text;
        this.wPanel = wPanel;
        this.scr = scr;
    }
    
    public void run(){
        for(WordRecord word : wPanel.getWords()){
            if(word.matchWord(text)){
                //You scored
                scr.caughtWord(word.getWord().length());
            }
        }
    }
}