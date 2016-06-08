package parallel;

/**
 * Created by roy on 2016/5/5.
 */

public class RunnableADemo implements Runnable {
    private Thread t;
    private String threadName;
    int threadId;
    int a[];
    int answer;

    RunnableADemo(String name, int id){
        threadName = name;
        threadId = id;
        System.out.println("Creating " + threadName);
    }

    public void run() {
        System.out.println("Running " + threadName);
        for(int i = 0; i < a.length; i++){
            if(i%2==threadId){
                a[i] += 1;
            }
            try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted.");
        }
        }
        System.out.println("Thread " + threadName + " exiting.");
    }

    public void start(int[] nums) {
        a = nums;
        System.out.println("Starting " + threadName);
        if(t == null)
        {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    public void join() {
        try {
            t.join();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
