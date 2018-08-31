package sample;

import javafx.concurrent.Task;

public class ImportazioneMovimentiTemp extends Task<Integer> {

    private int max = 500;

    @Override
    protected Integer call() throws Exception {

        for(int i = 1;i<=max;i++){
            if (isCancelled()){
                updateMessage("Cancelled");
                return -1;
            }
            else{
                try{
                    Thread.sleep(10);
                }catch(InterruptedException ex){
                    updateMessage("Interrupted");
                    return -1;

                }
                updateMessage("I'm calculating...");
                updateProgress(i,max);
                updateValue(i);
            }
        }

        updateMessage("Done");
        return 0;
    }
}
