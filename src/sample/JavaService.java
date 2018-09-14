package sample;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class JavaService {



    private static JavaService
            service = new JavaService();

    private ScheduledExecutorService executor;

    public static void main(String args[]) {
        service.start();
    }

    public void start() {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.execute(new ImpGiacFromDbfRunnable());
    }

    public void stop() {
        if (executor != null) {
            executor.shutdown();
        }
    }
}

class ScheduledService implements Runnable {


    @Override
    public void run() {
        System.out.println("I'm alive!");
        }
    }

